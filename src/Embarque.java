

public class Embarque {
    private int id;

    public Embarque(int id) {
        this.id=id;
    }

    public void embarcar()throws InterruptedException{
        System.out.println("El pasajero esta embarcado");
        Thread.sleep(1000);

    }


    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }   
    



}