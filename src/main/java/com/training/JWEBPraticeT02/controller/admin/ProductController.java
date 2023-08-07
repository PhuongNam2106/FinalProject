package com.training.JWEBPraticeT02.controller.admin;

import com.training.JWEBPraticeT02.Service.ProductService;
import com.training.JWEBPraticeT02.controller.BaseController;
import com.training.JWEBPraticeT02.entity.Category;
import com.training.JWEBPraticeT02.entity.Product;
import com.training.JWEBPraticeT02.entity.ProductSize;
import com.training.JWEBPraticeT02.entity.Size;
import com.training.JWEBPraticeT02.model.ProductSearchModel;
import com.training.JWEBPraticeT02.repositories.CategoryRepository;
import com.training.JWEBPraticeT02.repositories.ProductRepository;

import com.training.JWEBPraticeT02.repositories.ProductSizeRepository;
import com.training.JWEBPraticeT02.repositories.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController extends BaseController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;
    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @ModelAttribute("categories")
    public List<Category> categories() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }
    @GetMapping(value = { "/admin/product/add" })
    public String home(final Model model)
            throws IOException {

        Product product = new Product();
        List<Size> sizes = sizeRepository.findAll();
        List<ProductSize> productSize = new ArrayList<>();
        for (Size size: sizes) {
            productSize.add(new ProductSize(size));
        }

        ProductSize prdSize = new ProductSize();
        model.addAttribute("product", product);
        model.addAttribute("sizes",sizes);
        model.addAttribute("productSize",productSize);
        model.addAttribute("prdSize",prdSize);

        return "html/AdminView/productview/addproduct";
    }

    @PostMapping(value = { "/admin/product/add" })
    public String add_product(final Model model,
                              final @ModelAttribute("product") Product product,
                              final @RequestParam("avatarfile") MultipartFile avatarfile,
                              final @RequestParam("productImage") MultipartFile[] productImage,
                              @ModelAttribute("productSize") ArrayList<ProductSize> productSize) throws IOException {
        model.addAttribute("TB", "Đã thêm sản phẩm " + product.getTitle() + " vào hệ thống");

        if (product.getId() == null || product.getId() <= 0) { // thêm mới
            System.out.println("add");
            product.setPriceSale(product.getPrice(),product.getSale());
            productService.add(product, avatarfile, productImage);

        } else { // chỉnh sửa
            System.out.println("update");
            product.setPriceSale(product.getPrice(),product.getSale());
            productService.update(product, avatarfile, productImage);

        }

        return "redirect:/admin/product/list";
    }

    @GetMapping(value = { "/admin/product/add/{productId}" })
    public String adminProductEdit(final Model model, final HttpServletRequest request,
                                   final HttpServletResponse response, @PathVariable("productId") int productId) throws IOException {
        Optional<Product> productOptional = productRepository.findById(productId);

        Product product = productOptional.get();
        List<Size> sizes = sizeRepository.findAll();
        List<ProductSize> productSize = productSizeRepository.findByProductId(productId);
        ProductSize prdSize = new ProductSize();
        model.addAttribute("product", product);
        model.addAttribute("productSize",productSize);
        model.addAttribute("prdSize",prdSize);
        model.addAttribute("sizes",sizes);
        return "html/AdminView/productview/addproduct";
    }


    private Page<Product> pagingProduct(int pageIndex){
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        return productRepository.findAll(pageable);
    }
    @ExceptionHandler( MissingServletRequestParameterException.class)
    @GetMapping(value = { "/admin/product/list" })
    public String adminProductList(final Model model, final HttpServletRequest request,
                                   final HttpServletResponse response,
                                   @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
                                   @RequestParam(value = "id", required = false, defaultValue = "") String id,
                                   @RequestParam(value = "field", required = false) Integer field,
                                   @RequestParam(value = "keyword", required = false) String keyword
                                   ) throws IOException {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        Page<Product> products ;

        Page<Product> page;
        if("".equals(keyword)&&field==0||field==null)
        {
            products = productRepository.findAll(pageable);
        }
        else if("".equals(keyword)&&field!=0)
        {

            products = productRepository.findByCategoryId(field ,pageable);
        }
        else
        {
            products = productRepository.findByCategoryIdAndTitleContainingIgnoreCase(field,keyword ,pageable);
        }


        List<Size> sizeList = sizeRepository.findAll();
        List<ProductSize> productSizeListNuLLProductID = productSizeRepository.findNullProductIdAndSizeId();
        Product productLast = productRepository.findTopByOrderByIdDesc();

        if(productSizeListNuLLProductID.size()>0)
        {
            for(ProductSize productSize : productSizeListNuLLProductID)
            {

                productSize.setProduct(productLast);
                System.out.println("đã add" + productLast.getId());
                productSizeRepository.save(productSize);
            }
            for(int i=0; i<sizeList.size();i++)
            {
                productSizeListNuLLProductID.get(i).setSize(sizeList.get(i));
                productSizeRepository.save(productSizeListNuLLProductID.get(i));
            }
        }

        model.addAttribute("products",products);
        model.addAttribute("currentPage", pageIndex);
        List<Product> productList = productRepository.findAll();
        model.addAttribute("productList",productList);



        return "html/AdminView/productview/productlist";
    }


    @RequestMapping(value = {"/admin/product/delete/{productId}"},method = RequestMethod.GET)
    public String Delete(
                         @PathVariable("productId") int productId) throws IOException{

        productRepository.deleteById(productId);
        return "redirect:/admin/product/list";
    }

    public boolean max(int a, int b) {
            if(a > b) {
                return true;
            }
            return false;
    }
}
