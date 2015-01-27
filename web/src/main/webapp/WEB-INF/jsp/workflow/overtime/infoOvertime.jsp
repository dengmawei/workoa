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
	function subModule(agree){
		var param = {};
		param.flowId = $("#flowId").val();
		param.agree = agree;
		$.post("${ctx}/overtime/editOvertimeDraft.html",param,function(data){
			if(data.success){
				window.location.href="${ctx}/overtime/index.html?sid=${sid}";
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
		<a href="${ctx }/overtime/index.html?sid=${sid}" class="tip-bottom" title="加班申请列表">加班申请列表</a>
		<a href="javascript:void(0)" class="current">加班申请详情</a>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon">
							<i class="icon-align-justify"></i>									
						</span>
						<h5>加班申请详情</h5>
					</div>
					<div class="widget-content nopadding">
						<form id="moduleForm" action="" method="post" class="form-horizontal">
							<input type="hidden" id="flowId" value="${view.overtimeFlow.id }">
							<div class="control-group">
								<label class="control-label">申请人</label>
								<div class="controls">
									<div class="input-append">
										<input type="text" readonly="readonly" value="${view.overtimeFlow.realName }" class="span2">
									    <span class="add-on"><i class="icon-user"></i></span>
									</div>
								</div>
							</div>
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
									<textarea rows="10" cols="20" name="reason" id="reason" readonly="readonly">${view.overtimeFlow.overtimeReason }</textarea>
								</div>
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
							<c:if test="${empty view.overtimeApproveList }">
								<tr>
									<td>暂无数据！</td>
								</tr>
							</c:if>
							<c:if test="${not empty view.overtimeApproveList }">
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
							</c:if>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>