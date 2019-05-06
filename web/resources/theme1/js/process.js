$(document).ready(function(){
    addRow();
    $("#table").DataTable({})
});

function ajaxProduct () {
    $.get("/document/product/ajaxLoad.do").done(function (data) {

    }).fail(function (data) {
        errors(data);
    });
}

function ajaxMachine(select) {
    $.get("/document/machine/list.do").done(function (data) {
        for(var i = 0; i < data.length; i++){
            var option = document.createElement("option");
            $(option).val(data[i].code);
            $(option).text(data[i].code + "-" + data[i].name);
            $(select).append(option);
        }
    }).fail(function (data) {
        errors(data);
    })
}

function addProcessMachine(event){
    var body = document.createElement("div");
    $(body).addClass("panel-body");

    var row1 = document.createElement("div");
    $(row1).addClass("row");
    $(body).append(row1);

    var col1 = document.createElement("div");
    $(col1).addClass("col-md-6");
    $(row1).append(col1);

    var div_group = document.createElement("div");
    $(div_group).addClass("form-group");
    var label = document.createElement("label");
    $(label).text("نام دستگاه");
    $(div_group).append(label);
    var select = document.createElement("select");
    $(select).addClass("form-control");
    ajaxMachine(select);
    $(div_group).append(select);
    $(col1).append(div_group);

    var col2 = document.createElement("div");
    $(col2).addClass("col-md-6");
    $(row1).append(col2);
    var col2_1 = document.createElement("div");
    $(col2_1).addClass("col-md-6");
    $(col2).append(col2_1);

    var col2_2 = document.createElement("div");
    $(col2_2).addClass("col-md-6");
    $(col2).append(col2_2);

    div_group = document.createElement("div");
    $(div_group).addClass("form-group");
    label = document.createElement("label");
    $(label).text("نوع خروجی");
    $(div_group).append(label);
    select = document.createElement("select");
    $(select).append("form-control");
    var option = document.createElement("option");
    $(option).val("product");
    $(option).text("محصول");
    $(select).append(option);
    var option = document.createElement("option");
    $(option).val("semi");
    $(option).text("نیمه ساخته");
    $(select).append(option);
    $(select).on("change", function (event) {
        if ($(event.target).val() == "product"){
            div_group = document.createElement("div");
            $(div_group).addClass("form-group");
            label = document.createElement("label");
            $(label).text("محصول");
            $(div_group).append(label);
            var select2 = document.createElement("select");
            $(select2).append("form-control");
            $.post("/document/product/").done(function (data) {
                for(var i = 0; i < data.length; i++){
                    var option = document.createElement("option");
                    $(option).val(data[i].code);
                    $(option).text(data[i].size + "-" + data[i].design + "-" + data[i].pr);
                    $(select2).append(option);
                }
            }).fail(function (data) {
                errors(data);
            });
            $(div_group).append(select2);
            $(col2_2).append(div_group);
        }else if ($(event.target).val() == "semi"){
            div_group = document.createElement("div");
            $(div_group).addClass("form-group");
            label = document.createElement("label");
            $(label).text("نیمه ساخته");
            $(div_group).append(label);
            var select2 = document.createElement("select");
            $(select2).append("form-control");
            $.post("/document/semistructured/data.do").done(function (data) {
                for(var i = 0; i < data.length; i++){
                    var data2 = data[i];
                    for (var j = 0; j < data2.length; j++){
                        var option = document.createElement("option");
                        $(option).val(data2[j].code);
                        $(option).text(data2[j].name);
                        $(select2).append(option);
                    }
                }
            }).fail(function (data) {
                errors(data);
            });
            $(div_group).append(select2);
            $(col2_2).append(div_group);
        }
    });
    $(div_group).append(select);
    $(col2_1).append(div_group);

    var row2 = document.createElement("div");
    $(row2).addClass("row");
    $(body).append(row2);

    var col = document.createElement("div");
    $(col).addClass("col-md-12");
    $(row2).append(col);

    var table = document.createElement("table");
    $(table).addClass("table table-responsive table-striped table-bordered table-hover");
    $(table).attr("width", "100%");
    $(col).append(table);

    var thead = document.createElement("thead");
    $(thead)

}

function loadTable() {
    var tr = document.createElement("tr");
    var td = document.createElement("td");
    $(td).addClass("bda");
    $(td).append('<a type="button" value="Delete" onclick="deleteRow(event)" style="cursor: pointer"><i class="fa fa-trash-o fa-fw"></i></a><br /><br /> <a type="button" value="Add" onclick="addRow(event)" style="cursor: pointer"><i class="fa fa-plus fa-fw"></i></a>');

}

function refreshTable() {

}

