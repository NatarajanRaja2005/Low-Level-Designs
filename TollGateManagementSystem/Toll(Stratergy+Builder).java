import java.lang.module.ModuleDescriptor.Builder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

interface payStrategy{
    int pay(vehicle vehicle,HashMap<String,Integer> map);
}
class payAsVip implements payStrategy{
    @Override
    public int pay(vehicle vehicle,HashMap<String,Integer> map){
        int amt=map.getOrDefault(vehicle.type.toLowerCase(), 0);
        if(vehicle.isVip()){
            amt-=(amt)*0.1;
        }
       return amt;
    }
}
class payAsSunday implements payStrategy{
    @Override
    public int pay(vehicle vehicle,HashMap<String,Integer> map){
        int amt=map.getOrDefault(vehicle.type.toLowerCase(), 0);
      
            amt-=(amt)*0.2;
      
       return amt;
    }
}
class vehicle{
    int number;
    String type;
    List<Toll> toll=new ArrayList<>();
    boolean vip;

    
    public int getNumber() {
        return number;
    }
    public String getType() {
        return type;
    }
    public List<Toll> getToll() {
        return toll;
    }
    public boolean isVip() {
        return vip;
    }

    public void displayToll(){
        System.out.println("Displaying all Tolls oof Vehicle: "+this.number);
        for(Toll t:this.toll){
            System.out.println("Toll id: "+t.id+" Toll Name: "+t.name+" Location: "+t.location);
        }
        
        System.out.println();
    }

    public void addToll(Toll toll){
        if(this.toll.contains(toll)==false){
        this.toll.add(toll);
        }
        else{
            System.out.println("Dupicate Entry!");
        }
    }
    vehicle(vehBuilder builder){
        this.number=builder.number;
        this.type=builder.type;
        this.vip=builder.vip;
    }
    //Vehicle Builder
    static class vehBuilder{
    private int number;
    private String type;
    private List<Toll> toll=new ArrayList<>();
    private boolean vip;

    public vehBuilder setNumber(int number) {
        this.number = number;
        return this;
    }
    public vehBuilder setType(String type) {
        this.type = type;
        return this;
    }
    public vehBuilder setToll(List<Toll> toll) {
        this.toll = toll;
        return this;
    }
    public vehBuilder setVip(boolean vip) {
        this.vip = vip;
        return this;
    }

    public vehicle build(){
        return new vehicle(this);
    }
    
    }
}

class Toll{
    int id;
    String name;
    String location;
    HashMap<String,Integer> map;
    int amount;
    List<vehicle> vehicles=new ArrayList<>();

    payStrategy payStrategy;
    
    //Stratergy Design Pattern
    public void setStratergy(payStrategy payStrategy){
        this.payStrategy=payStrategy;
    }

    public void pay(vehicle Vehicle){
        this.vehicles.add(Vehicle);
        Vehicle.addToll(this);
        this.amount+=this.payStrategy.pay(Vehicle,this.map);
    }


    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getLocation() {
        return location;
    }


    public HashMap<String, Integer> getMap() {
        return map;
    }


    public int getAmount() {
        return amount;
    }


    public List<vehicle> getVehicles() {
        return vehicles;
    }


    public void displayVehicle(){
        System.out.println("Displaying All Vehicles in Toll: "+this.name);
        for(vehicle v:this.vehicles){
            System.out.println("Vehicle Number: "+v.number+" Type: "+v.type+" VIP: "+v.vip);
        }
        
        System.out.println("Total Amount: "+this.amount+"\n");
    }

    Toll(tollBuilder builder){
        this.id=builder.id;
        this.name=builder.name;
        this.location=builder.location;
        this.amount=builder.amount;
        this.map=builder.map;
        this.payStrategy=builder.payStrategy;
    }

    static class tollBuilder{
        //Default Values on Builder
        private int id=0;
        private String name=" ";
        private String location=" ";
        private HashMap<String,Integer> map=new HashMap<>();
        private int amount=0;
        private payStrategy payStrategy=new payAsVip();
        

        public tollBuilder setStratergy(payStrategy payStrategy){
            this.payStrategy=payStrategy;
            return this;
        }
        public tollBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public tollBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public tollBuilder setLocation(String location) {
            this.location = location;
            return this;
        }

        public tollBuilder setMap(HashMap<String, Integer> map) {
            this.map = map;
            return this;
        }

        public tollBuilder setAmount(int amount) {
            this.amount = amount;
            return this;
        }
        
        public Toll build(){
            return new Toll(this);
        }
        
     public  HashMap<String,Integer> getHashMap(String[] key,int[] value){
            HashMap<String,Integer> map=new HashMap<>();
            for(int i=0;i<key.length;i++){
                map.put(key[i],value[i]);
            }
            return map;
        }   
    }
     
}



public class joker{
    public static void main(String[] args) {
        Toll.tollBuilder tollBuilder=new Toll.tollBuilder();
        String[] key={"car","bus","truck"};
        int[] value={10,15,20};
        HashMap<String,Integer> map=new HashMap<>();

        map=tollBuilder.getHashMap(key,value);
        Toll chennai=tollBuilder.setId(1)
                                .setName("Chennai Toll")
                                .setLocation("Chennai")
                                .setMap(map)
                                .build();

        value[0]=15;
        value[1]=20;
        value[2]=25;
        map=tollBuilder.getHashMap(key, value);
        Toll villupuram=tollBuilder.setId(2)
                                .setName("Villupuram Toll")
                                .setLocation("Villupuram")
                                .setMap(map)
                                .setStratergy(new payAsSunday())
                                .build();

        
        value[0]=20;
        value[1]=25;
        value[2]=35;
        map=tollBuilder.getHashMap(key, value);
        Toll thirchi=tollBuilder.setId(3)
                                .setName("Thirichi Toll")
                                .setLocation("Thirichi")
                                .setMap(map)
                                .build();

        //Vehicle Builder
        vehicle.vehBuilder vehBuilder=new vehicle.vehBuilder();

        vehicle car=vehBuilder.setNumber(1010)
        .setType("car")
        .setVip(false)
        .build();

        vehicle bus=vehBuilder.setNumber(2020)
        .setType("bus")
        .build();

        vehicle truck=vehBuilder.setNumber(3030)
        .setType("truck")
        .setVip(false)
        .build();
        

        chennai.pay(car);
        chennai.pay(truck);
        chennai.pay(bus);

        thirchi.pay(truck);
        thirchi.pay(car);

        car.displayToll();

        villupuram.pay(bus);

        
        thirchi.displayVehicle();
        chennai.displayVehicle();
        
        bus.displayToll();

        villupuram.pay(truck);

        truck.displayToll();
    }
}
