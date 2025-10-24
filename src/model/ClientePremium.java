package model;

public class ClientePremium extends Cliente {
    private final int cuotaAnual = 30;
    private final double descuentoEnvio = 0.2;

    public ClientePremium(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email);
    }
    //GETTERS Y SETTERS
    @Override
    public double getCuota() {
        return this.cuotaAnual;
    }
    @Override
    public double getDescuento() {
        return this.descuentoEnvio;
    }
    //Metodos Descuento
    public double aplicarDescuentoEnvio(double gastosEnvio) {
        return gastosEnvio * 0.8;
    }

    @Override
    public String toString() {
        return String.format("Cliente Premium: %s, Cuota Anual: %d, Descuento Envío: %.2f",
                super.toString(), this.cuotaAnual, this.descuentoEnvio);
    }
}
