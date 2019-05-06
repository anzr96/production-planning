<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 10/1/2016
  Time: 1:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col-md-12">
        <h1 class="page-header">نمایش میزان کار دستگاه ها</h1>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-3">
                        <input id="picker" class="form-control" onchange="ajaxData()" />
                    </div>
                </div>
            </div>
            <div class="panel-body">
                <table class="table table-striped table-bordered table-hover text-center" width="100%">
                    <thead>
                    <tr class="text-center">
                        <th>کد دستگاه</th>
                        <th>نام دستگاه</th>
                        <th>مرکز</th>
                        <th colspan="2">ظرفیت</th>
                    </tr>
                    </thead>
                    <tbody id="tbody">
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        setDate();
        ajaxData();
        $("#picker").datepicker({
            todayBtn: "linked",
            clearBtn: true,
            nextText: "Later",
            prevText: "Earlier",
            isRTL:true,
            format: "mm-dd-yyyy",
            toggleActive: true
        });
    });
    function setDate() {
        var today = new Date();
        var dd = today.getDate();
        var mm = today.getMonth()+1; //January is 0!
        var yyyy = today.getFullYear();

        if(dd<10) {
            dd='0'+dd
        }

        if(mm<10) {
            mm='0'+mm
        }

        today = mm+'-'+dd+'-'+yyyy;
        $("#picker").val(today);
    }
    function ajaxData() {
        $.ajax("/document/machine/ajaxData.do",{
           data:{data:$("#picker").val()},
            type:"POST",
            dataType:"XML"
        }).done(function (data) {
            xmlDoc = data.responseXML;
            var machine = xmlDoc.getElementsByTagName("machine");

            var table = document.getElementById('tbody');
            table.innerHTML = "";

            for(var i = 0; i<machine.length; i++) {
                var row = table.insertRow();
                $(row).addClass("text-center");

                var newRow = row.insertCell(0);
                newRow.rowSpan = 3;
                var code = machine[i].getElementsByTagName("code");
                $(newRow).append(code[0].childNodes[0].nodeValue);

                var newRow = row.insertCell(1);
                newRow.rowSpan = 3;
                var name = machine[i].getElementsByTagName("name");
                $(newRow).append(name[0].childNodes[0].nodeValue);

                var newRow = row.insertCell(2);
                newRow.rowSpan = 3;
                var center = machine[i].getElementsByTagName("center");
                $(newRow).append(center[0].childNodes[0].nodeValue);

                var shift = machine[i].getElementsByTagName("shift");

                var newRow = row.insertCell(3);
                $(newRow).append(shift[0].getAttribute("degree"))

                var newRow = row.insertCell(4);
                $(newRow).addClass("col-md-4");
                var div1 = $(document.createElement("div"));
                div1.addClass("progress progress-striped active");

                var x = shift[0].childNodes[0].nodeValue * 8 * shift[0].childNodes[1].nodeValue / 100;
                var temp = "Unit per Hour : " + shift[0].childNodes[0].nodeValue * 1 + " , Capacity : " + x;
                $(div1).attr("title", temp);

                var div2 = $(document.createElement("div"));
                div2.prop("role", "progressbar");

                var temp = shift[0].childNodes[1].nodeValue == null ? 0 : shift[0].childNodes[1].nodeValue;
                if (temp > 90) {
                    div2.addClass("progress-bar progress-bar-danger");
                } else if (temp > 70) {
                    div2.addClass("progress-bar progress-bar-warning");
                } else {
                    div2.addClass("progress-bar progress-bar-success");
                }
                temp = temp + "%";

                $(div2).css("width", temp);
                $(div1).append(div2);
                $(newRow).append(div1);

                var row = table.insertRow();
                $(row).addClass("text-center");

                var newRow = row.insertCell(0);
                $(newRow).append(shift[1].getAttribute("degree"))

                var newRow = row.insertCell(1);
                var div1 = $(document.createElement("div"));
                div1.addClass("progress progress-striped active");

                var x = shift[1].childNodes[0].nodeValue * 8 * shift[1].childNodes[1].nodeValue / 100;
                var temp = "Unit per Hour : " + shift[1].childNodes[0].nodeValue * 1 + " , Capacity : " + x;
                $(div1).attr("title", temp);

                var div2 = $(document.createElement("div"));
                div2.prop("role", "progressbar");

                var temp = shift[1].childNodes[1].nodeValue == null ? 0 : shift[1].childNodes[1].nodeValue;
                if (temp > 90) {
                    div2.addClass("progress-bar progress-bar-danger");
                } else if (temp > 70) {
                    div2.addClass("progress-bar progress-bar-warning");
                } else {
                    div2.addClass("progress-bar progress-bar-success");
                }
                temp = temp + "%";

                $(div2).css("width", temp);
                $(div1).append(div2);
                $(newRow).append(div1);

                var row = table.insertRow();
                $(row).addClass("text-center");

                var newRow = row.insertCell(0);
                $(newRow).append(shift[2].getAttribute("degree"))

                var newRow = row.insertCell(1);
                var div1 = $(document.createElement("div"));
                div1.addClass("progress progress-striped active");

                var x = shift[2].childNodes[0].nodeValue * 8 * shift[2].childNodes[1].nodeValue / 100;
                var temp = "Unit per Hour : " + shift[2].childNodes[0].nodeValue * 1 + " , Capacity : " + x;
                $(div1).attr("title", temp);

                var div2 = $(document.createElement("div"));
                div2.prop("role", "progressbar");

                var temp = shift[2].childNodes[1].nodeValue == null ? 0 : shift[2].childNodes[1].nodeValue;
                if (temp > 90) {
                    div2.addClass("progress-bar progress-bar-danger");
                } else if (temp > 70) {
                    div2.addClass("progress-bar progress-bar-warning");
                } else {
                    div2.addClass("progress-bar progress-bar-success");
                }
                temp = temp + "%";

                $(div2).css("width", temp);
                $(div1).append(div2);
                $(newRow).append(div1);
            }
        }).fail(function (data) {
            errors(data);
        });
    }
</script>
