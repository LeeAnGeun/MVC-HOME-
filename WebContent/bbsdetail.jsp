<%@page import="dto.MemberDto"%>
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
MemberDto mem = (MemberDto)request.getSession().getAttribute("login");
%>


<%
BbsDto setDto = (BbsDto)request.getAttribute("bbs");
%>

<form action="">
<div align="center">
	<table border="1">
		<col width="100"><col width="300">
		<tr>
			<td>작성자id</td><td><%=setDto.getId() %></td>
		</tr>
		<tr>
			<td>작성일</td><td><%=setDto.getWdate() %></td>
		</tr>
		<tr>
			<td>제목</td><td><%=setDto.getTitle() %></td>
		</tr>
		<tr>
			<td>조회수</td><td><%=setDto.getReadcount() %></td>
		</tr>
		<tr>
			<td>정보</td><td><%=setDto.getRef() %>-<%=setDto.getStep() %>-<%=setDto.getDepth() %></td>
		</tr>
		<tr>
			<td>내용</td><td><textarea rows="20" cols="60" readonly><%=setDto.getContent() %></textarea></td>
		</tr>
	</table>
	<br>
	<button type="button" onclick="answerbbs('<%=setDto.getSeq() %>')" value="댓글추가">댓글추가</button>
	<% 
	if(setDto.getId().equals(mem.getId())){
		%>
	<button type="button" value="글삭제" id="remove">글삭제</button>
	<button type="button" value="글수정" id="update">글수정</button>
	<%
	}
	%>
	<button type="button" value="글목록" id="conlist">글목록</button>
</div>
</form>

<script type="text/javascript">
$(document).ready(function() {
	
	$("#remove").click(function() {
		location.href = "bbs?param=delete&seq=<%=setDto.getSeq() %>"
	});
	$("#update").click(function() {
		location.href = "bbs?param=update&seq=<%=setDto.getSeq() %>"
	});
	$("#conlist").click(function() {
		location.href = "bbs?param=searchbbs";
	});
});

function answerbbs(seq) {
	location.href = "bbs?param=answer&seq=" + seq;
}
</script>

</body>
</html>










