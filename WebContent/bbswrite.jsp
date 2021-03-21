<%@page import="dto.MemberDto"%>
<%@page import="dao.MemberDao"%>
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
Object ologin = request.getSession().getAttribute("login");
MemberDto mem = null;

if(ologin == null){ // 로그인 세션이 없을경우 
	%>
	<script>	
	alert('로그인을 해 주십시오');
	location.href = "login.jsp"; // 다시 login창으로 돌아간다.
	</script>	
	<%
}
mem = (MemberDto)ologin;
System.out.println(mem.getId());
%>

<!-- 글을 작성할수 있는 form -->
<form action="bbs" id="frm">
<input type="hidden" name="param" value="bbswriteAf">
<div align="center">
	<table border="1" >
		<col width="100"><col width="300">
		<tr>
			<td>id</td><td><input type="text" name="id" value="<%=mem.getId() %>"></td>
		</tr>
		<tr>
			<td>제목</td><td width="50"><input name="title" type="text" size="50"> </td>
		</tr>
		<tr>
			<td>내용</td><td><textarea rows="20" cols="60" name="content" ></textarea> </td>
		</tr>
	</table>
	<br>
	<input type="submit" value="글추가">
</div>

<!-- bbswriteAf.jsp -->
<script type="text/javascript">
$(document).ready(function() {
	
	$("#insert").click(function() {
		$("#frm").submit();
	});
});
</script>
</form>
</body>
</html>