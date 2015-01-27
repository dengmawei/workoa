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
<title>权限中心</title>
<script type="text/javascript">
	var pageOfNum = 5;
	var defaultPage = 1;
	var moduleId = "${module.id}";
	
	$(document).ready(function(){
		selectPermit();
		
		$("#setPermitDialog").modal({
			keyboard:true,
			show:false
		});
		
		$.validator.addMethod("actionName_required",function(value){
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'Action不能为空');
		
		$.validator.addMethod("permitDesc_required",function(value){
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'权限描述不能为空');
		
		
		$("#setPermitForm").validate({
			success: function(element) {
				element.text('OK!').addClass('valid').closest('.control-group').removeClass('error').removeClass('success');
			},
			highlight: function(element) {
				$(element).closest('.control-group').removeClass('success').addClass('error');
			},
			rules:{
				actionName:{
					actionName_required:true
				},
				permitDesc:{
					permitDesc_required:true
				}
			}
		});
	});
	
	
	function selectPermit(){
		$("#permitList tbody").html("");
		$.post("${ctx}/permit/selectPermit.html",{moduleId:moduleId},function(data){
			if(!data.success){
				return;
			}
			
			var result = data.data;
			
			var permitHtml = "";
			if(result!=null && result.length>0){
				for(var i=0;i<result.length;i++){
					var permit = result[i];
					permitHtml += "<tr><td>"
					+(i+1)+"</td><td>"
					+permit.permitValue+"</td><td>"
					+permit.permitCode+"</td><td>"
					+permit.permitDesc+"</td><td>"
					+permit.createTime+"</td><td>"
					+"<span class='icon'><button class='btn btn-mini' onclick='deletePermit("+permit.id+")'><i class='icon-trash'></i></button></span></td></tr>";
				}
			}
			
			$("#permitList tbody").append(permitHtml);
		},"json");
	}
	
	function toAddPermit(){
		clearPermitInfo();
		$("#setPermitDialog").modal('show');
	}
	
	function selectActionByPage(perPage,currentPage){
		$("#actionList tbody").html("");
		var param = {};
		param.perPage=pageOfNum;
		param.currentPage=currentPage;
		$.post("${ctx}/action/selectActionByPage.html",param,function(data){
			if(!data.success){
				return;
			}
			
			var result = data.data.list;
			var count = data.data.count;
			var currentPage = data.data.currentPage;
			var totalPage = data.data.totalPage;
			
			var actionHtml = "";
			if(result!=null && result.length>0){
				for(var i=0;i<result.length;i++){
					var action = result[i];
					actionHtml += "<tr onclick='checkAction("+action.id+",\""+action.actionName+"\")'><td>"
					+"<input type='radio' name='actionRadio' value='"+action.id+"' onclick='checkAction("+action.id+",\""+action.actionName+"\")'/></td><td>"
					+action.actionName+"</td></tr>";
				}
			}
			
			$("#actionList tbody").append(actionHtml);
			queryActionIsChecked();
			
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
						
						selectActionByPage(pageOfNum,page);
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
				$('#actionExample').bootstrapPaginator(options);
			}else{
				$('#actionExample').html('');
			}
		},"json");
	}
	
	function checkAction(actionId,actionName){
		$("#actionId").val(actionId);
		$("#actionName").val(actionName);
		$("#actionList").removeClass("visible").addClass("hidden");
	}
	
	function queryActionIsChecked(){
		var actionId = $("#actionId").val();
		if(actionId==null || actionId==""){
			return;
		}
		
		$.each($("#actionList input[name='actionRadio']"),function(){
			if($(this).val()==actionId){
				$(this).prop("checked",true);
				return;
			}
		});
	}
	
	function closeActionQueryBox(){
		if($("#actionList").is(":visible")){
			$("#actionList").removeClass("visible").addClass("hidden");
		}else{
			$("#actionList").removeClass("hidden").addClass("visible");
		}
	}
	
	function cancelSavePermit(){
		clearPermitInfo();
		$("#setPermitDialog").modal('hide');
	}
	
	function savePermit(){
		if($("#setPermitForm").validate().form() && window.confirm("您确定提交？")){
			var param = {};
			param.actionId = $("#actionId").val();
			param.moduleId = $("#moduleId").val();
			param.permitDesc = $("#permitDesc").val();
			$.post("${ctx}/permit/savePermit.html",param,function(data){
				if(data.success){
					showTipInfo("设置成功");
					$("#setPermitDialog").modal('hide');
					selectPermit();
				}else{
					alert(data.message);
				}
			},'json');
		}
	}
	
	function deletePermit(id){
		if(window.confirm("您确定删除？")){
			$.post("${ctx}/permit/deletePermit.html",{id:id},function(data){
				if(data.success){
					showTipInfo("删除成功");
					selectPermit();
				}else{
					showTipInfo(data.message);
				}
			},'json');
		}
	}
	
	function clearPermitInfo(){
		$("#actionId").val('');
		$("#actionName").val('');
		$("#permitDesc").val('');
		$("#actionList tbody").html('');
		$("#actionList").removeClass("visible").addClass("hidden");
		$("#actionExample").html('');
	}
</script>
</head>
<body>
	<div id="breadcrumb">
		<a href="${ctx }/index.html" title="返回首页" class="tip-bottom"><i class="icon-home"></i>首页</a>
		<a href="${ctx }/module/index.html?sid=${sid}" title="模块列表" class="tip-bottom"><i class="icon-home"></i>模块列表</a>
		<a href="javascript:void(0);" class="current">权限管理</a>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"> <i class="icon-th"></i>
						</span>
						<h5>模块名称：${module.moduleName }</h5>
						<span class="icon">
							<button class="btn btn-mini" id="addBtn" onclick="toAddPermit()"><i class="icon-plus"></i></button>
						</span>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="permitList">
							<thead>
								<tr>
									<th>序号</th>
									<th>权限值</th>
									<th>权限编码</th>
									<th>权限描述</th>
									<th>创建时间</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="setPermitDialog" title="权限设置" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3>权限设置</h3>
		</div>
		<div class="modal-body">
			<form method="post" class="form-horizontal" id="setPermitForm">
				<div class="control-group">
					<label class="control-label">模块</label>
					<div class="controls w50p">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-edit"></i></span>
							<input type="hidden" id="moduleId" value="${module.id }">
							<input type="text" id="moduleName" value="${module.moduleName }" readonly="readonly">
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">Action</label>
					<div class="controls w50p">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-edit"></i></span>
							<input type="hidden" name="actionId" id="actionId">
							<input type="text" name="actionName" id="actionName" readonly="readonly" onclick="selectActionByPage(pageOfNum,defaultPage);closeActionQueryBox();">
						</div>
					</div>
				</div>
				<div class="control-group hidden" id="actionList">
					<table class="table table-bordered table-hover data-table">
						<thead>
							<tr>
								<th><button class="btn btn-mini" title="关闭" onclick="closeActionQueryBox()"><i class="icon-folder-close"></i></button></th>
								<th>Action名称</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
					<div id="actionExample"></div>
				</div>
				<div class="control-group">
					<label class="control-label">权限描述</label>
					<div class="controls">
						<textarea rows="10" cols="20" id="permitDesc" name="permitDesc"></textarea>
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<input type="button" class="btn" onclick="cancelSavePermit()" value="取消">
    		<input type="button" class="btn btn-primary" onclick="savePermit()" value="保存">
		</div>
	</div>
</body>
</html>