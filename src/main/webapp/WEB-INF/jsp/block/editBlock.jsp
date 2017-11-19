<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form:form id="editBlockForm" method="POST" modelAttribute="block"
	action="/mumsched/editblock">
	<form:input type="hidden" path="id" id="id" />
		<div class="form-group">
			<label for="name">Block Name: </label>
			<form:input path="name" id="name" class="form-control" />
			<form:errors path="name" cssClass="error" />
		</div>
		<div class="form-group">
			<label for="startDate">Start Date: </label>
			<form:input type="date" path="startDate" id="startDate" class="form-control" />
			<form:errors path="startDate" cssClass="error" />
		</div>
		<div class="form-group">
			<label for="endDate">End Date: </label>
			<form:input type="date" path="endDate" id="endDate" class="form-control" />
			<form:errors path="endDate" cssClass="error" />
		</div>
		<div class="form-group">
			<label for="entries">Entry: </label>
			<form:select path="entries" class="form-control">
				<form:option value="">No Entry</form:option>
				<c:forEach items="${listEntry }" var="entry">
					 <form:option value="${entry.id }">${entry.name }</form:option>
				</c:forEach>
			</form:select>
			<form:errors path="entries" cssClass="error" />
		</div>
		<div class="form-group">
			<input type="submit" value="Update" class="btn btn-info" />
		</div>
	
</form:form>
<div id="invalidBlockInputDialog">
	<spring:message code="block.modal.invalidinput.content" />
</div>
<script>
	$('#editBlockForm').submit(function(event) {
		event.preventDefault(); // Prevent the form from submitting via the browser
		var form = $(this);
		
		var bname = $('#name').val();
		var bstartdate = $('#startDate').val();
		var benddate = $('#endDate').val();
		
		if ((bname == '') || (bstartdate == '') || (benddate == '')) {
			$("#invalidBlockInputDialog").dialog("open");
			return;
		}
		
		$.ajax({
			type : form.attr('method'),
			url : form.attr('action'),
			data : form.serialize(),
			success : function(data) {
				$("#blockDialog").dialog("close");
				loadBlocks();
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	});
	
	$("#invalidBlockInputDialog").dialog({
		title : '<spring:message code="block.modal.invalidinput.title" />',
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