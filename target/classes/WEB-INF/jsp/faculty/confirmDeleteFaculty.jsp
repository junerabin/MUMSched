<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<style>
	#cancel{
	width: 70px;
	}
	.btn-danger{
	width: 70px;
	}
</style>
<body>

<form:form id="deleteFacultyConfirm" method="POST" modelAttribute="faculty"
	action="/mumsched/deleteFaculty">
	<p>Do you want delete faculty: ${faculty.firstName} ${faculty.lastName}</p>
	<div class="col-md-6">
		<input type="submit" value="Yes" class="btn btn-danger" />
	</div>
	<div class="col-md-6">
		<input type="button" id="cancel" class="btn btn-info" value="Cancel" />
	</div>
	<form:input type="hidden" path="id" id="id" />
</form:form>

<script>
	$('#deleteFacultyConfirm').submit(function(event) {
		event.preventDefault(); // Prevent the form from submitting via the browser
		var form = $(this);
		$.ajax({
			type : form.attr('method'),
			url : form.attr('action'),
			data : form.serialize(),
			success : function(data) {
				$("#deleteFacultyDialog").dialog("close");
				loadFaculties();
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	});
	$('#cancel').on("click", function() {
		$("#deleteFacultyDialog").dialog("close");
	});
</script>
</body>
</html>
