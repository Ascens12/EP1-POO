package org.upemor.ep1;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import lombok.Getter;
import lombok.Setter;
import java.util.Scanner;

/**
 *
 * @author gerardo
 */

@Getter
@Setter

public class GrafoPonderado {
    
    private String nombre;
    private boolean visitado;
    private List<Vertice> vertices;

    public GrafoPonderado() {
        this.vertices = new LinkedList<>();
    }

    // Método para crear el grafo desde el scanner
    public void crearGrafo(Scanner scanner) {
        System.out.print("Ingresa el nombre del grafo: ");
        this.nombre = scanner.nextLine();

        System.out.print("Ingresa el número de nodos en el grafo: ");
        int numeroNodos = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        // Solicitar nombres para cada nodo
        for (int i = 0; i < numeroNodos; i++) {
            System.out.print("Ingresa el nombre del nodo " + (i + 1) + ": ");
            String nombreNodo = scanner.nextLine();
            Vertice vertice = new Vertice(nombreNodo);
            this.vertices.add(vertice);
        }

        // Solicitar lista de adyacencias para cada nodo
        System.out.println("\nIngresa las adyacencias para cada nodo. Si no tiene más adyacencias, ingresa 'fin'.");
        for (Vertice vertice : this.vertices) {
            System.out.println("Adyacencias para el nodo " + vertice.getNombre() + ":");
            while (true) {
                System.out.print("Ingresa el nombre del nodo adyacente a " + vertice.getNombre() + " o 'fin': ");
                String adyacente = scanner.nextLine();
                if (adyacente.equalsIgnoreCase("fin")) {
                    break;
                }
                Vertice nodoAdyacente = buscarVerticePorNombre(adyacente);
                if (nodoAdyacente != null) {
                    vertice.addAdyacencias(new Adyacencia(nodoAdyacente, 1));  // Agregar adyacencia con un peso de 1
                } else {
                    System.out.println("El nodo " + adyacente + " no existe.");
                }
            }
        }

        System.out.println("Grafo creado con éxito.");
    }

    // Mostrar información del grafo
    public void mostrarInformacionGrafo() {
        if (this.vertices.isEmpty()) {
            System.out.println("Primero debes crear o cargar un grafo.");
        } else {
            mostrarListaAdyacencias();
            mostrarGradoNodos();
        }
    }

    // Método para realizar el recorrido en anchura
    public void recorridoAnchuraDesdeUsuario(Scanner scanner) {
        if (this.vertices.isEmpty()) {
            System.out.println("Primero debes crear o cargar un grafo.");
        } else {
            System.out.print("Ingresa el nodo inicial para el recorrido en anchura: ");
            String nodoInicio = scanner.nextLine();
            Vertice inicio = buscarVerticePorNombre(nodoInicio);
            if (inicio != null) {
                recorridoPorAnchura(inicio);
            } else {
                System.out.println("El nodo no existe.");
            }
        }
    }

    // Método para realizar el recorrido en profundidad
    public void recorridoProfundidadDesdeUsuario(Scanner scanner) {
        if (this.vertices.isEmpty()) {
            System.out.println("Primero debes crear o cargar un grafo.");
        } else {
            System.out.print("Ingresa el nodo inicial para el recorrido en profundidad: ");
            String nodoInicio = scanner.nextLine();
            Vertice inicio = buscarVerticePorNombre(nodoInicio);
            if (inicio != null) {
                recorridoProfundidad(inicio);
            } else {
                System.out.println("El nodo no existe.");
            }
        }
    }

    // Verificar si el grafo es conexo
    public void verificarConectividad() {
        if (this.vertices.isEmpty()) {
            System.out.println("Primero debes crear o cargar un grafo.");
        } else {
            boolean conexo = esConexo();
            System.out.println("¿El grafo es conexo?: " + conexo);
        }
    }

    // Guardar el grafo en un archivo
    public void guardarGrafo(Scanner scanner) throws IOException {
        if (this.vertices.isEmpty()) {
            System.out.println("Primero debes crear o cargar un grafo.");
        } else {
            System.out.print("Ingresa el nombre del archivo (con extensión .graph): ");
            String nombreArchivo = scanner.nextLine();
            guardarGrafoEnArchivo(nombreArchivo);
            System.out.println("El grafo ha sido guardado en " + nombreArchivo);
        }
    }

    // Cargar un grafo desde un archivo
    public void cargarGrafo(Scanner scanner) throws IOException {
        System.out.print("Ingresa el nombre del archivo a cargar (con extensión .graph): ");
        String nombreArchivo = scanner.nextLine();
        cargarGrafoDesdeArchivo(nombreArchivo);
        System.out.println("El grafo cargado desde " + nombreArchivo + " es:");
        mostrarListaAdyacencias();
    }

    
    public void addVertices(Vertice... vertices){
        Collections.addAll(this.vertices, vertices);
    }
    
    public void mostrarListaAdyacencias() {
        System.out.println("\n\n Lista de adyacencias");
        for (Vertice vertice : vertices) {
            String etiquetaStr = (vertice.getEtiqueta() != null) ? vertice.getEtiqueta().toString() : "null";
            System.out.print(vertice + " " + etiquetaStr + " -> {");
            for (Adyacencia adyacencia : vertice.getAdyacencias()) {
                System.out.print(adyacencia + ", ");
            }
            System.out.println("}");
        }
    }
    
