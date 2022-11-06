public class Vehicle implements VehicleInterface{

    private int numberOfPassengers;
    private int manufactureYear;
    private double price;
    private String brand;

    public Vehicle(){
        this.numberOfPassengers = 5;
        this.manufactureYear = 2015;
        this.price = 25000;
        this.brand = "superCarBrand";
    }

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
