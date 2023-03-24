public class GuardiaSeguridad extends Guardia {
    

    public GuardiaSeguridad(PuestoAerolinea puestoAeropuerto, Tiempo tiempo){
        super(puestoAeropuerto, tiempo);
    }
    
    public void run(){
        while(true){
            try {
                this.aerolinea.despertarPasajero();
                System.out.println("El guardia "+ id+" despierta a los pasajeros ");
            }
            catch (InterruptedException err) {
                System.out.println(err.getMessage());
            }
        }
    }

}
