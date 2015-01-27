<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:set value="${pageContext.request.contextPath}" var="ctx" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${ctx }/js/twitter-bottstrap/js/bootstrap-modal.js"></script>
<title>模块中心</title>
<script type="text/javascript">
	var url = "${detailUrl}";
	$(document).ready(function(){
		loadProcessDetail();
	});
	
	function loadProcessDetail(){
		$.post(url,{},function(data){
			$("#container").append(data);
		},"html");
	}
</script>
</head>
<body>
	<div id="breadcrumb">
		<a href="${ctx }/index.html" title="返回首页" class="tip-bottom"><i class="icon-home"></i>首页</a>
		<a href="${ctx }/processManage/manageCenter.html?sid=${sid}" class="tip-bottom" title="流程管理中心">流程管理中心</a>
		<a href="javascript:void(0);" class="current" title="流程申请详情">流程申请详情</a>
	</div>
	<div class="container-fluid" id="container">
		
	</div>
</body>
</html>