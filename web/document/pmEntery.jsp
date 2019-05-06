<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 2/7/17
  Time: 4:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="col-md-12">
        <h2 class="page-title">ثبت pm دستگاه ها</h2>
    </div>
</div>
<br/>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-4">
                        <p>کد دستگاه</p>
                        <select id="code" name="code" class="form-control" onchange="dataSet(event)"></select>
                    </div>
                    <div class="col-md-4">
                        <p>نام دستگاه</p>
                        <select id="name" name="name" class="form-control" onchange="dataSet(event)"></select>
                    </div>
                </div>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-12">
                        <table id="table" class="table table-bordered table-responsive table-striped table-hover" style="table-layout: fixed" width="100%" cellspacing="2px">
                            <thead>
                            <tr>
                                <th>روزهای هفته</th>
                                <th></th>
                                <th></th>
                                <th></th>
                                <th></th>
                                <th></th>
                                <th></th>
                                <th></th>
                                <th></th>
                                <th></th>
                                <th></th>
                                <th></th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="panel-footer">
                <div class="row">
                    <div class="col-md-3">
                        <button id="button" type="button" class="btn btn-default btn-outline disabled">تایید</button>
                    </div>
                </div>
            </div>
        </div>
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
        $.ajax("/document/pm/data.do", {
            type:"POST",
            dataType:"JSON"
        }).done(function (data) {
            $("#code").children().remove();
            $("#name").children().remove();

            for(var i = 0; i< data.length; i++){
                var option = document.createElement("option");
                $(option).val(data[i].code);
                $(option).text(data[i].code);
                $("#code").append(option);

                option = document.createElement("option");
                $(option).val(data[i].code);
                $(option).text(data[i].name);
                $("#name").append(option);
            }

            $("#button").removeClass("disabled");
            $("#button").on("click", function () {
                submitAjax();
            });
        }).fail(function (data) {
            $("#button").addClass("disabled");
            errors(data);
        });
    }
    function dataSet(event) {
        var data = $(event.target).val();
        $(event.target).parent().parent().children().find("#code").val(data);
        $(event.target).parent().parent().children().find("#name").val(data);
    }
    function submitAjax() {
        $.ajax("/document/pm/add.do", {
            data:$("#form").serialize(),
            type:"POST"
        }).done(function () {
            $("#form").find(":input").val("");
            alertify.success(" pm ثبت شد");
        }).fail(function (data) {
            errors(data)
        })
    }


    var workingDates = [], offDates = [], editedDates = [];
    var edFull = [];
    var tempElement;
    var wdC = true, edC = false, odC = false;
    $(function () {
        $("#table").DataTable().destroy();
        $("#page-wrapper").find("input[name='start']").pDatepicker({
            format:"YYYY/MM/DD",
            navigator:{
                text:{
                    btnNextText:'>',
                    btnPrevText:'<'
                }
            },
            toolbox:{
                enabled:false
            }
        });
        $("#page-wrapper").find("input[name='end']").pDatepicker({
            format:"YYYY/MM/DD",
            navigator:{
                text:{
                    btnNextText:'>',
                    btnPrevText:'<'
                }
            },
            toolbox:{
                enabled:false
            }
        });
    });

    function setDates() {
        try {
            $("#table").DataTable().destroy();
        }catch (err){

        }
        try {
            $("#table").find("tbody").children().remove();
            workingDates = [];
            offDates = [];
            editedDates = [];
            edFull = [];
        }catch (err){

        }
        var min = getDate($("#page-wrapper").find("input[name='start']"));
        var max = getDate($("#page-wrapper").find("input[name='end']"));
        $.ajax("/document/workingCalendar/get.do",{
            data:{begin:min, end:max},
            type:"post",
            dataType:"json"
        }).done(function (data) {
            if (data.wd != undefined && data.wd.length > 0){
                workingDates = data.wd;
            }
            if (data.ed != undefined && data.ed.length > 0){
                var eds = data.ed;
                for (let obj of eds) {
                    editedDates.push(obj.unix);
                    edFull[obj.unix] = obj;
                }
            }
            afterAjax(min , max);
            $("#page-wrapper").find("div[class='panel-body']").prop("hidden", false);
            $("#page-wrapper").find("div[class='panel-footer']").prop("hidden", false);
        }).fail(function (jqXHR) {
            errors(jqXHR);
        });

    }
    function afterAjax(min, max) {
        setMonths(min);
        setRecords();
        setWeekDays();

        var dayMiliseconds = 24 *60 *60 *1000;
        var pd = new pDate(min).toArray();
        var temp = 0, offset = pd[3] - 1;
        var wdCheck = false, edCheck = false;
        if (workingDates.length > 0){
            wdCheck = true
        }
        if (editedDates.length > 0){
            edCheck = true;
        }
        var begin = new Date(min);
        var end = new Date(max);
        while(begin <= end){
            pd = new pDate(begin.valueOf()).toArray();

            var ths = $("#table").find("thead").find("tr").children();
            for(var i = 0; i < ths.length; i++){
                if ($(ths[i]).data("data") == pd[1]){
                    var trs = $("#table").find("tbody").find("tr");
                    if (temp != pd[1]){
                        if (pd[3] == 0){
                            offset = 6;
                        }else {
                            offset = pd[3] - 1;
                        }
                        temp = pd[1];
                    }
                    var color = "#000000";
                    var tds = $(trs[(pd[2] - 1) + offset]).children();
                    $(tds[i]).text(pd[2]);
                    $(tds[i]).data("unix", begin.valueOf());
                    if (wdCheck || edCheck){
                        if (workingDates.indexOf(begin.valueOf()) != -1){
                            $(tds[i]).data("k", "wd");
                        }else if (editedDates.indexOf(begin.valueOf()) != -1){
                            color = "#24aed9";
                            $(tds[i]).data("k", "ed");
                        }else{
                            color = "#c50000";
                            offDates.push(begin.valueOf());
                            $(tds[i]).data("k", "od");
                        }
                    }else {
                        if (pd[3] == 0){
                            color = "#c50000";
                            offDates.push(begin.valueOf());
                            $(tds[i]).data("k", "od");
                        }else{
                            workingDates.push(begin.valueOf());
                            $(tds[i]).data("k", "wd");
                        }
                    }
                    $(tds[i]).css("color", color);
                }
            }

            begin.setDate(begin.getDate() + 1);
            begin.setHours(0);
            begin.setMinutes(0);
            begin.setSeconds(0);
            begin.setMilliseconds(0);
        }
        getTotal(wdC, edC, odC);

        $('#table').find("tbody").contextMenu({
            selector: 'td',
            callback: function(key, options) {
                switch (key){
                    case "wd":{
                        var kind = $(this).data("k");
                        if (kind != undefined && kind != null){
                            if (kind == "od"){
                                offDates.splice(offDates.indexOf($(this).data("unix")), 1);
                            }else if (kind == "ed"){
                                editedDates.splice(editedDates.indexOf($(this).data("unix")), 1);
                                edFull.splice(edFull[$(this).data("unix")], 1);
                            }
                            workingDates.push($(this).data("unix"));
                            $(this).css("color", "#000000");
                            $(this).data("k", "wd");
                            getTotal(wdC, edC, odC);
                        }
                        break;
                    }
                    case "od":{
                        var kind = $(this).data("k");
                        if (kind != undefined && kind != null){
                            if (kind == "wd"){
                                workingDates.splice(workingDates.indexOf($(this).data("unix")), 1);
                            }else if (kind == "ed"){
                                editedDates.splice(editedDates.indexOf($(this).data("unix")), 1);
                                edFull.splice(edFull[$(this).data("unix")], 1);
                            }
                            offDates.push($(this).data("unix"));
                            $(this).css("color", "#c50000");
                            $(this).data("k", "od");
                            getTotal(wdC, edC, odC);
                        }
                        break;
                    }
                    case "ed":{
                        var kind = $(this).data("k");
                        if (kind != undefined && kind != null){
                            tempElement = this;
                            newModal("editModal");
                            $("#editModal").modal("show");
                        }
                        break;
                    }
                }
            },
            items: {
                "wd": {name: "روز کاری", icon: "add"},
                "od": {name: "روز تعطیل", icon: function($element, key, item){ return 'context-menu-icon context-menu-icon-quit'; }},
                "ed": {name: "اصلاح", icon: "edit"}
            }
        });
    }

    function setMonths(min) {
        var pd = new pDate(min).toArray();
        var temp = pd[1];

        var ths = $("#table").find("thead").find("tr").children();
        for (var i = 1; i < ths.length; i++){
            var obj = ths[i];
            $(obj).text(getMonthName(temp));
            $(obj).data("data", temp);
            if (temp == 12){
                temp = 0;
            }
            temp++;
        }
    }
    function setWeekDays() {
        var i = 0;
        var trs = $("#table").find("tbody").find("tr");
        for(var j = 0; j < trs.length - 1; j++){
            let obj = trs[j];
            var tds = $(obj).children();
            $(tds[0]).text(getWeekDay(i));
            if (i == 6){
                i = 0;
            }else{
                i++;
            }
        }
        let obj = trs[trs.length - 1];
        var tds = $(obj).children();
        $(tds[0]).text("جمع");
    }
    function setRecords() {
        for(var i = 0; i < 38; i++){
            var tr = document.createElement("tr");

            var td = document.createElement("td");
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

            td = document.createElement("td");
            $(tr).append(td);

            td = document.createElement("td");
            $(tr).append(td);

            $("#table").find("tbody").append(tr);
        }
    }
    function setCheck(event) {
        var name = $(event.target).attr("name");
        if ($(event.target).is(":checked")){
            switch (name){
                case "wdCheck":{
                    wdC = true;
                    break;
                }
                case "edCheck":{
                    edC = true;
                    break;
                }
                case "odCheck":{
                    odC = true;
                    break;
                }
            }
        }else{
            switch (name){
                case "wdCheck":{
                    wdC = false;
                    break;
                }
                case "edCheck":{
                    edC = false;
                    break;
                }
                case "odCheck":{
                    odC = false;
                    break;
                }
            }
        }
        try {
            getTotal(wdC, edC, odC);
        }catch (err){

        }
    }

    function getDate(element) {
        var year = parseInt($(element).val().substr(0,4));
        var month = parseInt($(element).val().substr(5,2));
        var day = parseInt($(element).val().substr(8,2));

        var d = new persianDate([year, month, day, 0, 0, 0, 0]).toDate();
        return d.getTime();
    }
    function getMonthName(num) {
        switch (num){
            case 1:{
                return "فروردین";
            }
            case 2:{
                return "اردیبهشت";
            }
            case 3:{
                return "خرداد";
            }
            case 4:{
                return "تیر";
            }
            case 5:{
                return "مرداد";
            }
            case 6:{
                return "شهریور";
            }
            case 7:{
                return "مهر";
            }
            case 8:{
                return "آبان";
            }
            case 9:{
                return "آذر";
            }
            case 10:{
                return "دی";
            }
            case 11:{
                return "بهمن";
            }
            case 12:{
                return "اسفند";
            }
        }
    }
    function getWeekDay(num) {
        switch (num){
            case 0:{
                return "شنبه";
            }
            case 1:{
                return "یکشنبه";
            }
            case 2:{
                return "دوشنبه";
            }
            case 3:{
                return "سه شنبه";
            }
            case 4: {
                return "چهارشنبه";
            }
            case 5:{
                return "پنج شنبه";
            }
            case 6:{
                return "جمعه";
            }
        }
    }
    function getTotal(wd, ed, od) {
        for(var j = 1; j <= 12; j++){
            var trs = $("#table").find("tbody").children();
            var total = 0;
            for (var i = 0; i < 37; i++ ){
                var tds = $(trs[i]).children();
                if ($(tds[j]).data("k") != undefined && $(tds[j]).data("k") != null){
                    if(wd && $(tds[j]).data("k") == "wd"){
                        total++;
                    }
                    if(od && $(tds[j]).data("k") == "od"){
                        total++;
                    }
                    if(ed && $(tds[j]).data("k") == "ed"){
                        total++;
                    }
                }
            }
            var tds = $(trs[37]).children();
            $(tds[j]).text(total);
        }
    }

    function acceptEditModal() {
        var unix = $(tempElement).data("unix");
        var reason = $("#editModal").find("input[name='reason']").val();
        var des = $("#editModal").find("textarea[name='des']").val();
        var x = new Object();
        x.unix = unix;
        x.reason = reason;
        x.des = des;
        edFull.push(x);

        var kind = $(tempElement).data("k");
        if (kind == "wd"){
            workingDates.splice(workingDates.indexOf($(tempElement).data("unix")), 1);
        }else if (kind == "ed"){
            offDates.splice(offDates.indexOf($(tempElement).data("unix")), 1);
        }
        editedDates.push($(tempElement).data("unix"));
        $(tempElement).css("color", "#24aed9");
        $(tempElement).data("k", "ed");
        getTotal(wdC, edC, odC);

        removeModal();
    }
    function newModal(a) {
        var selects = $("#" + a).find('select');
        for (var i = 0; i < selects.length; i++){
            $(selects[i]).val("");
        }
        var textarea = $("#" + a).find('textarea');
        for (var i = 0; i < textarea.length; i++){
            $(textarea[i]).val("");
        }

        try{
            $("#" + a).data("data", null);
        }catch (err){

        }
        addModal(a);
    }

    function submitBtn() {
        var data = new Object();
        data.wd = workingDates;
        data.ed = edFull;
        var begin = getDate($("#page-wrapper").find("input[name='start']"));
        var end = getDate($("#page-wrapper").find("input[name='end']"));

        $.ajax("/document/workingCalendar/register.do",{
            data:{dates:JSON.stringify(data), begin:begin, end:end, shift:$("#page-wrapper").find("input[name='shift']").val()},
            type:"post"
        }).done(function (data) {
            alertify.success("تقویم کاری ثبت شد");
            loadContent("/document/workingCalendar.jsp", false);
        }).fail(function (jqXHR) {
            errors(jqXHR);
        })
    }
</script>
