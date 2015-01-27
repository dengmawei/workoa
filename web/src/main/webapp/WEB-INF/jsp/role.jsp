<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:include page="/include.jsp"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:set value="${pageContext.request.contextPath}" var="ctx" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx }/js/bootstrap-pagination/bootstrap-paginator.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery-validation-1.8.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx }/js/Jquery-Validate-For-Bootstrap-master/jquery.validate.bootstrap.js"></script>
<script type="text/javascript" src="${ctx }/js/twitter-bottstrap/js/bootstrap-modal.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery-form/jquery.form.js"></script>
<title>角色中心</title>
<script type="text/javascript">
	var perPage = 10;
	var pageOfNum = 5;
	var defaultPage = 1;
	
	$(document).ready(function(){
		selectRoleByPage(perPage,defaultPage);
		
		$('#addRoleDialog').modal({
			keyboard:true,
			show:false	
		});
		
		$("#editRoleDialog").modal({
			keyboard:true,
			show:false	
		});
		
		$("#cancelSaveBtn").click(function(){
			cancelAddRole();
		});
		
		$("#cancelEditBtn").click(function(){
			cancelEditRole();
		});
		
		$("#saveRoleBtn").click(function(){
			saveRole();
		});
		
		$("#editRoleBtn").click(function(){
			editRole();
		});
		
		$("#searchRole").click(function(){
			selectRoleByPage(perPage,defaultPage);
		});
		
		$.validator.addMethod("roleName_required",function(value){
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'角色名不能为空');
		
		$("#addRoleForm").validate({
			success: function(element) {
				element.text('OK!').addClass('valid').closest('.control-group').removeClass('error').removeClass('success');
			},
			highlight: function(element) {
				$(element).closest('.control-group').removeClass('success').addClass('error');
			},
			rules:{
				roleName:{
					roleName_required:true
				}
			}
		});
		
		$("#editRoleForm").validate({
			success: function(element) {
				element.text('OK!').addClass('valid').closest('.control-group').removeClass('error').removeClass('success');
			},
			highlight: function(element) {
				$(element).closest('.control-group').removeClass('success').addClass('error');
			},
			rules:{
				roleName:{
					roleName_required:true
				}
			}
		});
	});
	
	function saveRole(){
		if($("#addRoleForm").validate().form()){
			if(window.confirm("您确定提交？")){
				var param = {};
				param.roleName = $("#roleNameAdd").val();
				param.roleDesc = $("#roleDescAdd").val();
				$.post("${ctx }/role/addRole.html",param,function(data){
					if(data.success){
						cancelAddRole();
						selectRoleByPage(perPage,defaultPage);
					}else{
						alert(data.message);
					}
				},"json");
			}
		}	
	}
	
	function editRole(){
		if($("#editRoleForm").validate().form()){
			if(window.confirm("您确定提交？")){
				var param = {};
				param.id = $("#roleIdUpdate").val();
				param.roleName = $("#roleNameUpdate").val();
				param.roleDesc = $("#roleDescUpdate").val();
				$.post("${ctx }/role/editRole.html",param,function(data){
					if(data.success){
						cancelEditRole();
						selectRoleByPage(perPage,defaultPage);
					}else{
						alert(data.message);
					}
				},"json");
			}
		}	
	}
	
	function cancelAddRole(){
		$("#addRoleDialog").modal("hide");
		$("#roleNameAdd").val('');
		$("#roleDescAdd").val('');
	}
	
	function cancelEditRole(){
		$("#editRoleDialog").modal("hide");
		$("#roleIdUpdate").val('');
		$("#roleNameUpdate").val('');
		$("#roleDescUpdate").val('');
	}
	
	function selectRoleByPage(perPage,currentPage){
		$("#roleList tbody").html("");
		var param = {};
		param.perPage=pageOfNum;
		param.currentPage=currentPage;
		param.roleName = $("#roleNameS").val();
		param.roleCode = $("#roleCodeS").val();
		$.post("${ctx}/role/selectRoleByPage.html",param,function(data){
			if(!data.success){
				return;
			}
			
			var result = data.data.list;
			var count = data.data.count;
			var currentPage = data.data.currentPage;
			var totalPage = data.data.totalPage;
			
			var roleHtml = "";
			if(result!=null && result.length>0){
				for(var i=0;i<result.length;i++){
					var role = result[i];
					var rolePermitManager = role.id==1?"":"<span class='icon'><button class='btn btn-mini' title='角色权限管理' onclick='toRolePermit("+role.id+")'><i class='icon-wrench'></i></button></span>";
					roleHtml += "<tr><td>"
					+role.id+"</td><td>"
					+role.roleName+"</td><td>"
					+role.roleDesc+"</td><td>"
					+role.createTime+"</td><td align='center'>"
					+"<span class='icon'><button class='btn btn-mini' title='编辑' onclick='openEditDialog("+role.id+")'><i class='icon-edit'></i></button></span>&nbsp;&nbsp;"
					+"<span class='icon'><button class='btn btn-mini' title='删除' onclick='deleteRole("+role.id+")'><i class='icon-trash'></i></button></span>&nbsp;&nbsp;"
					+"<span class='icon'><button class='btn btn-mini' title='人员角色管理' onclick='setRoleUser("+role.id+")'><i class='icon-user'></i></button></span>&nbsp;&nbsp;"
					+rolePermitManager+"</td></tr>";
				}
			}
			
			$("#roleList tbody").append(roleHtml);
			
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
						selectRoleByPage(perPage,page);
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
		},"json");
	}
	
	function openSaveRoleDialog(){
		$("#roleNameAdd").val('');
		$("#roleCodeAdd").val('');
		$("#roleDescAdd").val('');
		$('#addRoleDialog').modal("show");
	}
	
	function openEditDialog(roleId){
		$.post("${ctx}/role/seletRoleById.html",{roleId:roleId},function(data){
			if(data.success){
				var role = data.data;
				$("#roleNameUpdate").val(role.roleName);
				$("#roleDescUpdate").val(role.roleDesc);
				$("#roleIdUpdate").val(role.id);
				
				$("#editRoleDialog").modal("show");
			}else{
				return;
			}
		},"json");
	}
	
	function deleteRole(roleId){
		if(window.confirm("您确认删除？")){
			$.post("${ctx}/role/deleteRole.html",{roleId:roleId},function(data){
				if(data.success){
					showTipInfo("删除成功");
					selectRoleByPage(perPage,defaultPage);
				}else{
					showTipInfo(data.message);
				}
			},"json");
		}
	}
	
	function setRoleUser(roleId){
		window.location.href="${ctx}/role/toRoleUser/"+roleId+".html?sid=${sid}";
	}
	
	function openSearch(){
		if($("#searchBox").is(":visible")==false){
			$("#searchBox").show();
		}else{
			$("#searchBox").hide();
		}
	}
	
	function toRolePermit(roleId){
		window.location.href="${ctx}/role/selectRolePermit/"+roleId+".html?sid=${sid}";	
	}
	
	function clearQueryInfo(){
		$("#roleNameS").val('');
	}
</script>
</head>
<body>
	<div id="breadcrumb">
		<a href="${ctx }/index.html" title="返回首页" class="tip-bottom"><i class="icon-home"></i>首页</a>
		<a href="javascript:void(0)" class="current">角色列表</a>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"><i class="icon-th"></i></span>
						<h5>角色列表</h5>
						<span class="icon">
							<button class="btn btn-mini" title="增加" id="openAddRoleBtn" onclick="openSaveRoleDialog()">
								<i class="icon-plus"></i>
							</button>
							<button class="btn btn-mini" title="搜索" id="openSearchBtn" onclick="openSearch()">
								<i class="icon-search"></i>
							</button>
						</span>
					</div>
					<div class="widget-content nopadding" style="display: none" id="searchBox">
						<form class="form-horizontal">
							<div class="control-group">
								<label class="control-label">角色名称</label>
								<div class="controls w50p">
									<input type="text" id="roleNameS" maxlength="30">
								</div>
							</div>
							<div class="form-actions">
								<button type="button" class="btn btn-primary" id="searchRole">查询</button>
								<button type="button" class="btn" onclick="clearQueryInfo();">清空</button>
							</div>
						</form>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="roleList">
							<thead>
								<tr>
									<th>角色id</th>
									<th>角色名</th>
									<th>角色描述</th>
									<th>创建时间</th>
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
	
	<div id="addRoleDialog" title="新增角色" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3>新增角色</h3>
		</div>
		<div class="modal-body">
			<form action="${ctx }/role/addRole.html" method="post" class="form-horizontal" id="addRoleForm">
				<div class="control-group">
					<label class="control-label">角色名</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-edit"></i></span>
							<input type="text" name="roleName" id="roleNameAdd" maxlength="32" placeholder="请输入角色名"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">角色描述</label>
					<div class="controls">
						<textarea rows="5" cols="20" name="roleDesc" id="roleDescAdd" placeholder="请输入角色描述"></textarea>
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<input type="button" class="btn" id="cancelSaveBtn" value="取消">
    		<input type="button" class="btn btn-primary" id="saveRoleBtn" value="保存">
		</div>
	</div>
	
	<div id="editRoleDialog" title="编辑角色" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3>角色编辑</h3>
		</div>
		<div class="modal-body">
			<form action="${ctx }/role/editRole.html" method="post" class="form-horizontal" id="editRoleForm">
				<input type="hidden" id="roleIdUpdate">
				<div class="control-group">
					<label class="control-label">角色名</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-edit"></i></span>
							<input type="text" name="roleName" id="roleNameUpdate" maxlength="32" placeholder="请输入角色名"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">角色描述</label>
					<div class="controls">
						<textarea rows="5" cols="20" name="roleDesc" id="roleDescUpdate" placeholder="请输入角色描述"></textarea>
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<input type="button" class="btn" id="cancelEditBtn" value="取消">
    		<input type="button" class="btn btn-primary" id="editRoleBtn" value="保存">
		</div>
	</div>
</body>
</html>