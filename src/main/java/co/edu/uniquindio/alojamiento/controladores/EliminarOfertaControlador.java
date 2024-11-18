package co.edu.uniquindio.alojamiento.controladores;

import co.edu.uniquindio.alojamiento.modelo.Oferta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.List;

public class EliminarOfertaControlador {

    @FXML
    private ComboBox<Oferta> comboBoxOfertas; // ComboBox que muestra las ofertas disponibles

    @FXML
    private Label mensaje; // Label para mensajes al usuario

    /**
     * Método llamado al inicializar la interfaz gráfica.
     */
    @FXML
    public void initialize() {
        cargarOfertas(); // Llenamos el ComboBox al cargar la ventana
    }

    /**
     * Carga las ofertas disponibles desde el `ControladorPrincipal` y las muestra en el ComboBox.
     */
    private void cargarOfertas() {
        try {
            List<Oferta> ofertas = ControladorPrincipal.getInstancia().listarOfertasEspeciales(); // Llama a listarOfertasEspeciales()
            if (ofertas.isEmpty()) {
                mensaje.setText("No hay ofertas disponibles para eliminar.");
            } else {
                comboBoxOfertas.getItems().setAll(ofertas); // Llenamos el ComboBox
            }
        } catch (Exception e) {
            mensaje.setText("Error al cargar las ofertas: " + e.getMessage());
        }
    }

    /**
     * Elimina la oferta seleccionada en el ComboBox.
     */
    @FXML
    public void eliminarOferta(ActionEvent event) {
        Oferta ofertaSeleccionada = comboBoxOfertas.getSelectionModel().getSelectedItem();

        if (ofertaSeleccionada == null) {
            mensaje.setText("Debe seleccionar una oferta para eliminar.");
            return;
        }

        try {
            // Llama al método eliminarOferta() desde ControladorPrincipal
            ControladorPrincipal.getInstancia().eliminarOferta(ofertaSeleccionada.getIdOferta());
            mensaje.setStyle("-fx-text-fill: green;");
            mensaje.setText("Oferta eliminada exitosamente.");

            // Recargar las ofertas disponibles
            cargarOfertas();
        } catch (Exception e) {
            mensaje.setStyle("-fx-text-fill: red;");
            mensaje.setText("Error al eliminar la oferta: " + e.getMessage());
        }
    }
}

