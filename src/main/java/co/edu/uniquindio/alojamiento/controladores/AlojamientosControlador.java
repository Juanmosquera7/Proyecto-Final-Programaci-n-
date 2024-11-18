package co.edu.uniquindio.alojamiento.controladores;

import co.edu.uniquindio.alojamiento.modelo.Alojamiento;
import co.edu.uniquindio.alojamiento.servicio.ServiciosAlojamientos;
import co.edu.uniquindio.alojamiento.modelo.AlojamientoPrincipal;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class AlojamientosControlador implements Initializable {

    @FXML
    private TableView<Alojamiento> tablaAlojamientos;

    @FXML
    private TableColumn<Alojamiento, String> colNombre;

    @FXML
    private TableColumn<Alojamiento, String> colCiudad;

    @FXML
    private TableColumn<Alojamiento, String> colPrecio;

    @FXML
    private TableColumn<Alojamiento, String> colCapacidad;

    @FXML
    private TableColumn<Alojamiento, String> colTipo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colNombre.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getNombre()));
        colCiudad.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getCiudad()));
        colPrecio.setCellValueFactory(celda -> new SimpleStringProperty(String.valueOf(celda.getValue().getPrecioPorNoche())));
        colCapacidad.setCellValueFactory(celda -> new SimpleStringProperty(String.valueOf(celda.getValue().getCapacidadMaxima())));
        colTipo.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getTipo().toString()));


        cargarAlojamientos();
    }

    private void cargarAlojamientos() {
        try {

            var alojamientos = ControladorPrincipal.getInstancia().buscarAlojamiento(null, null, null, 0, Float.MAX_VALUE);
            tablaAlojamientos.setItems(FXCollections.observableArrayList(alojamientos));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



