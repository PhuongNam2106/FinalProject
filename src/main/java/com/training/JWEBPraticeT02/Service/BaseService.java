package com.training.JWEBPraticeT02.Service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.transaction.Transactional;

import com.training.JWEBPraticeT02.entity.BaseEntity;



public abstract class BaseService<E extends BaseEntity>{

	private static int SIZE_OF_PAGE = 16;
	
	@PersistenceContext //Inject entityManager
	protected EntityManager entityManager;

	protected abstract Class<E> clazz();

	/**
	 * Thực hiện lưu hoặc cập nhật bản ghi trong cơ sở dữ liệu.
	 * @param entity
	 * @return
	 */
	@Transactional
	public E saveOrUpdate(E entity) {
		if (entity.getId() == null || entity.getId() <= 0) {
			entity.setCreatedDate(new Date());
			entityManager.persist(entity); // thêm mới
			return entity;
		} else { 
			return entityManager.merge(entity); // cập nhật
		}
	}

	/**
	 * xóa bản ghi trong cơ sở dữ liệu
	 * @param entity
	 */
	public void delete(E entity) {
		entityManager.remove(entity);
	}

	/**
	 * xóa bản ghi trong cơ sở dữ liệu theo khóa chính id
//	 * @param id
	 */
	@Transactional
	public void deleteById(int primaryKey) {
		E entity = this.getById(primaryKey);
		delete(entity);
	}
	
	/**
	 * Lấy bản ghi trong cơ sở dữ liệu theo khóa chính ID.
//	 * @param id
	 * @return
	 */
	public E getById(int primaryKey) {
		return entityManager.find(clazz(), primaryKey);
	}

	/**
	 * Lấy bản ghi trong cơ sở dữ liệu theo name.
//	 * @param id
	 * @return
	 */
	public E getByName(int name) {
		return entityManager.find(clazz(), name);
	}
	
	/**
	 * thực thi câu lệnh truy vấn cơ sở dữ liệu và trả về duy nhất 1 kết quả
	 * @param sql -> ví dụ chạy câu lệnh [SELECT * FROM tbl_category WHERE name='Java']
//	 * @param page
	 * @return
	 */
	public E getOneByNativeSQL(String sql) {
		try {
			return executeByNativeSQL(sql, 0).getData().get(0);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Lấy tất cả bản ghi trong cơ sở dữ liệu.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<E> findAll() {
		Table tbl = clazz().getAnnotation(Table.class);
		return (List<E>) entityManager.createNativeQuery("SELECT * FROM " + tbl.name(), clazz()).getResultList();
	}
	
	/**
	 * thực thi câu lệnh truy vấn cơ sở dữ liệu
	 * @param sql -> ví dụ chạy câu lệnh [SELECT * FROM tbl_category;]
	 * @param page
	 * @return
	 */
	public PagerData<E> executeByNativeSQL(String sql, int page) {
		PagerData<E> result = new PagerData<E>();
		
		try {
			Query query = entityManager.createNativeQuery(sql, clazz());
			//trường hợp có thực hiện phân trang thì kết quả trả về
			//bao gồm tổng số page và dữ liệu page hiện tại
			if(page > 0) {
				result.setCurrentPage(page);
				result.setTotalItems(query.getResultList().size());
				
				query.setFirstResult((page - 1) * SIZE_OF_PAGE);
				query.setMaxResults(SIZE_OF_PAGE);
			}
			
			result.setData(query.getResultList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * thực thi câu lệnh cập nhật cơ sở dữ liệu
	 * @param sql -> ví dụ chạy câu lệnh [DELETE FROM tbl_category;] hoặc [UPDATE tbl_category SET Name = 'Alfred Schmidt' WHERE Id = 1;]
	 * @return
	 */
	public int executeUpdateOrDeleteByNativeSQL(String sql) {
		try {
			return entityManager.createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
}
