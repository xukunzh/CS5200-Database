<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Character Jobs</title>
    <style>
        .container { max-width: 1200px; margin: 0 auto; padding: 20px; }
        .job-card {
            border: 1px solid #ddd;
            padding: 15px;
            margin: 10px 0;
            border-radius: 5px;
            background-color: #f9f9f9;
        }
        .history-table { width: 100%; border-collapse: collapse; margin: 20px 0; }
        .history-table th, .history-table td { padding: 8px; text-align: left; border: 1px solid #ddd; }
        .history-table th { background-color: #f5f5f5; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Character Jobs</h1>
        <h2>Character: ${character.getFirstName()} ${character.getLastName()}</h2>
        <p>Player: ${character.getPlayerAccount().getName()}</p>

        <h3>Job Details</h3>
        <div>
            <c:forEach items="${characterJobs}" var="charJob">
                <div class="job-card">
                    <h4>${job.getJobName()}</h4>
                    <p>Level: ${charJob.getLevel()}</p>
                    <div>
                                <p>Experience: ${charJob.getExperiencePoints()} / ${charJob.getNextLevelExp()}</p>
                                <p>Needed for next level: ${charJob.getNextLevelExp() - charJob.getExperiencePoints()}</p>
                     </div>
                 </div>
            </c:forEach>
         </div>

         <h3>Job History</h3>
         <table class="history-table">
             <tr>
                 <th>Date/Time</th>
                 <th>Job</th>
                 <th>Type</th>
                 <th>Change Details</th>
                 <th>Reason</th>
             </tr>
             <c:forEach items="${jobHistory}" var="history">
                 <tr>
                     <td><fmt:formatDate value="${history.getChangeTime()}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                     <td>${history.getJob().getJobName()}</td>
                     <td>${history.getChangeType()}</td>
                     <td>
                         <c:choose>
                             <c:when test="${history.getChangeType() eq 'LEVEL_UP'}">
                                 Level ${history.getOldLevel()} → ${history.getNewLevel()}
                             </c:when>
                             <c:otherwise>
                                 EXP: ${history.getOldExp()} → ${history.getNewExp()}
                                 (Gained: ${history.getNewExp() - history.getOldExp()})
                             </c:otherwise>
                         </c:choose>
                     </td>
                     <td>${history.getReason()}</td>
                 </tr>
             </c:forEach>
         </table>

        <p><a href="findcharacters">Back to Character List</a></p>
    </div>
</body>
</html>