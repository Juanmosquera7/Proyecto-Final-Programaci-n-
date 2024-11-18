package co.edu.uniquindio.alojamiento.controladores;

import co.edu.uniquindio.alojamiento.modelo.Resena;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ResenasControlador implements Initializable {

    @FXML
    private TableView<Resena> tablaResenas;

    @FXML
    private TableColumn<Resena, String> colAlojamiento;

    @FXML
    private TableColumn<Resena, String> colCiudad;

    @FXML
    private TableColumn<Resena, String> colComentario;

    @FXML
    private TableColumn<Resena, Integer> colValoracion;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Establecer las celdas para cada columna
        colAlojamiento.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getAlojamiento().getNombre()));
        colCiudad.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getAlojamiento().getCiudad()));
        colComentario.setCellValueFactory(celda -> new SimpleStringProperty(celda.getValue().getComentario()));
        colValoracion.setCellValueFactory(celda -> new SimpleIntegerProperty(celda.getValue().getValoracion()).asObject());

        // Cargar las reseñas
        cargarResenas();
    }

    private void cargarResenas() {
        try {
            // Obtener las reseñas desde el controlador principal
            List<Resena> resenas = ControladorPrincipal.getInstancia().listarResenas();
            // Establecer los elementos en la tabla
            tablaResenas.setItems(FXCollections.observableArrayList(resenas));
        } catch (Exception e) {
            e.printStackTrace();
            // Mostrar alerta en caso de error al cargar las reseñas
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudieron cargar las reseñas");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}



