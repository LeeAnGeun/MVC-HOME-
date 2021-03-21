<%@page import="dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
Boolean b = (Boolean)request.getAttribute("b");

if( b == true){
%>
	<script type="text/javascript">
	alert("로그인에 성공하셨습니다");
	location.href = "member?param=bblist";
	</script>
<%
}else{
%>
	<script type="text/javascript">
	alert("로그인에 실패하셨습니다. id와 pw를 다시 확인해주세요.");
	location.href = "member?param=login";
	</script>
<%	
}
%>

</body>
</html>