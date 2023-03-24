
import java.util.concurrent.Semaphore;
/* 
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
*/
public class FreeShop {

    private static final int CAPACIDAD_FREE_SHOP= 6;
    private Semaphore lugaresFreeShop, caja; 
    private Tiempo tiempo;
    

    public FreeShop(Tiempo tiempo){
        this.lugaresFreeShop = new Semaphore(CAPACIDAD_FREE_SHOP); 
        this.caja= new Semaphore(2, true);
    
        this.tiempo= tiempo;
    }

    public boolean entrarYMirarProductos(){

        //metodo donde el pasajero pide entrar en el freeshop si hay lugar
        return lugaresFreeShop.tryAcquire();
    }

    
    
    public void comprarProducto() throws InterruptedException{
        //metodo donde el pasajero pide utilizar una caja para comprar
        caja.acquire();
    }
    public void terminarDeComprar() {
        //metodo donde el pasajero libera una caja
        caja.release();
    }

    public void salir(){
        // metodo donde el pasajero libera un lugar en el freeshop
        lugaresFreeShop.release();
    }
    

}
