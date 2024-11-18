package co.edu.uniquindio.alojamiento.controladores;

import co.edu.uniquindio.alojamiento.modelo.Alojamiento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.List;

public class OfertasEspecialesControlador {

    @FXML
    private TextField nombreOferta;

    @FXML
    private TextField descuentoOferta;

    @FXML
    private DatePicker fechaInicioOferta;

    @FXML
    private DatePicker fechaFinOferta;

    @FXML
    private ComboBox<String> comboBoxAlojamientos;  // Ahora es ComboBox<String>

    // Método para inicializar y cargar solo los nombres de los alojamientos en el ComboBox
    public void initialize() {
        try {
            // Llamada al servicio para obtener todos los alojamientos, sin filtros
            List<Alojamiento> alojamientos = ControladorPrincipal.getInstancia().obtenerTodosLosAlojamientos();

            // Convertir la lista de alojamientos en una lista de nombres (String)
            ObservableList<String> nombresAlojamientos = FXCollections.observableArrayList();
            for (Alojamiento alojamiento : alojamientos) {
                nombresAlojamientos.add(alojamiento.getNombre());  // Solo el nombre
            }

            // Asignamos los nombres al ComboBox
            comboBoxAlojamientos.setItems(nombresAlojamientos);
        } catch (Exception e) {
            mostrarAlerta("Error al cargar alojamientos: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Método para guardar la oferta
    @FXML
    public void guardarOferta(ActionEvent event) {
        try {
            // Obtener los valores de los campos del formulario
            String nombre = nombreOferta.getText();
            float descuento = Float.parseFloat(descuentoOferta.getText());
            LocalDate fechaInicio = fechaInicioOferta.getValue();
            LocalDate fechaFin = fechaFinOferta.getValue();

            // Validar los campos
            if (nombre.isEmpty() || descuento <= 0 || fechaInicio == null || fechaFin == null) {
                mostrarAlerta("Por favor, complete todos los campos correctamente.", Alert.AlertType.WARNING);
                return;
            }

            // Obtener el nombre del alojamiento seleccionado
            String nombreAlojamientoSeleccionado = comboBoxAlojamientos.getValue();
            if (nombreAlojamientoSeleccionado == null) {
                mostrarAlerta("Por favor, seleccione un alojamiento.", Alert.AlertType.WARNING);
                return;
            }

            // Obtener el alojamiento completo a partir del nombre
            Alojamiento alojamientoSeleccionado = buscarAlojamientoPorNombre(nombreAlojamientoSeleccionado);
            if (alojamientoSeleccionado == null) {
                mostrarAlerta("No se encontró el alojamiento seleccionado.", Alert.AlertType.ERROR);
                return;
            }

            // Llamar al método para crear la oferta
            ControladorPrincipal.getInstancia().crearOferta(alojamientoSeleccionado.getIdAlojamiento(), descuento, fechaInicio, fechaFin);

            // Mostrar mensaje de éxito
            mostrarAlerta("La oferta se ha guardado correctamente.", Alert.AlertType.INFORMATION);
            limpiarCampos();  // Limpiar los campos después de guardar la oferta

        } catch (NumberFormatException e) {
            mostrarAlerta("El descuento debe ser un número válido.", Alert.AlertType.WARNING);
        } catch (Exception e) {
            mostrarAlerta("Ocurrió un error al guardar la oferta: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Método para eliminar una oferta (si es necesario en el futuro)
    @FXML
    public void eliminarOferta(ActionEvent event) {
        // Aquí podrías agregar la lógica para eliminar una oferta
        mostrarAlerta("Funcionalidad de eliminar oferta no implementada aún.", Alert.AlertType.INFORMATION);
    }

    // Método para mostrar alertas
    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    // Limpiar los campos del formulario
    private void limpiarCampos() {
        nombreOferta.clear();
        descuentoOferta.clear();
        fechaInicioOferta.setValue(null);
        fechaFinOferta.setValue(null);
        comboBoxAlojamientos.setValue(null);  // Limpiar el ComboBox
    }

    // Buscar el alojamiento completo por su nombre
    private Alojamiento buscarAlojamientoPorNombre(String nombre) {
        try {
            List<Alojamiento> alojamientos = ControladorPrincipal.getInstancia().obtenerTodosLosAlojamientos();
            for (Alojamiento alojamiento : alojamientos) {
                if (alojamiento.getNombre().equals(nombre)) {
                    return alojamiento;
                }
            }
        } catch (Exception e) {
            mostrarAlerta("Error al buscar alojamiento por nombre: " + e.getMessage(), Alert.AlertType.ERROR);
        }
        return null;
    }
}




