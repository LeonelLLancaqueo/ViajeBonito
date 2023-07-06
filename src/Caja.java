public class Caja {


    private int cantCaja;

    public Caja(){
        cantCaja= 2;
    }


    public synchronized void comprarProducto() throws InterruptedException{
        //metodo donde el pasajero pide utilizar una caja para comprar
        while(cantCaja == 0){
            this.wait();
        }
        cantCaja--;
    }
    public synchronized void terminarDeComprar() {
        //metodo donde el pasajero libera una caja
        cantCaja++;
        this.notify();
    }
    
}
