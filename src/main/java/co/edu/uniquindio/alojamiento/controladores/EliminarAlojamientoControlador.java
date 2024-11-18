package co.edu.uniquindio.alojamiento.controladores;

import co.edu.uniquindio.alojamiento.modelo.Alojamiento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.List;

public class EliminarAlojamientoControlador {

    @FXML
    private ComboBox<Alojamiento> cmbAlojamientos;  // ComboBox para alojamientos

    @FXML
    private Button btnEliminar;  // Botón para eliminar el alojamiento

    private ControladorPrincipal controladorPrincipal;

    public EliminarAlojamientoControlador() {
        // Obtener la instancia del controlador principal utilizando el patrón Singleton
        this.controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    // Método para inicializar el ComboBox con los alojamientos
    @FXML
    public void initialize() {
        try {
            // Obtener la lista de alojamientos a través de la instancia del controlador principal
            List<Alojamiento> alojamientos = controladorPrincipal.obtenerTodosLosAlojamientos();
            ObservableList<Alojamiento> alojamientoObservableList = FXCollections.observableArrayList(alojamientos);
            cmbAlojamientos.setItems(alojamientoObservableList);
        } catch (Exception e) {
            mostrarError("Error al cargar los alojamientos", e.getMessage());
        }
    }

    // Método para manejar la acción de eliminar alojamiento
    @FXML
    public void eliminarAlojamiento(ActionEvent event) {
        Alojamiento alojamientoSeleccionado = cmbAlojamientos.getSelectionModel().getSelectedItem();

        if (alojamientoSeleccionado != null) {
            try {
                // Llamada al método eliminarAlojamiento del controlador principal
                controladorPrincipal.eliminarAlojamiento(alojamientoSeleccionado.getIdAlojamiento());
                mostrarMensaje("Alojamiento eliminado correctamente");
                // Volver a cargar los alojamientos después de la eliminación
                initialize();
            } catch (Exception e) {
                mostrarError("Error al eliminar el alojamiento", e.getMessage());
            }
        } else {
            mostrarError("Seleccionar un alojamiento", "Debe seleccionar un alojamiento para eliminar.");
        }
    }

    // Método para mostrar un mensaje de error
    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método para mostrar un mensaje informativo
    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

