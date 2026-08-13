package main;

import java.util.Scanner;

class main{
    public static void main(String args[]){
         Scanner sc = new Scanner(System.in);
 
        UserService userService = new UserService();
        ProductService productService = new ProductService();
        OrderService orderService = new OrderService(userService, productService);
 
        ProductController productController = new ProductController(sc, productService);
        OrderController orderController = new OrderController(sc, orderService, userService);
        UserController userController = new UserController(sc, userService);
 
        
        while(true){
            System.out.println("1.Product Operation\n2.Order operation\n3.User Operation\n4.Exit\nEnter your choice: ");
            int op=sc.nextInt();
            if(op==1){
                productController.productOperation();
            }
            else if(op==2){
                orderController.orderOperation();
            }
            else if(op==3){
                userController.userOperation();
            }
            else if(op==4){
                System.out.println("Exting from whole system... Thankyou :)");
                System.exit(0);
            }
        }
    }
}