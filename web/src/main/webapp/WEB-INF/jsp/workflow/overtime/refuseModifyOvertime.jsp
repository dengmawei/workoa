<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<c:set value="${pageContext.request.contextPath}" var="ctx" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	
<link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.min.css">
<script src="${ctx}/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>	
<script type="text/javascript" src="${ctx }/js/jquery-validation-1.8.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx }/js/Jquery-Validate-For-Bootstrap-master/jquery.validate.bootstrap.js"></script>
<script type="text/javascript" src="${ctx }/js/twitter-bottstrap/js/bootstrap-modal.js"></script>
<title>模块中心</title>
<script type="text/javascript">
	$(document).ready(function(){
		$("#subDialog").modal({
			keyboard:true,
			show:false	
		});
		
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
		
		$.validator.addMethod("startTime_required",function(value){
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'开始时间不能为空');
		
		$.validator.addMethod("endTime_required",function(value){
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'结束时间不能为空');
		
		$.validator.addMethod("reason_required",function(value){
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'加班理由不能为空');
		
		$("#moduleForm").validate({
			success: function(element) {
				element.text('OK!').addClass('valid').closest('.control-group').removeClass('error').removeClass('success');
			},
			highlight: function(element) {
				$(element).closest('.control-group').removeClass('success').addClass('error');
			},
			rules:{
				startTime:{
					startTime_required:true
				},
				endTime:{
					endTime_required:true
				},
				reason:{
					reason_required:true
				}
			}
		});
	});
	
	function subModule(abandon){
		$("#abandon").val(abandon);
		if($("#moduleForm").validate().form()){
			$('#subDialog').modal('show');
		}
	}
	
	function addOvertime(){
		var param = {};
		param.overtimeStartTime = $("#startTime").val();
		param.overtimeEndTime = $("#endTime").val();
		param.overtimeReason = $("#reason").val();
		param.abandon = $("#abandon").val();
		param.flowId = $("#flowId").val();
		$.post("${ctx}/overtime/refuseModify.html",param,function(data){
			if(data.success){
				window.location.href="${ctx}/processManage/index.html?sid=${sid}";
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
		<a href="javascript:void(0)" class="current">${taskName }</a>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon">
							<i class="icon-align-justify"></i>									
						</span>
						<h5>${taskName }</h5>
					</div>
					<div class="widget-content nopadding">
						<form id="moduleForm" action="" method="post" class="form-horizontal">
							<input type="hidden" id="flowId" value="${view.overtimeFlow.id}">
							<input type="hidden" id="abandon" value="0">
							<div class="control-group">
								<label class="control-label">开始时间</label>
								<div class="controls">
									<div class="input-append date" id="datetimepicker-start" data-date-format="yyyy-mm-dd hh:ii">
									    <input class="span2" type="text" name="startTime" id="startTime" readonly="readonly" value='<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${view.overtimeFlow.startTime }"></fmt:formatDate>'>
									    <span class="add-on"><i class="icon-th"></i></span>
									</div>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">结束时间</label>
								<div class="controls">
									<div class="input-append date" id="datetimepicker-end" data-date-format="yyyy-mm-dd hh:ii">
									    <input class="span2" type="text" name="endTime" id="endTime" readonly="readonly" value='<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${view.overtimeFlow.endTime }"></fmt:formatDate>'>
									    <span class="add-on"><i class="icon-th"></i></span>
									</div>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">加班理由</label>
								<div class="controls">
									<textarea rows="10" cols="20" name="reason" id="reason">${view.overtimeFlow.overtimeReason }</textarea>
								</div>
							</div>
							<div class="form-actions">
								<button type="button" class="btn btn-primary" onclick="subModule(0)">继续审核</button>
								<button type="button" class="btn btn-primary" onclick="subModule(1)">放弃审核</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"><i class="icon-th"></i>
						</span>
						<h5>审批列表</h5>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="overtimeList">
							<thead>
								<tr>
									<td>审核节点</td>
									<td>审核人</td>
									<td>审核时间</td>
									<td>审核内容</td>
									<td>审核状态</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${view.overtimeApproveList }" var="approve">
									<tr>
										<td>${approve.taskName }</td>
										<td>${approve.auditorName }</td>
										<td>${approve.createTime }</td>
										<td>${approve.auditContent }</td>
										<td>${approve.approveProgress }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="subDialog" title="系统提示" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3>添加模块</h3>
		</div>
		<div class="modal-body">
			您确认提交？
		</div>
		<div class="modal-footer">
			<input type="button" class="btn" value="取消" onclick="$('#subDialog').modal('hide');">
    		<input type="button" class="btn btn-primary" onclick="addOvertime()" value="确定">
		</div>
	</div>
</body>
</html>