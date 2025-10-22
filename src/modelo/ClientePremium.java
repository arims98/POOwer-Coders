package modelo;

public class ClientePremium extends Cliente {
    private final int cuotaAnual = 30;
    private final double descuentoEnvio = 0.20; // 20% sobre gastos de envío

    public ClientePremium(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email);
    }

    public int getCuotaAnualInt() { return cuotaAnual; }
    public double getDescuentoEnvio() { return descuentoEnvio; }

    @Override public double getDescuento() { return descuentoEnvio; }
    @Override public double getCuotaAnual() { return cuotaAnual; }

    public double aplicarDescuentoEnvio(double gastosEnvio) { return gastosEnvio * (1 - descuentoEnvio); }

    @Override
    public String toString() {
        return "ClientePremium{" + super.toString() + ", cuotaAnual=" + cuotaAnual + ", descuentoEnvio=" + descuentoEnvio + "}";
    }
}