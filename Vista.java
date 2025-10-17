import java.util.*;

/**
 * Clase Vista - Maneja toda la interacción con el usuario
 * Responsable de mostrar menús y capturar entradas
 */
public class Vista {
    private Scanner scanner;
    
    public Vista() {
        this.scanner = new Scanner(System.in);
    }
    
    // Menús principales del sistema
    public void mostrarMenuPrincipal() {
        System.out.println("\n=== SISTEMA HOSPITALARIO ===");
        System.out.println("1. Gestión de Personal");
        System.out.println("2. Gestión de Citas");
        System.out.println("3. Reportes y Análisis");
        System.out.println("4. Gestión de Crisis");
        System.out.println("5. Salir");
        System.out.print("Seleccione opción: ");
    }
    
    public void mostrarMenuPersonal() {
        System.out.println("\n--- GESTIÓN DE PERSONAL ---");
        System.out.println("1. Agregar Trabajador");
        System.out.println("2. Listar Todo el Personal");
        System.out.println("3. Buscar Personal Disponible");
        System.out.println("4. Volver al Menú Principal");
        System.out.print("Seleccione opción: ");
    }
    
    public void mostrarMenuCitas() {
        System.out.println("\n--- GESTIÓN DE CITAS ---");
        System.out.println("1. Programar Cita");
        System.out.println("2. Reagendar Cita");
        System.out.println("3. Listar Citas por Estado");
        System.out.println("4. Listar Citas por Trabajador");
        System.out.println("5. Gestionar Estados de Cita");
        System.out.println("6. Volver al Menú Principal");
        System.out.print("Seleccione opción: ");
    }
    
    // Menús de gestión de estados de citas
    public void mostrarMenuGestionEstados() {
        System.out.println("\n--- GESTIÓN DE ESTADOS DE CITA ---");
        System.out.println("1. Cambiar Estado de Cita");
        System.out.println("2. Listar Todas las Citas");
        System.out.println("3. Volver al Menú Anterior");
        System.out.print("Seleccione opción: ");
    }
    
    public void mostrarMenuCambioEstado() {
        System.out.println("\n--- CAMBIAR ESTADO DE CITA ---");
        System.out.println("1. CONFIRMAR (Programada -> Confirmada)");
        System.out.println("2. COMENZAR (Confirmada -> En Progreso)");
        System.out.println("3. COMPLETAR (En Progreso -> Completada)");
        System.out.println("4. CANCELAR (Cualquier estado -> Cancelada)");
        System.out.print("Seleccione acción: ");
    }
    
    public void mostrarTransicionesValidas() {
        System.out.println("Transiciones válidas:");
        System.out.println(" - PROGRAMADA -> CONFIRMADA");
        System.out.println(" - CONFIRMADA -> EN_PROGRESO"); 
        System.out.println(" - EN_PROGRESO -> COMPLETADA");
        System.out.println(" - Cualquier estado -> CANCELADA");
    }
    
    public void mostrarMenuReportes() {
        System.out.println("\n--- REPORTES Y ANÁLISIS ---");
        System.out.println("1. Reporte de Personal");
        System.out.println("2. Nómina Total");
        System.out.println("3. Nómina por Departamento");
        System.out.println("4. Análisis Financiero");
        System.out.println("5. Reporte de Eficiencia");
        System.out.println("6. Historial de Reagendamientos");
        System.out.println("7. Volver al Menú Principal");
        System.out.print("Seleccione opción: ");
    }
    
    public void mostrarMenuEspecialidades() {
        System.out.println("\n--- ESPECIALIDADES ---");
        System.out.println("1. Terapeuta");
        System.out.println("2. Enfermero");
        System.out.println("3. Radiólogo");
        System.out.println("4. Farmacéutico");
        System.out.print("Seleccione especialidad: ");
    }
    
    // Métodos para capturar entrada del usuario
    public int pedirOpcion() {
        try {
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            return opcion;
        } catch (Exception e) {
            scanner.nextLine(); // Limpiar buffer en caso de error
            return -1;
        }
    }
    
    public String pedirNombre() {
        System.out.print("Ingrese nombre: ");
        return scanner.nextLine().trim();
    }
    
    public String pedirDepartamento() {
        System.out.print("Ingrese departamento: ");
        return scanner.nextLine().trim();
    }
    
