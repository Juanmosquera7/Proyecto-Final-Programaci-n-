package co.edu.uniquindio.alojamiento.modelo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class Reserva {

    private UUID idReserva;
    private Cliente cliente;
    private Alojamiento alojamiento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int numHuespedes;
    private Factura factura;
}
