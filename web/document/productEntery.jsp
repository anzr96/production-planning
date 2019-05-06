<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 10/1/2016
  ProductInTime: 1:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<br/>
<div class="row">
    <div class="col-md-12">
        <form id="form">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">وارد کردن محصول</h3>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-3">
                            <p>کد محصول : </p>
                        </div>
                        <div class="col-md-3">
                            <select id="code" name="productCode" class="form-control" onchange="dataSet(event)">
                                <option value="">کد محصول را انتخاب کنید ...</option>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <p>نام محصول : </p>
                        </div>
                        <div class="col-md-3">
                            <select id="name" class="form-control" onchange="dataSet(event)">
                                <option value="">نام محصول را انتخاب کنید ...</option>
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
                                <input id="date" name="date" class="datepicker" data-bind='datepicker: startDate' />
                            </div>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-3">
                            <p>شماره بچ : </p>
                        </div>
                        <div class="col-md-3">
                            <input id="batchNumber" name="batchNumber" type="text" class="form-control">
                        </div>
                        <div class="col-md-3">
                            <p>درجه محصول : </p>
                        </div>
                        <div class="col-md-3">
                            <input class="form-control" type="number" name="degree" min="1" max="3" placeholder="درجه محصول" required/>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-3">
                            <p>مقدار محصول : </p>
                        </div>
                        <div class="col-md-3">
                            <input class="form-control" type="number" name="total" min="0" placeholder="مقدار محصول" required value="0"/>
                        </div>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col-md-2">
                            <button class="btn btn-default" type="button" onclick="ajaxSubmit()">تایید</button>
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
    function ajaxLoad() {
        $.ajax("/document/product/ajaxLoad.do",{
            type:"GET",
            dataType:"JSON"
        }).done(function (data) {
            $("#code").children().remove();
            $("#name").children().remove();
            for (var i = 0; i < data.length; i++){
                var option = document.createElement("option");
                $(option).val(data[i].code);
                $(option).text(data[i].code);
                $("#code").append(option);

                option = document.createElement("option");
                $(option).val(data[i].code);
                $(option).text(data[i].size + data[i].design + data[i].pr);
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
    function ajaxSubmit() {
        $.ajax("/document/product/entery.do",{
            data:$("#form").serialize(),
            type:"POST"
        }).done(function () {
            $("#form").find("input, select").val("");
            alertify.success("محصول به انبار اضافه شد");
        }).fail(function (data) {
            errors(data);
        })
    }
</script>
