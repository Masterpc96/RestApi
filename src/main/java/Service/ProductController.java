package Service;

import Classes.Product;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;

@RestController
public class ProductController {

    ArrayList<Product> products = new ArrayList<>();



    // create
    @PostMapping(value = "/product/")
    public String add(@RequestBody Product product) {
        boolean added = products.add(product);
        try {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("data.txt"));
            stream.writeObject(products);
        } catch (IOException e) {
            return "Data cannot be saved";
        }
        if (added) return "Product successfully added";
        else return "Product cannot be added";

    }

    // addProduct?name=test&price=50&count=200
    @DeleteMapping(value = "/product/{id}")
    public String remove(@PathVariable int id) {
        Product removed = products.remove(id);
        try {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("data.txt"));
            stream.writeObject(products);
        } catch (IOException e) {
            return "Data cannot be saved";
        }
        if (removed != null) return "Product successfully removed\n" + removed;
        else return "Product cannot be removed ";
    }

    @PutMapping(value = "/product/{id}")
    public String update(@PathVariable int id, @RequestBody Product product) {
        product.setId((long)id);
        Product temp = products.set(id, product);
        try {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("data.txt"));
            stream.writeObject(products);
        } catch (IOException e) {
            return "Data cannot be saved";
        }
        if(temp != null) return "Product successfully updated\nBefore: " + temp + "\nafter: " + products.get(id);
        else return "Product cannot be updated";
    }

    @GetMapping(value = "/product/")
    public ArrayList<Product> read() {
        try {
            if(new File("data.txt").exists()){
                ObjectInputStream stream = new ObjectInputStream(new FileInputStream("data.txt"));
                products = (ArrayList<Product>) stream.readObject();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return products;
    }

    @GetMapping(value = "/product/{id}")
    public Product readSelected(@PathVariable int id) {
        try {
            ObjectInputStream stream = new ObjectInputStream(new FileInputStream("data.txt"));
            products = (ArrayList<Product>) stream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return products.get(id);
    }

}