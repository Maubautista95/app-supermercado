package Main;

import Producto.Producto;
import ClienteProducto.ClienteProducto;
import Cajera.Cajera;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Creamos productos disponibles
        List<Producto> productosDisponibles = Arrays.asList(
            new Producto("Pan", 1.0),
            new Producto("Leche", 1.5),
            new Producto("Huevos", 0.5),
            new Producto("Queso", 2.0),
            new Producto("Cereal", 3.0),
            new Producto("Jugo", 2.5),
            new Producto("Fruta", 1.2),
            new Producto("Verdura", 0.8)
        );

        System.out.print("Ingrese el número de clientes: ");
        int numClientes = scanner.nextInt();
        scanner.nextLine(); // Consumimos el salto de línea

        List<ClienteProducto> clientes = new ArrayList<>();
        Random random = new Random();

        // Creamos clientes con productos aleatorios
        for (int i = 1; i <= numClientes; i++) {
            // Asignamos entre 1 y 5 productos aleatorios a cada cliente
            int numProductos = random.nextInt(5) + 1;
            List<Producto> productosCliente = new ArrayList<>();
            for (int j = 0; j < numProductos; j++) {
                productosCliente.add(productosDisponibles.get(random.nextInt(productosDisponibles.size())));
            }
            clientes.add(new ClienteProducto(i, productosCliente));
        }

        System.out.print("Ingrese el número de cajeras: ");
        int numCajeras = scanner.nextInt();
        scanner.nextLine(); // Consumimos el salto de línea

        List<Cajera> cajeras = new ArrayList<>();

        // Creamos cajeras
        for (int i = 1; i <= numCajeras; i++) {
            System.out.print("Ingrese el nombre de la cajera " + i + ": ");
            String nombreCajera = scanner.nextLine();
            cajeras.add(new Cajera(i, nombreCajera, new ArrayList<>())); // Inicialmente con lista de clientes vacía
        }

        // Asignamos clientes a las cajeras de manera equitativa
        for (int i = 0; i < clientes.size(); i++) {
            Cajera cajera = cajeras.get(i % numCajeras);
            cajera.getClientes().add(clientes.get(i));
        }

        List<Thread> hilosCajeras = new ArrayList<>();

        // Iniciamos hilos para que cada cajera procese sus clientes
        long initialTime = System.currentTimeMillis();
        for (Cajera cajera : cajeras) {
            cajera.setInitialTime(initialTime);
            Thread hilo = new Thread(cajera);
            hilosCajeras.add(hilo);
            hilo.start();
        }

        // Esperamos a que todos los hilos terminen
        for (Thread hilo : hilosCajeras) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Mostramos el resultado final
        for (Cajera cajera : cajeras) {
            System.out.println("\n" + cajera.getNombre() + " atendió a los siguientes clientes:");
            for (ClienteProducto cliente : cajera.getClientes()) {
                System.out.println("  - Cliente " + cliente.getId());
            }
            System.out.println("Tiempo total de atención: " + cajera.getTotalProcessingTime() + " segundos");
        }

        scanner.close();
    }
}
