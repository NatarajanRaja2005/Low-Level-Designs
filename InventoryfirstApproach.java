import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

enum OrderStatus{
    FAILED,PENDING,COMPLETED;
}

class OrderDetails{
    Product Product;
    int quantity;

    public OrderDetails(Product product, int quantity) {
        Product = product;
        this.quantity = quantity;
    }

    public void display(){
        System.out.println("Productname: "+this.Product.name+" Quantity: "+this.quantity);
    }
}
class Order{
    int id;
    LocalDateTime date;
    List<OrderDetails> orderDetails;
    int totalPrice;
    OrderStatus status;
    
    public Order() {
        this.date =LocalDateTime.now();
        this.orderDetails =new ArrayList<>();
        this.status=OrderStatus.FAILED;
    }

    public void updatePrice(){
        int price=0;
        for(OrderDetails details:orderDetails){
            price+=(details.quantity*details.Product.unitPrice);
        }
        this.totalPrice=price;
    }

    public void display(){
        System.out.println("Order id: "+this.id+" Order Date: "+this.date);
        for(OrderDetails od:this.orderDetails){
            od.display();
        }
    }

    public void addProducts(Product product,int quantity){
        OrderDetails od=new OrderDetails(product, quantity);
        orderDetails.add(od);
    }
}

class User{
    int id;
    String name;
    String email;
    List<Order> orders;
    Scanner scanner;

    User(String name,String email){
        this.name=name;
        this.email=email;
        scanner=new Scanner(System.in);
        this.orders=new ArrayList<>();
    }

    Order createOrder(List<Product> products,List<Order> orders){
        System.out.println("Enter the how much products: ");
        int number=scanner.nextInt();
        Order order=new Order();
        while(number>0){
            System.out.println("Enter name of product: ");
            String name=scanner.next();
            System.out.println("Enter the Quantity of the product: ");
            int quantity=scanner.nextInt();
            boolean found=false;
            for(Product p:products){
                if(p.name.equalsIgnoreCase(name)){
                    if(p.inventory<quantity){
                        System.out.println("Product out of stock.");
                        break;
                    }
                    order.orderDetails.add(new OrderDetails(p, quantity));
                    p.placingOrder(quantity);
                    found=true;
                    System.out.println("Order created for the product: "+name);
                    break;
                }
            }
            if(!found){
                System.out.println("Something failed!");
            }
            number--;
        }
        order.updatePrice();
        orders.add(order);
        this.orders.add(order);
        System.out.println("Order created Successfully.");
        return order;
    }

    void displayAllOrder(){
        System.out.println("UserId: "+this.id+" UserName: "+this.name);
        for(Order order:orders){
            order.display();
        }
    }

    void displayPendingOrder(){
        System.out.println("Pending Orders: \nUserId: "+this.id+" UserName: "+this.name);
        for(Order order:orders){
            if(order.status==OrderStatus.PENDING){
                order.display();
            }
        }
    }
}

class Product{
    int id;
    String name;
    int inventory;
    int unitPrice;
    String description;

    public Product(String name,int inventory,int price,String description){
        this.name=name;
        this.inventory=inventory;
        this.unitPrice=price;
        this.description=description;
    } 

    public void placingOrder(int quantity){
        this.inventory-=quantity;
    }

    public void display(){
        System.out.println("The id of Product: "+this.id+" The name of product: "+this.name+" The inventory of product: "+
        this.inventory+" The Unit Price of Products: "+this.unitPrice+" The Description of Product: "+this.description);
    }

    public String getName(){
        return this.name.toLowerCase();
    }
}

interface inventory{
    void addProduct(String name,int inventory,int price,String description);
    void updateProduct(int productId,String name,int inventory,int price,String description);
    void outOfStocks();
    void addUsers(String name,String email);
    void placeOrder();
    void getAllOrders();
    void getAllProducts();
    void getAllPendingOrders();
    void getUserpendingOrder(int userId);
    void getUserOrder(int userId);
}

class InventoryImplementation implements inventory{
    List<Product> products;
    List<User> users;
    List<Order> orders;   

    public InventoryImplementation(){
        this.products=new ArrayList<>();
        this.users=new ArrayList<>();
        this.orders=new ArrayList<>();
   }

