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
        <div class="page-header">
            <h1>نمایش محصولات</h1>
        </div>
    </div>
</div>
<br/>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-4">
                        <p>انتخاب جدول مورد نظر</p>
                        <select id="kind" class="form-control" name="kind" onchange="setKind(event)">
                            <option value="" selected>جدول مورد نظر را انتخاب کنید</option>
                            <option value="stock">موجودی</option>
                            <option value="in">تحویل به انبار</option>
                            <option value="out">خروج از انبار</option>
                            <option value="isolate">ایزوله ها</option>
                        </select>
                    </div>
                    <div class="col-md-3" hidden>
                        <p> تاریخ شروع </p>
                        <input id="start" class="form-control" name="start"/>
                    </div>
                    <div class="col-md-3" hidden>
                        <p> تاریخ پایان </p>
                        <input id="end" class="form-control" name="end"/>
                    </div>
                    <div class="col-md-2" hidden>
                        <br/>
                        <button type="button" class="btn btn-success" onclick="getData()">تایید</button>
                    </div>
                </div>
            </div>
            <div class="panel-body" id="tablePanel" hidden>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $("#start").persianDatepicker({
            format: 'YYYY/MM/DD'
        });
        $("#end").persianDatepicker({
            format: 'YYYY/MM/DD'
        });
    });
    function setKind(event) {
        var kind = $(event.target).val();
        if (kind == "stock"){
            $(event.target).parent().parent().children().prop("hidden", true);
            $(event.target).parent().prop("hidden", false);
            $("#tablePanel").children().remove();
            $("#tablePanel").prop("hidden", false);

            var table = document.createElement("table");
            $(table).addClass("table table-responsive table-striped table-bordered table-hover");
            $(table).attr("id", "table");
            $(table).attr("cellspacing", 0);
            $(table).attr("width", "100%");

            var thead = document.createElement("thead");

            var tr = document.createElement("tr");

            var th = document.createElement("th");
            $(th).text("کد محصول");
            $(tr).append(th);

            th = document.createElement("th");
            $(th).text("سایز محصول");
            $(tr).append(th);

            th = document.createElement("th");
            $(th).text("طرح محصول");
            $(tr).append(th);

            th = document.createElement("th");
            $(th).text("pr محصول");
            $(tr).append(th);

            th = document.createElement("th");
            $(th).text("موجودی (تعداد)");
            $(tr).append(th);

            th = document.createElement("th");
            $(th).text("موجودی (وزن)");
            $(tr).append(th);

            $(thead).append(tr);
            $(table).append(thead);

            var tbody = document.createElement("tbody");
            $(table).append(tbody);

            $("#tablePanel").append(table);

            $.ajax("/document/product/getData.do",{
                data:{kind:$("#kind").val()},
                dataType:"json",
                type:"post"
            }).done(function (data) {
                $("#table").find("tbody").children().remove();
                for (let obj of data) {
                    var tr = document.createElement("tr");

                    var td = document.createElement("td");
                    $(td).text(obj.code);
                    $(tr).append(td);

                    td = document.createElement("td");
                    $(td).text(obj.size);
                    $(tr).append(td);

                    td = document.createElement("td");
                    $(td).text(obj.design);
                    $(tr).append(td);

                    td = document.createElement("td");
                    $(td).text(obj.pr);
                    $(tr).append(td);

                    td = document.createElement("td");
                    $(td).text(obj.total);
                    $(tr).append(td);

                    td = document.createElement("td");
                    $(td).text(obj.tweight);
                    $(tr).append(td);

                    $("#table").find("tbody").append(tr);
                }
                createDataTable($("#table"));
            }).fail(function (data) {
                errors(data);
            })
        }else if (kind == "in"){
            $(event.target).parent().parent().children().prop("hidden", false);

        }else if (kind == "out"){
            $(event.target).parent().parent().children().prop("hidden", false);
        }else if (kind == "isolate"){
            $(event.target).parent().parent().children().prop("hidden", false);
        }
    }

    function getData() {
        var yearS = $("#start").val().substr(0,4);
        var monthS = $("#start").val().substr(5,2);
        var dayS = $("#start").val().substr(8,2);
        var yearE = $("#end").val().substr(0,4);
        var monthE = $("#end").val().substr(5,2);
        var dayE = $("#end").val().substr(8,2);

        var miliS = persianDate([yearS, monthS, dayS]).millisecond();
        var miliE = persianDate([yearE, monthE, dayE]).millisecond();

        var kind = $("#kind").val();
        $.ajax("/document/product/")
    }
</script>
