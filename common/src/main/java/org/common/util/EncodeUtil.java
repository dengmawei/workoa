package org.common.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;


public class EncodeUtil {
	public final static Md5PasswordEncoder md5 = new Md5PasswordEncoder();
	
	public static String encode(String password,String salt){
		return md5.encodePassword(password, salt); 
	}
}
