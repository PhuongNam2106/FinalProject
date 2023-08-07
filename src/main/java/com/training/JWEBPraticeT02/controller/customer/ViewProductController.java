package com.training.JWEBPraticeT02.controller.customer;

import com.training.JWEBPraticeT02.controller.BaseController;
import com.training.JWEBPraticeT02.entity.*;
import com.training.JWEBPraticeT02.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ViewProductController extends BaseController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductSizeRepository productSizeRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductImageRepository productImageRepository;

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

    private Page<Product> pagingProduct(int pageIndex){
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        return productRepository.findAll(pageable);
    }

    @GetMapping(value = { "/allproduct" })
    public String allproduct(final Model model,
                              @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
                              @RequestParam(value = "id", required = false, defaultValue = "") String id,
                              @RequestParam(value = "keyword", required = false) String keyword) throws IOException {
        int pageSize = 16;
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        Page<Product> products ;
        if("".equals(keyword)||keyword==null)
        {
            products = productRepository.findAll(pageable);
        }
        else
        {
            products = productRepository.findByTitleContainingIgnoreCase(keyword,pageable);
        }

        List<Product> productList = productRepository.findAll();
        model.addAttribute("currentPage", pageIndex);
        model.addAttribute("list",products);
        return "html/CustomerView/allproduct";
    }

    @GetMapping(value = "/category/{categoryId}")
    public String productByCategory (final Model model,@PathVariable("categoryId") int categoryId,
                                     @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
                                     @RequestParam(value = "id", required = false, defaultValue = "") String id,
                                     @RequestParam(value = "keyword", required = false) String keyword) throws IOException {
        int pageSize = 16;
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        Page<Product> productList = productRepository.findByCategoryId(categoryId,pageable);
        model.addAttribute("currentPage", pageIndex);
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
        List<Size> sizes = sizeRepository.sizeAvailable(product.getId());
        List<Product_Image> product_images = productImageRepository.findByProduct_Id(product.getId());
        Size size =new Size();
        model.addAttribute("sizes",sizes);
        model.addAttribute("product", product);
        model.addAttribute("productImage",product_images);
        model.addAttribute("size",size);

        return "html/CustomerView/detail-product";
    }
}
