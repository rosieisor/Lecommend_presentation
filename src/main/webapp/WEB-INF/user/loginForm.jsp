<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">

<title>Login Form</title>

<link rel="stylesheet" type="text/css" href="../../css/loginForm.css">
<script>
	function login() {
		if (form.userId.value == "") {
			alert("사용자 ID를 입력하십시오.");
			form.userId.focus();
			return false;
		}
		if (form.password.value == "") {
			alert("비밀번호를 입력하십시오.");
			form.password.focus();
			return false;
		}
		form.submit();
	}
</script>
</head>

<jsp:include page="../header.jsp"></jsp:include>


<body>
	<div class="login-custom">
		<main class="form-signin w-100 m-auto">
			<form name="form" method="POST"
				action="<c:url value='/user/login' />">
				<h1 class="h2 mb-3 color-point">LEcommend</h1>

				<div class="form-floating">
					<input type="text" class="form-control" id="floatingInput"
						placeholder="ID"> <label for="floatingInput">ID</label>
				</div>
				<div class="form-floating">
					<input type="password" class="form-control" id="floatingPassword"
						placeholder="Password"> <label for="floatingPassword">Password</label>
				</div>
				<button class="w-100 btn btn-lg btn-primary" onClick="login()">Log
					in</button>
				
				<div class="center_loc">
					<label>LEcommend 처음이신가요?</label> <br> 
					<a href="<c:url value='/user/join' />" class="signup">회원가입</a>
				</div>
			</form>
		</main>


	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
		crossorigin="anonymous"></script>

</body>
</html>

