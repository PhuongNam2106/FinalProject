//package com.training.JWEBPraticeT02.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//public class DeleteFresherController {
//    @Autowired
//    FresherRepository fresherRepository;
//    @Autowired
//    ClazzRepository clazzRepository;
//    @GetMapping("/delete/{id}")
//    public  String delete(@PathVariable("id") int id, RedirectAttributes attributes) {
//        fresherRepository.deleteById(id);
//        attributes.addFlashAttribute("message", "Delete successfully");
//        return "redirect:/list";
//
//
//    }
//}
