package com.example.springJWT.Controller;

import com.example.springJWT.DTO.ReqRes;
import com.example.springJWT.Entity.Product;
import com.example.springJWT.Repository.ProductRepo;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminUsers {

    @Autowired
    private ProductRepo productRepo;

    @GetMapping("/public/product")
    public ResponseEntity<Object> getAllProducts()
    {
        return ResponseEntity.ok(productRepo.findAll());
    }

    @PostMapping("/admin/saveProduct")
    public ResponseEntity<Object> saveProducts(@RequestBody Product productRequest)
    {
        Product productToSave=new Product();
        productToSave.setName(productRequest.getName());
        return  ResponseEntity.ok(productRepo.save(productToSave));
    }

    @GetMapping("/user/alone")
    public ResponseEntity<Object> userAlone()
    {
        return ResponseEntity.ok("Only users can Access this API");
    }

    @GetMapping("/adminUser/both")
    public ResponseEntity<Object> bothUser()
    {
        return ResponseEntity.ok("Both admin and User can access this API");
    }
}
