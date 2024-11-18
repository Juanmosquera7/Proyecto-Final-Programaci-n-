package co.edu.uniquindio.alojamiento.servicio;

import co.edu.uniquindio.alojamiento.modelo.*;
import co.edu.uniquindio.alojamiento.modelo.factory.TipoAlojamientos;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ServiciosAlojamientos {

    // Métodos para autenticación y gestión de usuarios
    Cliente loginCliente(String email, String contrasena) throws Exception;
    void registrarCliente(String cedula, String nombreCompleto, String telefono, String email, String contrasena) throws Exception;
    void activarCuentaCliente(String email, String codigoActivacion) throws Exception;
    void actualizarCliente(String cedula, String nombreCompleto, String telefono, String email, String contrasena) throws Exception;
    void eliminarCuentaCliente(String cedula) throws Exception;
    void solicitarCambioContrasena(String email) throws Exception;
    void cambiarContrasena(String email, String codigo, String nuevaContrasena) throws Exception;
    List<Alojamiento> buscarAlojamientoPorCiudadTipoYPrecio(String ciudad, TipoAlojamientos tipo, float precioMin, float precioMax);

    void agregarResena(Cliente cliente, Alojamiento alojamiento, String comentario, int valoracion) throws Exception;

    List<Resena> listarResenas() throws Exception;



     List<String> obtenerCiudadesUnicas() throws Exception;

    void eliminarResena(String idReseña, Alojamiento alojamiento) throws Exception;

    // Nuevo método para obtener reseñas por valoraciones
    List<Resena> obtenerReseñasSegunValoracion(Alojamiento alojamiento, int valoracion) throws Exception;



    long contarReservasPorAlojamiento(Alojamiento alojamiento);

    public List<Alojamiento> buscarAlojamientoPorNombre(String nombre);
    public List<Alojamiento> obtenerTodosLosAlojamientos();

    List<TipoAlojamientos> obtenerTiposUnicos();

    List<Alojamiento> obtenerAlojamientosPorCliente(Cliente cliente);

    double obtenerGananciasTotalesReservas(Alojamiento alojamiento);



    boolean existeReserva(Cliente cliente, Alojamiento alojamiento, LocalDate fechaInicio, LocalDate fechaFin) throws Exception;

    // Nuevo método en la interfaz ServiciosAlojamientos
    public Reserva reservarAlojamiento(Alojamiento alojamiento, Cliente cliente, LocalDate fechaInicio, LocalDate fechaFin, int numHuespedes) throws Exception;

    Cliente obtenerClienteActual() throws Exception;

    // Métodos para la gestión de alojamientos por el administrador
    void crearAlojamiento(String nombre, String ciudad, String descripcion, String imagenUrl, float precioPorNoche,
                          int capacidadMaxima, List<String> servicios, TipoAlojamientos tipo, Oferta oferta) throws Exception;
    void modificarAlojamiento(String idAlojamiento, String nombre, String ciudad, String descripcion, String imagenUrl,
                              float precioPorNoche, int capacidadMaxima, List<String> servicios) throws Exception;


    void eliminarAlojamiento(String idAlojamiento) throws Exception;

    List<Alojamiento> buscarAlojamientoPorCiudad(String ciudad);

    List<Alojamiento> buscarAlojamiento(String nombre, String tipo, String ciudad, float precioMin, float precioMax);
    // Métodos para la gestión de ofertas en los alojamientos
    void crearOferta(String idAlojamiento, float descuento, LocalDate fechaInicio, LocalDate fechaFin) throws Exception;
    void modificarOferta(String idOferta, float descuento, LocalDate fechaInicio, LocalDate fechaFin) throws Exception;
    void eliminarOferta(String idOferta) throws Exception;
    List<Oferta> buscarOferta(String idAlojamiento);
    List<Oferta> listarOfertasEspeciales() throws Exception;

    List<Alojamiento> obtenerAlojamientos();

    // Métodos de estadísticas y reportes
    List<Alojamiento> obtenerAlojamientosPopulares(String ciudad);
    List<TipoAlojamiento> obtenerTiposAlojamientoRentables();


    float obtenerOcupacionAlojamiento(String idAlojamiento);
    double obtenerOcupacionPorcentual(Alojamiento alojamiento);

    //Borrar
    float obtenerGananciasAlojamiento(String idAlojamiento);
    double obtenerGananciasTotales();

    // Métodos para la gestión de reservas por clientes
    Reserva crearReserva(String idAlojamiento, String cedulaCliente, LocalDate fechaInicio, LocalDate fechaFin,
                         int numHuespedes) throws Exception;
    void cancelarReserva(UUID idReserva) throws Exception;
    List<Reserva> listarReservasPorCliente(String cedulaCliente);
    List<Reserva> listarReservasPorAlojamiento(String idAlojamiento);

    // Métodos para la gestión de la billetera del cliente
    void recargarBilletera(String cedulaCliente, float monto) throws Exception;
    float consultarSaldoBilletera(String cedulaCliente);

    // Métodos para la gestión de reseñas y valoraciones de alojamientos
    void agregarResena(String idAlojamiento, String cedulaCliente, String comentario, int valoracion) throws Exception;
    List<Resena> listarResenasPorAlojamiento(String idAlojamiento);

    // Métodos para la generación y envío de códigos QR y facturas
    Factura generarFactura(UUID idReserva) throws Exception;
    String generarCodigoQR(Factura factura) throws Exception;
    void enviarCorreoConQR(String emailCliente, String codigoQR, String detallesReserva) throws Exception;
    Cliente obtenerClientePorCedula(String cedula) throws Exception;

    float consultarSaldoCliente(String cedulaCliente);


}

