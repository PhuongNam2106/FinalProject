package com.training.JWEBPraticeT02.Service;

import com.training.JWEBPraticeT02.entity.SaleOder;
import com.training.JWEBPraticeT02.model.SaleOrderSearchModel;
import com.training.JWEBPraticeT02.repositories.SaleOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
public class SaleOrderService extends BaseService<SaleOder>{
	@Autowired
	SaleOrderRepository saleOrderRepository;

	@Override
	protected Class<SaleOder> clazz() {
		// TODO Auto-generated method stub
		return SaleOder.class;
	}

	 public PagerData<SaleOder> search(SaleOrderSearchModel searchModel) {

	        // khởi tạo câu lệnh
	        String sql = "SELECT * FROM tbl_saleorder s WHERE 1=1";

	        if (searchModel != null) {
	            //tìm theo seo
	            if (!StringUtils.isEmpty(searchModel.getSeo())) {
	                sql += " and s.seo = '" + searchModel.getSeo() + "'";
	            }

	            //tìm theo Id
	            if (!StringUtils.isEmpty(searchModel.getId())) {
	                sql += " and s.id = '" + searchModel.getId() + "'";
	            }
	            if (!StringUtils.isEmpty(searchModel.keyword)) {
	                sql += " and (s.customer_name like '%" + searchModel.keyword + "%'" + " or s.customer_email like '%"
	                        + searchModel.keyword + "%'" + " or s.customer_phone like '%" + searchModel.keyword + "%')";
	            }
	        }

	        return executeByNativeSQL(sql, searchModel == null ? 0 : searchModel.getPage());
	    }
	public void setNullUserId(int userId)
	{
		List<SaleOder> saleOders = saleOrderRepository.findByUser_Id(userId);
		for (SaleOder so: saleOders) {
			so.setUser(null);
		}
	}
}
