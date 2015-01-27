package org.common.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.common.entity.User;
import org.common.entity.view.user.UserLoginView;
import org.common.entity.view.user.UserView;
import org.common.exception.OaException;

public interface IUserService {
	/**
	 * 根据用户名查询用户
	 * @param userName
	 * @return
	 */
	UserView selectUserByUserName(String userName)throws OaException;
	
	/**
	 * 添加用户
	 * @param user
	 */
	void addUser(User user,InputStream input)throws OaException;
	
	/**
	 * 编辑用户
	 * @param user
	 */
	void updateUser(User user,InputStream input);
	
	/**
	 * 根据用户id查询用户
	 * @param userId
	 * @return
	 */
	UserView selectUserById(Long userId);
	
	/**
	 * 根据用户id查询用户头像
	 * @param userId
	 */
	void selectUserImage(Long userId,OutputStream outputStream);
	
	/**
	 * 根据用户名查询用户登录信息
	 * @param userName
	 * @return
	 */
	UserLoginView selectUserLoginInfoByUserName(String userName);
	
	/**
	 * 根据用户id查询角色列表
	 * @param userId
	 * @return
	 */
	/*Set<String> selectRoleIdByUserId(Long userId);*/
	
	/**
	 * 更加部门id查询部门下的用户id
	 * @param departId
	 * @return
	 */
	List<Long> selectUserByDepartId(Long departId);
}
