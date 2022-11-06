public class ElectricCar extends Car{

    private int batteryBalance = 0;

    public ElectricCar(){
        super();
        this.batteryBalance = 0;
    }

    public ElectricCar(int pBatteryBalance){
        super();
        this.batteryBalance = pBatteryBalance;
    }

    public int getBatteryBalance() {
        return batteryBalance;
    }

    public void setBatteryBalance(int batteryBalance) {
        this.batteryBalance = batteryBalance;
    }


}
