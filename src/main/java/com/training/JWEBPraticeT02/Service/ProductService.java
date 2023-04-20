package com.training.JWEBPraticeT02.Service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.transaction.Transactional;

import com.training.JWEBPraticeT02.entity.Product;
import com.training.JWEBPraticeT02.entity.Product_Image;
import com.training.JWEBPraticeT02.model.ProductSearchModel;
import com.training.JWEBPraticeT02.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.util.StringUtils;
import com.github.slugify.Slugify;

@Service
public class ProductService extends BaseService<Product> {

	public String UP_LOAD_FOLDER_ROOT = "D:/Final Project/JWEB.Pratice.T02_NamNP30/src/main/resources/static/upload/";
	@Autowired
	private ProductRepository productRepository;
	@Autowired
 	private ProductImagesService productImagesService ;
	/*@Override
	protected Class<Products> clazz() {
		// TODO Auto-generated method stub
		return Products.class;
	}*/
	
	private boolean isEmptyUploadFile(MultipartFile[] images) {   
		if (images == null || images.length <= 0)
			return true;

		if (images.length == 1 && images[0].getOriginalFilename().isEmpty())
			return true;

		return false;
	}  

	private boolean isEmptyUploadFile(MultipartFile avatar) {
		return avatar == null || avatar.getOriginalFilename().isEmpty();
	}
	
	
	@Transactional
	public Product add(Product p, MultipartFile avatar, MultipartFile[] productImage)
	throws IllegalStateException, IOException{
		//kiểm tra xem admin có đẩy avatar lên không??
		
		if(!isEmptyUploadFile(avatar))
		{
			String pathToFile = UP_LOAD_FOLDER_ROOT + "product/avatar/" + avatar.getOriginalFilename();
			//luư avatar vào đường dẫn trên
			avatar.transferTo(new File(pathToFile));
			p.setAvatar("product/avatar/" + avatar.getOriginalFilename());
		}
		
		// có đẩy pictures(product_images) ???
				if (!isEmptyUploadFile(productImage)) {

					// nếu admin đẩy lên thì duyệt tất cả file đẩy lên và lưu trên server
					for (MultipartFile pic : productImage) {
						// lưu ảnh admin đẩy lên vào server
						pic.transferTo(new File(UP_LOAD_FOLDER_ROOT + "product/pictures/" + pic.getOriginalFilename()));

						// tạo mới 1 bản ghi product_images
						Product_Image pi = new Product_Image();
						pi.setPath("product/pictures/" + pic.getOriginalFilename());
						pi.setTitle(pic.getOriginalFilename());

						p.addProductImages(pi);
					}
				}
			//tạo seo
			p.setSeo(new Slugify().slugify(p.getTitle()));
			return super.saveOrUpdate(p);
		
	}
	
	//cập nhật 
	@Transactional
	public Product update(Product p, MultipartFile avatar, MultipartFile[] productImage)
			throws IllegalStateException, IOException {

		// láº¥y thÃ´ng tin cÅ© cá»§a product theo id
		Optional<Product> productOptional = productRepository.findById(p.getId()); /*super.getById(p.getId());*/
		Product productInDb = productOptional.get();
		// cÃ³ Ä‘áº©y avartar ??? => xÃ³a avatar cÅ© Ä‘i vÃ  thÃªm avatar má»›i
		if (!isEmptyUploadFile(avatar)) {
			// xÃ³a avatar trong folder lÃªn
			new File(UP_LOAD_FOLDER_ROOT + productInDb.getAvatar()).delete();

			// add avartar moi
			avatar.transferTo(new File(UP_LOAD_FOLDER_ROOT + "product/avatar/" + avatar.getOriginalFilename()));
			p.setAvatar("product/avatar/" + avatar.getOriginalFilename());
		} else {
			// su dung lai avatar cu
			p.setAvatar(productInDb.getAvatar());
		}

		// cÃ³ Ä‘áº©y pictures ???
		if (!isEmptyUploadFile(productImage)) {

			// xÃ³a pictures cÅ©
			if (productInDb.getProduct_Images() != null && productInDb.getProduct_Images().size() > 0) {
				for (Product_Image opi : productInDb.getProduct_Images()) {
					// xÃ³a avatar trong folder lÃªn
					new File(UP_LOAD_FOLDER_ROOT + opi.getPath()).delete();

					// xÃ³a dá»¯ liá»‡u trong database
					productImagesService.delete(opi);
				}
			}

			// thÃªm pictures má»›i
			for (MultipartFile pic : productImage) {
				pic.transferTo(new File(UP_LOAD_FOLDER_ROOT + "product/pictures/" + pic.getOriginalFilename()));

				Product_Image pi = new Product_Image();
				pi.setPath("product/pictures/" + pic.getOriginalFilename());
				pi.setTitle(pic.getOriginalFilename());

				p.addProductImages(pi);
			}
		}

		//táº¡o seo
		p.setSeo(new Slugify().slugify(p.getTitle()));

		// lÆ°u vÃ o database
		return super.saveOrUpdate(p);
	}
	
	public PagerData<Product> search(ProductSearchModel searchModel) {

		// khởi tạo câu lệnh
		String sql = "SELECT * FROM tbl_products p WHERE 1=1";

		if (searchModel != null) {
			// tìm kiếm theo category
			if(searchModel.categoryId != null) {
				sql += " and category_id = " + searchModel.categoryId;
			}
		
			//tìm theo seo
			if (!StringUtils.isEmpty(searchModel.seo)) {
				sql += " and p.seo = '" + searchModel.seo + "'";
			}

			// tìm kiếm sản phẩm hot
			if (searchModel.isHot != null) {
//				sql += " and p.is_hot = '" + searchModel.seo + "'";
			}
			
			// tim kiem san pham theo seachText
			if (!StringUtils.isEmpty(searchModel.keyword)) {
				sql += " and (p.title like '%" + searchModel.keyword + "%'" + " or p.detail_description like '%"
						+ searchModel.keyword + "%'"  + searchModel.keyword + "%')";
			}
		}

		// chi lay san pham chua xoa
//				sql += " and p.status=1 ";
		return executeByNativeSQL(sql, searchModel == null ? 0 : searchModel.getPage());
		
	}

	@Override
	protected Class<Product> clazz() {
		return null;
	}
}
