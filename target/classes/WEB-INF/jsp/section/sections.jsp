<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List Section</title>
<style>
.container {
	padding-left: 50px;
	padding-top: 20px;
}
</style>
</head>
<body>
    <div class="container">
        <div class="row">
            <input id="createSection" type="button" value="Create Section"
                class="btn btn-info">
        </div>
        <br />
        <table class="table table-hover">
            <thead>
                <tr>
                    <th></th>
                    <th><spring:message code="section.name" /></th>
                    <th><spring:message code="section.capacity" /></th>
                    <th><spring:message code="section.block" /></th>
                    <th><spring:message code="section.course" /></th>
                    <th><spring:message code="section.faculty" /></th>
                    <th><spring:message code="section.action" /></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${listSection}" var="section" varStatus="loop">
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td>${section.name}</td>
                        <td>${section.capacity}</td>
                        <td>${section.block.name}</td>
                        <td>${section.course.courseName}</td> 
                        <td>${section.faculty.firstName}  ${section.faculty.lastName}</td> 
                        <td><a href='javascript:editSection(${section.id})'><span class="glyphicon glyphicon-pencil"></span> </a>|
                            <a href='javascript:deleteSection(${section.id })'><span class="glyphicon glyphicon-trash"></span></a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <div id="sectionDialog"></div>
    <div id="deleteSectionDialog"></div>
    <script>
        $(document).ready(function() {
            $(function() {
                $("#sectionDialog").dialog({
                    title: "<spring:message code='section.modal.title' />",
                    autoOpen: false,
                    height: "auto",
                    width: "800px",
                    
                });
                $("#createSection").on("click", function() {
                    showSectionDialog("create");
                });
            });
        });
        
        $(document).ready(function() {
            $(function() {
                $("#deleteSectionDialog").dialog({
                    title: "<spring:message code='section.modal.title' />",
                    autoOpen: false,
                    height: "auto",
                    width: "auto",
                    
                });
                $("#createSection").on("click", function() {
                    showSectionDialog("create");
                });
            });
        });
        
        var showSectionDialog = function(action, id) {
            var ajaxUrl = "/mumsched/createsection";
            if (action == 'edit') {
                ajaxUrl = "/mumsched/editsection/" + id; 
            }
            $.ajax({
                type : "GET",
                url : ajaxUrl,
                success : function(data) {
                    var sectionDialog = $("#sectionDialog");
                    sectionDialog.html(data);
                    sectionDialog.dialog("open");
                },

                error : function(e) {
                    alert('Error: ' + e);
                }
            });
        }
        
        var editSection = function(id) {
            showSectionDialog("edit", id);
        }
        
        var deleteSection = function(id){
            $.ajax({
                type : "GET",
                url : "/mumsched/deletesection/" + id,
                success : function(data) {  
                    var sectionDialog = $("#deleteSectionDialog");
                    sectionDialog.html(data);
                    sectionDialog.dialog("open");
                },

                error : function(e) {
                    alert('Error: ' + e);
                }
            });
        }
    </script>

</body>
</html>
