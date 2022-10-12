package com.example.dewu.api;

import com.example.dewu.model.Paging;
import com.example.dewu.model.Product;
import com.example.dewu.model.Result;
import com.example.dewu.param.BasePageParam;
import com.example.dewu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/product")
@Controller
public class ProductApi {

    @Autowired
    private ProductService productService;

    @ResponseBody
    @GetMapping("/page")
    public Result<Paging<Product>> pageQuery(@RequestBody BasePageParam param) {
        Result<Paging<Product>> result = new Result<>();
        result.setSuccess(true);
        result.setData(productService.pageQueryProduct(param));
        return result;
    }
}
