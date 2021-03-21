<%@page import="dto.BbsDto"%>
<%@page import="dao.BbsDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

</head>
<body>

<%
request.setCharacterEncoding("utf-8");
BbsDto bbs = (BbsDto)request.getAttribute("bbs");
%>

<form action="bbs" method="post">
<input type="hidden" name="param" value="updateAf">
<input type="hidden" name="seq" value="<%=bbs.getSeq() %>">
<div align="center">
	<table border="1">
		<col width="100"><col width="300">
		<tr>
			<td>작성자id</td><td><input type="text" name="id" value="<%=bbs.getId() %>" readonly></td>
		</tr>
		<tr>
			<td>작성일</td><td ><input type="text" name="date" value="<%=bbs.getWdate() %>"></td>
		</tr>
		<tr>
			<td>제목</td><td ><input type="text" name="title" value="<%=bbs.getTitle() %>"></td>
		</tr>
		<tr>
			<td>조회수</td><td><input type="text" name="count" value="0"></td>
		</tr>
		<tr>
			<td>정보</td><td>없음</td>
		</tr>
		<tr>
			<td>내용</td><td><textarea name="content" rows="20" cols="60"><%=bbs.getContent() %></textarea></td>
		</tr>
	</table>
	<input type="submit" value="수정완료"></button>
	<button type="button" id="btn2">취소</button>
</div>
</form>

<script type="text/javascript">
$(document).ready(function() {
	$("#btn2").click(function() {
		location.href= "bbs?param=searchbbs";
	});
});
</script>

</body>
</html>












