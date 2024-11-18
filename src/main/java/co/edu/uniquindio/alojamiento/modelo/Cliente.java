package co.edu.uniquindio.alojamiento.modelo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Cliente {
    private String cedula;
    private String nombre;
    private String nombreCompleto;
    private String telefono;
    private String email;
    private String contrasena;
    private boolean cuentaActivada;
    private float saldoBilletera;
    private Billetera billetera;

    public static ClienteBuilder builder() {
        return new ClienteBuilder().billetera(new Billetera(0));  // Inicializa billetera con saldo 0
    }

    public void setCuentaActiva(boolean b) {

    }
}
