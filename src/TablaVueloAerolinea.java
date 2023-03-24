
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

public class TablaVueloAerolinea {
    
    private ConcurrentHashMap <Integer,Vuelo>hashVuelo;
    private int []arrHorario;
    private int iteradorHorario;
    private boolean sinPasajes;
    private String aerolinea;
    private Semaphore mutexVenderPasaje;
    private Tiempo tiempo;

    public TablaVueloAerolinea(int []arrHorario,String aerolinea, Terminal terminal, Tiempo tiempo ){
        
        hashVuelo=new ConcurrentHashMap<Integer,Vuelo>();
        this.arrHorario= arrHorario;
        
        iteradorHorario = 0;
        this.sinPasajes= false;
        this.aerolinea= aerolinea;

        this.mutexVenderPasaje= new Semaphore(1);

        this.inicializarTabla(arrHorario, aerolinea, terminal);
        //tiempo
        this.tiempo= tiempo;
        
    }

    public void inicializarTabla( int []arrHorario,String aerolinea,Terminal terminal){
        //int horaSalida,String aerolinea, int embarque, char terminal
        try {
            //  ESTO NO SABEMOS SI FUNCIONA
           for(int i=0; i<arrHorario.length; i++){
                hashVuelo.put(arrHorario[i], new Vuelo(arrHorario[i], aerolinea, terminal.getEmbarqueAleatorio(), terminal.getId())) ;
                
            }
          
        } catch (InterruptedException e) {
            System.out.println("interrupted exception");
        }
        
    }


    public String getAerolinea(){
        return this.aerolinea;
    }


    public Pasaje venderPasaje() throws InterruptedException{
        //se venden en orden hasta agotar stock

        mutexVenderPasaje.acquire();

        
        if(tiempo.getTiempo() >= arrHorario[iteradorHorario]-2 ){
            //si quedan menos de dos horas para el vuelo entonces se venden pasajes para el siguiente vuelo
            this.iteradorHorario++;
        }
        
        Vuelo aux= ((Vuelo)hashVuelo.get(this.arrHorario[iteradorHorario])); //obtenemos el vuelo con horario mas temprano
        Pasaje pasajeAux= aux.venderPasaje();
        if(!aux.isStockPasajes()){
            //si no quedan pasajes se venden pasajes para el siguiente vuelo
            this.iteradorHorario++;
            if(iteradorHorario> arrHorario.length){
                //si el iterador de horario llego a fin entonces ya no quedan pasajes para vuelos en la aerolinea
                this.sinPasajes= true;
            }
        }
        mutexVenderPasaje.release();

        return pasajeAux;

    }

    
    public boolean isSinPasajes() throws InterruptedException{
        mutexVenderPasaje.acquire();
        boolean res = sinPasajes;
        mutexVenderPasaje.release();
        return res;
    }

    public String toString(){
        return hashVuelo.toString();
    }
}
