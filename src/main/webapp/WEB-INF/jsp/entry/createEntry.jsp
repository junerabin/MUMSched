<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<form:form id="createEntryForm" method="POST" modelAttribute="entry"
	action="/mumsched/createentry">
	<form:input type="hidden" path="id" id="id" />
		<div class="form-group">
			<label for="name">Entry Name: </label>
			<form:input path="name" id="name" class="form-control" />
			<form:errors path="name" cssClass="error" />
		</div>
		<div class="form-group">
			<label for="totalFpp">Total of FPP: </label>
			<form:input path="totalFpp" id="totalFpp" class="form-control" />
			<form:errors path="totalFpp" cssClass="error" />
		</div>
		<div class="form-group">
			<label for="totalMpp">Total of MPP: </label>
			<form:input path="totalMpp" id="totalMpp" class="form-control" />
			<form:errors path="totalMpp" cssClass="error" />
		</div>
		<div class="form-group">
			<label for="percentOpt">Percent of OPT: </label>
			<form:input path="percentOpt" id="percentOpt" class="form-control" />
			<form:errors path="percentOpt" cssClass="error" />
		</div>
		<%-- <div class="form-group">
			<label for="blocks">Block: </label>
			<form:select path="blocks" class="form-control">
				<form:option value="">Not yet decide</form:option>
				<c:forEach items="${listBlock }" var="block">
					 <form:option value="${block.id }">${block.name }</form:option>
				</c:forEach>
			</form:select>
			<form:errors path="blocks" cssClass="error" />
		</div> --%>
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
			<input type="submit" value="Create" class="btn btn-info" />
		</div>
	
</form:form>
<div id="invalidEntryInputDialog">
	<spring:message code="entry.modal.invalidinput.content" />
</div>
<script>
	$('#createEntryForm').submit(function(event) {
		event.preventDefault(); // Prevent the form from submitting via the browser
		var form = $(this);
		
		var ename = $('#name').val();
		var efpp = $('#totalFpp').val();
		var empp = $('#totalMpp').val();
		var eopt = $('#percentOpt').val();
		var estartdate = $('#startDate').val();
		var eenddate = $('#endDate').val();
				
		if ((ename == '') || (efpp == '') || (empp == '') || (eopt == '') || (estartdate == '') || (eenddate == '')) {
			$("#invalidEntryInputDialog").dialog("open");
			return;
		}
		
		$.ajax({
			type : form.attr('method'),
			url : form.attr('action'),
			data : form.serialize(),
			success : function(data) {
				console.log(data);
				$("#entryDialog").dialog("close");
				loadEntries();
			},
			error : function(e) {
				alert(e.responseJSON["message"]);
			}
		});
	});
	$("#invalidEntryInputDialog").dialog({
		title : '<spring:message code="entry.modal.invalidinput.title" />',
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