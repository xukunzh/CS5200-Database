<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a Character</title>
<style>.container { max-width: 1200px; margin: 0 auto; padding: 20px; }</style>
</head>
<body>
  <div class = "container">
	<h1>Create a New Character</h1>
	
	<form action="charactercreate" method="post">
		<p>
			<label for="username">Player Email (Character)</label>
			<input id="username" name="username" value="${fn:escapeXml(param.username)}" required>
		</p>
		<p>
			<label for="firstname">Character First Name</label>
			<input id="firstname" name="firstname" value="${fn:escapeXml(param.firstname)}" required>
		</p>
		<p>
			<label for="lastname">Character Last Name</label>
			<input id="lastname" name="lastname" value="${fn:escapeXml(param.lastname)}" required>
		</p>
		<p>
			<input type="submit" value="Create Character">
		</p>
		<p>
			<span id="successMessage"><b>${messages.success}</b></span>
			<span id="errorMessage"><b>${messages.error}</b></span>
		</p>
	</form>
	<br/>
	<div id="backToFindCharacters"><a href="findcharacters">Back to Find Characters</a></div>
  </div>
</body>
</html>
 