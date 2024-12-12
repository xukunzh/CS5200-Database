<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a Character</title>
<style>.container { max-width: 1200px; margin: 0 auto; padding: 20px; }</style>
</head>
<body>
  <div class="container">
	<form action="findcharacters" method="post">
		<h1>Search for a Character by FirstName</h1>
		<p>
			<label for="firstname">FirstName</label>
			<input id="firstname" name="firstname" value="${fn:escapeXml(param.firstname)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<form action="findcharacters" method="get">
	    <input type="hidden" name="showAll" value="true">
	    <p>
	        <input type="submit" value="Show All Characters">
	    </p>
	</form>
	<br/>
	<div id="characterCreate"><a href="charactercreate">Create Character</a></div>
	<br/>
	<h1>Matching Characters</h1>
        <table border="1">
            <tr>
                <th>PlayerAccountName</th>
                <th>FirstName</th>
                <th>LastName</th>
                <th>Job</th>
                <th>Money</th>
                <th>Attribute</th>
                <th>Delete Character</th>
                <th>Update Character</th>
            </tr>
            <c:forEach items="${characters}" var="character" >
                <tr>
                    <td><c:out value="${character.getPlayerAccount().getName()}" /></td>
                    <td><c:out value="${character.getFirstName()}" /></td>
                    <td><c:out value="${character.getLastName()}" /></td>
                    <td><a href="characterjob?characterfullname=<c:out value='${character.getFirstName()} ${character.getLastName()}'/>">Job</a></td>
                    <td><a href="charactermoney?characterfullname=<c:out value='${character.getFirstName()} ${character.getLastName()}'/>">Money</a></td>
                    <td><a href="characterattribute?characterfullname=<c:out value='${character.getFirstName()} ${character.getLastName()}'/>">Attribute</a></td>
                    <td><a href="characterdelete?characterfullname=<c:out value='${character.getFirstName()} ${character.getLastName()}'/>">Delete</a></td>
                    <td><a href="characterupdate?characterfullname=<c:out value='${character.getFirstName()} ${character.getLastName()}'/>">Update</a></td>
                </tr>
            </c:forEach>
       </table>
	</div>
</body>
</html>