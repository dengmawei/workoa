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
<title>模块中心</title>
<script type="text/javascript">
	var perPage = 10;
	var pageOfNum = 10;
	var defaultPage = 1;
	
	$(document).ready(function(){
		selectActionByPage(perPage,defaultPage);
		
		$("#addActionDialog").modal({
			keyboard:true,
			show:false
		});
		
		$("#editActionDialog").modal({
			keyboard:true,
			show:false
		});
		
		$.validator.addMethod("actionName_required",function(value){
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'Action名不能为空');
		
		$.validator.addMethod("actionValue_required",function(value){
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'Action值不能为空');
		
		$("#addActionForm").validate({
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
				actionValue:{
					actionValue_required:true
				}
			}
		});
	
		$("#editActionForm").validate({
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
				actionValue:{
					actionValue_required:true
				}
			}
		});
	});
	
	
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
					actionHtml += "<tr><td>"
					+(i+1)+"</td><td>"
					+action.actionName+"</td><td>"
					+action.actionValue+"</td><td>"
					+"<span class='icon'><button class='btn btn-mini' onclick='toEditAction("+action.id+")'><i class='icon-edit'></i></button></span>&nbsp;&nbsp;"
					+"<span class='icon'><button class='btn btn-mini' onclick='deleteAction("+action.id+")'><i class='icon-trash'></i></button></span></td></tr>";
				}
			}
			
			$("#actionList tbody").append(actionHtml);
			
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
						
						selectActionByPage(perPage,page);
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
	
	function deleteAction(id){
		if(window.confirm("您确定删除？")){
			$.post("${ctx}/action/deleteAction.html",{id:id},function(data){
				if(data.success){
					showTipInfo("删除成功");
					selectActionByPage(perPage,defaultPage);
				}else{
					showTipInfo(data.message);
				}
			},'json');
		}
	}
	
	function toAddAction(){
		$("#actionNameS").val('');
		$("#actionValueS").val('');
		$("#addActionDialog").modal("show");
	}
	
	function toEditAction(id){
		$.post("${ctx}/action/selectAction.html",{id:id},function(data){
			if(!data.success){
				showTipInfo(data.message);
				return;
			}
			
			var action = data.data;
			$("#actionId").val(action.id);
			$("#actionNameU").val(action.actionName);
			$("#actionValueU").val(action.actionValue);
			
			$("#editActionDialog").modal("show");
		},'json');
	}
	
	function editAction(){
		if($("#editActionForm").validate().form() && window.confirm("您确认提交？")){
			var param = {};
			param.id=$("#actionId").val();
			param.actionName=$("#actionNameU").val();
			param.actionValue=$("#actionValueU").val();
			$.post("${ctx}/action/editAction.html",param,function(data){
				if(!data.success){
					showTipInfo(data.message);
					return;
				}else{
					showTipInfo("编辑成功");
					$("#editActionDialog").modal("hide");
					selectActionByPage(perPage,defaultPage);
				}
			},'json');
		}
	}
	
	function cancelSave(){
		$("#actionNameS").val('');
		$("#actionValueS").val('');
		$("#addActionDialog").modal("hide");
	}
	
	function saveAction(){
		var param = {};
		param.actionName=$("#actionNameS").val();
		param.actionValue=$("#actionValueS").val();
		
		if($("#addActionForm").validate().form() && window.confirm("您确认提交？")){
			$.post("${ctx}/action/saveAction.html",param,function(data){
				if(!data.success){
					showTipInfo(data.message);
					return;
				}else{
					showTipInfo("添加成功");
					$("#addActionDialog").modal("hide");
					selectActionByPage(perPage,defaultPage);
				}
			},'json');
		}
	}
</script>
</head>
<body>
	<div id="breadcrumb">
		<a href="${ctx }/index.html" title="返回首页" class="tip-bottom"><i class="icon-home"></i>首页</a>
		<a href="javascript:void(0);" class="current">Action管理</a>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"> <i class="icon-th"></i>
						</span>
						<h5>Action列表</h5>
						<span class="icon">
							<button class="btn btn-mini" id="addBtn" onclick="toAddAction()"><i class="icon-plus"></i></button>
						</span>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="actionList">
							<thead>
								<tr>
									<th>序号</th>
									<th>模块名</th>
									<th>模块值</th>
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
	
	<div id="addActionDialog" title="新增Action" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3>新增Action</h3>
		</div>
		<div class="modal-body">
			<form action="${ctx }/action/saveAction.html" method="post" class="form-horizontal" id="addActionForm">
				<div class="control-group">
					<label class="control-label">Action名</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-edit"></i></span>
							<input type="text" name="actionName" id="actionNameS" maxlength="10" placeholder="请输入Action名"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">Action值</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-edit"></i></span>
							<input type="text" name="actionValue" id="actionValueS" maxlength="20" placeholder="请输入Action值"/>
						</div>
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<input type="button" class="btn" onclick="cancelSave()" value="取消">
    		<input type="button" class="btn btn-primary" onclick="saveAction()" value="保存">
		</div>
	</div>
	
	<div id="editActionDialog" title="编辑Action" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3>编辑Action</h3>
		</div>
		<div class="modal-body">
			<form action="${ctx }/action/editAction.html" method="post" class="form-horizontal" id="editActionForm">
				<input type="hidden" id="actionId">
				<div class="control-group">
					<label class="control-label">Action名</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-edit"></i></span>
							<input type="text" name="actionName" id="actionNameU" maxlength="10" placeholder="请输入Action名"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">Action值</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-edit"></i></span>
							<input type="text" name="actionValue" id="actionValueU" maxlength="20" placeholder="请输入Action值"/>
						</div>
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<input type="button" class="btn" value="取消" onclick="$('#editActionDialog').modal('hide')">
    		<input type="button" class="btn btn-primary" onclick="editAction()" value="保存">
		</div>
	</div>
</body>
</html>