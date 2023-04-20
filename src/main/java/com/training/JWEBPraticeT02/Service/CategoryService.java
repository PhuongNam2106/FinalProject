package com.training.JWEBPraticeT02.Service;

import com.training.JWEBPraticeT02.entity.Category;
import com.training.JWEBPraticeT02.model.CategorySearchModel;
import org.springframework.stereotype.Service;


@Service
public class CategoryService extends BaseService<Category>{

	@Override
	protected Class<Category> clazz() {
		// TODO Auto-generated method stub
		return Category.class;
	}
	
	public PagerData<Category> search(CategorySearchModel searchModel) {

		// khởi tạo câu lệnh
		String sql = "SELECT * FROM tbl_contact  WHERE 1=1";

		if (searchModel != null) {
			// tìm kiếm theo id
			if(searchModel.categoryId != null) {
				sql += " and id = " + searchModel.categoryId;
			}
			
		
			
			// tim kiem san pham theo seachText
			/*
			 * if (!StringUtils.isEmpty(searchModel.keyword)) { sql +=
			 * " and (p.title like '%" + searchModel.keyword + "%'" +
			 * " or p.detail_description like '%" + searchModel.keyword + "%'" +
			 * " or p.short_description like '%" + searchModel.keyword + "%')"; }
			 */
		}

		// chi lay san pham chua xoa
//				sql += " and p.status=1 ";
		return executeByNativeSQL(sql, searchModel == null ? 0 : searchModel.getPage());
		
	}

}
