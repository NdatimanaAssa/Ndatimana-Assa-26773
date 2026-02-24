package auca.ac.rw.restfullApiAssignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import auca.ac.rw.restfullApiAssignment.modal.Product;
import auca.ac.rw.restfullApiAssignment.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {


    @Autowired
    private ProductService productserve;


    @PostMapping(value = "/addProduct", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addProduct(@RequestBody Product product){

    //public ResponseEntity<>
        String response = productserve.saveProduct(product);

        if(response.equals("Product saved successfully.")){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

    @GetMapping (value = "/getAllProducts", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllProducts(){
        List<Product> products = productserve.getAllProducts();

        if (products.isEmpty()){
            return new ResponseEntity<>("no products found.", HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Product product = productserve.findById(id);

        if (product == null) {
            return new ResponseEntity<>("Product with id " + id + " not found.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        boolean deleted = productserve.deleteProduct(id);

        if (!deleted) {
            return new ResponseEntity<>("Product with id " + id + " not found.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Product deleted successfully.", HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product updateProduct ){
       Product product = productserve.updateProduct(id, updateProduct);

       if (product == null) {
        return new ResponseEntity<>("Product with id " + id + " not found.", HttpStatus.NOT_FOUND);
       }

       return new ResponseEntity<>(product, HttpStatus.OK);
    }

  
 } 
