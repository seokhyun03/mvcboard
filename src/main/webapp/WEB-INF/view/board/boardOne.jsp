<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boardOne</title>
</head>
<body>
	<h1>게시글 상세정보</h1>
	
	<div>
		<a href="/board/boardList">이전</a>
	</div>
	
	<form action="/board/modifyBoard">
		<table border="1">
			<tr>
				<th>memberId</th>
				<td>
					<input type="hidden" name="memberId" value="${board.memberId}">
					${board.memberId}
				</td>
			</tr>
			<tr>
				<th>localName</th>
				<td>
					<input type="hidden" name="localName" value="${board.localName}">
					${board.localName}
				</td>
			</tr>
			<tr>
				<th>boardTitle</th>
				<td>
					<input type="hidden" name="boardNo" value="${board.boardNo}">
					<input type="hidden" name="boardTitle" value="${board.boardTitle}">
					${board.boardTitle}
				</td>
			</tr>
			<tr>
				<th>boardContent</th>
				<td>
					<input type="hidden" name="boardContent" value="${board.boardContent}">
					${board.boardContent}
				</td>
			</tr>
			<tr>
				<th>boardfiles</th>
				<td>
					<c:forEach var="f" items="${boardfiles}">
						<a href="/upload/${f.saveFilename}">${f.originFilename}</a><br>
					</c:forEach>
				</td>
			</tr>
		</table>
		<button type="submit">수정</button>
		<button type="submit" formaction="/board/removeBoard" formmethod="post">삭제</button>
	</form>
</body>
</html>