package co.edu.uniquindio.alojamiento.modelo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class Factura {
    private UUID idFactura;
    private Reserva reserva;
    private LocalDate fechaEmision;
    private float subtotal;
    private float total;
}
