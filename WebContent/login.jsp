<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">
.center{
	margin: auto;
	width: 60%;
	border: 3px solid #0000ff;
	padding: 10px;
}

</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="http://lab.alexcican.com/set_cookies/cookie.js" type="text/javascript" ></script>

</head>
<body>

<h2>login page</h2>

<div class="center">

<form action="member?param=loginAf" method="post" id="frm">

<table border="1">
<tr>
	<th>아이디</th>
	<td>
		<input type="text" id="_id" name="id" size="20"><br>
		<input type="checkbox" id="chk_save_id">save id
	</td>
</tr>
<tr>
	<th>패스워드</th>
	<td>
		<input type="password" name="pwd" id="_pwd" size="20">
	</td>
</tr>
<tr>
	<td colspan="2">
		<input type="button" id="btn" value="로그인">
		<button type="button" onclick="account()">회원가입</button>
	</td>
</tr>
</table>

</form>

</div>

<script type="text/javascript">
$(document).ready(function() {
	
	$("#btn").click(function() {
		// id와 pw는 조건절을 따로 만들어주는 것이 더 좋다.
		if( $("#_id").val() == ""){ // || $("#_pwd").val() == ""){
			alert("아이디 입력해주세요");
			return;
		}else if ($("#_pwd").val() == ""){
			alert("패스워드를 입력해주세요");
			return;
		}
		$("#frm").submit();
		
	});
});

// 회원가입 버튼을 클릭하였을 경우
function account() {
	location.href = "member?param=regi";
}

/*
 	seesion : Java에서 주로 사용 -> Server(Object 자료형)
 	cookie : Java Script -> Client String
 */

let user_id = $.cookie("user_id");
if(user_id != null){ // 저장된 ID가 있다면.
//	alert("쿠키있음");
	$("#_id").val(user_id); // 위에 input 값에 넣어줘라
	$("#chk_save_id").attr("checked", "checked"); // chackbox에 체크를 해준다
}

$("#chk_save_id").click(function() {
	
	if( $("#chk_save_id").is(":checked") ){ // 체크가 되었을 경우
	//	alert("체크됨");
		if( $("#_id").val().trim() == ""){
			alert('id를 입력해 주십시오');
			$("#chk_save_id").prop("checked", false); // 체크를 다시 풀어준다.
		} else{
			// 쿠키를 저장
			$.cookie("user_id", $("#_id").val().trim(), { expirese:7, path:'./'}); /* 기한은 7일 */
		}
	}
	else{ // 체크가 안되었을 경우
		$.removeCookie("user_id", {path:'./'} );
	}
});

</script>

</body>
</html>