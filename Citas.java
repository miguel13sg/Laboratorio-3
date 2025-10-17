import java.util.Calendar;

public class Citas {
    private int idCita;
    private String namePaciente;
    private Trabajador trabajador;
    private Calendar date;
    private String tipo; 
    private EstadoCita estado;
    private int duracionMinutos;

    // Constructor: Crea una nueva cita con todos sus datos
    public Citas(int idCita, String namePaciente, Trabajador trabajador, Calendar date, String tipo, EstadoCita estado, int duracionMinutos) {
        this.idCita = idCita;
        this.namePaciente = namePaciente;
        this.trabajador = trabajador;
        this.date = date;
        this.tipo = tipo;
        this.estado = estado;
        this.duracionMinutos = duracionMinutos;
    }
    
    // Getter: Obtiene la duración en minutos de la cita
    public int getDuracionMinutosCita()
    {
        return duracionMinutos;
    }
    
    // Cambia el estado de la cita a CONFIRMADA
    public void confirmar()
    {
        this.estado = EstadoCita.CONFIRMADA;
    }
    
    // Cambia el estado de la cita a EN_PROGRESO
    public void comenzar()
    {
        this.estado = EstadoCita.EN_PROGRESO;
    }
    
    // Cambia el estado de la cita a COMPLETADA
    public void completar()
    {
        this.estado = EstadoCita.COMPLETADA;
    }
    
    // Cambia el estado de la cita a CANCELADA
    public void cancelar()
    {
        this.estado = EstadoCita.CANCELADA;
    }
    
    // Reagenda la cita a una nueva fecha y cambia su estado
    public void reagendar(Calendar nuevaFecha)
    {
        this.date = nuevaFecha;
        this.estado = EstadoCita.REAGENDADA;
    }
    
    // Getter: Obtiene el ID único de la cita
    public int getIdCita()
    {
        return idCita;
    }
    
    // Getter: Obtiene el nombre del paciente
    public String getNamePaciente()
    {
        return namePaciente;
    }
    
    // Getter: Obtiene el trabajador asignado a la cita
    public Trabajador getTrabajador()
    {
        return trabajador;
    }
    
    // Getter: Obtiene la fecha y hora programada de la cita
    public Calendar getFechaHora()
    {
        return date;
    }
    
    // Getter: Obtiene el tipo de cita (consulta, terapia, etc.)
    public String getTipo()
    {
        return tipo;
    }
    
    // Getter: Obtiene el estado actual de la cita
    public EstadoCita getEstadoCita()
    {
        return estado;
    }
    
    // Representación en texto de la cita para mostrar en reportes
    @Override
    public String toString() {
        return String.format("Cita[ID = %d, Paciente = %s, Trabajador = %s, Tipo = %s, Duración = %d min, Estado = %s]",
                idCita, namePaciente, trabajador.getName(), tipo, duracionMinutos, estado);
    }
}