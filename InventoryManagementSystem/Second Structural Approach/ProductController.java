package main;

import java.util.Scanner;

public class ProductController {

    ProductService productService;
    Scanner sc;
    public ProductController(Scanner sc, ProductService productService) {
        this.sc = sc;
        this.productService = productService;
    }

    public void productOperation(){
        while(true){
            System.out.println("Product Operations:\n1. To add Products\n2. Update product\n3. Get Product by name\n4. Delete Product\n5. Display All products");
            int op=sc.nextInt();
            if(op==1){
                System.out.println("Creating new Product: \nEnter name of product: ");
                String name=sc.next();
                System.out.println("Enter description of product: ");
                String description=sc.nextLine();
                System.out.println("Enter category of product: ");
                String category=sc.next();
                System.out.println("Enter price of product: ");
                int price=sc.nextInt();
                System.out.println("Enter inventory of product: ");
                int inventory=sc.nextInt();
                productService.createProduct(name, description, category, price, inventory);
            }
            else if(op==2){
                System.out.println("Updating a product:\nEnter name of product: ");
                String name=sc.next();
                System.out.println("Enter description of product: ");
                String description=sc.nextLine();
                System.out.println("Enter category of product: ");
                String category=sc.next();
                System.out.println("Enter price of product: ");
                int price=sc.nextInt();
                System.out.println("Enter inventory of product: ");
                int inventory=sc.nextInt();
                productService.updateProduct(name, description, category, price, inventory);
            }
            else if(op==3){
                System.out.println("Retriving a product\nEnter name of product: ");
                String name=sc.next();
                productService.getProductByName(name);
            }
            else if(op==4){
                System.out.println("Deleting a product\nEnter name of product: ");
                String name=sc.next();
                productService.deleteProduct(name);
            }
            else if(op==5){
                productService.displayAllProducts();
            }
            else if(op==6){
                System.out.println("Existing from product...");
                break;
            }
        }
    }
}
