public class Reloj extends Thread{
    
    private Tiempo tiempo;
    private Aeropuerto aeropuerto;

    public Reloj(Tiempo tiempo, Aeropuerto aeropuerto){
        this.tiempo = tiempo;
        this.aeropuerto= aeropuerto;
    }
    public void run() {
        while(true){
            try {
                Thread.sleep(50000);
                tiempo.nextHour();
                System.out.println("el reloj marca las "+ tiempo.getTiempo());
                aeropuerto.abrirAeropuerto();
                aeropuerto.despertarGenteTerminal();


            }
            catch (Exception err) {
                System.out.println(err.getMessage());
            }
        }
       
        
    }
}
