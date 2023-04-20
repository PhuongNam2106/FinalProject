//package com.training.JWEBPraticeT02.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.List;
//
//@Controller
//public class AddFresherController {
//    @Autowired
//    FresherRepository fresherRepository;
//    @Autowired
//    ClazzRepository clazzRepository;
//    @ModelAttribute("clazzes")
//    public List<Clazz> clazzes() {
//        List<Clazz> clazzes = clazzRepository.findAll();
//        return clazzes;
//    }
//    @GetMapping("/add-fresher")
//    public String addFresher(Model model) {
//
//        if (model.getAttribute("fresher") == null) {
//            Fresher fresher = new Fresher();
//
//            model.addAttribute("fresher", fresher);
//        }
//
//        return "html/add";
//    }
//
//
//    @PostMapping("/add-fresher")
//    public String validateFresher(@ModelAttribute Fresher fresher,
//                               RedirectAttributes redirect) {
//        fresherRepository.save(fresher);
//        redirect.addFlashAttribute("message", "Your data has been successfully save!!");
//        return "redirect:list";
//    }
//}
