<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:include page="/include.jsp"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:set value="${pageContext.request.contextPath}" var="ctx" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${ctx }/js/jquery_ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery_ztree/jquery.ztree.excheck-3.5.js"></script>
<link rel="stylesheet" href="${ctx }/js/jquery_ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script src="${ctx }/js/bootstrap-pagination/bootstrap-paginator.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery-validation-1.8.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx }/js/Jquery-Validate-For-Bootstrap-master/jquery.validate.bootstrap.js"></script>
<script type="text/javascript" src="${ctx }/js/twitter-bottstrap/js/bootstrap-modal.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery-form/jquery.form.js"></script>
<link rel="stylesheet" href="${ctx }/css/layout/layout.css" >
<title>用户中心</title>
<script type="text/javascript">
	var paramMap = {};
	var pageOfNum = 10;
	var defaultPage = 1;
	var companyid = ${companyId};
	
	var setting = {
		treeId : "",
		treeObj : null,
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : false
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : ""
			}
		},
		callback : {
			onClick : loadUserByDepart
		}
	};
	
	var settingDefaultAdd = {
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : false
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : ""
			}
		},
		check : {
			enable:true,
			chkStyle:"radio",
			radioType:"all"
		},
		callback : {
			onCheck : getDepartCodeAdd
		}
	};
	
	var settingDefaultUpdate = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pId",
					rootPId : ""
				}
			},
			check : {
				enable:true,
				chkStyle:"radio",
				radioType:"all"
			},
			callback : {
				onCheck : getDepartCodeUpdate
			}
		};

	$(document).ready(function(){
		loadDepartTree(companyid);
		
		//初始化添加用户的表单
		$('#addUserDialog').modal({
			keyboard:true,
			show:false	
		});
		
		//初始化用户编辑表单
		$('#updateUserDialog').modal({
			keyboard:true,
			show:false	
		});
		
		$('#roleUserDialog').modal({
			keyboard:true,
			show:false
		});
		
		$("#openAddUserBtn").click(function(){
			$('#addUserDialog').modal('show');
		});
		
		$("#closeAddUserBtn").click(function(){
			$('#addUserDialog').modal('hide');
			clearAddUserInfo();
			$("#addUserForm").reset();
		});
		
		$("#addUserBtn").click(function(){
			addUser();	
		});
		
		$("#selectDepartBtnAdd").click(function(){
			$("#userAddTreeDiv").show();
		});
		
		$("#closeUpdateUserBtn").click(function(){
			$('#updateUserDialog').modal('hide');
			clearUpdateUserInfo();
			$("#updateUserForm").reset();
		});
		
		$("#closeRoleUserBtn").click(function(){
			cancelSaveRoleUser();
			$('#roleUserDialog').modal('hide');
		});
		
		$("#saveRoleUserBtn").click(function(){
			saveRoleUser();
		});
		
		$("#updateUserBtn").click(function(){
			updateUser();	
		});
		
		$("#selectDepartBtnUpdate").click(function(){
			$("#userUpdateTreeDiv").show();
			var deptCode = $("#deptCodeUpdate").val();
			if(deptCode!=null && deptCode!=""){
				var zTree = $.fn.zTree.getZTreeObj("departUpdateTree");
				
				var node = zTree.getNodeByParam("code", deptCode);
				node.checked = true;
				zTree.updateNode(node,true);
			}
		});
		
		$.validator.addMethod("userName_required",function(value){
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'用户名不能为空');
		
		$.validator.addMethod("realName_required",function(value){
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'真实姓名不能为空');
		
		$.validator.addMethod("realName_format",function(value){
			if(!/^[\u4e00-\u9fa5]{1}([\•\·\∙\・\.\·\．\﹒\●]|[\u4e00-\u9fa5]){0,8}[\u4e00-\u9fa5]{1}$/.test(value)){
				return false;
			}
			return true;
		},'请输入的真实姓名');
		
		$.validator.addMethod("password_required",function(value){
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'密码不能为空');
		
		$.validator.addMethod("confirmPassword_required",function(value){
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'确认密码不能为空');
		
		$.validator.addMethod("confirmPassword_validate",function(value){
			if($("#passwordAdd").val()!=value){
				return false;
			}
			return true;
		},'两次输入的密码不一致，请重新输入');
		
		$.validator.addMethod("userCode_required",function(value){
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'用户工号不能为空');
		
		$.validator.addMethod("userCode_format",function(value){
			return /^\d{5}$/.test(value);
		},'用户工号格式不正确');
		
		$.validator.addMethod("email_required",function(value){
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'邮箱不能为空');
		
		$.validator.addMethod("email_format",function(value){
			return /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(value);
		},'邮箱格式不正确');
		
		$.validator.addMethod("mobilePhone_required",function(value){
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'手机号码不能为空');
		
		$.validator.addMethod("mobilePhone_format",function(value){
			return /^1\d{10}$/.test(value);
		},'手机号码格式不正确');
		
		$.validator.addMethod("status_format",function(value){
			if(value!=0){
				return true;
			}
			return false;
		},'请选择入职状态');
		
		$.validator.addMethod("userPic_required",function(value){
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'请上传头像');
		
		$.validator.addMethod("userPic_format",function(value){
			var index = value.indexOf(".");
			if(index==-1){
				return false;
			}
			
			var prefix = value.substring(index+1);
			if(prefix!="jpg"&&prefix!="png"&&prefix!="gif"&&prefix!="jpeg"){
				return false;
			}
			return true;
		},'图片格式不正确');
		
		$.validator.addMethod("userPic_update_format",function(value){
			if(value==null || value==""){
				return true;
			}else{
				var index = value.indexOf(".");
				if(index==-1){
					return false;
				}
				
				var prefix = value.substring(index+1);
				if(prefix!="jpg"&&prefix!="png"&&prefix!="gif"&&prefix!="jpeg"){
					return false;
				}
				return true;
			}
		},'图片格式不正确');
		
		$.validator.addMethod("departName_required",function(value){
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'请选择用户所在部门');
		
		$("#addUserForm").validate({
			success: function(element) {
				element.text('OK!').addClass('valid').closest('.control-group').removeClass('error').removeClass('success');
			},
			highlight: function(element) {
				$(element).closest('.control-group').removeClass('success').addClass('error');
			},
			rules:{
				userName:{
					userName_required:true
				},
				realName:{
					realName_required:true,
					realName_format:true
				},
				password:{
					password_required:true
				},
				confirmPassword:{
					confirmPassword_required:true,
					confirmPassword_validate:true
				},
				userCode:{
					userCode_required:true,
					userCode_format:true
				},
				email:{
					email_required:true,
					email_format:true
				},
				mobilePhone:{
					mobilePhone_required:true,
					mobilePhone_format:true
				},
				status:{
					status_format:true
				},
				userPic:{
					userPic_required:true,
					userPic_format:true
				},
				deptName:{
					departName_required:true
				}
			}
		});
		
		$("#updateUserForm").validate({
			success: function(element) {
				element.text('OK!').addClass('valid').closest('.control-group').removeClass('error').removeClass('success');
			},
			highlight: function(element) {
				$(element).closest('.control-group').removeClass('success').addClass('error');
			},
			rules:{
				realName:{
					realName_required:true,
					realName_format:true
				},
				userCode:{
					userCode_required:true,
					userCode_format:true
				},
				email:{
					email_required:true,
					email_format:true
				},
				mobilePhone:{
					mobilePhone_required:true,
					mobilePhone_format:true
				},
				status:{
					status_format:true
				},
				userPic:{
					userPic_update_format:true
				},
				deptName:{
					departName_required:true
				}
			}
		});
	});
	
	//加载部门树
	function loadDepartTree(companyid){
		var defaultTree = $("#departTree");
		var addUserzTree = $("#departAddTree");
		var updateUserzTree = $("#departUpdateTree");
		var zNodes = [];
		var result = selectDepartByCompanyId(companyid);
		
		if(result.data){
			var map = result.data;
			zNodes.push({id:100000,pId:0,name:map.company.companyName,open:true,nocheck:true});
			$.each(map.departList,function(k,v){
				var treeNode = {};
				treeNode.id = v.id;
				if(v.superId==null || v.superId==""){
					treeNode.pId = 100000;
				}else{
					treeNode.pId = v.superId;
				}
				treeNode.name = v.departmentName;
				treeNode.code = v.departmentCode;
				zNodes.push(treeNode);
			});
		}
		$.fn.zTree.init(defaultTree, setting, zNodes);
		$.fn.zTree.init(addUserzTree, settingDefaultAdd, zNodes);
		$.fn.zTree.init(updateUserzTree, settingDefaultUpdate, zNodes);
	}
	
	//根据集团id查询其下的部门
	function selectDepartByCompanyId(companyId){
		var result;
		$.ajax({
			url:"${ctx}/depart/selectDepartByCompanyId.html",
			async:false,
			dataType:"json",
			data:"companyId="+companyId,
			type:"post",
			success:function(data){
				result = data;
			}
		});
		return result;
	}
	
	//查询部门下的员工
	function loadUserByDepart(event,treeId,treeNode){
		paramMap.deptCode = treeNode.code;
		selectUser(paramMap);
	}
	
	//分页查询用户
	function selectUser(param){
		$("#userList tbody").html("");
		param.perPage=pageOfNum;
		param.currentPage=defaultPage;
		$.post("${ctx}/user/selectUser.html",param,function(data){
			if(data.success){
				var result = data.data.list;
				var count = data.data.count;
				var currentPage = data.data.currentPage;
				var totalPage = data.data.totalPage;
				for(var i=0;i<result.length;i++){
					var user = result[i];
					var status = "";
					if(user.status==1){
						status = "在职";
					}else if(user.status==2){
						status = "离职";
					}else if(user.status==3){
						status = "实习";
					}else if(user.status==4){
						status = "试用期";
					}
					
					var userImage = user.userId==1?"":"<img src='${ctx}/user/selectUserImage.html?userId="+user.userId+"' width='100' height='50'>";
					var htm = "";
					htm = htm 
					+"<tr><td valign='middle' align='center'>"+userImage+"</td>"
					+"<td valign='middle' align='center'>"+user.userName+"</td><td align='center'>"
					+user.code+"</td><td valign='middle' align='center'>"
					+user.email+"</td><td valign='middle' align='center'>"
					+user.address+"</td><td valign='middle' align='center'>"
					+user.mobile+"</td><td valign='middle' align='center'>"
					+status+"</td><td valign='middle' align='center'>"
					+"<span class='icon'><button class='btn btn-mini' onclick='openEditDialog("+user.userId+")'><i class='icon-edit' title='编辑'></i></button></span>&nbsp;&nbsp;"
					+"<span class='icon'><button class='btn btn-mini' onclick='openRoleUserDialog("+user.userId+")'><i class='icon-user' title='编辑'></i></button></span></td></tr>";
					
					$("#userList tbody").append(htm);
				}
				
				if(count>0){
					var options = {
						currentPage:currentPage,
						totalPages:totalPage,
						numberOfPages:pageOfNum,
						onPageClicked:function(event,originalEvent, type,page){
							if(currentPage==1 && (type=='first' || type=='prev')){
								return;
							}
							
							if(currentPage==totalPage && (type=='last' || type=='next')){
								return;
							}
							
							paramMap.perPage=pageOfNum;
							paramMap.currentPage=page;
							selectUser(paramMap);
						},
						itemTexts:function(type,page,current){
							switch (type) {  
				            	case "first":  
				                	return "首页";  
				                case "prev":  
				                	return "上一页";  
				                case "next":  
				                	return "下一页";  
				                case "last":  
				                    return "末页";  
				                case "page":  
				                    return  page;  
				            }                
						},
						shouldShowPage:true
					};
					$('#example').bootstrapPaginator(options);
				}else{
					$('#example').html('');
				}
			}
		},"json");
	}
	
	function getDepartCodeAdd(e,treeId,treeNode){
		$("#deptAdd").val(treeNode.name);
		$("#deptCodeAdd").val(treeNode.code);
		$("#deptIdAdd").val(treeNode.id);
		$("#userAddTreeDiv").hide();
	}
	
	function getDepartCodeUpdate(e,treeId,treeNode){
		$("#deptUpdate").val(treeNode.name);
		$("#deptCodeUpdate").val(treeNode.code);
		$("#deptIdUpdate").val(treeNode.id);
		$("#userUpdateTreeDiv").hide();
	}
	
	//保存用户信息
	function addUser(){
		if($("#addUserForm").validate().form()){
			if(window.confirm("您确定提交？")){
				var param = {
					type:"post",
					success:afterAddUser
				};
				
				$("#bgHideDiv").show();
				$("#waitSendDiv").show();
				$("#addUserForm").ajaxSubmit(param);
				return false;
			}
		}
	}
	
	//编辑用户
	function updateUser(){
		if($("#updateUserForm").validate().form()){
			if(window.confirm("您确定提交？")){
				var param = {
					type:"post",
					success:afterUpdateUser
				};
				
				$("#bgHideDiv").show();
				$("#waitSendDiv").show();
				$("#updateUserForm").ajaxSubmit(param);
				return false;
			}
		}
	}
	
	//添加用户的后续操作
	function afterAddUser(data){
		$("#bgHideDiv").hide();
		$("#waitSendDiv").hide();
		var result = $.parseJSON(data);
		if(result.success){
			$('#addUserDialog').modal('hide');
			clearAddUserInfo();
			selectUser(paramMap);
		}else{
			alert(result.message);
		}
	}
	
	//修改用户的后续操作
	function afterUpdateUser(data){
		$("#bgHideDiv").hide();
		$("#waitSendDiv").hide();
		var result = $.parseJSON(data);
		if(result.success){
			$('#updateUserDialog').modal('hide');
			clearUpdateUserInfo();
			selectUser(paramMap);
		}else{
			alert(result.message);
		}
	}
	
	//清空添加用户的信息
	function clearAddUserInfo(){
		$("#userNameAdd").val('');
		$("#realNameAdd").val('');
		$("#passwordAdd").val('');
		$("#confirmPasswordAdd").val('');
		$("#userCodeAdd").val('');
		$("#emailAdd").val('');
		$("#mobilePhoneAdd").val('');
		$("#telphoneAdd").val('');
		$("#addressAdd").val('');
		$("#statusAdd option").eq(0).prop("checked",true);
		$("#userPicAdd").val('');
		$("#deptAdd").val('');	
		$("#deptCodeAdd").val('');	
		$("#deptIdAdd").val('');
	}
	
	function clearUpdateUserInfo(){
		$("#userNameUpdate").val('');
		$("#realNameUpdate").val('');
		$("#userCodeUpdate").val('');
		$("#emailUpdate").val('');
		$("#mobilePhoneUpdate").val('');
		$("#telphoneUpdate").val('');
		$("#addressUpdate").val('');
		$("#statusUpdate option").eq(0).prop("checked",true);
		$("#userPicUpdate").val('');
		$("#deptUpdate").val('');	
		$("#deptCodeUpdate").val('');	
		$("#deptIdUpdate").val('');
	}
	
	//初始化编辑用户表单
	function openEditDialog(userid){
		$.post("${ctx}/user/selectUserById.html",{userId:userid},function(data){
			var user = data.data;
			
			$("#userNameUpdate").val(user.userName);
			$("#realNameUpdate").val(user.realName);
			$("#userCodeUpdate").val(user.userCode);
			$("#emailUpdate").val(user.email);
			$("#mobilePhoneUpdate").val(user.mobilePhone);
			$("#telphoneUpdate").val(user.telphone);
			$("#addressUpdate").val(user.address);
			$("#statusUpdate option").each(function(){
				if($(this).val()==user.status){
					$(this).prop("selected",true);
				}
			});
			$("#updateUserId").val(user.userId);
			$("#deptCodeUpdate").val(user.deptCode);
			$("#deptIdUpdate").val(user.deptId);
			$("#deptUpdate").val(user.deptName);
		},"json");
		$('#updateUserDialog').modal('show');
	}
	
	function openRoleUserDialog(userId){
		$("#ruId").val(userId);
		$('#roleUserDialog').modal("show");
	}
	
	function selectRole(pageOfNum,currentPage){
		var param = {};
		param.roleName = $("#roleName").val();
		
		$.post("${ctx}/role/selectRoleByPage.html",param,function(data){
			var result = data.data.list;
			var count = data.data.count;
			var currentPage = data.data.currentPage;
			var totalPage = data.data.totalPage;
			
			var roleHtml = "";
			if(result!=null && result.length>0){
				for(var i=0;i<result.length;i++){
					var role = result[i];
					roleHtml += "<tr onclick='checkRole("+role.id+",\""+role.roleName+"\")'><td>"
					+(i+1)+"</td><td>"
					+role.roleName+"</td><td>"
					+role.roleDesc+"</td></tr>";
				}
			}
			
			$("#roleList").html(roleHtml);
			
			if(count>0){
				var options = {
					currentPage:currentPage,
					totalPages:totalPage,
					numberOfPages:pageOfNum,
					onPageClicked:function(event,originalEvent, type,page){
						selectRole(10,page);
					},
					itemTexts:function(type,page,current){
						switch (type) {  
							case "first":
								return "首页";
							case "prev":
								return "上一页";
							case "next":
								return "下一页";
							case "last":
								return "末页";
							case "page":
								return  page;
				            }                
						},
					shouldShowPage:true
				};
				$('#roleExample').bootstrapPaginator(options);
			}else{
				$('#roleExample').html('');
			}
		},"json");
	}
	
	function checkRole(roleId,roleName){
		var flag = true;
		$.each($("#roleIdList input[name='roleIdArr']"),function(){
			if($(this).val()==roleId){
				alert("当前角色已被选择");
				flag = false;
				return;
			}
		});
		
		if(flag){
			var content = '<li><span class="mr5">'+roleName+'<input type="hidden" name="roleIdArr" value="'+roleId+'"></span><button class="btn btn-mini" onclick="removeRole(this)"><i class="icon-remove"></i></button></li>';
			$("#roleIdList").append(content);
		}
	}
	
	function removeRole(obj){
		$(obj).parent().remove();
	}
	
	function cancelSaveRoleUser(){
		$("#ruId").val('');
		$("#roleName").val('');
		$("#roleIdList").html('');
	}
	
	function saveRoleUser(){
		var param = {};
		var roleIds = [];
		var userId = $("#ruId").val();
		var roleArr = $("#roleIdList input[name='roleIdArr']");
		if(roleArr.length>0){
			$.each(roleArr,function(){
				roleIds.push($(this).val());
			});
			
			param.userId = userId;
			param.roleIds = roleIds;
		}else{
			alert("请选择要设置的角色");
		}
		
		$.post("${ctx}/role/saveRoleUser.html",param,function(data){
			alert('设置成功');
			cancelSaveRoleUser();
			$('#roleUserDialog').modal('hide');
		},'json');
	}
</script>
</head>
<body>
	<div id="breadcrumb">
		<a href="${ctx }/index.html" title="返回首页" class="tip-bottom"><i class="icon-home"></i>首页</a>
		<a href="javascript:void(0)" class="current">员工管理</a>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span4">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"> <i class="icon-th"></i>
						</span>
						<h5>部门列表</h5>
					</div>
					<div class="widget-content nopadding">
						<ul id="departTree" class="ztree" style="width:260px; overflow:auto;"></ul>
					</div>
				</div>
			</div>
			<div class="span8">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"> <i class="icon-th"></i>
						</span>
						<h5>用户列表</h5>
						<span class="icon">
							<button class="btn btn-primary btn-mini" id="openAddUserBtn">
								<i class="icon-plus" title="增加"></i>
							</button>
						</span>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="userList">
							<thead>
								<tr>
									<th>用户头像</th>
									<th>用户名</th>
									<th>工号</th>
									<th>邮箱</th>
									<th>地址</th>
									<th>联系电话</th>
									<th>状态</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
					<div id="example"></div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="addUserDialog" title="新增用户" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3>新增用户</h3>
		</div>
		<div class="modal-body">
			<form action="${ctx }/user/addUser.html" method="post" class="form-horizontal" id="addUserForm" enctype="multipart/form-data">
				<div class="control-group">
					<label class="control-label">用户名</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-user"></i></span>
							<input type="text" name="userName" id="userNameAdd" maxlength="10" placeholder="请输入用户名"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">真实姓名</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-user"></i></span>
							<input type="text" name="realName" id="realNameAdd" maxlength="10" placeholder="请输入真实姓名"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">密码</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-lock"></i></span>
							<input type="password" name="password" id="passwordAdd" maxlength="20" placeholder="请输入密码"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">确认密码</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-lock"></i></span>
							<input type="password" name="confirmPassword" id="confirmPasswordAdd" maxlength="20" placeholder="请再次输入密码"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">用户工号</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-user"></i></span>
							<input type="text" name="userCode" id="userCodeAdd" maxlength="5" placeholder="请输入用户工号"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">邮箱</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-envelope"></i></span>
							<input class="span2" type="text" name="email" id="emailAdd" maxlength="30" placeholder="请输入用户邮箱">
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">联系方式</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-pencil"></i></span>
							<input type="text" name="mobilePhone" id="mobilePhoneAdd" maxlength="11" placeholder="请输入联系方式"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">电话</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-pencil"></i></span>
							<input type="text" name="telphone" id="telphoneAdd" maxlength="20" placeholder="请输入电话"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">地址</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-pencil"></i></span>
							<input type="text" name="address" id="addressAdd" maxlength="100" placeholder="请输入地址"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">入职状态</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-list"></i></span>
							<select id="statusAdd" name="status" class="span2">
								<option value="0">--请选择--</option>
								<option value="1">在职</option>
								<option value="2">离职</option>
								<option value="3">实习</option>
								<option value="4">试用期</option>
							</select>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">用户头像</label>
					<div class="controls">
						<input type="file" name="userPic" id="userPicAdd">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">所属部门</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-edit"></i></span>
							<input type="text" name="deptName" id="deptAdd" readonly="readonly">
						</div>
						<input type="hidden" name="deptCode" id="deptCodeAdd">
						<input type="hidden" name="deptId" id="deptIdAdd">
						<input type="button" value="请选择" class="btn btn-primary" id="selectDepartBtnAdd">
					</div>
					<div id="userAddTreeDiv" class="controls" style="display: none">
						<ul id="departAddTree" class="ztree" style="width:260px; overflow:auto;"></ul>
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<input type="button" class="btn" id="closeAddUserBtn" value="取消">
    		<input type="button" class="btn btn-primary" id="addUserBtn" value="保存"></a>
		</div>
	</div>
	
	<div id="updateUserDialog" title="用户编辑" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3>用户编辑</h3>
		</div>
		<div class="modal-body">
			<form action="${ctx }/user/editUser.html" method="post" class="form-horizontal" id="updateUserForm" enctype="multipart/form-data">
				<input type="hidden" id="updateUserId" name="userId">
				<div class="control-group">
					<label class="control-label">用户名</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-user"></i></span>
							<input type="text" name="userName" id="userNameUpdate" readonly="readonly" maxlength="10" placeholder="请输入用户名"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">真实姓名</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-user"></i></span>
							<input type="text" name="realName" id="realNameUpdate" maxlength="10" placeholder="请输入真实姓名"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">用户工号</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-user"></i></span>
							<input type="text" name="userCode" id="userCodeUpdate" maxlength="5" readonly="readonly" placeholder="请输入用户工号"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">邮箱</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-envelope"></i></span>
							<input class="span2" type="text" name="email" id="emailUpdate" maxlength="30" readonly="readonly" placeholder="请输入用户邮箱">
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">联系方式</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-pencil"></i></span>
							<input type="text" name="mobilePhone" id="mobilePhoneUpdate" maxlength="11" placeholder="请输入联系方式"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">电话</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-pencil"></i></span>
							<input type="text" name="telphone" id="telphoneUpdate" maxlength="20" placeholder="请输入电话"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">地址</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-pencil"></i></span>
							<input type="text" name="address" id="addressUpdate" maxlength="100" placeholder="请输入地址"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">入职状态</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-list"></i></span>
							<select id="statusUpdate" name="status" class="span2">
								<option value="0">--请选择--</option>
								<option value="1">在职</option>
								<option value="2">离职</option>
								<option value="3">实习</option>
								<option value="4">试用期</option>
							</select>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">用户头像</label>
					<div class="controls">
						<input type="file" name="userPic" id="userPicUpdate">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">所属部门</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-edit"></i></span>
							<input type="text" name="deptName" id="deptUpdate" readonly="readonly">
						</div>
						<input type="hidden" name="deptCode" id="deptCodeUpdate">
						<input type="hidden" name="deptId" id="deptIdUpdate">
						<input type="button" value="请选择" class="btn btn-primary" id="selectDepartBtnUpdate">
					</div>
					<div id="userUpdateTreeDiv" class="controls" style="display: none">
						<ul id="departUpdateTree" class="ztree" style="width:260px; overflow:auto;"></ul>
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<input type="button" class="btn" id="closeUpdateUserBtn" value="取消">
    		<input type="button" class="btn btn-primary" id="updateUserBtn" value="保存">
		</div>
	</div>
	
	<div id="roleUserDialog" title="角色设置" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3>角色设置</h3>
		</div>
		<div class="modal-body">
			<form method="post" class="form-horizontal">
				<input type="hidden" id="ruId">
				<div class="control-group">
					<label class="control-label">角色名称</label>
					<div class="controls">
						<input type="text" name="roleName" id="roleName">
						<button type="button" class="btn btn-primary" id="searchRole" onclick="selectRole(10,1)">查询</button>
					</div>
				</div>
				<div class="control-group">
					<table class="table table-bordered table-hover data-table">
						<thead>
							<tr>
								<th></th>
								<th>角色名称</th>
								<th>角色描述</th>
							</tr>
						</thead>
						<tbody id="roleList"></tbody>
					</table>
					<div id="roleExample"></div>
				</div>
				<div class="control-group">
					<label class="control-label">已选角色</label>
					<div class="controls">
						<ul style="list-style-type: none" id="roleIdList">
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<input type="button" class="btn" id="closeRoleUserBtn" value="取消">
    		<input type="button" class="btn btn-primary" id="saveRoleUserBtn" value="保存">
		</div>
	</div>
	
	<div class="brg2" id="bgHideDiv" style="display: none"></div>
	<div class="rzwaitBox" id="waitSendDiv" style="display: none">
		<div class="rzwait">
	        <img src="${ctx}/img/layout/rzwait.gif" />
	        <h1>发送中</h1>
	     </div>   
	</div>
</body>
</html>