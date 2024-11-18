package co.edu.uniquindio.alojamiento.modelo;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class Oferta {
    private String idOferta;
    private Alojamiento alojamiento;
    private float descuento; // porcentaje de descuento
    private LocalDate fechaInicio;
    private LocalDate fechaFin;


}
