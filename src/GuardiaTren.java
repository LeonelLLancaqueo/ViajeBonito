public class GuardiaTren extends Guardia {
    
    private Tren tren;
    public GuardiaTren(Tiempo tiempo, Tren tren){
        super(tiempo);
        this.tren=tren;
    }
    
    public void run() {

        while(true){
            try {


                


                //considerar que el tren pueda salir sin que se haya llenado el tren
                System.out.println("El guardia del tren espera para empezar el recorrido");
                Thread.sleep(3000);

                
                this.tren.empezarRecorrido();
                Thread.sleep(3000);
                this.tren.terminarRecorrido();
                System.out.println("El guardia del tren termina el recorrido del tren");
            }
            catch (Exception err) {
                System.out.println(err.getMessage());
            }
            
        }
        

    }

}
