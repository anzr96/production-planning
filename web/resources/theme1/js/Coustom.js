var modalArray = new Array();
let codeStmt = "لطفا کد مورد نظر را انتخاب کنید", nameStmt = "لطفا نام مورد نظر را انتخاب کنید";
let messageTop = '<div name="top" class="row">\n' +
    '    <div class="col-sm-4">\n' +
    '\n' +
    '    </div>\n' +
    '    <div class="col-sm-4">\n' +
    '        <br/>\n' +
    '        <br/>\n' +
    '        <div class="panel panel-default">\n' +
    '            <div class="panel-body">\n' +
    '                <h4 name="title" style="text-align: center"></h4>\n' +
    '            </div>\n' +
    '        </div>\n' +
    '    </div>\n' +
    '    <div class="col-sm-4">\n' +
    '        <div class="row">\n' +
    '            <div class="col-sm-12">\n' +
    '                <div class="panel panel-default">\n' +
    '                    <div class="panel-body">\n' +
    '                        <p name="pd">تاریخ : </p>\n' +
    '                    </div>\n' +
    '                </div>\n' +
    '            </div>\n' +
    '        </div>\n' +
    '        <div class="row">\n' +
    '            <div class="col-sm-12">\n' +
    '                <div class="panel panel-default">\n' +
    '                    <div class="panel-body">\n' +
    '                        <p name="pcf">کد فرم : </p>\n' +
    '                    </div>\n' +
    '                </div>\n' +
    '            </div>\n' +
    '        </div>\n' +
    '    </div>\n' +
    '</div>';
let messageBottom = '';
$(function () {
    $.ajaxSetup({
        beforeSend: function (jqxhr) {
            progressCoustom();
        },
        complete: function (jqxhr) {
            progressCoustom();
        }
    });
    alertify.defaults.glossary.ok = 'تایید';
    alertify.defaults.glossary.cancel = 'لغو';
    Highcharts.setOptions({
        global
            : {},
        lang
            : {
            contextButtonTitle: "دریافت نمودار",
            decimalPoint: ".",
            downloadJPEG: "دانلود JPEG",
            downloadPDF: "دانلود PDF",
            downloadPNG: "دانلود PNG",
            downloadSVG: "دانلود SVG",
            drillUpText: "Back to {series.name}",
            invalidDate: "",
            loading: "بارگذاری ...",
            months: [ "فروردین" , "اردیبهشت" , "خرداد" , "تیر" , "مرداد" , "شهریور" , "مهر" , "آبان" , "آذر" , "دی" , "بهمن" , "اسفند"],
            noData: "اطلاعاتی یافت نشد",
            numericSymbolMagnitude: 1000,
            numericSymbols: [ "k" , "M" , "G" , "T" , "P" , "E"],
            printChart: "پرینت نمودار",
            resetZoom: "Reset zoom",
            resetZoomTitle: "Reset zoom level 1:1",
            shortMonths: [ "فروردین" , "اردیبهشت" , "خرداد" , "تیر" , "مرداد" , "شهریور" , "مهر" , "آبان" , "آذر" , "دی" , "بهمن" , "اسفند"],
            shortWeekdays: undefined,
            thousandsSep: " ",
            weekdays: ["شنبه", "یکشنبه", "دوشنبه", "سشنبه", "چهارشنبه", "پنج شنبه", "جمعه"]
        }
    });

    starter();

});

function createDataTable(table, ordering = true, messageTop = null, messageBottom = null, leftFixed = 0) {
    if ( $.fn.DataTable.isDataTable( $(table) ) ){
        $(table).DataTable().destroy();
    }
    var firstHeader = '';
    var dt =  $(table).DataTable({
        dom: 'lBftip',
        buttons: [
            {
                extend:'excel',
                text:'اکسل'
            },
            {
                extend:'print',
                text:'پرینت',
                title:"شرکت لاستیک پارس (سهامی عام)",
                autoPrint:true,
                messageTop: messageTop,
                messageBottom: messageBottom,
                customize: function (win) {
                    var style = document.createElement("style");
                    $(style).prop("type", "text/css").html("\
    @page {\
        margin: 0 1cm 0 1cm;\
    }\
    \
    @font-face {\n" +
                        "    font-family: \"BNazanin\";\n" +
                        "    src: url(/resources/fonts/BNazanin.ttf) format(\"truetype\");\n" +
                        "}");
                    $(win.document.head).append(style);
                    var size = $(win.document.body).find("table").find("thead").find("tr").children().length;
                    $(win.document.body).find("table").append("<tr><td rowspan='2' style='text-align: right !important;' colspan='" + size + "'>" + "توضیحات :" +"</td></tr>");
                    $(win.document.body).find("thead").prepend(firstHeader);
                    if (firstHeader != ""){
                        $(win.document.body).find("thead").find("tr").last().children().first().remove();
                    }
                    $(win.document.body).find('h1').css('text-align','center');
                    $(win.document.body).find('h1').css('font-size','30px');
                },
                exportOptions : {
                    columns: ':visible',
                    format: {
                        body: function(data, col, row, node) {
                            if($(data).is("input")){
                                return $(node).find("input").val();
                            }else if($(data).is("select")){
                                return $(node).find("select").find(':selected').text();
                            }else if($(data).is("a")){
                                return "";
                            }else {
                                return data;
                            }
                        },
                        header: function (data, col, node) {
                            if ($(node).parents("tr").prev().is("tr")){
                                firstHeader = $(node).parents("tr").prev().clone();
                            }
                            return data;
                        }
                    }
                },
            },
            {
                extend:'colvis',
                text:'ستون ها',
                columnText: function ( dt, idx, title ) {
                    if (title === ""){
                        return "حذف و اضافه";
                    }else {
                        return title;
                    }
                }
            }
        ],
        fixedColumns: {
            leftColumns: leftFixed
        },
        scrollX:true,
        fixedHeader: true,
        ordering:ordering
    });
    return dt;
}

