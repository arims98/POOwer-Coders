package model;

public class Articulo {
    private int codigoArticulo;
    private String descripcion;
    private float precioVenta;
    private float gastosEnvio;
    private int tiempoPrep;

    //Creo el constructor de la clase

    public Articulo (int codigoArticulo, String descripcion, float precioVenta, float gastosEnvio, int tiempoPrep)  {
        this.codigoArticulo = codigoArticulo;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.gastosEnvio = gastosEnvio;
        this.tiempoPrep = tiempoPrep;
    }
//Creo los getters y setters

    public int getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(int codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public int getTiempoPrep() {
        return tiempoPrep;
    }

    public void setTiempoPrep(int tiempoPrep) {
        this.tiempoPrep = tiempoPrep;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public float getGastosEnvio() {
        return gastosEnvio;
    }

    public void setGastosEnvio(float gastosEnvio) {
        this.gastosEnvio = gastosEnvio;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "codigoArticulo=" + codigoArticulo +
                ", descripcion='" + descripcion + '\'' +
                ", precioVenta=" + precioVenta +
                ", gastosEnvio=" + gastosEnvio +
                ", tiempoPrep=" + tiempoPrep +
                '}';
    }
}
