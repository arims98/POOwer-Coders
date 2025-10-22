package modelo;

import java.util.Objects;

public abstract class Cliente {
    private final String nif;     // identificador único (recomendado inmutable)
    private String nombre;
    private String domicilio;
    private String email;

    public Cliente(String nombre, String domicilio, String nif, String email) {
        if (nif == null || nif.isBlank()) throw new IllegalArgumentException("NIF vacío");
        this.nif = nif.trim();
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.email = email;
    }

    // Getters / setters
    public String getNif() { return nif; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Para la lógica de negocio (Premium/Estandar)
    public abstract double getDescuento();   // [0..1]
    public abstract double getCuotaAnual();  // 0 si no aplica

    @Override
    public boolean equals(Object o) {
        return (o instanceof Cliente c) && nif.equals(c.nif);
    }

    @Override
    public int hashCode() { return Objects.hash(nif); }

    @Override
    public String toString() {
        return "Cliente{" +
                "nif='" + nif + '\'' +
                ", nombre='" + nombre + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

