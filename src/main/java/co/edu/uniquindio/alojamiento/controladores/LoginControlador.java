package co.edu.uniquindio.alojamiento.controladores;

import co.edu.uniquindio.alojamiento.modelo.Cliente;
import co.edu.uniquindio.alojamiento.modelo.Sesion;
import co.edu.uniquindio.alojamiento.servicio.ServiciosAlojamientos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

public class LoginControlador {

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Hyperlink recuperarContrasenaLink;



    @FXML
    public void login(ActionEvent event) {
        try {
            String email = txtCorreo.getText();
            String contrasena = txtPassword.getText();

            // Verificación directa para el administrador
            if (email.equals("admin") && contrasena.equals("admin")) {
                mostrarAlerta("Inicio de sesión como Administrador exitoso.", "Bienvenido", Alert.AlertType.INFORMATION);
                ControladorPrincipal.getInstancia().navegarVentana("/panelAdmin.fxml", "Panel del Administrador");
                return;
            }

            // Validación del cliente si no es el admin
            Cliente cliente = ControladorPrincipal.getInstancia().loginCliente(email, contrasena);

            if (cliente != null) {
                // Guardamos la sesión del cliente
                Sesion sesion = Sesion.getInstancia();
                sesion.setCliente(cliente);

                // Mostrar mensaje de éxito y proceder con la navegación
                mostrarAlerta("Inicio de sesión exitoso.", "Bienvenido", Alert.AlertType.INFORMATION);

                // Navegar hacia el panel de cliente
                ControladorPrincipal.getInstancia().navegarVentana("/panelCliente.fxml", "Panel del Usuario");

            } else {
                mostrarAlerta("Correo o contraseña incorrectos.", "Error de Login", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            mostrarAlerta("Ocurrió un error durante el inicio de sesión: " + e.getMessage(), "Error", Alert.AlertType.ERROR);
        }
    }

    /**
     * Muestra una alerta en pantalla
     * @param mensaje Mensaje a mostrar
     * @param titulo Título de la alerta
     * @param tipo Tipo de alerta (información, error, advertencia, etc.)
     */
    private void mostrarAlerta(String mensaje, String titulo, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Abre la ventana para recuperar la contraseña.
     * Aquí puedes cargar un nuevo FXML para la ventana de recuperación de contraseña.
     */
    @FXML
    public void abrirRecuperarContrasena(ActionEvent event) {
        try {
            // Cargar el archivo FXML para la recuperación de la contraseña
            Stage stage = (Stage) recuperarContrasenaLink.getScene().getWindow();
            ControladorPrincipal.getInstancia().navegarVentana("/recuperarContrasena.fxml", "Recuperar Contraseña");
        } catch (Exception e) {
            mostrarAlerta("Error al abrir la ventana de recuperación de contraseña", "Error", Alert.AlertType.ERROR);
        }
    }
}


