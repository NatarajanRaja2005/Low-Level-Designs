package main;

import java.util.Scanner;

public class OrderController {
    OrderService orderService;
    UserService userService;
    Scanner sc;
    public OrderController(Scanner sc, OrderService orderService, UserService userService) {
        this.sc = sc;
        this.orderService = orderService;
        this.userService = userService;
    }
    
    public void orderOperation(){
        while(true){
        System.out.println("Order Operations:\n1. To create Orders\n2. To add product to orders\n3. Get Order\n4. Cancel order\n5. Display Order of particular User: \n6.Exit from Orders\nEnter your choice: ");
        int op=sc.nextInt();
        if(op==1){
            System.out.println("Creating order\nEnter the email of user: ");
            String email=sc.next();
            orderService.createOrder(email);
        }   
        else if(op==2){
            System.out.println("Add products to order\nEnter order id: ");
            Long orderId=sc.nextLong();
            System.out.println("Enter name of the product: ");
            String productName=sc.next();
            System.out.println("Enter the quantity: ");
            int quantity=sc.nextInt();
            orderService.addProductsToOrder(orderId, productName, quantity);
        }
        else if(op==3){
            System.out.println("Get order\nEnter the id of order: ");
            Long orderId=sc.nextLong();
            orderService.getOrderById(orderId);
        }
        else if(op==4){
            System.out.println("Cancelling the order\nEnter the orderId: ");
            Long orderId=sc.nextLong();
            orderService.cancelOrder(orderId);
        }
        else if(op==5){
            System.out.println("Creating order\nEnter the email of user: ");
            String email=sc.next();
            userService.displayOrderOfParticularUser(email);
        }
        else if(op==6){
            System.out.println("Exiting from orders...");
            break;
        }
     }
    }
}
