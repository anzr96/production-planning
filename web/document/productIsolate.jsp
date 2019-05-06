<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 2/8/17
  Time: 6:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="col-md-12">
        <form id="form">
            <div class="panel panel-warning">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-md-12">
                            <h3 class="page-title">ایزوله کردن محصول</h3>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-3">
                            <p>کد محصول</p>
                            <select name="code" class="form-control" onchange="dataSet(event)"></select>
                        </div>
                        <div class="col-md-3">
                            <p>نام محصول</p>
                            <select name="name" class="form-control" onchange="dataSet(event)"></select>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <p>تاریخ</p>
                            <input name="date" class="form-control"/>
                        </div>
                        <div class="col-md-3">
                            <p>شیفت</p>
                            <input name="shift" class="form-control"/>
                        </div>
                        <div class="col-md-3">
                            <p>شماره بچ</p>
                            <input name="batchNumber" class="form-control"/>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-3">
                            <p>تعداد</p>
                            <input name="total" class="form-control"/>
                        </div>
                        <div class="col-md-3">
                            <label class="control-label">دلیل ایزوله شدن</label>
                            <select name="reason" class="form-control">
                                <option value="">دلیل ایزوله</option>
                                <option value="برگشتی">برگشتی</option>
                                <option value="ضایعات">ضایعات</option>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <p>توضیحات</p>
                            <textarea name="des" class="form-control" maxlength="255"></textarea>
                        </div>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col-md-3">
                            <button type="button" class="btn btn-default" onclick="ajaxSubmit()">تایید</button>
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
        $.ajax("/document/workingCalendar/get.do", {
            type:"GET",
            dataType:"json"
        }).done(function (data) {
            var dates = data.wd;
            var eds = data.ed;
            for (let obj of eds) {
                dates.push(obj.unix);
            }
            $("#datepicker").pDatepicker({
                format:"YYYY/MM/DD",
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
                }
            })
        }).fail(function( jqXHR ) {
            errors(jqXHR);
        });
    });
    function ajaxLoad() {
        $.ajax("/document/product/ajaxLoad.do", {
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
                $(option).text(data[i].size + " " +  data[i].design + " " +  data[i].pr);
                $("#name").append(option);
            }
        }).fail(function (data) {
            errors(data)
        });
    }
    function dataSet(event) {
        $("#code").val($(event.target).val());
        $("#name").val($(event.target).val());
    }
    function getDate(element) {
        var year = parseInt($(element).val().substr(0,4));
        var month = parseInt($(element).val().substr(5,2));
        var day = parseInt($(element).val().substr(8,2));

        var d = new persianDate([year, month, day, 0, 0, 0, 0]).toDate();
        return d.getTime();
    }
    function ajaxSubmit() {
        var x = new Object();
        x.code = $("#page-wrapper").find("select[name='code']").val();
        x.date = getDate($("#page-wrapper").find("input[name='date']"));
        x.batch = $("#page-wrapper").find("input[name='batchNumber']").val();
        x.shift = $("#page-wrapper").find("input[name='shift']").val();
        x.total = $("#page-wrapper").find("input[name='total']").val();
        x.reason = $("#page-wrapper").find("select[name='reason']").val();
        x.des = $("#page-wrapper").find("textarea[name='des']").val();

        $.ajax("/document/product/isolate.do", {
            data:{data:JSON.stringify(x)},
            type:"POST"
        }).done(function () {
            alertify.success("اطلاعات ثبت شد");
            loadContent("productIsolate.jsp", false);
        }).fail(function (data) {
            errors(data);
        })
    }
</script>

