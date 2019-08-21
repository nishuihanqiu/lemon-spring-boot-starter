package com.lls.lemon.ds.controller;

import com.lls.lemon.ds.modal.Product;
import com.lls.lemon.ds.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 */

@RestController
@RequestMapping("/product")
public class ProduceController {

    @Autowired
    private ProductService productService;

    /**
     * Get product by id
     *
     * @param productId
     * @return
     * @throws Exception
     */
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") Long productId) throws Exception {
        return productService.select(productId);
    }

    /**
     * Get all product
     *
     * @return
     * @throws Exception
     */
    @GetMapping
    public List<Product> getAllProduct() {
        return productService.getAllProduct();
    }

    /**
     * Update product by id
     *
     * @param productId
     * @param newProduct
     * @return
     * @throws Exception
     */
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Long productId, @RequestBody Product newProduct) throws Exception {
        return productService.update(productId, newProduct);
    }

    /**
     * Delete product by id
     *
     * @param productId
     * @return
     * @throws Exception
     */
    @DeleteMapping("/{id}")
    public boolean deleteProduct(@PathVariable("id") long productId) throws Exception {
        return productService.delete(productId);
    }

    /**
     * Save product
     *
     * @param newProduct
     * @return
     * @throws Exception
     */
    @PostMapping
    public boolean addProduct(@RequestBody Product newProduct) throws Exception {
        return productService.add(newProduct);
    }
}
