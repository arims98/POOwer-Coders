package model;

public class ClientePremium extends Cliente{
    private final int cuotaAnual = 30;     //Cuota que paga el cliente premium
    private final double descuentoEnvio = 0.20;  //20% de descuento
    //Al utilizar valores constantes indicamos "final" en los atributos

    public ClientePremium(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email);
    }

    public int getCuotaAnual() {
        return cuotaAnual;
    }

    public double getDescuentoEnvio() {
        return descuentoEnvio;
    }

    @Override
    public String toString() {
    return super.toString().replace("}","") +
        ", tipo=Premium" +
        ", cuotaAnual=" + cuotaAnual +
        ", descuentoEnvio=" + descuentoEnvio +
        '}';
    }
}
