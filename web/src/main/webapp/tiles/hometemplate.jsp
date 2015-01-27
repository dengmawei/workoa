<%@ include file="/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>home.jsp</title>
	<link rel="stylesheet" href="${ctx}/css/main.css" />
	<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.min.css" />
	<link rel="stylesheet" href="${ctx }/css/unicorn.main.css" />
	<link rel="stylesheet" href="${ctx }/css/unicorn.grey.css" class="skin-color" />
	<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.gritter.css" />

	<script src="${ctx}/js/excanvas.min.js"></script>
    <script src="${ctx}/js/jquery-1.9.1.js"></script>
	<script src="${ctx}/js/jquery.ui.custom.js"></script>
	<script src="${ctx}/js/bootstrap.min.js"></script>
	<script src="${ctx}/js/jquery.peity.min.js"></script>
	<script src="${ctx}/js/jquery.gritter.min.js"></script>
	<script src="${ctx}/js/workoa.js"></script>
</head>
<body>
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="leftMenu" />
	<div id="style-switcher">
		<i class="icon-arrow-left icon-white"></i>
		<span>Style:</span>
		<a href="#grey" style="background-color: #555555;border-color: #aaaaaa;"></a>
		<a href="#blue" style="background-color: #2D2F57;"></a>
		<a href="#red" style="background-color: #673232;"></a>
	</div>
	<div id="content">
		<div id="content-header">
			<h1>Charts &amp; graphs</h1>
			<div class="btn-group">
				<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
				<shiro:hasPermission name="employee:view"><a class="btn btn-large tip-bottom" title="用户管理"><i class="icon-user"></i></a></shiro:hasPermission>
				<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
				<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
			</div>
		</div>
		<tiles:insertAttribute name="body" />
		<div class="container-fluid">
			<div class="row-fluid">
				<div id="footer" class="span12">
					2012 &copy; Unicorn Admin. Brought to you by <a
						href="https://wrapbootstrap.com/user/diablo9983">diablo9983</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>