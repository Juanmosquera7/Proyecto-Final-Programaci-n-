package co.edu.uniquindio.alojamiento.modelo.factory;

import co.edu.uniquindio.alojamiento.modelo.Servicio;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@Builder
public class Casa implements TipoAlojamientos {
    private String idAlojamiento;
    private String nombre;
    private String ciudad;
    private String descripcion;
    private String imagenUrl;
    private float precioPorNoche;
    private int capacidad;

    // Hacer el constructor público para poder acceder desde otros paquetes
    public Casa(String idAlojamiento, String nombre, String ciudad, String descripcion,
                String imagenUrl, float precioPorNoche, int capacidad) {
        this.idAlojamiento = idAlojamiento;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.descripcion = descripcion;
        this.imagenUrl = imagenUrl;
        this.precioPorNoche = precioPorNoche;
        this.capacidad = capacidad;
    }

    @Override
    public List<Servicio> listarServicios() {
        return Arrays.asList(
                Servicio.builder().idServicio("C001").nombre("Wi-Fi").descripcion("Internet de alta velocidad").costo(0.0f).build(),
                Servicio.builder().idServicio("C002").nombre("Aseo y Mantenimiento").descripcion("Servicio de limpieza semanal").costo(15.0f).build(),
                Servicio.builder().idServicio("C003").nombre("Parqueadero").descripcion("Espacio para estacionar vehículos").costo(0.0f).build(),
                Servicio.builder().idServicio("C004").nombre("Zona BBQ").descripcion("Zona para asados").costo(5.0f).build()
        );
    }

    @Override
    public String generarFactura() {
        return "Factura generada para la Casa: " + nombre + " en la ciudad de " + ciudad;
    }
}


