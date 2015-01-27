<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:include page="include.jsp"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:set value="${pageContext.request.contextPath}" var="ctx" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>登录</title>
        <link rel="stylesheet" href="${ctx }/css/main.css" />
		<link rel="stylesheet" href="${ctx }/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${ctx }/css/bootstrap-responsive.min.css" />
        <link rel="stylesheet" href="${ctx }/css/unicorn.login.css" />
        <script src="${ctx }/js/jquery.min.js" type="text/javascript"></script>  
        <script src="${ctx }/js/unicorn.login.js" type="text/javascript"></script>
        <script type="text/javascript">
        	$(document).ready(function(){
        		initLogin();
        	});
        	
        	function initLogin(){
        		var errorMessage = "${errorMessage}";
        		if(errorMessage!=null && errorMessage!=""){
        			$("#errorMessage").html(errorMessage);
        		}
        	}
        </script>
    </head>
    <body>
        <div id="logo">
            <img src="img/logo.png" alt="" />
        </div>
        <div id="loginbox">            
            <form id="loginform" action="${ctx }/login.html" method="post">
				<p id="errorMessage"></p>
                <div class="control-group">
                    <div class="controls">
                        <div class="input-prepend">
                            <span class="add-on"><i class="icon-user"></i></span><input type="text" value="${userName }" name="userName" placeholder="用户名" />
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <div class="input-prepend">
                            <span class="add-on"><i class="icon-lock"></i></span><input type="password" value="${password }" name="password" placeholder="密码" />
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls tl ml90">
                    	<label><input type="checkbox" name="rememberMe" value="1"/><span class="lh20">自动登录</span></label>
                    </div>
                </div>
                <div class="form-actions">
                    <span class="pull-left"><a href="#" class="flip-link" id="to-recover">忘记密码?</a></span>
                    <span class="pull-right"><input type="submit" class="btn btn-inverse" value="登陆"/></span>
                </div>
            </form>
            <form id="recoverform" action="#" class="form-vertical">
				<p>请输入您的邮箱，我们将告诉您如何取回密码</p>
				<div class="control-group">
                    <div class="controls">
                        <div class="input-prepend">
                            <span class="add-on"><i class="icon-envelope"></i></span><input type="text" placeholder="邮箱地址" />
                        </div>
                    </div>
                </div>
                <div class="form-actions">
                    <span class="pull-left"><a href="#" class="flip-link" id="to-login">&lt; 返回</a></span>
                    <span class="pull-right"><input type="submit" class="btn btn-inverse" value="取回" /></span>
                </div>
            </form>
        </div>
    </body>
</html>