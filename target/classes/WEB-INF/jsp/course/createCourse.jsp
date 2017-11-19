<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<form:form id="createCourseForm" method="POST" modelAttribute="course"
	action="/mumsched/createcourse" commandName="course">
	<form:input type="hidden" path="id" id="id"  />
		<div class="form-group">
			<label for="courseName">Course Name: </label>
			<form:input path="courseName" id="courseName" class="form-control" />
			<form:errors path="courseName" cssClass="error" />
		</div>
		<div class="form-group">
			<label for="courseCode">Course Code: </label>
			<form:input path="courseCode" id="courseCode" class="form-control" />
			<form:errors path="courseCode" cssClass="error" />
		</div>
		<div class="form-group">
			<label for="coursesPre">Prerequisite: </label>
			<form:select path="coursesPre" class="form-control">
				<form:option value="" selected="true">No prerequisite</form:option>
				<c:forEach items="${listCourse }" var="coursePre">
					<form:option value="${coursePre.id }" >${coursePre.courseName }</form:option>					 
				</c:forEach>
			</form:select>
			<form:errors path="coursesPre" cssClass="error" />
		</div>
		<div class = "form-group">
			<label for="targetBlock">Target Block: </label>
			<form:select path="targetBlock" id="targetBlock"
				class="form-control" multiple="multiple">
				<form:option value="A">A</form:option>
				<form:option value="B">B</form:option>
				<form:option value="C">C</form:option>
				<form:option value="D">D</form:option>
				<form:option value="E">E</form:option>
			</form:select>
			<form:errors path="targetBlock" cssClass="error" />
		</div>
		<div class="form-group">
			<label for="faculties">Faculty: </label>
			<form:select path="faculties" class="form-control">
				<form:option value="">No faculty</form:option>
				<c:forEach items="${listFaculty }" var="faculty">
				<form:option value="${faculty.id}">${faculty.firstName} ${faculty.lastName}</form:option>
				</c:forEach>
			</form:select>
			<%-- <form:input path="faculties" id="faculties" class="form-control" /> --%>
			<form:errors path="faculties" cssClass="error" />
		</div>
		<div class="form-group">
			<input type="submit" value="Register" class="btn btn-info" />
		</div>
	
</form:form>
<div id="invalidCourseInputDialog">
	<spring:message code="course.modal.invalidinput.content" />
</div>
<script>
	$('#createCourseForm').submit(function(event) {
		event.preventDefault(); // Prevent the form from submitting via the browser
		var form = $(this);

		var cname = $('#courseName').val();
		var ccode = $('#courseCode').val();
				
		if ((cname == '') || (ccode == '')) {
			$("#invalidCourseInputDialog").dialog("open");
			return;
		}
		$.ajax({
			type : form.attr('method'),
			url : form.attr('action'),
			data : form.serialize(),
			success : function(data) {				
				$("#courseDialog").dialog("close");
				loadCourses();
			},
			error : function(e) {
				alert(e.responseJSON["message"]);
			}
		});
	});
	
	$("#invalidCourseInputDialog").dialog({
		title : '<spring:message code="course.modal.invalidinput.title" />',
		autoOpen : false,
		height : "auto",
		width : "auto",
		modal: true,
     	buttons: {
        	Ok: function() {
				$(this).dialog("close");
	        }
      }
	});
</script>