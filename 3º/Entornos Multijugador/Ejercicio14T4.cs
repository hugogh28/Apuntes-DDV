using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Entornos
{
    
     class Ejercicio14T4
    {
        const int N_Tareas = 10;
        Random _random = new Random();
        List<Task<string>> listatareas = new List<Task<string>>(N_Tareas);
        static void Main()
        {
            new Ejercicio14T4().Exec();
        }


        void Exec()
        {
            Console.WriteLine("Hola");
            for (int i = 0; i < N_Tareas; i++)
            {
                //listatareas.Add(Task.Run(Method));
            }

            while (listatareas.Count > 0)
            { 
                try
                {
                    int taskIndex = Task.WaitAny(listatareas.ToArray());// (listatareas);
                    var tareafi = listatareas[taskIndex];
                    listatareas.RemoveAt(taskIndex);
                    if (tareafi.IsFaulted)
                    { Console.WriteLine("Esta nos va a fallar"); }
                    else
                    { Console.Write("No");
                    }
                        Console.WriteLine("Una tarea ha dicho" + tareafi.Result);
                }
                catch (Exception ex)
                {
                    Console.WriteLine("Excepcion en tarea: " + ex.InnerException.Message.ToString());
                }
            }

        }

        string Method()
        {
            Thread.Sleep(_random.Next(500));

            if (_random.Next(10) < 2)
            {
                throw new MyException("Estamos probando");
            }
            else
            {
                return "Tarea correcta";
            }

        }
    }

    [Serializable]
    internal class MyException : Exception
    {
        public MyException()
        {
        }

        public MyException(string message) : base(message)
        {
        }

        public MyException(string message, Exception innerException) : base(message, innerException)
        {
        }

        protected MyException(SerializationInfo info, StreamingContext context) : base(info, context)
        {
        }
    }
}
