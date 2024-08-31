package com.tb.ch_sb_1_tb_baja_mulya.controller;

import com.tb.ch_sb_1_tb_baja_mulya.constant.APIUrl;
import com.tb.ch_sb_1_tb_baja_mulya.constant.ConstantMessage;
import com.tb.ch_sb_1_tb_baja_mulya.entity.Product;
import com.tb.ch_sb_1_tb_baja_mulya.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.PRODUCT_API)
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public Product create(@RequestBody Product product){
        return productService.create(product);
    }

    @GetMapping(path = APIUrl.PATH_VAR_ID)
    public Product getById(@PathVariable String id){
        return productService.getById(id);
    }

    @GetMapping
    public List<Product> getAll(@RequestParam(name = "name", required = false)String name){
        return productService.getAll(name);
    }

    @PutMapping
    public Product update (@RequestBody Product product){
        return productService.update(product);
    }

    @DeleteMapping(path = APIUrl.PATH_VAR_ID)
    public String delete(@PathVariable String id){
        productService.deleteById(id);
        return ConstantMessage.DELETESUCCESS+id;
    }

}
