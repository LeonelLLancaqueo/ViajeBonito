
public class Guardia extends Persona{

    public Guardia(Aeropuerto aeropuerto, Tiempo tiempo){
        super(aeropuerto, tiempo);
    }
    public Guardia(Aeropuerto aeropuerto, PuestoAerolinea puestoAerolinea, Tiempo tiempo){
        super(aeropuerto,  puestoAerolinea, tiempo);
    }
    public Guardia(PuestoAerolinea puestoAerolinea, Tiempo tiempo){
        super(puestoAerolinea, tiempo);
    }

    
}
