package com.github.julianomachadoo.forumapi.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class IndexController {

    @GetMapping(value = "/")
    public void method(HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Location", "/swagger-ui.html");
        httpServletResponse.setStatus(302);
    }
}
