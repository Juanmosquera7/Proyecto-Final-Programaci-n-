package co.edu.uniquindio.alojamiento.controladores;

import co.edu.uniquindio.alojamiento.modelo.Sesion;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.layout.StackPane;

public class PanelClienteControlador {

    private final ControladorPrincipal controladorPrincipal;

    @FXML
    private Button btnBuscarAlojamiento;

    @FXML
    private Button btnMisReservas;

    @FXML
    private Button btnRecargarBilletera;

    @FXML
    private Button btnEditarPerfil;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Button btnAnadirResena;

    public PanelClienteControlador() {
        this.controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    @FXML
    public StackPane contentArea;

    @FXML
    private void buscarAlojamiento(ActionEvent event) {
        Parent node = controladorPrincipal.cargarPanel("/buscarAlojamiento.fxml");
        contentArea.getChildren().setAll(node);
    }

    @FXML
    private void verMisReservas(ActionEvent event) {
        Parent node = controladorPrincipal.cargarPanel("/misReservas.fxml");
        contentArea.getChildren().setAll(node);
    }

    @FXML
    private void recargarBilletera(ActionEvent event) {
        Parent node = controladorPrincipal.cargarPanel("/recargarBilletera.fxml");
        contentArea.getChildren().setAll(node);
    }

    @FXML
    private void editarPerfil(ActionEvent event) {
        Parent node = controladorPrincipal.cargarPanel("/editarPerfil.fxml");
        contentArea.getChildren().setAll(node);
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        // Llamar al método cerrarSesion del Singleton Sesion
        Sesion.getInstancia().cerrarSesion();

        // Cerrar la ventana actual utilizando el nodo fuente del evento
        controladorPrincipal.cerrarVentana((Node) event.getSource());


    }

    @FXML
    public void AnadirResena(ActionEvent event) {
        Parent node = controladorPrincipal.cargarPanel("/crearResena.fxml");
        contentArea.getChildren().setAll(node);
    }
}

