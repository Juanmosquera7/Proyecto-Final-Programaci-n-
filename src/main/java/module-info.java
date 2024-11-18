module co.edu.uniquindio.clinica {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.desktop;
    requires org.simplejavamail.core;
    requires org.simplejavamail;
    requires com.google.zxing;


    opens co.edu.uniquindio.alojamiento to javafx.fxml;
    exports co.edu.uniquindio.alojamiento;
    exports co.edu.uniquindio.alojamiento.controladores;
    opens co.edu.uniquindio.alojamiento.controladores to javafx.fxml;
}
