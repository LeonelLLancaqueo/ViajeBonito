public class Reloj extends Thread{

        //Esta clase esta encargada de pasar el tiempo de la clase Tiempo
        //Tambien es la encargada de manejar eventos del aeropuerto relacionados con el tiempo
    
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
