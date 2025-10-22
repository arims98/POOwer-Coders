package modelo;

// Clase Cliente Estandar - Hereda de Cliente
public class ClienteEstandar extends Cliente {
    public ClienteEstandar(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email);
    }

    // Sin cuota, sin descuento en envío
    @Override public double getDescuento() { return 0.0; }    // [0..1], 0% envío
    @Override public double getCuotaAnual() { return 0.0; }

    @Override
    public String toString() {
        return "ClienteEstandar{" + super.toString() + "}";
    }
}
