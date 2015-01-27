<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
	var pageOfNum = 10;
	var defaultPage = 1;
	var roleId = '${roleId}';
	
	$(document).ready(function(){
		selectRoleUserByPage(pageOfNum,defaultPage);
	});
	
	
	function selectRoleUserByPage(perPage,currentPage){
		$("#roleUserList tbody").html("");
		var param = {};
		param.perPage=perPage;
		param.currentPage=currentPage;
		param.roleId=roleId;
		param.userCode = $("#userCode").val();
		param.userName = $("#userName").val();
		$.post("${ctx}/role/selectRoleUserByPage.html",param,function(data){
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
					roleHtml += "<tr><td>"
					+(i+1)+"</td><td>"
					+role.roleName+"</td><td>"
					+role.realName+"</td><td>"
					+role.userCode+"</td><td>"
					+"<span class='icon'><button class='btn btn-mini' onclick='deleteRoleUser("+role.ruId+")'><i class='icon-trash'></i></button></span></td></tr>";
				}
			}
			
			$("#roleUserList tbody").append(roleHtml);
			
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
						selectRoleUserByPage(pageOfNum,page);
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
	
	function openSearch(){
		if($("#searchBox").is(":visible")==false){
			$("#searchBox").show();
		}else{
			$("#searchBox").hide();
		}
	}
	
	function deleteRoleUser(ruId){
		if(window.confirm("您确定删除？")){
			$.post("${ctx}/role/deleteRoleUser.html",{ruId:ruId},function(data){
				if(data.success){
					showTipInfo("删除成功");
					clearQueryInfo();
					selectRoleUserByPage(pageOfNum,defaultPage);
				}
			},'json');
		}
	}
	
	function clearQueryInfo(){
		$("#userCode").val('');
		$("#userName").val('');
	}
</script>
</head>
<body>
	<div id="breadcrumb">
		<a href="${ctx }/index.html" title="返回首页" class="tip-bottom"><i class="icon-home"></i>首页</a>
		<a href="${ctx }/role/index.html?sid=${sid}" class="tip-bottom" title="角色列表">角色列表</a>
		<a href="javascript:void(0)" class="current">人员角色查询</a>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"> <i class="icon-th"></i>
						</span>
						<h5>角色:<font color="green">${role.roleDesc }</font></h5>
						<h5>角色用户列表</h5>
						<span class="icon">
							<button class="btn btn-mini" id="openSearchBtn" title="搜索" onclick="openSearch()"><i class="icon-search"></i></button>
						</span>
					</div>
					<div class="widget-content nopadding" style="display: none" id="searchBox">
						<form class="form-horizontal">
							<div class="control-group">
								<label class="control-label">员工名</label>
								<div class="controls w50p">
									<input type="text" id="userName" maxlength="30">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">员工编码</label>
								<div class="controls w50p">
									<input type="text" id="userCode" maxlength="5">
								</div>
							</div>
							<div class="form-actions">
								<button type="button" class="btn btn-primary" onclick="selectRoleUserByPage(pageOfNum,defaultPage);">查询</button>
								<button type="button" class="btn" onclick="clearQueryInfo()">清空</button>
							</div>
						</form>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="roleUserList">
							<thead>
								<tr>
									<th>序号</th>
									<th>角色名</th>
									<th>用户名</th>
									<th>用户编码</th>
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
</body>
</html>