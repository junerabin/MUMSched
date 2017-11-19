<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- <link href="css/course.css" rel="stylesheet" type="text/css"> -->

<form:form id="createFacultyForm" method="POST" modelAttribute="faculty"
	action="/mumsched/newupdatefaculty">
	<form:input type="hidden" path="id" id="id" />
	<div class="form-group">
		<label for="user">User: </label>
		
		<form:select path="user" class="form-control">
			<c:forEach items="${availableUsers}" var="useritem">
				<form:option value="${useritem.username}">${useritem.username}</form:option>
			</c:forEach>
		</form:select>
		<form:errors path="user" cssClass="error" />
	</div>

	<div class="form-group">
		<label for="firstName">First Name: </label>
		<form:input path="firstName" id="firstName" class="form-control" />
		<form:errors path="firstName" cssClass="error" />

	</div>
	<div class="form-group">
		<label for="lastName">Last Name: </label>
		<form:input path="lastName" id="lastName" class="form-control" />
		<form:errors path="lastName" cssClass="error" />
	</div>
	<div class="form-group">
		<label for="dob">DOB: </label>
		<form:input type="date" path="dob" id="dob" class="form-control" />
		<form:errors path="dob" cssClass="error" />
	</div>
	<div class="form-group">
		<label for="email">Email: </label>
		<form:input path="email" id="email" class="form-control" />
		<form:errors path="email" cssClass="error" />
	</div>

	<div class="form-group">
		<label for="targetBlock">Pref. Block: </label>
		<form:select path="targetBlock" id="targetBlock" class="form-control"
			multiple="multiple">

			<c:set var="blockNames">A,B,C,D,E</c:set>
			<c:forTokens items="${blockNames}" delims="," var="blockItem">
				<c:set var="checkB"
					value="${fn:indexOf(faculty.targetBlock, blockItem)}" />

				<c:if test="${checkB gt -1 }">
					<form:option value="${blockItem}" selected="true">${blockItem}</form:option>
				</c:if>
				<c:if test="${checkB eq -1 }">
					<form:option value="${blockItem}">${blockItem}</form:option>
				</c:if>
			</c:forTokens>

		</form:select>
		<form:errors path="targetBlock" cssClass="error" />
	</div>

	<div class="form-group">
		<label for="courses">Teaching Courses: </label>
		<form:select path="courses" class="form-control">
			<c:forEach items="${availableCourses}" var="course">
				<c:forEach items="${faculty.courses}" var="facultycourse">
					<c:if test="${course.id == facultycourse.id}">
						<form:option value="${course.id }" selected="true">${course.courseName }</form:option>
									<<${checkC = 1 }>>
								</c:if>
				</c:forEach>
				<c:if test="${checkC != 1 }">
					<form:option value="${course.id }">${course.courseName }</form:option>
				</c:if>	
						<<${checkC = 0 }>>
					</c:forEach>
		</form:select>
		<form:errors path="courses" cssClass="error" />
	</div>

	<div class="form-group">
		<input type="submit" value="Register" class="btn btn-info" />
	</div>

</form:form>

<div id="invalidInputDialog">
	<spring:message code="faculty.modal.invalidinput.content" />
</div>
<script>
	$('#createFacultyForm').submit(function(event) {
		event.preventDefault(); // Prevent the form from submitting via the browser
		var form = $(this);
		var fname = $('#firstName').val();
		var lname = $('#lastName').val();
		//var dob = $('#dob').val();
		var email = $('#email').val();

		if ((fname == '') || (lname == '') || (email == '')) {
			$("#invalidInputDialog").dialog("open");
			return;
		}

		$.ajax({
			type : form.attr('method'),
			url : form.attr('action'),
			data : form.serialize(),
			success : function(data) {
				$("#facultyDialog").dialog("close");
				loadFaculties();
			},
			error : function(e) {
				alert(e.responseJSON["message"]);
			}
		});
	});

	$("#invalidInputDialog").dialog({
		title : '<spring:message code="faculty.modal.invalidinput.title" />',
		autoOpen : false,
		height : "auto",
		width : "auto",
		modal : true,
		buttons : {
			Ok : function() {
				$(this).dialog("close");
			}
		}
	});
</script>



