<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:set value="${pageContext.request.contextPath}" var="ctx" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx }/js/bootstrap-pagination/bootstrap-paginator.js"></script>
<script type="text/javascript" src="${ctx }/js/twitter-bottstrap/js/bootstrap-modal.js"></script>
<title>流程管理中心</title>
<script type="text/javascript">
	var pageOfNum = 10;
	var defaultPage = 1;
	
	$(document).ready(function(){
		selectTaskInProcess();
	});
	
	function selectTaskInProcess(perPage,currentPage){
		$("#processList tbody").html("");
		var param = {};
		param.perPage=pageOfNum;
		param.currentPage=currentPage;
		param.processType = $("#processType").val();
		param.processStatus = $("#processStatus").val();
		$.post("${ctx}/processManage/findTaskInProcess.html",param,function(data){
			if(!data.success){
				return;
			}
			
			var result = data.data.list;
			var count = data.data.count;
			var currentPage = data.data.currentPage;
			var totalPage = data.data.totalPage;
			
			var taskhtml = "";
			if(result!=null && result.length>0){
				for(var i=0;i<result.length;i++){
					var task = result[i];
					
					var buttonHtml = "";
					if(task.status==0){
						buttonHtml = "<span class='icon'><button class='btn btn-mini' title='处理待办' onclick='dealTask("+task.taskId+")'><i class='icon-search'></i></button></span>&nbsp;&nbsp;";
					}else if(task.status==1){
						buttonHtml = "<span class='icon'><button class='btn btn-mini' title='查看已办' onclick='searchTask("+task.taskId+")'><i class='icon-search'></i></button></span>&nbsp;&nbsp;";
					}
					taskhtml = taskhtml+"<tr><td>"
					+(i+1)+"</td><td>"
					+task.processName+"</td><td>"
					+task.createTime+"</td><td>"
					+task.activityName+"</td><td>"
					+buttonHtml
					+"</td></tr>";
				}
			}
			
			$("#processList tbody").append(taskhtml);
			
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
						selectTaskInProcess(pageOfNum,page);
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
	
	function clearQueryInfo(){
	}
	
	function dealTask(taskId){
		$.post("${ctx}/processManage/dealTask.html?taskId="+taskId,{},function(data){
			if(!data.success){
				return;
			}else{
				var url = data.data;
				window.location.href=url+"&sid=${sid}";
			}
		},"json");
	}
	
	function searchTask(taskId){
		$.post("${ctx}/processManage/searchTask.html?taskId="+taskId,{},function(data){
			if(!data.success){
				return;
			}else{
				var url = data.data;
				window.location.href=url+"&sid=${sid}";
			}
		},"json");
	}
</script>
</head>
<body>
	<div id="breadcrumb">
		<a href="${ctx }/index.html" title="返回首页" class="tip-bottom"><i class="icon-home"></i>首页</a>
		<a href="javascript:void(0);" class="current">我的代办流程</a>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"> <i class="icon-th"></i>
						</span>
						<h5>我的待办列表</h5>
						<span class="icon">
							<button class="btn btn-mini" id="openSearchBtn" onclick="openSearch()"><i class="icon-search" title="搜索"></i></button>
						</span>
					</div>
					<div class="widget-content nopadding" id="searchBox">
						<form class="form-horizontal">
							<div class="control-group">
								<label class="control-label">流程类型</label>
								<div class="controls w50p">
									<select id="processType">
										<option value="" selected="selected">---请选择---</option>
										<c:forEach items="${processTypeList }" var="processKey">
											<option value="${processKey.key }">${processKey.value }</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">处理状态</label>
								<div class="controls w50p">
									<select id="processStatus">
										<option value="0" selected="selected">待办</option>
										<option value="1">已办</option>
									</select>
								</div>
							</div>
							<div class="form-actions">
								<button type="button" class="btn btn-primary" onclick="selectTaskInProcess(pageOfNum,defaultPage);">查询</button>
								<button type="button" class="btn" onclick="clearQueryInfo();">清空</button>
							</div>
						</form>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="processList">
							<thead>
								<tr>
									<th>序号</th>
									<th>流程类型</th>
									<th>创建时间</th>
									<th>任务名称</th>
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