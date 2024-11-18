package co.edu.uniquindio.alojamiento.controladores;

import co.edu.uniquindio.alojamiento.modelo.Alojamiento;
import co.edu.uniquindio.alojamiento.modelo.Cliente;
import co.edu.uniquindio.alojamiento.modelo.Resena;
import co.edu.uniquindio.alojamiento.modelo.Sesion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class CrearResenaControlador implements Initializable {

    @FXML
    private ComboBox<Alojamiento> comboAlojamientos;

    @FXML
    private TextArea txtComentario;

    @FXML
    private TextField txtValoracion;

    // Método para inicializar los datos en el ComboBox de alojamientos
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarAlojamientos();
    }

    // Cargar alojamientos en el ComboBox
    private void cargarAlojamientos() {
        try {
            List<Alojamiento> alojamientos = ControladorPrincipal.getInstancia().obtenerAlojamientos();
            ObservableList<Alojamiento> alojamientosObservable = FXCollections.observableArrayList(alojamientos);
            comboAlojamientos.setItems(alojamientosObservable);
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudieron cargar los alojamientos.");
        }
    }

    // Método para guardar la reseña
    @FXML
    public void manejarGuardarResena(ActionEvent event) {
        try {
            Alojamiento alojamientoSeleccionado = comboAlojamientos.getValue();
            String comentario = txtComentario.getText().trim();
            String valoracionTexto = txtValoracion.getText().trim();

            // Validar datos
            if (alojamientoSeleccionado == null) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Debes seleccionar un alojamiento.");
                return;
            }
            if (comentario.isEmpty()) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "El comentario no puede estar vacío.");
                return;
            }
            if (valoracionTexto.isEmpty() || Integer.parseInt(valoracionTexto) < 1 || Integer.parseInt(valoracionTexto) > 5) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "La valoración debe ser un número entre 1 y 5.");
                return;
            }

            int valoracion = Integer.parseInt(valoracionTexto);

            // Obtener el cliente desde la sesión
            Cliente cliente = Sesion.getInstancia().getCliente();

            if (cliente == null) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Debes estar logueado para hacer una reseña.");
                return;
            }

            // Llamamos al método agregarResena para agregar la reseña
            ControladorPrincipal.getInstancia().agregarResena(cliente, alojamientoSeleccionado, comentario, valoracion);

            // Confirmar éxito
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Reseña guardada correctamente.");

            // Limpiar campos después de guardar
            comboAlojamientos.setValue(null);
            txtComentario.clear();
            txtValoracion.clear();

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "La valoración debe ser un número válido.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo guardar la reseña: " + e.getMessage());
        }
    }

    // Método para cancelar la creación de la reseña
    @FXML
    public void manejarCancelar(ActionEvent event) {
        // Limpiar campos y cerrar el formulario o retornar a la pantalla anterior
        comboAlojamientos.setValue(null);
        txtComentario.clear();
        txtValoracion.clear();
    }

    // Método para mostrar alertas
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}


