package co.edu.uniquindio.alojamiento.modelo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Resena {
    private String idResena;
    private Cliente cliente;
    private Alojamiento alojamiento;
    private String comentario;
    private int valoracion; // escala de 1 a 5 estrellas
}
