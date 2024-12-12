<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Character Money</title>
<style>
	.container { max-width: 1200px; margin: 0 auto; padding: 20px; }
    table { border-collapse: collapse; width: 100%; margin-top: 20px; }
    th, td { padding: 8px; text-align: left; border: 1px solid #ddd; }
    th { background-color: #f2f2f2; }
    .money-increase { color: green; }
    .money-decrease { color: red; }
    .update-form { margin-top: 20px; padding: 10px; border: 1px solid #ddd; background-color: #f9f9f9; }
</style>
</head>
<body>
  <div class="container">
    <h1>Character Money Details</h1>
    
    <c:if test="${not empty character}">
        <h2>Character: ${character.getFirstName()} ${character.getLastName()}</h2>
        <p>Player Account Name: ${character.getPlayerAccount().getName()}</p>

        <c:if test="${not empty money}">
            <h3>Current Balance</h3>
            <table>
                <tr>
                    <th>Currency Type</th>
                    <th>Current Amount</th>
                    <th>Weekly Amount</th>
                </tr>
                <tr>
                    <td>${money.getCurrency().getName()}</td>
                    <td>${money.getTotalAmount()}</td>
                    <td>${money.getWeeklyAmount()}</td>
                </tr>
            </table>

            <div class="update-form">
                <h3>Update Money Amount</h3>
                <form action="moneyupdate" method="post">
                    <input type="hidden" name="characterfullname" 
                           value="${character.getFirstName()} ${character.getLastName()}">
                    <input type="hidden" name="currency" 
                           value="${money.getCurrency().getName()}">
                    <p>
                        <label for="newAmount">New Amount:</label><br/>
                        <input type="number" id="newAmount" name="newAmount" 
                               value="${money.getTotalAmount()}"
                               min="0" required>
                    </p>
                    <p>
                        <label for="reason">Reason for Change:</label><br/>
                        <input type="text" id="reason" name="reason" 
                               placeholder="Enter reason for update"
                               style="width: 300px;" required>
                    </p>
                    <p>
                        <input type="submit" value="Update Amount">
                    </p>
                </form>
            </div>

            <!-- Transaction History -->
            <c:if test="${not empty transactions}">
                <h3>Transaction History</h3>
                <table>
                    <tr>
                        <th>Date/Time</th>
                        <th>Previous Amount</th>
                        <th>Change</th>
                        <th>New Amount</th>
                        <th>Type</th>
                        <th>Reason</th>
                    </tr>
                    <c:forEach items="${transactions}" var="transaction">
                        <tr>
                            <td><fmt:formatDate value="${transaction.getTransactionTime()}" 
                                pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>${transaction.getPreviousAmount()}</td>
                            <td class="${transaction.getAmount() >= 0 ? 'money-increase' : 'money-decrease'}">
                                ${transaction.getAmount() >= 0 ? '+' : ''}${transaction.getAmount()}
                            </td>
                            <td>${transaction.getNewAmount()}</td>
                            <td>${transaction.getTransactionType()}</td>
                            <td>${transaction.getReason()}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </c:if>

        <c:if test="${empty money}">
            <p>No money details available for this character.</p>
        </c:if>
    </c:if>

    <c:if test="${not empty success}">
        <p style="color: green;">${success}</p>
    </c:if>

    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>

    <br/>
    <a href="findcharacters">Back to Character List</a>
  </div>
</body>
</html>