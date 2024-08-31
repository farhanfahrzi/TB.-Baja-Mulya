package com.tb.ch_sb_1_tb_baja_mulya.service.impl;

import com.tb.ch_sb_1_tb_baja_mulya.entity.Product;
import com.tb.ch_sb_1_tb_baja_mulya.repository.ProductRepository;
import com.tb.ch_sb_1_tb_baja_mulya.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        return productRepository.saveAndFlush(product);
    }

    @Override
    public Product getById(String id) {
        Optional<Product> optionalBranch = productRepository.findById(id);
        if(optionalBranch.isEmpty()){
            throw new RuntimeException("Branch Not Found");
        }
        return optionalBranch.get();
    }

    @Override
    public List<Product> getAll(String name) {
        if(name != null){
            return productRepository.findAllByNameLike("%"+ name +"%");
        }
        return productRepository.findAll();
    }

    @Override
    public Product update(Product product) {
        getById(product.getId());
        return productRepository.saveAndFlush(product);
    }

    @Override
    public void deleteById(String id) {
        Product currentProduct = getById(id);
        productRepository.delete(currentProduct);
    }
}
