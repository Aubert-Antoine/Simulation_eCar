import java.util.Date;

public class Car extends Vehicle {

    private int carTravelDistance = 0;
    private Date lastService = new Date(0000-00-00);
    private String carColor = "";
    private int model_ID = 0;

    public Car(){
        super();
        this.carTravelDistance = 0;
        this.lastService = new Date(0000-00-00);
    }

    public Car(int pCarId, int pCarTravelDistance, Date pLastService, int pTotalMili, String pCarColor){
        super();
        this.carTravelDistance = pCarTravelDistance;
        this.lastService = new Date(0000-00-00);
        this.carColor = pCarColor;
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


    public String getCarColor() {
        return carColor;
    }

    public int getModel_ID() {
        return model_ID;
    }
}
