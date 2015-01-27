<jsp:include page="include.jsp"/>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:set value="${pageContext.request.contextPath}" var="ctx" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" href="${ctx}/js/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.min.css">
	<script src="${ctx}/js/jquery-1.9.1.js"></script>
	<script src="${ctx}/js/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${ctx}/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#datetimepicker').datetimepicker({
				weekStart: 1,
				startDate:new Date(),
				daysOfWeekDisabled:[0,6],
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
	</script>
</head>
<body>
	<div class="container">
    		<div class="input-append date" id="datetimepicker" data-date-format="yyyy-mm-dd HH:ii:ss">
			    <input class="span2" size="30" type="text">
			    <span class="add-on"><i class="icon-remove"></i></span>
			    <span class="add-on"><i class="icon-th"></i></span>
			</div>
</div>
</body>
</html>