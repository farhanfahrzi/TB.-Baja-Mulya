package com.tb.ch_sb_1_tb_baja_mulya.service.impl;

import com.tb.ch_sb_1_tb_baja_mulya.dto.request.NewProductRequest;
import com.tb.ch_sb_1_tb_baja_mulya.entity.Product;
import com.tb.ch_sb_1_tb_baja_mulya.repository.ProductRepository;
import com.tb.ch_sb_1_tb_baja_mulya.service.ProductService;
import com.tb.ch_sb_1_tb_baja_mulya.utils.Validationutil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final Validationutil validationutil;

    @Override
    public Product create(NewProductRequest productRequest) {
        // sebelum kita save data ke database, kita panggil dulu ValidationUtil. biar validasi di NewProductRequest dapat di tangkap dan di lempar errornya
        validationutil.validate(productRequest);
        // setelah validation baru di masukkan datanya ke DB
        Product product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .build();
        return productRepository.saveAndFlush(product);
    }


    @Override
    public Product getById(String id) {
        Optional<Product> optionalBranch = productRepository.findById(id);
        if(optionalBranch.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product Not Found");
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
