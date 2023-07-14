<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>modifyBoard</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script>
	$(document).ready(function(){
		$('#addfile').click(function(){
			if($('.boardfiles').last().val() == ''){
				alert('빈 파일업로드 태그가 있습니다');
			} else{
				$('#files').append('<input type="file" name="multipartFile" class="boardfiles">') 
			}
		});
		$('#delfile').click(function(){
			if($('.boardfiles').last().val() != ''){
				alert('빈 파일업로드 태그가 없습니다');
			} else {
				$('.boardfiles').last().remove();
			}
		});
	});
</script>
</head>
<body>
	<h1>게시글 수정</h1>
	<div>
		<a href="/board/boardOne?boardNo=${board.boardNo}">이전</a>
	</div>
	<form action="/board/modifyBoard" method="post">
		<table border="1">
			<tr>
				<th>memberId</th>
				<td><input type="hidden" name="memberId" value="${board.memberId}">${board.memberId}</td>
			</tr>
			<tr>
				<th>localName</th>
				<td>
					<select name="localName">
						<c:forEach var="l" items="${localNameList}">
							<c:if test="${l.localName == board.memberId}">
								<option value="${l.localName}" selected="selected">${l.localName}</option>
							</c:if>
							<c:if test="${l.localName != board.memberId}">
								<option value="${l.localName}">${l.localName}</option>
							</c:if>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>boardTitle</th>
				<td>
					<input type="hidden" name="boardNo" value="${board.boardNo}">
					<input type="text" name="boardTitle" value="${board.boardTitle}">
				</td>
			</tr>
			<tr>
				<th>boardContent</th>
				<td><textarea cols="50" rows="3" name="boardContent">${board.boardContent}</textarea></td>
			</tr>
			<tr>
				<th>boardfiles</th>
				<td>
				<div>
					현재 업로드된 파일
					<c:forEach var="f" items="${fileList}">
							${f.originFilename}&nbsp;<button type="submit" formaction="/board/modifyBoard/deleteBoardfileOne">삭제</button><br>
					</c:forEach>
				</div>
					<button type="button" id="addfile">추가</button>
					<button type="button" id="delfile">삭제</button>
					<div id="files">
						<input type="file" name="multipartFile" class="boardfiles">
					</div>
				</td>
			</tr>
		</table>
		<button type="submit">수정</button>
	</form>
</body>
</html>