<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 10/1/2016
  ProductInTime: 3:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="col-md-12">
        <div class="page-header">
            <h1>نمایش موجودی مواد اولیه و اطلاعات تکمیلی آنها</h1>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-4">
                        <p>انتخاب جدول مورد نظر</p>
                        <select class="form-control" name="kind" onchange="setKind(event)">
                            <option value="" selected>جدول مورد نظر را انتخاب کنید</option>
                            <option value="stock">موجودی</option>
                            <option value="in">تحویل به انبار</option>
                            <option value="out">خروج از انبار</option>
                        </select>
                    </div>
                    <div class="col-md-4" hidden>
                        <p> تاریخ شروع </p>
                        <input class="form-control" name="start" disabled onchange="getSemis(event)"/>
                    </div>
                    <div class="col-md-4" hidden>
                        <p> تاریخ پایان </p>
                        <input class="form-control" name="end" disabled onchange="getSemis(event)"/>
                    </div>
                </div>
            </div>
            <div class="panel-body" id="tablePanel" hidden>
                <table id="table" class="" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th>کد محصول</th>
                        <th>سایز محصول</th>
                        <th>طرح محصول</th>
                        <th>pr محصول</th>
                        <th> موجودی (تعداد)</th>
                        <th> موجودی (وزن)</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script>
    function setKind(event) {
        var kind = $(event.target).val();
        if (kind == "stock"){
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
            $(th).text("کد ماده اولیه");
            $(tr).append(th);

            th = document.createElement("th");
            $(th).text("نام ماده اولیه");
            $(tr).append(th);

            th = document.createElement("th");
            $(th).text("موجودی");
            $(tr).append(th);

            $(thead).append(tr);
            $(table).append(thead);

            var tbody = document.createElement("tbody");
            $(table).append(tbody);

            $("#tablePanel").append(table);

            $.ajax("/document/rawmaterial/stock.do",{
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
                    $(td).text(obj.name);
                    $(tr).append(td);

                    td = document.createElement("td");
                    $(td).text(obj.total);
                    if (obj.color !== undefined){
                        $(td).css("color", obj.color);
                    }
                    $(tr).append(td);

                    $("#table").find("tbody").append(tr);
                }
                createDataTable($("#table"));
            }).fail(function (data) {
                errors(data);
            })
        }else if (kind == "in"){

        }else if (kind == "out"){

        }
    }
</script>

