//Modelo: Contiene la lógica y los datos esenciales de la aplicación.
//Representa el estado de la aplicación y reglas de negocio. Puede usar DAO para manipular la persistencia de datos.
package app.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Table(name = "CLIENTE")
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Cliente {
    @Column (name = "nombre")
    private String nombre;
    @Column (name = "domicilio")
    private String domicilio;
    @Id
    private String nif;
    @Column (name = "email")
    private String email;

    public Cliente() {} //Para JPA
   
    public Cliente(String nombre, String domicilio, String nif, String email) {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.nif = nif;
        this.email = email;
    }
    //GETTERS Y SETTERS
    //Nombre
    public String getNombre() {
        return this.nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    //Domicilio
    public String getDomicilio() {
        return this.domicilio;
    }
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }
    //NIF
    public String getNif() {
        return this.nif;
    }
    public void setNif(String nif) {
        this.nif = nif;
    }
    //email
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("Cliente: %s, Domicilio: %s, NIF: %s, Email: %s",
                this.nombre,
                this.domicilio,
                this.nif,
                this.email);
    }
}