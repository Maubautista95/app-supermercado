package Producto;

public class Producto {
    private String nombre;
    private double tiempoDeProcesamiento;

    public Producto(String nombre, double tiempoDeProcesamiento) {
        this.nombre = nombre;
        this.tiempoDeProcesamiento = tiempoDeProcesamiento;
    }

    public String getNombre() {
        return nombre;
    }

    public double getTiempoDeProcesamiento() {
        return tiempoDeProcesamiento;
    }
}
