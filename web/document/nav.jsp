<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 1/25/17
  Time: 3:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="progress-line" class="progress-line" style="display: none"></div>
<!--Modal-->
<div class="modal fade" id="mailModal" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal" aria-hidden="true" onclick="removeModal()">×</button>
                <h4 class="modal-title">نمایش پیام</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <fieldset>
                            <div class="row">
                                <div class="col-md-6">
                                    <h4>نام فرستنده :</h4>
                                    <input name="sender" type="text" class="form-control"/>
                                </div>
                                <div class="col-md-6">
                                    <h4>موضوع :</h4>
                                    <input name="subject" type="text" class="form-control"/>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
                <hr/>
                <div class="row">
                    <div class="col-md-12">
                        <textarea name="description" class="form-control"></textarea>
                    </div>
                </div>
                <hr/>
                <div class="row" name="attach">

                </div>
                <hr/>
                <div class="row">
                    <div class="col-md-12" name="attachFile">

                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" data-dismiss="modal" onclick="removeModal()">بستن</button>
                <button class="btn btn-primary" onclick="acceptReplyModal()">پاسخ</button>
                <button class="btn btn-primary" onclick="acceptForwardModal()">ارسال</button>
            </div>
        </div>
    </div>
    <!-- /.modal-content -->
</div>
<div class="modal fade" id="newMailModal" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal" aria-hidden="true" onclick="removeModal()">×</button>
                <h4 class="modal-title">پیام جدید</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <fieldset>
                            <div class="row">
                                <div class="col-md-6">
                                    <h4>نام گیرنده :</h4>
                                    <select multiple name="receiver" class="form-control"></select>
                                </div>
                                <div class="col-md-6">
                                    <h4>موضوع :</h4>
                                    <input name="subject" type="text" class="form-control"/>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
                <hr/>
                <div class="row">
                    <div class="col-md-12">
                        <textarea name="description" class="form-control"></textarea>
                    </div>
                </div>
                <hr/>
                <div class="row">
                    <div class="col-md-12">
                        <input id="atf" type="file" name="attachFile"/>
                    </div>
                </div>
                <hr/>
                <div class="row">
                    <div class="col-md-12" name="attach">

                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" data-dismiss="modal" onclick="removeModal()">لغو</button>
                <button class="btn btn-primary" onclick="acceptMailModal()">تایید</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
