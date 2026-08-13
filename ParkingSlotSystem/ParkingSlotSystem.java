import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.time.Duration;

//For payment
interface Payment{
    void pay();
}
//Here are the different types of payment services
class onlinePayment implements Payment{
    public void pay(){
        System.out.println("Payment was successful with use of online UPI services...");
    }
}
class DebitCard implements Payment{
    public void pay(){
        System.out.println("Paid with Debit card...");
    }
}

class CreditCard implements Payment{
    public void pay(){
        System.out.println("Credit Card Payment was successfully executed...");
    }
}
class offlinePayment implements Payment{
    public void pay(){
        System.out.println("Hand cash payment was successfully implemented...");
    }
}

//Here factory design pattern is used
class PaymentStratergy{
    public static Payment toPay(String stratergy){
        if(stratergy.equals("online")){
            return new onlinePayment();
        }
        else if(stratergy.equals("offline")){
            return new offlinePayment();
        }
        else if(stratergy.equals("credit")){
            return new CreditCard();
        }
        else if(stratergy.equals("debit")){
            return new DebitCard();
        }
        else{
            return null;
        }
    }
}
//It is a ticket given to customer
class Ticket{
    int id;
    int floor;
    int vehicleNum;
    String vehicleType;
    LocalDateTime inTime;
    LocalDateTime outTime;
    boolean payment;
    boolean exit;
    long fees;

    public Ticket(int id,int floor,int vehicleNum,String vehicleType){
        this.id=id;
        this.floor=floor;
        this.vehicleNum=vehicleNum;
        this.vehicleType=vehicleType;
        this.payment=false;
        this.inTime=LocalDateTime.now();
        this.exit=false;
        this.fees=0L;
    }

    public void print(){
        DateTimeFormatter format=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("\nTicket Informations\nFloor: "
        +this.floor+"\nTicketId: "+this.id+"\nVehicle Number: "+this.vehicleNum+"\nVehicle Type: "+this.vehicleType+
        "\nIn time: "+this.inTime.format(format));
        if(outTime!=null){
            System.out.print("\nOut Time: "+this.outTime.format(format));
        }
        System.out.print("\nFees: "+this.fees);
    }

    //Here implementing payment for each ticket
    public void topay(String payType,long hrs){
        payType=payType.toLowerCase();
        long payment=calculateParkingFees(hrs);
        //Here abstract design Pattern
        Payment pay=PaymentStratergy.toPay(payType);
        if(pay==null){
            System.out.println("Invalid payment option!");
            return;
        }
        this.fees=payment;
        //Else do the payment here
        pay.pay();
        System.out.println("Amount: "+this.fees);
        this.payment=true;
    }

    public long calculateParkingFees(long hrs){
        if(hrs<=0) hrs=1;
        if(this.vehicleType.equals("car")){
            return hrs*50;
        }
        else if(this.vehicleType.equals("bus")){
            return hrs*100;
        }
        else if(this.vehicleType.equals("bike")){
            return hrs*20;
        }
        //Like auto
        else{
            return hrs*30;
        }
    }
}

//Here If there are multiple buildings are there for parking means, For that thing
interface Building{
    void viewParkedVehicles();
    void viewPastVehicles();
}

//inside of building you have floors
class floor{
        int floor;
        HashMap<String,Integer> map;
        List<Ticket> tickets;

        public floor(int floor,int car,int bike,int bus){
            this.floor=floor;
            this.map=new HashMap<>();
            map.put("car",car);
            map.put("bike",bike);
            map.put("bus", bus);
            tickets=new ArrayList<>();
        }

        //Here validating there is a slot or not
        public boolean checkSlot(String vehicleType){
            int count=map.get(vehicleType.toLowerCase());
            return count==0;
        }

        public Ticket entry(String vehicleType,int vehicleNum){
            //Here validating there i sa free space for park of vehicles ...
            if(!map.containsKey(vehicleType) || map.get(vehicleType)==0){
                System.out.println("There is No Enough space for this type of vehicles!");
                return null;
            }
            //Here creating new ticket for billing
            Ticket newticket=new Ticket(this.tickets.size(),this.floor,vehicleNum,vehicleType);
            tickets.add(newticket);
            newticket.print();
            //Here one place is occupied
            map.put(vehicleType,map.get(vehicleType)-1);
            return newticket;
        }

