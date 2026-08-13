package main;

import java.util.HashMap;
import java.util.Map;

public class ProductService {
    public static Long count=1L;
    CategoryService categoryService=new CategoryService();

    HashMap<String,Product> map=new HashMap<>();
    
    public Product createProduct(String name,String description,String category,int price,int inventory){
        Product product=new Product();
        Category category2=categoryService.getCategoryByName(category);
        product.setId(count++);
        product.setDescription(description);
        product.setInventory(inventory);
        product.setCategory(category2);
        product.setProductName(name);
        product.setPrice(price);

        categoryService.addProductsToCategory(category, product);
        map.put(name.toLowerCase(), product);
        return product;
    }

    public Product getProductByName(String name) {
        Product product=map.get(name.toLowerCase());
        if(product==null){
            System.out.println("Product not exists!");
            return null;
        }
        return product;
    }

    public Product updateProduct(String name,String description,String category,int price,int inventory){
        Product product=getProductByName(name);
        if(product==null){
            return createProduct(name, description, category, price, inventory);
        }
        Category category2=categoryService.getCategoryByName(category);
        
        product.setDescription(description);
        product.setInventory(inventory);
        product.setCategory(category2);
        product.setProductName(name);
        product.setPrice(price);

        categoryService.addProductsToCategory(category, product);
        map.put(name.toLowerCase(), product);
        return product;
    }

    public void deleteProduct(String name){
        Product product=getProductByName(name);
        if(product==null){
            System.out.println("Deletion failed, Product Not Found!");
            return;
        }
        map.remove(product);
        System.out.println("Product deleted successfully!");
    }

    public void displayAllProducts(){
        for(Map.Entry<String,Product> i:map.entrySet()){
           System.out.println(i.getValue().toString());
        }
    }

    
}
