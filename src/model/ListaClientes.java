package model;
import java.util.ArrayList;     //Creamos lista dinÃ¡mica
import java.util.List;      //Usamos la interfaz List
import java.util.stream.Collectors; //Para usar streams y collectors (hace de filtro)

public class ListaClientes {

    //"EstanterÃ­a dÃ³nde guardamos los clientes"
    private List<Cliente> lista;

    //Constructor que inicializa la lista (estanterÃ­a), como Array list vacÃ­a
    public ListaClientes() {
        this.lista = new ArrayList<>();
    }

    //MÃ©todo para aÃ±adir clientes a la lista
    public void agregarCliente(Cliente cliente) {
        //AÃ±adimos el cliente a la lista
        lista.add(cliente);
    }

    //MÃ©todo para obtener la lista de clientes
    public List<Cliente> getLista() {
        //Devuelve la lista completa
        return lista;  
    }

    //MÃ©todo para buscar un cliente por su DNI
    public Cliente buscarClientePorEmail(String email) {
        for (Cliente cliente : lista) {
            //Si el email del cliente coincide con el email buscado
            if (cliente.getEmail().equals(email)) {
                //Devolvemos el cliente encontrado
                return cliente;
            }
        }
        //Si no se encuentra el cliente, devolvemos null
        return null;
    }

    //MÃ©todo de filtrado por tipo de cliente (Estandar)
    public List<Cliente> getClientesEstandar() {
        //Se usan streams y "instanceof" para filtrar la lista
        return lista.stream()
                .filter(c -> c instanceof ClienteEstandar)
                .collect(Collectors.toList());
    }

    //MÃ©todo de filtrado por tipo de cliente (Premium)
    public List<Cliente> getClientesPremium() {
        //Se usan streams y "instanceof" para filtrar la lista
        return lista.stream()
                .filter(c -> c instanceof ClientePremium)
                .collect(Collectors.toList());
    }
    
}