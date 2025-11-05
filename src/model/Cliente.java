//Modelo: Contiene la lógica y los datos esenciales de la aplicación.
//Representa el estado de la aplicación y reglas de negocio. Puede usar DAO para manipular la persistencia de datos.
package model;

public abstract class Cliente {
    private String nombre;
    private String domicilio;
    private String nif;
    private String email;

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
