package com.training.JWEBPraticeT02.controller.admin;

import com.training.JWEBPraticeT02.Service.CategoryService;
import com.training.JWEBPraticeT02.entity.Category;
import com.training.JWEBPraticeT02.repositories.CategoryRepository;
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
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/admin/category/add")
    public String addCategory(Model model) {

        if (model.getAttribute("category") == null) {
            Category category = new Category();

            model.addAttribute("category", category);
        }

        return "html/AdminView/categoryView/addcategory";
    }


    @PostMapping("/admin/category/add")
    public String validateCategory(@ModelAttribute Category category,
                                  RedirectAttributes redirect) {
        categoryRepository.save(category);
        redirect.addFlashAttribute("message", "Thêm loại sản phẩm thành công");
        return "redirect:list";
    }

    @GetMapping("/admin/category/list")
    public String getList(Model model){

        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("list",categories);

        return "html/AdminView/categoryView/categoryList";
    }

    @GetMapping("/admin/category/delete/{id}")
    public  String delete(@PathVariable("id") int id, RedirectAttributes attributes) {
        //đoạn này là để xử lý khi xóa 1 category thì tất sp thuộc category đó sẽ được chuyển về category "Khác"
        categoryService.updateCategoryIdToDefaultCategoryId(id);
        categoryRepository.deleteById(id);
        attributes.addFlashAttribute("message", "Delete successfully");

        return "redirect:/admin/category/list";
    }

    @GetMapping("/admin/category/edit/{id}")
    public  String editPage(@PathVariable("id") int id, Model model) {


        Optional<Category> categoryOptional = categoryRepository.findById(id);
        Category category = categoryOptional.get();

        model.addAttribute("category", category);
        return "html/AdminView/categoryView/editcategory";
    }

    @PostMapping("/admin/category/edit")
    public  String editFresher(
            @ModelAttribute("category") Category category,
            RedirectAttributes redirectAttributes)
    {
        redirectAttributes.addFlashAttribute("message","Đã thêm thành công");
        categoryRepository.save(category);

        return "redirect:/admin/category/list";
    }
}
