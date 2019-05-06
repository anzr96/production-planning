<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 1/30/17
  Time: 11:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col-md-12">
        <form id="form">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">وارد کردن بودجه</h4>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-3">
                            <p>سال</p>
                            <input id="datepicker" class="form-control" placeholder="yyyy" onchange="getBudget()"/>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-3">
                            <p>بودجه(ریالی)</p>
                            <input id="price" name="price" class="form-control" placeholder="ریال" onkeyup="priceChange(event)"/>
                        </div>
                        <div class="col-md-3">
                            <p>بودجه(وزنی)</p>
                            <input id="weight" type="number" name="weight" class="form-control" placeholder="kg"/>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-12">
                            <table id="table" width="100%" class="table table-striped table-bordered table-hover text-center" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th rowspan="2"></th>
                                        <th colspan="2">مشخصات محصول</th>
                                        <th colspan="2">تبدیل ها</th>
                                        <th colspan="3">آذر</th>
                                        <th colspan="3">دی</th>
                                        <th colspan="3">بهمن</th>
                                        <th colspan="3">اسفند</th>
                                        <th colspan="3">فروردین</th>
                                        <th colspan="3">اردیبهشت</th>
                                        <th colspan="3">خرداد</th>
                                        <th colspan="3">تیر</th>
                                        <th colspan="3">مرداد</th>
                                        <th colspan="3">شهریور</th>
                                        <th colspan="3">مهر</th>
                                        <th colspan="3">آبان</th>
                                        <th colspan="3">جمع</th>
                                    </tr>
                                    <tr>
                                        <th>کد محصول</th>
                                        <th>نام محصول</th>
                                        <th>وزن</th>
                                        <th>قیمت</th>
                                        <th>تعداد</th>
                                        <th>وزن</th>
                                        <th>قیمت</th>
                                        <th>تعداد</th>
                                        <th>وزن</th>
                                        <th>قیمت</th>
                                        <th>تعداد</th>
                                        <th>وزن</th>
                                        <th>قیمت</th>
                                        <th>تعداد</th>
                                        <th>وزن</th>
                                        <th>قیمت</th>
                                        <th>تعداد</th>
                                        <th>وزن</th>
                                        <th>قیمت</th>
                                        <th>تعداد</th>
                                        <th>وزن</th>
                                        <th>قیمت</th>
                                        <th>تعداد</th>
                                        <th>وزن</th>
                                        <th>قیمت</th>
                                        <th>تعداد</th>
                                        <th>وزن</th>
                                        <th>قیمت</th>
                                        <th>تعداد</th>
                                        <th>وزن</th>
                                        <th>قیمت</th>
                                        <th>تعداد</th>
                                        <th>وزن</th>
                                        <th>قیمت</th>
                                        <th>تعداد</th>
                                        <th>وزن</th>
                                        <th>قیمت</th>
                                        <th>تعداد</th>
                                        <th>وزن</th>
                                        <th>قیمت</th>
                                        <th>تعداد</th>
                                        <th>وزن</th>
                                        <th>قیمت</th>
                                    </tr>
                                </thead>
                                <tbody id="tbody">
                                    <tr>
                                        <td>
                                            <a value="Delete" onclick="deleteRow(event)" style="cursor: pointer"><i class="fa fa-trash-o fa-fw"></i></a>
                                            <br />
                                            <br />
                                            <a value="Add" onclick="addRow(event)" style="cursor: pointer"><i class="fa fa-plus fa-fw"></i></a>
                                        </td>
                                        <td>
                                            <select name="code" class="form-control" onchange="dataSet(event)">
                                                <option value="">کد محصول را انتخاب کنید</option>
                                            </select>
                                        </td>
                                        <td>
                                            <select name="name" class="form-control" onchange="dataSet(event)">
                                                <option value="">نام محصول را انتخاب کنید</option>
                                            </select>
                                        </td>
                                        <td>
                                            <input name="wp" value="0" class="form-control input-sm" type="number"/>
                                        </td>
                                        <td>
                                            <input name="cp" value="0" class="form-control input-sm" type="number"/>
                                        </td>
                                        <td>
                                            <input name="t" index="0" class="form-control input-sm" type="number" onchange="setWeightCost(event)"/>
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>
                                            <input name="t" index="1" class="form-control input-sm" type="number" onchange="setWeightCost(event)"/>
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>
                                            <input name="t" index="2" class="form-control input-sm" type="number" onchange="setWeightCost(event)"/>
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>
                                             <input name="t" index="3" class="form-control input-sm" type="number" onchange="setWeightCost(event)"/>
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>
                                             <input name="t" index="4" class="form-control input-sm" type="number" onchange="setWeightCost(event)"/>
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>
                                             <input name="t" index="5" class="form-control input-sm" type="number" onchange="setWeightCost(event)"/>
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>
                                             <input name="t" index="6" class="form-control input-sm" type="number" onchange="setWeightCost(event)"/>
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>
                                             <input name="t" index="7" class="form-control input-sm" type="number" onchange="setWeightCost(event)"/>
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>
                                             <input name="t" index="8" class="form-control input-sm" type="number" onchange="setWeightCost(event)"/>
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>
                                             <input name="t" index="9" class="form-control input-sm" type="number" onchange="setWeightCost(event)"/>
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>
                                             <input name="t" index="10" class="form-control input-sm" type="number" onchange="setWeightCost(event)"/>
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>
                                             <input name="t" index="11" class="form-control input-sm" type="number" onchange="setWeightCost(event)"/>
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>0
                                            
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>
                                            0
                                        </td>
                                        <td>
                                            0
                                        </td>
                                    </tr>
                                    <tr name="sum">
                                        <td>-</td>
                                        <td>-</td>
                                        <td>-</td>
                                        <td>-</td>
                                        <td>جمع کل</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-3">
                            <button class="btn btn-default btn-outline" onclick="ajaxSubmit()">تایید</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    var d, equal = true;
    $(function () {
        try {
            ajaxLoad();
        }catch (err){
            console.log(err)
        }

        try {
            year();
        }catch (err){
            console.log(err)
        }

    });
    function year() {
        $.ajax("/document/budget/year.do",{
            type:"GET",
            dataType:"JSON"
        }).done(function (data) {
            var array = [];
            for(var i = 0; i < data.length; i++){
                if (data[i].date != null){
                    var pdy = new persianDate(data[i].date).year() + 1;
                    array.push(pdy);
                }
            }
            $("#datepicker").pDatepicker({
                initialValue:false,
                format: "YYYY",
                autoclose: true,
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
                checkYear: function (year) {
                    if (array.indexOf(year) == -1){
                        return false;
                    }
                    return true;
                }
            });
        }).fail(function (data) {
            errors(data);
        });
    }
    function priceChange(event) {
        var str = $(event.target).val();
        var number = "";
        for (var i = 0; i < str.length; i++){
            if ($.isNumeric(str[i])){
                number += str[i];
            }
            var n = parseInt(number);
            if (n == 0){
                number = "";
            }
        }
        $(event.target).val(commafy(number));
    }
    function ajaxLoad() {
        $.ajax("/document/product/ajaxLoad.do",{
            type:"GET",
            dataType:"JSON"
        }).done(function (data) {
            d = data;

            var code = $($("#table").find("tbody").children()[1]).find("select[name='code']");
            $(code).children().remove();
            var name = $($("#table").find("tbody").children()[1]).find("select[name='name']");
            $(name).children().remove();
            for (var i = 0; i < data.length; i++){
                var option = document.createElement("option");
                $(option).val(data[i].code);
                $(option).text(data[i].code);
                $(code).append(option);

                option = document.createElement("option");
                $(option).val(data[i].code);
                $(option).text(data[i].size + data[i].design + data[i].pr);
                $(name).append(option);
            }
            refreshSelect($("#page-wrapper").find("select[name='code']"), "کد محصول را انتخاب کنید");
            refreshSelect($("#page-wrapper").find("select[name='name']"), "نام محصول را انتخاب کنید");

            createDataTable($("#table"), false, messageTop, messageBottom);

        }).fail(function (data) {
            errors(data);
        });
    }
    function setWeightCost(event) {
        var tr = $(event.target).parents("tr");
        $(event.target).parent().next().text(parseInt($(tr).find("input[name='wp']").val() * $(event.target).val()));
        $(event.target).parent().next().next().text(parseInt($(tr).find("input[name='cp']").val() * $(event.target).val()));
        setSumRow(event);
        setSumColumn(event);
        setSum();
        createDataTable($("#table"), false, messageTop, messageBottom);
    }
    function setSumRow(event) {
        var inputs = $(event.target).parents("tr").find("input[name='t']");
        var total = 0;

        for (let input of inputs) {
            if($(input).val() == ""){
                $(input).val(0);
            }
            try {
                total += parseInt($(input).val());
            }catch (err){

            }
        }
        if ($(event.target).parents("tr").find("input[name='wp']").val() == ""){
            $(event.target).parents("tr").find("input[name='wp']").val(0)
        }
        if ($(event.target).parents("tr").find("input[name='cp']").val() == ""){
            $(event.target).parents("tr").find("input[name='cp']").val(0)
        }
        $(event.target).parents("tr").children().last().text(parseInt(total * $(event.target).parents("tr").find("input[name='cp']").val()));
        $(event.target).parents("tr").children().last().prev().text(parseInt(total * $(event.target).parents("tr").find("input[name='wp']").val()));
        $(event.target).parents("tr").children().last().prev().prev().text(parseInt(total));
    }
    function setSumColumn(event) {
        var index = parseInt($(event.target).attr("index"));
        var children = $("#table").find("tbody").children();
        var total = 0, totalWeight = 0, totalCost = 0;
        for ( var i = 0; i < children.length - 1; i++){
            var tr = children[i];
            if($($(tr).children()[5 + index * 3]).find("input[name='t']").val() == ""){
                $($(tr).children()[5 + index * 3]).find("input[name='t']").val(0)
            }
            if($(tr).find("input[name='wp']").val() == ""){
                $(tr).find("input[name='wp']").val(0)
            }
            if($(tr).find("input[name='cp']").val() == ""){
                $(tr).find("input[name='cp']").val(0)
            }
            total += parseInt($($(tr).children()[5 + index * 3]).find("input[name='t']").val());
            totalWeight += parseInt($($(tr).children()[5 + index * 3]).find("input[name='t']").val()) * parseInt($(tr).find("input[name='wp']").val());
            totalCost += parseInt($($(tr).children()[5 + index * 3]).find("input[name='t']").val()) * parseInt($(tr).find("input[name='cp']").val());
        }
        $($("#table").find("tbody").children().last().children()[5 + index * 3]).text(parseInt(total));
        $($("#table").find("tbody").children().last().children()[6 + index * 3]).text(parseInt(totalWeight));
        $($("#table").find("tbody").children().last().children()[7 + index * 3]).text(parseInt(totalCost));
    }
    function setSum() {
        var children = $("#table").find("tbody").children();
        var total = 0, totalWeight = 0, totalCost = 0;
        for ( var i = 0; i < children.length - 1; i++){
            var tr = children[i];
            total += parseInt($(tr).children().last().prev().prev().text());
            totalWeight += parseInt($(tr).children().last().prev().text());
            totalCost += parseInt($(tr).children().last().text());
        }
        $("#table").find("tbody").children().last().children().last().prev().prev().text(parseInt(total));
        $("#table").find("tbody").children().last().children().last().prev().text(parseInt(totalWeight));
        $("#table").find("tbody").children().last().children().last().text(parseInt(totalCost));
    }
    function getBudget() {
        if ($("#datepicker").val() != null && $("#datepicker").val() != ""){
            var year = parseInt($("#datepicker").val());
            var d = new persianDate([year, 1, 1, 0, 0, 0, 0]).toDate();
            $.ajax("/document/budget/show.do",{
                data:{date:d.getFullYear()},
                type:"POST",
                dataType:"JSON"
            }).done(function (data) {
                $("#price").val(data.price);
                $("#weight").val(data.weight);

                var products = data.data;
                for (let product of products) {
                    addRow();
                    $("#tbody").children().last().find("select[name='code']").val(product.code);
                    refreshSelect($("#tbody").children().last().find("select[name='code']"), codeStmt);
                    $("#tbody").children().last().find("select[name='name']").val(product.code);
                    refreshSelect($("#tbody").children().last().find("select[name='name']"), nameStmt);
                    $("#tbody").children().last().find("input[name='total']").val(product.total);
                    $("#tbody").children().last().find("input[name='price']").val(product.price);
                }
            });
        }
    }
    function addRow(event) {
        $("#page-wrapper").find('table').DataTable().destroy();

        var table = document.getElementById('tbody');
        var rowCount = table.rows.length;
        var row = table.insertRow(rowCount - 1);

        var newRow = row.insertCell(0);
        var i = $(document.createElement("i"));
        $(i).addClass("fa fa-trash-o fa-fw");
        var a = $(document.createElement("a"));
        $(a).css("cursor", "pointer");
        $(a).val("Delete");
        $(a).on("click", function (event) {
            deleteRow(event);
        });
        $(a).append(i);
        $(newRow).append(a);
        $(newRow).append("<br /><br />");

        try {
            $(event.target).parent().remove();

            var i = $(document.createElement("i"));
            $(i).addClass("fa fa-plus fa-fw");
            var a = $(document.createElement("a"));
            $(a).css("cursor", "pointer");
            $(a).val("Add");
            $(a).on("click", function (event) {
                addRow(event);
            });
            $(a).append(i);
            $(newRow).append(a);
        }catch (err){

        }

        var newRow = row.insertCell(1);
        var select = document.createElement("select");
        $(select).addClass("form-control");
        $(select).on("change", function (event) {
            dataSet(event);
        });
        $(select).attr("name","code");
        for (var i = 0; i < d.length; i++){
            var option = document.createElement("option");
            $(option).val(d[i].code);
            $(option).text(d[i].code);
            $(select).append(option);
        }
        $(newRow).append(select);
        refreshSelect(select, "کد محصول را انتخاب کنید");

        var newRow = row.insertCell(2);
        var select = document.createElement("select");
        $(select).addClass("form-control");
        $(select).attr("name","name");
        $(select).on("change", function (event) {
            dataSet(event);
        });
        for (var i = 0; i < d.length; i++){
            option = document.createElement("option");
            $(option).val(d[i].code);
            $(option).text(d[i].size + d[i].design + d[i].pr);
            $(select).append(option);
        }
        $(newRow).append(select);
        refreshSelect(select, "نام محصول را انتخاب کنید");

        var newRow = row.insertCell(3);
        var input = document.createElement("input");
        $(input).addClass("form-control input-sm");
        $(input).attr("type", "number");
        $(input).attr("name", "wp");
        $(input).val(0);
        $(newRow).append(input);

        var newRow = row.insertCell(4);
        var input = document.createElement("input");
        $(input).addClass("form-control input-sm");
        $(input).attr("type", "number");
        $(input).attr("name", "cp");
        $(input).val(0);
        $(newRow).append(input);

        for (var i = 0; i < 12; i++){
            var newRow = row.insertCell(5 + i * 3);
            var input = document.createElement("input");
            $(input).addClass("form-control input-sm");
            $(input).attr("type", "number");
            $(input).attr("name", "t");
            $(input).attr("index", i);
            $(input).val(0);
            $(input).on("change", function (event) {
                setWeightCost(event);
            });
            $(newRow).append(input);

            var newRow = row.insertCell(5 + i * 3 + 1);
            $(newRow).text(0);

            var newRow = row.insertCell(5 + i * 3 + 2);
            $(newRow).text(0);
        }
        var newRow = row.insertCell(41);
        $(newRow).text(0);

        var newRow = row.insertCell(42);
        $(newRow).text(0);

        var newRow = row.insertCell(43);
        $(newRow).text(0);

        createDataTable($("#page-wrapper").find("table"), false, messageTop, messageBottom);
    }
    function deleteRow(event) {
        var table = document.getElementById("table");
        var rowCount = table.rows.length;
        if (rowCount > 2) {
            $("#page-wrapper").find('table').DataTable().destroy();
            if ($(event.target).parent().parent().children().last().val() == "Add"){
                var i = $(document.createElement("i"));
                $(i).addClass("fa fa-plus fa-fw");
                var a = $(document.createElement("a"));
                $(a).css("cursor", "pointer");
                $(a).val("Add");
                $(a).on("click", function (event) {
                    addRow(event);
                });
                $(a).append(i);
                $(event.target).parent().parent().parent().prev().children().first().append(a);
            }
            $(event.target).parent().parent().parent().remove();
            createDataTable($("#page-wrapper").find("table"), false, messageTop, messageBottom);
        }
        else {
            alertify.alert("هشدار", "امکان حذف تمام ردیف ها نمی باشد");
        }
    }
    function dataSet(event) {
        $(event.target).parents("tr").first().find("select[name='code']").val($(event.target).val());
        $(event.target).parents("tr").first().find("select[name='name']").val($(event.target).val());

        refreshSelect($(event.target).parents("tr").first().find("select[name='code']"), "لطفا محصول مورد نظر را انتخاب کنید");
        refreshSelect($(event.target).parents("tr").first().find("select[name='name']"), "لطفا محصول مورد نظر را انتخاب کنید")
    }
    function ajaxSubmit() {
        var selects = $("#page-wrapper").find("select[name='code']");
        var products = [];
        for (let select of selects) {
            var product = new Object();
            product.code = $(select).val();
            product.total = $(select).parents("tr").first().find("input[name='total']").val();
            product.cost = $(select).parents("tr").first().find("input[name='cost']").val();
            products.push(product);
        }

        var object = new Object();
        object.date = new persianDate([$("#datepicker").val(), 1, 1, 0, 0, 0]).toDate().getFullYear();
        object.price = $("#price").val();
        object.weight = $("#weight").val();
        object.equal = equal;
        object.products = products;

        $.ajax("/document/budget/register.do",{
            data:{data:JSON.stringify(object)},
            type:"POST"
        }).done(function () {
            alertify.success("بودجه سال " + $("#datepicker").val() + " ثبت شد");
            loadContent("/document/budgetEntry.jsp", false);
        }).fail(function (data) {
            if (data.status === 400 && data.responseText === "nep"){
                alertify.confirm('تاییدیه بودجه ریالی', 'جمع ریالی محصول ها با بودجه یکسان نمی باشد . در صورتی که مورد تایید نیست لغو را زده و تصحیح کنید در غیر این صورت بودجه با شرایط فعلی ثبت خواهد شد', function(){
                    equal = false;
                    ajaxSubmit();
                    }
                    , function(){ alertify.error('لغو شد')});
            }else if (data.status === 400 && data.responseText === "new"){
                alertify.confirm('تاییدیه بودجه وزنی', 'جمع وزنی محصول ها با بودجه یکسان نمی باشد . در صورتی که مورد تایید نیست لغو را زده و تصحیح کنید در غیر این صورت بودجه با شرایط فعلی ثبت خواهد شد', function(){
                    equal = false;
                    ajaxSubmit();
                    }
                    , function(){ alertify.error('لغو شد')});
            }else {
                errors(data)
            }
        })
    }
</script>
