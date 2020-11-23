var cusImportExcel = function(window,$){
    var _init = function(){
        $("body").mCustomScrollbar({
            advanced:{
                updateOnBrowserResize:true
            },
            autoHideScrollbar: true
        });
        _getExcelList();
    };

    //保存表单时保存上传的excel文件中的数据到数据库中
    var _uploadAndSubmitData = function(finDataVal,cusNo,finRptType,finRptDate,cusName) {
        var url =webPath+"/importexcel/mfCusImportExcel/checkImportExcel";
        $.ajax({
            type:"post",
            url:url,
            async:false,
            data:{
                allPath:finDataVal,
                cusNo:cusNo,
                finRptType:finRptType,
                finRptDate:finRptDate,
                cusName:cusName
            },
            success:function(data){
                if(data.flag=="success"){
                    top.addFlag = true;
                    //触发回调函数
                    $(top.window.document).find("#showDialog .close").click();
                }else if(data.flag=="error"){
                    alert(data.msg,0);
                }
            }
        });
    };

    var _updateHis = function(obj,url){

        $.ajax({
            type:"post",
            url:url,
            async:false,
            success:function(data){
                if(data.flag = 'success'){
                    window.location.reload();
                }else{
                    alert("删除失败,请联系管理员",0);
                }
            }
        });

    }

    var _getExcelList = function(){
        var url = webPath+"/mfCusImportExcel/getHisList";
        $(function(){
            var table = "tableMfCusImportExcelHis";
            myCustomScrollbar({
                obj : "#content",//页面内容绑定的id
                url : url,//列表数据查询的url
                tableId : table,//列表数据查询的table编号
                tableType : "tableTag",//table所需解析标签的种类
                pageSize : 30,//加载默认行数(不填为系统默认行数)
                topHeight : 100 // 顶部区域的高度，用于计算列表区域高度。
            });
        });
    };
    function _againDoc(obj,url){
        window.location.href = webPath+url;
    };

    //跳转历史数据导入校验结果
    var _uploadHisResult = function (obj,url) {
        top.openBigForm(webPath+url,"历史数据导入校验结果", function () {

        });
    };

    //跳转历史数据导入新增页面
    var _uploadExcel = function () {
        top.openBigForm(webPath+"/mfCusImportExcel/uploadExcel","历史数据导入", function () {
            _init();
        });
    };
    //下载错误数据
    var _downloadErrorHisDataAjax = function(obj,url){
        var params = url.split("?")[1].split("&");
        var failCount = params[2].split("=")[1];
        var id = params[0].split("=")[1];
        if (failCount>0){
            LoadingAnimate.start();
            $.ajax({
                type:"post",
                url:webPath+url,
                async:false,
                dataType: "json",
                success:function(data){
                    if(data.flag == 'success'){
                        LoadingAnimate.stop();
                        window.location.href = webPath+"/mfCusImportExcel/downErrorDataFile?id="+id;
                    }else{
                        LoadingAnimate.stop();
                        alert(data.msg,0);
                    }
                }
            });
        }else{
            alert("不存在错误数据!",1);
        }
    };

    //下载历史数据导入模板
    var _downloadModel = function(){
        var elemIF = document.createElement("iframe");
        elemIF.src = webPath + "/mfCusImportExcel/getZipFileDownloadByPath";
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    };

    //跳转历史数据导入详情
    var _inputDetail = function (obj,url) {
        top.openBigForm(webPath+url,"历史数据导入详情", function () {
            _init();
        });
    };
    return{
        init:_init,
        uploadAndSubmitData:_uploadAndSubmitData,
        updateHis : _updateHis,
        againDoc : _againDoc,
        uploadExcel:_uploadExcel,
        uploadHisResult:_uploadHisResult,
        downloadErrorHisDataAjax:_downloadErrorHisDataAjax,
        downloadModel:_downloadModel,
        inputDetail:_inputDetail
    };
}(window,jQuery);