package co.edu.uniquindio.alojamiento.modelo;

import lombok.Getter;
import lombok.Setter;


public class Sesion {


    public static Sesion INSTANCIA;

    @Getter @Setter
    private Cliente cliente;


    private Sesion() {
    }


    public static Sesion getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new Sesion();
        }
        return INSTANCIA;
    }


    public void cerrarSesion() {
        cliente = null;
    }


}

