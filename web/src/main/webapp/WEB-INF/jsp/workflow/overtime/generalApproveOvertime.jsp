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
	function subModule(){
		var param = {};
		param.flowId = $("#flowId").val();
		param.status = $("input[name='status']:checked").val();
		param.auditContent = $("#auditContent").val();
		$.post("${ctx}/overtime/generalManagerApprove.html",param,function(data){
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
		<jsp:include page="approveOvertimeInfo.jsp"></jsp:include>
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"><i class="icon-th"></i>
						</span>
						<h5>审批意见</h5>
					</div>
					<div class="widget-content nopadding">
						<form id="moduleForm" action="" method="post" class="form-horizontal">
							<div class="control-group">
								<label class="control-label">是否同意</label>
								<div class="controls">
									<label><input type="radio" name="status" value="1" checked="checked">同意</label>
									<label><input type="radio" name="status" value="0">驳回</label>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">审核意见</label>
								<div class="controls">
									<textarea rows="10" cols="10" name="auditContent" id="auditContent"></textarea>
								</div>
							</div>
							<div class="form-actions">
								<button type="button" class="btn btn-primary" onclick="subModule()">提交</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>