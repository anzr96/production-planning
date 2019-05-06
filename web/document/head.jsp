<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 1/26/17
  Time: 1:45 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv = "Content-Language" content = "fa"/>
<meta http-equiv = "Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="Web Application Lastic Pars">
<meta name="author" content="Amir Nazari">

<title>لاستیک پارس</title>
<style>
    @font-face {
        font-family: "BNazanin";
        src: url(<c:url value="/resources/fonts/BNazanin.ttf"/>) format("truetype");
    }
</style>

<link rel="shortcut icon" href="<c:url value="/resources/images/PhotoBox.png"/>" />

<link rel="stylesheet" href="<c:url value="/resources/css/normalize.min.css"/> " type="text/css">
<link rel="stylesheet" href="<c:url value="/resources/css/animate.min.css"/> " type="text/css">

<!-- Bootstrap Core CSS -->
<link href="<c:url value="/resources/css/bootstrap.min.css"/> " rel="stylesheet" type="text/css" >
<link href="<c:url value="/resources/css/bootstrap-datepicker.css"/> " rel="stylesheet" type="text/css">

<!--TimePicker-->
<link href="<c:url value="/resources/css/jquery.timepicker.min.css"/> " rel="stylesheet" type="text/css">

<!-- Custom Fonts -->
<link href="<c:url value="/resources/css/font-awesome.min.css"/> " rel="stylesheet" type="text/css">

<!--jQuery Core-->
<link href="<c:url value="/resources/css/jquery-ui.min.css"/> " rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/css/theme.css"/> " rel="stylesheet" type="text/css">

<!--Alertify-->
<link href="<c:url value="/resources/css/alertify.rtl.min.css"/> " rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/css/default.rtl.min.css"/> " rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/css/semantic.rtl.min.css"/> " rel="stylesheet" type="text/css">

<!-- Select2 -->
<link href="<c:url value="/resources/css/select2.min.css"/> " rel="stylesheet" type="text/css">

<!-- ProgressLine -->
<link href="<c:url value="/resources/css/progressLine.css"/> " rel="stylesheet" type="text/css">

<!-- Custom CSS -->
<link href="<c:url value="/resources/css/sb-admin-2.css" /> " type="text/css" rel="stylesheet">
<link href="<c:url value="/resources/css/coustom.css" /> " type="text/css" rel="stylesheet">

<!-- jQuery context menu -->
<link href="<c:url value="/resources/css/jquery.contextMenu.min.css" /> " type="text/css" rel="stylesheet">

<!-- highcharts -->
<link href="<c:url value="/resources/css/highcharts.css" /> " type="text/css" rel="stylesheet">

<!-- navSideBar -->
<link href="<c:url value="/resources/css/navSideBar.css" /> " type="text/css" rel="stylesheet">

<!--DataTable-->
<link href="<c:url value="/resources/css/dataTables.bootstrap.min.css"/> " rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/css/scroller.bootstrap.min.css"/> " rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/css/buttons.dataTables.min.css"/> " rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/css/buttons.bootstrap.min.css"/> " rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/css/fixedHeader.bootstrap.min.css"/> " rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/css/fixedColumns.bootstrap.min.css"/> " rel="stylesheet" type="text/css">
<!--/DataTable-->

<!-- persian-date -->
<link href="<c:url value="/resources/css/persian-datepicker-0.4.5.min.css" /> " type="text/css" rel="stylesheet">

<!--Javascript-->

<!-- jQuery -->
<script type="application/javascript" src="<c:url value="/resources/js/jquery.min.js"/>"></script>
<script type="application/javascript" src="<c:url value="/resources/js/jquery-ui.js"/>"></script>
<script type="application/javascript" src="<c:url value="/resources/js/jQuery.Mask.js"/>"></script>

<!--Alertify-->
<script type="application/javascript" src="<c:url value="/resources/js/alertify.min.js"/>"></script>

<!-- Bootstrap Core JavaScript -->
<script type="application/javascript" src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
<script type="application/javascript" src="<c:url value="/resources/js/bootstrap-datepicker.js"/>"></script>

