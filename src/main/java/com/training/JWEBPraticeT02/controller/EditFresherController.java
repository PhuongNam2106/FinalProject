//package com.training.JWEBPraticeT02.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//public class EditFresherController {
//    @Autowired
//    FresherRepository fresherRepository;
//    @Autowired
//    ClazzRepository clazzRepository;
//    @ModelAttribute("clazzes")
//    public List<Clazz> clazzes() {
//        List<Clazz> clazzes = clazzRepository.findAll();
//        return clazzes;
//    }
//    @GetMapping("/edit/{id}")
//    public  String editPage(@PathVariable("id") int id, Model model) {
//
//
//        Optional<Fresher> fresherOptional = fresherRepository.findById(id);
//        Fresher fresher = fresherOptional.get();
//
//        model.addAttribute("fresher", fresher);
//        return "html/edit";
//    }
//
//    @PostMapping("/edit")
//    public  String editFresher(
//            @ModelAttribute("fresher") Fresher fresher,
//            RedirectAttributes redirectAttributes)
//    {
//        redirectAttributes.addFlashAttribute("message","Update information successfully");
//        fresherRepository.save(fresher);
//
//        return "redirect:/list";
//    }
//}
