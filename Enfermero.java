/**
 * Representa un enfermero en el sistema hospitalario
 * Extiende la clase base Trabajador con especializaciones propias
 */
public class Enfermero extends Trabajador{
    private boolean turnoNoche;
    private int nivelCertificacion;
    private int extraNoche;
    private int extraCertificacion;
    
    /**
     * Constructor para crear un nuevo enfermero
     */
    public Enfermero(int id, String name, String departamento, int añosExperiencia, boolean turnoNoche, int nivelCertificacion){
        super(id, name, departamento, añosExperiencia);
        this.turnoNoche = turnoNoche;
        this.nivelCertificacion = nivelCertificacion;
        this.extraNoche = 500;        // Bono fijo por turno nocturno
        this.extraCertificacion = 100; // Bono por cada nivel de certificación
    }
    
    /**
     * Calcula el bono por turno nocturno
     * @return 500 si es turno nocturno, 0 si es diurno
     */
    public int getExtraNoche()
    {
        if(turnoNoche == true)
        {
            return extraNoche;
        }
        else
        {
            return 0;
        }
    }
    
    /**
     * Calcula bono por nivel de certificación
     * @return nivel certificación * 100
     */
    public int getBonoCerti()
    {
        return nivelCertificacion * extraCertificacion;
    }
    
    /**
     * Suma todos los extras del enfermero
     */
    private int getTotalExtras()
    {
        return getExtraNoche() + getBonoCerti();
    }
    
    // Getters básicos
    public int getNivelCertificacion()
    {
        return nivelCertificacion;
    }

    public boolean haceTurnoNoche()
    {
        return turnoNoche;
    }

    /**
     * Calcula salario total con polimorfismo
     * @return salario base + todos los extras
     */
    @Override
    public int getSalarioTotal()
    {
        return getSalarioBase() + getTotalExtras();
    }
    
    /**
     * Representación en texto del enfermero
     */
    @Override
    public String toString() {
        return String.format("Enfermero[ID = %d, Nombre = %s, Turno = %s, Certificación = %d, Salario = %d]",
            getId(), getName(), haceTurnoNoche() ? "Nocturno" : "Diurno", getNivelCertificacion(), getSalarioTotal());
        }
}