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
		selectProcessConfigureByPage(perPage,defaultPage);
		
		$("#addConfigureDialog").modal({
			keyboard:true,
			show:false
		});
		
		$("#editConfigureDialog").modal({
			keyboard:true,
			show:false
		});
		
		
		$.validator.addMethod("processKey_required",function(value){
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'流程关键字不能为空');
		
		$.validator.addMethod("taskName_required",function(value){
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'任务名不能为空');
		
		$.validator.addMethod("taskDealUrl_required",function(value){
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'任务处理链接地址不能为空');
		
		$("#addConfigureForm").validate({
			success: function(element) {
				element.text('OK!').addClass('valid').closest('.control-group').removeClass('error').removeClass('success');
			},
			highlight: function(element) {
				$(element).closest('.control-group').removeClass('success').addClass('error');
			},
			rules:{
				processKey:{
					processKey_required:true
				},
				taskName:{
					taskName_required:true
				},
				taskDealUrl:{
					taskDealUrl_required:true
				}
			}
		});
	
		$("#editConfigureForm").validate({
			success: function(element) {
				element.text('OK!').addClass('valid').closest('.control-group').removeClass('error').removeClass('success');
			},
			highlight: function(element) {
				$(element).closest('.control-group').removeClass('success').addClass('error');
			},
			rules:{
				processKey:{
					processKey_required:true
				},
				taskName:{
					taskName_required:true
				},
				taskDealUrl:{
					taskDealUrl_required:true
				}
			}
		});
	});
	
	
	function selectProcessConfigureByPage(perPage,currentPage){
		$("#processConfigureList tbody").html("");
		var param = {};
		param.perPage=pageOfNum;
		param.currentPage=currentPage;
		var pkey = $("#queryProcessKey option:selected").val();
		param.processKey = pkey!="-1"?pkey:"";
		$.post("${ctx}/processConfigure/selectProcessConfigureByPage.html",param,function(data){
			if(!data.success){
				return;
			}
			
			var result = data.data.list;
			var count = data.data.count;
			var currentPage = data.data.currentPage;
			var totalPage = data.data.totalPage;
			
			var phtml = "";
			if(result!=null && result.length>0){
				for(var i=0;i<result.length;i++){
					var configure = result[i];
					phtml += "<tr><td>"
					+(i+1)+"</td><td>"
					+configure.processKey+"</td><td>"
					+configure.processName+"</td><td>"
					+configure.taskName+"</td><td>"
					+configure.taskDesc+"</td><td>"
					+configure.taskDealUrl+"</td><td>"
					+configure.createTime+"</td><td>"
					+"<span class='icon'><button class='btn btn-mini' onclick='editConfigure("+configure.id+")'><i class='icon-edit'></i></button></span>&nbsp;&nbsp;"
					+"<span class='icon'><button class='btn btn-mini' onclick='deleteConfigure("+configure.id+")'><i class='icon-trash'></i></button></span></td></tr>";
				}
			}
			
			$("#processConfigureList tbody").append(phtml);
			
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
						
						selectProcessConfigureByPage(perPage,page);
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
	
	function deleteConfigure(id){
		if(window.confirm("您确定删除？")){
			$.post("${ctx}/processConfigure/delete.html",{id:id},function(data){
				if(data.success){
					showTipInfo("删除成功");
					selectProcessConfigureByPage(perPage,defaultPage);
				}else{
					showTipInfo(data.message);
				}
			},'json');
		}
	}
	
	function toAddConfigure(){
		$("#taskNameS").val('');
		$("#taskDescS").val('');
		$("#taskDealUrlS").val('');
		$("#addConfigureDialog").modal("show");
	}
	
	function editConfigure(id){
		$.post("${ctx}/processConfigure/selectProcessConfigureById/"+id+".html",{},function(data){
			if(!data.success){
				showTipInfo(data.message);
				return;
			}
			
			var configure = data.data;
			
			$("#configureId").val(configure.id);
			$("#processKeyU option").each(function(){
				if($(this).val()==configure.processKey){
					$(this).prop("selected",true);
				}
			});
			$("#taskNameU").val(configure.taskName);
			$("#taskDescU").val(configure.taskDesc);
			$("#taskDealUrlU").val(configure.taskDealUrl);
			
			$("#editConfigureDialog").modal("show");
		},'json');
	}
	
	function subEditConfigure(){
		if($("#editConfigureForm").validate().form() && window.confirm("您确认提交？")){
			var param = {};
			param.id=$("#configureId").val();
			var optionObj = $("#processKeyU option:selected");
			param.processKey=$(optionObj).val();
			param.processName=$(optionObj).text();
			param.taskName=$("#taskNameU").val();
			param.taskDesc=$("#taskDescU").val();
			param.taskDealUrl=$("#taskDealUrlU").val();
			$.post("${ctx}/processConfigure/update.html",param,function(data){
				if(!data.success){
					showTipInfo(data.message);
					return;
				}else{
					showTipInfo("编辑成功");
					$("#editConfigureDialog").modal("hide");
					selectProcessConfigureByPage(perPage,defaultPage);
				}
			},'json');
		}
	}
	
	function cancelSave(){
		$("#processKeyS option").first().prop("selected",true);
		$("#taskNameS").val('');
		$("#taskDescS").val('');
		$("#taskDealUrlS").val('');
		$("#addConfigureDialog").modal("hide");
	}
	
	function saveConfigure(){
		var param = {};
		var optionObj = $("#processKeyS option:selected");
		param.processKey=$(optionObj).val();
		param.processName=$(optionObj).text();
		param.taskName=$("#taskNameS").val();
		param.taskDesc=$("#taskDescS").val();
		param.taskDealUrl=$("#taskDealUrlS").val();
		
		if($("#addConfigureForm").validate().form() && window.confirm("您确认提交？")){
			$.post("${ctx}/processConfigure/save.html",param,function(data){
				if(!data.success){
					showTipInfo(data.message);
					return;
				}else{
					showTipInfo("添加成功");
					$("#addConfigureDialog").modal("hide");
					selectProcessConfigureByPage(perPage,defaultPage);
				}
			},'json');
		}
	}
	
	function openSearch(){
		if($("#searchBox").is(":visible")==false){
			$("#searchBox").show();
		}else{
			$("#searchBox").hide();
		}
	}
	
	function clearQueryInfo(){
		$("#queryProcessKey option").first().prop("selected",true);
	}
</script>
</head>
<body>
	<div id="breadcrumb">
		<a href="${ctx }/index.html" title="返回首页" class="tip-bottom"><i class="icon-home"></i>首页</a>
		<a href="javascript:void(0);" class="current">流程配置管理</a>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"> <i class="icon-th"></i>
						</span>
						<h5>流程配置列表</h5>
						<span class="icon">
							<button class="btn btn-mini" id="addBtn" onclick="toAddConfigure()"><i class="icon-plus"></i></button>
							<button class="btn btn-mini" id="openSearchBtn" onclick="openSearch()"><i class="icon-search" title="搜索"></i></button>
						</span>
					</div>
					<div class="widget-content nopadding" style="display: none" id="searchBox">
						<form class="form-horizontal">
							<div class="control-group">
								<label class="control-label">所属流程</label>
								<div class="controls w50p">
									<select id="queryProcessKey">
										<option value="-1" selected="selected"></option>
										<c:forEach items="${keys }" var="key">
											<option value="${key.key }">${key.value }</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-actions">
								<button type="button" class="btn btn-primary" onclick="selectProcessConfigureByPage(pageOfNum,defaultPage);">查询</button>
								<button type="button" class="btn" onclick="clearQueryInfo();">清空</button>
							</div>
						</form>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="processConfigureList">
							<thead>
								<tr>
									<th>序号</th>
									<th>流程关键字</th>
									<th>流程名</th>
									<th>任务名</th>
									<th>任务描述</th>
									<th>任务处理地址</th>
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
	
	<div id="addConfigureDialog" title="新增配置" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3>新增配置</h3>
		</div>
		<div class="modal-body">
			<form action="${ctx }/processConfigure/save.html" method="post" class="form-horizontal" id="addConfigureForm">
				<div class="control-group">
					<label class="control-label">流程关键字</label>
					<div class="controls">
						<select id="processKeyS" name="processKey">
							<c:forEach items="${keys }" var="key">
								<option value="${key.key }">${key.value }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">任务名称</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-edit"></i></span>
							<input type="text" name="taskName" id="taskNameS" maxlength="50" placeholder="请输入任务名称"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">任务描述</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-edit"></i></span>
							<input type="text" name="taskDesc" id="taskDescS" maxlength="100" placeholder="请输入任务描述"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">任务处理请求地址</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-edit"></i></span>
							<input type="text" name="taskDealUrl" id="taskDealUrlS" maxlength="100" placeholder="请输入任务处理请求地址"/>
						</div>
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<input type="button" class="btn" onclick="cancelSave()" value="取消">
    		<input type="button" class="btn btn-primary" onclick="saveConfigure()" value="保存">
		</div>
	</div>
	
	<div id="editConfigureDialog" title="编辑配置" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3>编辑配置</h3>
		</div>
		<div class="modal-body">
			<form action="${ctx }/processConfigure/update.html" method="post" class="form-horizontal" id="editConfigureForm">
				<input type="hidden" id="configureId">
				<div class="control-group">
					<label class="control-label">流程关键字</label>
					<div class="controls">
						<select id="processKeyU" name="processKey">
							<c:forEach items="${keys }" var="key">
								<option value="${key.key }">${key.value }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">任务名称</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-edit"></i></span>
							<input type="text" name="taskName" id="taskNameU" maxlength="50" placeholder="请输入任务名称"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">任务描述</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-edit"></i></span>
							<input type="text" name="taskDesc" id="taskDescU" maxlength="100" placeholder="请输入任务描述"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">任务处理请求地址</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-edit"></i></span>
							<input type="text" name="taskDealUrl" id="taskDealUrlU" maxlength="100" placeholder="请输入任务处理请求地址"/>
						</div>
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<input type="button" class="btn" value="取消" onclick="$('#editConfigureDialog').modal('hide')">
    		<input type="button" class="btn btn-primary" onclick="subEditConfigure()" value="保存">
		</div>
	</div>
</body>
</html>