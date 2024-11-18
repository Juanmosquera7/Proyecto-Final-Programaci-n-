// Clase base Alojamiento
package co.edu.uniquindio.alojamiento.modelo;

import co.edu.uniquindio.alojamiento.modelo.factory.TipoAlojamientos;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class Alojamiento {
    private String idAlojamiento;
    private String nombre;
    private String ciudad;
    private String descripcion;
    private String imagenUrl;
    private float precioPorNoche;
    private int capacidadMaxima;
    private List<String> servicios; // Ejemplo: piscina, wifi, desayuno
    private TipoAlojamientos tipo;
    private Oferta oferta;



    @Override
    public String toString() {
        return "Nombre: " + nombre + " | Tipo: " + tipo + " | Ciudad: " + ciudad;
    }
}
