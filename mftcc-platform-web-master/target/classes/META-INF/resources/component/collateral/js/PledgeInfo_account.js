;
var PledgeInfo_account = function(window, $) {
    //
	var _init = function(){
        myCustomScrollbar({
            url:webPath+"/mfBusPact/getDataByAccount",//列表数据查询的url
            obj : "#content",//页面内容绑定的id
            tableId : tableId,//列表数据查询的table编号
            tableType : "tableTag",//table所需解析标签的种类
            pageSize : 30,//加载默认行数(不填为系统默认行数)
            topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
        });
	};
    //详情合同详情
    var _initPact = function (){
        $.ajax({
            url:webPath+"/mfBusPact/getPactDetailFormAjax?appId="+appId+"&pactId="+pactId+"&formId=pact004",
            type:'post',
            dataType:'json',
            success:function(data){
                var html = data.htmlStr;
                $("#pactDetailForm").empty().html(html);
            }
        });
    };

    //展示该合同下的应收账款的信息
    var _initAccount = function(){
        $.ajax({
            type: "post",
            dataType: 'json',
            url: webPath + "/pledgeBaseInfo/getAccountByAppId",
            data: {
                appId: appId
            },
            async: false,
            success: function (data) {
                if (data.flag == "success") {
                    $("#content").html(data.tableHtml);
                } else {
                 alert("数据查询出错")
                }
            }
        });
    };
    //展示该合同下的应收账款的信息
    var _deleteBill= function(obj,url){
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        $.ajax({
            type: "post",
            dataType: 'json',
            url:url+"&tableId=tableaccountInfoBase",
            async: false,
            success: function (data) {
                if (data.flag == "success") {
                    $("#accountInfoBaseList").html(data.htmlStr);
                } else {
                 alert("数据查询出错")
                }
            }
        });
    };
    //展示应收账款详情
    var _accountDetail = function(obj,url){
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        top.window.openBigForm(url, "合同清单详情", function () {
            window.location.href=webPath+"/mfBusPact/accountInputNew?appId="+appId+"&pledgeNo="+collateralId;
        });

    };

	//导入在保业务的应收账款做更新或者新增
    var _importExcel= function() {
      /*  window.parent.openBigForm(webPath+'/pssSaleBill/importExcel', '销货单导入', function(){
            updateTableData();//重新加载列表数据
        });
        window.location.href = webPath + "/pledgeBaseInfo/importAccountExcel?appId="+appId;*/
        var formData = new FormData($('#upload')[0]);
        $.ajax({
            url:webPath + "/pledgeBaseInfo/importAccountExcel?appId="+appId,
            type:'post',
            dataType:'json',
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            success:function(data){
                if (data.success == true) {
                    $("#content").empty();
                    _initAccount();
                }else{
                    window.top.alert(data.msg,1);
                }
            }
        });

    }
    var _getDetailPage = function (obj, url) {
        top.LoadingAnimate.start();
        window.location.href = webPath + url;
    };
    //应收账款质押合同详情
    var _accountInput = function (obj,url){
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        top.window.openBigForm(url,"应收账款质押",function(){
            _myclose();
        });
    }
    //上传合同清单
    var _uploadBill = function () {
        var url = '/pledgeBaseInfo/uploadBill?appId=' + appId;
        top.window.openBigForm(webPath + url+ "&collateralId=" + collateralId, "上传合同清单", function () {
            window.location.href=webPath+"/mfBusPact/accountInputNew?appId="+appId+"&pledgeNo="+collateralId;
        });
    };
    var _history = function (obj,url) {
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        top.window.openBigForm( url+ "&pledgeNo=" + collateralId, "合同清单历史", function () {
            window.location.href=webPath+"/mfBusPact/accountInputNew?appId="+appId+"&pledgeNo="+collateralId;
        });
    };
    var _addBill = function () {
        var url = '/pledgeBaseInfoBill/input?appId=' + appId;
        top.window.openBigForm(webPath + url+ "&collateralNo=" + collateralId, "添加合同清单", function () {
            window.location.href=webPath+"/mfBusPact/accountInputNew?appId="+appId+"&pledgeNo="+collateralId;
        });
    };
    var _changeReword = function () {
        var url = '/insInfo/changeReword?appId=' + appId;
        top.window.openBigForm(webPath + url+ "&collateralNo=" + collateralId, "合同清单历史", function () {
            window.location.href=webPath+"/mfBusPact/accountInputNew?appId="+appId+"&pledgeNo="+collateralId;
        });
    };
    var _exportExcel = function(obj,url){
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        var tableId = "tableaccountInfoBaseImport";
        window.top.location.href = encodeURI(url +  "&tableId=" + tableId+'&collateralNo='+collateralId);
    };
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
        getDetailPage: _getDetailPage,
        accountInput:_accountInput,
        initPact:_initPact,
        initAccount:_initAccount,
        accountDetail:_accountDetail,
        importExcel:_importExcel,
        uploadBill:_uploadBill,
        exportExcel:_exportExcel,
        deleteBill:_deleteBill,
        history:_history,
        changeReword:_changeReword,
        addBill:_addBill,
    };
}(window, jQuery);