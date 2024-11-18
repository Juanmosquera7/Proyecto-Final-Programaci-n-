package co.edu.uniquindio.alojamiento.controladores;

import co.edu.uniquindio.alojamiento.modelo.Alojamiento;
import co.edu.uniquindio.alojamiento.modelo.Habitacion;
import co.edu.uniquindio.alojamiento.modelo.factory.Apartamento;
import co.edu.uniquindio.alojamiento.modelo.factory.Casa;
import co.edu.uniquindio.alojamiento.modelo.factory.Hotel;
import co.edu.uniquindio.alojamiento.modelo.factory.TipoAlojamientos;
import co.edu.uniquindio.alojamiento.controladores.ControladorPrincipal;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrearAlojamientoControlador {

    @FXML
    private TextField nombreAlojamiento;

    @FXML
    private TextField ciudadAlojamiento;

    @FXML
    private TextArea descripcionAlojamiento;

    @FXML
    private TextField imagenUrl;

    @FXML
    private TextField precioPorNoche;

    @FXML
    private Spinner<Integer> capacidadMaxima;

    @FXML
    private ComboBox<String> tipoAlojamiento;

    @FXML
    private CheckBox servicioPiscina, servicioWifi, servicioDesayuno, servicioGimnasio, servicioAireAcondicionado, servicioParqueadero;

    @FXML
    private Button btnGuardar, btnCancelar;

    // Método para inicializar el controlador y configurar elementos
    @FXML
    public void initialize() {
        capacidadMaxima.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1));
        tipoAlojamiento.getItems().addAll("Casa", "Apartamento", "Hotel");
    }

    // Método para manejar la acción del botón Guardar
    @FXML
    public void guardarAlojamiento() {
        try {
            String nombre = nombreAlojamiento.getText();
            String ciudad = ciudadAlojamiento.getText();
            String descripcion = descripcionAlojamiento.getText();
            String imagen = imagenUrl.getText();
            float precio = Float.parseFloat(precioPorNoche.getText());
            int capacidad = capacidadMaxima.getValue();
            String tipoSeleccionado = tipoAlojamiento.getValue();

            // Obtener lista de servicios seleccionados
            List<String> servicios = obtenerServiciosSeleccionados();

            // Validar tipo de alojamiento
            TipoAlojamientos tipo = obtenerTipoAlojamiento(tipoSeleccionado, nombre, ciudad, descripcion, imagen, precio, capacidad, servicios);


            // Agregar el alojamiento a la lista a través de ControladorPrincipal
            ControladorPrincipal.getInstancia().crearAlojamiento(
                    nombre, ciudad, descripcion, imagen, precio, capacidad, servicios, tipo, null
            );

            // Mostrar mensaje de éxito
            mostrarAlertaExito("Alojamiento creado", "El alojamiento se ha creado correctamente.");

            // Limpiar los campos después de crear el alojamiento para permitir crear otro
            limpiarCampos();

        } catch (Exception e) {
            mostrarAlertaError("Error al crear alojamiento", "Por favor, verifica los campos e inténtalo de nuevo.");
        }
    }

    // Método para obtener los servicios seleccionados
    private List<String> obtenerServiciosSeleccionados() {
        List<String> servicios = new ArrayList<>();
        if (servicioPiscina.isSelected()) servicios.add("Piscina");
        if (servicioWifi.isSelected()) servicios.add("WiFi");
        if (servicioDesayuno.isSelected()) servicios.add("Desayuno incluido");
        if (servicioGimnasio.isSelected()) servicios.add("Gimnasio");
        if (servicioAireAcondicionado.isSelected()) servicios.add("Aire acondicionado");
        if (servicioParqueadero.isSelected()) servicios.add("Parqueadero gratuito");
        return servicios;
    }

    // Método para convertir el tipo seleccionado en el objeto correspondiente
    private TipoAlojamientos obtenerTipoAlojamiento(String tipoSeleccionado, String nombre, String ciudad,
                                                    String descripcion, String imagen, float precio,
                                                    int capacidad, List<String> servicios) {
        switch (tipoSeleccionado) {
            case "Casa":
                return new Casa(
                        "C001",                          // idAlojamiento (puede ser generado dinámicamente)
                        nombre,                          // nombre
                        ciudad,                          // ciudad
                        descripcion,                     // descripcion
                        imagen,                          // imagenUrl
                        precio,                          // precioPorNoche
                        capacidad                        // capacidadMaxima
                );
            case "Apartamento":
                return new Apartamento(
                        "A001",                          // idAlojamiento
                        precio,                          // precioPorNoche
                        servicios,                       // servicios
                        20.0f                            // costoAseo (un valor fijo para este ejemplo)
                );
            case "Hotel":
                return new Hotel(
                        "H001",                          // idAlojamiento
                        precio,                          // precioPorNoche
                        servicios,                       // servicios
                        new ArrayList<>()                // habitaciones (vacío por ahora)
                );
            default:
                throw new IllegalArgumentException("Tipo de alojamiento no reconocido");
        }
    }

    // Método para limpiar los campos del formulario después de guardar el alojamiento
    private void limpiarCampos() {
        nombreAlojamiento.clear();
        ciudadAlojamiento.clear();
        descripcionAlojamiento.clear();
        imagenUrl.clear();
        precioPorNoche.clear();
        capacidadMaxima.getValueFactory().setValue(1);
        tipoAlojamiento.setValue(null);
        servicioPiscina.setSelected(false);
        servicioWifi.setSelected(false);
        servicioDesayuno.setSelected(false);
        servicioGimnasio.setSelected(false);
        servicioAireAcondicionado.setSelected(false);
        servicioParqueadero.setSelected(false);
    }

    // Método para mostrar una alerta de error
    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método para mostrar una alerta de éxito
    private void mostrarAlertaExito(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}



