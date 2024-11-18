package co.edu.uniquindio.alojamiento.controladores;

import co.edu.uniquindio.alojamiento.modelo.Cliente;
import co.edu.uniquindio.alojamiento.modelo.Reserva;
import co.edu.uniquindio.alojamiento.modelo.Alojamiento;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MisReservasControlador implements Initializable {

    @FXML
    private TableView<Reserva> tablaReservas;

    @FXML
    private TableColumn<Reserva, String> colIdReserva;

    @FXML
    private TableColumn<Reserva, String> colNombreCliente;

    @FXML
    private TableColumn<Reserva, String> colAlojamiento;

    @FXML
    private TableColumn<Reserva, String> colFechaInicio;

    @FXML
    private TableColumn<Reserva, String> colFechaFin;

    @FXML
    private TableColumn<Reserva, Integer> colNumHuespedes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Establecer las celdas para cada columna
        colIdReserva.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getIdReserva().toString()));
        colNombreCliente.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getCliente().getNombreCompleto()));
        colAlojamiento.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getAlojamiento().getNombre()));
        colFechaInicio.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getFechaInicio().toString()));
        colFechaFin.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getFechaFin().toString()));
        colNumHuespedes.setCellValueFactory(celda -> new SimpleIntegerProperty(celda.getValue().getNumHuespedes()).asObject());

        // Cargar las reservas
        cargarReservas();
    }

    private void cargarReservas() {
        try {
            // Obtener el cliente actual (puedes obtenerlo desde la sesión o contexto)
            Cliente cliente = ControladorPrincipal.getInstancia().obtenerClienteActual();

            // Obtener las reservas del cliente
            List<Reserva> reservas = ControladorPrincipal.getInstancia().listarReservasPorCliente(cliente.getCedula());

            // Cargar las reservas en la tabla
            tablaReservas.setItems(FXCollections.observableArrayList(reservas));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


