//Modelo: Contiene la lógica y los datos esenciales de la aplicación.
//Representa el estado de la aplicación y reglas de negocio. Puede usar DAO para manipular la persistencia de datos.
package model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Premium")
public class ClientePremium extends Cliente {
    
    @Column(name = "cuota_anual")
    private final int cuotaAnual = 30;
    
    @Column(name = "descuento_envio")
    private final double descuentoEnvio = 0.2;

    public ClientePremium() {
        super();
    }

    public ClientePremium(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email);
    }
    //GETTERS Y SETTERS
    public int getCuotaAnual() {
        return 30;
    }
    public double getDescuentoEnvio() {
        return 0.2;
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