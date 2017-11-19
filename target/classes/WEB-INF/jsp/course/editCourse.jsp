<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<form:form id="editCourseForm" method="POST" modelAttribute="course"
	action="/mumsched/edit">
	<form:input type="hidden" path="id" id="id" />
	<div class="form-group">
		<label for="courseName">Course Name: </label>
		<form:input path="courseName" id="courseName" class="form-control" value="${course.courseName }" />
		<form:errors path="courseName" cssClass="error" />
	</div>
	<div class="form-group">
		<label for="courseCode">Course Code: </label>
		<form:input path="courseCode" id="courseCode" class="form-control" value="${course.courseCode }" />
		<form:errors path="courseCode" cssClass="error" />
	</div>
	<div class="form-group">
			<label for="coursesPre">Prerequisite: </label>
			<form:select path="coursesPre" class="form-control">
				<form:option value="">No prerequisite</form:option>
				<c:forEach items="${listCourse }" var="coursePre">
				<c:forEach items="${course.coursesPre }" var="courseComp">
					
						<c:if test="${coursePre.id == courseComp.id}">
							<form:option value="${coursePre.id }" selected="true">${coursePre.courseName }</form:option>
							<<${check = 1 }>>
						</c:if>
						
				</c:forEach>	
					<c:if test ="${check != 1 }">
					 <form:option value="${coursePre.id }">${coursePre.courseName }</form:option>
					
					</c:if>	
					 <<${check = 0 }>>
				</c:forEach>
			</form:select>
			<form:errors path="coursesPre" cssClass="error" />
		</div>
	<div class="form-group">
		<label for="targetBlock">Target Block: </label>
		<form:select path="targetBlock" id="targetBlock" class="form-control" multiple="multiple">
				<form:option value="A">A</form:option>
				<form:option value="B">B</form:option>
				<form:option value="C">C</form:option>
				<form:option value="D">D</form:option>
				<form:option value="E">E</form:option>
			</form:select>
		<%-- <form:input path="targetBlock" id="targetBlock" value="${course.targetBlock }" /> --%>
		<form:errors path="targetBlock" cssClass="error" />
	</div>
	<div class="form-group">
			<label for="faculties">Faculty: </label>
			<form:select path="faculties" class="form-control">
			<form:option value="">No faculty</form:option>
				<c:forEach items="${listFaculty }" var="faculty">
					<c:forEach items="${course.faculties }" var="facultyC">
						<c:if test = "${faculty.id == facultyC.id}">
							<form:option value="${faculty.id}" selected = "true">${faculty.firstName} ${faculty.lastName}</form:option>
							<<${checkF = 1 }>>
						</c:if>
					</c:forEach>
					<c:if test = "${checkF != 1 }">
						<form:option value="${faculty.id}">${faculty.firstName} ${faculty.lastName}</form:option>
					</c:if>
					<<${checkF = 0 }>>
				</c:forEach>
			</form:select>
			<%-- <form:input path="faculties" id="faculties" class="form-control" /> --%>
			<form:errors path="faculties" cssClass="error" />
		</div>
	<div class="form-group">
		<input type="submit" value="Update" class="btn btn-info"/>
	</div>
	
</form:form>
<div id="invalidCourseInputDialog">
	<spring:message code="course.modal.invalidinput.content" />
</div>
<script>
	$('#editCourseForm').submit(function(event) {
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
				alert('Error: ' + e);
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