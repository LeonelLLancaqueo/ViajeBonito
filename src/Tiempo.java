



public class Tiempo {
    private int tiempo;
    
    
    public Tiempo(int tiempo){
        this.tiempo= tiempo;
    }
    public Tiempo(){
        this.tiempo= 0;
    }

    public int getTiempo(){
        return tiempo;
    }

    public synchronized boolean mayor(int otroTiempo){
        return this.tiempo> otroTiempo; 
    }
    public synchronized boolean menor(int otroTiempo){
        return this.tiempo < otroTiempo;
    }
    public synchronized boolean igual(int otroTiempo){
        return this.tiempo == otroTiempo;
    }

    public synchronized void nextHour(){
        this.tiempo++;
        if(this.tiempo > 24){
            this.tiempo= 0;
        }
        


    }

}
