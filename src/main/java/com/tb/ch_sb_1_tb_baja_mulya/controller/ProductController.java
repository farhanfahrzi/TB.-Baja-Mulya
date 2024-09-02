package com.tb.ch_sb_1_tb_baja_mulya.controller;

import com.tb.ch_sb_1_tb_baja_mulya.constant.APIUrl;
import com.tb.ch_sb_1_tb_baja_mulya.constant.ConstantMessage;
import com.tb.ch_sb_1_tb_baja_mulya.dto.request.NewProductRequest;
import com.tb.ch_sb_1_tb_baja_mulya.dto.response.CommonResponse;
import com.tb.ch_sb_1_tb_baja_mulya.entity.Product;
import com.tb.ch_sb_1_tb_baja_mulya.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.PRODUCT_API)
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<CommonResponse<Product>> createNewProduct(@RequestBody NewProductRequest product) {
        Product newProduct = productService.create(product);

        CommonResponse<Product> commonResponse = CommonResponse.<Product>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully Create New Product")
                .data(newProduct)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commonResponse);
    }

    @GetMapping(path = APIUrl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> getById(@PathVariable String id){
        Product product = productService.getById(id);
        CommonResponse<Product> commonResponse = CommonResponse.<Product>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully Fetch Data")
                .data(product)
                .build();
        return ResponseEntity.ok(commonResponse);
    }


    @GetMapping
    public ResponseEntity<CommonResponse<List<Product>>> getAllProduct(@RequestParam(name = "name", required = false) String name){
        List<Product> productList = productService.getAll(name);

        // setting paginationnya

        CommonResponse<List<Product>> commonResponse = CommonResponse.<List<Product>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully Get All Data")
                .data(productList)
                .build();

        return ResponseEntity.ok(commonResponse);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<Product>> updateProduct(@RequestBody Product product){
        Product updateProduct = productService.update(product);

        CommonResponse<Product> commonResponse = CommonResponse.<Product>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully Update Data")
                .data(updateProduct)
                .build();

        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping(path = APIUrl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> deleteById(@PathVariable String id){
        productService.deleteById(id);
        String msg = ConstantMessage.DELETESUCCESS + id;
        CommonResponse<?> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(msg)
                .build();

        return ResponseEntity.ok(commonResponse);
    }


}
