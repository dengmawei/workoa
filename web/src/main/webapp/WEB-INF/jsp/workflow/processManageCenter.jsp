<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:set value="${pageContext.request.contextPath}" var="ctx" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.min.css">
<script src="${ctx}/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>	
<script src="${ctx }/js/bootstrap-pagination/bootstrap-paginator.js"></script>
<script type="text/javascript" src="${ctx }/js/twitter-bottstrap/js/bootstrap-modal.js"></script>
<title>流程管理中心</title>
<script type="text/javascript">
	var pageOfNum = 10;
	var defaultPage = 1;
	
	$(document).ready(function(){
		$('#datetimepicker-start').datetimepicker({
			weekStart: 1,
			startDate:new Date(),
			autoclose:true,
			startView:'month',
			minView:'hour',
			maxView:'year',
			todayBtn:true,
			todayHighlight:true,
			keyboardNavigation:true,
			language:'en',
			forceParse:true,
			minuteStep:10,
			initialDate:new Date()
	    });
		
		$('#datetimepicker-end').datetimepicker({
			weekStart: 1,
			startDate:new Date(),
			autoclose:true,
			startView:'month',
			minView:'hour',
			maxView:'year',
			todayBtn:true,
			todayHighlight:true,
			keyboardNavigation:true,
			language:'en',
			forceParse:true,
			minuteStep:10,
			initialDate:new Date()
	    });
	});
	
	function selectAllTaskProcess(perPage,currentPage){
		var param = {};
		param.perPage=pageOfNum;
		param.currentPage=currentPage;
		param.startTime=$("#startTime").val();
		param.endTime=$("#endTime").val();
		param.applierId=$("#applierId").val();
		param.processKey = $("#processType option:selected").val();
		
		if(param.processKey==""){
			showTipInfo("请选择流程类型");
			return;
		}
		
		$.post("${ctx}/processManage/selectAllTaskProcess.html",param,function(data){
			$("#processList tbody").html("");
			if(!data.success){
				showTipInfo(data.message);
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
					
					taskhtml = taskhtml+"<tr><td>"
					+(i+1)+"</td><td>"
					+task.processName+"</td><td>"
					+task.realName+"</td><td>"
					+task.createTime+"</td><td>"
					+"<span class='icon'><button class='btn btn-mini' title='查看流程详情' onclick='showProcessInfo("+task.id+",\""+task.processKey+"\")'><i class='icon-search'></i></button></span>&nbsp;&nbsp;"
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
						selectAllTaskProcess(pageOfNum,page);
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
		$("#startTime").val('');
		$("#endTime").val('');
		$("#processType option").first().prop("selected",true);
		$("#applierId").val('');
	}
	
	function showProcessInfo(id,processKey){
		window.location.href="${ctx}/processManage/processDetail.html?flowId="+id+"&processKey="+processKey;
	}
</script>
</head>
<body>
	<div id="breadcrumb">
		<a href="${ctx }/index.html" title="返回首页" class="tip-bottom"><i class="icon-home"></i>首页</a>
		<a href="javascript:void(0);" class="current">流程管理</a>
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
								<label class="control-label">申请人id</label>
								<div class="controls w50p">
									<div class="input-append">
										<input type="text" class="span2" name="applierId" id="applierId" maxlength="10">
										<span class="add-on"><i class="icon-edit"></i></span>
									</div>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">申请时间</label>
								<div class="controls w50p">
									<div class="input-append date" id="datetimepicker-start" data-date-format="yyyy-mm-dd">
									    <input class="span2" type="text" name="startTime" id="startTime" readonly="readonly">
									    <span class="add-on"><i class="icon-th"></i></span>
									</div>
									至&nbsp;&nbsp;
									<div class="input-append date" id="datetimepicker-end" data-date-format="yyyy-mm-dd">
									    <input class="span2" type="text" name="endTime" id="endTime" readonly="readonly">
									    <span class="add-on"><i class="icon-th"></i></span>
									</div>
								</div>
							</div>
							<div class="form-actions">
								<button type="button" class="btn btn-primary" onclick="selectAllTaskProcess(pageOfNum,defaultPage);">查询</button>
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
									<th>申请人</th>
									<th>申请时间</th>
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