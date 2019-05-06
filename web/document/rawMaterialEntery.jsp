<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 10/1/2016
  ProductInTime: 1:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="col-md-12">
        <form id="form" name="form">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">وارد کردن مواد اولیه</h3>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-3">
                            <p>کد ماده اولیه </p>
                        </div>
                        <div class="col-md-3">
                            <select id="code" name="rawCode" class="form-control" onchange="dataSet(event)">
                            </select>
                        </div>
                        <div class="col-md-3">
                            <p>نام ماده اولیه </p>
                        </div>
                        <div class="col-md-3">
                            <select id="name" class="form-control" onchange="dataSet(event)">
                            </select>
                        </div>

                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-3">
                            <p>تاریخ</p>
                        </div>
                        <div class="col-md-5">
                            <div class="form-inline">
                                <input id="date" name="date" class="form-control"/>
                            </div>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-3">
                            <p>شماره بچ </p>
                        </div>
                        <div class="col-md-3">
                            <input class="form-control" name="batchNumber" placeholder="شماره بچ" />
                        </div>
                        <div class="col-md-3">
                            <p>بار نامه </p>
                        </div>
                        <div class="col-md-3">
                            <input class="form-control" name="loadNumber" placeholder="بار نامه" />
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-3">
                            <p>مقدار</p>
                        </div>
                        <div class="col-md-3">
                            <input class="form-control" type="number" name="total" placeholder="مقدار ماده اولیه" />
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-3">
                            <button class="btn btn-default btn-outline" type="button" onclick="ajaxSubmit()">تایید</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    $(function () {
        ajaxLoad();
        date();
    });
    function date() {
        $.ajax("/document/workingCalendar/get.do", {
            type:"GET",
            dataType:"json"
        }).done(function (data) {
            var dates = data.wd;
            var eds = data.ed;
            for (let obj of eds) {
                dates.push(obj.unix);
            }
            $("#page-wrapper").find("input[name='date']").pDatepicker({
                format:"YYYY/MM/DD",
                navigator:{
                    text:{
                        btnNextText:'>',
                        btnPrevText:'<'
                    }
                },
                timePicker:{
                    enabled:true,
                    hour:{
                        enabled:true
                    },
                    minute:{
                        enabled:true
                    },
                    second:{
                        enabled:false
                    },
                    meridian:{
                        enabled:true
                    }
                },
                checkDate:function (unix) {
                    if (dates.indexOf(unix) != -1){
                        return true;
                    }else {
                        return false;
                    }
                }
            })
        }).fail(function( jqXHR ) {
            errors(jqXHR);
        });
    }
    function ajaxLoad() {
        $.ajax("/document/rawmaterial/ajaxData.do",{
            type:"GET",
            dataType:"JSON"
        }).done(function (data) {
            for (var i = 0; i < data.length; i++){
                var option = document.createElement("option");
                $(option).val(data[i].code);
                $(option).text(data[i].code);
                $("#code").append(option);

                option = document.createElement("option");
                $(option).val(data[i].code);
                $(option).text(data[i].name);
                $("#name").append(option);
            }
        }).fail(function (data) {
            errors(data);
        });
    }
    function dataSet(event) {
        $("#code").val($(event.target).val());
        $("#name").val($(event.target).val());
    }
    function getDate() {
        var year = parseInt($("#page-wrapper").find("input[name='date']").val().substr(0,4));
        var month = parseInt($("#page-wrapper").find("input[name='date']").val().substr(5,2));
        var day = parseInt($("#page-wrapper").find("input[name='date']").val().substr(8,2));

        var d = new persianDate([year, month, day, 0, 0, 0, 0]).toDate();
        return d.getTime();
    }
    function ajaxSubmit() {
        var x = new Object();
        x.code = $("#page-wrapper").find("select[name='rawCode']").val();
        x.date = getDate();
        x.batchNumber = $("#page-wrapper").find("input[name='batchNumber']").val();
        x.loadNumber = $("#page-wrapper").find("input[name='loadNumber']").val();
        x.total = $("#page-wrapper").find("input[name='total']").val();
        $.ajax("/document/rawmaterial/entery.do", {
            type: "POST",
            data: {data:JSON.stringify(x)}
        }).done(function () {
            alertify.success("اطلاعات ثبت شد");
            loadContent("/document/rawMaterialEntery.jsp", false);
        }).fail(function (data) {
            errors(data);
        });
    }
</script>
