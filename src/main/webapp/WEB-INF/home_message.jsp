<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<style>
.home-msg {
	font-weight: bold;
	font-size: 28px;
	margin: 150px;
	color: #c0c0c0;
	text-align: center;
}

.btn_start {
	font-weight: bold;
	border-radius: 1em;
	border: none;
	background-color: #6f263d;
	color: #fff;
	font-size: 25px;
	width: 400px;
	padding: 0px 60px 0px 60px;
	text-decoration: none;
	
	
}

.btn_start:hover {
	border: none;
	background-color: #F5F5F5;
	color: #6f263d;
	transition-duration: 1s;
}

.color-point {
	color: #6f263d;
}


</style>

</head>
<body>

	<div class="home-msg">

		<p>
			안녕하세요. <br>강의 추천 사이트 <span class="color-point">"LEcommend"</span>입니다.
			<br> <br>아래 버튼을 눌러 시작하십시오.
		</p>

		<a href="<c:url value='/lecture/filter/form' />" class="btn_start">START</a>
	</div>
</body>
</html>