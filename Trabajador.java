/**
 * Clase abstracta que representa un trabajador médico base
 * Define la estructura común para todas las especializaciones médicas
 */
public abstract class Trabajador{
    protected int id;
    protected String name;
    protected String departamento;
    protected int añosExperiencia;
    protected int salarioBase;

    /**
     * Constructor base para todos los trabajadores médicos
     */
    public Trabajador(int id, String name, String departamento, int añosExperiencia){
        this.id = id;
        this.name = name;
        this.departamento = departamento;
        this.añosExperiencia = añosExperiencia;
        this.salarioBase = 8000;  // Salario base común para todos
    }

    /**
     * Método abstracto - cada especialización implementa su propio cálculo
     */
    public abstract int getSalarioTotal();

    // Getters básicos para atributos comunes
    public String getName()
    {
        return name;
    }

    public int getSalarioBase()
    {
        return salarioBase;
    }

    public int getId()
    {
        return id;
    }

    public String getDepartamento()
    {
        return departamento;
    }
    
    public int getAñosExperiencia()
    {
        return añosExperiencia;
    }
    
    /**
     * Verifica disponibilidad del trabajador
     * @return true por defecto - las subclases pueden sobrescribir
     */
    public boolean isDisponible()
    {
        return true;
    }
}