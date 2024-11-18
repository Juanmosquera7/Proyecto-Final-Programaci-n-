package co.edu.uniquindio.alojamiento.controladores;

import co.edu.uniquindio.alojamiento.modelo.Alojamiento;
import co.edu.uniquindio.alojamiento.modelo.Cliente;
import co.edu.uniquindio.alojamiento.modelo.Reserva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import java.net.URL;

import java.time.LocalDate;
import java.util.*;

public class ReservarAlojamientoControlador implements Initializable {

    @FXML
    private TextField clienteCedulaField;

    @FXML
    private ComboBox<String> ciudadComboBox;

    @FXML
    private TextField numHuespedesField;

    @FXML
    private ComboBox<Alojamiento> alojamientoComboBox;

    @FXML
    private DatePicker fechaInicioPicker;

    @FXML
    private DatePicker fechaFinPicker;

    @FXML
    private Spinner<Integer> numHuespedesSpinner;

    // Método para cargar las ciudades en el ComboBox de ciudades.
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        // Configurar los ComboBox de ciudad y alojamiento
        cargarCiudades();
        cargarAlojamientos();

        // Agregar un listener para cargar los alojamientos cuando se selecciona una ciudad
        ciudadComboBox.valueProperty().addListener((observable, oldValue, newValue) -> cargarAlojamientos());
    }

    private void cargarCiudades() {
        try {
            // Obtener los alojamientos desde el controlador principal
            var alojamientos = ControladorPrincipal.getInstancia().buscarAlojamiento(null, null, null, 0, Float.MAX_VALUE);

            // Extraer las ciudades únicas
            Set<String> ciudadesUnicas = new HashSet<>();
            for (Alojamiento alojamiento : alojamientos) {
                ciudadesUnicas.add(alojamiento.getCiudad());
            }

            // Cargar las ciudades en el ComboBox
            ObservableList<String> ciudadesObservable = FXCollections.observableArrayList(ciudadesUnicas);
            ciudadComboBox.setItems(ciudadesObservable);

        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudieron cargar las ciudades: " + e.getMessage());
        }
    }

    private void cargarAlojamientos() {
        try {
            // Obtener la ciudad seleccionada
            String ciudadSeleccionada = ciudadComboBox.getValue();

            if (ciudadSeleccionada != null) {
                // Obtener los alojamientos de la ciudad seleccionada
                var alojamientos = ControladorPrincipal.getInstancia().buscarAlojamientoPorCiudad(ciudadSeleccionada);

                // Cargar los alojamientos en el ComboBox
                ObservableList<Alojamiento> alojamientosObservable = FXCollections.observableArrayList(alojamientos);
                alojamientoComboBox.setItems(alojamientosObservable);
            }

        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudieron cargar los alojamientos: " + e.getMessage());
        }
    }


    /**
     * Método para realizar la reserva basado en los datos ingresados.
     */
    @FXML
    public void realizarReserva(ActionEvent event) {
        try {
            // Obtener la instancia de ControladorPrincipal
            ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

            // Validar y obtener los datos del formulario
            String cedulaCliente = clienteCedulaField.getText().trim();
            String ciudadSeleccionada = ciudadComboBox.getValue();
            Alojamiento alojamientoSeleccionado = alojamientoComboBox.getValue();
            LocalDate fechaInicio = fechaInicioPicker.getValue();
            LocalDate fechaFin = fechaFinPicker.getValue();

            // Obtener y validar el número de huéspedes
            String numHuespedesTexto = numHuespedesField.getText().trim();
            if (numHuespedesTexto.isEmpty() || Integer.parseInt(numHuespedesTexto) <= 0) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "El número de huéspedes debe ser mayor que cero.");
                return;
            }
            int numHuespedes = Integer.parseInt(numHuespedesTexto);

            // Validaciones básicas
            if (cedulaCliente.isEmpty() || ciudadSeleccionada == null || alojamientoSeleccionado == null || fechaInicio == null || fechaFin == null) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Todos los campos son obligatorios.");
                return;
            }

            if (!fechaInicio.isBefore(fechaFin)) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "La fecha de inicio debe ser anterior a la fecha de fin.");
                return;
            }

            // Obtener cliente desde el controlador principal
            Cliente cliente = controladorPrincipal.obtenerClientePorCedula(cedulaCliente);
            if (cliente == null) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Cliente no encontrado.");
                return;
            }
            boolean reservaExistente = controladorPrincipal.existeReserva(cliente, alojamientoSeleccionado, fechaInicio, fechaFin);
            if (reservaExistente) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ya existe una reserva para este alojamiento en las fechas seleccionadas.");
                return;
            }

            // Intentar realizar la reserva
            Reserva reserva = controladorPrincipal.reservarAlojamiento(alojamientoSeleccionado, cliente, fechaInicio, fechaFin, numHuespedes);

            // Mostrar éxito si no hay excepciones
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Reserva realizada con éxito.\nID de reserva: " + reserva.getIdReserva());

        } catch (NullPointerException e) {
            // Si alguna variable es null inesperadamente, capturamos y mostramos el error.
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Hubo un problema con los datos ingresados: " + e.getMessage());
        } catch (NumberFormatException e) {
            // Si hay un error al intentar convertir algún dato numérico.
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "El número de huéspedes no es válido: " + e.getMessage());
        } catch (Exception e) {
            // Capturamos cualquier otra excepción general.
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo realizar la reserva: " + (e.getMessage() != null ? e.getMessage() : "Error desconocido"));
        }
    }


    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}





