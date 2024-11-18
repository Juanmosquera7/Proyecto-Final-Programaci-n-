package co.edu.uniquindio.alojamiento.controladores;

import co.edu.uniquindio.alojamiento.modelo.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistroClienteControlador {

    @FXML
    private TextField campoCedula;
    @FXML
    private TextField campoNombre;
    @FXML
    private TextField campoTelefono;
    @FXML
    private TextField campoCorreo;
    @FXML
    private PasswordField campoContrasena;
    @FXML
    private Button btnRegistrar;

    // Usar el controlador principal a través del Singleton
    private ControladorPrincipal controladorPrincipal;

    // Constructor
    public RegistroClienteControlador() {
        // Inicializar el ControladorPrincipal utilizando el Singleton
        this.controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    @FXML
    public void registrarCliente(ActionEvent event) {
        try {
            // Recoger los datos del formulario
            String cedula = campoCedula.getText();
            String nombre = campoNombre.getText();
            String telefono = campoTelefono.getText();
            String correo = campoCorreo.getText();
            String contrasena = campoContrasena.getText();

            // Validación de los campos requeridos
            if (cedula.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                mostrarAlerta("Todos los campos son obligatorios.", Alert.AlertType.WARNING);
                return;
            }

            // Registrar el cliente usando el controlador principal
            controladorPrincipal.registrarCliente(cedula, nombre, telefono, correo, contrasena);

            // Mostrar mensaje de éxito
            mostrarAlerta("Cliente registrado exitosamente.", Alert.AlertType.INFORMATION);

            // Limpiar los campos del formulario
            limpiarCampos();
        } catch (Exception e) {
            mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Mostrar alertas con un mensaje específico
    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Limpiar los campos del formulario después de registrar al cliente
    private void limpiarCampos() {
        campoCedula.clear();
        campoNombre.clear();
        campoTelefono.clear();
        campoCorreo.clear();
        campoContrasena.clear();
    }
}


