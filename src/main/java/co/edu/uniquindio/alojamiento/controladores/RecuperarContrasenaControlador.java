package co.edu.uniquindio.alojamiento.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class RecuperarContrasenaControlador {

    @FXML
    private TextField txtCorreo;

    /**
     * Método que se ejecuta al hacer clic en el botón de recuperar contraseña.
     * @param event Evento del botón.
     */
    @FXML
    public void recuperarContrasena(ActionEvent event) {
        String correo = txtCorreo.getText().trim();

        if (correo.isEmpty()) {
            mostrarAlerta("Por favor, ingresa tu correo electrónico.", "Error", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Usamos el ControladorPrincipal para manejar la lógica de recuperación
            ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
            controladorPrincipal.solicitarCambioContrasena(correo);

            mostrarAlerta("Se ha enviado un correo de recuperación a " + correo + ".", "Éxito", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            mostrarAlerta("Error al intentar recuperar la contraseña: " + e.getMessage(), "Error", Alert.AlertType.ERROR);
        }
    }

    /**
     * Método para mostrar una alerta en la interfaz.
     * @param mensaje Mensaje de la alerta.
     * @param titulo Título de la alerta.
     * @param tipo Tipo de alerta.
     */
    private void mostrarAlerta(String mensaje, String titulo, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}


