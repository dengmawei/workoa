package org.common.command.user;

import java.io.IOException;
import java.io.InputStream;

import net.sf.oval.constraint.Email;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.ValidateWithMethod;

import org.common.exception.OaException;
import org.common.exception.system.PictureFormatException;
import org.common.exception.system.SystemException;
import org.common.exception.system.UploadPictureMaxException;
import org.springframework.web.multipart.MultipartFile;

public class UserUpdateCommand {
	@NotNull(message="用户id不能为空")
	private Long userId;
	
	@NotEmpty(message="真实姓名不能为空")
	@Length(max=10,message="长度不能大于10")
	private String realName;
	
	@NotEmpty(message="员工工号不能为空")
	@Length(min=5,max=5,message="长度必须为5位数字")
	private String userCode;
	
	@NotEmpty(message="邮箱不能为空")
	@Email(message="邮箱格式不正确")
	@ValidateWithMethod(methodName="validateEmail",parameterType=String.class,message="邮箱格式不正确")
	private String email;
	
	@NotEmpty(message="手机号码不能为空")
	@Length(min=11,max=11,message="手机号码必须为11位")
	@ValidateWithMethod(methodName="validatePhone",parameterType=String.class,message="手机号码格式不正确")
	private String mobilePhone;
	
	private String telphone;
	
	private String address;
	
	@NotNull(message="员工状态不能为空")
	private Integer status;
	
	@NotNull(message="部门编号不能为空")
	private String deptCode;
	
	private MultipartFile picFile = null;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public InputStream getInputStream() throws OaException{
		if(picFile != null){
			long size = picFile.getSize();
			if(size>5242880){
				throw new UploadPictureMaxException();
			}
			
			String fileName = picFile.getOriginalFilename();
			int index = fileName.lastIndexOf(".");
			if(index == -1){
				throw new PictureFormatException();
			}
			
			String suffix = fileName.substring(index+1);
			if(!"jpg".equals(suffix) && !"jpeg".equals(suffix) && !"gif".equals(suffix) && !"png".equals(suffix)){
				throw new PictureFormatException();
			}
			
			try {
				return picFile.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
				throw new SystemException();
			}
		}
		
		return null;
	}

	public MultipartFile getPicFile() {
		return picFile;
	}

	public void setPicFile(MultipartFile picFile) {
		this.picFile = picFile;
	}

	private boolean validateEmail(String email){
		String regex = "([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+";
		return email.matches(regex);
	}
	
	private boolean validatePhone(String mobile){
		String regex = "((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}";
		return mobile.matches(regex);
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
