package org.upemor.ep1;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author gerardo
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        GrafoPonderado grafo = new GrafoPonderado();

        boolean continuar = true;
        System.out.println("Bienvenido al gestor de grafos.");

        while (continuar) {
            // Menú de opciones
            System.out.println("\nElige una opción:");
            System.out.println("1. Crear un nuevo grafo");
            System.out.println("2. Ver la información del grafo actual");
            System.out.println("3. Realizar recorrido por anchura");
            System.out.println("4. Realizar recorrido por profundidad");
            System.out.println("5. Verificar si el grafo es conexo");
            System.out.println("6. Guardar grafo en archivo");
            System.out.println("7. Cargar grafo desde archivo");
            System.out.println("8. Salir");
            System.out.print("Ingresa tu opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    grafo.crearGrafo(scanner);
                    break;
                case 2:
                    grafo.mostrarInformacionGrafo();
                    break;
                case 3:
                    grafo.recorridoAnchuraDesdeUsuario(scanner);
                    break;
                case 4:
                    grafo.recorridoProfundidadDesdeUsuario(scanner);
                    break;
                case 5:
                    grafo.verificarConectividad();
                    break;
                case 6:
                    grafo.guardarGrafo(scanner);
                    break;
                case 7:
                    grafo.cargarGrafo(scanner);
                    break;
                case 8:
                    continuar = false;
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, selecciona una opción válida.");
            }
        }

        scanner.close();
    }
}
