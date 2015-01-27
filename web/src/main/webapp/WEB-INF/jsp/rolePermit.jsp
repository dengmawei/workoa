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
	var defaultPage = 1;
	
	$(document).ready(function(){
		$('#rolePermitDialog').modal({
			keyboard:true,
			show:false	
		});
	});
	
	function setPermit(roleId,roleName){
		$("#roleIdP").val(roleId);
		$("#roleNameP").val(roleName);
		clearSetPermit();
		
		$("#rolePermitDialog").modal('show');
	}
	
	function clearSetPermit(){
		$("#moduleId option").eq(0).prop("selected",true);
		$("#permitList tbody").html("");
		$('#permitExample').html('');
		$("#permitIdList").html('');
		hidePermitQueryInfo();
	}
	
	function searchPermit(perPage,currentPage){
		$("#permitList tbody").html("");
		var param = {};
		param.perPage=perPage;
		param.currentPage=currentPage;
		param.moduleId=$("#moduleId").val();
		$.post("${ctx}/permit/selectPermitByPage.html",param,function(data){
			if(!data.success){
				return;
			}
			
			var result = data.data.list;
			var count = data.data.count;
			var currentPage = data.data.currentPage;
			var totalPage = data.data.totalPage;
			
			var permitHtml = "";
			if(result!=null && result.length>0){
				for(var i=0;i<result.length;i++){
					var permit = result[i];
					permitHtml += "<tr><td>"
					+"<input type='checkbox' name='permitIds' value='"+permit.id+"' onclick='checkPermit(this,"+permit.id+",\""+permit.permitDesc+"\")'/></td><td>"
					+permit.permitDesc+"</td><td>"
					+permit.permitCode+"</td><td>"
					+permit.permitValue+"</td></tr>";
				}
			}
			$("#permitList tbody").append(permitHtml);
			
			
			if(count>0){
				var options = {
					currentPage:currentPage,
					totalPages:totalPage,
					numberOfPages:perPage,
					onPageClicked:function(event,originalEvent, type,page){
						if(currentPage==1 && (type=='first' || type=='prev')){
							return;
						}
						
						if(currentPage==totalPage && (type=='last' || type=='next')){
							return;
						}
						searchPermit(perPage,page);
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
				$('#permitExample').bootstrapPaginator(options);
			}else{
				$('#permitExample').html('');
			}
			
			showPermitQueryInfo();
		},"json");
	}
	
	function checkPermit(obj,permitId,permitDesc){
		if($(obj).prop("checked")){
			var flag = true;
			
			$.each($("#permitIdList input[name='permitIdArr']"),function(){
				if($(this).val()==permitId){
					alert("该权限已被选择");
					flag = false;
					return;
				}
			});
			
			if(flag){
				var content = '<li><span class="mr5">'+permitDesc+'<input type="hidden" name="permitIdArr" value="'+permitId+'"></span><button class="btn btn-mini" onclick="removePermit(this)"><i class="icon-remove"></i></button></li>';
				$("#permitIdList").append(content);
			}
		}else{
			$.each($("#permitIdList input[name='permitIdArr']"),function(){
				if($(this).val()==permitId){
					$(this).parents("li").remove();
					return;
				}
			});
		}
	}
	
	function removePermit(obj){
		var permitId = $(obj).prev("span").children("input[name='permitIdArr']").val();
		$.each($("#permitList input[name='permitIds']"),function(){
			if($(this).val()==permitId){
				$(this).prop("checked",false);
				return false;
			}
		});
		$(obj).parent().remove();
	}
	
	function cancelSavePermit(){
		clearSetPermit();
		$("#rolePermitDialog").modal('hide');
	}
	
	function showPermitQueryInfo(){
		$("#permitList").removeClass("hidden").addClass("visible");
	}
	
	function hidePermitQueryInfo(){
		$("#permitList").removeClass("visible").addClass("hidden");
	}
	
	function saveRolePermit(){
		var roleId = $("#roleIdP").val();
		var permitIdArr = [];
		$.each($("#permitIdList input[name='permitIdArr']"),function(){
			permitIdArr.push($(this).val());
		});
		
		if(permitIdArr.length==0){
			alert("请选择权限");
			return;
		}
		
		var param = {};
		param.roleId = roleId;
		param.permitIdArr = permitIdArr;
		$.post("${ctx}/role/saveRolePermit.html",param,function(data){
			if(data.success){
				$("#rolePermitDialog").modal('hide');
				showTipInfo("添加成功");
				setTimeout(window.location.reload(),1000);
			}else{
				alert(data.message);
			}
		},'json');
	}
	
	function deleteRolePermit(roleId,permitId){
		if(window.confirm("您确定删除？")){
			$.post("${ctx}/role/deleteRolePermit.html",{roleId:roleId,permitId:permitId},function(data){
				if(data.success){
					showTipInfo("删除成功！");
					window.location.reload();
				}else{
					showTipInfo(data.message);
				}
			},"json");
		}
	}
</script>
</head>
<body>
	<div id="breadcrumb">
		<a href="${ctx }/index.html" title="返回首页" class="tip-bottom"><i class="icon-home"></i>首页</a>
		<a href="${ctx }/role/index.html?sid=${sid}" title="角色列表" class="tip-bottom">角色列表</a>
		<a href="javascript:void(0)" class="current">角色权限列表</a>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"><i class="icon-th"></i></span>
						<h5>角色:<font color="green">${role.roleDesc }</font></h5>
						<h5>角色权限列表</h5>
						<span class="icon">
							<button class="btn btn-mini" title="增加" onclick="setPermit('${role.id}','${role.roleName}')">
								<i class="icon-plus"></i>
							</button>
						</span>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table">
							<thead>
								<tr>
									<th>序号</th>
									<th>权限描述</th>
									<th>权限值</th>
									<th>权限编码</th>
									<th>创建时间</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${list!=null }">
									<c:forEach items="${list }" var="permit" varStatus="i">
										<tr>
											<td>${i.index+1 }</td>
											<td>${permit.permitDesc }</td>
											<td>${permit.permitValue }</td>
											<td>${permit.permitCode }</td>
											<td>${permit.createTime }</td>
											<td><span class='icon'><button class='btn btn-mini' title='删除' onclick='deleteRolePermit(${permit.roleId},${permit.permitId })'><i class='icon-trash'></i></button></span></td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="rolePermitDialog" title="权限设置" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3>权限设置</h3>
		</div>
		<div class="modal-body">
			<form method="post" class="form-horizontal">
				<div class="control-group">
					<label class="control-label">角色名称</label>
					<div class="controls">
						<input type="hidden" id="roleIdP">
						<input type="text" id="roleNameP" readonly="readonly">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">模块名称</label>
					<div class="controls">
						<select name="moduleId" id="moduleId" class="w100">
							<option value="0" selected="selected">--请选择--</option>
							<c:forEach items="${moduleList }" var="module">
								<option value="${module.id }">${module.moduleName }</option>
							</c:forEach>
						</select>
						<button type="button" class="btn btn-primary" onclick="searchPermit(perPage,defaultPage);">查询</button>
					</div>
				</div>
				<div class="control-group hidden" id="permitList">
					<table class="table table-bordered table-hover data-table">
						<thead>
							<tr>
								<th>序号</th>
								<th>权限描述</th>
								<th>权限编码</th>
								<th>权限值</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
					<div id="permitExample"></div>
				</div>
				<div class="control-group">
					<label class="control-label">已选权限</label>
					<div class="controls">
						<ul style="list-style-type: none" id="permitIdList">
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<input type="button" class="btn" onclick="cancelSavePermit()" value="取消">
    		<input type="button" class="btn btn-primary" onclick="saveRolePermit()" value="保存">
		</div>
	</div>
</body>
</html>