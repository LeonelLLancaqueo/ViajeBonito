public class Pasaje {
    
    private int  idVuelo, horaSalida, idPuestoEmbarque;
    private char idTerminal;
    private String aerolinea;

    public Pasaje(int idVuelo, String aerolinea ,char idTerminal, int puestoEmbarque, int horaSalida){
        this.idVuelo = idVuelo;
        this.idTerminal = idTerminal;
        this.idPuestoEmbarque = puestoEmbarque;
        this.horaSalida= horaSalida;
        this.aerolinea= aerolinea;
    }


    public int getIdVuelo() {
        return idVuelo;
    }

    public void setIdVuelo(int idVuelo) {
        this.idVuelo = idVuelo;
    }

    public char getIdTerminal() {
        return idTerminal;
    }

    public void setIdTerminal(char idTerminal) {
        this.idTerminal = idTerminal;
    }

    public int getIdPuestoEmbarque() {
        return idPuestoEmbarque;
    }

    public void setIdPuestoEmbarque(int idPuestoEmbarque) {
        this.idPuestoEmbarque = idPuestoEmbarque;
    }

    public int getHoraSalida() {
        return horaSalida;
    }


    public void setHoraSalida(int horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(String aerolinea) {
        this.aerolinea = aerolinea;
    }


    @Override
    public String toString() {
        return "Pasaje [idVuelo=" + idVuelo + ", horaSalida=" + horaSalida + ", idPuestoEmbarque=" + idPuestoEmbarque
                + ", idTerminal=" + idTerminal + ", aerolinea=" + aerolinea + "]";
    }
    
    


}
