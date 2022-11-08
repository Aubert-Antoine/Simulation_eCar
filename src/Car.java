import java.util.Date;

public class Car extends Vehicle {

    private static int carTravelDistance = 0;
    private static Date lastService = new Date(0000-00-00);
    private static String carColor = "";
    private static int model_ID = 0;

    public Car(){
        super();
        this.carTravelDistance = 0;
        this.lastService = new Date(0000-00-00);
    }

    public Car(int pCarId, int pCarTravelDistance, Date pLastService, String pCarColor){
        super();
        this.model_ID = pCarId;
        this.carTravelDistance = pCarTravelDistance;
        this.lastService = pLastService;
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

    public static Date getLastService() {
        return lastService;
    }

    public static int getCarTravelDistance() {
        return carTravelDistance;
    }


    public String getCarColor() {
        return carColor;
    }

    public int getModel_ID() {
        return model_ID;
    }
}
