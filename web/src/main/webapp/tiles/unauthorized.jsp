<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>未授权</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"><i class="icon-info-sign"></i></span>
					 	<h5>未授权</h5>
					</div>
					<div class="widget-content">
						<div class="error_ex">
			              	<h1>访问受限</h1>
			              	<h3>${errorMessage }</h3>
			              	<a class="btn btn-warning btn-big"  href="/index.html">返回首页</a>
			            </div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>