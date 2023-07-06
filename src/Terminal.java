
import java.util.concurrent.Semaphore;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

import java.util.concurrent.ConcurrentHashMap;

public class Terminal {
    private char id;
    private int colIdEmbarque[], siguienteEmbarque;
    private Semaphore mutexSigEmb; 
    

    //embarque general
    private Lock lockEmbarqueGeneral;
    private Condition esperarEmbarcar;

    private Tiempo tiempo;

    private ConcurrentHashMap<Integer,Embarque> hashEmbarque;

    private FreeShop freeShop;


    

    public Terminal(char id, int []idEmbarque, Tiempo tiempo){
        this.id = id;
        this.colIdEmbarque= idEmbarque;

        this.hashEmbarque= new ConcurrentHashMap<Integer,Embarque>();
        //embarque;

        

        this.siguienteEmbarque = 0;
        mutexSigEmb= new Semaphore(1);
    
        this.lockEmbarqueGeneral= new ReentrantLock();
        this.esperarEmbarcar= lockEmbarqueGeneral.newCondition();
    
        this.tiempo= tiempo;
    
        //Free shop
        

        this.inicializarColEmbarqueYHash(idEmbarque);

        
        
        this.freeShop= new FreeShop(tiempo);

    }

    public FreeShop irAFreeShop(){
        return this.freeShop;
    }

    public void despertarGente(){
        try {
            lockEmbarqueGeneral.lock();
            esperarEmbarcar.signalAll();
        }
        catch (Exception err) {
            System.out.println(err.getMessage());
        }finally {
            lockEmbarqueGeneral.unlock();
        }
    }

    public Embarque esperarEmbarcar(Pasaje pasaje){
        try {
            lockEmbarqueGeneral.lock();
            while(tiempo.mayor(pasaje.getHoraSalida()-1)){
                esperarEmbarcar.await();
            }
        
        
        }
        catch (Exception err) {
            System.out.println(err.getMessage()+ "Error en metodo esperarEmbarcar");
        
        
        }finally {
            lockEmbarqueGeneral.unlock();
        }
        return this.hashEmbarque.get(pasaje.getIdPuestoEmbarque());
    }

    private void inicializarColEmbarqueYHash(int [] colIdEmbarque){
        //CREAMOS LOS EMBARQUES DE LA TERMINAL CON LOS ID PASADOS POR PARAMETROS
        
        for(int i=0; i<colIdEmbarque.length; i++){
            this.hashEmbarque.put(colIdEmbarque[i], new Embarque(colIdEmbarque[i]) );
        }

    }


    public char getId() {
        return id;
    }


    public void setId(char id) {
        this.id = id;
    }




    public int getEmbarqueAleatorio() throws InterruptedException {
        //metodo que retorna el id de un embarque correspondiente a la terminal
        

        mutexSigEmb.acquire();

        int aux= this.colIdEmbarque[this.siguienteEmbarque];//obtenemos el id de un embarque
        

        siguienteEmbarque++; //iterador  de la colEmbarque para que no sea siempre el mismo embarque
        
        if(siguienteEmbarque > colIdEmbarque.length-1 ){
            this.siguienteEmbarque= 0; // si se sobrepasa la logitud de la colEmbarque el iterador vuelve  a 0
        }
        //monitor 
        mutexSigEmb.release();

        return aux;

    }

    public boolean equals(char idOtraTerminal){
        return this.id == idOtraTerminal;
    }

}


    

