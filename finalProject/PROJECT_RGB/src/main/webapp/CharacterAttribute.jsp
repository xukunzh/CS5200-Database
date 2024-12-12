<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Character Attribute</title>
<style>.container { max-width: 1200px; margin: 0 auto; padding: 20px; }</style>
</head>
<body>
  <div class="container">
	<h1>${messages.title}</h1>
        <table border="1">
            <tr>
                <th>Attribute</th>
                <th>Value</th>
            </tr>
            <c:forEach items="${characterAttributes}" var="characterAttribute" >
                <tr>
                    <td><c:out value="${characterAttribute.getAttribute().getName()}" /></td>
                    <td><c:out value="${characterAttribute.getValue()}" /></td>
                </tr>
            </c:forEach>
       </table>
    <p><a href="findcharacters">Back to Character List</a></p>
  </div>
</body>
</html>