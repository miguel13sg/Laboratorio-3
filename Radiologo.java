/**
 * Representa un radiólogo en el sistema hospitalario
 * Especializado en estudios con equipos médicos
 */
public class Radiologo extends Trabajador{
    private String equipo;
    private int tarifa;
    private int estudios;
    
    /**
     * Constructor para crear un nuevo radiólogo
     */
    public Radiologo(int id, String name, String departamento, int añosExperiencia, String equipo, int tarifa){
        super(id, name, departamento, añosExperiencia);
        this.equipo = equipo;    // Equipo médico certificado para usar
        this.tarifa = tarifa;    // Tarifa por estudio realizado
        this.estudios = 0;       // Contador de estudios realizados
    }
    
    /**
     * Registra un nuevo estudio realizado
     */
    public void agregarEstudio()
    {
        estudios++;
    }
    
    // Getters para acceso a los atributos
    public int getEstudios()
    {
        return estudios;
    }
    
    public int getTarifa()
    {
        return tarifa;
    }
    
    public String getEquipo()
    {
        return equipo;
    }

    /**
     * Calcula salario total: base + comisión por estudios
     * @return salario base + (estudios * tarifa)
     */
    @Override
    public int getSalarioTotal()
    {
        return getSalarioBase() + (estudios * tarifa);
    }

    /**
     * Representación en texto del radiólogo
     */
    @Override
    public String toString() {
        return String.format("Radiólogo[ID = %d, Nombre = %s, Equipo = %s, Estudios = %d, Salario = %d]",
                getId(), getName(), getEquipo(), getEstudios(), getSalarioTotal());
    }
}