// ==UserScript==
// @name vahed
// @namespace localhost
// @description entekhab vahed
// @include https://pooya.um.ac.ir/educ/stu_portal/ShowPreCSelsForm.php
// ==/UserScript==
var codes = [23152138, 23152116, 23151237, 23152434, 23152467, 23151157, 23151088];
var values = [1, 1, 2, 1, 1, 1, 1];
var total = 0;
for (var i = 0; i < codes.length; i++){
    try {
        $('*:contains("' + codes[i] + '")').parents("tr").first().find("input").val(values[i]);
        console.log(codes[i]);
    }catch (err){
        console.log("failed : " + codes[i]);
        total++;
    }
}

if (total < 7){
    try {
        $('*:contains("' + codes[0] + '")').parents("form").first().submit();
    }catch (err){
        console.log("submit failed")
    }
}else{
    console.log("all of them was failed")
}

