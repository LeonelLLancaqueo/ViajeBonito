
import java.util.concurrent.Semaphore;
import java.util.Random;


//Utilizamos locks para restringir el ingreso del aeropuerto con condicion


public class Aeropuerto {
    
    private PuestoAerolinea colAerolinea[];
    private GuardiaSeguridad colGuardia[];
    private Terminal colTerminal [];
     
    private static final int HORA_CIERRE= 18;
    private static final int HORA_APERTURA= 6;
    private int espEntrarAeropuerto;
    
    private Semaphore  mutexEspEntrar, entrarAeropuerto;
    
    private Random r;

    //tiempo
    private Tiempo tiempo;
        



    //datos para inicializar a los demas objetos pasivos del aeropuerto

    private final int horarioTren[]={7,8,9,10,11,12,13,14,15,16,17};
    private final int arrHorario[]= {10,13,16,18};
    private final String nombreAerolinea[]= new String[]{"Aerolineas Argentinas", "AirBang", "Fly", "AliExpress", "Despegar"};
    private final char idTerminal[]= new char[]{'A', 'B', 'C', 'D','E'};
    private final int colEmbarque[][]= new int[][]{ {0,1,2},{6,7,8},{12,13,14},{18,19,20},{22,23,24}};  
    
    
    //TREN
    private Tren tren;
    private GuardiaTren choferTren;


    

    

    public Aeropuerto(int cantAerolinea, Tiempo paramTiempo) {
        
        r= new Random();
        

        // Entrar Aeropuerto y Puesto de informe
        entrarAeropuerto= new Semaphore(0);
        mutexEspEntrar= new Semaphore(1);
        espEntrarAeropuerto= 0;

        this.tiempo= paramTiempo;

        

        //Terminal
        this.inicializarTerminal(paramTiempo);
        
        //Tren

        this.tren= new Tren(colTerminal, tiempo, horarioTren);
        choferTren= new GuardiaTren(paramTiempo, this.tren);
        choferTren.start();
        
        //aerolineas
        this.inicializarColAerolineaYGuardia(cantAerolinea, paramTiempo);

    }


    
    public void abrirAeropuerto() throws InterruptedException{
        //METODO EJECUTADO POR EL GAURDIA SEGURIDAD AEROPUERTO

        //En este metodo el guardia del aeropuerto abre la puerta a los pasajeros esperando a entrar al puesto de informe
        
        if(tiempo.igual(HORA_APERTURA)){ //SI ES LA HORA DE ABRIR
            mutexEspEntrar.acquire();  
            
            entrarAeropuerto.release(espEntrarAeropuerto); // dejamos entrar a la gente al aeropuerto
            espEntrarAeropuerto=0;                // reseteamos el contador de gente esperando para entrar al aeropuerto
            
            mutexEspEntrar.release();    
        }

    }

    
    //METODOS EJECUTADOS POR EL PASAJEROS
    public void entrarAeropuerto() throws InterruptedException{
        /* EN ESTE METODO LOS PASAJEROS INTENTA ENTRAR AL AEROPUERTO */

        if(tiempo.menor(HORA_APERTURA) || !tiempo.menor(HORA_CIERRE)){ 
            // SI EL AEROPUERTO ESTA CERRADO LOS PASAJEROS QUEDAN BLOQUEADOS ESPERANDO A QUE VUELVA A ABRIR

            mutexEspEntrar.acquire();
            espEntrarAeropuerto++;
            mutexEspEntrar.release();
            
            entrarAeropuerto.acquire(); //semaforo donde quedan bloqueados   
        }        
    }
    
    public PuestoAerolinea entrarPuestoInforme() throws InterruptedException{
        
        //RETONAMOS UN PUESTO DE AEROLINEA DE MANERA ALEATORIA
        return colAerolinea[r.nextInt(colAerolinea.length)];
        
    }

    // PARTE DEL TREN
    public Tren entrarTren(){
        return this.tren;
    }
    

    public void despertarGenteTerminal(){
        //DESPERTAMOS A LOS PASAJEROS QUE ESTEN ESPERANDO A EMBARCAR 
        //METODO EJECUTADO POR RELOJ
        for (int i = 0; i< colTerminal.length; i++){ //interamos todas las terminales
            colTerminal[i].despertarGente();
        }
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

        System.out.println();
    }
    
    

}
