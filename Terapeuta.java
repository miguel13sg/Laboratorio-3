/**
 * Representa un terapeuta en el sistema hospitalario
 * Especializado en diferentes tipos de terapia
 */
public class Terapeuta extends Trabajador{
    private String tipoTerapia;
    private int duracion;
    private int extra;
    
    /**
     * Constructor para crear un nuevo terapeuta
     */
    public Terapeuta(int id, String name, String departamento, int añosExperiencia, String tipoTerapia, int duracion){
        super(id, name, departamento, añosExperiencia);
        this.tipoTerapia = tipoTerapia;  // Tipo de terapia especializada
        this.duracion = duracion;        // Duración promedio de sesión en minutos
        this.extra = 300;                // Bono por cada hora de terapia
    }

    /**
     * Calcula bono basado en duración de sesiones
     * @return (duración en horas) * 300
     */
    private int getExtra()
    {
        return (duracion / 60) * extra;  // 300 por cada hora completa
    }

    public String getTipoTerapia()
    {
        return tipoTerapia;
    }
    
    /**
     * Representación en texto del terapeuta
     */
    @Override
    public String toString() {
        return String.format("Terapeuta[ID = %d, Nombre = %s, Terapia = %s, Duración = %d min, Salario = %d]",
                           getId(), getName(), tipoTerapia, duracion, getSalarioTotal());
    }
    
    /**
     * Calcula salario total: base + bono por duración
     * @return salario base + extra si la duración es positiva
     */
    @Override
    public int getSalarioTotal()
    {
        if(duracion > 0)
        {
            return getSalarioBase() + getExtra();
        }
        else{
            return getSalarioBase();  // Solo salario base sin sesiones
        }
    }
}