


abstract class Persona extends Thread{
    protected Aeropuerto aeropuerto;
    protected PuestoAerolinea aerolinea;
    protected static int idPersona=0;
    protected Tiempo tiempo;
    int id; 

    public Persona(Aeropuerto aeropuerto, Tiempo tiempo){
        this.aeropuerto= aeropuerto;
        this.id= inicializarId();
        this.tiempo = tiempo;
    }
    public Persona(Aeropuerto aeropuerto, PuestoAerolinea aerolinea, Tiempo tiempo){
        this.aeropuerto= aeropuerto;
        this.aerolinea= aerolinea;
        this.id= inicializarId();
        this.tiempo = tiempo;
    }
    public Persona(PuestoAerolinea aerolinea, Tiempo tiempo){
        
        this.aerolinea= aerolinea;
        this.id= inicializarId();
        this.tiempo = tiempo;

    }
    public Persona(Tiempo tiempo){
        this.tiempo=tiempo;
    }

    
    private int inicializarId(){
        int id= idPersona;
        idPersona++;
        return id;
    }

    

}
