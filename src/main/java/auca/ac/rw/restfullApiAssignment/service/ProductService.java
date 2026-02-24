package auca.ac.rw.restfullApiAssignment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.rw.restfullApiAssignment.modal.Product;
import auca.ac.rw.restfullApiAssignment.repository.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepo; 

    public String saveProduct(Product product){
        if (product.getId() == null) {
            return "Product id is required.";
        }

        Optional<Product> checkProduct = productRepo.findById(product.getId());
        
        if(checkProduct.isPresent()){
            return "Product with id "+ product.getId() + " already exists.";
        }else{
                 productRepo.save(product);
                 return "Product saved successfully.";
        }
       
    }

    public List<Product> getAllProducts() {
        // fetch everything; the controller decides how to present it
        return productRepo.findAll();
    }

    public Product findById(Long id) {
        return productRepo.findById(id).orElse(null);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = findById(id);

        if (existingProduct == null) {
            return null;
        }

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setStockQuantity(updatedProduct.getStockQuantity());

        return productRepo.save(existingProduct);
    }

    public boolean deleteProduct(Long id) {
        if (!productRepo.existsById(id)) {
            return false;
        }

        productRepo.deleteById(id);
        return true;
    }
}
