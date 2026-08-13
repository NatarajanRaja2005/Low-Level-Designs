package main;

import java.util.*;
import java.util.*;

public class CategoryService {
    public static Long count=1L;

    HashMap<String,Category> map=new HashMap<>();

    public Category addCategory(String name){
        if(map.containsKey(name.toLowerCase())){
            return map.get(name);
        }
        Category category=new Category();
        category.setId(count++);
        category.setCategoryName(name);
        map.put(name.toLowerCase(), category);
        return category;
    }

    public void removeCategory(String name){
        if(map.containsKey(name.toLowerCase())){
            map.remove(name);
            System.out.println("Category removed successfully!");
            return;
        }
        System.out.println("Category not found!");
    }

    public Category addProductsToCategory(String name,Product product){
        Category category=category=map.get(name.toLowerCase());
        if(category==null){
            category=addCategory(name);
        }
        category.getProducts().add(product);
        System.out.println("Products added successfully!");
        return category;
    }


    public Category getCategoryByName(String name){
        Category category=map.get(name.toLowerCase());
        if(map.get(name.toLowerCase())==null){
            return addCategory(name);
        }
        return category;
    }

    public void displayAllCategories(){
        for(Map.Entry i:map.entrySet()){
            i.getValue().toString();
        }
    }
}