    public int pedirId() {
        System.out.print("Ingrese el ID: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();
            return id;
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }
    
    public int pedirAñosExperiencia() {
        System.out.print("Ingrese años de experiencia: ");
        try {
            int años = scanner.nextInt();
            scanner.nextLine();
            return años;
        } catch (Exception e) {
            scanner.nextLine();
            return 0;
        }
    }
    
    // Métodos específicos para cada especialidad
    public String pedirTipoTerapia() {
        System.out.print("Ingrese tipo de terapia: ");
        return scanner.nextLine().trim();
    }
    
    public int pedirDuracionSesion() {
        System.out.print("Ingrese duración de sesión (minutos): ");
        try {
            int duracion = scanner.nextInt();
            scanner.nextLine();
            return duracion;
        } catch (Exception e) {
            scanner.nextLine();
            return 0;
        }
    }
    
    public String pedirEquipoRadiologo() {
        System.out.print("Ingrese equipo del radiólogo: ");
        return scanner.nextLine().trim();
    }
    
    public int pedirTarifa() {
        System.out.print("Ingrese tarifa: ");
        try {
            int tarifa = scanner.nextInt();
            scanner.nextLine();
            return tarifa;
        } catch (Exception e) {
            scanner.nextLine();
            return 0;
        }
    }
    
    public int pedirLimitePrescripciones() {
        System.out.print("Ingrese límite de prescripciones: ");
        try {
            int limite = scanner.nextInt();
            scanner.nextLine();
            return limite;
        } catch (Exception e) {
            scanner.nextLine();
            return 0;
        }
    }
    
    public String pedirLicencia() {
        System.out.print("Ingrese tipo de licencia: ");
        return scanner.nextLine().trim();
    }
    
    public boolean pedirTurnoNoche() {
        System.out.print("¿Turno nocturno? (s/n): ");
        String respuesta = scanner.nextLine().trim().toLowerCase();
        return respuesta.equals("s") || respuesta.equals("si");
    }
    
    public int pedirNivelCertificacion() {
        System.out.print("Ingrese nivel de certificación (1-5): ");
        try {
            int nivel = scanner.nextInt();
            scanner.nextLine();
            return nivel;
        } catch (Exception e) {
            scanner.nextLine();
            return 1;
        }
    }
    
    // Métodos para gestión de citas
    public String pedirNombrePaciente() {
        System.out.print("Ingrese nombre del paciente: ");
        return scanner.nextLine().trim();
    }
    
    public String pedirTipoCita() {
        System.out.print("Ingrese tipo de cita: ");
        return scanner.nextLine().trim();
    }
    
    /**
     * Captura fecha y hora con formato dd/MM/yyyy HH:mm
     */
    public Calendar pedirFechaHora() {
        System.out.println("Ingrese fecha y hora (formato: dd/MM/yyyy HH:mm): ");
        System.out.println("Ejemplo: 25/12/2024 14:30");
        System.out.print("Fecha: ");
        String fechaStr = scanner.nextLine();
        
        try {
            String[] partes = fechaStr.split(" ");
            String[] fechaPartes = partes[0].split("/");
            String[] horaPartes = partes[1].split(":");
            
            Calendar calendar = Calendar.getInstance();
            calendar.set(
                Integer.parseInt(fechaPartes[2]),
                Integer.parseInt(fechaPartes[1]) - 1, // Mes empieza en 0
                Integer.parseInt(fechaPartes[0]),
                Integer.parseInt(horaPartes[0]),
                Integer.parseInt(horaPartes[1])
            );
            return calendar;
        } catch (Exception e) {
            System.out.println("Formato inválido. Usando fecha actual.");
            return Calendar.getInstance();
        }
    }
    
    // Métodos de visualización
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
    
    public void mostrarError(String mensaje) {
        System.out.println("ERROR: " + mensaje);
    }
    
    public void mostrarExito(String mensaje) {
        System.out.println(mensaje);
    }
    
    public void mostrarLista(List<String> items) {
        if (items.isEmpty()) {
            System.out.println("No hay elementos para mostrar.");
            return;
        }
        
        System.out.println("\n--- RESULTADOS ---");
        for (String item : items) {
            System.out.println("> " + item);
        }
        System.out.println("------------------");
    }
    
    public void mostrarTrabajadores(List<Trabajador> trabajadores) {
        if (trabajadores.isEmpty()) {
            System.out.println("No hay trabajadores para mostrar.");
            return;
        }
        
        System.out.println("\n--- PERSONAL DISPONIBLE ---");
        for (Trabajador t : trabajadores) {
            System.out.println("• " + t.toString());
        }
    }
    
    public void mostrarDespedida() {
        System.out.println("\n¡Gracias por usar el Sistema Hospitalario!");
    }
    
    public void cerrar() {
        scanner.close();
        System.out.println("Recursos liberados.");
    }
    
    public int pedirDuracionCita()
    {
        System.out.print("Ingrese duración de la cita (minutos): ");
        try {
            int duracion = scanner.nextInt();
            scanner.nextLine();
            if (duracion <= 0) {
                System.out.println("Duración inválida. Usando 30 minutos por defecto.");
                return 30;
            }
            return duracion;
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Entrada inválida. Usando 30 minutos por defecto.");
            return 30;
        }
    }
    
    public void mostrarMenuEstadosCita() {
        System.out.println("\n--- LISTAR CITAS POR ESTADO ---");
        System.out.println("1. PROGRAMADA");
        System.out.println("2. CONFIRMADA");
        System.out.println("3. EN PROGRESO");
        System.out.println("4. COMPLETADA");
        System.out.println("5. CANCELADA");
        System.out.println("6. REAGENDADA");
        System.out.print("Seleccione estado: ");
    }
}