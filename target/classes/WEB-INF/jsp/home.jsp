<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" type="image/jpg" href="images/mum.jpg">

<title>Home page</title>

<link href="webjars/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet" />
<link href="webjars/jquery-ui/1.12.1/jquery-ui.min.css" rel="stylesheet" />
<style type="text/css">
body {
	background-image: url('images/register.jpg');
	background-size: cover;
	min-height: 440px;
	background-position: center;
	border-bottom: 3px solid #0179be;
	background-color: #cccccc;
}

</style>
</head>
<body>
	<jsp:include page="layout/header2.jsp" />

	<c:url value="/logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>

	<!-- body goes here -->
	<div class="container" id="content"></div>

	<script src="webjars/jquery/3.1.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="webjars/jquery-ui/1.12.1/jquery-ui.min.js"></script>

	<script>
		var logout = function() {
			$("#logoutForm").submit();
		}
		var loadCourses = function() {
			$.ajax({
				type : "GET",
				url : "/mumsched/course",
				success : function(data) {
					$('#content').html(data);
				},

				error : function(e) {
					alert('Error: ' + e);
				}
			});
		}
		var loadEntries = function(){
			$.ajax({
				type : "GET",
				url : "/mumsched/entry",
				success : function(data){
					$('#content').html(data);
				},
				error : function(e){
					alert('Error: ' + e);
				}
			});
		}
		var loadBlocks = function(){
			$.ajax({
				type : "GET",
				url : "/mumsched/block",
				success : function(data){
					$('#content').html(data);
				},
				error : function(e){
					alert('Error: ' + e);
				}
			});
		}
		var loadFaculties = function() {
			$.ajax({
				type : "GET",
				url : "/mumsched/facultylist",
				success : function(data) {
					$('#content').html(data);
				},

				error : function(e) {
					alert('Error: ' + e);
				}
			});
		}

		var viewFacultyProfile = function() {
			$.ajax({
				type : "GET",
				url : "/mumsched/facultyprofile",
				success : function(data) {
					$('#content').html(data);
				},

				error : function(e) {
					alert('Error: ' + e);
				}
			});
		}
		
        var loadSections = function() {
            $.ajax({
                type : "GET",
                url : "/mumsched/sections",
                success : function(data) {
                    $('#content').html(data);
                },

                error : function(e) {
                    alert('Error: ' + e);
                }
            });
        }		
		var loadSchedules = function() {
			$.ajax({
				type : "GET",
				url : "/mumsched/schedules",
				success : function(data) {
					$('#content').html(data);
				},

				error : function(e) {
					alert('Error: ' + e);
				}
			});
		}
		
		var loadScheduleDetail = function(id) {
			var ajaxUrl = "/mumsched/viewScheduleDetail/" + id;
			$.ajax({
				type : "GET",
				url : ajaxUrl,
				success : function(data) {
					$('#content').html(data);
				},
				error : function(e) {
					alert('Error: ' + e);
				}
			});
		}
		
		var studentViewSchedule = function() {
			$.ajax({
				type : "GET",
				url : "/mumsched/student/viewSchedule",
				success : function(data) {
					$('#content').html(data);
				},

				error : function(e) {
					alert('Error: ' + e);
				}
			});
		}
		
		var viewScheduleGeneration = function() {
			var ajaxUrl = "/mumsched/generateSchedule";
			$.ajax({
				type : "GET",
				url : ajaxUrl,
				success : function(data) {
					$('#content').html(data);
				},
				error : function(e) {
					alert('Error: ' + e);
				}
			});
		}
		
		var loadSchedule = function(data) {
			$('#content').html(data);
		}
		
		var loadRegisterSection = function(studentId) {
			$.ajax({
				type : "GET",
				url : "/mumsched/registersection/" + studentId,
				success : function(data) {
					$('#content').html(data);
				},

				error : function(e) {
					alert('Error: ' + e);
				}
			});
		}
		
		var loadStudents = function() {
			$.ajax({
				type : "GET",
				url : "/mumsched/studentlist",
				success : function(data) {
					$('#content').html(data);
				},

				error : function(e) {
					alert('Error: ' + e);
				}
			});
		}
		
	</script>
</body>
</html>