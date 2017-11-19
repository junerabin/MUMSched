<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student Profile</title>
<!-- link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link -->
<!-- link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link -->
</head>
<style>
.container {
	padding-left: 50px;
	padding-top: 20px;
}
</style>
<body>

	<div class="container">
		<sec:authorize access="hasRole('ROLE_ADMIN')">
			<div class="row">
				<input id="createStudent" type="button" value="Create Student"
					class="btn btn-info">
			</div>
		</sec:authorize>

		<br />
		<table class="table table-hover">
			<thead>
				<tr>
					<th><spring:message code="student.firstname" /></th>
					<th><spring:message code="student.lastname" /></th>
					<th><spring:message code="student.dob" /></th>
					<th><spring:message code="student.email" /></th>
					<th><spring:message code="student.entry" /></th>
					<th><spring:message code="student.kind" /></th>
					<th><spring:message code="student.action" /></th>
				</tr>
			</thead>
			<tbody>

				<tr>
					<td>${student.firstName}</td>
					<td>${student.lastName}</td>
					<td><fmt:formatDate pattern="MM/dd/yyyy"
							value="${student.dob}" /></td>
					<td>${student.email}</td>
					<td>${student.entry.name}</td>
					<c:choose>
						<c:when test="${student.kind == 0}">
							<td><spring:message code="student.kind.CPT" /></td>
						</c:when>
						<c:when test="${salary == 1}">
							<td><spring:message code="student.kind.US" /></td>
						</c:when>
						<c:otherwise>
							<td><spring:message code="student.kind.OPT" /></td>
						</c:otherwise>
					</c:choose>
					<td><a href='javascript:editStudent(${student.id})'><span
							class="glyphicon glyphicon-pencil"></span> </a></td>
				</tr>
			</tbody>
		</table>
	</div>
	<br />
	<h3>
		<span class="label label-default"><spring:message
				code="student.transcript" /></span>
	</h3>
	<div class="container">
		<table class="table table-hover">
			<thead>
				<tr>
					<th></th>
					<th><spring:message code="student.class" /></th>
					<th><spring:message code="student.instructor" /></th>
					<th><spring:message code="student.startdate" /></th>
					<th><spring:message code="student.grade" /></th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${studentsections}" var="studentsection"
					varStatus="loop">
					<tr>
						<td>${loop.index + 1}</td>
						<td>${studentsection.section.name}</td>
						<td>${studentsection.section.faculty.firstName}
							${studentsection.section.faculty.lastName}</td>
						<td><fmt:formatDate pattern="MM/dd/yyyy"
								value="${studentsection.section.block.startDate}" /></td>
						<c:choose>
							<c:when test="${studentsection.grade == -1}">
								<td>NA</td>
							</c:when>
							<c:otherwise>
								<td>${studentsection.grade}</td>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<div id="editStudentDialog"></div>

	<script>
		$(document).ready(function() {
			$(function() {
				$("#editStudentDialog").dialog({
					title : "<spring:message code='student.modal.title' />",
					autoOpen : false,
					height : "auto",
					width : "800px",

				});
				$("#createStudent").on("click", function() {
					showStudentDialog("create");
				});
			});
		});

		var showStudentDialog = function(action, userid) {
			var ajaxUrl = "/mumsched/editProfile";
			if (action == 'edit') {
				ajaxUrl = "/mumsched/editprofile/" + userid;
			}
			$.ajax({
				type : "GET",
				url : ajaxUrl,
				success : function(data) {
					var studentDialog = $("#editStudentDialog");
					studentDialog.html(data);
					studentDialog.dialog("open");
				},

				error : function(e) {
					alert('Error: ' + e);
				}
			});
		}

		var editStudent = function(userid) {
			showStudentDialog("edit", userid);
		}
	</script>
</body>
</html>