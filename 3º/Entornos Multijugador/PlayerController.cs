using Unity.Collections.LowLevel.Unsafe;
using Unity.Netcode;
using UnityEngine;
using UnityEngine.InputSystem;

public class PlayerController : NetworkBehaviour
{
    Vector2 _movement;
    float _speed = 0.2f;
    float _rotSpeed = 10.0f;

    Transform _playerTransform;

    NetworkVariable<Color> micolor = new NetworkVariable<Color>(Color.grey);


    // Start is called once before the first execution of Update after the MonoBehaviour is created
    void Start()
    {
        _playerTransform = transform;

        if (IsOwner)
        {
            GetComponent<PlayerInput>().enabled = true;
        }

        micolor.OnValueChanged += OnChangeColorEvent;
        GetComponentInChildren<Renderer>().material.color = micolor.Value;

    }

    private void OnChangeColorEvent(Color previousValue, Color newValue)
    {
        GetComponentInChildren<Renderer>().material.color = newValue;
    }

    // Update is called once per frame
    void Update()
    {
        
    }
    private void FixedUpdate()
    {
        if (!IsOwner) return;
        _playerTransform.Rotate(Vector3.up*(_movement.x*_rotSpeed));
        _playerTransform.Translate(Vector3.forward*(_movement.y*_speed));
    }

    public void OnMove(InputAction.CallbackContext context)
    {
        //Debug.Log("He recibido una acción");
        _movement = context.ReadValue<Vector2>();
        
    }

    public void OnJump(InputAction.CallbackContext context)
    {
        Debug.Log("Cambio de color");
        OnChangeColorRpc();
        Debug.Log("Mi color es"+ micolor.Value);
    }

    [Rpc(SendTo.Server)]
    void OnChangeColorRpc()
    {
        Color newColor = new Color(Random.Range(0F, 1F), Random.Range(0F, 1F), Random.Range(0F, 1F));
        GetComponentInChildren<Renderer>().material.color = newColor;
        micolor.Value = newColor;
        //BackChangeColorRpc(newColor);

    }

    [Rpc(SendTo.Everyone)]
    void BackChangeColorRpc(Color newColor)
    {
        GetComponentInChildren<Renderer>().material.color = newColor;
    }
}
