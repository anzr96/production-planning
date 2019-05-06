<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div class="col-md-12">
        <h1 class="page-header rtl">برنامه ریزی لاستیک پارس</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>
<br/>
<div class="row">
    <div class="col-md-4">
        <div class="panel panel-default rtl" draggable ondragstart="drag(event)">
            <div class="panel-heading">
                <i class="fa fa-clock-o fa-fw"></i>رخداد ها
                <div class="pull-left">
                    <div class="btn-group">
                        <select class="form-control" >
                            <option value="10" selected>10</option>
                            <option value="20">20</option>
                            <option value="50">50</option>
                        </select>
                    </div>
                </div>
            </div>
            <!-- /.panel-heading -->
            <div class="panel-body">
                <ul class="timeline" id="timeline">
                </ul>
            </div>
            <!-- /.panel-body -->
            <div class="panel-footer">
                <a class="btn btn-default btn-lg btn-block" onclick="loadContent('/document/notificationView.jsp', false)">نمایش تمامی رخداد ها</a>
            </div>
            <!-- /.panel-footer -->
        </div>
    </div>
    <div class="col-md-4">
        <div class="panel panel-default rtl" id="printPanel">
            <div class="panel-heading">
                <i class="fa fa-bell fa-fw"></i> پیام ها
                <div class="pull-left">
                    <div class="btn-group">
                        <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
                            عملیات
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu pull-left" role="menu" style="min-width: 0 !important;">
                            <li><a href="#newMailModal" data-toggle="modal" onclick="openMailModal('newMailModal')"><i class="fa fa-plus fa-fw"></i> پیام جدید </a>
                            </li>
                            <li><a style="cursor: pointer" onclick="printMail(event)"><i class="glyphicon glyphicon-print "></i> پرینت </a>
                            </li>
                            <li><a style="cursor: pointer" onclick="sendMail(event)"><i class="fa fa-reply fa-fw "></i> ارسال </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <!-- /.panel-heading -->
            <div class="panel-body">
                <div class="list-group" id="mail-panel">
                </div>
                <!-- /.list-group -->
                <a href="#" class="btn btn-default btn-block">نمایش تمامی پیام ها</a>
            </div>
            <!-- /.panel-body -->
        </div>
    </div>
</div>

<script>
    function addNotification(data, kind) {
        var li = document.createElement("li");
        $(li).attr("id", data.id);
        if ($("#timeline").children().first().attr("class") != "timeline-inverted"){
            $(li).addClass("timeline-inverted");
        }else {
            $(li).removeClass("timeline-inverted");
        }

        var a = document.createElement("a");
        $(a).css("cursor", "pointer");
        var i = document.createElement("i");
        switch (kind){
            case "0":{
                $(a).addClass("timeline-badge success");
                $(i).addClass("fa fa-plus ff-center");
                break;
            }
            case "1":{
                $(a).addClass("timeline-badge info");
                $(i).addClass("fa fa-minus ff-center");
                break;
            }
            case "2":{
                $(a).addClass("timeline-badge danger");
                $(i).addClass("fa fa-times ff-center");
                break;
            }
            default:{
                $(a).addClass("timeline-badge");
                $(i).addClass("fa  fa-comment-o ff-center");
            }
        }

        $(a).append(i);
        $(li).append(a);

        var div = document.createElement("div");
        $(div).addClass("timeline-panel");
        var div1 = document.createElement("div");
        $(div1).addClass("timeline-heading");
        var h4 = document.createElement("h4");
        $(h4).addClass("timeline-title");
        $(h4).text(data.title);
        $(div1).append(h4);
        var p = document.createElement("p");
        var small = document.createElement("small");
        $(small).addClass("text-muted");
        i = document.createElement("i");
        $(i).addClass("fa fa-clock-o");
        $(small).append(i);
        $(small).text(data.date);
        $(p).append(small);
        $(div1).append(p);
        $(div).append(div1);

        div1 = document.createElement("div");
        $(div1).addClass("timeline-body");
        $(div1).attr("hidden", "true");
        p = document.createElement("p");
        $(p).text(data.description);
        $(div1).append(p);
        $(div).append(div1);

        $(a).on("click", function () {
            $(div1).attr("hidden", !$(div1).attr("hidden"));
        });

        $(li).append(div);

        $("#timeline").prepend(li);
    }
    function addMailPanel(data) {
        for(var i = 0; i < data.length; i++){
            var a = document.createElement("a");
            $(a).attr("href", "#mailModal");
            $(a).attr("data-toggle", "modal");
            $(a).prop("draggable", true);
            $(a).data("value", data[i]);
            $(a).on("click", function () {
                openMailModal("mailModal", $(this).data("value"));
            });
            $(a).on("dragstart", function (event) {
                drag(event);
            });
            $(a).addClass("list-group-item");

            var div = document.createElement("div");
            $(a).append(div);

            var strong = document.createElement("strong");
            $(strong).text(data[i].sender);
            $(div).append(strong);

            var span = document.createElement("span");
            $(span).addClass("pull-left text-muted");
            $(div).append(span);

            var em = document.createElement("em");
            $(em).text(data[i].date);
            $(span).append(em);

            div = document.createElement("div");
            $(div).text(data[i].subject);
            $(a).append(div);

            $("#mail-panel").prepend(a);
        }
    }
    function removeMailPanel(data) {
        var mailChildren = $("#mail-panel").find("a");
        for(let i = 0; i < mailChildren.length; i++){
            if ($(mailChildren[i]).data("value") != undefined && $(mailChildren[i]).data("value") != null){
                if ($(mailChildren[i]).data("value").id == data){
                    $(mailChildren[i]).remove();
                    break;
                }
            }
        }
    }
    function printMail(event){
        $(event.target).parents("div[class='panel']").first().find()
//        $("#printPanel").printThis()
//        printDiv($(event.target).parents("div[class='panel']").first());
    }
    function sendMail(event) {
        var html = $('div').append($(event.target).parents("div[class='panel']").first().clone()).html();
        openMailModal("newMailModal");
        var hr = document.createElement("hr");
        $("#newMailModal").find("div[class='modal-body']").append(hr);
        var div = document.createElement("div");
        $(div).addClass("row");
        var div2 = document.createElement("div");
        $(div2).addClass("col-md-12");
        $(div).append(div2);
        $(div2).append($("#printPanel"));
        $("#newMailModal").find("div[class='modal-body']").append(div);
        $("#newMailModal").modal("show");
    }
</script>