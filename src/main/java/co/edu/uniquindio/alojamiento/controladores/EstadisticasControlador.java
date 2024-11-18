package co.edu.uniquindio.alojamiento.controladores;

import co.edu.uniquindio.alojamiento.modelo.Alojamiento;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class EstadisticasControlador implements Initializable {

    @FXML
    private BarChart<String, Number> ocupacionChart; // Gráfico de barras para ocupación

    @FXML
    private PieChart gananciasChart; // Gráfico de pastel para ganancias

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarEstadisticasOcupacion();
        cargarEstadisticasGanancias();
    }

    /**
     * Carga los datos de ocupación porcentual de cada alojamiento en el gráfico de barras.
     */
    private void cargarEstadisticasOcupacion() {
        try {
            // Obtener la lista de alojamientos desde ControladorPrincipal
            List<Alojamiento> alojamientos = ControladorPrincipal.getInstancia().obtenerAlojamientos();

            // Crear una serie para el gráfico de barras
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Ocupación (%)");

            // Agregar los datos de ocupación porcentual de cada alojamiento
            for (Alojamiento alojamiento : alojamientos) {
                // Obtener el porcentaje de ocupación de cada alojamiento
                double ocupacion = ControladorPrincipal.getInstancia().obtenerOcupacionPorcentual(alojamiento);
                series.getData().add(new XYChart.Data<>(alojamiento.getDescripcion(), ocupacion));
            }

            // Añadir la serie al gráfico
            ocupacionChart.getData().clear();
            ocupacionChart.getData().add(series);

        } catch (Exception e) {
            e.printStackTrace(); // Manejo de excepciones
        }
    }

    /**
     * Carga los datos de ganancias totales de cada alojamiento en el gráfico de pastel.
     */
    private void cargarEstadisticasGanancias() {
        try {
            // Obtener la lista de alojamientos desde ControladorPrincipal
            List<Alojamiento> alojamientos = ControladorPrincipal.getInstancia().obtenerAlojamientos();

            // Crear una lista para los datos del gráfico de pastel
            List<PieChart.Data> dataList = FXCollections.observableArrayList();

            // Agregar los datos de ganancias totales de cada alojamiento
            for (Alojamiento alojamiento : alojamientos) {
                // Obtener las ganancias totales de cada alojamiento
                double ganancias = ControladorPrincipal.getInstancia().obtenerGananciasTotales();
                dataList.add(new PieChart.Data(alojamiento.getDescripcion(), ganancias));
            }

            // Configurar el gráfico con los datos
            gananciasChart.setData(FXCollections.observableArrayList(dataList));

        } catch (Exception e) {
            e.printStackTrace(); // Manejo de excepciones
        }
    }
}

