package co.edu.uniquindio.alojamiento.controladores;

import co.edu.uniquindio.alojamiento.modelo.Alojamiento;
import co.edu.uniquindio.alojamiento.modelo.factory.TipoAlojamientos;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class BuscarAlojamientoControlador implements Initializable {

    @FXML
    private ComboBox<TipoAlojamientos> tipoAlojamiento;

    @FXML
    private ComboBox<String> ciudadAlojamiento;

    @FXML
    private TextField precioMinimo;

    @FXML
    private TextField precioMaximo;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private TableView<Alojamiento> tablaAlojamientos;

    @FXML
    private TableColumn<Alojamiento, String> columnaNombre;

    @FXML
    private TableColumn<Alojamiento, String> columnaTipo;

    @FXML
    private TableColumn<Alojamiento, String> columnaCiudad;

    @FXML
    private TableColumn<Alojamiento, String> columnaPrecio;

    private ControladorPrincipal controladorPrincipal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        columnaNombre.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getNombre()));
        columnaTipo.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getTipo().toString()));
        columnaCiudad.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getCiudad()));
        columnaPrecio.setCellValueFactory(celda -> new SimpleStringProperty(String.valueOf(celda.getValue().getPrecioPorNoche())));


        cargarCiudades();
        cargarTiposAlojamientos();


        configurarHabilitacionBotonBuscar();

        cargarAlojamientos();
    }

    private void configurarHabilitacionBotonBuscar() {
        btnBuscar.setDisable(true);


        tipoAlojamiento.valueProperty().addListener((observable, oldValue, newValue) -> actualizarHabilitacionBoton());
        ciudadAlojamiento.valueProperty().addListener((observable, oldValue, newValue) -> actualizarHabilitacionBoton());
        precioMinimo.textProperty().addListener((observable, oldValue, newValue) -> actualizarHabilitacionBoton());
        precioMaximo.textProperty().addListener((observable, oldValue, newValue) -> actualizarHabilitacionBoton());
    }

    private void actualizarHabilitacionBoton() {
        btnBuscar.setDisable(tipoAlojamiento.getValue() == null &&
                ciudadAlojamiento.getValue() == null &&
                precioMinimo.getText().isEmpty() &&
                precioMaximo.getText().isEmpty());
    }

    @FXML
    public void buscarAlojamientos(ActionEvent event) {
        try {

            TipoAlojamientos tipoSeleccionado = tipoAlojamiento.getValue();
            String ciudadSeleccionada = ciudadAlojamiento.getValue();
            Float precioMinimoValor = precioMinimo.getText().isEmpty() ? 0f : Float.parseFloat(precioMinimo.getText());
            Float precioMaximoValor = precioMaximo.getText().isEmpty() ? Float.MAX_VALUE : Float.parseFloat(precioMaximo.getText());

            // Realizar la búsqueda utilizando los filtros seleccionados
            List<Alojamiento> alojamientosEncontrados = ControladorPrincipal.getInstancia().buscarAlojamiento(
                    null,
                    tipoSeleccionado != null ? tipoSeleccionado.toString() : null,
                    ciudadSeleccionada,
                    precioMinimoValor,
                    precioMaximoValor
            );

            // Verificar si se encontraron alojamientos
            if (alojamientosEncontrados.isEmpty()) {
                mostrarAlerta("No se encontraron alojamientos que coincidan con los filtros.", Alert.AlertType.INFORMATION);
            } else {
                // Actualizar la tabla con los alojamientos encontrados
                tablaAlojamientos.getItems().setAll(alojamientosEncontrados);
                mostrarAlerta("Se han encontrado " + alojamientosEncontrados.size() + " alojamientos.", Alert.AlertType.INFORMATION);
            }

        } catch (Exception e) {
            mostrarAlerta("Ocurrió un error al realizar la búsqueda: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void limpiarFiltros() {
        tipoAlojamiento.setValue(null);
        ciudadAlojamiento.setValue(null);
        precioMinimo.clear();
        precioMaximo.clear();
        tablaAlojamientos.getItems().clear();
        btnBuscar.setDisable(true);
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }


    private void cargarCiudades() {
        try {
            List<String> ciudades = ControladorPrincipal.getInstancia().obtenerCiudadesUnicas();
            ciudadAlojamiento.setItems(FXCollections.observableArrayList(ciudades));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void cargarTiposAlojamientos() {
        try {
            List<TipoAlojamientos> tipos = ControladorPrincipal.getInstancia().obtenerTiposUnicos();
            tipoAlojamiento.setItems(FXCollections.observableArrayList(tipos));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @FXML
    private void cargarAlojamientos() {
        try {
            List<Alojamiento> alojamientos = ControladorPrincipal.getInstancia().buscarAlojamiento(null, null, null, 0, Float.MAX_VALUE);


            List<Alojamiento> alojamientosFiltrados = filtrarAlojamientosPorCiudad(alojamientos);


            // Actualizar la tabla con los alojamientos filtrados
            tablaAlojamientos.setItems(FXCollections.observableArrayList(alojamientosFiltrados));
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Ocurrió un error al cargar los alojamientos.", Alert.AlertType.ERROR);
        }
    }

    private List<Alojamiento> filtrarAlojamientosPorCiudad(List<Alojamiento> alojamientos) {

        Map<String, Alojamiento> ciudadAlojamientoMap = new HashMap<>();

        for (Alojamiento alojamiento : alojamientos) {
            String ciudad = alojamiento.getCiudad();

            if (!ciudadAlojamientoMap.containsKey(ciudad)) {
                ciudadAlojamientoMap.put(ciudad, alojamiento);
            }
        }


        return new ArrayList<>(ciudadAlojamientoMap.values());
    }

    private List<Alojamiento> filtrarAlojamientosPorTipo(List<Alojamiento> alojamientos) {

        Map<String, Alojamiento> tipoAlojamientoMap = new HashMap<>();

        for (Alojamiento alojamiento : alojamientos) {
            String tipo = alojamiento.getTipo().toString();

            if (!tipoAlojamientoMap.containsKey(tipo)) {
                tipoAlojamientoMap.put(tipo, alojamiento);
            }
        }


        return new ArrayList<>(tipoAlojamientoMap.values());
    }



    @FXML
    public void reservarAlojamiento(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reservarAlojamiento.fxml"));


            AnchorPane root = loader.load();


            ReservarAlojamientoControlador controlador = loader.getController();


            Stage stage = new Stage();
            stage.setTitle("Reservar Alojamiento");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Ocurrió un error al abrir la ventana de reserva: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

}






