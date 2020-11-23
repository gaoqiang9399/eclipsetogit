;
var MfEncryptFields_List=function(window,$){
    var _init=function(){
        myCustomScrollbar({
            obj:"#content",//页面内容绑定的id
            url:webPath+"/mfEncryptFields/findByPageAjax.action",//列表数据查询的url
            tableId:"tableEncryptFieldsList",//列表数据查询的table编号
            tableType:"thirdTableTag",//table所需解析标签的种类
            pageSize:30,//加载默认行数(不填为系统默认行数)
            topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
            callback:function(){
                $("table").tableRcswitcher({
                    name:"isUsed"});
            }//方法执行完回调函数（取完数据做处理的时候）
        });
    };

    var _input = function(){
        top.window.flag=false;
        top.window.msg="";
        top.window.openBigForm(webPath+"/mfEncryptFields/input?flag=insert","新增加密信息", function(){
            if(top.window.flag){
                alert(top.window.msg, 3);
                updateTableData(true);
            }
        });
    };



    var _getDetail = function(object, url) {
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        top.window.flag=false;
        top.window.msg="";
        top.window.openBigForm(url,'修改加密信息',function(){
            if(top.window.flag){
                alert(top.window.msg, 3);
                updateTableData(true);
            }
        });
    };


    var _insertOrUpdateAjax = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var url;
            if(opFlag=="update"){
                url=webPath+"/mfEncryptFields/updateAjax";
            }else if(opFlag=="insert"){
                url=webPath+"/mfEncryptFields/insertAjax";
            }
            var dataParam = JSON.stringify($(obj).serializeArray());
            $.ajax({
                url : url,
                type : 'POST',
                data : {ajaxData:dataParam},
                success : function(data) {
                    if (data.flag == "success") {
                        top.window.flag=true;
                        top.window.msg=data.msg;
                        myclose_click();
                    }else{
                        alert(data.msg, 0);
                    }
                },error : function() {
                    alert(top.getMessage("ERROR_SERVER"),0);
                }
            });
        }
    };

    var _cleanCache = function(obj){
            LoadingAnimate.start();
            var url=webPath+"/mfEncryptFields/cleanCache";
            $.ajax({
                url : url,
                type : 'POST',
                success : function(data) {
                    if (data.flag == "success") {
                        alert(data.msg, 3);
                    }else{
                        alert(data.msg, 0);
                    }
                    LoadingAnimate.stop();
                },error : function() {
                    LoadingAnimate.stop();
                  alert(top.getMessage("ERROR_SERVER"),0);
                }
            });
    };

    var _encryptHistoryData = function(obj,url){
        LoadingAnimate.start();
        if(url!=undefined&&url!=""){
            if(url.substr(0,1)=="/"){
                url =webPath + url;
            }else{
                url =webPath + "/" + url;
            }
        }else{
            url=webPath+"/mfEncryptFields/encryptHistoryData?fieldId=";
        }
        $.ajax({
            url : url,
            type : 'POST',
            success : function(data) {
                if (data.flag == "success") {
                    alert(data.msg, 3);
                }else{
                    alert(data.msg, 0);
                }
                LoadingAnimate.stop();
            },error : function() {
                LoadingAnimate.stop();
                alert(top.getMessage("ERROR_SERVER"),0);
            }
        });
    };

    var _dencryptHistoryData = function(obj,url){
        LoadingAnimate.start();
        if(url!=undefined&&url!=""){
            if(url.substr(0,1)=="/"){
                url =webPath + url;
            }else{
                url =webPath + "/" + url;
            }
        }else{
            url=webPath+"/mfEncryptFields/dencryptHistoryData?fieldId=";
        }
        $.ajax({
            url : url,
            type : 'POST',
            success : function(data) {
                if (data.flag == "success") {
                    alert(data.msg, 3);
                }else{
                    alert(data.msg, 0);
                }
                LoadingAnimate.stop();
            },error : function() {
                LoadingAnimate.stop();
                alert(top.getMessage("ERROR_SERVER"),0);
            }
        });
    };


    return{
        init:_init,
        input:_input,
        insertOrUpdateAjax:_insertOrUpdateAjax,
        getDetail:_getDetail,
        cleanCache:_cleanCache,
        encryptHistoryData:_encryptHistoryData,
        dencryptHistoryData:_dencryptHistoryData
    };
}(window,jQuery);
