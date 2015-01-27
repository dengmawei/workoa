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
<script type="text/javascript" src="${ctx }/js/jquery-validation-1.8.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx }/js/Jquery-Validate-For-Bootstrap-master/jquery.validate.bootstrap.js"></script>
<script type="text/javascript" src="${ctx }/js/twitter-bottstrap/js/bootstrap-modal.js"></script>
<title>模块中心</title>
<script type="text/javascript">
	var pageOfNum = 10;
	var defaultPage = 1;
	
	$(document).ready(function(){
		selectOvertimeByPage(pageOfNum,defaultPage);
		
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
	
	
	function selectOvertimeByPage(perPage,currentPage){
		$("#overtimeList tbody").html("");
		var param = {};
		param.perPage=pageOfNum;
		param.currentPage=currentPage;
		param.status = $("#status").val();
		param.startTime = $("#startTime").val();
		param.endTime = $("#endTime").val();
		$.post("${ctx}/overtime/selectOvertimeByPage.html",param,function(data){
			if(!data.success){
				return;
			}
			
			var result = data.data.list;
			var count = data.data.count;
			var currentPage = data.data.currentPage;
			var totalPage = data.data.totalPage;
			
			var overtimehtml = "";
			if(result!=null && result.length>0){
				for(var i=0;i<result.length;i++){
					var overtime = result[i];
					
					var statusVal = "";
					if(overtime.status==0){
						statusVal="草稿";
					}else if(overtime.status==1){
						statusVal="审核中";
					}else if(overtime.status==2){
						statusVal="审批完成";
					}else if(overtime.status==3){
						statusVal="驳回";
					}else if(overtime.status==4){
						statusVal="失败";
					}
					
					var editHtml = overtime.status==0?"<span class='icon'><button class='btn btn-mini' title='编辑' onclick='editOvertime("+overtime.id+")'><i class='icon-edit'></i></button></span>&nbsp;&nbsp;":"";
					var delHtml = overtime.status==0?"<span class='icon'><button class='btn btn-mini' title='删除' onclick='deleteOvertime("+overtime.id+")'><i class='icon-trash'></i></button></span>&nbsp;&nbsp;":"";
							
					overtimehtml += "<tr><td>"
					+(i+1)+"</td><td>"
					+overtime.id+"</td><td>"
					+overtime.realName+"</td><td>"
					+overtime.departName+"</td><td>"
					+overtime.stime+"</td><td>"
					+overtime.etime+"</td><td>"
					+overtime.hours+"</td><td>"
					+statusVal+"</td><td>"
					+overtime.createTime+"</td><td>"
					+editHtml+delHtml
					+"<span class='icon'><button class='btn btn-mini' title='详情查看' onclick='searchOvertime("+overtime.id+")'><i class='icon-search'></i></button></span>&nbsp;&nbsp;"
					+"</td></tr>";
				}
			}
			
			$("#overtimeList tbody").append(overtimehtml);
			
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
						selectOvertimeByPage(pageOfNum,page);
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
		$("#status option").first().prop("selected",true);
		$("#startTime").val('');
		$("#endTime").val('');
	}
	
	function toAddOvertime(){
		window.location.href="${ctx}/overtime/toApplyOvertime.html?sid=${sid}";
	}
	
	function editOvertime(flowId){
		window.location.href="${ctx}/overtime/toEditOvertime/"+flowId+".html?sid=${sid}";
	}
	
	function searchOvertime(flowId){
		window.location.href="${ctx}/overtime/toOvertimeFlowInfo.html?flowId="+flowId+"&sid=${sid}";
	}
	
	function deleteOvertime(flowId){
		if(window.confirm("你确定删除？")){
			$.post("${ctx}/overtime/deleteOvertime.html?flowId="+flowId,{},function(data){
				if(!data.success){
					showTipInfo(data.message);
					return;
				}else{
					showTipInfo("删除成功");				
				}
			},"json");
		}
	}
	
	function refuseModifyOvertime(flowId){
		window.location.href="${ctx}/overtime/toRefuseModify.html?flowId="+flowId+"&sid=${sid}";
	}
</script>
</head>
<body>
	<div id="breadcrumb">
		<a href="${ctx }/index.html" title="返回首页" class="tip-bottom"><i class="icon-home"></i>首页</a>
		<a href="javascript:void(0);" class="current">加班申请</a>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"> <i class="icon-th"></i>
						</span>
						<h5>我的加班列表</h5>
						<span class="icon">
							<button class="btn btn-mini" id="addBtn" onclick="toAddOvertime()"><i class="icon-plus"></i></button>
							<button class="btn btn-mini" id="openSearchBtn" onclick="openSearch()"><i class="icon-search" title="搜索"></i></button>
						</span>
					</div>
					<div class="widget-content nopadding" style="display: none" id="searchBox">
						<form class="form-horizontal">
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
							<div class="control-group">
								<label class="control-label">审批状态</label>
								<div class="controls w50p">
									<select id="status">
										<option value="-1">所有</option>
										<option value="0">草稿</option>
										<option value="1">审核中</option>
										<option value="2">审核成功</option>
										<option value="3">驳回</option>
										<option value="4">失败</option>
									</select>
								</div>
							</div>
							<div class="form-actions">
								<button type="button" class="btn btn-primary" onclick="selectOvertimeByPage(pageOfNum,defaultPage);">查询</button>
								<button type="button" class="btn" onclick="clearQueryInfo();">清空</button>
							</div>
						</form>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="overtimeList">
							<thead>
								<tr>
									<th>序号</th>
									<th>流程id</th>
									<th>申请人</th>
									<th>申请部门</th>
									<th>开始时间</th>
									<th>结束时间</th>
									<th>加班时长（小时）</th>
									<th>状态</th>
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
</body>
</html>