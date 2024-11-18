package co.edu.uniquindio.alojamiento.modelo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Habitacion {
    private String numero;
    private float precioPorNoche;
    private int capacidad;
    private String descripcion;
    private String imagenUrl;
}
