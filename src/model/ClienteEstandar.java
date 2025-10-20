//Creo subclase ClienteEstandar que hereda de Cliente

public class ClienteEstandar extends Cliente {

    public ClienteEstandar(String nombre, String domicilio, String nif, String email) {
        super(nombre,domicilio,nif,email);
    }

    @Override
    public String toString() {
        return "ClienteEstandar{}";
    }
}
