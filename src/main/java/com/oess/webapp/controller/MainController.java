package com.oess.webapp.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String mainScreen(Map<String, Object> model) {
	model.put("name", "Java");
	return "main";
    }
    
}