        //For simple complexity now we will execute our search of ticket with id, 
        //But in future the id is shown as QR and that qr will gets and retrive ans information
        //with use if an index. So that better time complexity rises here...
        public void exit(int id,String payType){
            if(id>=tickets.size() || id<0){
                System.out.println("Invalid ticket id...");
                return;
            }
            Ticket curTicket=tickets.get(id);
            if(curTicket.payment){
                System.out.println("Ticket is already paid!");
                return;
            }
            
            //Settting out time
            curTicket.outTime=LocalDateTime.now();
            //Here calculating duration
            Duration duration = Duration.between(curTicket.inTime, curTicket.outTime);
            long hours = duration.toHours();
            
            hours=Math.max(1,hours);

            curTicket.topay(payType,hours);

            if(curTicket.payment){
            curTicket.exit=true;
            System.out.println("The vehicle "+curTicket.vehicleNum+" was exited!");
            //Here one place is free
            map.put(curTicket.vehicleType,map.get(curTicket.vehicleType)+1);
            }
            else{
                System.out.println("Payment failed! Kindly Retry again...");
            }
        }

        //Exit with use of vehicle number, If somebody losts the ticket
        public void exitByVehicleNum(int vehicleNum){
            for(Ticket t:tickets){
                if(t.vehicleNum==vehicleNum){
                    //Pay fees only by offline
                    exit(t.id,"offline");
                    return;
                }
            }
            System.out.println("Invalid Vehicle Number...");
        }

        //For printing Empty Slots
        public void emptySlots(){
            System.out.println("The Empty slots are: \n1.Car: "+map.get("car")
            +"\n2.Bus: "+map.get("bus")+"\n3.Bike: "+map.get("bike")+"\n");
        }

        //For printing all current parking vehicles
        public void parkedVehicles(){
            System.out.println("Currently in vehicles: ");
            for(int i=0;i<tickets.size();i++){
                if(tickets.get(i).exit==false){
                    tickets.get(i).print();
                }
            }
        }

        //For printing all parking completed vehicles
        public void parkCompletedVehicles(){
            System.out.println("Past parked vehicles: ");
            for(int i=0;i<tickets.size();i++){
                if(tickets.get(i).exit==true){
                    tickets.get(i).print();
                }
            }
        }
}

class mall implements Building{
    List<floor> floors=new ArrayList<>();

   //Here printing all vehicles in this building
   public void viewParkedVehicles(){
    for(floor f:floors){
        System.out.println("All vehicles in this floor: "+f.floor);
        f.parkedVehicles();
    }
   }

   //Here all these for park completed vehicles
   public void viewPastVehicles(){
    for(floor f:floors){
        System.out.println("All vehicles in this floor: "+f.floor);
        f.parkCompletedVehicles();
    }
   }
   
}
public class joker {
    public static void main(String[] args) {
        List<Building> building=new ArrayList<>();
        
        //Creating buildings
        Building mall=new mall();
        //Creating floors
        floor f1=new floor(1,10, 2, 20);
        //mall.floors.add(f1);
        building.add(mall);

        System.out.println("Welcome to Joker's Parking!");
        Scanner sc=new Scanner(System.in);
        while(true){
            System.out.println("1.Enter Vehicle\n2.Exit Vehicle By Id\n3.View Current Parked Vehicle\n4.View Past Parked Vehicle\n5.View Remain Slots\n6.Exit Vehicle By Vehicle Nummber\n7.To Exit\n~Enter you option: ");
            int op=sc.nextInt();
            if(op==1){
            System.out.println("Enter the Vehicle Type: ");
            String vehType=sc.next();
            System.out.println("Enter the Vehicle Number: ");
            int vehNum=sc.nextInt();
            f1.entry(vehType.toLowerCase(), vehNum);
            
            System.out.println();
            }
            else if(op==2){
                System.out.println("Enter the id of ticket: ");
                int ticketId=sc.nextInt();
                System.out.println("Enter payment type(online,offline,credit,debit): ");
                String payType=sc.next();
                f1.exit(ticketId, payType.toLowerCase());

                
            System.out.println();
            }
            else if(op==3){
                f1.parkedVehicles();
                
            System.out.println();
            }
            else if(op==4){
                f1.parkCompletedVehicles();
                
            System.out.println();
            }
            else if(op==5){
                f1.emptySlots();
            }
            else if(op==6){
            System.out.println("Enter the Vehicle Number: ");
            int vehNum=sc.nextInt();
            f1.exitByVehicleNum(vehNum);
            
            System.out.println();
            }
            else if(op==7){
                System.out.println("Exiting...");
                System.exit(0);
            }
            else{
                System.out.println("Kindly enter valid option!");
                
            System.out.println();
            }
        }
    }
}