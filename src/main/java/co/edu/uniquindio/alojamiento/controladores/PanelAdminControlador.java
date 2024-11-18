package co.edu.uniquindio.alojamiento.controladores;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;

public class PanelAdminControlador {

    @FXML
    private StackPane contentArea;

    private Button btnEliminarResena;

    // Método para gestionar alojamientos
    @FXML
    private void gestionarAlojamientos(ActionEvent event) {
        cargarContenido("/gestionarAlojamientos.fxml");
    }

    // Método para gestionar ofertas especiales
    @FXML
    private void ofertasEspeciales(ActionEvent event) {
        cargarContenido("/ofertasEspeciales.fxml");
    }

    // Método para mostrar estadísticas
    @FXML
    private void estadisticas(ActionEvent event) {
        cargarContenido("/estadisticas.fxml");
    }

    // Método para mostrar alojamientos populares
    @FXML
    private void alojamientosPopulares(ActionEvent event) {
        cargarContenido("/alojamientosPopulares.fxml");
    }
    @FXML
    public void eliminarResena(ActionEvent event) {
        cargarContenido("/eliminar_resena.fxml");
    }


    // Método para cerrar sesión
    //@FXML
    //private void cerrarSesion(ActionEvent event) {
        //ControladorPrincipal.getInstancia().cerrarSesion();
    //}

    // Método para cargar el contenido dinámico en contentArea
    private void cargarContenido(String fxmlPath) {
        try {
            ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
            AnchorPane newContent = (AnchorPane) controladorPrincipal.cargarPanel(fxmlPath);

            if (newContent != null) {
                contentArea.getChildren().setAll(newContent);
            } else {
                mostrarAlerta("Error", "No se pudo cargar el contenido.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Ocurrió un problema al cargar el contenido.");
        }
    }

    // Método auxiliar para mostrar alertas
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void cerrarSesion(ActionEvent event) {
        ControladorPrincipal controladorPrincipal= ControladorPrincipal.getInstancia();
        controladorPrincipal.cerrarVentana((Node) event.getSource());
    }



}






