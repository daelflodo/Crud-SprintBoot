package com.proyecto.crud.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Service
public class ProductService {

//    HashMap<String, Object> data = new HashMap<>();
    HashMap<String, Object> data;
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return this.productRepository.findAll();
//        return List.of(
//                new Product(
//                        1L,
//                        "Laptop",
//                        1500,
//                        LocalDate.of(2023, Month.DECEMBER, 22),
//                        2
//
//                )
//        );
    }

    @PostMapping
    public ResponseEntity<Object> newProduct(Product product) {
        Optional<Product> res = productRepository.findProductByName(product.getName());
        data = new HashMap<>();


        if (res.isPresent() && product.getId() == null) {
//            throw new IllegalStateException("Ya existe el producto");
            data.put("error", true);
            data.put("message", "Ya existe un producto con ese nombre");
            return new ResponseEntity<>(data, HttpStatus.CONFLICT);
        }
        data.put("message", "El producto se guardo con exito");
        if(product.getId() != null){
            data.put("message", "El producto se actualizo con exito");
        }
        productRepository.save(product);
        data.put("Data", product);

        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }

    public  ResponseEntity<Object>  deleteProduct(UUID id){
        data = new HashMap<>();
        boolean productFound = this.productRepository.existsById(id);
        if(!productFound) {
            data.put("error", true);
            data.put("message", "No existe un producto con ese id");
            return new ResponseEntity<>(data, HttpStatus.CONFLICT);
        }
        productRepository.deleteById(id);
        data.put("message", "Producto eliminado");
        return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
    }
}
