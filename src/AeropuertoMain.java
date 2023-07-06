public class AeropuertoMain {
    public static void main(String[] args) {
        try {
            Tiempo tiempo= new Tiempo(10);


            Aeropuerto aeropuerto= new Aeropuerto(3, tiempo );
            

            Reloj reloj= new Reloj(tiempo, aeropuerto );

            Pasajero pasajero[]= new Pasajero[20]; 

            Pasajero pasajeroTarde[]= new Pasajero[20]; 

            

            
            
            for(int i=0; i<pasajero.length; i++) {
                pasajero[i]=new Pasajero(aeropuerto, tiempo);
                
            }

            for(int i=0; i<pasajeroTarde.length; i++) {
                
                pasajeroTarde[i]=new Pasajero(aeropuerto, tiempo);
            }

            reloj.start();
            //guardiaAeropuerto.start();
            
            for(int i=0; i<pasajero.length; i++) {
                pasajero[i].start();
            }
            Thread.sleep(100000);
            for(int i=0; i<pasajero.length; i++) {
                pasajeroTarde[i].start();
            }
            
        }
        catch (Exception err) {
            System.out.println(err.getMessage());
        }
        
        


        

    }    
}
/*

*/