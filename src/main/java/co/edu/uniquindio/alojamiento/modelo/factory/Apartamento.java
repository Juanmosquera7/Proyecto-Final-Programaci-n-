package co.edu.uniquindio.alojamiento.modelo.factory;

import co.edu.uniquindio.alojamiento.modelo.Servicio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Apartamento implements TipoAlojamientos {

    private String id;
    private float precioPorNoche;
    private List<String> servicios;
    private float costoAseo;

    public Apartamento(String id, float precioPorNoche, List<String> servicios, float costoAseo) {
        this.id = id;
        this.precioPorNoche = precioPorNoche;
        this.servicios = servicios;
        this.costoAseo = costoAseo;
    }

    @Override
    public String generarFactura() {
        return "Factura de Apartamento ID: " + id + "\nTotal: $" + (precioPorNoche + costoAseo);
    }

    @Override
    public List<Servicio> listarServicios() {
        return Arrays.asList(
                Servicio.builder().idServicio("A001").nombre("Wi-Fi").descripcion("Internet de alta velocidad").costo(0.0f).build(),
                Servicio.builder().idServicio("A002").nombre("Lavandería").descripcion("Lavadora y secadora").costo(5.0f).build(),
                Servicio.builder().idServicio("A003").nombre("Cocina equipada").descripcion("Cocina con utensilios y electrodomésticos").costo(0.0f).build(),
                Servicio.builder().idServicio("A004").nombre("Televisión por cable").descripcion("TV con canales nacionales e internacionales").costo(0.0f).build()
        );
    }
}

