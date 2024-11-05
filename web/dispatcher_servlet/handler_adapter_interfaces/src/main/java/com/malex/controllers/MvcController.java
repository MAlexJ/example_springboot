package com.malex.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MvcController {


    @GetMapping("/greeting")
    public ModelAndView getEmployeeName() {
        ModelAndView model = new ModelAndView("greeting");
        model.addObject("message", "Madhwal");
        return model;
    }

}
