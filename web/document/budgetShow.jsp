<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 1/31/17
  Time: 12:10 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<br/>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-3">
                        <p>تاریخ</p>
                    </div>
                    <div class="col-md-3">
                        <input id="datepicker" name="date" class="form-control" placeholder="yyyy" onchange="ajaxData()"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<br/>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">نمایش بودجه و مقدار باقی مانده</h4>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-12">
                        <table width="100%" class="table table-striped table-bordered table-hover text-center" id="table">
                            <thead >
                            <tr>
                                <td></td>
                                <td>مقدار کل</td>
                                <td>مقدار باقی مانده</td>
                            </tr>
                            </thead>
                            <tbody id="tbody">

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="row" id="divData" hidden>
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">نمایش مقدار تولید نسبت به بودجه</h4>
            </div>
            <div class="panel-body">
                <div class="row" id="barDiv">

                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $("#datepicker").pDatepicker({
            format: "YYYY",
            dayPicker:{
                enabled:false
            },
            monthPicker:{
                enabled:false
            },
            yearPicker:{
                enabled:true,
                titleFormat:"YYYY"
            },
            navigator:{
                enabled: true,
                text: {
                    btnNextText: ">",
                    btnPrevText: "<"
                },
            },
            initialValue:false
        });
    });
    function ajaxData() {
        if ($("#datepicker").val() != null && $("#datepicker").val() != ""){
            var year = parseInt($("#datepicker").val());
            var d = new persianDate([year, 1, 1, 0, 0, 0, 0]).toDate();
            $.ajax("/document/budget/show.do",{
                data:{date:d.getFullYear()},
                type:"POST",
                dataType:"JSON"
            }).done(function (data) {
                var price = data.price;
                var weight = data.weight;
                var remainPrice = data.remainPrice;
                var remainWeight = data.remainWeight;

                $("#table").DataTable().destroy();
                $("#tbody").children().remove();

                var tr = document.createElement("tr");
                var td = document.createElement("td");
                $(td).text("بودجه(ریالی)");
                $(tr).append(td);
                var td = document.createElement("td");
                $(td).text(price);
                $(tr).append(td);
                var td = document.createElement("td");
                $(td).text(remainPrice);
                $(tr).append(td);
                $("#tbody").append(tr);

                var tr = document.createElement("tr");
                var td = document.createElement("td");
                $(td).text("بودجه(وزنی)");
                $(tr).append(td);
                var td = document.createElement("td");
                $(td).text(weight);
                $(tr).append(td);
                var td = document.createElement("td");
                $(td).text(remainWeight);
                $(tr).append(td);
                $("#tbody").append(tr);

                createDataTable($("#table"));

                bar(data.data);
            }).fail(function (data) {
                errors(data);
            });
        }
    }
    function bar(data) {
        $("#barDiv").children().remove();

        Highcharts.chart('barDiv', {
            chart: {
                type: 'column'
            },

            title: {
                text: 'نمایش بودجه'
            },

            xAxis:{
                categories: data.code,
                crosshair: true
            },

            yAxis: {
                title: {
                    text: 'مقدار بودجه'
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

            series: data

        });
        $("#divData").attr("hidden", false);
    }
</script>