</div>
<!--Modal-->
<nav class="navbar navbar-default navbar-static-top" role="navigation" >
    <div class="navbar-header">
        <button class="hamburger is-closed animated fadeInRight" data-toggle="offcanvas" onclick="hamburger_cross()">
            <span class="hamb-top"></span>
            <span class="hamb-middle"></span>
            <span class="hamb-bottom"></span>
        </button>
        <a class="navbar-brand" onclick='loadContent("/document/home.jsp", false)'>
            <p>
                لاستیک پارس
                <img width="20" height="20" src="<c:url value="/resources/images/PhotoBox.png"></c:url>"/>
            </p>
        </a>
    </div>
    <!-- /.navbar-header -->
    <ul id="ulnavbar" class="nav navbar-top-links navbar-left" style="direction: rtl">
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#" onclick="seenMail()">
                <i id="totalMail" class="fa" style="color: red" hidden ></i>
                <span>
                    <i class="fa fa-envelope fa-fw"></i>
                    <i class="fa fa-caret-down"></i>
                </span>
            </a>
            <ul class="dropdown-menu dropdown-messages" id="mail">
                <li>
                    <a class="text-center" href="#">
                        <strong>نمایش تمامی پیام ها</strong>
                        <i class="fa fa-angle-left"></i>
                    </a>
                </li>
                <li class="divider"></li>
                <li>
                    <a class="text-center" href="#newMailModal" data-toggle="modal" onclick="openMailModal('newMailModal')">
                        <strong> پیام جدید  </strong>
                        <i class="fa fa-plus"></i>
                    </a>
                </li>
            </ul>
            <!-- /.dropdown-messages -->
        </li>
        <!-- /.dropdown -->
        <li class="dropdown" >
            <a id="notifBell" class="dropdown-toggle" data-toggle="dropdown" style="cursor: pointer" onclick="toggleNotif()">
                <i id="totalNotif" class="fa" style="color: red" hidden></i>
                <span>
                    <i class="fa fa-bell fa-fw"></i>
                    <i class="fa fa-caret-down"></i>
                </span>
            </a>
            <ul class="dropdown-menu dropdown-alerts" id="notif">
                <li>
                    <a class="text-center" onclick='loadContent("/document/notificationView.jsp", false)'>
                        <strong>نمایش تمامی رخدادها</strong>
                        <i class="fa fa-angle-left"></i>
                    </a>
                </li>
            </ul>
            <!-- /.dropdown-alerts -->
        </li>
        <!-- /.dropdown -->
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
                <li><a onclick='loadContent("/document/profile.jsp", false)'>پروفایل <i class="fa fa-user fa-fw"></i></a>
                </li>
                <li><a href="#"> شخصی سازی <i class="glyphicon glyphicon-edit"></i></a>
                </li>
                <li><a onclick='loadContent("/document/createUser.jsp", false)'> کاربر جدید <i class="glyphicon glyphicon-user"></i></a>
                </li>
                <li><a onclick='loadContent("/document/accessEntry.jsp", false)'> ایجاد دسترسی <i class="glyphicon glyphicon-user"></i></a>
                </li>
                <li><a onclick='loadContent("/document/eventAccessEntery.jsp", false)'> ایجاد دسترسی پیام ها <i class="glyphicon glyphicon-user"></i></a>
                </li>
                <li><a onclick='loadContent("/document/roleEntery.jsp", false)'> ایجاد سطح دسترسی <i class="glyphicon glyphicon-user"></i></a>
                </li>
                <li class="divider"></li>
                <li><a href="/logout.jsp" style="cursor: pointer"> خروج <i class="fa fa-sign-out fa-fw"></i></a>
                </li>
            </ul>
            <!-- /.dropdown-user -->
        </li>
        <!-- /.dropdown -->
    </ul>
    <!-- /.navbar-top-links -->

    <div class="overlay"></div>

    <!-- Sidebar -->
    <nav class="navbar navbar-inverse navbar-fixed-top" id="sidebar-wrapper" role="navigation">
        <ul class="nav sidebar-nav">
            <li class="sidebar-brand">
                <a onclick='loadContent("/document/home.jsp")'>
                    لاستیک پارس
                </a>
            </li>
            <li>
                <a onclick='loadContent("/document/workingCalendar.jsp")'><i class="fa fa-calendar"></i>تقویم کاری</a>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-upload fa-fw "></i> ثبت کردن اطلاعات اولیه <span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                    <li class="dropdown-header">ثبت کردن اطلاعات اولیه</li>
                    <li><a onclick='loadContent("/document/rawMaterialRegister.jsp")'>ثبت مواد اولیه</a></li>
                    <li><a onclick='loadContent("/document/sectionRegister.jsp")'>ثبت بخش ها و محصولات</a></li>
                    <li><a onclick='loadContent("/document/processEntery.jsp")'>ثبت فرایند تولید</a></li>
                </ul>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-money fa-fw "></i> بودجه <span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                    <li class="dropdown-header">بودجه</li>
                    <li><a onclick='loadContent("/document/budgetEntry.jsp")'>ثبت بودجه</a></li>
                    <li><a onclick='loadContent("/document/budgetShow.jsp")'>نمایش بودجه</a></li>
                </ul>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-pencil fa-fw "></i> برنامه ریزی تولید <span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                    <li class="dropdown-header">برنامه ریزی تولید</li>
                    <li><a onclick='loadContent("/document/planingEnter.jsp")'>ثبت برنامه تولید روزانه</a></li>
                    <li><a onclick='loadContent("/document/planingShow.jsp")'>نمایش برنامه تولید روزانه</a></li>
                </ul>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-flask fa-fw "></i> مواد اولیه <span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                    <li class="dropdown-header">مواد اولیه</li>
                    <li><a onclick='loadContent("/document/rawMaterialEntery.jsp")'>تحویل به انبار</a></li>
                    <li><a onclick='loadContent("/document/rawMaterialView.jsp")'>نمایش موجودی</a></li>
                </ul>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-flask fa-fw "></i> محصولات <span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                    <li class="dropdown-header">محصولات</li>
                    <li><a onclick='loadContent("/document/productEntery.jsp")'>تحویل به انبار</a></li>
                    <li><a onclick='loadContent("/document/productOut.jsp")'>دریافت از انبار</a></li>
                    <li><a onclick='loadContent("/document/productIsolate.jsp")'>ایزوله کردن</a></li>
                    <li><a onclick='loadContent("/document/productView.jsp")'>نمایش موجودی</a></li>
                </ul>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-circle-o fa-fw"></i> نیمه ساخته <span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                    <li class="dropdown-header">نیمه ساخته</li>
                    <li><a onclick='loadContent("/document/semistructuredEntery.jsp")'>ثبت خروجی ایستگاه</a></li>
                    <li><a onclick='loadContent("/document/semistructuredOut.jsp")'>دریافت از انبار</a></li>
                    <li><a onclick='loadContent("/document/semistructuredWaste.jsp")'>ثبت دوباره کاری و ضایعات</a></li>
                    <li><a onclick='loadContent("/document/semistructuredView.jsp")'>نمایش موجودی</a></li>
                </ul>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa  fa-times-circle fa-fw "></i> توقفات تولید <span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                    <li class="dropdown-header">توقفات تولید</li>
                    <li><a onclick='loadContent("/document/discontinuedEntery.jsp")'>ثبت توقف</a></li>
                    <li><a onclick='loadContent("/document/discontinuedShow.jsp")'>مشاهده توقفات تولید</a></li>
                </ul>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><object type="image/svg+xml" width="16px" data="<c:url value="/resources/images/icon.svg"></c:url> "></object>دستگاه<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                    <li class="dropdown-header">دستگاه</li>
                    <li><a onclick='loadContent("/document/pmEntery.jsp")'>ثبت pm دستگاه ها</a></li>
                    <li><a onclick='loadContent("/document/pmShow.jsp")'>نمایش pm دستگاه ها</a></li>
                    <li><a onclick='loadContent("/document/machineView.jsp")'>نمایش برنامه دستگاه ها</a></li>
                </ul>
            </li>
            <li>
                <a onclick='loadContent("/document/reports.jsp")'><object type="image/svg+xml" width="16px" data="<c:url value="/resources/images/line-chart.svg"></c:url>"></object>گزارشات</a>
            </li>
            <li>
                <a onclick='loadContent("/document/elasticity.jsp")'><i class="glyphicon glyphicon-tasks"></i>کشش پذیری</a>
            </li>
            <li>
                <a onclick='loadContent("/document/identity.jsp")'><i class="glyphicon glyphicon-book"></i>شناسنامه ها</a>
            </li>
        </ul>
    </nav>
    <!-- /#sidebar-wrapper -->

