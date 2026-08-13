package main;

import java.util.Scanner;

public class UserController {
    UserService userService;
    Scanner sc;
    public UserController(Scanner sc, UserService userService) {
        this.sc = sc;
        this.userService = userService;
    }

    public void userOperation(){
        while(true){
         System.out.println("UserOperations:\n1.To create new user\n2. To retrive user by email\n3.Exit\nEnter Your choice: ");
         int op=sc.nextInt();
         if(op==1){
            System.out.println("Creating user\nEnter name of user: ");
            String name=sc.next();
            System.out.println("Enter email of user: ");
            String email=sc.next();
            System.out.println("Enter password for user: ");
            String password=sc.next();
            userService.createUser(name, email, password);
         }
         else if(op==2){
            System.out.println("Retriving user by email\nEnter email of user: ");
            String email=sc.next();
            userService.getUserByEmail(email);
         }
         else if(op==3){
            System.out.println("Exiting from user...");
            break;
         }
        }
    }
}
