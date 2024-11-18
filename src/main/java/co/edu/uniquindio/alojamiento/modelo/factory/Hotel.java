package co.edu.uniquindio.alojamiento.modelo.factory;

import co.edu.uniquindio.alojamiento.modelo.Habitacion;
import co.edu.uniquindio.alojamiento.modelo.Servicio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hotel implements TipoAlojamientos {

    private String id;
    private float precioPorNoche;
    private List<String> servicios;
    private List<Habitacion> habitaciones;

    public Hotel(String id, float precioPorNoche, List<String> servicios, List<Habitacion> habitaciones) {
        this.id = id;
        this.precioPorNoche = precioPorNoche;
        this.servicios = servicios;
        this.habitaciones = habitaciones;
    }

    @Override
    public String generarFactura() {
        return "Factura de Hotel ID: " + id + "\nTotal: $" + precioPorNoche;
    }

    @Override
    public List<Servicio> listarServicios() {
        return Arrays.asList(
                Servicio.builder().idServicio("H001").nombre("Wi-Fi").descripcion("Internet de alta velocidad").costo(0.0f).build(),
                Servicio.builder().idServicio("H002").nombre("Desayuno incluido").descripcion("Desayuno buffet").costo(0.0f).build(),
                Servicio.builder().idServicio("H003").nombre("Piscina").descripcion("Piscina al aire libre").costo(10.0f).build(),
                Servicio.builder().idServicio("H004").nombre("Gimnasio").descripcion("Acceso al gimnasio").costo(5.0f).build()
        );
    }
}