function deleteRow(event) {
    var td = $(event.target).parent().lastChild;
    if ($(td).tagName == "a" && $(td).val() == "Add"){
        var tds = document.getElementsByClassName("bda");
        if (tds.length > 1){
            var td2 = document.createElement("td");
            $(td2).addClass("bda");
            $(td2).append('<a type="button" value="Delete" onclick="deleteRow(event)" style="cursor: pointer"><i class="fa fa-trash-o fa-fw"></i></a><br /><br /> <a type="button" value="Add" onclick="addRow(event)" style="cursor: pointer"><i class="fa fa-plus fa-fw"></i></a>');
            var parent = $(tds[tds.length - 2]).parent();
            $(tds[tds.length - 2]).remove();
            $(parent).prepend(td2);
            $("#table").row($(event.target).parent().parent()).remove().draw();
        }else if(tds.length == 1){
            alertify.error("حداقل یک ردیف باید باقی بماند");
        }
    }else {
        $("#table").row($(event.target).parent().parent()).remove().draw();
    }
}

function addRow(event) {
    var tr = document.createElement("tr");

    //Buttons
    var td = document.createElement("td");
    $(td).attr("colspan", "2");
    $(td).attr("rowspan", "2");
    $(td).addClass("bda");
    $(td).append('<a type="button" value="Delete" onclick="deleteRow(event)" style="cursor: pointer"><i class="fa fa-trash-o fa-fw"></i></a><br /><br /> <a type="button" value="Add" onclick="addRow(event)" style="cursor: pointer"><i class="fa fa-plus fa-fw"></i></a>')
    $(tr).append(td);

    //نام دستگاه
    td = document.createElement("td");
    $(td).attr("colspan", "2");
    $(td).attr("rowspan", "2");
    var select = document.createElement("select");
    $(select).addClass("form-control");
    $(td).append(select);
    $(tr).append(td);

    //نام خروجی
    td = document.createElement("td");
    $(td).attr("colspan", "2");
    $(td).attr("rowspan", "2");
    var select = document.createElement("select");
    $(select).addClass("form-control");
    $(td).append(select);
    $(tr).append(td);

    //نام دستگاه های ورودی
    td = document.createElement("td");
    $(td).attr("colspan", "2");
    select = document.createElement("select");
    $(select).addClass("form-control");
    $(td).append(select);
    $(tr).append(td);


    //ورودی ها
    td = document.createElement("td");
    select = document.createElement("select");
    $(td).append(select);
    $(tr).append(td);
    td = document.createElement("td");
    var input = document.createElement("input");
    $(input).attr("type", "number");
    $(input).addClass("form-control");
    $(td).append(input);
    $(tr).append(td);

    //نام دستگاه های خروجی
    td = document.createElement("td");
    $(td).attr("colspan", "2");
    select = document.createElement("select");
    $(select).addClass("form-control");
    $(td).append(select);
    $(tr).append(td);

    //خروجی ها
    td = document.createElement("td");
    select = document.createElement("select");
    $(td).append(select);
    $(tr).append(td);
    td = document.createElement("td");
    var input = document.createElement("input");
    $(input).attr("type", "number");
    $(input).addClass("form-control");
    $(td).append(input);
    $(tr).append(td);

    //ورودی های خام
    td = document.createElement("td");
    select = document.createElement("select");
    $(td).append(select);
    $(tr).append(td);
    td = document.createElement("td");
    var input = document.createElement("input");
    $(input).attr("type", "number");
    $(input).addClass("form-control");
    $(td).append(input);
    $(tr).append(td);

    $("#tbody").append(tr);

    var trEnteranceMachine = document.createElement("tr");

    td = document.createElement("td");
    $(td).attr("colspan", "2");
    $(td).append('<a type="button" value="Add" onclick="addRowEnteranceMachine(event)" style="cursor: pointer"><i class="fa fa-plus fa-fw"></i></a>');
    $(trEnteranceMachine).append(td);
    td = document.createElement("td");
    $(td).attr("colspan", "2");
    $(td).append('<a type="button" value="Add" onclick="addRowEnteranceMachine(event)" style="cursor: pointer"><i class="fa fa-plus fa-fw"></i></a>');
    $(trEnteranceMachine).append(td);
    td = document.createElement("td");
    $(td).attr("colspan", "2");
    $(td).append('<a type="button" value="Add" onclick="addRowEnteranceMachine(event)" style="cursor: pointer"><i class="fa fa-plus fa-fw"></i></a>');
    $(trEnteranceMachine).append(td);
    td = document.createElement("td");
    $(td).attr("colspan", "2");
    $(td).append('<a type="button" value="Add" onclick="addRowEnteranceMachine(event)" style="cursor: pointer"><i class="fa fa-plus fa-fw"></i></a>');
    $(trEnteranceMachine).append(td);
    td = document.createElement("td");
    $(td).attr("colspan", "2");
    $(td).append('<a type="button" value="Add" onclick="addRowEnteranceMachine(event)" style="cursor: pointer"><i class="fa fa-plus fa-fw"></i></a>');
    $(trEnteranceMachine).append(td);

    $("#tbody").append(trEnteranceMachine);
}

function addRowEnteranceMachine(event) {

}
