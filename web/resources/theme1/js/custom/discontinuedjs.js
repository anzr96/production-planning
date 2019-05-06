$(document).ready(function () {
    disGroup();
    $("#kind").select2({
        placeholder: "نوع توقف", //placeholder
        tags: true
    });
    date();
});
function disGroup() {
    $("#disGroup").children().remove();
    $.ajax("/document/discontinued/disGroup.do",{
        type: "POST",
        dataType: "XML"
    }).done(function (data) {
        var name = data.getElementsByTagName("name");
        if (name != null){
            for (var i = 0; i < name.length; i++){
                var option = document.createElement("option");
                $(option).val(name[i].childNodes[0].nodeValue);
                $(option).text(name[i].childNodes[0].nodeValue);
                $("#disGroup").append(option);
            }
            $("#disGroup").prop("disabled", false);
        }
        $("#disGroup").select2({
            placeholder: "گروه توقف", //placeholder
            tags: true
        });
    }).fail(function (data) {
        errors(data);
    });
}
function disKind(event) {
    $("#kind").children().remove();
    var data = "data=";
    data += $(event.target).val();
    $.ajax("/document/discontinued/disKindMultiple.do",{
        data: data,
        type: "POST",
        dataType: "XML"
    }).done(function (data) {
        var discontinued = data.getElementsByTagName("discontinued");
        if (discontinued != null){
            for(var i = 0; i < discontinued.length; i++){
                var optGroup = document.createElement("optgroup");
                $(optGroup).attr("label", discontinued[i].getAttribute("label"));

                var name = discontinued[i].getElementsByTagName("name");
                for (var j = 0; j < name.length; j++){
                    console.log("hello")
                    var option = document.createElement("option");
                    $(option).val(name[j].childNodes[0].nodeValue);
                    $(option).text(name[j].childNodes[0].nodeValue);
                    $(optGroup).append(option);
                }
                $("#kind").append(optGroup);
            }
            $("#kind").prop("disabled", false);
        }
        $("#kind").select2({
            placeholder: "نوع توقف", //placeholder
            tags: true
        });
    }).fail(function (data) {
        errors(data);
    });
}
function date() {
    $("#datepicker").datepicker({
        format: "mm-dd-yyyy",
        minViewMode: 0,
        todayBtn: true,
        clearBtn: true,
        multidate: false,
        autoclose: true,
        todayHighlight: true,
        toggleActive: true
    });
}
function ajaxCheck() {
    var data = $("#form").serialize();
    $.post("/document/discontinued/show.do",data).done(function (data) {
        if (document.getElementById("table").checked){
            var temp = JSON.parse(data);
            table(temp.table);
        }else {
            $("#tableDiv").attr("hidden", true);
        }
        if (document.getElementById("line").checked){
            var temp = JSON.parse(data);
            line(temp.line);
        }else {
            $("#lineDiv").attr("hidden", true);
        }
        if (document.getElementById("bar").checked){
            var temp = JSON.parse(data);
            bar(temp.bar);
        }else {
            $("#barDiv").attr("hidden", true);
        }
    }).fail(function (data) {
        errors(data);
        $("#tableDiv").attr("hidden", true);
        $("#lineDiv").attr("hidden", true);
        $("#barDiv").attr("hidden", true);
    });
}
function table(data) {
    newTable();
    $("#tableResult").DataTable({
        data:data,
        columns:[
            {data: 'group'},
            {data: 'kind'},
            {data: 'date'},
            {data: 'discontinuedTime'},
            {data: 'repairTime'},
            {data: 'place'},
            {data: 'explain'}
        ]
    });
    $("#tableDiv").attr("hidden", false);
}
function line(data) {
    $("#lineDiv").children().first().children().first().children().last().children().remove();

    var div = document.createElement("div");
    $(div).attr("id", "lineGraph");

    $("#lineDiv").children().first().children().first().children().last().append(div);

    Morris.Line({
        element: 'lineGraph',
        data: data,
        xkey: "date",
        ykeys: ["value"],
        labels: ["زمان توقف"],
        xLabels: "day",
        resize: true
    });
    $("#lineDiv").attr("hidden", false);
}
function bar(data) {
    $("#barDiv").children().first().children().first().children().last().children().remove();

    var div = document.createElement("div");
    $(div).attr("id", "barGraph");

    $("#barDiv").children().first().children().first().children().last().append(div);

    Morris.Bar({
        element: 'barGraph',
        data: data,
        xkey: "name",
        ykeys: ["value"],
        labels: ["زمان توقف"],
        resize: true
    });
    $("#barDiv").attr("hidden", false);
}
function newTable() {
    $("#tableDiv").children().first().children().first().children().last().children().remove();

    var table = document.createElement("table");
    $(table).addClass("table table-striped table-hover table-bordered table-responsive");
    $(table).attr("id", "tableResult");
    $(table).attr("width", "100%");
    $(table).attr("cellspacing", "0");

    var thead = document.createElement("thead");
    var tr = document.createElement("tr");
    var td = document.createElement("td");
    $(td).text("گروه توقف");
    $(tr).append(td);
    td = document.createElement("td");
    $(td).text("نوع توقف");
    $(tr).append(td);
    td = document.createElement("td");
    $(td).text("تاریخ");
    $(tr).append(td);
    td = document.createElement("td");
    $(td).text("زمان توقف");
    $(tr).append(td);
    td = document.createElement("td");
    $(td).text("زمان تعمیر");
    $(tr).append(td);
    td = document.createElement("td");
    $(td).text("محل توقف");
    $(tr).append(td);
    td = document.createElement("td");
    $(td).text("شرح توقف");
    $(tr).append(td);
    $(thead).append(tr);

    $(table).append(thead);

    $("#tableDiv").children().first().children().first().children().last().append(table);
}