<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 10/1/2016
  Time: 1:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="row">
    <div class="col-md-12 col-sm-12">
        <h1 class="page-header rtl">دریافت برنامه روزانه</h1>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-3">
                        <p>تاریخ</p>
                        <input name="date" class="form-control"/>
                    </div>
                    <div class="col-md-3">
                        <p>شیفت</p>
                        <select name="shift" class="form-control" onchange="getSections()"></select>
                    </div>
                    <div class="col-md-3">
                        <p>انتخاب قسمت</p>
                        <select name="section" class="form-control"></select>
                    </div>
                    <div class="col-md-3">
                        <br/>
                        <button class="btn btn-default" onclick="getPlan()" >تایید</button>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <label class="checkbox-inline">
                            نمایش محصول ها
                            <input name="chp" type="checkbox" onchange="check(event)">
                        </label>
                    </div>
                    <div class="col-md-3">
                        <label class="checkbox-inline">
                            نمایش مواد اولیه
                            <input name="chr" type="checkbox" onchange="check(event)">
                        </label>
                    </div>
                    <div class="col-md-3">
                        <label class="checkbox-inline">
                            نمایش جداگانه نیمه ساخته ها
                            <input name="chs" type="checkbox" onchange="check(event)">
                        </label>
                    </div>
                    <div class="col-md-3">
                        <label class="checkbox-inline">
                            نمایش برنامه دستگاه ها
                            <input name="chm" type="checkbox" checked onchange="check(event)">
                        </label>
                    </div>
                </div>
            </div>
            <div class="panel-body">
                <div class="row" hidden>
                    <div class="col-md-12">
                        <table id="productTable" class="table table-bordered table-responsive table-hover" width="100%" cellspacing="0" >
                            <thead>
                            <tr>
                                <th>کد</th>
                                <th>نام</th>
                                <th>مقدار</th>
                                <th>وزن</th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="row" hidden>
                    <div class="col-md-12">
                        <table id="rawTable" class="table table-bordered table-responsive table-hover" width="100%" cellspacing="0" >
                            <thead>
                            <tr>
                                <th>کد</th>
                                <th>نام</th>
                                <th>مقدار</th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="row" hidden>
                    <div class="col-md-12">
                        <table id="semiTable" class="table table-bordered table-responsive table-hover" width="100%" cellspacing="0"  >
                            <thead>
                            <tr>
                                <th>قسمت</th>
                                <th>گروه</th>
                                <th>نیمه ساخته</th>
                                <th>مقدار</th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="row" hidden>
                    <div class="col-md-12">
                        <table id="machineTable" class="table table-bordered table-responsive table-hover" width="100%" cellspacing="0"  >
                            <thead>
                            <tr>
                                <th>شیفت</th>
                                <th>دستگاه</th>
                                <th>نیمه ساخته</th>
                                <th>برنامه</th>
                                <th>عملکرد تولید</th>
                                <th>علت توقف</th>
                                <th>کد</th>
                                <th>شروع</th>
                                <th>پایان</th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
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
        })

        $("#page-wrapper").find("table").DataTable().destroy();

        var option = document.createElement("option");
        $(option).val("");
        $(option).text("لطفا شیفت مورد نظر را انتخاب کنید");
        $("#page-wrapper").find("select[name='shift']").append(option);
        option = document.createElement("option");
        $(option).val("1");
        $(option).text("1");
        $("#page-wrapper").find("select[name='shift']").append(option);
        option = document.createElement("option");
        $(option).val("2");
        $(option).text("2");
        $("#page-wrapper").find("select[name='shift']").append(option);
        option = document.createElement("option");
        $(option).val("3");
        $(option).text("3");
        $("#page-wrapper").find("select[name='shift']").append(option);
        refreshSelect($("#page-wrapper").find("select[name='shift']"), "لطفا شیفت مورد نظر را انتخاب کنید");
    });
    function getSections() {
        $.ajax("/document/planing/sections.do", {
            data:{date:getDate(), shift:$("#page-wrapper").find("select[name='shift']").val()},
            type:"post",
            dataType:"json"
        }).done(function (data) {
            $("#page-wrapper").find("select[name='section']").children().remove()
            var option = document.createElement("option");
            $(option).val("");
            $(option).text("لطفا قسمت مورد نظر را انتخاب کنید");
            $("#page-wrapper").find("select[name='section']").append(option);
            for (let section of data) {
                option = document.createElement("option");
                $(option).val(section);
                $(option).text(section);
                $("#page-wrapper").find("select[name='section']").append(option);
            }
            refreshSelect($("#page-wrapper").find("select[name='section']"), "لطفا قسمت مورد نظر را انتخاب کنید");
        }).fail(function (data) {
            errors(data)
        })
    }
    function getPlan() {
        var x = new Object();
        x.date = getDate();
        x.shift = $("#page-wrapper").find("select[name='shift']").val();
        x.sections = $("#page-wrapper").find("select[name='section']").val();

        $.ajax("/document/planing/plan.do",{
            data:{data:JSON.stringify(x)},
            type:"post",
            dataType:"json"
        }).done(function (data) {
            if (data.raws != undefined){
                try {
                    setRaws(data.raws);
                }catch (err){

                }
            }
            if (data.products != undefined){
                try {
                    setProducts(data.products);
                }catch (err){

                }
            }
            if (data.sections != undefined){
                try {
                    setSemis(data.sections);
                }catch (err){

                }
                try {
                    setMachines(data.sections);
                }catch (err) {

                }
            }
        }).fail(function (data) {
            errors(data)
        })
    }
    function setRaws(data) {
        $("#rawTable").DataTable().destroy();
        $("#rawTable").find("tbody").children().remove();
        for (let raw of data) {
            var tr = document.createElement("tr");

            var td = document.createElement("td");
            $(td).text(raw.code);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).text(raw.name);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).text(raw.total);
            $(tr).append(td);

            $("#rawTable").find("tbody").append(tr);
        }
        createDataTable($("#rawTable"), true, null, null);
        if ($("#page-wrapper").find("input[name='chr']").is(":checked")){
            $("#rawTable").parents("div[class='row']").first().prop("hidden", false);
        }
    }
    function setProducts(data) {
        $("#productTable").DataTable().destroy();
        $("#productTable").find("tbody").children().remove();
        for (let product of data) {
            var tr = document.createElement("tr");

            var td = document.createElement("td");
            $(td).text(product.code);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).text(product.name);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).text(product.total);
            $(tr).append(td);

            document.createElement("td");
            $(td).text(product.weight);
            $(tr).append(td);

            $("#productTable").find("tbody").append(tr);
        }
        createDataTable($("#productTable"));
        if ($("#page-wrapper").find("input[name='chp']").is(":checked")){
            $("#productTable").parents("div[class='row']").first().prop("hidden", false);
        }
    }
    function setMachines(data) {
        $("#machineTable").DataTable().destroy();
        $("#machineTable").find("tbody").children().remove();
        var planingNumber = 123456;
        var formCode = 78945;
        var section2;

        for (let section of data) {
            for (let group of section.groups) {
                for (let machine of group.machines) {
                    for (let semiMachine of machine.semiMachines) {
                        section2 = "( " +  section.code + " : " + section.name + " )";
                        var tr = document.createElement("tr");

                        var td = document.createElement("td");
                        $(td).text($("#page-wrapper").find("select[name='shift']").val());
                        $(tr).append(td);

                        td = document.createElement("td");
                        $(td).text(machine.code + ":" + machine.name);
                        $(tr).append(td);

                        td = document.createElement("td");
                        $(td).text(semiMachine.code + ":" + semiMachine.name);
                        $(tr).append(td);

                        td = document.createElement("td");
                        $(td).text(semiMachine.total);
                        $(tr).append(td);

                        td = document.createElement("td");
                        $(tr).append(td);

                        td = document.createElement("td");
                        $(tr).append(td);

                        td = document.createElement("td");
                        $(tr).append(td);

                        td = document.createElement("td");
                        $(tr).append(td);

                        td = document.createElement("td");
                        $(tr).append(td);

                        $("#machineTable").find("tbody").append(tr);
                    }
                }
            }
        }

        var message = document.createElement("div");
        $(message).append('<div name="top" class="row">\n' +
            '    <div class="col-sm-4">\n' +
            '        <div class="row">\n' +
            '            <div class="col-sm-12">\n' +
            '                <div class="panel panel-default" style="margin-bottom: 5px">\n' +
            '                    <div class="panel-body" style="padding: 5px">\n' +
            '                        <p name="pn">شماره برنامه : </p>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '        <div class="row">\n' +
            '            <div class="col-sm-12">\n' +
            '                <div class="panel panel-default" style="margin-bottom: 5px">\n' +
            '                    <div class="panel-body" style="padding: 5px">\n' +
            '                        <p name="ps">قسمت : </p>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '    </div>\n' +
            '    <div class="col-sm-4">\n' +
            '        <br/>\n' +
            '        <div class="panel panel-default" style="margin-bottom: 5px">\n' +
            '            <div class="panel-body" style="padding: 5px">\n' +
            '                <h4 style="text-align: center">برنامه روزانه تولید</h4>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '    </div>\n' +
            '    <div class="col-sm-4">\n' +
            '        <div class="row">\n' +
            '            <div class="col-sm-12">\n' +
            '                <div class="panel panel-default" style="margin-bottom: 5px">\n' +
            '                    <div class="panel-body" style="padding: 5px">\n' +
            '                        <p name="pcf">کد فرم : </p>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '        <div class="row">\n' +
            '            <div class="col-sm-12">\n' +
            '                <div class="panel panel-default" style="margin-bottom: 5px">\n' +
            '                    <div class="panel-body" style="padding: 5px">\n' +
            '                        <p name="pd">تاریخ : </p>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '    </div>\n' +
            '</div>');
        $(message).append('<div name="bottom" class="row">\n' +
            '    <div class="col-sm-4">\n' +
            '        <p style="text-align: center !important;">امضاء صادر کننده :</p>\n' +
            '    </div>\n' +
            '    <div class="col-sm-4">\n' +
            '\n' +
            '    </div>\n' +
            '    <div class="col-sm-4">\n' +
            '        <p style="text-align: center !important;">امضاء رئیس قسمت : </p>\n' +
            '    </div>\n' +
            '</div>');
        $(message).find("div[name='top']").find("p[name='pn']").text("شماره برنامه : " + planingNumber);
        $(message).find("div[name='top']").find("p[name='ps']").text("قسمت : " + section2);
        $(message).find("div[name='top']").find("p[name='pd']").text("تاریخ : " + $("#page-wrapper").find("input[name='date']").val());
        $(message).find("div[name='top']").find("p[name='pcf']").text("کد فرم : " + formCode);
        var messageTop = document.createElement("div");
        $(messageTop).append($(message).find("div[name='top']"));
        var messageBottom = document.createElement("div");
        $(messageBottom).append($(message).find("div[name='bottom']"));

        createDataTable($("#machineTable"),true, $(messageTop).html(), $(messageBottom).html());

        if ($("#page-wrapper").find("input[name='chm']").is(":checked")){
            $("#machineTable").parents("div[class='row']").first().prop("hidden", false);
        }
    }
    function setSemis(data) {
        $("#semiTable").DataTable().destroy();
        $("#semiTable").find("tbody").children().remove();
        for (let section of data) {
            for (let group of section.groups) {
                for (let semi of group.semis) {
                    var tr = document.createElement("tr");

                    var td = document.createElement("td");
                    $(td).text(section.code + ":" + section.name);
                    $(tr).append(td);

                    td = document.createElement("td");
                    $(td).text(group.code + ":" + group.name);
                    $(tr).append(td);

                    td = document.createElement("td");
                    $(td).text(semi.code + ":" + semi.name);
                    $(tr).append(td);

                    td = document.createElement("td");
                    $(td).text(semi.total);
                    $(tr).append(td);

                    $("#semiTable").find("tbody").append(tr);
                }
            }
        }
        createDataTable($("#semiTable"));
        if ($("#page-wrapper").find("input[name='chs']").is(":checked")){
            $("#semiTable").parents("div[class='row']").first().prop("hidden", false);
        }
    }
    function check(event) {
        var name = $(event.target).attr("name");
        switch (name){
            case "chr":{
                $("#rawTable").parents("div[class='row']").first().prop("hidden", !$(event.target).is(":checked"));
                break;
            }
            case "chp":{
                $("#productTable").parents("div[class='row']").first().prop("hidden", !$(event.target).is(":checked"));
                break;
            }
            case "chs":{
                $("#semiTable").parents("div[class='row']").first().prop("hidden", !$(event.target).is(":checked"));
                break;
            }
            case "chm":{
                $("#machineTable").parents("div[class='row']").first().prop("hidden", !$(event.target).is(":checked"));
                break;
            }
        }
    }

    function getDate() {
        var year = parseInt($("#page-wrapper").find("input[name='date']").val().substr(0,4));
        var month = parseInt($("#page-wrapper").find("input[name='date']").val().substr(5,2));
        var day = parseInt($("#page-wrapper").find("input[name='date']").val().substr(8,2));

        var d = new persianDate([year, month, day, 0, 0, 0, 0]).toDate();
        return d.getTime();
    }

</script>
