<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/include.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:set value="${pageContext.request.contextPath }" var="ctx"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>leftMenu.jsp</title>
	<script src="${ctx}/js/unicorn.js"></script>
	<script type="text/javascript">
		var sid = '${sid}';
		
		$(document).ready(function(){
			showMenu(sid);
		});
		
		function showMenu(sid){
			if(sid!=''){
				$("#li"+sid).addClass("active");
				$("#li"+sid).parent().parent().addClass("active open");
			}
		}
	</script>
</head>
<body>
	<div id="sidebar">
		<ul>
			<li>
				<a href="${ctx }/index.html">
					<i class="icon icon-home"></i>
					<span>首页</span>
				</a>
			</li>
			<c:forEach items="${menuList }" var="superModule">
				<li class="submenu">
					<a href="javascript:void(0)">
						<i class="icon icon-th-list"></i>
						<span>${superModule.superModuleName }</span><span class="label">${superModule.subModuleCount }</span>
					</a>
					<ul>
						<c:forEach items="${superModule.subModuleList }" var="subModule">
							<li onclick="showMenu()" id="li${subModule.id}"><a href="${subModule.linkUrl }?sid=${subModule.id}">${subModule.moduleName }</a></li>
						</c:forEach>
					</ul>
				</li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>