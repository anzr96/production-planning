<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 8/3/17
  Time: 4:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="/document/head.jsp" />
</head>
<body>
<div id="wrapper">
    <!-- Navigation -->
    <jsp:include page="/document/nav.jsp" />

    <div id="page-wrapper">
        <div class="row">
            <div class="col-md-12">
                <div id="container" class="ltr"></div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <table id="table" class="table table-striped table-bordered table-hover text-center" width="100%" cellspacing="0">
                    <thead>
                    <tr>
                        <th></th>
                        <th>select</th>
                        <th>input</th>
                    </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <button onclick="addRow()">add</button>
            </div>
        </div>
    </div>
</div>
<script type="application/javascript">
    var table = [];
    Highcharts.chart('container', {

        title: {
            text: 'اولین چارت'
        },

        subtitle: {
            text: 'چارت'
        },

        yAxis: {
            title: {
                text: 'تعداد کارمندان'
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            rtl:true
        },

        plotOptions: {
            series: {
                pointStart: 2010
            }
        },

        series: [{
            name: 'نصاب',
            data: [43934, 52503, 57177, 69658, 97031, 119931, 137133, 154175]
        }, {
            name: 'تولید',
            data: [24916, 24064, 29742, 29851, 32490, 30282, 38121, 40434]
        }, {
            name: 'فروش',
            data: [11744, 17722, 16005, 19771, 20185, 24377, 32147, 39387]
        }, {
            name: 'توسعه دهندگان',
            data: [null, null, 7988, 12169, 15112, 22452, 34400, 34227]
        }, {
            name: 'غیره',
            data: [12908, 5948, 8105, 11248, 8989, 11816, 18274, 18111]
        }]

    });
    function addRow() {
        var row = new Object();

        var i = $(document.createElement("i"));
        $(i).addClass("fa fa-trash-o fa-fw");
        var a = document.createElement("a");
        $(a).css("cursor", "pointer");
        $(a).val("Delete");
        $(a).on("click", function (event) {
            deleteRow(event);
        });
        $(a).append(i);
        row.a = a;

        var select = document.createElement("select");
        $(select).addClass("form-control");
        $(select).on("change", function (event) {
            console.log("hi")
        });
        $(select).attr("name","code");
        $(select).append("<option value=''>کد محصول را انتخاب کنید</option>");
        $(select).select2({
            dir:"rtl",
            placeholder:"کد محصول را انتخاب کنید",
            allowClear:true
        });
        row.select = select;

        var input = document.createElement("input");
        $(input).addClass("form-control");
        $(input).attr("type", "number");
        $(input).attr("name", "total");
        row.input = input;

        table.push(row);

        var t = $("#table").DataTable();
        t.row.add([
            "a",
            "s",
            "i"
        ]).draw(false);
    }
</script>

</body>
</html>
