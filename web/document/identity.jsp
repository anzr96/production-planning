<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 8/30/17
  Time: 10:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="col-md-12">
        <h2 class="page-header">شناسنامه</h2>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-3">
                        <select name="kind" class="form-control" onchange="setKind(event)">
                            <option value="">گزینه مورد نظر را انتخاب کنید</option>
                            <option value="raw">ماده اولیه</option>
                            <option value="product">محصول</option>
                            <option value="semi">نیمه ساخته</option>
                            <option value="machine">دستگاه</option>
                        </select>
                    </div>
                </div>
                <hr/>
                <div class="row" id="kindDiv">

                </div>
            </div>
            <div class="panel-body" id="contentPanel">

            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function setKind(event) {
        $("#contentPanel").children().remove();
        $("#kindDiv").children().remove();
        switch ($(event.target).val()){
            case "raw":{
                try {
                    $("#contentPanel").load("/document/rawMaterialIdentity.jsp", function () {
                        var div = document.createElement("div");
                        $(div).addClass("col-md-3");

                        var p = document.createElement("p");
                        $(p).text("ماده اولیه");
                        $(div).append(p);

                        var select = document.createElement("select");
                        $(select).addClass("form-control");
                        $(select).attr("name", "code");
                        $(select).on("change", function () {
                            loadRaw($(this).val());
                        });
                        $(div).append(select);

                        $.ajax("/document/rawmaterial/ajaxData.do", {
                            type:"get",
                            dataType:"json"
                        }).done(function (data) {
                            for (let obj of data) {
                                var option = document.createElement("option");
                                $(option).val(obj.code);
                                $(option).text(obj.code + " : " + obj.name);
                                $(select).append(option);
                            }
                            refreshSelect(select, "لطفا ماده اولیه مورد نظر را انتخاب کنید");
                        }).fail(function (jqXHR) {
                            errors(jqXHR);
                        });

                        $("#kindDiv").append(div);
                    });
                }catch (err){
                    alertify.error("اختلال در بارگذاری محتوای سایت!")
                }
                break;
            }
            case "product":{
                try {
                    $("#contentPanel").load("/document/productIdentity.jsp", function () {
                        var div = document.createElement("div");
                        $(div).addClass("col-md-3");

                        var p = document.createElement("p");
                        $(p).text("محصول");
                        $(div).append(p);

                        var select = document.createElement("select");
                        $(select).addClass("form-control");
                        $(select).attr("name", "code");
                        $(select).on("change", function () {
                            loadProduct($(this).val());
                        });
                        $(div).append(select);

                        $.ajax("/document/product/data.do", {
                            type:"get",
                            dataType:"json"
                        }).done(function (data) {
                            for (let obj of data) {
                                var option = document.createElement("option");
                                $(option).val(obj.code);
                                $(option).text(obj.code + " : " + obj.name);
                                $(select).append(option);
                            }
                            refreshSelect(select, "لطفا محصول مورد نظر را انتخاب کنید");
                        }).fail(function (jqXHR) {
                            errors(jqXHR);
                        });

                        $("#kindDiv").append(div);
                    });
                }catch (err){
                    alertify.error("اختلال در بارگذاری محتوای سایت!")
                }
                break;
            }
            case "semi":{
                try {
                    $("#contentPanel").load("/document/semistructuredIdentity.jsp", function () {
                        var div = document.createElement("div");
                        $(div).addClass("col-md-3");

                        var p = document.createElement("p");
                        $(p).text("قسمت");
                        $(div).append(p);

                        var sectionSelect = document.createElement("select");
                        $(sectionSelect).addClass("form-control");
                        $(sectionSelect).attr("name", "section");
                        $(sectionSelect).on("change", function () {
                            loadSection($(this).val());
                        });
                        $(div).append(sectionSelect);
                        refreshSelect(sectionSelect, "لطفا قسمت مورد نظر را انتخاب کنید");

                        $("#kindDiv").append(div);

                        div = document.createElement("div");
                        $(div).addClass("col-md-3");

                        p = document.createElement("p");
                        $(p).text("گروه");
                        $(div).append(p);

                        var groupSelect = document.createElement("select");
                        $(groupSelect).addClass("form-control");
                        $(groupSelect).attr("name", "group");
                        $(groupSelect).on("change", function () {
                            loadGroup($(this).val(), "semi");
                        });
                        $(div).append(groupSelect);
                        refreshSelect(groupSelect, "لطفا گروه مورد نظر را انتخاب کنید");

                        $("#kindDiv").append(div);

                        div = document.createElement("div");
                        $(div).addClass("col-md-3");

                        p = document.createElement("p");
                        $(p).text("نیمه ساخته");
                        $(div).append(p);

                        var select = document.createElement("select");
                        $(select).addClass("form-control");
                        $(select).attr("name", "semi");
                        $(select).on("change", function () {
                            loadSemi($(this).val());
                        });
                        $(div).append(select);
                        refreshSelect(select, "لطفا نیمه ساخته مورد نظر را انتخاب کنید");

                        $("#kindDiv").append(div);

                        $.ajax("/document/machine/listSection.do", {
                            type:"get",
                            dataType:"json"
                        }).done(function (data) {
                            for (let obj of data) {
                                var option = document.createElement("option");
                                $(option).val(obj.code);
                                $(option).text(obj.code + " : " + obj.name);
                                $(sectionSelect).append(option);
                            }
                            refreshSelect(sectionSelect, "لطفا قسمت مورد نظر را انتخاب کنید");
                        }).fail(function (jqXHR) {
                            errors(jqXHR);
                        });
                    });
                }catch (err){
                    alertify.error("اختلال در بارگذاری محتوای سایت!")
                }
                break;
            }
            case "machine":{
                try {
                    $("#contentPanel").load("/document/machineIdentity.jsp", function () {
                        var div = document.createElement("div");
                        $(div).addClass("col-md-3");

                        var p = document.createElement("p");
                        $(p).text("قسمت");
                        $(div).append(p);

                        var sectionSelect = document.createElement("select");
                        $(sectionSelect).addClass("form-control");
                        $(sectionSelect).attr("name", "section");
                        $(sectionSelect).on("change", function () {
                            loadSection($(this).val());
                        });
                        $(div).append(sectionSelect);
                        refreshSelect(sectionSelect, "لطفا قسمت مورد نظر را انتخاب کنید");

                        $("#kindDiv").append(div);

                        div = document.createElement("div");
                        $(div).addClass("col-md-3");

                        p = document.createElement("p");
                        $(p).text("گروه");
                        $(div).append(p);

                        var groupSelect = document.createElement("select");
                        $(groupSelect).addClass("form-control");
                        $(groupSelect).attr("name", "group");
                        $(groupSelect).on("change", function () {
                            loadGroup($(this).val(), "machine");
                        });
                        $(div).append(groupSelect);
                        refreshSelect(groupSelect, "لطفا گروه مورد نظر را انتخاب کنید");

                        $("#kindDiv").append(div);

                        div = document.createElement("div");
                        $(div).addClass("col-md-3");

                        p = document.createElement("p");
                        $(p).text("نیمه ساخته");
                        $(div).append(p);

                        var select = document.createElement("select");
                        $(select).addClass("form-control");
                        $(select).attr("name", "machine");
                        $(select).on("change", function () {
                            loadMachine($(this).val());
                        });
                        $(div).append(select);
                        refreshSelect(select, "لطفا دستگاه مورد نظر را انتخاب کنید");

                        $("#kindDiv").append(div);

                        $.ajax("/document/machine/listSection.do", {
                            type:"get",
                            dataType:"json"
                        }).done(function (data) {
                            for (let obj of data) {
                                var option = document.createElement("option");
                                $(option).val(obj.code);
                                $(option).text(obj.code + " : " + obj.name);
                                $(sectionSelect).append(option);
                            }
                            refreshSelect(sectionSelect, "لطفا قسمت مورد نظر را انتخاب کنید");
                        }).fail(function (jqXHR) {
                            errors(jqXHR);
                        });
                    });
                }catch (err){
                    alertify.error("اختلال در بارگذاری محتوای سایت!")
                }
                break;
            }
            default:{
                $(event.target).val("");
            }
        }
    }

    function loadRaw(value) {
        var object = new Object();
        object.code = value;
        $.ajax("/document/rawmaterial/ajaxIdentity.do",{
            data:{data:JSON.stringify(object)},
            type:"post",
            dataType:"json"
        }).done(function (data) {
            $("#code").val(data.code);
            $("#name").val(data.name);
            $("#companyName").val(data.company);
            $("#inout").val(data.inout);
            $("#op").val(data.op);
            try {
                if (data.tlo != undefined){
                    var pd = new persianDate(data.tlo).format("YYYY/MM/DD");
                    $("#tlo").val(pd);
                }else{
                    $("#tlo").val("-");
                }
            }catch (err){
                $("#tlo").val("-");
            }
            $("#total").val(data.total);
            if (data.udfs != undefined){
                for (let udf of data.udfs) {
                    var div = document.createElement("div");
                    $(div).addClass("col-md-3");

                    var p = document.createElement("p");
                    $(p).text(udf.name);
                    $(div).append(p);

                    var input = document.createElement("input");
                    $(input).addClass("form-control");
                    $(input).attr("type", udf.type);
                    $(input).val(udf.value);
                    $(div).append(input);

                    $("#detail").children().last().append(div);
                }
            }
        }).fail(function (jqXHR) {
            errors(jqXHR);
        })
    }
    function loadProduct(value) {
        var object = new Object();
        object.code = value;
        $.ajax("/document/product/ajaxIdentity.do",{
            data:{data:JSON.stringify(object)},
            type:"post",
            dataType:"json"
        }).done(function (data) {
            $("#code").val(data.code);
            $("#size").val(data.size);
            $("#design").val(data.design);
            $("#pr").val(data.pr);

            try{
                $("#rawTable").DataTable().destroy();
            }catch (err){

            }
            for (let raw of data.raws) {
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
            createDataTable($("#rawTable"));

            try{
                $("#semiTable").DataTable().destroy();
            }catch (err){

            }
            for (let semi of data.semis) {
                var tr = document.createElement("tr");

                var td = document.createElement("td");
                $(td).text(semi.sc + " : " +  semi.sn);
                $(tr).append(td);

                td = document.createElement("td");
                $(td).text(semi.gc + " : " +  semi.gn);
                $(tr).append(td);

                td = document.createElement("td");
                $(td).text(semi.oc + " : " +  semi.on);
                $(tr).append(td);

                td = document.createElement("td");
                $(td).text(semi.total);
                $(tr).append(td);

                $("#semiTable").find("tbody").append(tr);
            }
            createDataTable($("#semiTable"));

            if (data.udfs != undefined){
                for (let udf of data.udfs) {
                    var div = document.createElement("div");
                    $(div).addClass("col-md-3");

                    var p = document.createElement("p");
                    $(p).text(udf.name);
                    $(div).append(p);

                    var input = document.createElement("input");
                    $(input).addClass("form-control");
                    $(input).attr("type", udf.type);
                    $(input).val(udf.value);
                    $(div).append(input);

                    $("#home").children().last().append(div);
                }
            }
        }).fail(function (jqXHR) {
            errors(jqXHR);
        });
        setProduct(object);
    }
    function loadSection(value) {
        $.ajax("/document/machine/listGroup.do", {
            data:{code:value},
            type:"post",
            dataType:"json"
        }).done(function (data) {
            $("#kindDiv").find("select[name='group']").children().remove();
            for (let obj of data) {
                var option = document.createElement("option");
                $(option).val(obj.code);
                $(option).text(obj.code + " : " + obj.name);
                $("#kindDiv").find("select[name='group']").append(option);
            }
            refreshSelect($("#kindDiv").find("select[name='group']"), "لطفا گروه مورد نظر را انتخاب کنید");
        }).fail(function (jqXHR) {
            errors(jqXHR);
        })
    }
    function loadGroup(value, kind) {
        switch (kind){
            case "semi":{
                var optgroups = [];

                var section = new Object();
                section.code = $("#kindDiv").find("select[name='section']").val();
                section.options = [];

                var group = new Object();
                group.code = value;
                section.options.push(group);

                optgroups.push(section);
                $.ajax("/document/semistructured/getSemi.do",{
                    data:{groups:JSON.stringify(optgroups)},
                    dataType:"json",
                    type:"post"
                }).done(function (data) {
                    $("#kindDiv").find("select[name='semi']").children().remove();

                    for (let obj of data) {
                        for (let obj2 of obj.groups) {
                            for (let obj3 of obj2.semis) {
                                var option = document.createElement("option");
                                $(option).val(obj3.code);
                                $(option).text(obj3.code + " : " + obj3.name);
                                $("#kindDiv").find("select[name='semi']").append(option);
                            }
                        }
                    }
                    refreshSelect($("#kindDiv").find("select[name='semi']"), "نیمه ساخته مورد نظر را انتخاب کنید");
                }).fail(function (data) {
                    errors(data);
                });
                break;
            }
            case "machine":{
                var optgroups = [];

                var section = new Object();
                section.code = $("#kindDiv").find("select[name='section']").val();
                section.options = [];

                var group = new Object();
                group.code = value;
                section.options.push(group);

                optgroups.push(section);
                $.ajax("/document/machine/listMachines.do",{
                    data:{groups:JSON.stringify(optgroups)},
                    dataType:"json",
                    type:"post"
                }).done(function (data) {
                    $("#kindDiv").find("select[name='machine']").children().remove();

                    for (let obj of data) {
                        for (let obj2 of obj.groups) {
                            for (let obj3 of obj2.machines) {
                                var option = document.createElement("option");
                                $(option).val(obj3.code);
                                $(option).text(obj3.code + " : " + obj3.name);
                                $("#kindDiv").find("select[name='machine']").append(option);
                            }
                        }
                    }
                    refreshSelect($("#kindDiv").find("select[name='machine']"), "دستگاه مورد نظر را انتخاب کنید");
                }).fail(function (data) {
                    errors(data);
                });
                break
            }
        }
//        $.ajax("/document/machine/listGroup.do", {
//            data:{code:value},
//            type:"post",
//            dataType:"json"
//        }).done(function (data) {
//            $("#kindDiv").find("select[name='group']").children().remove();
//            for (let obj of data) {
//                var option = document.createElement("option");
//                $(option).val(obj.code);
//                $(option).text(obj.code + " : " + obj.name);
//                $("#kindDiv").find("select[name='group']").append(option);
//            }
//            refreshSelect($("#kindDiv").find("select[name='group']"), "لطفا گروه مورد نظر را انتخاب کنید");
//        }).fail(function (jqXHR) {
//            errors(jqXHR);
//        })
    }
    function loadSemi(value) {
        var object = new Object();
        object.sc = $("#kindDiv").find("select[name='section']").val();
        object.gc = $("#kindDiv").find("select[name='group']").val();
        object.oc = $("#kindDiv").find("select[name='semi']").val();
        $.ajax("/document/semistructured/ajaxIdentity.do", {
            data:{data:JSON.stringify(object)},
            type:"post",
            dataType:"json"
        }).done(function (data) {
            $("#sc").val(data.sc);
            $("#sn").val(data.sn);
            $("#gc").val(data.gc);
            $("#gn").val(data.gn);
            $("#oc").val(data.oc);
            $("#on").val(data.on);
            $("#unit").val(data.unit);
            $("#ageMin").val(data.ageMin);
            $("#ageMax").val(data.ageMax);
            $("#min").val(data.min);
            $("#max").val(data.max);
            $("#des").val(data.des);
            if (data.udfs != undefined){
                for (let udf of data.udfs) {
                    var div = document.createElement("div");
                    $(div).addClass("col-md-3");

                    var p = document.createElement("p");
                    $(p).text(udf.name);
                    $(div).append(p);

                    var input = document.createElement("input");
                    $(input).addClass("form-control");
                    $(input).attr("type", udf.type);
                    $(input).val(udf.value);
                    $(div).append(input);

                    $("#detail").children().last().append(div);
                }
            }
        }).fail(function (jqXHR) {
            errors(jqXHR);
        })
    }
    function loadMachine(value) {
        var object = new Object();
        object.sc = $("#kindDiv").find("select[name='section']").val();
        object.gc = $("#kindDiv").find("select[name='group']").val();
        object.machine = $("#kindDiv").find("select[name='machine']").val();
        $.ajax("/document/machine/ajaxData.do", {
            data:{data:JSON.stringify(object)},
            type:"post",
            dataType:"json"
        }).done(function (data) {
            try{
                $("#sc").val(data.sc);
                $("#sn").val(data.sn);
                $("#gc").val(data.gc);
                $("#gn").val(data.gn);
                $("#mc").val(data.mc);
                $("#mn").val(data.mn);
                $("#uph").val(data.uph);
                $("#capacity").val(data.capacity);
                var pd = new pDate(data.birthday).toArray();
                $("#birthday").val(pd[0] + "/" + pd[1] + "/"  + pd[2]);
                $("#country").val(data.country);
                $("#des").val(data.des);
                if (data.udfs != undefined){
                    for (let udf of data.udfs) {
                        var div = document.createElement("div");
                        $(div).addClass("col-md-3");

                        var p = document.createElement("p");
                        $(p).text(udf.name);
                        $(div).append(p);

                        var input = document.createElement("input");
                        $(input).addClass("form-control");
                        $(input).attr("type", udf.type);
                        $(input).val(udf.value);
                        $(div).append(input);

                        $("#detail").children().last().append(div);
                    }
                }
            }catch (err){
                console.log(err)
            }
        }).fail(function (jqXHR) {
            errors(jqXHR);
        })
    }
</script>
