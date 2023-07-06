
import java.util.concurrent.Semaphore;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;



public class Tren {

    private int  personasEnTren; 
    private final int CANT_LUGARES_TREN= 10; 
    private Semaphore   lugaresTren, guardiaTren, mutexPEN, semSolTren;   
    
    
    private CyclicBarrier tren; //variable utilizada para bloquear a los pasajeros hasta que se llene el tren
    private Lock lockTren; 
    private Condition colaEsperarBajar;

    private Terminal []colTerminal;
    private Terminal paradaTren;
    private int horario[];
    private Tiempo tiempo;
    
    public Tren(Terminal []colTerminal, Tiempo tiempo, int [] horario){

        //tren

        this.horario= horario;
        this.colTerminal= colTerminal;

        paradaTren= null;
        
        lugaresTren= new Semaphore(CANT_LUGARES_TREN);

        mutexPEN= new Semaphore(1);
        
        guardiaTren= new Semaphore(0);

        semSolTren= new Semaphore(1);

        //sincronizacion para llenar el tren
        
        tren= new CyclicBarrier(CANT_LUGARES_TREN);
        
        //sincronizacion para poder bajar
        this.lockTren= new ReentrantLock(true);
        
        colaEsperarBajar= lockTren.newCondition();
       
        this.personasEnTren=0;
        
        this.tiempo= tiempo;
    }

    
    public void tomarTren() throws  BrokenBarrierException, InterruptedException {
        
        semSolTren.acquire(); //utilizamos para bloquear si el guardia de tren se desperto
        semSolTren.release();
        
        //Utilizamo una CyclicBarrier para simular el tren
        lugaresTren.acquire();
        mutexPEN.acquire();
        personasEnTren++;
        
        /*if(personasEnTren == CANT_LUGARES_TREN){
            //despertamos al chofer
            this.guardiaTren.release();      
        }*/

        mutexPEN.release();
        tren.await(); //la persona se sube al tren
    
        
    }

    public Terminal bajarTren(char idTerminal) throws InterruptedException{
    

        try {
            lockTren.lock();

            colaEsperarBajar.await(); //los hacemos esperar hasta que lleguen a la primera parada
            
            while(!paradaTren.equals(idTerminal)){
                colaEsperarBajar.await();
            }    
        } catch (Exception e) {
            
        }finally{
            lockTren.unlock();
        }

        mutexPEN.acquire();

        this.personasEnTren--;
        /* 
        if(this.personasEnTren == 0){
            guardiaTren.release(); //liberamos al guardia para que reset el tren
        }
        */
        mutexPEN.release();       

        return paradaTren; 
    } 
    
    public void empezarRecorrido() throws InterruptedException, BrokenBarrierException{
        //--------------EL RECORRIDO SE SIMULA RECORRIENDO LA COLECCION DE TERMINALES Y 

        //guardiaTren.acquire(); // 
        semSolTren.acquire();
        lugaresTren.acquire(CANT_LUGARES_TREN-personasEnTren);
         
        System.out.println("El guardia del tren empieza el recorrido del tren");
        
        //SIMULAMOS EL RECORRIDO DEL TREN RECORRIENDO EL ARREGLO DE TERMINALES
        for(int i=0; i<colTerminal.length; i++){
            Thread.sleep(3000); // simulamos el viaje en tren
            this.paradaTren = colTerminal[i]; // asignamos la terminal en la que para el tren
            
            try {
                lockTren.lock();    
                colaEsperarBajar.signalAll(); //despertamos a los pasajeros del tren
            } catch (Exception e) {
                
            }finally{
                lockTren.unlock();
            }
            
            System.out.println("El Tren llega a terminal "+ paradaTren.getId());
            
            Thread.sleep(4000); // aguardamos a que terminen de bajar los pasajeros
            System.out.println("Cantidad de personas en tren " + personasEnTren);
            
        }

    }
    public void terminarRecorrido() throws InterruptedException{
        //METODO EJECUTADO POR EL GUARDIA DE TREN

        //guardiaTren.acquire(); // detenemos al guardia hasta se bajen todos los pasajeros del tren
        
        this.paradaTren= null;
        
        tren.reset(); //reiniciamos la cyclic barrier
    
        lugaresTren.release(CANT_LUGARES_TREN);

        semSolTren.release();
    }
}
