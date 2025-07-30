package com.pooju.security.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sekoority")
public class HelloController {

    private List<Products> productsList;

    public HelloController() {
        productsList=new ArrayList<>();
    }

    @GetMapping("/hi")
    public String hello(){
        return "Hi goois";
    }

    @GetMapping("products")
    public Products getProducts(){
        return new Products("jetti", 6,100.0);
    }

    @PostMapping("add")
    public List<Products> addProduct(@RequestBody List<Products> products){
        productsList.addAll(products);
        return productsList;
    }

    @GetMapping("/csrf")
    public CsrfToken getCsrf(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");

    }


}