function refreshSelect(select, placeholder, clear = false, value = true) {
    if(clear){
        $(select).children().remove();
    }
    let children = $(select).children();
    let check = false;
    for (let obj of children) {
        if ($(obj).val() === ""){
            check = true;
            break;
        }
    }
    if (!check){
        let option = document.createElement("option");
        $(option).text(placeholder);
        $(option).val("");
        $(select).prepend(option);
        if (value){
            var attr = $(select).attr('multiple');
            if (typeof attr === typeof undefined || attr === false) {
                $(select).val("");
            }
        }
    }

    try{
        $(select).select2("destroy");
    }catch(err){
        console.log("refreshSelect");
        console.log(err);
    }
    $(select).select2({
        placeholder: placeholder,
        allowClear:true,
        dir:'rtl',
        tags:true
    });
}

function addModal(id) {
    if (modalArray.length == 0){
        modalArray.push(id);
    }else{
        var oldID = modalArray.pop();
        $("#" + oldID).modal("hide");
        modalArray.push(oldID);
        modalArray.push(id);
    }
    // if ( !$("#" + id).is(':visible') ) {
    //     $("#" + id).modal('show');
    // }
}

function removeModal(){
    if (modalArray.length == 0){
        var id = modalArray.pop();
        $("#" + id).modal("hide");
    }else {
        var id = modalArray.pop();
        $("#" + id).modal("hide");
        var oldID = modalArray.pop();
        $("#" + oldID).modal("show");
        modalArray.push(oldID);
    }
}

function errors(jqXHR) {
    if (jqXHR.responseText.length > 0){
        if(jqXHR.status === 404){
            alertify.error(jqXHR.responseText + " ( " + jqXHR.status + " ) ");
        }else if (jqXHR.status === 400){
            alertify.error(jqXHR.responseText + " ( " + jqXHR.status + " ) ");
        }else if (jqXHR.status === 500){
            alertify.error("خطا در سرور")
            alertify.error(jqXHR.responseText + " ( " + jqXHR.status + " ) ");
        }else if (jqXHR.status === 408){
            alertify.error("Request Time-out")
        }else if (jqXHR.status === 403){
            alertify.error(jqXHR.responseText + " ( " + jqXHR.status + " ) ");
            alertify.error("به صفحه فراخوانی شده دسترسی ندارید");
            loadContent("/403.jsp", false);
        }
    }else {
        if(jqXHR.status === 404){
            alertify.error("اطلاعات مورد نظر یافت نشد")
        }else if (jqXHR.status === 400){
            alertify.error("اطلاعات فرستاده شده صحیح نمی باشد")
        }else if (jqXHR.status === 500){
            alertify.error("خطا در سرور")
        }else if (jqXHR.status === 408){
            alertify.error("Request Time-out")
        }else if (jqXHR.status === 403){
            alertify.error("به صفحه فراخوانی شده دسترسی ندارید");
            loadContent("/403.jsp", false);
        }
    }
}

function resetPage() {
    $("#page-wrapper").find('input').val("");
    $("#page-wrapper").find('select').val("");
    $("#page-wrapper").find('table tbody').children().remove();
    $("#page-wrapper").find('table').DataTable().draw();
}

function progressCoustom() {
    // if ($("#progressDiv").length){
    //     $("#progressDiv").remove();
    // }else {
    //     var div = document.createElement("div");
    //     $(div).addClass("progressDiv");
    //     $(div).attr("id", "progressDiv");
    //     $("#wrapper").append(div);
    // }
    if ($("#progress-line").css("display") === "none"){
        $("#progress-line").css("display", "flex");
    }else {
        $("#progress-line").css("display", "none");
    }
}

window.alert = function(data) {
    alertify.alert("هشدار", data, function () {
        alertify.success("تایید شد!");
    });
};

function getBase64(file) {
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function () {
        alertify.success("با موفقیت بارگذاری شد");
        return reader.result;
    };
    reader.onerror = function (error) {
        var p = document.createElement("p");
        $(p).text("اشکال در بارگذاری");
        var p2 = document.createElement("p");
        $(p2).text("Error : " + error);
        var div = document.createElement("div");
        $(div).append(p);
        $(div).append(p2);
        alertify.error(div);
    };
}

