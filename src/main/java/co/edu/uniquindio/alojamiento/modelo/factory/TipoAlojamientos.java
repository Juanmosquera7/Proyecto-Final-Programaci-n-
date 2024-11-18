package co.edu.uniquindio.alojamiento.modelo.factory;

import co.edu.uniquindio.alojamiento.modelo.Servicio;

import java.util.Arrays;
import java.util.List;

public interface TipoAlojamientos {
    String generarFactura();  // Método para generar la factura
    List<Servicio> listarServicios();  // Método para listar los servicios de cada alojamiento
    List<String> TIPOS_DISPONIBLES = Arrays.asList("Hotel", "Casa", "Apartamento");
}

