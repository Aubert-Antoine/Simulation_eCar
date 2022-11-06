public class Vehicle implements VehicleInterface{

    private int numberOfPassengers=0;
    private int manufactureYear=0;
    private double price=0.0;
    private String brand="";

    public Vehicle(){}

    public Vehicle(int pNumberOfPassengers, int pManufactureYear, double pPrice, String pBrand){
        this.numberOfPassengers = pNumberOfPassengers;
        this.manufactureYear = pManufactureYear;
        this.price = pPrice;
        this.brand = pBrand;
    }

    @Override
    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    @Override
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public void setManufactureYear(int manufactureYear) {
        this.manufactureYear = manufactureYear;
    }


    @Override
    public int getNumberOfPassengers() {
        return this.numberOfPassengers;
    }


    @Override
    public int getManufactureYear() {
        return this.manufactureYear;
    }

    @Override
    public String getBrand() {
        return this.brand;
    }

    @Override
    public double getPrice() {
        return this.price;
    }


}
