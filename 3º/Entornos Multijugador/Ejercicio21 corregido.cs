using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Entornos
{
    class Ejercicio21
    {
        static int ServerPort = 9050;
        static void Client(object o)
        {
            int minombre = (int)o;
            Socket client = new Socket(AddressFamily.InterNetwork,
                SocketType.Dgram, ProtocolType.Udp);
            EndPoint clientEP = new IPEndPoint(IPAddress.Loopback, ServerPort);
            client.ReceiveTimeout = -1;
            byte[] msg = new byte[1024];
            Thread.Sleep(100);
            string msgWelcome = "Handsake";
            ///PASO 1: Cliente manda un saludo
            msg = Encoding.UTF8.GetBytes(msgWelcome);
            client.SendTo(msg, clientEP);

            //PASO 4: Recibido el nuevo puerto, creo el nuevo socket
            int recv = client.ReceiveFrom(msg, ref clientEP);
            int nuevopuerto = int.Parse(Encoding.UTF8.GetString(msg, 0, recv));

            EndPoint nuevoConnectionServer = new IPEndPoint(IPAddress.Loopback, nuevopuerto);
            //PASO 5: Mandar mensajes por el nuevo puerto
            for (int i = 0; i < 10; i++) 
            {
                msgWelcome = "Mensaje"+i;
                Console.WriteLine("Cliente:" + minombre + msgWelcome);
                msg = Encoding.UTF8.GetBytes(msgWelcome);
                client.SendTo(msg, nuevoConnectionServer);

                if (msgWelcome == "EXIT")
                {
                    break;
                }
            }

            msgWelcome = "EXIT";
            msg = Encoding.UTF8.GetBytes(msgWelcome);
            client.SendTo(msg, nuevoConnectionServer);

            try
            {
                client.Shutdown(SocketShutdown.Both);
            }
            finally
            {
                client.Close();
            }

        }

        static void ConnectionThread(object o)
        {
            //PASO 5: Escuchar al cliente por este Hilo
            int newServerPort = (int)o;
            Socket connThread = new Socket(AddressFamily.InterNetwork,
                SocketType.Dgram, ProtocolType.Udp);
            connThread.SetSocketOption(SocketOptionLevel.Socket, SocketOptionName.ReuseAddress, true);

            EndPoint conEP = new IPEndPoint(IPAddress.Any, newServerPort);
            connThread.Bind(conEP);

            byte[] msg = new byte[1024];
            EndPoint remote = new IPEndPoint(IPAddress.Any, 0);
            while (true)
            {


                int recv = connThread.ReceiveFrom(msg, ref remote);
                string message = Encoding.UTF8.GetString(msg, 0, recv);
                Console.WriteLine(remote.ToString() + ":" + message);

                if (message == "EXIT")
                {
                    Console.WriteLine("Me han mandado cerrar");
                    break;
                }


            }

            try
            {
                connThread.Shutdown(SocketShutdown.Both);
            }
            finally
            {
                connThread.Close();
                connThread.Dispose();
            }

        }


        static void Server()
        {
            Socket server = new Socket(AddressFamily.InterNetwork,
                SocketType.Dgram, ProtocolType.Udp);
            EndPoint serverEP = new IPEndPoint(IPAddress.Any, ServerPort);
            server.Bind(serverEP);
            Console.WriteLine(" Server started at " + DateTime.UtcNow.ToString());

            
            EndPoint remote = new IPEndPoint(IPAddress.Any, 0);

            int i = 0;
            while(true) 
            {
                
                byte[] msg = new byte[1024];
                //PASO 2: Recibo el primer Handsake
                int recv = server.ReceiveFrom(msg, ref remote);
                string message = Encoding.UTF8.GetString(msg, 0, recv);
                Console.WriteLine(remote.ToString() + ":" + message);

                //PASO 3: AVISAR AL CLIENTE del nuevo puerto
                i++;
                if (i > 20)
                    i = 0;

                int nuevopuerto = ServerPort + 1 + i;
                msg = Encoding.UTF8.GetBytes(nuevopuerto.ToString());
                server.SendTo(msg,remote);

                //PASO4: Creo un nuevo hilo para comunicarme con el nuevo cliente            
                 new Thread(ConnectionThread).Start(nuevopuerto);
                

                /*if (message == "EXIT")
                {
                    Console.WriteLine("Me han mandado cerrar");
                    break;
                }*/
            }

            try
            {
                server.Shutdown(SocketShutdown.Both);
            }
            finally
            {
                server.Close();
            }
            


        }

        static void Main(string[] args)
        {
            new Thread(Server).Start();
            for (int i = 0; i < 10; i++)
            {
                
                //new Thread(Client).Start(i);
            }
            


        }

    }
}
