package co.edu.uniquindio.alojamiento.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class GestionarAlojamientosControlador {

    @FXML
    private StackPane contentArea;

    // Método para mostrar el formulario de Crear Alojamiento
    @FXML
    public void crearAlojamiento(ActionEvent event) {
        try {
            // Usamos el Singleton de ControladorPrincipal
            ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

            // Cargar el FXML de CrearAlojamiento
            AnchorPane newContent = (AnchorPane) controladorPrincipal.cargarPanel("/crearAlojamiento.fxml");

            // Reemplazar el contenido actual con el nuevo FXML
            if (newContent != null) {
                contentArea.getChildren().setAll(newContent);
            } else {
                controladorPrincipal.mostrarAlerta("Error", "No se pudo cargar el formulario.", javafx.scene.control.Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
            ControladorPrincipal.getInstancia().mostrarAlerta("Error", "No se pudo cargar el formulario de crear alojamiento.", javafx.scene.control.Alert.AlertType.ERROR);
        }
    }

    // Método para mostrar el formulario de Eliminar Alojamiento
    @FXML
    public void eliminarAlojamiento(ActionEvent event) {
        try {
            // Usamos el Singleton de ControladorPrincipal
            ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

            // Cargar el FXML de EliminarAlojamiento
            AnchorPane newContent = (AnchorPane) controladorPrincipal.cargarPanel("/eliminarAlojamiento.fxml");

            // Reemplazar el contenido actual con el nuevo FXML
            if (newContent != null) {
                contentArea.getChildren().setAll(newContent);
            } else {
                controladorPrincipal.mostrarAlerta("Error", "No se pudo cargar el formulario.", javafx.scene.control.Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
            ControladorPrincipal.getInstancia().mostrarAlerta("Error", "No se pudo cargar el formulario de eliminar alojamiento.", javafx.scene.control.Alert.AlertType.ERROR);
        }
    }
}