function printData(id) {
    var divToPrint = document.getElementById(id);
    var newWin= window.open("");
    newWin.document.write(divToPrint.outerHTML);
    newWin.print();
    newWin.close();
}

function createField() {
    var row = document.createElement("div");
    $(row).addClass("row");

    var col = document.createElement("div");
    $(col).addClass("col-md-1");
    $(row).append(col);

    var p = document.createElement("p");
    $(p).text("حذف");
    $(col).append(p);

    var a = document.createElement("a");
    $(a).attr("type", "button");
    $(a).on("click", function (event) {
        alertify.confirm('تاییدیه حذف', 'آیا از حذف فیلد مطمئن هستید ؟', function(){
                $(event.target).parents("div[class='row']").first().next().remove();
                $(event.target).parents("div[class='row']").first().remove();
                alertify.success('حذف شد');
            }
            , function(){
            alertify.error('حذف نشد');
        });

    });
    $(a).css("cursor", "pointer");
    $(a).append('<i class="fa fa-trash-o fa-fw"></i>');
    $(col).append(a);

    col = document.createElement("div");
    $(col).addClass("col-md-4");
    $(row).append(col);

    p = document.createElement("p");
    $(p).text("نام فیلد");
    $(col).append(p);

    var input = document.createElement("input");
    $(input).attr("name", "fn");
    $(input).attr("type", "text");
    $(input).addClass("form-control");
    $(col).append(input);

    col = document.createElement("div");
    $(col).addClass("col-md-3");
    $(row).append(col);

    p = document.createElement("p");
    $(p).text("نوع فیلد");
    $(col).append(p);

    var select = document.createElement("select");
    $(select).attr("name", "ft");
    $(select).addClass("form-control");
    $(select).on("change", function (event) {
        var input = $(event.target).parents("div[class='row']").first().find("input[name='fv']");
        $(input).attr("type", $(this).val());
        if ($(this).val() === "file"){
            $(input).removeClass("form-control");
        }else {
            $(input).addClass("form-control");
        }
        if ($(this).val() === "date"){
            $(input).datepicker({
                format: "mm-dd-yyyy",
                minViewMode: 0,
                todayBtn: true,
                clearBtn: true,
                todayHighlight: true,
                toggleActive: true
            });
        }else {
            $(input).datepicker("remove");
        }
    });
    $(col).append(select);
    $(select).select2({
        placeholder:"نوع فیلد را انتخاب کنید"
    });

    var option = document.createElement("option");
    $(option).val("text");
    $(option).text("متن");
    $(select).append(option);
    option = document.createElement("option");
    $(option).val("number");
    $(option).text("عدد");
    $(select).append(option);
    option = document.createElement("option");
    $(option).val("date");
    $(option).text("تاریخ");
    $(select).append(option);
    option = document.createElement("option");
    $(option).val("file");
    $(option).text("فایل");
    $(select).append(option);

    col = document.createElement("div");
    $(col).addClass("col-md-4");
    $(row).append(col);

    p = document.createElement("p");
    $(p).text("مقدار فیلد");
    $(col).append(p);

    input = document.createElement("input");
    $(input).attr("name", "fv");
    $(input).attr("type", "text");
    $(input).addClass("form-control");
    $(col).append(input);

    return row;
}

function drag(ev) {
    let data = ev.target.cloneNode(true);
    openMailModal("newMailModal");
    $("#newMailModal").find("div[name='attach']").append(data);
    $("#newMailModal").modal("show");
}

function loadContent(url, nav = true) {
    try {
        clearPage();
    }catch (err){

    }
    try {
        $("#page-wrapper").load(url, function () {
            starter();
            if (nav){
                hamburger_cross();
            }
        });
    }catch (err){
        alertify.error("اختلال در بارگذاری محتوای سایت!")
    }
}

function hamburger_cross() {
    if ($('.hamburger').hasClass("is-open")){
        $('.overlay').hide();
        $('.hamburger').removeClass('is-open');
        $('.hamburger').addClass('is-closed');
    }else{
        $('.overlay').show();
        $('.hamburger').removeClass('is-closed');
        $('.hamburger').addClass('is-open');
    }
    $('#wrapper').toggleClass('toggled');
}

function clearPage() {
    try {
        if ($('body').find("input[name='date']") !== undefined){
            $('body').find("div[class='datepicker-plot-area']").remove();
        }
        if ($("#page-wrapper").find("table") !== undefined){
            $("#page-wrapper").find("table").DataTable().destroy();
        }
    }catch (err){

    }
    $("#page-wrapper").children().remove();
}

function starter() {
    createDataTable($('body').find('table'));
    var selects = $('body').find('select');
    for (let obj of selects) {
        if ($(obj).children().length > 0){
            $(obj).select2({
                dir:"rtl",
                placeholder:$(obj).children().first().text(),
                allowClear:true,
                tags:true
            });
        }else{
            $(obj).select2({
                dir:"rtl",
                placeholder:"لطفا گزینه مورد نظر را انتخاب کنید",
                allowClear:true,
                tags:true
            });
        }
    }
}









