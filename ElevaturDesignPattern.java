import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

enum Direction{
    UP,DOWN;
}
enum Status{
    MOVING,STANDING,IDLE,MAINTAINENCE;
}
abstract class Buttons{
    int floor;
    Direction directionToMove;
    ElevatorStratergy stratergy;
    
    public Buttons(int floor){
        this.floor=floor;
    }
    
    public Buttons(int floor,ElevatorStratergy stratergy){
        this.floor=floor;
        this.stratergy=stratergy;
    }

    public abstract void setStratergy(ElevatorStratergy stratergy);
    public abstract void pressDown();
    public abstract void pressUp();
}
class ButtonFunctionalities extends Buttons{

    public ButtonFunctionalities(int floor){
        super(floor);
    }

    public ButtonFunctionalities(int floor,ElevatorStratergy stratergy){
        super(floor,stratergy);
    }
    

    @Override
    public void pressDown() {
        stratergy.moveTo(Direction.DOWN,this.floor);
    }

    @Override
    public void pressUp() {
        stratergy.moveTo(Direction.UP,this.floor);
    }
    
    @Override
    public void setStratergy(ElevatorStratergy stratergy){
        this.stratergy=stratergy;
    }
}
class ElevatorStratergy{

    List<Elevator> elevators;

    public ElevatorStratergy(){
        this.elevators=new ArrayList<>();
    }

    public void addElevator(Elevator elevator){
        this.elevators.add(elevator);
    }

    //It should have to analysis the thing of which lift we should have to move there
    public void moveTo(Direction direction,int currentFloor){
        
        int optimalDis=Integer.MAX_VALUE;
        Elevator pickkedElevator=null;
        for(int i=0;i<elevators.size();i++){
            Elevator toPickElevator=elevators.get(i);

            //Here checking the IDLE lifts
            if(toPickElevator.getCurMovingStatus()==Status.IDLE){
                int dis=Math.abs(toPickElevator.getCurFloor()-currentFloor);
                if(dis<optimalDis){
                    optimalDis=dis;
                    pickkedElevator=toPickElevator;
                }
            }
            //Here is for MOVING lifts
            else if(toPickElevator.getCurMovingStatus()==Status.MOVING){
                if(toPickElevator.getCurMovingDirection()==direction){
                    if(direction==Direction.UP){
                        if(toPickElevator.getCurFloor()<=currentFloor){
                            pickkedElevator=toPickElevator;
                            break;
                        }
                    }
                    else{
                        if(toPickElevator.getCurFloor()>=currentFloor){
                            pickkedElevator=toPickElevator;
                            break;
                        }
                    }
                }
            }
        }
        if(pickkedElevator==null){
            pickkedElevator=new HybridElevator();
            pickkedElevator.underMaintainence();
            return;
        }
        pickkedElevator.setToFloor(direction);
        pickkedElevator.addFloors(currentFloor);
        pickkedElevator.move();
    }
}
interface Elevator{
    
    Scanner sc=new Scanner(System.in);
    void openDoors();
    void move();
    void closeDoors();
    void underMaintainence();
    void addFloors(int floor);
    void addFloors();
    void EmergencyCall();
    void setToFloor(Direction direction);
    Direction getCurMovingDirection();
    Status getCurMovingStatus();
    int getCurFloor();
}

//Hybrid covers of moving the both UP and DOWN directions
class HybridElevator implements Elevator{
    int id;
    Direction curMovingDirection;
    Status curMovingStatus;
    int curFloor;
    TreeSet<Integer> toFloors;
       
    @Override
    public Direction getCurMovingDirection() {
        return curMovingDirection;
    }

    @Override
    public Status getCurMovingStatus() {
        return curMovingStatus;
    }

    @Override
    public int getCurFloor() {
        return curFloor;
    }


    public HybridElevator(int id){
        this.id=id;
        curMovingStatus=Status.IDLE;
        curMovingDirection=null;
        curFloor=0;
    }

    public HybridElevator(){

    }
    
    public int getFirstFromToFloors(){
        for(int i:toFloors){
            return i;
        }
        return -1;
    }

    @Override
    public void closeDoors() {
        System.out.println("Elevator : "+this.id+" Doors are Closing :)");
    }

    @Override
    public void move() {
        while(!toFloors.isEmpty()){
            curMovingStatus=Status.MOVING;
            curMovingDirection=(curFloor-getFirstFromToFloors() <0)?Direction.UP:Direction.DOWN;
            this.curFloor=getFirstFromToFloors();
            curMovingStatus=Status.IDLE;
            openDoors();
            toFloors.remove(getFirstFromToFloors());
            closeDoors();
            addFloors();
       }
    }

    @Override
    public void openDoors() {
        System.out.println("Elevator : "+this.id+" Doors are Opening :)" + " Current Floor: "+this.curFloor);
    }

    @Override
    public void underMaintainence(){
        System.out.println("Sorry for inconvenience! The Elevator is Under Maintainence...");
    }

    @Override
    public void addFloors(int floor){
        this.toFloors.add(floor);
    }

    @Override
    public void addFloors(){
        System.out.println("Enter the floors where to Move Or -1 to close: ");
        while(sc.hasNext()){
        int input=sc.nextInt();
        if(input==-1){
            break;
        }
        addFloors(input);
        }
    }

    @Override
    public void EmergencyCall(){
        System.out.println("In Emergency Call...");
    }

    @Override
    public void setToFloor(Direction direction){
        if(direction==Direction.UP){
            this.toFloors=new TreeSet<>();
        }
        else{
            this.toFloors=new TreeSet<>(Comparator.reverseOrder());
        }
    }
}
public class joker {
    public static void main(String[] args) {

        Scanner sc=new Scanner(System.in);

        ElevatorStratergy stratergy=new ElevatorStratergy();

        

        Elevator e1=new HybridElevator(1);
        Elevator e2=new HybridElevator(2);
        Elevator e3=new HybridElevator(3);
        

        Buttons b0=new ButtonFunctionalities(0,stratergy);
        Buttons b1=new ButtonFunctionalities(1,stratergy);
        Buttons b2=new ButtonFunctionalities(2,stratergy);
        Buttons b3=new ButtonFunctionalities(3,stratergy);
        Buttons b4=new ButtonFunctionalities(4,stratergy);
        Buttons b5=new ButtonFunctionalities(5,stratergy);

        stratergy.addElevator(e1);
        stratergy.addElevator(e2);
        stratergy.addElevator(e3);

        
        System.out.println("Welcome to Joker's Elevator");
        b0.pressUp();
        b1.pressUp();
        b3.pressUp();
        b0.pressUp();
        b4.pressDown();
        b5.pressDown();
      System.out.println("Thanking so much...");
    }
}