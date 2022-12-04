<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%
String[] locList = { "대학원", "동인관", "인문관", "약학관", "숭인관", "예지관", "백주년" };
request.setAttribute("locList", locList);

String[] lecTypeList = { "토론", "실기", "이론", "독서", "실습", "발표" };
request.setAttribute("lecTypeList", lecTypeList);

String[] interestList = { "경영", "경제", "교육", "기술", "데이터", "동양", "코딩" };
request.setAttribute("interestList", interestList);

String[] lecTimeList = { "1교시", "2교시", "3교시", "4교시", "5교시", "6교시" };
request.setAttribute("lecTimeList", lecTimeList);

String[] examTypeList = { "대면", "비대면", "과제대체" };
request.setAttribute("examTypeList", examTypeList);

String[] weekList = { "월요일", "화요일", "수요일", "목요일", "금요일", "토요일" };
request.setAttribute("weekList", weekList);

String[] onOffList = { "ON", "OFF", "ON+OFF", "ALL" };
request.setAttribute("onOffList", onOffList);
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>filterForm</title>

<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">

<link rel="stylesheet" type="text/css" href="../../css/filterForm.css">

</head>
<body>

	<jsp:include page="../header.jsp"></jsp:include>
	<jsp:include page="../nav.jsp"></jsp:include>

	<form name="filterForm" method="POST" action="<c:url value='/lecture/searchResult'/>">
		<table class="keywordTable">
			<tr>
				<td class="keywordCatagory">🏫 강의실</td>
				<td>
					<div class="btn-group-toggle keywords" data-toggle="buttons">
						<c:forEach var="loc" items="${locList}">
							<label class="btn btn-primary keywordBtn"> <input
								type="radio" name="loc" 
								value="${ loc }" required> ${loc}
								<!-- value="${ fn:substring(loc,0,1) }"> ${loc} -->
							</label>
						</c:forEach>
					</div>
				</td>
			</tr>
			<tr>
				<td class="keywordCatagory">📓 강의 형식</td>
				<td>
					<div class="btn-group-toggle keywords" data-toggle="buttons">
						<c:forEach var="lecType" items="${lecTypeList}">
							<label class="btn btn-primary keywordBtn"> <input
								type="radio" name="lecType" value="${ lecType }" required>
								${lecType}
							</label>
						</c:forEach>
					</div>
				</td>
			</tr>
			<tr>
				<td class="keywordCatagory">🔔 시간대</td>
				<td>
					<div class="btn-group-toggle keywords" data-toggle="buttons">
						<c:forEach var="lecTime" items="${lecTimeList}">
							<label class="btn btn-primary keywordBtn"> <input
								type="radio" name="lecTime"
								value="${ lecTime }" required> ${lecTime}
								<!-- value="${ fn:substring(lecTime,0,1) }"> ${lecTime} -->
							</label>
						</c:forEach>
					</div>
				</td>
			</tr>
			<tr>
				<td class="keywordCatagory">🔍 관심사</td>
				<td>
					<div class="btn-group-toggle keywords" data-toggle="buttons">
						<c:forEach var="interest" items="${interestList}">
							<label class="btn btn-primary keywordBtn"> <input
								type="radio" name="interest" value="${ interest }" required>
								${interest}
							</label>
						</c:forEach>
					</div>
				</td>
			</tr>
			<tr>
				<td class="keywordCatagory">📖 시험 형식</td>
				<td>
					<div class="btn-group-toggle keywords" data-toggle="buttons">
						<c:forEach var="examType" items="${examTypeList}">
							<label class="btn btn-primary keywordBtn"> <input
								type="radio" name="examType" value="${ examType }" required>
								${examType}
							</label>
						</c:forEach>
					</div>
				</td>
			</tr>
			<tr>
				<td class="keywordCatagory">📅 요일</td>
				<td>
					<div class="btn-group-toggle keywords" data-toggle="buttons">
						<c:forEach var="week" items="${weekList}">
							<label class="btn btn-primary keywordBtn"> <input
								type="radio" name="week" value=${ fn:substring(week,0,1) } required>
								${week}
							</label>
						</c:forEach>
					</div>
				</td>
			</tr>

			<tr>
				<td class="keywordCatagory">🏢 On/OFF</td>
				<td>
					<div class="btn-group-toggle keywords" data-toggle="buttons">
						<c:forEach var="onOff" items="${onOffList}">
							<label class="btn btn-primary keywordBtn"> <input
								type="radio" name="onOff" value="${ onOff }" required> ${onOff}
							</label>
						</c:forEach>
					</div>
				</td>
			</tr>
			<tr>
				<td class="keywordCatagory">👩‍ 수강인원</td>
				<td><input type="number" class="occupancyBtn" name="occupancy"
					min="1" max="200" required></td>
			</tr>
			<tr>
				<td class="keywordCatagory">🔖‍ 과거 수강</td>
				<td>
					<div class="btn-group-toggle keywords" data-toggle="buttons">
						<label class="btn btn-primary keywordBtn"> <input
							type="radio" name="status" value=0 required> 포함 O
						</label> <label class="btn btn-primary keywordBtn"> <input
							type="radio" name="status" value=1 required> 포함 X
						</label>
					</div>
				</td>
			</tr>
			<tr>
				<td class="keywordCatagory">🔡‍ 학점</td>
				<td>
					<div class="btn-group-toggle keywords" data-toggle="buttons">
						<label class="btn btn-primary keywordBtn"> <input
							type="radio" name="credit" value=2 required> 2학점
						</label> <label class="btn btn-primary keywordBtn"> <input
							type="radio" name="credit" value=3 required> 3학점
						</label>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan='2'>&nbsp;</td>
			</tr>
			<tr>
				<td colspan='2'><div class="keywordFinishBtn">
						<input type="submit" class="btn btn-primary keywordSubmitBtn"
							value="SEARCH" /> <input type="reset"
							class="btn btn-primary keywordSubmitBtn" value="RESET" />
					</div></td>

			</tr>
			

		</table>
	</form>
</body>
</html>