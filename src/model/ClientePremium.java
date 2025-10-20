package model;

public class ClientePremium extends Cliente {
    private final int cuotaAnual = 30;
    private final double descuentoEnvio = 0.2;

    public ClientePremium(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email);
    }

    public int getCuotaAnual() {
        return 30;
    }

    public double getDescuentoEnvio() {
        return 0.2;
    }

    public double aplicarDescuentoEnvio(double gastosEnvio) {
        return gastosEnvio * 0.8;
    }

    public String toString() {
        return "ClientePremium{" + super.toString() + ", cuotaAnual=" + 30 + ", descuentoEnvio=" + 0.2 + '}';
    }
}
