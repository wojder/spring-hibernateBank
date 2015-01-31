<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="a" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset=UTF-8">
        <title><s:message code="home.title"/></title>
    </head>
    <body>
        <h1><s:message code="home.header"/></h1>
        <ul>
            <li><a href=""><s:message code="home.createAccount"/></li>
            <li><a href=""><s:message code="home.payIn"/></li>
            <li><a href=""><s:message code="home.payOut"/></li>
            <li><a href=""><s:message code="home.transfer"/></li>
        </ul>
    </body>
</html>
