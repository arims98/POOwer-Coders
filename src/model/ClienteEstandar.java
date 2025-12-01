package model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Estandar")
public class ClienteEstandar extends Cliente {
    public ClienteEstandar() {} // Obligatorio JPA
    public ClienteEstandar(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email);
    }

    @Override
    public String toString() {
        return "Cliente Estándar: " + super.toString();
    }
}
