package co.edu.uniquindio.alojamiento.controladores;

import co.edu.uniquindio.alojamiento.modelo.Alojamiento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AlojamientosPopularesControlador implements Initializable {

    @FXML
    private BarChart<String, Number> graficoBarras;

    @FXML
    private CategoryAxis ejeX;

    @FXML
    private NumberAxis ejeY;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configurar etiquetas de los ejes
        ejeX.setLabel("Ciudad");
        ejeY.setLabel("Número de Reservas");

        // Cargar los alojamientos populares
        cargarAlojamientosPopulares();
    }

    private void cargarAlojamientosPopulares() {
        try {

            List<String> ciudades = ControladorPrincipal.getInstancia().obtenerCiudadesUnicas();


            ObservableList<XYChart.Series<String, Number>> seriesList = FXCollections.observableArrayList();


            for (String ciudad : ciudades) {

                List<Alojamiento> alojamientosPopulares = ControladorPrincipal.getInstancia().obtenerAlojamientosPopulares(ciudad);


                Map<Alojamiento, Long> reservasPorAlojamiento = alojamientosPopulares.stream()
                        .collect(Collectors.toMap(
                                alojamiento -> alojamiento,
                                alojamiento -> ControladorPrincipal.getInstancia()
                                        .contarReservasPorAlojamiento(alojamiento)
                        ));


                int numeroReservas = reservasPorAlojamiento.values().stream().mapToInt(Long::intValue).sum();


                XYChart.Series<String, Number> serie = new XYChart.Series<>();
                serie.setName(ciudad);
                serie.getData().add(new XYChart.Data<>(ciudad, numeroReservas));


                seriesList.add(serie);
            }


            graficoBarras.setData(seriesList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

