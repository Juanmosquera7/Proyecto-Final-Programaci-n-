package co.edu.uniquindio.alojamiento.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import co.edu.uniquindio.alojamiento.modelo.Cliente;
import java.awt.event.ActionEvent;

public class RecargarBilleteraControlador {

    @FXML
    private Label saldoActual;

    @FXML
    private TextField montoRecarga;

    @FXML
    private Button btnRecargar;

    @FXML
    private TextField cedulaTextField;  // Campo para que el usuario ingrese la cédula

    
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void recargarBilletera(javafx.event.ActionEvent event) {
        try {
            // Obtener la cédula del cliente desde un campo de texto
            String cedulaCliente = cedulaTextField.getText();

            // Validar que se haya ingresado la cédula
            if (cedulaCliente.isEmpty()) {
                mostrarAlerta("Error", "Por favor ingresa una cédula.");
                return;
            }

            // Obtener el cliente con la cédula proporcionada
            Cliente cliente = ControladorPrincipal.getInstancia().obtenerClientePorCedula(cedulaCliente);

            // Validar que se haya ingresado un monto
            if (montoRecarga.getText().isEmpty()) {
                mostrarAlerta("Error", "Por favor ingresa un monto a recargar.");
                return;
            }

            // Parsear el monto ingresado
            float monto = Float.parseFloat(montoRecarga.getText());

            // Llamar al método de recarga de billetera en el ControladorPrincipal
            ControladorPrincipal.getInstancia().recargarBilletera(cedulaCliente, monto);

            // Actualizar el saldo mostrado (obtener el nuevo saldo del cliente)
            float nuevoSaldo = ControladorPrincipal.getInstancia().consultarSaldoCliente(cedulaCliente);
            saldoActual.setText(String.valueOf(nuevoSaldo));

            // Mostrar mensaje de éxito
            mostrarAlerta("Recarga Exitosa", "La billetera ha sido recargada exitosamente.");

            // Limpiar el campo de monto
            montoRecarga.clear();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor ingresa un monto válido.");
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }
}