<!--TimePicker-->
<script type="application/javascript" src="<c:url value="/resources/js/jquery.timepicker.min.js"/>"></script>

<!-- Custom Theme JavaScript -->
<script type="application/javascript" src="<c:url value="/resources/js/sb-admin-2.min.js"/>"></script>

<!--CanvasJs-->
<script type="application/javascript" src="<c:url value="/resources/js/jquery.canvasjs.min.js"/>"></script>
<script type="application/javascript" src="<c:url value="/resources/js/canvasjs.min.js"/>"></script>

<!--Select2-->
<script type="application/javascript" src="<c:url value="/resources/js/select2.min.js"/>"></script>

<!--MD5-->
<script type="application/javascript" src="<c:url value="/resources/js/md5.js"/>"></script>

<!--commafy-->
<script type="application/javascript" src="<c:url value="/resources/js/commafy.js"/>"></script>

<!--printThis-->
<script type="application/javascript" src="<c:url value="/resources/js/printThis.js"/>"></script>

<!--jQuery.cookie-->
<script type="application/javascript" src="<c:url value="/resources/js/jquery.cookie.js"/>"></script>

<!--jQuery Position-->
<script type="application/javascript" src="<c:url value="/resources/js/jquery.ui.position.min.js"/>"></script>

<!--jQuery Context Menu-->
<script type="application/javascript" src="<c:url value="/resources/js/jquery.contextMenu.min.js"/>"></script>

<!--highcharts-->
<script type="application/javascript" src="<c:url value="/resources/js/jdate.min.js"/>"></script>
<script type="application/javascript" src="<c:url value="/resources/js/jspdf.min.js"/>"></script>
<script type="application/javascript" src="<c:url value="/resources/js/svg2pdf.min.js"/>"></script>
<script type="application/javascript" src="<c:url value="/resources/js/highcharts.js"/>"></script>
<script type="application/javascript" src="<c:url value="/resources/js/modules.exporting.js"/>"></script>
<script type="application/javascript" src="<c:url value="/resources/js/modules.offline-exporting.js"/>"></script>

<!-- navSideBar-->
<script type="application/javascript" src="<c:url value="/resources/js/navSideBar.js"/>"></script>

<!--DataTable-->
<script type="application/javascript" src="<c:url value="/resources/js/jquery.dataTables.js"/>"></script>
<script type="application/javascript" src="<c:url value="/resources/js/dataTables.bootstrap.min.js"/>"></script>
<script type="application/javascript" src="<c:url value="/resources/js/jszip.min.js"/>"></script>
<script type="application/javascript" src="<c:url value="/resources/js/dataTables.buttons.min.js"/>"></script>
<script type="application/javascript" src="<c:url value="/resources/js/dataTables.scroller.min.js"/>"></script>
<script type="application/javascript" src="<c:url value="/resources/js/dataTables.fixedColumns.min.js"/>"></script>
<script type="application/javascript" src="<c:url value="/resources/js/buttons.colVis.min.js"/>"></script>
<script type="application/javascript" src="<c:url value="/resources/js/buttons.html5.min.js"/>"></script>
<script type="application/javascript" src="<c:url value="/resources/js/buttons.print.min.js"/>"></script>
<script type="application/javascript" src="<c:url value="/resources/js/buttons.bootstrap.min.js"/>"></script>
<script type="application/javascript" src="<c:url value="/resources/js/dataTables.fixedHeader.min.js"/>"></script>
<!--/DataTable-->

<!-- persian-date-->
<script type="application/javascript" src="<c:url value="/resources/js/persian-date.js"/>"></script>
<script type="application/javascript" src="<c:url value="/resources/js/persian-datepicker-0.4.5.js"/>"></script>

<!--Custom-->
<script type="application/javascript" src="<c:url value="/resources/js/Coustom.js"/>"></script>

<style>
    label {
        display: inline-block;
        width: 5em;
    }
</style>


