package com.training.JWEBPraticeT02.controller.admin;

import com.training.JWEBPraticeT02.entity.Category;
import com.training.JWEBPraticeT02.entity.Size;
import com.training.JWEBPraticeT02.repositories.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class SizeController {
    @Autowired
    private SizeRepository sizeRepository;
    @GetMapping("/size/add")
    public String addSize(Model model) {

        if (model.getAttribute("size") == null) {
            Size size = new Size();

            model.addAttribute("size", size);
        }

        return "html/AdminView/sizeView/addsize";
    }


    @PostMapping("/size/add")
    public String validateSize(@ModelAttribute Size size,
                                   RedirectAttributes redirect) {
        sizeRepository.save(size);
        redirect.addFlashAttribute("message", "Thêm loại sản phẩm thành công");
        return "redirect:/size/list";
    }

    @GetMapping("/size/list")
    public String getList(Model model){

        List<Size> sizes = sizeRepository.findAll();
        model.addAttribute("list",sizes);

        return "html/AdminView/sizeView/sizeList";
    }

    @GetMapping("/size/edit/{id}")
    public  String editPage(@PathVariable("id") int id, Model model) {


        Optional<Size> sizeOptional = sizeRepository.findById(id);
        Size size = sizeOptional.get();

        model.addAttribute("size", size);
        return "html/AdminView/sizeView/addsize";
    }

    @PostMapping("/size/edit")
    public  String editSize(
            @ModelAttribute("size") Size size,
            RedirectAttributes redirectAttributes)
    {
        redirectAttributes.addFlashAttribute("message","Update information successfully");
        sizeRepository.save(size);

        return "redirect:/size/list";
    }

    @GetMapping("/size/delete/{id}")
    public  String delete(@PathVariable("id") int id, RedirectAttributes attributes) {
        sizeRepository.deleteById(id);
        attributes.addFlashAttribute("message", "Delete successfully");
        return "redirect:/size/list";
    }
}
