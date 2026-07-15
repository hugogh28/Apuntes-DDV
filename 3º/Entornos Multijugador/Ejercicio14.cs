using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Entornos
{


    class Ejercicio14
    {
        const int N_Hilos = 4;
        static volatile int contador = 0;
        static volatile Random random = new Random();

        static SemaphoreSlim barrera = new SemaphoreSlim(0);
        static SemaphoreSlim ExcMutua = new SemaphoreSlim(1);
        static SemaphoreSlim hiloFinal = new SemaphoreSlim(0);
        static void Proceso(object o)
        {
            char c = (char)o;
            for (int i = 0; i < 10; i++) 
            {
                Console.Write(c);
                ExcMutua.Wait();
                contador++;
                if (contador < N_Hilos)
                {
                    ExcMutua.Release();
                    //Sincronizar
                    
                    barrera.Wait();
                    hiloFinal.Release();
                }
                else
                {
                    contador = 0;
                    
                    Thread.Sleep(100);
                    Console.Write('-');
                    barrera.Release(N_Hilos - 1);
                    for (int j = 0; j < N_Hilos-1;j++)
                    {
                        hiloFinal.Wait();
                    }
                    ExcMutua.Release();

                }
            }
        }

        static void Main()
        {
            char letra = 'A';
            for (int i = 0; i < N_Hilos; i++)
            {
                new Thread(Proceso).Start(letra);
                letra++;
            }



        }


    }
}
