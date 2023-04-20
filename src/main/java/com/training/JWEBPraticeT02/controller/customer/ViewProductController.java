package com.training.JWEBPraticeT02.controller.customer;

import com.training.JWEBPraticeT02.entity.Category;
import com.training.JWEBPraticeT02.entity.Product;
import com.training.JWEBPraticeT02.entity.ProductSize;
import com.training.JWEBPraticeT02.entity.Size;
import com.training.JWEBPraticeT02.repositories.CategoryRepository;
import com.training.JWEBPraticeT02.repositories.ProductRepository;

import com.training.JWEBPraticeT02.repositories.ProductSizeRepository;
import com.training.JWEBPraticeT02.repositories.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ViewProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductSizeRepository productSizeRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @ModelAttribute("categories")
    public List<Category> categories() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    @Autowired
    private SizeRepository sizeRepository;
    @GetMapping(value = {"/home"})
    public String home (final Model model, final HttpServletRequest request, final HttpServletResponse response)
            throws IOException {
//        ProductSearchModel searchModel = new ProductSearchModel();
//        searchModel.keyword = request.getParameter("keyword");
//        searchModel.setPage(getCurrentPage(request));
//        searchModel.categoryId = super.getInteger(request, "categoryId");
        List<Product> productLatest = new ArrayList<>();
        int i =0;
        for (Product product: productRepository.findLatestProducts())
        {
            if(i<6)
            {
                productLatest.add(product);
                i++;
            }
            else break;
        }

        model.addAttribute("productLatest",productLatest);
        List<Product> productHot = productRepository.findByHot(true);

        model.addAttribute("productHot",productHot);
       /* model.addAttribute("productsWithPaging", productService.search(searchModel));
        model.addAttribute("searchModel", searchModel);*/
        return "html/CustomerView/homepage";
    }


    @GetMapping(value = "/allproduct")
    public String allproduct (final Model model) throws IOException {
        List<Product> productList = productRepository.findAll();
        model.addAttribute("list",productList);
        return "html/CustomerView/allproduct";
    }

    @GetMapping(value = "/category/{categoryId}")
    public String productByCategory (final Model model,@PathVariable("categoryId") int categoryId) throws IOException {
        List<Product> productList = productRepository.findByCategoryId(categoryId);
        model.addAttribute("list",productList);
        return "html/CustomerView/allproduct";
    }

    @GetMapping(value = { "/detail/product/{productSeo}" })
    public String detail_product(final Model model, @PathVariable("productSeo") String productSeo) throws IOException {

//        ProductSearchModel searchModel = new ProductSearchModel();
//        searchModel.seo = productSeo;
//
//        PagerData<Products> products = productService.search(searchModel);
        List<Product> productList = productRepository.findBySeo(productSeo);
        Product product = productList.get(0);
        System.out.println(product.getId());
        System.out.println(product.getId());
        System.out.println(product.getId());
        System.out.println(product.getId());
        System.out.println(product.getId());
        List<Size> sizes = sizeRepository.sizeAvailable(product.getId());

        model.addAttribute("sizes",sizes);
        model.addAttribute("product", product);
        return "html/CustomerView/detail-product";
    }
}
