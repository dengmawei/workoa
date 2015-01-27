<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:set value="${pageContext.request.contextPath}" var="ctx" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
	<div id="breadcrumb">
		<a href="${ctx }/index.html" title="返回首页" class="tip-bottom"><i class="icon-home"></i>首页</a>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12 center" style="text-align: center;">
				<ul class="quick-actions">
					<li>
						<a href="${ctx }/schedule/index.html"><i class="icon-calendar"></i>工作日历</a>
					</li>
					<li>
						<a href="#"><i class="icon-shopping-bag"></i> Manage Orders</a>
					</li>
					<li>
						<a href="#"> <i class="icon-database"></i> Manage DB</a>
					</li>
					<li>
						<a href="${ctx }/user/index.html"><i class="icon-people"></i> Manage Users</a>
					</li>
					<li>
						<a href="#"> <i class="icon-lock"></i> Security</a>
					</li>
					<li>
						<a href="#"> <i class="icon-piechart"></i> Statistics</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>