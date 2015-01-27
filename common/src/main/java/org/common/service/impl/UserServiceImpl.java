package org.common.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.common.dao.DepartmentRepository;
import org.common.dao.UserPermitRepository;
import org.common.dao.UserRepository;
import org.common.entity.Department;
import org.common.entity.User;
import org.common.entity.view.user.UserLoginView;
import org.common.entity.view.user.UserView;
import org.common.exception.OaException;
import org.common.exception.system.user.UserCodeExistException;
import org.common.exception.system.user.UserEmailExistException;
import org.common.exception.system.user.UserNameExistException;
import org.common.exception.system.user.UserNotFoundException;
import org.common.service.IUserService;
import org.common.util.MongoDbManager;
import org.common.util.PasswordHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

@Service("userService")
public class UserServiceImpl implements IUserService {
	Log logger = LogFactory.getLog(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userDao;
	
	@Autowired
	private DepartmentRepository departmentDao;
	
	@Autowired
	private UserPermitRepository userPermitDao;
	
	@Autowired
	MongoDbManager mongoDbManager;
	
	@Autowired
	private PasswordHelper passwordHelper;

	public UserView selectUserByUserName(String userName)throws OaException{
		User user = userDao.selectUserByUserName(userName);
		if(user==null){
			throw new UserNotFoundException();
		}
		
		UserView userView = new UserView();
		BeanUtils.copyProperties(user, userView);
		Department depart = departmentDao.selectDepartmentById(user.getDeptId());
		userView.setDeptName(depart!=null?depart.getDepartmentName():"");
		return userView;
	}

	@Transactional(rollbackFor=Exception.class)
	public void addUser(User user,InputStream input)throws OaException {
		validateUserInfo(user);
		passwordHelper.encryptPassword(user);
		user.setCreateTime(new Date());
		user.setIsDel(1);
		userDao.addUser(user);
		user.setImageKey("user-"+user.getUserId());
		userDao.updateUser(user);
		
		//保存图片
		GridFS userDb = mongoDbManager.getUserPicDB();
		GridFSInputFile file = userDb.createFile(input);
		file.setId("user-"+user.getUserId().toString());
		file.save();
	}
	
	private void validateUserInfo(User user)throws OaException{
		User tempUser = userDao.selectUserByUserName(user.getUserName());
		if(tempUser!=null){
			throw new UserNameExistException();
		}
		
		tempUser = userDao.selectUserByCode(user.getUserCode());
		if(tempUser!=null){
			throw new UserCodeExistException();
		}
		
		tempUser = userDao.selectUserByEmail(user.getEmail());
		if(tempUser!=null){
			throw new UserEmailExistException();
		}
	}

	public void setMongoDbManager(MongoDbManager mongoDbManager) {
		this.mongoDbManager = mongoDbManager;
	}

	public UserView selectUserById(Long userId) {
		UserView userView = new UserView();
		User user = this.userDao.selectUserById(userId);
		BeanUtils.copyProperties(user, userView);
		
		Department depart = departmentDao.selectDepartmentByCode(user.getDeptCode());
		userView.setDeptName(depart.getDepartmentName());
		
		return userView;
	}

	public void selectUserImage(Long userId, OutputStream output) {
		GridFS userDb = mongoDbManager.getUserPicDB();
		GridFSDBFile file = userDb.findOne((DBObject) new BasicDBObject("_id", "user-"+userId.toString()));
		try {
			if(file!=null){
				file.writeTo(output);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.info(e.getMessage()+"文件读取失败");
		} 
	}

	@Transactional(rollbackFor=Exception.class)
	public void updateUser(User user, InputStream input) {
		userDao.updateUser(user);
		
		if(input != null){
			GridFS userDb = mongoDbManager.getUserPicDB();
			userDb.remove(new BasicDBObject("_id", "user-"+user.getUserId().toString()));
			
			GridFSInputFile file = userDb.createFile(input);
			file.setId("user-"+user.getUserId().toString());
			file.save();
		}
	}

	public UserLoginView selectUserLoginInfoByUserName(String userName) {
		return userDao.selectLoginInfoByUserName(userName);
	}

	public List<Long> selectUserByDepartId(Long departId) {
		return userDao.selectUserByDepartId(departId);
	}

	/*public Set<String> selectRoleCodeByUserId(Long userId) {
		return userPermitDao.selectRoleCodeByUserId(userId);
	}*/
}
