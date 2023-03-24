public class GuardiaTren extends Guardia {
    public GuardiaTren(Aeropuerto aeropuerto, Tiempo tiempo){
        super(aeropuerto, tiempo);
    }
    
    public void run() {

        while(true){
            try {
                
                System.out.println("El guardia del tren espera para empezar el recorrido");
                this.aeropuerto.empezarRecorrido();
                
                this.aeropuerto.terminarRecorrido();
                System.out.println("El guardia del tren termina el recorrido del tren");
            }
            catch (Exception err) {
                System.out.println(err.getMessage());
            }
            
        }
        

    }

}
