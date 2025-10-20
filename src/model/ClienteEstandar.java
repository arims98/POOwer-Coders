package model;

public class ClienteEstandar extends Cliente {
    public ClienteEstandar(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email);
    }

    public String toString() {
        return "ClienteEstandar{" + super.toString() + "}";
    }
}
