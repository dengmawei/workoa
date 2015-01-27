<%@ include file="/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:set value="${pageContext.request.contextPath}" var="ctx" />
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>header.jsp</title>
	<script type="text/javascript" src="${ctx }/js/jquery-validation-1.8.1/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${ctx }/js/Jquery-Validate-For-Bootstrap-master/jquery.validate.bootstrap.js"></script>
	<script type="text/javascript" src="${ctx }/js/twitter-bottstrap/js/bootstrap-modal.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#changePassDialog").modal({
				keyboard:true,
				show:false
			});
			
			$("#userDialog").modal({
				keyboard:true,
				show:false
			});
			
			
			$.validator.addMethod("password_required",function(value){
				if(value!=null && value!=""){
					return true;
				}
				return false;
			},'密码不能为空');
			
			$.validator.addMethod("password_format",function(value){
				if(value.length<20){
					return true;
				}
				return false;
			},'密码长度不能超过20');
			
			$.validator.addMethod("password_comparison",function(value){
				if(value==$("#newPassword").val()){
					return true;
				}
				return false;
			},'两次输入的密码不一致');
			
			$("#changePassForm").validate({
				success: function(element) {
					element.text('OK!').addClass('valid').closest('.control-group').removeClass('error').removeClass('success');
				},
				highlight: function(element) {
					$(element).closest('.control-group').removeClass('success').addClass('error');
				},
				rules:{
					password:{
						password_required:true,
						password_format:true
					},
					newPassword:{
						password_required:true,
						password_format:true
					},
					newAgainPassword:{
						password_required:true,
						password_format:true,
						password_comparison:true
					}
				}
			});
		});
		
		//打开密码编辑框
		function openChangePassDialog(){
			clearChangePassInfo();
			$("#changePassDialog").modal('show');
		}
		
		//取消密码编辑
		function cancelChangePass(){
			clearChangePassInfo();
			$("#changePassDialog").modal('hide');
		}
		
		//修改密码
		function changePass(){
			if($("#changePassForm").validate().form() && window.confirm("您确认修改？")){
				var param = {};
				param.password = $("#password").val();
				param.newPassword = $("#newPassword").val();
				param.newAgainPassword = $("#newAgainPassword").val();
				$.post("${ctx}/changePassword.html",param,function(data){
					if(data.success){
						$("#changePassDialog").modal('hide');	
						showTipInfo("密码修改成功");
					}else{
						alert(data.message);
					}
				},"json");
			}
		}
		
		//清空密码框
		function clearChangePassInfo(){
			$("#password").val('');
			$("#newPassword").val('');
			$("#newAgainPassword").val('');
		}
		
		function showUserInfo(){
			$.post("${ctx}/user/selectCurrentUser.html",{},function(data){
				if(data.success){
					var user = data.data;
					
					$("#userCodeP").val(user.userCode);
					$("#emailP").val(user.email);
					$("#mobilePhoneP").val(user.mobilePhone);
					$("#telphoneP").val(user.telphone);
					$("#addressP").val(user.address);
					$("#deptNameP").val(user.deptName);
					
					$("#userDialog").modal('show');
				}else{
					alert(data.message);
				}
			},"json");
		}
		
		function refreshPermission(){
			$.post("${ctx}/user/refreshPermission.html",{},function(data){
				if(data.success){
					showTipInfo("更新成功!");
				}
			},"json");
		}
	</script>
</head>
<body>
	<div id="header">
		<h1><a href="./dashboard.html">Unicorn Admin</a></h1>		
	</div>

	<div id="search">
		<input type="text" value="Search here..." onfocus="this.value=''" onblur="this.value='Search here...'"/>
		<button type="submit" class="tip-right" title="Search">
			<i class="icon-search icon-white"></i>
		</button>
	</div>
	<div id="user-nav" class="navbar navbar-inverse">
		<ul class="nav btn-group">
			<li class="btn btn-inverse dropdown" id="menu-user">
				<a data-toggle="dropdown" data-target="#menu-user" class="dropdown-toggle">
					<i class="icon icon-user"></i>
					<span class="text"><shiro:principal/></span>
					<b class="caret"></b>
				</a>
				<ul class="dropdown-menu">
					<li><a href="javascript:openChangePassDialog()">密码修改</a></li>
					<li><a href="javascript:showUserInfo()">个人信息</a></li>
					<li><a href="javascript:refreshPermission()">更新权限</a></li>
				</ul>
			</li>
			<li class="btn btn-inverse dropdown" id="menu-messages">
				<a href="#" data-toggle="dropdown" data-target="#menu-messages" class="dropdown-toggle">
					<i class="icon icon-envelope"></i>
					<span class="text">消息</span><!-- <span class="label label-important">5</span> -->
					<b class="caret"></b>
				</a>
				<ul class="dropdown-menu">
					<li><a class="sAdd" title="" href="#">new message</a></li>
				</ul>
			</li>
			<li class="btn btn-inverse">
				<a title="设置" href="#">
					<i class="icon icon-cog"></i>
					<span class="text">设置</span>
				</a>
			</li>
			<li class="btn btn-inverse">
				<a title="退出" href="${ctx }/logout.html">
					<i class="icon icon-share-alt"></i>
					<span class="text">安全退出</span>
				</a>
			</li>
		</ul>
	</div>
	
	<div id="changePassDialog" title="密码修改" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3>密码修改</h3>
		</div>
		<div class="modal-body">
			<form method="post" class="form-horizontal" id="changePassForm">
				<div class="control-group">
					<label class="control-label">原密码</label>
					<div class="controls">
						<input type="password" name="password" id="password" maxlength="20">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">新密码</label>
					<div class="controls">
						<input type="password" name="newPassword" id="newPassword" maxlength="20">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">再次确认新密码</label>
					<div class="controls">
						<input type="password" name="newAgainPassword" id="newAgainPassword" maxlength="20">
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<input type="button" class="btn" onclick="cancelChangePass()" value="取消">
    		<input type="button" class="btn btn-primary" onclick="changePass()" value="保存">
		</div>
	</div>
	
	<div id="userDialog" title="用户个人信息" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="icon icon-remove"></i></button>
			<h3>用户个人信息</h3>
		</div>
		<div class="modal-body">
			<form method="post" class="form-horizontal" id="updateUserForm">
				<div class="control-group">
					<label class="control-label">用户工号</label>
					<div class="controls">
						<input type="text" name="userCodeP" id="userCodeP" readonly="readonly"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">邮箱</label>
					<div class="controls">
						<input type="text" name="emailP" id="emailP" readonly="readonly">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">联系方式</label>
					<div class="controls">
						<input type="text" name="mobilePhoneP" id="mobilePhoneP" readonly="readonly"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">电话</label>
					<div class="controls">
						<input type="text" name="telphoneP" id="telphoneP" readonly="readonly"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">地址</label>
					<div class="controls">
						<input type="text" name="addressP" id="addressP" readonly="readonly"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">所属部门</label>
					<div class="controls">
						<input type="text" name="deptNameP" id="deptNameP" readonly="readonly">
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<input type="button" class="btn" onclick="$('#userDialog').modal('hide');" value="关闭">
		</div>
	</div>
</body>
</html>