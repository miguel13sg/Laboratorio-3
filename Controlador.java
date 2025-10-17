import java.util.*;

/**
 * Controlador principal del sistema de gestión hospitalaria
 * Coordina todas las operaciones del sistema
 */
public class Controlador {
    private List<Trabajador> trabajadores;
    private List<Citas> citas;
    private List<String> historialReagendamientos;
    private Vista vista;
    private int proximoIdCita;
    private int proximoIdTrabajador;

    public Controlador(Vista vista)
    {
        this.trabajadores = new ArrayList<>();
        this.citas = new ArrayList<>();
        this.historialReagendamientos = new ArrayList<>();
        this.vista = vista;
        this.proximoIdCita = 1;
        this.proximoIdTrabajador = 1;
    }

    // Gestión de trabajadores
    public void agregarTrabajador(Trabajador trabajador)
    {
        trabajadores.add(trabajador);
    }
    
    public int getProximoTrabajadorId() {
        return proximoIdTrabajador;
    }
    
    /**
     * Encuentra personal disponible por especialidad y horario
     */
    public List<Trabajador> encontrarPersonalDisponible(String tipoEspecialidad, Calendar horario) {
        List<Trabajador> disponibles = new ArrayList<>();
        int duracionBusqueda = 30; // Duración predeterminada para búsqueda
        
        for (Trabajador t : trabajadores) {
            if (t.getClass().getSimpleName().equalsIgnoreCase(tipoEspecialidad) && 
                t.isDisponible() && 
                !tieneConflictoHorario(t, horario, duracionBusqueda)) {
                disponibles.add(t);
            }
        }
        return disponibles;
    }
    
    /**
     * Programa una nueva cita médica
     */
    public boolean programarCita(String nombre, Trabajador trabajador, Calendar fecha, String tipo, int duracionMinutos)
    {
        // Verificar disponibilidad
        if(!trabajador.isDisponible() || tieneConflictoHorario(trabajador, fecha, duracionMinutos))
        {
            return false;
        }
        
        int nuevoId = proximoIdCita++;
        Citas citaNueva = new Citas(nuevoId, nombre, trabajador, fecha, tipo, EstadoCita.PROGRAMADA, duracionMinutos);
        citas.add(citaNueva);
        return true;
    }
    
    public int getProximoIdCita() {
        return proximoIdCita;
    }
    
    /**
     * Reagenda una cita existente
     */
    public boolean reagendarCita(int idCita, Calendar nuevaFecha)
    {
        Citas cita = buscarCitaPorId(idCita);
        if(cita == null)
        {
            return false;
        }
        
        Trabajador trabajador = cita.getTrabajador();
        if(!trabajador.isDisponible() || tieneConflictoHorario(trabajador, nuevaFecha, cita.getDuracionMinutosCita()))
        {
            return false;
        }
        
        // Registrar en historial
        String historial = String.format("Cita %d reagendada de %s a %s", idCita, cita.getFechaHora().getTime(), nuevaFecha.getTime());
        historialReagendamientos.add(historial);
        
        cita.reagendar(nuevaFecha);
        return true;
    }
    
    /**
     * Resuelve conflictos de horario automáticamente
     */
    public boolean resolverConflictoHorario(Trabajador trabajador, Calendar fechaMala)
    {
        List<Citas> citasConflicto = new ArrayList<>();
        
        // Buscar citas en conflicto
        for(Citas c : citas)
        {
            if(c.getTrabajador().equals(trabajador) && 
               c.getFechaHora().equals(fechaMala) && 
               c.getEstadoCita() == EstadoCita.PROGRAMADA)
            {
                citasConflicto.add(c);
            }
        }
        
        if(citasConflicto.isEmpty())
        {
            return false;
        }
        
        // Intentar reagendar una hora después
        Calendar nuevaFecha = (Calendar) fechaMala.clone();
        nuevaFecha.add(Calendar.HOUR, 1);
        return reagendarCita(citasConflicto.get(0).getIdCita(), nuevaFecha);
    }
    
