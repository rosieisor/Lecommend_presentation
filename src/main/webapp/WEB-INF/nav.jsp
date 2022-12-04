<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">

<style>
.nav_custom {
	font-weight: bold;
	font-size: 18px;
	color: #6f263d;
	height: 45px;
}

.menu_custom {
	padding: 0;
	margin: 0;
	height: auto;
	overflow: hidden;
	margin: auto;
}

.submenu_custom {
	height: 0;
	overflow: hidden;
}

.menu_custom li {
	width: 33.33%;
}

.submenu_custom li {
	width: 48%;
	font-size: 15px;
}

.nav_custom li {
	list-style: none;
	float: left;
	text-align: center;
	line-height: 35px;
	background-color: #fff;
}

.nav_custom a {
	text-decoration: none;
	display: block;
	color: #6f263d;
}

.menu_custom li a:hover {
	background-color: #6f263d;
	color: #fff;
	transition-duration: 1s;
}

.menu_custom>li:hover .submenu_custom {
	height: auto;
	transition-duration: 1s;
}

</style>
</head>
<body>
	<div class="nav_custom">
		<ul class="menu_custom">
			<li><a href="<c:url value='/' />">HOME</a></li>
			<li><a href="<c:url value='/lecture/filter/form' />">RECOMMEND</a>
				<ul class="submenu_custom">
					<li><a href="<c:url value='/lecture/filter/form' />">FILTER</a></li>
					<li><a href="<c:url value='/lecture/searchResult' />">SEARCH RESULT</a></li>
				</ul></li>
			<li><a href="<c:url value='/user/mypage' />">MY PAGE</a></li>
		</ul>
	</div>

</body>
</html>