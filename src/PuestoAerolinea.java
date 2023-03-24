import java.util.concurrent.Semaphore;



import java.util.concurrent.LinkedBlockingQueue;




public class PuestoAerolinea {
    private String nombre;
    private Semaphore puesto, guardia;
    private TablaVueloAerolinea tablaVuelo;


    private Tiempo tiempo;
     
    private LinkedBlockingQueue<Semaphore> colaEspera;


    

    public PuestoAerolinea(String nombre, TablaVueloAerolinea tablaVuelo, Tiempo tiempo){
        this.nombre = nombre;

        //tablaVuelo
        this.tablaVuelo = tablaVuelo;

        //SolicitarEntrar
        colaEspera= new LinkedBlockingQueue<Semaphore>();

        this.tiempo= tiempo;

        puesto= new Semaphore(3, true);


        //despertar pasajero
        guardia= new Semaphore(0);
    }
   
    
    //COMO LINKEDBLOCKINGQUEUE PROPORSIONA UNA COLA SUS METODOS YA SINCRONIZADOS NO NOS PREOCUPAMOS POR SU ACCESO

    
    public boolean solicitarEntrar() throws InterruptedException{

        return colaEspera.isEmpty() && !tablaVuelo.isSinPasajes() && puesto.tryAcquire() ; //evaluacion por corto circuito
    }

    public void esperarYEntrar() throws InterruptedException{
        
        //Este metodo crea un semaforo que es colocado en la cola de espera y se manda a dormir al pasajero al mismo
        Semaphore sem= new Semaphore(0);
        colaEspera.put(sem);
        sem.acquire();

    
        puesto.acquire();//el pasajero es atendido
    }



    public void despertarPasajero() throws InterruptedException{
        //Metodo ejecutado por el guardia para despertar al pasajero que este primero en la cola cuando el puesto de atencion se lo pida

        guardia.acquire();
        if(!colaEspera.isEmpty()){
            ((Semaphore)colaEspera.poll()).release();
        }
        
    } 

    public Pasaje hacerCheckIn() throws InterruptedException{ 
        //En este metodo se le asigna un vuelo al pasajero        
        System.out.println("Se vende un vuelo al pasajero, si todavia quedan");

        return tablaVuelo.venderPasaje();


        
    }
    public void salirPuesto() throws InterruptedException{        
      
        puesto.release();//liberamos un lugar en el puesto
        guardia.release();//despertamos al guardia


    }

    //METODOS GET SET
    public String getNombre(){
        return nombre;
    }


}