</nav>

<script>
    window.addEventListener("load", connect, false);
    var loc = window.location, new_uri;
    var mailList = new Array();
    if (loc.protocol === "https:") {
        new_uri = "wss:";
    } else {
        new_uri = "ws:";
    }
    new_uri += "//" + loc.host;
    new_uri += "/notification";
    var totalNotif = 0, seeNotif = false, interval, notifBellOpen = false;
    var webSocket;
    function connect() {
        webSocket = new WebSocket(new_uri);
        webSocket.onmessage = onMessage;
        webSocket.onopen = function (evt) {
            var t = new Object();
            t.token = "${cookie['T'].value}";
            webSocket.send(JSON.stringify(t));
        };
        webSocket.onerror = onError;
        webSocket.onclose = onClose;
    }
    function onError(event) {
        window.alert(event);
        connect();
    }
    function onClose(code, reason, wasClean) {
        window.alert(code + " : " + reason + " : " + wasClean);
        connect();
    }
    function onMessage(evt) {
        var data = JSON.parse(evt.data);
        if (data.alert != null && data.alert != undefined) {
            alert(data.alert);
        }
        if (data.mail != null && data.mail != undefined) {
            mail(data.mail);
        }
    }

    function alert(data) {
        for(var i = 0; i < data.length; i++){
            if (data[i].kind == "add"){
                if (data[i].seen == false){
                    try {
                        addNotif(data[i], "0");
                    }catch (err){
                        console.log(err);
                    }
                    totalNotif++;
                }
                try {
                    addNotification(data[i], "0");
                }catch(err){

                }
            }else if(data[i].kind == "use"){
                if (data[i].seen == false){
                    addNotif(data[i], "1");
                    totalNotif++;
                }
                try {
                    addNotification(data[i], "1");
                }catch(err){

                }
            }else if (data[i].kind == "discontinued"){
                if (data[i].seen == false){
                    addNotif(data[i], "2");
                    totalNotif++;
                }
                try {
                    addNotification(data[i], "2");
                }catch(err){

                }
            }

            if (totalNotif > 0){
                $("#totalNotif").text(totalNotif + "");
                $("#totalNotif").attr("hidden", false);
            }
        }
    }
    function mail(data) {
        let newMails = new Array();
        for (let i = 0; i <data.length; i++){
            let mail = new Object();
            mail.id = data[i].id;
            mail.sender = data[i].s;
            mail.receiver = data[i].r;
            mail.subject = data[i].sb;
            mail.description = data[i].ds;
            mail.attach = data[i].a;
            mail.attachFile = data[i].af;
            mail.date = data[i].d;

            mailList.push(mail);
            newMails.push(mail);
        }
        if (mailList.length > 0){
            try {
                var total = parseInt($("#totalMail").text());
                if(total > 0){
                    total += mailList.length;
                    $("#totalMail").text(total + "");
                    let m = "شما " + total + " پیام جدید دارید" ;
                    alertify.message(m, 5);
                }else {
                    $("#totalMail").text(mailList.length + "");
                    let m = "شما " + mailList.length + " پیام جدید دارید" ;
                    alertify.message(m, 5);
                }
            }catch (err){
                $("#totalMail").text(mailList.length + "");
                let m = "شما " + mailList.length + " پیام جدید دارید" ;
                alertify.message(m, 5);
            }
        }

        addMail(newMails);
        try {
            addMailPanel(newMails);
        }catch(err){

        }
    }
    function seenMail() {
        $("#totalMail").text("");
    }
    function addMail(newMails) {
        var total = ($("#mail").children().length - 3) / 2;
        var x = newMails.length - (5 - total);
        if (x > 5){
            x = 5;
        }
        if (x > 0){
            for (var i = 9; i >= (5 - x) * 2; i--){
               $($("#mail").children()[i]).remove();
            }
        }
        for (var i = 0; i < newMails.length; i++){
            if (i > 4)
                break;
            var li = document.createElement("li");
            $(li).addClass("divider");
            $("#mail").prepend(li);

            li = document.createElement("li");

            var a = document.createElement("a");
            $(a).attr("href", "#mailModal");
            $(a).data("value", newMails[i]);
            $(a).attr("data-toggle", "modal");
            $(a).on("click", function () {
                openMailModal("mailModal", $(this).data("value"));
            });
            $(li).append(a);

            var div = document.createElement("div");
            $(a).append(div);

            var strong = document.createElement("strong");
            $(strong).text(newMails[i].sender);
            $(div).append(strong);

            var span = document.createElement("span");
            $(span).addClass("pull-right text-muted");
            $(div).append(span);

            var em = document.createElement("em");
            $(em).text(newMails[i].date);
            $(span).append(em);

            div = document.createElement("div");
            $(div).text(newMails[i].subject);
            $(a).append(div);

            $("#mail").prepend(li);
        }
    }
    function removeMail(data) {
        var mailChildren = $("#mail").find("a");

        for (let i = 0; i < mailChildren.length; i++){
            if ($(mailChildren[i]).data("value") != undefined && $(mailChildren[i]).data("value") != null){
                if ($(mailChildren[i]).data("value").id == data){
                    console.log($(mailChildren[i]).data("value").id)
                    $(mailChildren[i]).parent().next().remove();
                    $(mailChildren[i]).parent().remove();
                    break;
                }
            }
        }
    }
    function addNotif(data, kind) {
        if ($("#notif").find("li[name='" + data.id + "']").length){
            var li = $("#notif").find("li[name='" + data.id + "']");
            $(li).children().first().attr("href", "/document/event/get.do?t=" + data.id);

            $(li).find(":input").val(data.id);

            $(li).children().first().children().remove();

            var div = document.createElement("div");
            var i = document.createElement("i");
            if (kind == "0"){
                $(i).addClass("fa fa-plus fa-fw");
            }else if(kind == "1"){
                $(i).addClass("fa fa-minus fa-fw");
            }else if(kind == "2"){
                $(i).addClass("fa fa-times fa-fw");
            }else {
                $(i).addClass("fa  fa-comment-o fa-fw");
            }
            $(div).append(i);
            $(div).append(data.title);

            var span = document.createElement("span");
            $(span).addClass("pull-right text-muted small");
            $(span).text(data.date);
            $(div).append(span);

            var input = document.createElement("input");
            $(input).attr("hidden", true);
            $(input).val(data.id);
            $(input).attr("name", "id");

            $(li).children().first().append(div);
        }else {
            var li = document.createElement("li");
            $(li).addClass("divider");
            $("#notif").prepend(li);

            li = document.createElement("li");
            $(li).attr("name", data.id);

            var a = document.createElement("a");
            $(a).attr("href", "/document/event/get.do?t=" + data.id);

            var div = document.createElement("div");
            var i = document.createElement("i");
            if (kind == "0"){
                $(i).addClass("fa fa-plus fa-fw");
            }else if(kind == "1"){
                $(i).addClass("fa fa-minus fa-fw");
            }else if(kind == "2"){
                $(i).addClass("fa fa-times fa-fw");
            }else {
                $(i).addClass("fa  fa-comment-o fa-fw");
            }
            $(div).append(i);
            $(div).append(data.title);

            var span = document.createElement("span");
            $(span).addClass("pull-right text-muted small");
            $(span).text(data.date);
            $(div).append(span);

            $(a).append(div);
            $(li).append(a);

            $("#notif").prepend(li);
        }
    }
    function toggleNotif() {
        if ($("#notifBell").attr("aria-expanded") === "false"){
            notifBellOpen = false;
        }else{
            notifBellOpen = true;
        }
        seeNotif = true;
        if (interval != null && notifBellOpen){
            window.clearInterval(interval);
            interval = null;
        }
        interval = window.setInterval(function () {
            if ($("#notifBell").attr("aria-expanded") === "false" && seeNotif){
                seen();
                window.clearInterval(interval);
                interval = null;
            }
        }, 2000);
    }
    function seen() {
        var data = [];
        var lis  = $("#notif").find("li");
        for (let li of lis) {
            if ($(li).attr("name") !== undefined && $("li").attr("name") !== null){
                data.push($(li).attr("name"));
            }
        }
        $.ajax("/document/event/seen.do", {
            data:{data:JSON.stringify(data)},
            type:"POST"
        }).done(function () {
            totalNotif = 0;
            seeNotif = false;
            $("#totalNotif").text("");
            $("#totalNotif").attr("hidden", true);

            $("#notif").children().remove();
            var li = document.createElement("li");

            var a = document.createElement("a");
            $(a).addClass("text-center");
            $(a).attr("href", "/document/notificationView.jsp");

            var strong = document.createElement("strong");
            $(strong).text("نمایش تمامی پیام ها");
            $(a).append(strong);

            var i = document.createElement("i");
            $(i).addClass("fa fa-angle-left");
            $(a).append(i);

            $(li).append(a);

            $("#notif").append(li)
        }).fail(function (data) {
            errors(data);
        });
    }
    function openMailModal(a, data) {
        var inputs = $("#" + a).find('input');
        for (var i = 0; i < inputs.length; i++){
            $(inputs[i]).val("");
        }
        var selects = $("#" + a).find('select');
        for (var i = 0; i < selects.length; i++){
            $(selects[i]).val("");
            $(selects[i]).children().remove();
            refreshSelect($(selects[i]), "لطفا گزینه مورد نظر را انتخاب نمایید");
            $(selects[i]).select2("data", null);
        }
        var textarea = $("#" + a).find('textarea');
        for (var i = 0; i < textarea.length; i++){
            $(textarea[i]).val("");
        }

        if (a == "newMailModal") {
            $("#newMailModal").find("input").prop("disabled", true);
            $("#newMailModal").find("select").prop("disabled", true);
            $("#newMailModal").find("textarea").prop("disabled", true);
            $("#newMailModal").find("div[name='attach']").children().remove();

            $.ajax("/document/user/get.do", {
                type:"POST",
                dataType:"json",
                error:function(XMLHttpRequest, status, errorThrown){
                    errors(status);
                }
            }).done(function (data) {
                for (var i = 0; i < data.length; i++){
                    var option = document.createElement("option");
                    $(option).text(data[i].n);
                    $(option).val(data[i].u);
                    $("#newMailModal").find("select").append(option);
                }
                $("#newMailModal").find("select").select2({
                    placeholder:"گیرنده"
                });

                $("#newMailModal").find("input").prop("disabled", false);
                $("#newMailModal").find("select").prop("disabled", false);
                $("#newMailModal").find("textarea").prop("disabled", false);
            });
        }else if (a == "mailModal"){
            var mail = data;

            $("#mailModal").find("input[name='sender']").val(mail.sender);
            $("#mailModal").find("input[name='subject']").val(mail.subject);
            $("#mailModal").find("textarea").val(mail.description);
            $("#mailModal").find("div[name='attach']").append(mail.attach);
            for(let i = 0; i < mail.attachFile.length; i++){
                saveData(mail.attachFile[i], i);
            }

            mailList.splice(mailList.indexOf(mail), 1);

            $.ajax("/document/mail/seen.do",{
                data:{id:mail.id},
                type:"POST"
            }).done(function (data) {
                if (data != null && data != undefined){
                    removeMail(data);
                    try{
                        removeMailPanel(data);
                    }catch (err){

                    }
                }
            }).fail(function (data) {
                errors(data)
            });
        }
        addModal(a);
    }
    function acceptMailModal() {
        var mail = new Object();

        mail.receiver = $("#newMailModal").find("select[name='receiver']").val();
        mail.subject = $("#newMailModal").find("input[name='subject']").val();
        mail.description = $("#newMailModal").find("textarea[name='description']").val();
        var df = document.getElementById("atf").files[0];
        if (df != null && df != undefined){
            mail.attachFile = getBase64(df);
        }
        if($("#newMailModal").find("div[name='attach']").children().length > 0){
            mail.attach = $("#newMailModal").find("div[name='attach']").clone();
        }

        $.ajax("/document/mail/add.do", {
            data:{data:JSON.stringify(mail)},
            type:"POST",
            dataType:"json"
        }).done(function (data) {
            alertify.success("پیام با موفقیت ارسال شد");
            removeModal();
            $("#newMailModal").modal("hide");
        }).fail(function (data) {
            if (data.status == 200){
                alertify.success("پیام با موفقیت ارسال شد")
                removeModal();
                $("#newMailModal").modal("hide");
            }else {
                errors(data);
            }
        });
    }
    function replyModal(){

    }
    function acceptReplyModal() {
        var mail = new Object();

        mail.sender = $("");
        mail.receiver = $("#newMailModal").find("select[name='receiver']").val();
        mail.subject = $("#newMailModal").find("input[name='subject']").val();
        mail.description = $("#newMailModal").find("textarea[name='description']").val();

        var data = JSON.stringify(mail);
        $.ajax("/document/mail/reply.do", {
            data:{data},
            type:"POST"
        }).done(function (data) {
            alertify.success("پیام با موفقیت ارسال شد")
            removeModal();
            $("#newMailModal").modal("hide");
        }).fail(function (data) {
            if (data.status == 200){
                alertify.success("پیام با موفقیت ارسال شد")
                removeModal();
                $("#newMailModal").modal("hide");
            }else {
                errors(data);
            }
        });
    }
    function acceptForwardModal() {
        var mail = new Object();

        mail.receiver = $("#newMailModal").find("select[name='receiver']").val();
        mail.subject = $("#newMailModal").find("input[name='subject']").val();
        mail.description = $("#newMailModal").find("textarea[name='description']").val();

        var data = JSON.stringify(mail);
        $.ajax("/document/mail/forward.do", {
            data:{data},
            type:"POST"
        }).done(function (data) {
            alertify.success("پیام با موفقیت ارسال شد")
            removeModal();
            $("#newMailModal").modal("hide");
        }).fail(function (data) {
            if (data.status == 200){
                alertify.success("پیام با موفقیت ارسال شد")
                removeModal();
                $("#newMailModal").modal("hide");
            }else {
                errors(data);
            }
        });
    }

    var saveData = (function () {
        var a = document.createElement("a");
        $("#mailModal").find("div[name='attachFile']").append(a);
        return function (data, fileName) {
            var blob = new Blob([data], {type: "octet/stream"}),
                url = window.URL.createObjectURL(blob);
            a.href = url;
            a.download = fileName;
            window.URL.revokeObjectURL(url);
        };
    }());
</script>
