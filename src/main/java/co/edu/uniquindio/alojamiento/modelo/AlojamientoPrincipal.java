package co.edu.uniquindio.alojamiento.modelo;

import co.edu.uniquindio.alojamiento.modelo.factory.TipoAlojamientos;
import co.edu.uniquindio.alojamiento.servicio.ServiciosAlojamientos;
import co.edu.uniquindio.alojamiento.utils.EnvioEmail;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AlojamientoPrincipal implements ServiciosAlojamientos {
    // Listas para almacenar los objetos
    private final List<Cliente> clientes = new ArrayList<>();
    private final List<Alojamiento> alojamientos = new ArrayList<>();
    private final List<Reserva> reservas = new ArrayList<>();
    private final List<Oferta> ofertas = new ArrayList<>();
    private final List<Resena> resenas = new ArrayList<>();

    // Métodos de cliente

    @Override
    public Cliente loginCliente(String email, String contrasena) throws Exception {
        for (Cliente cliente : clientes) {
            if (cliente.getEmail().equals(email) && cliente.getContrasena().equals(contrasena)) {
                return cliente;
            }
        }
        throw new Exception("Usuario o contraseña incorrectos.");
    }

    @Override
    public void registrarCliente(String cedula, String nombreCompleto, String telefono, String email, String contrasena) throws Exception {
        // Verificar si el cliente ya está registrado con el mismo correo
        for (Cliente cliente : clientes) {
            if (cliente.getEmail().equals(email)) {
                throw new Exception("El cliente ya está registrado con este correo.");
            }
        }

        // Crear un nuevo cliente utilizando el patrón Builder de Lombok
        Cliente nuevoCliente = Cliente.builder()
                .cedula(cedula)
                .nombreCompleto(nombreCompleto)
                .telefono(telefono)
                .email(email)
                .contrasena(contrasena)
                .cuentaActivada(true)  // La cuenta no está activada al registrarse
                .saldoBilletera(0)      // El saldo inicial es 0
                .build();

        // Agregar el cliente a la lista
        clientes.add(nuevoCliente);
    }


    @Override
    public void activarCuentaCliente(String email, String codigoActivacion) throws Exception {
        for (Cliente cliente : clientes) {
            if (cliente.getEmail().equals(email)) {
                cliente.setCuentaActiva(true);
                return;
            }
        }
        throw new Exception("Cliente no encontrado.");
    }

    @Override
    public void actualizarCliente(String cedula, String nombreCompleto, String telefono, String email, String contrasena) throws Exception {
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equals(cedula)) {
                cliente.setNombreCompleto(nombreCompleto);
                cliente.setTelefono(telefono);
                cliente.setEmail(email);
                cliente.setContrasena(contrasena);
                return;
            }
        }
        throw new Exception("Cliente no encontrado.");
    }

    @Override
    public void eliminarCuentaCliente(String cedula) throws Exception {
        for (Iterator<Cliente> iterator = clientes.iterator(); iterator.hasNext();) {
            Cliente cliente = iterator.next();
            if (cliente.getCedula().equals(cedula)) {
                iterator.remove();
                return;
            }
        }
        throw new Exception("Cliente no encontrado.");
    }

    @Override
    public void solicitarCambioContrasena(String email) throws Exception {
        Cliente cliente = clientes.stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new Exception("Cliente no encontrado."));
    }

    @Override
    public void cambiarContrasena(String email, String codigo, String nuevaContrasena) throws Exception {
        Cliente cliente = clientes.stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new Exception("Cliente no encontrado."));
        cliente.setContrasena(nuevaContrasena);
    }

    @Override
    public List<Alojamiento> buscarAlojamientoPorCiudadTipoYPrecio(String ciudad, TipoAlojamientos tipo, float precioMin, float precioMax) {
        return alojamientos.stream()
                .filter(alojamiento -> (ciudad == null || alojamiento.getCiudad().equalsIgnoreCase(ciudad)))
                .filter(alojamiento -> (tipo == null || alojamiento.getTipo() == tipo))
                .filter(alojamiento -> alojamiento.getPrecioPorNoche() >= precioMin && alojamiento.getPrecioPorNoche() <= precioMax)
                .collect(Collectors.toList());
    }

    @Override
    public void agregarResena(Cliente cliente, Alojamiento alojamiento, String comentario, int valoracion) throws Exception {
        // Validar la valoración, debe estar entre 1 y 5
        if (valoracion < 1 || valoracion > 5) {
            throw new Exception("La valoración debe estar entre 1 y 5.");
        }

        // Crear la nueva reseña
        Resena nuevaResena = Resena.builder()
                .idResena(UUID.randomUUID().toString())  // Generamos un ID único para la reseña
                .cliente(cliente)
                .alojamiento(alojamiento)
                .comentario(comentario)
                .valoracion(valoracion)
                .build();

        // Agregar la reseña a la lista de reseñas
        resenas.add(nuevaResena);

        // Opcional: puedes agregar lógica para guardar la reseña en una base de datos o almacenamiento persistente si es necesario
    }

    @Override
    public List<Resena> listarResenas() throws Exception {
        if (resenas.isEmpty()) {
            throw new Exception("No hay reseñas disponibles.");
        }
        return new ArrayList<>(resenas); // Devolver una copia de la lista de reseñas
    }



    // Método para obtener las ciudades únicas
    public List<String> obtenerCiudadesUnicas() {
        return alojamientos.stream()
                .map(Alojamiento::getCiudad)  // Obtener la ciudad de cada alojamiento
                .distinct()  // Eliminar duplicados
                .collect(Collectors.toList());  // Recoger las ciudades en una lista
    }

    @Override
    public void eliminarResena(String idReseña, Alojamiento alojamiento) throws Exception {
        Resena resenaAEliminar = resenas.stream()
                .filter(resena -> resena.getIdResena().equals(idReseña) && resena.getAlojamiento().equals(alojamiento))
                .findFirst()
                .orElseThrow(() -> new Exception("Reseña no encontrada."));

        resenas.remove(resenaAEliminar);
    }


    @Override
    public List<Resena> obtenerReseñasSegunValoracion(Alojamiento alojamiento, int valoracion) throws Exception {
        if (valoracion < 1 || valoracion > 5) {
            throw new Exception("La valoración debe estar entre 1 y 5.");
        }

        return resenas.stream()
                .filter(resena -> resena.getAlojamiento().equals(alojamiento) && resena.getValoracion() == valoracion)
                .collect(Collectors.toList());
    }


    @Override
    public long contarReservasPorAlojamiento(Alojamiento alojamiento) {
        // Filtrar las reservas que corresponden al alojamiento específico
        return reservas.stream()
                .filter(reserva -> reserva.getAlojamiento().equals(alojamiento))
                .count(); // Contar las reservas filtradas
    }


    @Override
    public List<Alojamiento> buscarAlojamientoPorNombre(String nombre) {
        List<Alojamiento> todosAlojamientos = obtenerTodosLosAlojamientos();  // Supón que tienes un método que obtiene todos los alojamientos
        List<Alojamiento> alojamientosFiltrados = new ArrayList<>();

        for (Alojamiento alojamiento : todosAlojamientos) {
            if (alojamiento.getNombre().toLowerCase().contains(nombre.toLowerCase())) {  // Filtrar por nombre (case insensitive)
                alojamientosFiltrados.add(alojamiento);
            }
        }

        return alojamientosFiltrados;
    }

    // Método para obtener todos los alojamientos
    @Override
    public List<Alojamiento> obtenerTodosLosAlojamientos() {
        return new ArrayList<>(alojamientos);
    }



    // Método para obtener los tipos de alojamiento únicos
    public List<TipoAlojamientos> obtenerTiposUnicos() {
        return alojamientos.stream()
                .map(Alojamiento::getTipo)  // Obtener el tipo de cada alojamiento
                .distinct()  // Eliminar duplicados
                .collect(Collectors.toList());  // Recoger los tipos en una lista
    }

    @Override
    public List<Alojamiento> obtenerAlojamientosPorCliente(Cliente cliente) {
        // Filtrar las reservas que corresponden al cliente dado
        List<Reserva> reservasCliente = reservas.stream()
                .filter(reserva -> reserva.getCliente().equals(cliente)) // Filtrar por cliente
                .collect(Collectors.toList());

        // Obtener los alojamientos asociados a las reservas del cliente
        List<Alojamiento> alojamientosCliente = reservasCliente.stream()
                .map(Reserva::getAlojamiento) // Mapear cada reserva a su alojamiento
                .distinct() // Asegurarse de que no haya duplicados
                .collect(Collectors.toList());

        return alojamientosCliente;
    }

    @Override
    public double obtenerGananciasTotalesReservas(Alojamiento alojamiento) {
        double gananciasTotales = 0;

        // Obtener las reservas asociadas al alojamiento
        List<Reserva> reservasAlojamiento = reservas.stream()
                .filter(reserva -> reserva.getAlojamiento().equals(alojamiento)) // Filtrar reservas del alojamiento
                .collect(Collectors.toList());

        // Calcular las ganancias totales
        for (Reserva reserva : reservasAlojamiento) {
            // Calcular la cantidad de noches de la reserva
            long nochesReserva = ChronoUnit.DAYS.between(reserva.getFechaInicio(), reserva.getFechaFin());

            // Calcular las ganancias de la reserva (número de noches * precio por noche)
            double gananciasReserva = nochesReserva * alojamiento.getPrecioPorNoche();

            // Sumar la ganancia de esta reserva
            gananciasTotales += gananciasReserva;
        }

        return gananciasTotales;
    }



    @Override
    public boolean existeReserva(Cliente cliente, Alojamiento alojamiento, LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        try {
            // Obtener todas las reservas para el cliente
            List<Reserva> reservasCliente = listarReservasPorCliente(cliente.getCedula());

            // Iterar a través de las reservas para verificar si alguna se solapa con las fechas proporcionadas
            for (Reserva reserva : reservasCliente) {
                // Si el alojamiento de la reserva actual es el mismo que el que estamos verificando
                if (reserva.getAlojamiento().equals(alojamiento)) {
                    // Comprobar si las fechas de la reserva se solapan con las fechas solicitadas
                    if ((fechaInicio.isBefore(reserva.getFechaFin()) && fechaFin.isAfter(reserva.getFechaInicio())) ||
                            fechaInicio.isEqual(reserva.getFechaInicio()) ||
                            fechaFin.isEqual(reserva.getFechaFin())) {
                        return true; // Ya existe una reserva en las fechas solicitadas
                    }
                }
            }

            // Si no se encuentra ninguna reserva que se solape, devolver false
            return false;

        } catch (Exception e) {
            // Manejo de excepciones
            throw new Exception("Error al verificar la existencia de la reserva: " + e.getMessage(), e);
        }
    }



    @Override
    public Reserva reservarAlojamiento(Alojamiento alojamiento, Cliente cliente, LocalDate fechaInicio, LocalDate fechaFin, int numHuespedes) throws Exception {
        // Validaciones iniciales
        if (alojamiento == null) {
            throw new Exception("El alojamiento no puede ser nulo.");
        }
        if (cliente == null) {
            throw new Exception("El cliente no puede ser nulo.");
        }
        if (fechaInicio == null || fechaFin == null) {
            throw new Exception("Las fechas de inicio y fin no pueden ser nulas.");
        }
        if (fechaInicio.isBefore(LocalDate.now())) {
            throw new Exception("La fecha de inicio no puede ser anterior a la fecha actual.");
        }
        if (fechaFin.isBefore(fechaInicio)) {
            throw new Exception("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
        if (numHuespedes <= 0) {
            throw new Exception("El número de huéspedes debe ser mayor a 0.");
        }
        if (numHuespedes > alojamiento.getCapacidadMaxima()) {
            throw new Exception("El número de huéspedes excede la capacidad máxima del alojamiento.");
        }

        // Validar que el cliente tenga saldo suficiente
        float saldoCliente = cliente.getBilletera().getSaldo();
        long diasReserva = fechaInicio.until(fechaFin).getDays();
        float costoReserva = diasReserva * alojamiento.getPrecioPorNoche();

        // Aplicar descuento si el alojamiento tiene oferta
        if (alojamiento.getOferta() != null) {
            float descuento = alojamiento.getOferta().getDescuento() / 100.0f;
            costoReserva -= costoReserva * descuento;
        }

        if (saldoCliente < costoReserva) {
            throw new Exception("El cliente no tiene suficiente saldo para realizar la reserva.");
        }

        // Crear la reserva
        Reserva nuevaReserva = Reserva.builder()
                .idReserva(UUID.randomUUID())
                .cliente(cliente)
                .alojamiento(alojamiento)
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .numHuespedes(numHuespedes)
                .build();

        // Actualizar el saldo de la billetera del cliente
        cliente.getBilletera().setSaldo(saldoCliente - costoReserva);

        // Generar factura
        //Factura factura = Factura.builder()
                //.idFactura(UUID.randomUUID())
                //.Total(costoReserva)
                //.fechaEmision(LocalDate.now())
                //.detalles("Reserva de alojamiento: " + alojamiento.getNombre())
                //.build();
        //nuevaReserva.setFactura(factura);

        // Registrar la reserva en el sistema (puedes agregarla a una lista global de reservas)
        reservas.add(nuevaReserva);

        //enviarEmailConfirmacionReserva(nuevaReserva);


        return nuevaReserva;
    }
    private void enviarEmailConfirmacionReserva(Reserva reserva) {

        String destinatario = reserva.getCliente().getEmail();
        String asunto = "Confirmación de Reserva de Alojamiento";


        Alojamiento alojamiento = reserva.getAlojamiento();
        String mensaje = "Detalles de la Reserva:\n" +
                "Alojamiento: " + alojamiento.getNombre() + "\n" +
                "Ciudad: " + alojamiento.getCiudad() + "\n" +
                "Descripción: " + alojamiento.getDescripcion() + "\n" +
                "Precio por Noche: $" + alojamiento.getPrecioPorNoche() + "\n" +
                "Capacidad Máxima: " + alojamiento.getCapacidadMaxima() + " personas\n" +
                "Servicios incluidos: " + String.join(", ", alojamiento.getServicios()) + "\n\n" +
                "Factura: \n" + alojamiento.getTipo().generarFactura();  // Genera la factura según el tipo de alojamiento


        EnvioEmail.enviarNotificacion(destinatario, asunto, mensaje);
    }



    @Override
    public Cliente obtenerClienteActual() throws Exception {
        try {
            // Suponiendo que la clase Sesion tiene un método getCliente() que retorna el cliente actual
            Cliente clienteActual = Sesion.getInstancia().getCliente();

            // Si no hay cliente logueado, lanzamos una excepción
            if (clienteActual == null) {
                throw new Exception("No hay ningún cliente logueado.");
            }

            return clienteActual;
        } catch (Exception e) {
            throw new Exception("Error al obtener el cliente actual: " + e.getMessage(), e);
        }
    }


    @Override
    public void crearAlojamiento(String nombre, String ciudad, String descripcion, String imagenUrl, float precioPorNoche,
                                 int capacidadMaxima, List<String> servicios, TipoAlojamientos tipo, Oferta oferta) throws Exception {
        Alojamiento alojamiento = new Alojamiento(
                UUID.randomUUID().toString(),
                nombre,
                ciudad,
                descripcion,
                imagenUrl,
                precioPorNoche,
                capacidadMaxima,
                servicios,
                tipo,
                oferta
        );
        alojamientos.add(alojamiento);
    }



    // Métodos de alojamiento



    @Override
    public void modificarAlojamiento(String idAlojamiento, String nombre, String ciudad, String descripcion, String imagenUrl, float precioPorNoche, int capacidadMaxima, List<String> servicios) throws Exception {
        Alojamiento alojamiento = alojamientos.stream()
                .filter(a -> a.getIdAlojamiento().equals(idAlojamiento))
                .findFirst()
                .orElseThrow(() -> new Exception("Alojamiento no encontrado."));

        alojamiento.setNombre(nombre);
        alojamiento.setCiudad(ciudad);
        alojamiento.setDescripcion(descripcion);
        alojamiento.setImagenUrl(imagenUrl);
        alojamiento.setPrecioPorNoche(precioPorNoche);
        alojamiento.setCapacidadMaxima(capacidadMaxima);
        alojamiento.setServicios(servicios);
    }

    @Override
    public void eliminarAlojamiento(String idAlojamiento) throws Exception {
        Alojamiento alojamiento = alojamientos.stream()
                .filter(a -> a.getIdAlojamiento().equals(idAlojamiento))
                .findFirst()
                .orElseThrow(() -> new Exception("Alojamiento no encontrado."));
        alojamientos.remove(alojamiento);
    }

    @Override
    public List<Alojamiento> buscarAlojamientoPorCiudad(String ciudad) {
        // Filtramos los alojamientos por la ciudad
        return alojamientos.stream()
                .filter(alojamiento -> alojamiento.getCiudad().equalsIgnoreCase(ciudad))
                .collect(Collectors.toList());
    }

    @Override
    public List<Alojamiento> buscarAlojamiento(String nombre, String tipo, String ciudad, float precioMin, float precioMax) {
        List<Alojamiento> resultado = new ArrayList<>();
        for (Alojamiento alojamiento : alojamientos) {
            if ((nombre == null || alojamiento.getNombre().contains(nombre)) &&
                    (tipo == null || alojamiento.getTipo().toString().equals(tipo)) &&
                    (ciudad == null || alojamiento.getCiudad().equals(ciudad)) &&
                    alojamiento.getPrecioPorNoche() >= precioMin && alojamiento.getPrecioPorNoche() <= precioMax) {
                resultado.add(alojamiento);
            }
        }
        return resultado;
    }

    // Métodos de oferta

    @Override
    public Reserva crearReserva(String idAlojamiento, String cedulaCliente, LocalDate fechaInicio, LocalDate fechaFin, int numHuespedes) throws Exception {
        // 1. Buscar al cliente usando su cédula
        Cliente cliente = clientes.stream()
                .filter(c -> c.getCedula().equals(cedulaCliente))
                .findFirst()
                .orElseThrow(() -> new Exception("Cliente no encontrado."));

        // 2. Buscar el alojamiento usando su ID
        Alojamiento alojamiento = alojamientos.stream()
                .filter(a -> a.getIdAlojamiento().equals(idAlojamiento))
                .findFirst()
                .orElseThrow(() -> new Exception("Alojamiento no encontrado."));

        // 3. Verificar si la capacidad máxima del alojamiento es suficiente para el número de huéspedes
        if (alojamiento.getCapacidadMaxima() < numHuespedes) {
            throw new Exception("Número de huéspedes excede la capacidad del alojamiento.");
        }

        // 4. Verificar que las fechas de inicio y fin sean válidas
        if (fechaInicio.isAfter(fechaFin)) {
            throw new Exception("La fecha de inicio no puede ser después de la fecha de fin.");
        }

        // 5. Crear la reserva
        Reserva reserva = Reserva.builder()
                .idReserva(UUID.randomUUID())  // Generar un ID único para la reserva
                .cliente(cliente)
                .alojamiento(alojamiento)
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .numHuespedes(numHuespedes)
                .factura(null)  // Inicialmente no hay factura generada
                .build();

        // 6. Agregar la reserva a la lista de reservas
        reservas.add(reserva);

        // 7. Retornar la reserva creada
        return reserva;
    }



    @Override
    public void modificarOferta(String idOferta, float descuento, LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        Oferta oferta = ofertas.stream()
                .filter(o -> o.getIdOferta().equals(idOferta))
                .findFirst()
                .orElseThrow(() -> new Exception("Oferta no encontrada."));

        oferta.setDescuento(descuento);
        oferta.setFechaInicio(fechaInicio);
        oferta.setFechaFin(fechaFin);
    }

    @Override
    public void eliminarOferta(String idOferta) throws Exception {
        Oferta oferta = ofertas.stream()
                .filter(o -> o.getIdOferta().equals(idOferta))
                .findFirst()
                .orElseThrow(() -> new Exception("Oferta no encontrada."));
        ofertas.remove(oferta);
    }

    @Override
    public List<Oferta> buscarOferta(String idAlojamiento) {
        List<Oferta> resultado = new ArrayList<>();

        // Recorremos todas las ofertas disponibles
        for (Oferta oferta : ofertas) {
            // Verificamos si la oferta está asociada al alojamiento cuyo id se ha pasado como parámetro
            if (oferta.getAlojamiento().getIdAlojamiento().equals(idAlojamiento)) {
                resultado.add(oferta);
            }
        }

        return resultado;
    }

    @Override
    public List<Oferta> listarOfertasEspeciales() throws Exception {
        try {
            // Devolver la lista de ofertas especiales
            return new ArrayList<>(ofertas);
        } catch (Exception e) {
            // Manejo de excepciones
            throw new Exception("Error al listar las ofertas especiales: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Alojamiento> obtenerAlojamientos() {
        // Retorna todos los alojamientos almacenados en la lista
        return new ArrayList<>(alojamientos); // Retornamos una copia para evitar modificaciones externas
    }



    @Override
    public List<Alojamiento> obtenerAlojamientosPopulares(String ciudad) {
        // Filtrar alojamientos por ciudad y ordenarlos por cantidad de reservas realizadas por clientes
        return alojamientos.stream()
                .filter(a -> a.getCiudad().equalsIgnoreCase(ciudad)) // Filtrar por ciudad
                .sorted((a1, a2) -> Long.compare(
                        reservas.stream()
                                .filter(r -> r.getAlojamiento().equals(a1) && r.getCliente() != null) // Contar reservas de a1
                                .count(),
                        reservas.stream()
                                .filter(r -> r.getAlojamiento().equals(a2) && r.getCliente() != null) // Contar reservas de a2
                                .count()
                ))
                .collect(Collectors.toList());
    }


    @Override
    public List<TipoAlojamiento> obtenerTiposAlojamientoRentables() {
        return List.of();
    }


    private double calcularGananciaReserva(Reserva reserva) {
        long dias = ChronoUnit.DAYS.between(reserva.getFechaInicio(), reserva.getFechaFin());
        return dias * reserva.getAlojamiento().getPrecioPorNoche();
    }

    @Override
    public float obtenerOcupacionAlojamiento(String idAlojamiento) {
        Alojamiento alojamiento = alojamientos.stream()
                .filter(a -> a.getIdAlojamiento().equals(idAlojamiento))
                .findFirst()
                .orElse(null);

        if (alojamiento == null) {
            return 0;
        }

        // Calcular los días reservados y totales en el año actual
        long diasReservados = reservas.stream()
                .filter(r -> r.getAlojamiento().equals(alojamiento))
                .mapToLong(r -> ChronoUnit.DAYS.between(r.getFechaInicio(), r.getFechaFin()))
                .sum();

        long diasTotales = LocalDate.now().isLeapYear() ? 366 : 365;  // Año bisiesto o no

        return (float) diasReservados / diasTotales * 100;
    }

    @Override
    public double obtenerOcupacionPorcentual(Alojamiento alojamiento) {
        // Definir el rango de fechas (últimos 365 días)
        LocalDate fechaInicio = LocalDate.now().minusDays(365);
        LocalDate fechaFin = LocalDate.now();

        // Días totales en el rango
        long diasTotales = ChronoUnit.DAYS.between(fechaInicio, fechaFin);

        // Filtrar las reservas que pertenecen al alojamiento, están dentro del rango de fechas y son efectivas
        long diasOcupados = reservas.stream()
                .filter(reserva -> reserva.getAlojamiento().equals(alojamiento)) // Reservas del alojamiento
                .filter(reserva -> !reserva.getFechaInicio().isAfter(fechaFin) && !reserva.getFechaFin().isBefore(fechaInicio)) // Reservas dentro del rango
                .mapToLong(reserva -> {
                    // Determinar el período de ocupación dentro del rango
                    LocalDate inicio = reserva.getFechaInicio().isBefore(fechaInicio) ? fechaInicio : reserva.getFechaInicio();
                    LocalDate fin = reserva.getFechaFin().isAfter(fechaFin) ? fechaFin : reserva.getFechaFin();

                    // Contar los días ocupados
                    return ChronoUnit.DAYS.between(inicio, fin);
                })
                .sum();

        // Evitar división por cero
        if (diasTotales == 0) {
            return 0.0;
        }

        // Calcular porcentaje de ocupación
        return (double) diasOcupados / diasTotales * 100;
    }



    @Override
    public float obtenerGananciasAlojamiento(String idAlojamiento) {
        Alojamiento alojamiento = alojamientos.stream()
                .filter(a -> a.getIdAlojamiento().equals(idAlojamiento))
                .findFirst()
                .orElse(null);

        if (alojamiento == null) {
            return 0;
        }

        // Calcular las ganancias sumando el valor de cada reserva
        return (float) reservas.stream()
                .filter(r -> r.getAlojamiento().equals(alojamiento))
                .mapToDouble(this::calcularGananciaReserva)
                .sum();
    }

    @Override
    public double obtenerGananciasTotales() {
        // Sumar las ganancias de todas las reservas
        double gananciasTotales = 0.0;

        for (Reserva reserva : reservas) {
            gananciasTotales += calcularGananciaReserva(reserva);
        }

        return gananciasTotales;
    }



    // Métodos de reservas

    @Override
    public void crearOferta(String idAlojamiento, float descuento, LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        // Buscar el alojamiento por su ID
        Alojamiento alojamiento = alojamientos.stream()
                .filter(a -> a.getIdAlojamiento().equals(idAlojamiento))
                .findFirst()
                .orElseThrow(() -> new Exception("Alojamiento no encontrado."));

        // Validación de descuento
        if (descuento < 0 || descuento > 100) {
            throw new Exception("El descuento debe ser entre 0 y 100.");
        }

        // Validación de fechas
        if (fechaInicio.isAfter(fechaFin)) {
            throw new Exception("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }

        // Generar un ID único para la oferta
        String idOferta = UUID.randomUUID().toString();  // Crear un ID único para la oferta

        // Crear la nueva oferta para el alojamiento
        Oferta nuevaOferta = Oferta.builder()
                .idOferta(idOferta)  // Asignar el ID generado
                .alojamiento(alojamiento)  // Asociar el alojamiento con la oferta
                .descuento(descuento)  // Asignar el descuento
                .fechaInicio(fechaInicio)  // Asignar la fecha de inicio
                .fechaFin(fechaFin)  // Asignar la fecha de fin
                .build();

        // Agregar la oferta a la lista de ofertas
        ofertas.add(nuevaOferta);
    }




    @Override
    public void cancelarReserva(UUID idReserva) throws Exception {
        Reserva reserva = reservas.stream()
                .filter(r -> r.getIdReserva().equals(idReserva))
                .findFirst()
                .orElseThrow(() -> new Exception("Reserva no encontrada."));
        reservas.remove(reserva);
    }

    @Override
    public List<Reserva> listarReservasPorCliente(String cedulaCliente) {
        List<Reserva> resultado = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getCliente().getCedula().equals(cedulaCliente)) {
                resultado.add(reserva);
            }
        }
        return resultado;
    }

    @Override
    public List<Reserva> listarReservasPorAlojamiento(String idAlojamiento) {
        List<Reserva> resultado = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getAlojamiento().getIdAlojamiento().equals(idAlojamiento)) {
                resultado.add(reserva);
            }
        }
        return resultado;
    }

    @Override
    public void recargarBilletera(String cedulaCliente, float monto) throws Exception {
        // Verificar que el monto sea positivo
        if (monto <= 0) {
            throw new Exception("El monto debe ser mayor a 0.");
        }

        // Buscar el cliente por su cédula
        Cliente cliente = clientes.stream()
                .filter(c -> c.getCedula().equals(cedulaCliente))
                .findFirst()
                .orElseThrow(() -> new Exception("Cliente no encontrado."));

        // Verificar que la cuenta esté activada
        if (!cliente.isCuentaActivada()) {
            throw new Exception("La cuenta del cliente no está activada.");
        }

        // Si la billetera es null, inicializarla
        if (cliente.getBilletera() == null) {
            cliente.setBilletera(new Billetera(0)); // O usar el saldo actual si quieres
        }

        // Recargar el saldo de la billetera
        cliente.getBilletera().setSaldo(cliente.getBilletera().getSaldo() + monto);
    }



    @Override
    public float consultarSaldoBilletera(String cedulaCliente) {
        return 0;
    }

    @Override
    public void agregarResena(String idAlojamiento, String cedulaCliente, String comentario, int valoracion) throws Exception {

    }

    @Override
    public List<Resena> listarResenasPorAlojamiento(String idAlojamiento) {
        return List.of();
    }

    @Override
    public Factura generarFactura(UUID idReserva) throws Exception {
        return null;
    }

    @Override
    public String generarCodigoQR(Factura factura) throws Exception {
        return "";
    }

    @Override
    public void enviarCorreoConQR(String emailCliente, String codigoQR, String detallesReserva) throws Exception {

    }

    @Override
    public Cliente obtenerClientePorCedula(String cedula) throws Exception {
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equals(cedula)) {
                return cliente; // Retorna el cliente si la cédula coincide
            }
        }
        throw new Exception("No se encontró un cliente con la cédula proporcionada."); // Lanza una excepción si no lo encuentra
    }


    @Override
    public float consultarSaldoCliente(String cedulaCliente) {
        // Buscar al cliente por su cédula en la lista de clientes
        Cliente cliente = clientes.stream()
                .filter(c -> c.getCedula().equals(cedulaCliente))
                .findFirst()
                .orElse(null);

        if (cliente == null) {
            // Si no se encuentra el cliente, devolvemos 0 o lanzamos una excepción
            return 0; // O lanzar una excepción personalizada
        }

        // Retornamos el saldo de la billetera asociado al cliente
        return cliente.getBilletera() != null ? cliente.getBilletera().getSaldo() : cliente.getSaldoBilletera();
    }



    // Métodos de reseñas


}
