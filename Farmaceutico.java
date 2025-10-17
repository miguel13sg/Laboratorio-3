/**
 * Representa un farmacéutico en el sistema hospitalario
 * Maneja prescripciones y despacho de medicamentos
 */
public class Farmaceutico extends Trabajador{
    private int preescripciones;
    private String licencia;
    private int despachos;

    /**
     * Constructor para crear un nuevo farmacéutico
     */
    public Farmaceutico(int id, String name, String departamento, int añosExperiencia, int preescripciones, String licencia){
        super(id, name, departamento, añosExperiencia);
        this.preescripciones = preescripciones;  // Límite diario de prescripciones
        this.licencia = licencia;                // Licencia para sustancias controladas
        this.despachos = 0;                      // Contador de despachos realizados
    }
    
    /**
     * Procesa una prescripción médica
     * Reduce prescripciones disponibles y aumenta despachos
     */
    public void darPreescripcion()
    {
        if(preescripciones>0)
        {
            preescripciones--;
            despachos++;
        }
    }
    
    // Getters para acceso a los atributos
    public int getDespachos()
    {
        return despachos;
    }
    
    public int getPreescripciones()
    {
        return preescripciones;
    }
    
    public String getLicencia()
    {
        return licencia;
    }

    /**
     * Calcula salario total: base + comisión por despachos
     * @return salario base + (despachos * 200)
     */
    @Override
    public int getSalarioTotal()
    {
        return getSalarioBase() + (despachos * 200);  // 200 por cada despacho
    }

    /**
     * Representación en texto del farmacéutico
     */
    @Override
    public String toString() {
        return String.format("Farmacéutico[ID = %d, Nombre = %s, Licencia = %s, Prescripciones = %d, Despachos = %d, Salario = %d]",
                getId(), getName(), licencia, preescripciones, despachos, getSalarioTotal());
    }
}