    public void mostrarGradoNodos() {
        System.out.println("\nGrado de los nodos:");
        for (Vertice vertice : vertices) {
            System.out.println("Nodo: " + vertice.getNombre() + " - Grado: " + vertice.getAdyacencias().size());
        }
    }
    
    public Vertice buscarVerticePorNombre(String nombre) {
        for (Vertice vertice : vertices) {
            if (vertice.getNombre().equalsIgnoreCase(nombre)) {
                return vertice; // Devuelve el vértice si el nombre coincide
            }
        }
        return null; // Devuelve null si no se encuentra ningún vértice con ese nombre
    }
    
    public boolean esConexo() {
        if (vertices.isEmpty()) return false; // Si no hay vértices, no puede ser conexo

        // Limpiar el estado de visitado de los vértices
        for (Vertice vertice : vertices) {
            vertice.setVisitado(false);
        }

        // Realizar recorrido desde el primer vértice
        recorridoPorAnchura(vertices.get(0));

        // Verificar si todos los vértices fueron visitados
        for (Vertice vertice : vertices) {
            if (!vertice.isVisitado()) {
                return false;
            }
        }
        return true;
    }

    
    public void recorridoPorAnchura(Vertice inicio){
        Queue<Vertice> cola = new LinkedList<>();
        inicio.setVisitado(true);
        cola.add(inicio);
        
        Vertice actual = null;
        while(!cola.isEmpty()){
            actual = cola.poll();
            System.out.println(actual);
            
            for(Adyacencia a: actual.getAdyacencias()){
                if(!a.getVertice().isVisitado()){
                    a.getVertice().setVisitado(true);
                    cola.add(a.getVertice());
                }
            }
        }
    }
    
    public void recorridoProfundidad(Vertice inicio){
        Stack<Vertice> pila = new Stack<>();
        inicio.setVisitado(true);
        pila.add(inicio);

        Vertice actual = null;
        while(!pila.isEmpty() ){
            actual = pila. pop();
            System. out.println(actual);
            for (Adyacencia a: actual.getAdyacencias()){
                if(!a.getVertice().isVisitado()){
                    a.getVertice().setVisitado(true);
                    pila.add(a.getVertice());
                }
            }
        }
    }
    
    public void caminoMasCorto(Vertice inicio) {
        Queue<Vertice> cola = new LinkedList<>();
        inicio.setVisitado(true);
        inicio.setEtiqueta(new Etiqueta(inicio, 0));  // Asegurar que la etiqueta inicial tiene costo 0
        cola.add(inicio);

        Vertice actual;
        while (!cola.isEmpty()) {
            actual = cola.poll();

            for (Adyacencia a : actual.getAdyacencias()) {
                Vertice verticeAdyacente = a.getVertice();
                double nuevoCosto = actual.getEtiqueta().getCosto() + a.getCosto();
                Etiqueta nuevaEtiqueta = new Etiqueta(actual, nuevoCosto);

                // Si el vértice adyacente no ha sido visitado o el nuevo costo es menor, actualizar etiqueta
                if (!verticeAdyacente.isVisitado() || nuevoCosto < verticeAdyacente.getEtiqueta().getCosto()) {
                    verticeAdyacente.setVisitado(true);
                    verticeAdyacente.setEtiqueta(nuevaEtiqueta);
                    cola.add(verticeAdyacente);  // Agregar a la cola para seguir explorando
                }
            }
        }
    }
   
    public void guardarGrafoEnArchivo(String nombreArchivo) throws IOException {
        File archivo = new File(nombreArchivo);  // Utiliza la clase File para crear el archivo
        try (FileWriter writer = new FileWriter(archivo)) {
            // Guardar los nombres de los nodos
            for (Vertice vertice : vertices) {
                writer.write(vertice.getNombre() + ";");
            }
            writer.write(System.lineSeparator());  // Nueva línea

            // Guardar las listas de adyacencias
            for (Vertice vertice : vertices) {
                writer.write(vertice.getNombre() + ";");
                for (Adyacencia adyacencia : vertice.getAdyacencias()) {
                    writer.write(adyacencia.getVertice().getNombre() + ";");
                }
                writer.write(System.lineSeparator());  // Nueva línea
            }
        }
    }


    public void cargarGrafoDesdeArchivo(String nombreArchivo) throws IOException {
        File archivo = new File(nombreArchivo);  // Utiliza la clase File para leer el archivo
        try (FileReader reader = new FileReader(archivo)) {
            StringBuilder sb = new StringBuilder();
            int i;
            while ((i = reader.read()) != -1) {
                sb.append((char) i);
            }
            String[] lineas = sb.toString().split(System.lineSeparator());

            // Obtener los nombres de los nodos
            String[] nombresNodos = lineas[0].split(";");

            // Crear los vértices
            Map<String, Vertice> mapaVertices = new HashMap<>();
            for (String nombre : nombresNodos) {
                Vertice vertice = new Vertice(nombre);
                mapaVertices.put(nombre, vertice);
                this.vertices.add(vertice);
            }

            // Leer las adyacencias
            for (int j = 1; j < lineas.length; j++) {
                String[] partes = lineas[j].split(";");
                Vertice vertice = mapaVertices.get(partes[0]);

                for (int k = 1; k < partes.length; k++) {
                    Vertice verticeAdyacente = mapaVertices.get(partes[k]);
                    vertice.addAdyacencias(new Adyacencia(verticeAdyacente, 1));  // Asumimos costo de 1
                }
            }
        }
    }


    
}
