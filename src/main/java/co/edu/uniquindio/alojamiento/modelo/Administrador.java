package co.edu.uniquindio.alojamiento.modelo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Administrador {
    private String nombreCompleto;
    private String email;
    private String contrasena;
}