    // Sistema de nóminas
    public int calcularNominaTotal()
    {
        int total = 0;
        for(Trabajador t : trabajadores)
        {
            total += t.getSalarioTotal(); // Usa polimorfismo
        }
        return total;
    }
    
    /**
     * Calcula nómina por departamento
     */
    public List<String> calcularNominaDepartamento()
    {
        List<String> resultado = new ArrayList<>();
        List<String> departamentos = new ArrayList<>();
        
        // Obtener lista de departamentos únicos
        for(Trabajador t : trabajadores)
        {
            String dpto = t.getDepartamento();
            if(!departamentos.contains(dpto))
            {
                departamentos.add(dpto);
            }
        }
        
        // Calcular total por departamento
        for(String dpto : departamentos)
        {
            int totalDpto = 0;
            for(Trabajador t : trabajadores)
            {
                if(t.getDepartamento().equals(dpto))
                {
                    totalDpto += t.getSalarioTotal();
                }
            }
            resultado.add("Departamento: " + dpto + " - Nómina: " + totalDpto);
        }
        return resultado;
    }
    
    // Sistema de reportes
    public List<String> generarReporteEficiencia()
    {
        List<String> resultado = new ArrayList<>();
        for(Trabajador t : trabajadores)
        {
            String estado = t.isDisponible() ? "Disponible" : "No disponible";
            resultado.add(t.getName() + " - " + estado);
        }
        return resultado;
    }
    
    public List<String> generarReportePersonal()
    {
        List<String> reporte = new ArrayList();
        for(Trabajador t : trabajadores)
        {
            reporte.add(t.toString()); // Usa polimorfismo en toString()
        }
        return reporte;
    }
    
    public List<String> generarReporteCitasPorEstado(EstadoCita estado)
    {
        List<String> reporte = new ArrayList<>();
        for(Citas c : citas)
        {
            if(c.getEstadoCita() == estado)
            {
                reporte.add(c.toString());
            }
        }
        return reporte;
    }
    
    public List<String> generarReporteCitasPorTrabajador(int idTrabajador) {
        List<String> reporte = new ArrayList<>();
        for (Citas c : citas) {
            if (c.getTrabajador().getId() == idTrabajador) {
                reporte.add(c.toString());
            }
        }
        return reporte;
    }
    
    /**
     * Genera análisis financiero completo
     */
    public List<String> generarAnalisisFinanciero() {
        List<String> analisis = new ArrayList<>();
        analisis.add("Nómina Total: " + calcularNominaTotal());
        analisis.add("Total Trabajadores: " + trabajadores.size());
        
        List<String> nominaDeptos = calcularNominaDepartamento();
        analisis.addAll(nominaDeptos);
        
        return analisis;
    }
    
    public List<String> getHistorialReagendamientos() {
        return new ArrayList<>(historialReagendamientos);
    }
    
