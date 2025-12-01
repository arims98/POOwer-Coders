//Modelo: Contiene la lógica y los datos esenciales de la aplicación.
//Representa el estado de la aplicación y reglas de negocio. Puede usar DAO para manipular la persistencia de datos.
package model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Estandar")
public class ClienteEstandar extends Cliente {
    
    public ClienteEstandar() {
        super();
    }

    public ClienteEstandar(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email);
    }

    @Override
    public String toString() {
        return String.format("Cliente Estándar: %s", super.toString());
    }
}