package model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Entity
@DiscriminatorValue("Premium")
public class ClientePremium extends Cliente {

    @Transient
    private final int cuotaAnual = 30;
    @Transient
    private final double descuentoEnvio = 0.2;

    public ClientePremium() {} // Obligatorio JPA
    public ClientePremium(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email);
    }

    public int getCuotaAnual() { return cuotaAnual; }
    public double getDescuentoEnvio() { return descuentoEnvio; }

    public double aplicarDescuentoEnvio(double gastosEnvio) {
        return gastosEnvio * 0.8;
    }

    @Override
    public String toString() {
        return String.format("Cliente Premium: %s, Cuota Anual: %d, Descuento Envío: %.2f",
                super.toString(), cuotaAnual, descuentoEnvio);
    }
}
