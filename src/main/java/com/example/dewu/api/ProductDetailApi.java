package com.example.dewu.api;

import com.example.dewu.model.ProductDetail;
import com.example.dewu.model.Result;
import com.example.dewu.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/productdetail")
public class ProductDetailApi {

    @Autowired
    private ProductDetailService productDetailService;

    @GetMapping("/productId")
    @ResponseBody
    public Result<List<ProductDetail>> getProductDetails(@RequestParam("productId") String productId) {
        Result<List<ProductDetail>> result = new Result<>();
        List<ProductDetail> productDetails = productDetailService.getByProductId(productId);
        result.setData(productDetails);
        result.setSuccess(true);
        return result;
    }
}
