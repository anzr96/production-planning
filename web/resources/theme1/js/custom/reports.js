$(document).ready(function () {
        $($("#divData3").children()[0]).select2({
            placeholder: "گزینه مورد نظر را انتخاب کنید", //placeholder
            tags: true
        })
});
function data2Set(event) {
    $("#data2").children().remove();
    var option = document.createElement("option");
    $(option).val("");
    $(option).text("لطفا داده مورد نظر را انتخاب کنید");
    $("#data2").append(option);
    var value = $(event.target).val();
    if (value == "product"){
        var option = document.createElement("option");
        $(option).val("products");
        $(option).text("تولیدات");
        $("#data2").append(option);
        var option = document.createElement("option");
        $(option).val("sells");
        $(option).text("فروش");
        $("#data2").append(option);
        var option = document.createElement("option");
        $(option).val("isolated");
        $(option).text("ایزوله");
        $("#data2").append(option);
        $("#data2").prop("disabled", false);
    }else if (value == "semistructured"){
        var option = document.createElement("option");
        $(option).val("products");
        $(option).text("تولیدات");
        $("#data2").append(option);
        var option = document.createElement("option");
        $(option).val("wastes");
        $(option).text("ضایعات");
        $("#data2").append(option);
        $("#data2").prop("disabled", false);
    }else if (value == "rawmaterial"){
        var option = document.createElement("option");
        $(option).val("buy");
        $(option).text("خرید");
        $("#data2").append(option);
        var option = document.createElement("option");
        $(option).val("use");
        $(option).text("مصرف");
        $("#data2").append(option);
        $("#data2").prop("disabled", false);
    }else if (value == "machine"){
        var option = document.createElement("option");
        $(option).val("faulty");
        $(option).text("معیوب");
        $("#data2").append(option);
        $("#data2").prop("disabled", false);
    }
}
function selection(event) {
    var data = "data=";
    data = data + $("#data").val();
    if (data != null && data != ""){
        $.ajax("/document/reports/list.do", {
            data: data,
            type:"POST",
            dataType: "json"
        }).done(function (data) {
            $("#divData3").children().remove();

            var select = document.createElement("select");
            $(select).attr("name", "data3");
            $(select).attr("id", "data3");
            $(select).attr("data-placeholder", "گزینه مورد نظر را انتخاب کنید");
            $(select).attr("multiple", true);
            $(select).on("change", function (event) {
                viewModeSet(event);
            });
            $(select).addClass("form-control");
            $("#divData3").append(select);

            var option = document.createElement("option");
            $(option).val("all");
            $(option).text("انتخاب همه موارد");
            $("#data3").append(option);

            for (var i = 0; i < data.length; i++){
                option = document.createElement("option");
                $(option).val(data[i]);
                $(option).text(data[i]);
                $("#data3").append(option);
            }

            $("#data3").select2({
                palceholder: "گزینه مورد نظر را انتخاب کنید",
                tag: true
            });
        }).fail(function () {
            selection(event);
        });
    }
}
function viewModeSet(event) {
    if ($(event.target).val() != "" && $(event.target) != null){
        $("#viewMode").prop("disabled", false);
    }
}
function datepick(event) {
    if ($(event.target).val() != "" && $(event.target).val() != null){
        var mode = 0;
        if ($(event.target).val() == "year"){
            mode = 2;
        }else if($(event.target).val() == "month"){
            mode = 1;
        }

        $("#datepicker").remove();

        var div = document.createElement("div");
        $(div).addClass("input-daterange input-group");
        $(div).attr("id", "datepicker");
        $("#divDate").append(div);

        var input = document.createElement("input");
        $(input).addClass("input-sm form-control");
        $(input).attr("type", "text");
        $(input).attr("name", "start");
        $(input).attr("placeholder", "شروع");
        $(input).attr("id", "start");
        $("#datepicker").append(input);

        var span = document.createElement("span");
        $(span).addClass("input-group-addon");
        $(span).text("تا");
        $("#datepicker").append(span);

        var input = document.createElement("input");
        $(input).addClass("input-sm form-control");
        $(input).attr("type", "text");
        $(input).attr("name", "end");
        $(input).attr("placeholder", "پایان");
        $(input).attr("id", "end");
        $("#datepicker").append(input);

        $('#datepicker').datepicker({
            format: "mm-dd-yyyy",
            minViewMode: mode,
            todayBtn: true,
            clearBtn: true,
            autoclose: true,
            todayHighlight: true
        });
    }
}
function checkData() {
    $("#graph").children().remove();
    $.ajax("/document/reports/getData.do",{
        data:$("#form").serialize(),
        type:"POST",
        dataType:"JSON"
    }).done(function (data) {
        var keys = Object.keys(data[0]);
        var index = keys.indexOf("date");
        if (index > -1){
            keys.splice(index, 1);
        }
        graphData(data, keys);
    }).fail(function (data) {
        errors(data);
    });
}
function graphData(data, keys) {
    if ($(".graphDiv").length > 0) {
        $(".graphDiv").each(function() {
            $(".graphDiv").remove();
        });
    }

    if(document.getElementById("line").checked){
        create("graphLine");
        Morris.Line({
            element: 'graphLine',
            data: data,
            xkey: "date",
            ykeys: keys,
            labels: keys
        });
    }
    if(document.getElementById("bar").checked){
        create("graphBar");
        Morris.Bar({
            element: 'graphBar',
            data: data,
            xkey: "date",
            ykeys: keys,
            labels: keys
        });
    }
    if(document.getElementById("donat").checked){
        create("graphDonat");
        Morris.Donut({
           element: 'graphDonat',
            data:data,
            xkey:"date",
            ykeys:keys,
            labels:keys
        });
    }
}
function create(id) {
    var divRow = document.createElement("div");
    $(divRow).addClass("row graphDiv");
    $("#page-wrapper").append(divRow);

    var divCol = document.createElement("div");
    $(divCol).addClass("col-md-12");
    $(divRow).append(divCol);

    var divPanel = document.createElement("div");
    $(divPanel).addClass("panel panel-default");
    $(divCol).append(divPanel);

    var divHead = document.createElement("div");
    $(divHead).addClass("panel-heading");
    $(divPanel).append(divHead);

    var divBody = document.createElement("div");
    $(divBody).addClass("panel-body");
    $(divPanel).append(divBody);

    var divGraph = document.createElement("div");
    $(divGraph).attr("id", id);
    $(divBody).append(divGraph);

    var h4 = document.createElement("h4");
    $(h4).addClass("panel-title");
    $(h4).text("نتایج گزارشات");
    $(divHead).append(h4);
}