package com.training.JWEBPraticeT02.Service;

import com.training.JWEBPraticeT02.entity.Product_Image;
import org.springframework.stereotype.Service;


@Service
public class ProductImagesService extends BaseService<Product_Image>{

	@Override
	protected Class<Product_Image> clazz() {
		// TODO Auto-generated method stub
		return Product_Image.class;
	}

}
