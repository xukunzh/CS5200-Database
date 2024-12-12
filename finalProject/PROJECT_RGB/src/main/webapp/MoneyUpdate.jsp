<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Character Money</title>
</head>
<body>
    <h1>Character Money Details</h1>
    <c:if test="${not empty character}">
        <h2>Character: ${character.getFirstName()} ${character.getLastName()}</h2>
        <p>Player Account Name: ${character.getPlayerAccount().getName()}</p>

        <c:if test="${not empty money}">
            <h3>Money Details:</h3>
            <table border="1">
                <tr>
                    <th>Currency Type</th>
                    <th>Total Amount</th>
                    <th>Weekly Amount</th>
                    <th>Actions</th>
                </tr>
                <tr>
                    <td>${money.getCurrency().getName()}</td>
                    <td>${money.getTotalAmount()}</td>
                    <td>${money.getWeeklyAmount()}</td>
                    <td>
                        <form action="moneyupdate" method="post">
                            <input type="hidden" name="characterfullname" 
                                   value="${character.getFirstName()} ${character.getLastName()}">
                            <input type="hidden" name="currency" 
                                   value="${money.getCurrency().getName()}">
                            <input type="number" name="newAmount" 
                                   value="${money.getTotalAmount()}"
                                   min="0" required>
                            <input type="submit" value="Update">
                        </form>
                    </td>
                </tr>
            </table>
        </c:if>

        <c:if test="${empty money}">
            <p>No money details available for this character.</p>
        </c:if>
    </c:if>

    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>

    <br/>
    <a href="findcharacters">Back to Character List</a>
</body>
</html>