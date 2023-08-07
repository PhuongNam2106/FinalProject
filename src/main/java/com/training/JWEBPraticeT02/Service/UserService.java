package com.training.JWEBPraticeT02.Service;

import java.util.List;

import com.training.JWEBPraticeT02.entity.User;
import com.training.JWEBPraticeT02.model.UserSearchModel;
import com.training.JWEBPraticeT02.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


@Service
@Transactional
public class UserService extends BaseService<User> {

	@Override
	protected Class<User> clazz() {
		return User.class;
	}

	@Autowired
	private UserRepository userRepository;

	public User loadUserByUsername(String userName) {
		String sql = "select * from tbl_users u where u.username = '" + userName + "'";
		List<User> users = this.executeByNativeSQL(sql, 0).getData();

		if(users == null || users.size() <= 0) return null;
		return users.get(0);
	}

	public PagerData<User> search(UserSearchModel searchModel) {

		// khởi tạo câu lệnh
		String sql = "SELECT * FROM tbl_users u WHERE 1=1";

		if (searchModel != null) {
			// tìm kiếm theo category
			if(searchModel.Id != null) {
				sql += " and id = " + searchModel.Id;
			}

			//tìm theo username
			if (!StringUtils.isEmpty(searchModel.userName)) {
				sql += " and u.username = '" + searchModel.userName + "'";
			}

			//tìm theo customer name
			if (!StringUtils.isEmpty(searchModel.customerName)) {
				sql += " and u.customer_name = '" + searchModel.customerName + "'";
			}




		}

		// chi lay san pham chua xoa
//				sql += " and p.status=1 ";
		return executeByNativeSQL(sql, searchModel == null ? 0 : searchModel.getPage());

	}

	public User findUserByEmail(String userEmail) {
		return userRepository.findByEmail(userEmail);
	}

//	public void createPasswordResetTokenForUser(User user, String token) {
//		PasswordResetToken myToken = new PasswordResetToken(token, user);
//		passwordTokenRepository.save(myToken);
//	}


	public void updateResetPasswordToken(String token, String email){
		User user = userRepository.findByEmail(email);
		if (user != null) {
			user.setResetPasswordToken(token);
			userRepository.save(user);
		} else {
			System.out.println("Not found");
		}
	}

	public User getByResetPasswordToken(String token) {
		return userRepository.findByResetPasswordToken(token);
	}

	public void updatePassword(User user, String newPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(newPassword);
		user.setPassword(encodedPassword);

		user.setResetPasswordToken(null);
		userRepository.save(user);
	}

	@PersistenceContext
	private EntityManager entityManager;

	public void deleteUserRoleByUserId(int userId) {
		String query = "DELETE FROM tbl_users_roles WHERE user_id = :userId";
		entityManager.createNativeQuery(query)
				.setParameter("userId", userId)
				.executeUpdate();
	}


}
