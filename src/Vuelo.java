
import java.util.LinkedList;

//clase utilizada para modelar los vuelos dentro del aeropuerto

public class Vuelo {
    private int id, horaSalida, embarque;
    static int idVuelo;
    
    private final static int CANT_LUGARES=10;
    private char terminal;
    private String aerolinea;
    private LinkedList<Pasaje> colPasaje;
    private boolean realizado, stockPasajes, permitidoVender;

    


    public Vuelo(int horaSalida,String aerolinea, int embarque, char terminal){
        this.id = inicializarId();
        this.aerolinea= aerolinea;
        this.horaSalida = horaSalida;
        this.terminal = terminal;
        this.embarque = embarque;
        colPasaje= new LinkedList<Pasaje>();
        this.stockPasajes = true;
        this.permitidoVender= true;
        this.inicializarColPasajes();
    }

    private void inicializarColPasajes(){ 
        for(int i=0; i<CANT_LUGARES; i++){
            colPasaje.add(new Pasaje(id, aerolinea, terminal, embarque, horaSalida));
        }
    }
    public Pasaje venderPasaje(){
        // este metodo retorna un pasajae
        Pasaje pasajeAux= colPasaje.poll();
        if(colPasaje.isEmpty()){
            stockPasajes= false;
        } 
        
        return pasajeAux; 
    }
    private int inicializarId(){
        int id= idVuelo;
        idVuelo++;
        return id;
    }


    public int getHoraSalida() {
        return horaSalida;
    }


    public int getEmbarque() {
        return embarque;
    }

    public char getTerminal() {
        return terminal;
    }

    public void setTerminal(char terminal) {
        this.terminal = terminal;
    }

    public boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }

    public boolean isStockPasajes() {
        return stockPasajes;
    }
    
  


    public boolean isPermitidoVender() {
        //permite marcar a el avion si esta permitido vender pasajes
        return permitidoVender;
    }

    public void setPermitidoVender(boolean permitidoVender) {
        this.permitidoVender = permitidoVender;
    }

    @Override
    public String toString() {
        return "\n \n  Vuelo [aerolinea=" + aerolinea + ", embarque=" + embarque + ", horaSalida=" + horaSalida + ", id=" + id
                + ", realizado=" + realizado + ", stockPasajes=" + stockPasajes + ", terminal=" + terminal + "]"+
                "\n Coleaccion de pasajes "+colPasaje.toString();
    }
    
}
