import java.util.Date;

public class Car {

    private int carTravelDistance = 0;
    private Date lastService = new Date(0000-00-00);

    public Car(){
        super();
        this.carTravelDistance = 0;
        this.lastService = new Date(0000-00-00);
    }

    public Car(int pCarId, int pCarTravelDistance, Date pLastService){
        super();
        this.carTravelDistance = pCarTravelDistance;
        this.lastService = new Date(0000-00-00);
    }

    public int calculateMillimetres(int pAddedDistance){
        return this.carTravelDistance = this.carTravelDistance+pAddedDistance;
    }

    public void setLastService(Date lastService) {
        this.lastService = lastService;
    }

    public void setCarTravelDistance(int carTravelDistance) {
        this.carTravelDistance = carTravelDistance;
    }

    public Date getLastService() {
        return lastService;
    }

    public int getCarTravelDistance() {
        return carTravelDistance;
    }
}
