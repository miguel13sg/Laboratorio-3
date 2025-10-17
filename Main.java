/**
 * Clase principal que inicia el Sistema de Gestión Hospitalaria
 * Punto de entrada de la aplicación
 */
public class Main {
    /**
     * Método main - punto de inicio del programa
     * @param args argumentos de línea de comandos (no usados)
     */
    public static void main(String[] args)   
    {
        // Crear componentes del sistema MVC
        Vista vista = new Vista();
        Controlador controlador = new Controlador(vista);
        
        // Iniciar el sistema hospitalario
        controlador.iniciarSistema();
    }
}