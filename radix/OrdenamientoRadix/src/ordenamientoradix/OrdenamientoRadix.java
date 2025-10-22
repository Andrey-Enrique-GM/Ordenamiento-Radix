
package ordenamientoradix;

// @author Andrey

import java.util.Arrays;
import java.util.Scanner;

/**
 * Clase principal que implementa el algoritmo de ordenamiento Radix Sort
 * (version LSD - Least Significant Digit). 
 * 
 * Este algoritmo ordena numeros enteros positivos procesando cada digito 
 * individualmente, empezando desde las unidades hasta las cifras mas altas.
 * 
 * Utiliza Counting Sort como subrutina estable para cada posicion de digito.
 */
public class OrdenamientoRadix 
{

    /**
     * Obtiene el valor maximo dentro del arreglo.
     * 
     * Esto sirve para determinar cuantas cifras tiene el numero mas grande 
     * y, por tanto, cuantas pasadas de ordenamiento se deben realizar.
     * 
     * @param arr El arreglo de enteros.
     * @return El valor maximo encontrado.
     */
    private static int getMax(int[] arr) 
    {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    /**
     * Aplica el algoritmo Counting Sort (Ordenamiento por Conteo) a un arreglo,
     * considerando unicamente el digito indicado por el exponente 'exp'.
     * 
     * Este metodo mantiene la estabilidad del ordenamiento, lo cual es esencial
     * para que Radix Sort funcione correctamente.
     * 
     * @param arr El arreglo de enteros a ordenar.
     * @param exp El exponente correspondiente a la posicion del digito actual
     *            (1 = unidades, 10 = decenas, 100 = centenas, etc.)
     */
    private static void countingSort(int[] arr, int exp) 
    {
        
        int n = arr.length;
        int[] output = new int[n];      // Arreglo auxiliar donde se almacenan los resultados temporales
        int[] count = new int[10];     // Contadores para los digitos (0 al 9)

        // 1.- Inicializar el arreglo de conteo con ceros
        Arrays.fill(count, 0);
        for (int i = 0; i < n; i++) 
        {
            // Calcula el digito actual: (arr[i] / exp) % 10
            count[(arr[i] / exp) % 10]++;
        }

        // 2.- Contar cuantas veces aparece cada digito en la posicion actual
        for (int i = 1; i < 10; i++) 
        {
            count[i] += count[i - 1];
        }

        // 3.- Acumular los conteos para obtener las posiciones reales
        // (garantiza que el ordenamiento sea estable)
        for (int i = n - 1; i >= 0; i--) 
        {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }

        // 4.- Construir el arreglo de salida recorriendo de derecha a izquierda
        // Esto mantiene el orden estable (elementos iguales conservan su orden original)
        System.arraycopy(output, 0, arr, 0, n);
        
    }

    /**
     * Metodo principal del algoritmo Radix Sort (LSD).
     * 
     * Llama a Counting Sort una vez por cada posicion de digito, desde las unidades
     * hasta el digito mas significativo.
     * 
     * @param arr El arreglo de enteros positivos a ordenar.
     */
    public static void radixSort(int[] arr) 
    {
        
        if (arr.length == 0) return;    // Caso base: arreglo vacio

        // Obtener el valor maximo para determinar el numero de pasadas
        int max = getMax(arr);          

        // Aplicar Counting Sort para cada posicion de digito
        for (int exp = 1; max / exp > 0; exp *= 10) 
        {
            countingSort(arr, exp);
            // Mostrar el progreso despues de cada pasada
            System.out.println("  -> Despues de la pasada con exp=" + exp + ": " + Arrays.toString(arr));
        }
        
    }

    /**
     * Metodo principal (main) que permite la interaccion con el usuario.
     * 
     * 1.- Pide al usuario la cantidad de elementos.
     * 2.- Solicita los valores (solo numeros positivos). 
     * 3.- Muestra el proceso de ordenamiento paso a paso. 
     * 4.- Imprime el resultado final.
     * 
     * Incluye validaciones de entrada para evitar errores de tipo o valores negativos.
     * 
     * @param args Argumentos de linea de comandos (no se usan en este programa).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1: Pedir el tamaño del array
        System.out.println("--- Ordenamiento Radix Sort ---");
        System.out.print("Ingrese la cantidad de elementos que desea ordenar: ");
        
        int size = 0;
        
        // Validar que la entrada sea un numero entero
        try {
            size = scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Entrada invalida. Debe ingresar un numero entero.");
            scanner.close();
            return;
        }

        // Validar que el tamaño sea mayor que cero
        if (size <= 0) {
            System.out.println("La cantidad de elementos debe ser mayor que cero.");
            scanner.close();
            return;
        }

        int[] data = new int[size];

        // 2: Pedir los elementos del array
        System.out.println("\nIngrese los " + size + " elementos (solo numeros enteros positivos):");
        for (int i = 0; i < size; i++) {
            System.out.print("Elemento " + (i + 1) + ": ");
            try {
                data[i] = scanner.nextInt();
                if (data[i] < 0) {
                    System.out.println("Por favor, ingrese solo numeros positivos!!!");
                    scanner.close();
                    return;
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada invalida. Debe ingresar un numero entero positivo!!!");
                scanner.close();
                return;
            }
        }
        
        scanner.close(); // Cerrar/Limpiar el scanner

        // 3.1: Mostrar el array original
        System.out.println("\nArray original: " + Arrays.toString(data));

        // 3.2: Ejecutar el Radix Sort
        System.out.println("\n--- Proceso de Radix Sort (pasadas) ---");
        radixSort(data);

        // 4: Mostrar el resultado final
        System.out.println("\n--- Resultado Final ---");
        System.out.println("Array ordenado: " + Arrays.toString(data));
    }
    
}
