import java.util.concurrent.ConcurrentHashMap;


/*  EN ESTA CLASE SE GUARDARA LA INFORMACION SOBRE LOS VUELOS QUE SE DEBEN REALIZAR DURANTE EL DIA */
public class TablaVueloAeropuerto {

    //ESTA CLASE CONTIENE TODAS LAS COLECCIONES DE VUELOS DE LAS AEROLINEAS

    private ConcurrentHashMap<String, TablaVueloAerolinea> tabla;    

    // NO SABES SI SIRVE ------    private int hora[]= {10,13,15,18}; //{8,9,10,11,12,13,14,15,16,17,18

    public TablaVueloAeropuerto() {
        tabla= new ConcurrentHashMap<String, TablaVueloAerolinea>();
    }


    public void AgrearTablaVuelo(TablaVueloAerolinea tab){
        tabla.put(tab.getAerolinea(), tab);
    }

    public TablaVueloAerolinea getTablaVuelo(){
        return ((TablaVueloAerolinea)tabla.values());
    }

    public String mostrarVuelos(){
        return tabla.values().toString(); // to string de la tabla

        
    }
}
