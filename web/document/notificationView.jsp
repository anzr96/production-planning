<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 2/4/17
  Time: 4:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col-md-12">
        <h1 class="page-header">پیام ها</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>
<br/>
<div class="row">
    <div class="col-md-12">
        <table id="table" width="100%" class="table table-striped table-hover table-bordered table-responsive" cellspacing="0">
            <thead>
            <tr>
                <th>
                    <a type="button" value="Delete" onclick="deleteRows()" style="cursor: pointer"><i class="fa fa-trash-o fa-fw"></i></a>
                </th>
                <th>موضوع</th>
                <th>تاریخ</th>
                <th>توضیحات</th>
            </tr>
            </thead>
            <tbody id="tbody">
            </tbody>
        </table>
    </div>
</div>
<script>
    $(loadData());
    function data() {
        if ($("#tbody").children().length == 0){
            console.log("0")
        }else {
            $("#table").DataTable({

            });
        }
    }

    function deleteRows() {
        var table = $("#tbody").children();
        var data = [];
        for (var i = 0; i < table.length; i++){
            if ($(table[i]).children().first().children().first().is(":checked")){
                data.push($(table[i]).children().first().children().last().val())
            }
        }
        $.ajax("",{
            data:{data:data},
            type:"POST"
        }).done(function (data) {
            if (data.admin != null){
                alertify.confirm('حذف پیام', 'آیا برای همه پاک شود یا فقط خودتان ؟', function(){ adminDelete(true) }
                        , function(){ adminDelete(false)}).set('labels', {ok:'برای همه', cancel:'فقط خودم'});
            }else {
                alertify.success("پیام برای شما حذف شد");
            }
        }).fail(function (data) {

        });
    }

    function adminDelete(data) {
        var table = $("#tbody").children();
        for (var i = 0; i < table.length; i++){
            if ($(table[i]).children().first().children().first().is(":checked")){
                $(table[i]).children().remove();
            }
        }
//        $.ajax("/document/event/delete.do", {
//            data:{data:data},
//            type:"POST"
//        }).done(function (data) {
//            var table = $("#tbody").children();
//            for (var i = 0; i < table.length; i++){
//                if ($(table[i]).children().first().children().first().val() == "on"){
//                    $(table[i]).remove();
//                }
//            }
//        }).fail(function (data) {
//            errors(data);
//        });
    }

    function loadData(){
        $.get("/document/event/getEvent.do").done(function (data) {
            for (var i = 0; i < data.length; i++){
                var tr = document.createElement("tr");
                var td = document.createElement("td");
                var input = document.createElement("input");
                $(input).attr("type","checkbox");
                $(input).attr("name","checkbox");
                $(td).append(input);
                input = document.createElement("input");
                $(input).val(data[i].id);
                $(input).attr("type","number");
                $(input).attr("name", "id");
                $(input).attr("hidden", "true");
                $(td).append(input);
                $(tr).append(td);
                td = document.createElement("td");
                $(td).text(data[i].title);
                $(tr).append(td);
                td = document.createElement("td");
                $(td).text(data[i].date);
                $(tr).append(td);
                td = document.createElement("td");
                $(td).text(data[i].description);
                $(tr).append(td);
                $("#tbody").append(tr);
            }
            $("#table").DataTable({

            });
        }).fail(function (data) {
            errors(data);
        });
    }

</script>
