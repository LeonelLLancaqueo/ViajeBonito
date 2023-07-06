
import java.util.concurrent.Semaphore;

public class FreeShop {

    private static final int CAPACIDAD_FREE_SHOP= 6;
    private Semaphore lugaresFreeShop;
    private Caja caja; 
    private Tiempo tiempo;
    

    public FreeShop(Tiempo tiempo){
        this.lugaresFreeShop = new Semaphore(CAPACIDAD_FREE_SHOP); 
        this.caja= new Caja();
    
        this.tiempo= tiempo;
    }

    public boolean entrarYMirarProductos(){

        //metodo donde el pasajero pide entrar en el freeshop si hay lugar
        return lugaresFreeShop.tryAcquire();
    }

    public Caja irACaja(){
        return caja;
    }

    public void salir(){
        // metodo donde el pasajero libera un lugar en el freeshop
        lugaresFreeShop.release();
    }
    

}
