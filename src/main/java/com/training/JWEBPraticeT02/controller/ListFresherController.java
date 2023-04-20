//package com.training.JWEBPraticeT02.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.List;
//
//@Controller
//public class ListFresherController {
//    @Autowired
//    FresherRepository fresherRepository;
//    @GetMapping("/list")
//    public String getList(Model model){
//
//        List<Fresher> freshers = fresherRepository.findAll();
//        model.addAttribute("list",freshers);
//
//        return "html/list";
//    }
//}
