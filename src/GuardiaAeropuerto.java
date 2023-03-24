public class GuardiaAeropuerto extends Guardia{
    
    public GuardiaAeropuerto(Aeropuerto aeropuerto, Tiempo tiempo){
        super(aeropuerto, tiempo);
    }
    public void run(){
        while(true){
            try {

               // aeropuerto.abrirAeropuerto();
                
                System.out.println("El guardia del aeropuerto abre el aeropuerto");
            }
            catch (Exception err) {
                System.out.println(err.getMessage());
            }
        }
    }

}
