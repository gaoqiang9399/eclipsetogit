//获取url中的参数，会中文乱码
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}
//获取url参数，并且不会中文乱码
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var l = decodeURI(window.location.search);
    var r = l.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

//补0操作
function getzf(num){
    if(parseInt(num) < 10){
        num = '0'+num;
    }
    return num;
}

//当值为null时转为""
function isItEmpty(str){
    if(str == null){
        return "";
    }else{
        return str;
    }
}

//当值为null或""时 转为0
function retuenZero(str){
    if(str == null || str ==""){
        return 0;
    }else{
        return str;
    }
}

//前台金额转为千分位显示
function toThousands(num) {
    if(num == null || num == ""){
        return 0;
    }else{
        return (num-0).toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
    }
}

//前台金额解除千分位显示
function relieveThousands(str) {
    if(str == null || str == ""){
        return 0;
    }else{
        return str.replace(/,/g,"");
    }
}

//时间戳格式的转换
function getMyDate(str){
    if(str == null || str == ""){
        str = "" ;
        return str;
    }
    var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth()+1,
        oDay = oDate.getDate(),
        oHour = oDate.getHours(),
        oMin = oDate.getMinutes(),
        oSen = oDate.getSeconds(),
        oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay);// +' '+ getzf(oHour) +':'+ getzf(oMin) +':'+getzf(oSen);//最后拼接时间
    return oTime;
}

function getMyDate1(str){
    var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth()+1,
        oDay = oDate.getDate(),
        oHour = oDate.getHours(),
        oMin = oDate.getMinutes(),
        oSen = oDate.getSeconds(),
        oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay) +' '+ getzf(oHour) +':'+ getzf(oMin) +':'+getzf(oSen);//最后拼接时间
    return oTime;
}

//补0操作
/*function getzf(num){
    if(parseInt(num) < 10){
        num = '0'+num;
    }
    return num;
}*/

//日期比较
function compareDate(s1,s2){
    return ((new Date(s1.replace(/-/g,"\/")))>=(new Date(s2.replace(/-/g,"\/"))));
}

//限制字符串长度为10（事件监听）
/*function inputNum(name,num){
     if($(name).val().length > num){
        $(name).val( $(name).val().substring(0,num) );
     }
}


var patrn=/[`~!@#$%^&*()_+<>?:"{},./;'[]]/im;*/