    /**
     * Verifica conflictos de horario para un trabajador
     */
    private boolean tieneConflictoHorario(Trabajador trabajador, Calendar fecha, int duracionMinutos)
    {
        for(Citas c : citas)
        {
            if(c.getTrabajador().equals(trabajador) && 
               c.getFechaHora().equals(fecha) && 
               c.getEstadoCita() != EstadoCita.CANCELADA && 
               c.getEstadoCita() != EstadoCita.COMPLETADA)
            {
                // Verificar superposición de horarios
                Calendar inicioExistente = c.getFechaHora();
                Calendar finExistente = (Calendar) inicioExistente.clone();
                finExistente.add(Calendar.MINUTE, c.getDuracionMinutosCita());
                
                Calendar finNueva = (Calendar) fecha.clone();
                finNueva.add(Calendar.MINUTE, duracionMinutos);
                
                boolean hayConflicto = fecha.before(finExistente) && finNueva.after(inicioExistente);
                if(hayConflicto)
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    // Métodos auxiliares
    private Citas buscarCitaPorId(int id)
    {
        for(Citas c : citas)
        {
            if(c.getIdCita() == id)
            {
                return c;
            }
        }
        return null;
    }
    
    public List<Trabajador> getTrabajadores() {
         return new ArrayList<>(trabajadores); 
    }
    
    public List<Citas> getCitas() {
        return new ArrayList<>(citas);
    }
    
    /**
     * Inicia el sistema principal
     */
    public void iniciarSistema() {
        boolean ejecutando = true;
        
        while (ejecutando) {
            vista.mostrarMenuPrincipal();
            int opcion = vista.pedirOpcion();
            
            switch (opcion) {
                case 1: gestionarPersonal(); break;
                case 2: gestionarCitas(); break;
                case 3: generarReportes(); break;
                case 4: gestionarCrisisPersonal(); break;
                case 5: ejecutando = false; break;
                default: vista.mostrarError("Opción inválida");
            }
        }
        vista.mostrarDespedida();
        vista.cerrar();
    }
    
    // Menús de gestión
    private void gestionarPersonal()
    {
        boolean enMenu = true;
        while(enMenu)
        {
            vista.mostrarMenuPersonal();
            int opcion = vista.pedirOpcion();
            switch(opcion)
            {
                case 1 : agregarTrabajadorMenu(); break;
                case 2: vista.mostrarLista(generarReportePersonal()); break;
                case 3: buscarPersonalDisponibleMenu(); break;
                case 4: enMenu = false; break;
                default: vista.mostrarError("Opción inválida");
            }
        }
    }
    
    /**
     * Menú para agregar nuevo trabajador
     */
    private void agregarTrabajadorMenu()
    {
        vista.mostrarMenuEspecialidades();
        int opcionEspecialidad = vista.pedirOpcion();
        int id = proximoIdTrabajador++;
        String nombre = vista.pedirNombre();
        String departamento = vista.pedirDepartamento();
        int añosExperiencia = vista.pedirAñosExperiencia();
        Trabajador nuevoTrabajador = null;
        
        // Crear especialización según opción
        switch(opcionEspecialidad)
        {
            case 1: // Terapeuta
                String tipoTerapia = vista.pedirTipoTerapia();
                int duracion = vista.pedirDuracionSesion();
                nuevoTrabajador = new Terapeuta(id, nombre, departamento, añosExperiencia, tipoTerapia, duracion);
                break;
            case 2: // Enfermero
                boolean turnoNoche = vista.pedirTurnoNoche();
                int nivelCertificacion = vista.pedirNivelCertificacion();
                nuevoTrabajador = new Enfermero(id, nombre, departamento, añosExperiencia, turnoNoche, nivelCertificacion);
                break;
            case 3: // Radiólogo
                String equipo = vista.pedirEquipoRadiologo();
                int tarifa = vista.pedirTarifa();
                nuevoTrabajador = new Radiologo(id, nombre, departamento, añosExperiencia, equipo, tarifa);
                break;
            case 4: // Farmacéutico
                int limitePrescripciones = vista.pedirLimitePrescripciones();
                String licencia = vista.pedirLicencia();
                nuevoTrabajador = new Farmaceutico(id, nombre, departamento, añosExperiencia, limitePrescripciones, licencia);
                break;
            default:
                vista.mostrarError("Especialidad inválida");
                return;
        }
        
        if(nuevoTrabajador != null)
        {
            agregarTrabajador(nuevoTrabajador);
            vista.mostrarExito("Trabajador agregado exitosamente");
        }
    }
    
    private void buscarPersonalDisponibleMenu() {
        vista.mostrarMenuEspecialidades();
        int opcionEspecialidad = vista.pedirOpcion();
        
        String especialidad = "";
        switch (opcionEspecialidad) {
            case 1: especialidad = "Terapeuta"; break;
            case 2: especialidad = "Enfermero"; break;
            case 3: especialidad = "Radiologo"; break;
            case 4: especialidad = "Farmaceutico"; break;
            default: 
                vista.mostrarError("Especialidad inválida");
                return;
        }
        
        Calendar horario = vista.pedirFechaHora();
        List<Trabajador> disponibles = encontrarPersonalDisponible(especialidad, horario);
        vista.mostrarTrabajadores(disponibles);
    }
    
    private void gestionarCitas() {
        boolean enMenu = true;
        
        while (enMenu) {
            vista.mostrarMenuCitas();
            int opcion = vista.pedirOpcion();
            
            switch (opcion) {
                case 1: programarCitaMenu(); break;
                case 2: reagendarCitaMenu(); break;
                case 3: listarCitasPorEstadoMenu(); break;
                case 4: listarCitasPorTrabajadorMenu(); break;
                case 5: gestionarEstadosCita(); break;
                case 6: enMenu = false; break;
                default: vista.mostrarError("Opción inválida");
            }
        }
    }
    
    // Métodos de gestión de citas
    private void programarCitaMenu() {
        String nombrePaciente = vista.pedirNombrePaciente();
        
        vista.mostrarMenuEspecialidades();
        int opcionEspecialidad = vista.pedirOpcion();
        String especialidad = convertirEspecialidad(opcionEspecialidad);
        
        Calendar fecha = vista.pedirFechaHora();
        int duracion = vista.pedirDuracionCita();
        List<Trabajador> disponibles = encontrarPersonalDisponible(especialidad, fecha);
        
        if (disponibles.isEmpty()) {
            vista.mostrarError("No hay personal disponible en ese horario");
            return;
        }
        
        Trabajador trabajador = disponibles.get(0); // Toma el primero disponible
        String tipoCita = vista.pedirTipoCita();
        
        boolean exito = programarCita(nombrePaciente, trabajador, fecha, tipoCita, duracion);
        if (exito) {
            vista.mostrarExito("Cita programada exitosamente");
        } else {
            vista.mostrarError("No se pudo programar la cita");
        }
    }
    
    private void reagendarCitaMenu() {
        int idCita = vista.pedirId();
        Calendar nuevaFecha = vista.pedirFechaHora();
        
        boolean exito = reagendarCita(idCita, nuevaFecha);
        if (exito) {
            vista.mostrarExito("Cita reagendada exitosamente");
        } else {
            vista.mostrarError("No se pudo reagendar la cita");
        }
    }
    
    private void listarCitasPorEstadoMenu() {
        vista.mostrarMenuEstadosCita();
        int opcionEstado = vista.pedirOpcion();
        EstadoCita estado = null;
        
        switch (opcionEstado) {
            case 1: estado = EstadoCita.PROGRAMADA; break;
            case 2: estado = EstadoCita.CONFIRMADA; break;
            case 3: estado = EstadoCita.EN_PROGRESO; break;
            case 4: estado = EstadoCita.COMPLETADA; break;
            case 5: estado = EstadoCita.CANCELADA; break;
            case 6: estado = EstadoCita.REAGENDADA; break;
            default: 
                vista.mostrarError("Opción de estado inválida");
                return;
        }
        
        List<String> citasPorEstado = generarReporteCitasPorEstado(estado);
        if (citasPorEstado.isEmpty()) {
            vista.mostrarMensaje("No hay citas con estado: " + estado);
        } else {
            vista.mostrarMensaje("=== CITAS CON ESTADO: " + estado + " ===");
            vista.mostrarLista(citasPorEstado);
        }
    }
    
    private void listarCitasPorTrabajadorMenu() {
        int idTrabajador = vista.pedirId();
        List<String> citas = generarReporteCitasPorTrabajador(idTrabajador);
        vista.mostrarLista(citas);
    }
    
    /**
     * Menú de reportes del sistema
     */
    private void generarReportes() {
        boolean enMenu = true;
        
        while (enMenu) {
            vista.mostrarMenuReportes();
            int opcion = vista.pedirOpcion();
            
            switch (opcion) {
                case 1: vista.mostrarLista(generarReportePersonal()); break;
                case 2: vista.mostrarMensaje("Nómina Total: " + calcularNominaTotal()); break;
                case 3: vista.mostrarLista(calcularNominaDepartamento()); break;
                case 4: vista.mostrarLista(generarAnalisisFinanciero()); break;
                case 5: vista.mostrarLista(generarReporteEficiencia()); break;
                case 6: vista.mostrarLista(getHistorialReagendamientos()); break;
                case 7: enMenu = false; break;
                default: vista.mostrarError("Opción inválida");
            }
        }
    }
    
    // Métodos de conversión
    private String convertirEspecialidad(int opcion) {
        switch (opcion) {
            case 1: return "Terapeuta";
            case 2: return "Enfermero";
            case 3: return "Radiologo";
            case 4: return "Farmaceutico";
            default: return "";
        }
    }
    
    /**
     * Gestión de crisis - reagendamiento automático masivo
     */
    public void gestionarCrisisPersonal() {
        vista.mostrarMensaje("\n=== GESTIÓN DE CRISIS DE PERSONAL ===");
        vista.mostrarMenuEspecialidades();
        int opcionEspecialidad = vista.pedirOpcion();
        String especialidad = convertirEspecialidad(opcionEspecialidad);
        
        if (especialidad.isEmpty()) {
            vista.mostrarError("Especialidad inválida");
            return;
        }
        
        Calendar fechaProblema = vista.pedirFechaHora();
        List<Trabajador> disponibles = encontrarPersonalDisponible(especialidad, fechaProblema);
        
        if (!disponibles.isEmpty()) {
            vista.mostrarMensaje("Hay personal disponible: " + disponibles.size() + " " + especialidad + "s");
            vista.mostrarMensaje("No es necesaria la reagendación automática.");
            return;
        }
        
        vista.mostrarMensaje("CRISIS DETECTADA: No hay " + especialidad + "s disponibles el " + fechaProblema.getTime());
        int citasReasignadas = reagendarCitasAutomaticamente(fechaProblema, especialidad);
        vista.mostrarExito("Se reasignaron " + citasReasignadas + " citas automáticamente.");
    }
    
    /**
     * Reagenda citas automáticamente durante crisis
     */
    private int reagendarCitasAutomaticamente(Calendar fechaProblema, String especialidad) {
        List<Citas> citasAfectadas = new ArrayList<>();
        
        // Buscar citas afectadas
        for (Citas c : citas) {
            if (c.getEstadoCita() == EstadoCita.PROGRAMADA && 
                esMismoDia(c.getFechaHora(), fechaProblema) && 
                c.getTrabajador().getClass().getSimpleName().equalsIgnoreCase(especialidad)) {
                citasAfectadas.add(c);
            }
        }
        
        if (citasAfectadas.isEmpty()) {
            vista.mostrarMensaje("No hay citas afectadas para reagendar.");
            return 0;
        }
        
        vista.mostrarMensaje("Encontradas " + citasAfectadas.size() + " citas para reagendar.");
        int exitos = 0;
        
        // Intentar reagendar cada cita
        for (Citas cita : citasAfectadas) {
            boolean reagendada = false;
            
            // Probar diferentes horarios (1-6 horas después)
            for (int horaOffset = 1; horaOffset <= 6; horaOffset++) {
                Calendar nuevoHorario = (Calendar) cita.getFechaHora().clone();
                nuevoHorario.add(Calendar.HOUR, horaOffset);
                
                List<Trabajador> disponibles = encontrarPersonalDisponible(especialidad, nuevoHorario);
                if (!disponibles.isEmpty()) {
                    cita.reagendar(nuevoHorario);
                    
                    String historial = String.format("CITA %d REASIGNADA (Crisis): De %s a %s",
                            cita.getIdCita(), fechaProblema.getTime(), nuevoHorario.getTime());
                    historialReagendamientos.add(historial);
                    
                    vista.mostrarMensaje("✓ Cita " + cita.getIdCita() + " reagendada +" + horaOffset + " horas");
                    reagendada = true;
                    exitos++;
                    break;
                }
            }        
            
            if (!reagendada) {
                vista.mostrarError("✗ No se pudo reagendar cita " + cita.getIdCita());
            }
        }
        return exitos;
    }
    
    /**
     * Verifica si dos fechas son el mismo día
     */
    private boolean esMismoDia(Calendar fecha1, Calendar fecha2) {
        return fecha1.get(Calendar.YEAR) == fecha2.get(Calendar.YEAR) &&
               fecha1.get(Calendar.MONTH) == fecha2.get(Calendar.MONTH) &&
               fecha1.get(Calendar.DAY_OF_MONTH) == fecha2.get(Calendar.DAY_OF_MONTH);
    }
    
    // Gestión de estados de citas
    public boolean comenzarCita(int idCita) {
        Citas cita = buscarCitaPorId(idCita);
        if (cita != null && cita.getEstadoCita() == EstadoCita.CONFIRMADA) {
            cita.comenzar();
            return true;
        }
        return false;
    }
    
    public boolean completarCita(int idCita) {
        Citas cita = buscarCitaPorId(idCita);
        if (cita != null && cita.getEstadoCita() == EstadoCita.EN_PROGRESO) {
            cita.completar();
            actualizarTrabajadorDespuesCita(cita);
            return true;
        }
        return false;
    }
    
    public boolean confirmarCita(int idCita) {
        Citas cita = buscarCitaPorId(idCita);
        if (cita != null && cita.getEstadoCita() == EstadoCita.PROGRAMADA) {
            cita.confirmar();
            return true;
        }
        return false;
    }
    
    /**
     * Actualiza estadísticas del trabajador después de cita
     */
    private void actualizarTrabajadorDespuesCita(Citas cita) {
        Trabajador trabajador = cita.getTrabajador();
        
        if (trabajador instanceof Radiologo) {
            ((Radiologo) trabajador).agregarEstudio();
            vista.mostrarMensaje("Estudio agregado al radiólogo: " + trabajador.getName());
        } else if (trabajador instanceof Farmaceutico) {
            ((Farmaceutico) trabajador).darPreescripcion();
            vista.mostrarMensaje("Prescripción procesada por farmacéutico: " + trabajador.getName());
        }
    }
    
    private void gestionarEstadosCita() {
        boolean enMenu = true;
        while (enMenu) {
            vista.mostrarMenuGestionEstados();
            int opcion = vista.pedirOpcion();
            switch (opcion) {
                case 1: cambiarEstadoCitaMenu(); break;
                case 2: listarTodasLasCitasMenu(); break;
                case 3: enMenu = false; break;
                default: vista.mostrarError("Opción inválida");
            }
        }
    }
    
    private void cambiarEstadoCitaMenu() {
        int idCita = vista.pedirId();
        Citas cita = buscarCitaPorId(idCita);
        
        if (cita == null) {
            vista.mostrarError("Cita no encontrada");
            return;
        }
        
        vista.mostrarMensaje("Cita actual: " + cita.toString());
        vista.mostrarMenuCambioEstado();
        int opcion = vista.pedirOpcion();
        
        boolean exito = false;
        switch (opcion) {
            case 1: exito = confirmarCita(idCita); break;
            case 2: exito = comenzarCita(idCita); break;
            case 3: exito = completarCita(idCita); break;
            case 4: cita.cancelar(); exito = true; break;
            default: 
                vista.mostrarError("Opción inválida");
                return;
        }
        
        if (exito) {
            vista.mostrarExito("Estado de cita actualizado exitosamente");
            vista.mostrarMensaje("Nuevo estado: " + cita.getEstadoCita());
        } else {
            vista.mostrarError("No se pudo cambiar el estado. Verifique las transiciones permitidas.");
            vista.mostrarMensaje("Estado actual: " + cita.getEstadoCita());
            vista.mostrarTransicionesValidas();
        }
    }
    
    private void listarTodasLasCitasMenu() {
        if (citas.isEmpty()) {
            vista.mostrarMensaje("No hay citas registradas.");
            return;
        }
        
        List<String> todasCitas = new ArrayList<>();
        for (Citas c : citas) {
            todasCitas.add(c.toString());
        }
        vista.mostrarLista(todasCitas);
    }
}