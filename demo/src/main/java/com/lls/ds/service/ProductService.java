package com.lls.ds.service;

import com.lls.ds.mapper.ProductDao;
import com.lls.ds.modal.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Product service for handler logic of product operation
 *
 */

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    /**
     * Get product by id
     * If not found product will throw Exception
     *
     * @param productId
     * @return
     * @throws Exception
     */
    public Product select(long productId) throws Exception {
        Product product = productDao.select(productId);
        if (product == null) {
            throw new Exception("Product:" + productId + " not found");
        }
        return product;
    }

    /**
     * Update product by id
     * If update failed will throw Exception
     *
     * @param productId
     * @param newProduct
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = DataAccessException.class)
    public Product update(long productId, Product newProduct) throws Exception {

        if (productDao.update(newProduct) <= 0) {
            throw new Exception("Update product:" + productId + "failed");
        }
        return newProduct;
    }

    /**
     * Add product to DB
     *
     * @param newProduct
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = DataAccessException.class)
    public boolean add(Product newProduct) throws Exception {
        Integer num = productDao.insert(newProduct);
        if (num <= 0) {
            throw new Exception("Add product failed");
        }
        return true;
    }

    /**
     * Delete product from DB
     *
     * @param productId
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = DataAccessException.class)
    public boolean delete(long productId) throws Exception {
        Integer num = productDao.delete(productId);
        if (num <= 0) {
            throw new Exception("Delete product:" + productId + "failed");
        }
        return true;
    }

    /**
     * Get all product
     *
     * @return
     */
    public List<Product> getAllProduct() {
        return productDao.getAllProduct();
    }
}
