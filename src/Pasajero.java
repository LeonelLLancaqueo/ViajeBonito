import java.util.concurrent.BrokenBarrierException;

public class Pasajero extends Persona {
   
    private Pasaje pasaje;
    private Terminal terminal; // donde el pasajero


    public Pasajero(Aeropuerto aeropuerto, Tiempo tiempo) {
        super(aeropuerto, tiempo);
        this.pasaje= null;
    }


    public void run() {

        
        
        try {


            //Puesto informe entrada del aeropuerto

            System.out.println("El pasajero "+id+" quiere ingresar al aeropuerto ");
            aeropuerto.entrarAeropuerto();
            System.out.println("El pasajero "+id+" entro al aeropuerto ");
            Thread.sleep(5000);
            aerolinea= aeropuerto.entrarPuestoInforme();
            System.out.println("El pasajero "+id+"  se le asigno la aerolinea "+ aerolinea.getNombre());
            
            //Puesto de la aerolinea
            
            if(!aerolinea.solicitarEntrar()){
                System.out.println("El pasajero "+id+" ingresa a la cola de espera hasta ser despertado y atendido");
                aerolinea.esperarYEntrar();
                System.out.println("El pasajero "+id+" sale de la cola y es atendido");
                

            }

            System.out.println("El pasajero "+id+" pasa a a hacer el check in");        
            Thread.sleep(5000);//simulamos tiempo
            this.pasaje= aerolinea.hacerCheckIn();
            System.out.println("El pasajero "+id+" sale del puesto de atencion con su pasaje: \n" + pasaje.toString());
            aerolinea.salirPuesto();
            try {
            
                //  TREN
                Thread.sleep(5000);
                System.out.println("El pasajero "+id+" intenta subirse al tren");
                aeropuerto.tomarTren();
                System.out.println("El pasajero "+id+" consiguio lugar en el tren");
            
            }
            catch (BrokenBarrierException err) {
                System.out.println(err.getMessage());
            }

            terminal= aeropuerto.bajarTren(pasaje.getIdTerminal()); 
            System.out.println("El pasajero "+id+" se bajo en la terminal "+ terminal.getId());
        
            //   TERMINAL
            
            Thread.sleep(1000);
            System.out.println("El pasajero "+id+" Esta yendo al free Shop");
            FreeShop freeShop= terminal.irAFreeShop();
            boolean yaCompro= false;
            while(pasaje.getHoraSalida() >=  tiempo.getTiempo()-3 && !yaCompro){ //PREGUNTAMO SI EL TIENE TIEMPO ANTES DE TENER QUE TOMAR SU VUELO O SI YA ENTRO Y COMPRO
                    
                if(freeShop.entrarYMirarProductos()){ //SI HAY LUGAR EN EL FREE SHOP

                    System.out.println("El pasajero "+id+" entro al free Shop y esta mirando productos");
                    Thread.sleep(2000); //simulamos el tiempo

                    if(pasaje.getHoraSalida() >=  tiempo.getTiempo()-2){ //PREGUNTAMO SI EL TIENE TIEMPO ANTES DE TENER QUE TOMAR SU VUELO

                        System.out.println("El pasajero "+id+" decide comprar un producto y va hacia la caja");
                        freeShop.comprarProducto();
                        Thread.sleep(1000); //simulamos el tiempo
                        System.out.println("El pasajero "+id+" termino de comprar y libera la caja");
                        freeShop.terminarDeComprar();
                        yaCompro= true;   
                    
                    }
                }else{
                    Thread.sleep(3000);
                }
                System.out.println("El pasajero "+id+" se va del freeShop");
                freeShop.salir();
                
            }
            Thread.sleep(2000);
            System.out.println("El pasajero "+id+" va a la sala de embarque general a esperar a embarcar");
            Embarque embarque= terminal.esperarEmbarcar(pasaje);

            System.out.println("El pasajero "+id+" es llamado para embarcar");
            embarque.embarcar();

            System.out.println("El pasajero "+id+" termino de embarcar y tomo su vuelo");



        }
        catch (InterruptedException err) {
            System.out.println(err.getMessage());
        }

        


    }

}
