<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:set value="${pageContext.request.contextPath}" var="ctx" />
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
		
		selectScheduleByPage(pageOfNum,defaultPage);
		
		$("#showScheduleDialog").modal({
			keyboard:true,
			show:false
		});
	});
	
	
	function selectScheduleByPage(perPage,currentPage){
		$("#scheduleList tbody").html("");
		var param = {};
		param.perPage=pageOfNum;
		param.currentPage=currentPage;
		param.startTime = $("#scheduleStartTime").val();
		param.endTime = $("#scheduleEndTime").val();
		param.status = $("#searchBox input[name='status']:checked").val();
		param.personal = $("#searchBox input[name='personal']:checked").val();
		$.post("${ctx}/schedule/selectScheduleByPage.html",param,function(data){
			if(!data.success){
				return;
			}
			
			var result = data.data.list;
			var count = data.data.count;
			var currentPage = data.data.currentPage;
			var totalPage = data.data.totalPage;
			
			var scheduleHtml = "";
			if(result!=null && result.length>0){
				for(var i=0;i<result.length;i++){
					var schedule = result[i];
					var delIsShow = "";
					if(schedule.userId=="${userId}" && schedule.personal==1){
						delIsShow = "<span class='icon'><button class='btn btn-mini' title='删除' onclick='deleteSchedule("+schedule.id+")'><i class='icon-remove'></i></button></span>&nbsp;&nbsp;";
					}
					
					var checkShow = "";
					if(schedule.personal==0){
						checkShow = "<span class='icon'><button class='btn btn-mini' title='通过' onclick='checkSingle(1,"+schedule.id+")'><i class='icon-remove'></i></button></span>&nbsp;&nbsp;"
						+"<span class='icon'><button class='btn btn-mini' title='驳回' onclick='checkSingle(2,"+schedule.id+")'><i class='icon-remove'></i></button></span>&nbsp;&nbsp;";
					}
					var statusVal = "";
					if(schedule.status==0){
						statusVal = "待审核";
					}else if(schedule.status==1){
						statusVal = "审核通过";
					}else if(schedule.status==2){
						statusVal = "驳回";
					}
					var flagVal = schedule.flag==1?"重要":"普通";
					scheduleHtml += "<tr><td style='text-align:center'>"
					+"<input type='checkbox' name='checkall' value='"+schedule.id+"'></td><td>"
					+(i+1)+"</td><td>"
					+schedule.title+"</td><td>"
					+schedule.startTime+"</td><td>"
					+schedule.endTime+"</td><td>"
					+schedule.createTime+"</td><td>"
					+statusVal+"</td><td>"
					+flagVal+"</td><td>"
					+delIsShow+checkShow
					+"<span class='icon'><button class='btn btn-mini' title='详情查看' onclick='selectScheduleById("+schedule.id+")'><i class='icon-search'></i></button></span></td></tr>";
				}
			}
			
			$("#scheduleList tbody").append(scheduleHtml);
			
			if(count>0){
				var options = {
					size:"normal",
					alignment:"left",
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
						selectScheduleByPage(pageOfNum,page);
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
	
	function deleteSchedule(id){
		if(window.confirm("您确定删除？")){
			$.post("${ctx}/schedule/deleteSchedule.html",{id:id},function(data){
				if(data.success){
					showTipInfo("删除成功");
					clearQueryInfo();
					selectScheduleByPage(pageOfNum,defaultPage);
				}else{
					showTipInfo(data.message);
				}
			},'json');
		}
	}
	
	function clearQueryInfo(){
		$("#scheduleStartTime").val('');
		$("#scheduleEndTime").val('');
		$("#searchBox input[name='status']").first().prop("checked",true);
		$("#searchBox input[name='personal']").first().prop("checked",true);
	}
	
	function selectScheduleById(id){
		$.post("${ctx}/schedule/selectScheduleById/"+id+".html",{},function(data){
			if(data.success){
				clearScheduleInfoDialog();
				
				var shcedule = data.data;
				$("#scheduleTitleInfo").val(shcedule.title);
				$("#scheduleStartTimeInfo").val(shcedule.startTime);
				$("#scheduleEndTimeInfo").val(shcedule.endTime);
				$("#scheduleContentInfo").val(shcedule.content);
				if(shcedule.personal==1){
					$("#isPrivateInfo").prop("checked",true);
				}else{
					$("#isPrivateInfo").prop("checked",false);
				}
				if(shcedule.flag==1){
					$("#flagInfo").prop("checked",true);
				}else{
					$("#flagInfo").prop("checked",false);
				}
				
				$("#showScheduleDialog").modal("show");
			}
		},"json");
	}
	
	function clearScheduleInfoDialog(){
		$("#scheduleTitleInfo").val("");
		$("#scheduleStartTimeInfo").val("");
		$("#scheduleEndTimeInfo").val("");
		$("#scheduleContentInfo").val("");
		$("#isPrivateInfo").prop("checked",true);
		$("#flagInfo").prop("checked",false);
	}
	
	function selectAll(){
		if($("#selectAll").is(":checked")){
			$("#scheduleList tbody").find("input[name='checkall']").prop("checked",true);
		}else{
			$("#scheduleList tbody").find("input[name='checkall']").prop("checked",false);
		}
	}
	
	function checkSingle(status,scheduleId){
		var arr = [];
		arr.push(scheduleId);
		if(scheduleId<=0){
			return;
		}
		
		check(arr,status);
	}
	
	function checkBatch(status){
		var scheduleList = $("#scheduleList tbody").find("input[name='checkall']:checked");
		var arr = [];
		if(scheduleList!=null && scheduleList.length>0){
			$(scheduleList).each(function(){
				arr.push($(this).val());
			});
		}else{
			showTipInfo("请先勾选要审核的日程");
			return;
		}
		
		check(arr,status);
	}
	
	function check(ids,status){
		$.post("${ctx}/schedule/checkSchedule.html",{ids:ids,status:status},function(data){
			if(data.success){
				showTipInfo("审核完成");
			}else{
				showTipInfo(data.message);
			}
		},"json");
	}
</script>
</head>
<body>
	<div id="breadcrumb">
		<a href="${ctx }/index.html" title="返回首页" class="tip-bottom"><i class="icon-home"></i>首页</a>
		<a href="javascript:void(0);" class="current">工作日程审核</a>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"> <i class="icon-th"></i>
						</span>
						<h5>日程列表</h5>
						<span class="icon">
							<button class="btn btn-mini" id="openSearchBtn" onclick="openSearch()" title="搜索"><i class="icon-search"></i></button>&nbsp;
							<button class="btn btn-mini" id="checkBtn" onclick="checkBatch(1)" title="审核通过"><i class="icon-check"></i></button>&nbsp;
							<button class="btn btn-mini" id="checkBtn" onclick="checkBatch(2)" title="驳回"><i class="icon-share"></i></button>&nbsp;
						</span>
					</div>
					<div class="widget-content nopadding" style="display: none" id="searchBox">
						<form class="form-horizontal">
							<div class="control-group">
								<label class="control-label">创建开始时间</label>
								<div class="controls w50p">
									<div class="input-append date" id="datetimepicker-start" data-date-format="yyyy-mm-dd">
									    <input class="span2" type="text" name="startTime" id="scheduleStartTime" readonly="readonly">
									    <span class="add-on"><i class="icon-th"></i></span>
									</div>
									至&nbsp;&nbsp;
									<div class="input-append date" id="datetimepicker-end" data-date-format="yyyy-mm-dd">
									    <input class="span2" type="text" name="endTime" id="scheduleEndTime" readonly="readonly">
									    <span class="add-on"><i class="icon-th"></i></span>
									</div>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">状态</label>
								<div class="controls w50p">
									<c:if test="${isPermit==true }">
										<input type="radio" name="status" value="-1" checked="checked">所有
										<input type="radio" name="status" value="0">待审核
										<input type="radio" name="status" value="1">审核通过
										<input type="radio" name="status" value="2">驳回
									</c:if>
									<c:if test="${isPermit==false }">
										<input type="radio" name="status" value="-1" checked="checked" disabled="disabled">所有
										<input type="radio" name="status" value="0" disabled="disabled">待审核
										<input type="radio" name="status" value="1" disabled="disabled">审核通过
										<input type="radio" name="status" value="2" disabled="disabled">驳回
									</c:if>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">属性</label>
								<div class="controls w50p">
									<input type="radio" name="personal" value="1" checked="checked">个人
									<c:if test="${isPermit==true }">
										<input type="radio" name="personal" value="0">非个人
									</c:if>
									<c:if test="${isPermit==false }">
										<input type="radio" name="personal" value="0" disabled="disabled">非个人
									</c:if>
								</div>
							</div>
							<div class="form-actions">
								<button type="button" class="btn btn-primary" onclick="selectScheduleByPage(pageOfNum,defaultPage);">查询</button>
								<button type="button" class="btn" onclick="clearQueryInfo();">清空</button>
							</div>
						</form>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="scheduleList">
							<thead>
								<tr>
									<th><input type="checkbox" id="selectAll" onclick="selectAll()"></th>
									<th>序号</th>
									<th>日程标题</th>
									<th>开始时间</th>
									<th>结束时间</th>
									<th>创建时间</th>
									<th>状态</th>
									<th>是否重要</th>
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
	
	<div id="showScheduleDialog" title="日程详情" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3>日程详情</h3>
		</div>
		<div class="modal-body">
			<form method="post" class="form-horizontal" id="scheduleForm">
				<div class="control-group">
					<label class="control-label">日程标题</label>
					<div class="controls">
						<div class="input-append">
							<input type="text" id="scheduleTitleInfo" maxlength="50" readonly="readonly"/>
							<span class="add-on"><i class="icon-pencil"></i></span>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">开始时间</label>
					<div class="controls">
						<div class="input-append date">
						    <input class="span2" type="text" id="scheduleStartTimeInfo" readonly="readonly">
						    <span class="add-on"><i class="icon-th"></i></span>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">结束时间</label>
					<div class="controls">
						<div class="input-append date">
						    <input class="span2" type="text" id="scheduleEndTimeInfo" readonly="readonly">
						    <span class="add-on"><i class="icon-th"></i></span>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">个人使用</label>
					<div class="controls">
						<div class="input-append">
						    <input type="checkbox" id="isPrivateInfo" disabled="disabled">
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">重要</label>
					<div class="controls">
						<div class="input-append">
						    <input type="checkbox" id="flagInfo" disabled="disabled">
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">详细内容</label>
					<div class="controls">
						<div class="input-prepend">
							<textarea rows="5" cols="100" id="scheduleContentInfo" readonly="readonly"></textarea>
						</div>
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<input type="button" class="btn" id="closeBtn" onclick="$('#showScheduleDialog').modal('hide')" value="关闭">
		</div>
	</div>
</body>
</html>