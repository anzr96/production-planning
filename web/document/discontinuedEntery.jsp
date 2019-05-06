<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 10/1/2016
  Time: 1:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col-md-12">
        <form id="form">
            <div class="panel panel-danger">
                <div class="panel-heading">
                    <h4 class="panel-title">ثبت توقف جدید</h4>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-3">
                            <p>گروه توقف را انتخاب کنید : </p>
                        </div>
                        <div class="col-md-3">
                            <select id="groupKind" name="groupKind" class="form-control" onchange="disKind(event)" multiple disabled>
                            </select>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-3">
                            <p>نوع توقف را انتخاب کنید :</p>
                        </div>
                        <div class="col-md-3">
                            <select id="kind" name="kind" class="form-control" multiple disabled>
                            </select>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-3">
                            <p>زمان توقف : </p>
                        </div>
                        <div class="col-md-5">
                            <div class="form-inline">
                                <input id="date" name="date" class="datepicker" data-bind='datepicker: startDate' />
                            </div>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-3">
                            <p>مدت زمان توقف : (دقیقه)</p>
                        </div>
                        <div class="col-md-3">
                            <input type="number" name="disTime" class="form-control" min="0" />
                        </div>
                        <div class="col-md-3">
                            <p>مدت زمان تعمیر : (دقیقه)</p>
                        </div>
                        <div class="col-md-3">
                            <input type="number" name="repTime" class="form-control" min="0" />
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-3">
                            <p>محل توقف : </p>
                        </div>
                        <div class="col-md-3">
                            <select name="place" class="form-control" id="place" multiple>
                            </select>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-3">
                            <p>شرح توقف : </p>
                        </div>
                        <div class="col-md-5">
                                    <textarea rows="5" cols="50" maxlength="400" name="explain">
                                    </textarea>
                        </div>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col-md-3">
                            <button type="button" class="btn btn-danger btn-outline" onclick="ajaxCheck()">تایید</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    $(function () {
        disGroup();
        place();
        date();
    });
    function disGroup() {
        $.ajax("/document/discontinued/disGroup.do",{
            type: "POST",
            dataType: "XML"
        }).done(function (data) {
            var name = data.getElementsByTagName("name");
            if (name != null){
                for (var i = 0; i < name.length; i++){
                    var option = document.createElement("option");
                    $(option).val(name[i].childNodes[0].nodeValue);
                    $(option).text(name[i].childNodes[0].nodeValue);
                    $("#groupKind").append(option);
                }
                $("#groupKind").prop("disabled", false);
            }
            refreshSelect($("#groupKind"), "گروه توقف");

        }).fail(function (data) {
            errors(data);
        });
    }
    function disKind(event) {
        $.ajax("/document/discontinued/disKind.do",{
            data: {data:$(event.target).val()},
            type: "POST",
            dataType: "XML"
        }).done(function (data) {
            var name = data.getElementsByTagName("name");
            if (name != null){
                for (var i = 0; i < name.length; i++){
                    var option = document.createElement("option");
                    $(option).val(name[i].childNodes[0].nodeValue);
                    $(option).text(name[i].childNodes[0].nodeValue);
                    $("#kind").append(option);
                }
                $("#kind").prop("disabled", false);
            }
            refreshSelect($("#kind"), "لطفا نوع توقف را انتخاب کنید");
        }).fail(function (data) {
            errors(data);
        });
    }
    function place() {
        $.ajax("/document/discontinued/disPlace.do",{
            type: "POST",
            dataType: "XML"
        }).done(function (data) {
            var machine = data.getElementsByTagName("machine");
            if (machine != null){
                for (var i = 0; i < machine.length; i++){
                    var option = document.createElement("option");
                    $(option).val(machine[i].childNodes[0].childNodes[0].nodeValue);
                    $(option).text(machine[i].childNodes[1].childNodes[0].nodeValue);
                    $("#place").append(option);
                }
                $("#place").prop("disabled", false);
            }
            refreshSelect($("#place"), "محل مورد نظر را انتخاب کنید");
        }).fail(function (data) {
            errors(data);
        });
    }
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
            $("#date").pDatepicker({
                format:"YYYY/MM/DD HH:mm",
                navigator:{
                    text:{
                        btnNextText:'>',
                        btnPrevText:'<'
                    }
                },
                checkDate:function (unix) {
                    if (dates.indexOf(unix) != -1){
                        return true;
                    }else {
                        return false;
                    }
                },
                timePicker: {
                    enabled: true,
                    showSeconds: false,
                    showMeridian: false,
                    scrollEnabled: true
                }
            })
        }).fail(function( jqXHR ) {
            errors(jqXHR);
        });
    }
    function getDate(element) {
        var year = parseInt($(element).val().substr(0,4));
        var month = parseInt($(element).val().substr(5,2));
        var day = parseInt($(element).val().substr(8,2));
        var hour = parseInt($(element).val().substr(11,2));
        var minute = parseInt($(element).val().substr(14,2));

        var d = new persianDate([year, month, day, hour, minute, 0, 0]).toDate();
        return d.getTime();
    }
    function ajaxCheck() {
        var data = $("#form").serialize();
        $.ajax("/document/discontinued/add.do",{
            data: data,
            type:"POST",
        }).done(function () {
            alertify.success("توقف با موفقیت ثبت شد");
            loadContent("/document/discontinuedEntery.jsp", false);
        }).fail(function (data) {
            errors(data);
        });
    }
</script>

