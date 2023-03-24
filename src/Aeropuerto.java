
import java.util.concurrent.Semaphore;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

//Utilizamos locks para restringir el ingreso del aeropuerto
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Aeropuerto {
    
    private PuestoAerolinea colAerolinea[];
    private GuardiaSeguridad colGuardia[];
    private Terminal colTerminal [];
    private int espEntrar, personasEnTren; 
    private final int CANT_LUGARES_TREN= 10; 
    private static final int HORA_CIERRE= 18;
    private static final int HORA_APERTURA= 6;
    private Semaphore  entrar, lugaresTren, guardiaTren;
    private Semaphore  mutexEspEntrar, mutexPEN;
    


    //tiempo
    private Tiempo tiempo;
        



    //datos para inicializar a los demas objetos pasivos del aeropuerto

    private final int arrHorario[]= {10,13,16,18};
    private final String nombreAerolinea[]= new String[]{"Aerolineas Argentinas", "AirBang", "Fly", "AliExpress", "Despegar"};
    private final char idTerminal[]= new char[]{'A', 'B', 'C', 'D','E'};
    private final int colEmbarque[][]= new int[][]{ {0,1,2},{6,7,8},{12,13,14},{18,19,20},{22,23,24}};  
    
    private Random r;
    private CyclicBarrier tren; //variable utilizada para bloquear a los pasajeros hasta que se llene el tren
    private Terminal paradaTren; 
    private TablaVueloAeropuerto tablaVuelo;

    private Lock lockTren;
    private char paradaTrenChar; 
    private Condition colaEsperarBajar;

    public Aeropuerto(int cantAerolinea, Tiempo paramTiempo) {
        
        r= new Random();
        

        // Entrar Aeropuerto y Puesto de informe
        entrar= new Semaphore(0);
        mutexEspEntrar= new Semaphore(1);
        espEntrar= 0;

        this.tiempo= paramTiempo;
        
        //tren
        
        paradaTren= null;
        
        lugaresTren= new Semaphore(CANT_LUGARES_TREN);
        mutexPEN= new Semaphore(1);
        guardiaTren= new Semaphore(0);
        tren= new CyclicBarrier(CANT_LUGARES_TREN);
        this.lockTren= new ReentrantLock(true);
        colaEsperarBajar= lockTren.newCondition();
        paradaTrenChar= 'x';

       
        this.personasEnTren=0;

        //mutex hora
       // mutexHora= new Semaphore(1);

        //Terminal

        
        this.inicializarTerminal(paramTiempo);

        //tablaVuelo
        tablaVuelo= new TablaVueloAeropuerto();
        
        
        //aerolineas
        this.inicializarColAerolineaYGuardia(cantAerolinea, paramTiempo);

    
      
        

        
        
        
       

        

    }
    // METODO EJECUTADO POR EL RELOJ
    /* 
    public void reloj() throws InterruptedException{
        //El reloj pasa a la siguiente hora
        mutexHora.acquire();
        hora++;
        System.out.println("El reloj marca las "+ hora+ " hs");
        if(hora == 6){ //SI ES LA HORA DE ABRIR
            guardia.release();  //EL RELOJ DESPIERTA AL GUARDIA
        }
        if(hora == 24){
            
            hora= 0;
        }
        mutexHora.release();
    }*/

    //METODO EJECUTADO POR EL GAURDIA SEGURIDAD AEROPUERTO
    public void abrirAeropuerto() throws InterruptedException{
        //En este metodo el guardia del aeropuerto abre la puerta a los pasajeros esperando a entrar al puesto de informe
        
        if(tiempo.getTiempo() == HORA_APERTURA){ //SI ES LA HORA DE ABRIR
            mutexEspEntrar.acquire();  
            entrar.release(espEntrar); // dejamos entrar a la gente al aeropuerto
            espEntrar=0;                // reseteamos el contador de gente esperando para entrar al aeropuerto
            mutexEspEntrar.release();    
        }

    }

    
    //METODOS EJECUTADOS POR EL PASAJEROS
    public void entrarAeropuerto() throws InterruptedException{
        /* EN ESTE METODO LOS PASAJEROS INTENTA ENTRAR AL AEROPUERTO */
        //mutexHora.acquire();
        if(tiempo.getTiempo() < HORA_APERTURA || tiempo.getTiempo() > HORA_CIERRE){ // SI EL AEROPUERTO ESTA CERRADO LOS PASAJEROS QUEDAN BLOQUEADOS ESPERANDO A QUE VUELVA A ABRIR

            mutexEspEntrar.acquire();
            espEntrar++;
            mutexEspEntrar.release();
            
          //  mutexHora.release(); //liberamos el semaforo
            
            entrar.acquire();
            
            //mutexHora.acquire(); //lo volvemos a agarrar 
        }   
        
        //mutexHora.release();
    }
    
    public PuestoAerolinea entrarPuestoInforme() throws InterruptedException{
        

        return colAerolinea[r.nextInt(colAerolinea.length)];
        
    }
    
    public void despertarGenteTerminal(){
        for (int i = 0; i< colTerminal.length; i++){
            colTerminal[i].despertarGente();
        }
    }


    // PARTE DEL TREN

    public void tomarTren() throws  BrokenBarrierException, InterruptedException {
        //Utilizamo una CyclicBarrier para simular el tren
        lugaresTren.acquire();
        mutexPEN.acquire();
        personasEnTren++;
        
        if(personasEnTren == CANT_LUGARES_TREN){
            //despertamos al chofer
            guardiaTren.release();      
        }

        mutexPEN.release();
        tren.await(); //la persona se sube al tren
    
        
    }

    public Terminal bajarTren(char idTerminal) throws InterruptedException{
    //ESTE METODO ESTA TARDANDO MUCHO

        lockTren.lock();
        while(paradaTrenChar != idTerminal){
            colaEsperarBajar.await();
        }
        lockTren.unlock();
        
        mutexPEN.acquire();
        this.personasEnTren--;
        if(this.personasEnTren == 0){
            guardiaTren.release(); //liberamos al guardia para que reset el tren
        }
        mutexPEN.release();       

        return paradaTren; 
    } 

    public void empezarRecorrido() throws InterruptedException, BrokenBarrierException{
        //--------------EL RECORRIDO SE SIMULA RECORRIENDO LA COLECCION DE TERMINALES Y 

        guardiaTren.acquire(); // 
        System.out.println("El guardia del tren empieza el recorrido del tren");
        for(int i=0; i<colTerminal.length; i++){
            Thread.sleep(3000); // simulamos el viaje en tren
            this.paradaTren = colTerminal[i]; // asignamos la terminal en la que para el tren
            
            lockTren.lock();
            this.paradaTrenChar= colTerminal[i].getId();
            colaEsperarBajar.signalAll();
            lockTren.unlock();

            System.out.println("El Tren llega a terminal "+ paradaTren.getId());
            
            Thread.sleep(30000); // aguardamos a que terminen de bajar los pasajeros
            System.out.println("Cantidad de personas en tren " + personasEnTren);
            
        }

        
    }
    public void terminarRecorrido() throws InterruptedException{
        guardiaTren.acquire(); // detenemos al guardia hasta se bajen todos los pasajeros del tren
        this.paradaTrenChar= 'x';
        tren.reset(); //reiniciamos el contador de pasajeros de tren
        lugaresTren.release(CANT_LUGARES_TREN);
    }

    // METODOS PROPIOS DE AEROPUERTO
    private void inicializarColAerolineaYGuardia(int n, Tiempo paramTiempo){
        
        
        // Este metodo inicializa crea los puesto de aerolinea y los guardias que controlan la cola de espera
        colAerolinea= new PuestoAerolinea [n];
        colGuardia= new GuardiaSeguridad [n];

        //la tabla de vuelo es creada por el aeropuerto 



        for(int i = 0; i < n; i++){


            TablaVueloAerolinea tablaAux= new TablaVueloAerolinea(this.arrHorario, nombreAerolinea[i], colTerminal[i], paramTiempo);

            colAerolinea[i]= new PuestoAerolinea(nombreAerolinea[i], tablaAux, paramTiempo); 

            this.tablaVuelo.AgrearTablaVuelo(tablaAux);

            colGuardia[i]= new GuardiaSeguridad(colAerolinea[i], paramTiempo);
            colGuardia[i].start();
        }
    }

    private void inicializarTerminal(Tiempo paramTiempo){
        
        colTerminal= new Terminal[3]; //la cantidad no es importante
        for(int i=0; i<3; i++){
            colTerminal[i]= new Terminal(idTerminal[i], colEmbarque[i], paramTiempo);

        }
    } 

    public void testString(){

        for(int i=0; i< colAerolinea.length;i++){
            System.out.println(colAerolinea[i].toString());
        }
        for(int i=0; i< colAerolinea.length;i++){
            System.out.println(tablaVuelo.mostrarVuelos());
        }
        System.out.println();
    }
    
    

}
