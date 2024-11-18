package co.edu.uniquindio.alojamiento.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import co.edu.uniquindio.alojamiento.modelo.Cliente;
import co.edu.uniquindio.alojamiento.modelo.Sesion;

public class EditarPerfilControlador {

    @FXML
    private TextField nombreUsuario;

    @FXML
    private TextField correoUsuario;

    @FXML
    private PasswordField contrasenaUsuario;

    @FXML
    private PasswordField nuevaContrasenaUsuario;

    @FXML
    private PasswordField confirmarContrasenaUsuario;

    private ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    /**
     * Método que guarda los cambios en el perfil del usuario.
     */
    @FXML
    public void guardarCambios(ActionEvent event) {
        try {
            // Obtener los datos del usuario actual de la sesión
            Cliente clienteActual = Sesion.getInstancia().getCliente();

            if (clienteActual == null) {
                mostrarAlerta(AlertType.ERROR, "Error de Sesión", "No se encontró un cliente activo en la sesión.");
                return;
            }

            // Validar los datos ingresados
            String nombre = nombreUsuario.getText().trim();
            String correo = correoUsuario.getText().trim();
            String contrasenaActual = contrasenaUsuario.getText().trim();
            String nuevaContrasena = nuevaContrasenaUsuario.getText().trim();
            String confirmarContrasena = confirmarContrasenaUsuario.getText().trim();

            if (nombre.isEmpty() || correo.isEmpty() || contrasenaActual.isEmpty()) {
                mostrarAlerta(AlertType.WARNING, "Campos Vacíos", "Por favor, complete todos los campos requeridos.");
                return;
            }

            // Verificar que la contraseña actual sea correcta
            if (!clienteActual.getContrasena().equals(contrasenaActual)) {
                mostrarAlerta(AlertType.ERROR, "Contraseña Incorrecta", "La contraseña actual ingresada es incorrecta.");
                return;
            }

            // Validar que las nuevas contraseñas coincidan, si se proporciona una nueva
            if (!nuevaContrasena.isEmpty() || !confirmarContrasena.isEmpty()) {
                if (!nuevaContrasena.equals(confirmarContrasena)) {
                    mostrarAlerta(AlertType.ERROR, "Contraseñas No Coinciden", "La nueva contraseña y su confirmación no coinciden.");
                    return;
                }
            }

            // Actualizar la información del cliente
            String contrasenaFinal = nuevaContrasena.isEmpty() ? contrasenaActual : nuevaContrasena;

            controladorPrincipal.actualizarCliente(
                    clienteActual.getCedula(),
                    nombre,
                    clienteActual.getTelefono(), // Se mantiene el teléfono original
                    correo,
                    contrasenaFinal
            );

            // Actualizar la información en la sesión
            clienteActual.setNombre(nombre);
            clienteActual.setEmail(correo);
            clienteActual.setContrasena(contrasenaFinal);

            mostrarAlerta(AlertType.INFORMATION, "Éxito", "Los cambios en su perfil se han guardado exitosamente.");

            // Limpiar los campos después de guardar los cambios
            limpiarCampos();

        } catch (Exception e) {
            mostrarAlerta(AlertType.ERROR, "Error al Guardar", "No se pudieron guardar los cambios: " + e.getMessage());
        }
    }

    /**
     * Método para limpiar los campos del formulario.
     */
    private void limpiarCampos() {
        nombreUsuario.clear();
        correoUsuario.clear();
        contrasenaUsuario.clear();
        nuevaContrasenaUsuario.clear();
        confirmarContrasenaUsuario.clear();
    }

    /**
     * Método para mostrar una alerta al usuario.
     *
     * @param tipo    Tipo de alerta (INFO, WARNING, ERROR).
     * @param titulo  Título de la alerta.
     * @param mensaje Mensaje de la alerta.
     */
    private void mostrarAlerta(AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

