<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<table>
		<tr>
			<th>用户id</th>
			<th>用户名称</th>
		</tr>
		<c:forEach items="${userList }" var="user">
			<tr>
				<td>${user.userId }</td>
				<td>${user.userName }</td>
			</tr>
		</c:forEach>	
	</table>
</body>
</html>