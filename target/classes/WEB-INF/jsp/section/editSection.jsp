<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<form:form id="editSectionForm" method="POST" modelAttribute="section"
	action="/mumsched/editsection">
	<form:input type="hidden" path="id" id="id" />
		<div class="form-group">
			<label for="name">Section Name: </label>
			<form:input path="name" id="name" class="form-control" />
			<form:errors path="name" cssClass="error" />
		</div>

		<div class="form-group">
			<label for="capacity">Capacity: </label>
			<form:input path="capacity" id="capacity" class="form-control" />
			<form:errors path="capacity" cssClass="error" />
		</div>


		<div class="form-group">
			<label for="block">Block: </label>
			<form:select path="block" class="form-control">
				<c:forEach items="${listBlock}" var="block">
					<c:if test = "${block.id == section.block.id}">
						<form:option value="${block.id}" selected = "true">${block.name}</form:option>
						<<${checkB = 1}>>
					</c:if>
					<c:if test = "${checkB != 1}">
						<form:option value="${block.id}">${block.name}</form:option>
					</c:if>
					<<${checkB = 0}>>					
				</c:forEach>
			</form:select>
			<form:errors path="block" cssClass="error" />
		</div>

		<div class="form-group">
			<label for="course">Course: </label>
			<form:select path="course" class="form-control">
				<c:forEach items="${listCourse }" var="course">
					<c:if test = "${course.id == section.course.id}">
						<form:option value="${course.id}" selected = "true">${course.courseName}</form:option>
						<<${checkC = 1}>>
					</c:if>
					<c:if test = "${checkC != 1}">
						<form:option value="${course.id}">${course.courseName}</form:option>
					</c:if>
					<<${checkC = 0}>>					
				</c:forEach>
			</form:select>
			<form:errors path="course" cssClass="error" />
		</div>

		<div class="form-group">
			<label for="faculty">Faculty: </label>
			<form:select path="faculty" class="form-control">
			<form:option value="">No faculty</form:option>
				<c:forEach items="${listFaculty }" var="faculty">
					<c:if test = "${faculty.id == section.faculty.id}">
						<form:option value="${faculty.id}" selected = "true">${faculty.firstName} ${faculty.lastName}</form:option>
						<<${checkF = 1 }>>
					</c:if>
					<c:if test = "${checkF != 1 }">
						<form:option value="${faculty.id}">${faculty.firstName} ${faculty.lastName}</form:option>
					</c:if>
					<<${checkF = 0 }>>
				</c:forEach>
			</form:select>
			<form:errors path="faculty" cssClass="error" />
		</div>

		<div class="form-group">
			<input type="submit" value="Update" class="btn btn-info" />
		</div>
</form:form>
<div id="invalidSectionInputDialog">
	<spring:message code="section.modal.invalidinput.content" />
</div>
<script>
	$('#editSectionForm').submit(function(event) {
		event.preventDefault(); // Prevent the form from submitting via the browser
		var form = $(this);
		var bcapacity = $('#capacity').val();		
		if ((bcapacity < 5) || (bcapacity > 25)) {
			$("#invalidSectionInputDialog").dialog("open");
			return;
		}		
		$.ajax({
			type : form.attr('method'),
			url : form.attr('action'),
			data : form.serialize(),
			success : function(data) {
				$("#sectionDialog").dialog("close");
				loadSections();
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	});
	$("#invalidSectionInputDialog").dialog({
		title : '<spring:message code="section.modal.invalidinput.title" />',
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