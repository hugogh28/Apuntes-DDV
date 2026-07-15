using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;

namespace URJC.EM.Tema4.Ejercicios
{
    class Ejercicio14
    {
        const int N_TAREAS = 10;
        readonly Random random = new Random();
        readonly List<Task<string>> tareas = new List<Task<string>>(N_TAREAS);

        static void Main()
        {
            new Ejercicio14().Exec();
        }

        void Exec()
        {
            for (int i = 0; i < N_TAREAS; i++)
            {
                tareas.Add(new Task<string>(Method, i));
            }

            foreach (var tarea in tareas)
            {
                tarea.Start();
            }

            while (tareas.Count > 0)
            {
                int taskIndex = Task.WaitAny(tareas.ToArray());
                var tareaFinalizada = tareas[taskIndex];
                tareas.RemoveAt(taskIndex);
                try
                {
                    Console.WriteLine($"Tarea finalizada: {tareaFinalizada.Result}");
                }
                catch (Exception e)
                {
                    Console.WriteLine($"Excepción en tarea : {e.InnerException.Message}");

                    throw e;
                }
            }
        }

        string Method(object taskID)
        {
            Thread.Sleep(random.Next(500));

            if (random.Next(10) < 2)
            {
                throw new TaskFailedException($"Tarea {(int)taskID} con error");
            }
            else
            {
                return String.Format($"Tarea {(int)taskID} correcta");
            }
        }
    }

    class TaskFailedException : Exception
    {
        public TaskFailedException(string message) : base(message)
        {
        }
    }
}
