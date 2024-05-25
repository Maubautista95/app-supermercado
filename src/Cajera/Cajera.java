package Cajera;

import ClienteProducto.ClienteProducto;
import Producto.Producto;
import java.util.List;

public class Cajera implements Runnable {
    private int numero;
    private String nombre;
    private List<ClienteProducto> clientes;
    private long initialTime;
    private double totalProcessingTime;

    public Cajera(int numero, String nombre, List<ClienteProducto> clientes) {
        this.numero = numero;
        this.nombre = nombre;
        this.clientes = clientes;
        this.totalProcessingTime = 0;
    }

    public int getNumero() {
        return numero;
    }

    public String getNombre() {
        return nombre;
    }

    public List<ClienteProducto> getClientes() {
        return clientes;
    }

    public void setInitialTime(long initialTime) {
        this.initialTime = initialTime;
    }

    public double getTotalProcessingTime() {
        return totalProcessingTime;
    }

    @Override
    public void run() {
        for (ClienteProducto cliente : this.clientes) {
            long startTime = System.currentTimeMillis();
            long elapsedTime = (startTime - this.initialTime) / 1000;

            System.out.println("Cajera " + this.numero + " (" + this.nombre + ") comienza a procesar la compra del Cliente " + cliente.getId() +
                               " en el tiempo: " + elapsedTime + "seg");

            double tiempoCliente = 0;
            StringBuilder productosProcesados = new StringBuilder("Cliente " + cliente.getId() + " decidió llevar: ");

            for (Producto producto : cliente.getProductos()) {
                this.esperarXsegundos(producto.getTiempoDeProcesamiento());
                tiempoCliente += producto.getTiempoDeProcesamiento();
                productosProcesados.append(producto.getNombre()).append(", ");
            }

            productosProcesados.setLength(productosProcesados.length() - 2); // Eliminamos la última coma y espacio
            System.out.println(productosProcesados.toString());

            long endTime = System.currentTimeMillis();
            double tiempoTotalCliente = (endTime - this.initialTime) / 1000.0;
            System.out.println("Cajera " + this.numero + " (" + this.nombre + ") ha terminado de procesar Cliente " + cliente.getId() +
                               " en el tiempo: " + tiempoTotalCliente + "seg");
            System.out.println("El tiempo total de procesamiento fue: " + tiempoCliente + " segundos\n");

            this.totalProcessingTime += tiempoCliente;
        }
    }

    private void esperarXsegundos(double segundos) {
        try {
            Thread.sleep((long) (segundos * 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