    @Override
    public void addProduct(String name, int inventory, int price, String description) {
        for(Product p:products){
            if(p.name.equals(name)){
                updateProduct(p.id, name, inventory, price, description);
                return;
            }
        }
        Product product=new Product(name,inventory,price,description);
        product.id=products.size();
        products.add(product);
        System.out.println("Products are added Successfully.");
    }

    @Override
    public void outOfStocks() {
        System.out.println("The Low Stocks items are: ");
        for(Product p:products){
            if(p.inventory<=10){
                p.display();
            }
        }
    }

    @Override
    public void updateProduct(int productId, String name, int inventory, int price, String description) {
        if(productId>=products.size()){
            System.out.println("Invalid Product Id!");
            return;
        }
        Product product=products.get(productId);
        product.name=name;
        product.inventory=inventory;
        product.description=description;
        product.unitPrice=price;
        System.out.println("Products are Updated Successfully.");
    }
    
    @Override
    public void addUsers(String name,String email){
        for(User u:users){
            if(u.email.equals(email)){
                System.out.println("Try with Other Email...");
                return;
            }
        }
        User user=new User(name,email);
        users.add(user);
        System.out.println("User added SUccessfully");
    }

    @Override
    public void getAllOrders(){
        System.out.println("The orders list are: ");
        for(Order order:orders){
            order.display();
        }
    }

    @Override
    public void placeOrder(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the user Id: ");
        int id=sc.nextInt();
        if(id>=users.size()){
            System.out.println("Not a valid user id...");
            return;
        }
        User user=users.get(id);
        user.createOrder(products,orders);
    }

    @Override
    public void getAllProducts(){
        for(Product p:products){
            p.display();
        }
    }

    @Override
    public void getAllPendingOrders(){
        for(Order order:orders){
            if(order.status==OrderStatus.PENDING){
                order.display();
            }
        }
    }

    @Override
    public void getUserpendingOrder(int userId){
        User user=users.get(userId);
        user.displayPendingOrder();
    }

    @Override
    public void getUserOrder(int userId){
        User user=users.get(userId);
        user.displayAllOrder();
    }
    
}
public class joker {
    public static void main(String[] args) {
        InventoryImplementation inventory=new InventoryImplementation();
        Scanner sc=new Scanner(System.in);
        while(true){
            System.out.println("1.Add users\n2.Add Product\n3.Update Product\n4.List Products\n5.List orders\n6.Place Order\n7.Out of Stocks\n8.Get All pending orders in inventory\n9.Get user pending order\n10.Get all order of user\nEnter your choice: ");
            int op=sc.nextInt();
            if(op==1){
                System.out.println("Enter name of user: ");
                String name=sc.next();
                System.out.println("Enter name of email: ");
                String email=sc.next();
                inventory.addUsers(name,email);
            }
            else if(op==2){
                System.out.println("Enter name of product: ");
                String name=sc.next();
                System.out.println("Enter no of inventory: ");
                int inven=sc.nextInt();
                System.out.println("Enter price of the product: ");
                int price=sc.nextInt();
                sc.nextLine();
                System.out.println("Enter description of the product: ");
                String description=sc.nextLine();
                inventory.addProduct(name, inven, price, description);
            }
            else if(op==3){
                System.out.println("Enter the product id: ");
                int productId=sc.nextInt();
                System.out.println("Enter name of product: ");
                String name=sc.next();
                System.out.println("Enter no of inventory: ");
                int inven=sc.nextInt();
                System.out.println("Enter price of the product: ");
                int price=sc.nextInt();
                sc.nextLine();
                System.out.println("Enter description of the product: ");
                String description=sc.nextLine();
                inventory.updateProduct(productId, name, inven, price, description);
            }
            else if(op==4){
                System.out.println("The list of products are: ");
                inventory.getAllProducts();
            }
            else if(op==5){
                inventory.getAllOrders();
            }
            else if(op==6){
                inventory.placeOrder();
            }
            else if(op==7){
                inventory.outOfStocks();
            }
            else if(op==8){
                inventory.getAllPendingOrders();
            }
            else if(op==9){
                System.out.println("Enter the userId: ");
                int userId=sc.nextInt();
                inventory.getUserpendingOrder(userId);
            }
            else if(op==10){
                System.out.println("Enter the userId: ");
                int userId=sc.nextInt();
                inventory.getUserOrder(userId);
            }
            else{
                System.out.println("Enter the valid option!");
            }
        }
    }
}