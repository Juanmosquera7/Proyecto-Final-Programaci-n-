package co.edu.uniquindio.alojamiento.modelo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Servicio {
    private String idServicio;
    private String nombre;
    private String descripcion;
    private float costo;

    public Servicio(String idServicio, String nombre, String descripcion, float costo) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo = costo;
    }
}

