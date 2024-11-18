package co.edu.uniquindio.alojamiento.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class InicioControlador {


    private final ControladorPrincipal controladorPrincipal;
    @FXML
    public StackPane panelPrincipal;

    public InicioControlador() {
        this.controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    public void irIniciarSesion(ActionEvent actionEvent) {
        Parent node = controladorPrincipal.cargarPanel("/login.fxml");
        panelPrincipal.getChildren().setAll(node);
    }

    public void irRegistroCliente(ActionEvent actionEvent) {
        Parent node = controladorPrincipal.cargarPanel("/registroCliente.fxml");
        panelPrincipal.getChildren().setAll(node);
    }

    public void verAlojamientos(ActionEvent actionEvent) {
        Parent node = controladorPrincipal.cargarPanel("/alojamientos.fxml");
        panelPrincipal.getChildren().setAll(node);
    }

    public void mostrarOfertas(ActionEvent actionEvent) {
        Parent node = controladorPrincipal.cargarPanel("/ofertas.fxml");
        panelPrincipal.getChildren().setAll(node);
    }

    public void cargarResenas(ActionEvent event) {
        Parent node = controladorPrincipal.cargarPanel("/resenas.fxml");
        panelPrincipal.getChildren().setAll(node);
    }
}


