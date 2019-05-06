<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 1/25/17
  Time: 4:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="/document/head.jsp" />
</head>
<body>
    <form id="form" action="/Authentication/logout.do" hidden>
    </form>
<script>
    $(document).ready(submiting());
    function submiting() {
        $("#form").submit();
    }
</script>
</body>
</html>
