var MfCusMainBusiness = function(window,$){
    var _init = function(){
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                theme : "minimal-dark",
                updateOnContentResize : true
            }
        });

    }
    //上游信息新增方法
    var _infoAdd= function () {
        var mainType="2";
        top.addMainInfo=false;
        top.window.openBigForm(webPath+"/mfCusMainBusiness/inputForList?cusNo="+cusNo+"&mainType=2"+"&cusName="+cusName,'上下游信息及政策',function() {
            if(top.addMainInfo){
                _getInfoListHtmlForAjax(mainType);
            }
        });
    }
    //下游客户新增
    var _cusInfoAdd = function () {
        var mainType="3";
        top.addMainInfo=false;
        top.window.openBigForm(webPath+"/mfCusMainBusiness/inputForList?cusNo="+cusNo+"&mainType=3"+"&cusName="+cusName,'上下游信息及政策',function() {
            if(top.addMainInfo){
                _getInfoListHtmlForAjax(mainType);
            }
        });
    }
    //上游信息列表
    var  _getInfoListHtmlForAjax = function (mianType) {
        $.ajax({
            url: webPath + "/mfCusMainBusiness/getInfoListHtmlForAjax",
            data:{cusNo:cusNo,
                mainType:mianType
                },
            type:'post',
            dataType:'json',
            success: function (data) {
                if (data.flag == "success") {
                    if(data.mainType=="2"){//上游信息
                        $("#info").html(data.tableData);
                    }else{//下游客户
                        $("#cusInfo").html(data.tableData);
                    }
                } else {

                }
            }, error: function () {
                LoadingAnimate.stop();
            }
        });
    }

    //上游信息,下游客户保存方法
    var _insertInfo = function (obj) {
        var data = JSON.stringify($(obj).serializeArray());
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            $.ajax({
                type : "POST",
                data:{ajaxData:data},
                url : webPath+"/mfCusMainBusiness/insertInfo",
                dataType : "json",
                success : function(data) {
                    if(data.flag=="success"){
                        window.top.alert(data.msg,1);
                        if(typeof (data.mainType)!="undefined" && data.mainType=="2"){//上游信息
                            top.addMainInfo = true;
                            var infoList=$('#bigFormShowiframe', parent.document).contents().find("#info");
                            if(infoList.length<=0){
                                infoList=$('#taskShowDialogiframe', parent.document).contents().find("#info");
                            }
                            infoList.html(data.htmlStr);
                            myclose_click();
                        }else{
                            top.addMainInfo = true;
                            var infoList=$('#bigFormShowiframe', parent.document).contents().find("#cusInfo");
                            if(infoList.length<=0){
                                infoList=$('#taskShowDialogiframe', parent.document).contents().find("#cusInfo");
                            }
                            infoList.html(data.htmlStr);
                            myclose_click();
                        }

                    }else{
                        window.top.alert(data.msg,0);
                    }
                },
                error : function(xmlhq, ts, err) {
                    console.log(xmlhq);
                    console.log(ts);
                    console.log(err);
                }
            });
        }
    }

    //关闭登记信息框则清除没保存的上下游信息
    var _closeInfo = function () {
        $.ajax({
            url: webPath + "/mfCusMainBusiness/deleteAjaxForSelect",
            data:{cusNo:cusNo},
            type:'post',
            dataType:'json',
            success: function (data) {
                if (data.flag == "success") {
                    myclose_click();
                } else {

                }
            }, error: function () {
                LoadingAnimate.stop();
            }
        });
    }
    /** 保存 */
    var _ajaxSave = function(obj) {
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            ajaxInsertCusForm(obj);
        }
    };

   var _deleteAjax = function (obj,url) {
       if(url.substr(0,1)=="/"){
           url =webPath + url;
       }else{
           url =webPath + "/" + url;
       }
       $.ajax({
           url: url,
           type:'post',
           dataType:'json',
           success: function (data) {
               if (data.flag == "success") {
                   window.top.alert(data.msg,1);
                    if(data.mainType=="2"){
                        _getInfoListHtmlForAjax("2");
                    }else{
                        _getInfoListHtmlForAjax("3");
                    }

               } else {
                   alert("数据查询出错")
               }
           }
       });
   }
   var _updateSave = function (obj) {
       var flag = submitJsMethod($(obj).get(0), '');
       if (flag) {
           ajaxInsertCusForm(obj);
       }
   }
    return {
        init : _init,
        infoAdd : _infoAdd,
        cusInfoAdd:_cusInfoAdd,
        insertInfo:_insertInfo,
        closeInfo:_closeInfo,
        ajaxSave : _ajaxSave,
        getInfoListHtmlForAjax:_getInfoListHtmlForAjax,
        deleteAjax:_deleteAjax,
        updateSave:_updateSave
    };
}(window, jQuery);