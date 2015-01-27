package org.common.dao;

import java.util.List;
import java.util.Map;

import org.common.entity.User;
import org.common.entity.view.user.UserBaseView;
import org.common.entity.view.user.UserLoginView;
import org.springframework.stereotype.Component;
@Component
@MyBatisRepository
public interface UserRepository{
	User selectUserByUserName(String userName);

	/**
	 * 查询用户登录信息
	 * @param userName
	 * @return
	 */
	UserLoginView selectLoginInfoByUserName(String userName);
	
	/**
	 * 分页查询用户
	 * @param sqlconfig
	 * @param paramter
	 * @return
	 */
	List<UserBaseView> selectUserByPage(Map<String,Object> paramter);
	
	/**
	 * 分页查询用户的总数
	 * @param sqlconfig
	 * @param paramter
	 * @return
	 */
	int selectUserCountByPage(Map<String,Object> paramter);
	
	/**
	 * 根据用户id查询用户
	 * @param userId
	 * @return
	 */
	User selectUserById(Long userId);
	
	/**
	 * 添加用户
	 * @param user
	 */
	long addUser(User user);
	
	/**
	 * 修改用户
	 * @param user
	 */
	void updateUser(User user);
	
	/**
	 * 根据用户编码查询用户
	 * @param code
	 * @return
	 */
	User selectUserByCode(String code);
	
	/**
	 * 根据用户邮箱查询用户
	 * @param email
	 * @return
	 */
	User selectUserByEmail(String email);
	
	/**
	 * 查询部门下的用户
	 * @param departId
	 * @return
	 */
	int selectCountUserByDepartId(Long departId);
	
	/**
	 * 查询部门下的用户id
	 * @param departId
	 * @return
	 */
	List<Long> selectUserByDepartId(Long departId);
}
	