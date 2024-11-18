package co.edu.uniquindio.alojamiento.controladores;

import co.edu.uniquindio.alojamiento.modelo.Resena;
import co.edu.uniquindio.alojamiento.modelo.Alojamiento;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.util.List;

public class EliminarResenaControlador {

    @FXML
    private ComboBox<Resena> comboBoxResenas;  // ComboBox para seleccionar la reseña
    @FXML
    private Text mensaje;  // Texto para mostrar mensajes de error o confirmación
    @FXML
    private Button btnEliminarResena;  // Botón para eliminar la reseña

    private List<Resena> listaResenas;  // Lista de reseñas

    @FXML
    public void initialize() {
        // Cargar las reseñas en el ComboBox al iniciar el controlador
        cargarReseñas();
    }

    /**
     * Método para cargar las reseñas en el ComboBox.
     */
    private void cargarReseñas() {
        try {
            // Obtener la lista de reseñas desde el servicio de ControladorPrincipal
            listaResenas = ControladorPrincipal.getInstancia().listarResenas();

            if (listaResenas.isEmpty()) {
                mensaje.setText("No hay reseñas disponibles para eliminar.");
            } else {
                // Llenar el ComboBox con las reseñas
                comboBoxResenas.getItems().clear();
                comboBoxResenas.getItems().addAll(listaResenas);
            }
        } catch (Exception e) {
            mensaje.setText("Error al cargar las reseñas: " + e.getMessage());
        }
    }

    /**
     * Método que se ejecuta al hacer clic en el botón de eliminar reseña.
     */
    @FXML
    public void eliminarResena(ActionEvent event) {
        // Obtener la reseña seleccionada
        Resena resenaSeleccionada = comboBoxResenas.getSelectionModel().getSelectedItem();

        if (resenaSeleccionada == null) {
            mensaje.setText("Debe seleccionar una reseña para eliminar.");
            return;
        }

        try {
            // Obtener el alojamiento asociado a la reseña
            Alojamiento alojamiento = resenaSeleccionada.getAlojamiento();

            // Llamar al método de eliminar reseña del controlador principal
            ControladorPrincipal.getInstancia().eliminarResena(resenaSeleccionada.getIdResena(), alojamiento);

            // Mostrar un mensaje de confirmación
            mostrarMensaje("Reseña eliminada exitosamente.");

            // Recargar las reseñas después de eliminar una
            cargarReseñas();
        } catch (Exception e) {
            // Mostrar un mensaje de error en caso de fallo
            mostrarMensaje("Error al eliminar la reseña: " + e.getMessage());
        }
    }

    /**
     * Método para mostrar mensajes de información (errores o confirmaciones).
     *
     * @param mensajeTexto El mensaje a mostrar.
     */
    private void mostrarMensaje(String mensajeTexto) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensajeTexto);
        alert.showAndWait();
    }
}


