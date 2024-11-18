package co.edu.uniquindio.alojamiento.controladores;

import co.edu.uniquindio.alojamiento.modelo.Oferta;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class OfertasControlador implements Initializable {

    @FXML
    private TableView<Oferta> tablaOfertas;

    @FXML
    private TableColumn<Oferta, String> colAlojamiento;

    @FXML
    private TableColumn<Oferta, String> colFechaInicio;

    @FXML
    private TableColumn<Oferta, String> colFechaFin;

    @FXML
    private TableColumn<Oferta, String> colDescuento;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarColumnas(); // Configura las columnas de la tabla
        cargarOfertas(); // Carga las ofertas especiales
    }

    // Configuramos las columnas de la tabla
    private void configurarColumnas() {
        // Alojamiento
        colAlojamiento.setCellValueFactory(celda ->
                new SimpleStringProperty(celda.getValue().getAlojamiento().getNombre()));

        // Fecha de Inicio (formateada)
        colFechaInicio.setCellValueFactory(celda ->
                new SimpleStringProperty(celda.getValue().getFechaInicio().format(dateFormatter)));

        // Fecha de Fin (formateada)
        colFechaFin.setCellValueFactory(celda ->
                new SimpleStringProperty(celda.getValue().getFechaFin().format(dateFormatter)));

        // Descuento
        colDescuento.setCellValueFactory(celda ->
                new SimpleStringProperty(String.valueOf(celda.getValue().getDescuento())));
    }

    // Método para cargar las ofertas desde el controlador principal
    private void cargarOfertas() {
        try {
            // Llamar al método de listar ofertas especiales desde el controlador principal
            List<Oferta> ofertas = ControladorPrincipal.getInstancia().listarOfertasEspeciales();

            // Asignar las ofertas a la tabla
            tablaOfertas.setItems(FXCollections.observableArrayList(ofertas));
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir el error si ocurre algún problema
        }
    }
}







