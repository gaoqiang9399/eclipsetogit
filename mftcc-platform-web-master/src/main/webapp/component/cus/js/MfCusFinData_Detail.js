;
var MfCusFinData_Detail = function(window, $) {
	/**
	 * 在此处开始定义内部处理函数。
	 */
	var _init = function () {
		$(function(){
           //获取财务列表
            _getCusFinMainList();
		 });
	};


	//获取财务指标列表
	var _getCusFinMainList = function(){
            $.ajax({
                url:webPath + "/cusFinMain/queryCusFinDataAjax",
                data:{cusNo:cusNo},
                success:function(data){
                    _getFinDataHtml(data);
                },error:function(){

                }
            });
    };


	var _getFinDataHtml =function (data) {
        if(data.cusFinMainList.length > 0) {
            var htmlStr = "";
            $.each(data.cusFinMainList, function (i, cusFinMain) {
                var viewStr = webPath + "/cusFinMain/inputReportView?finRptType=" + cusFinMain.reportType + "&finRptDate=" + cusFinMain.weeks + "&cusNo=" + cusFinMain.cusNo + "&accRule=1&finRatioEditFlag=1";
                var confirStr = webPath + "/cusFinMain/updateReportConfirmAjax?finRptType=" + cusFinMain.reportType + "&finRptDate=" + cusFinMain.weeks + "&cusNo=" + cusFinMain.cusNo;
                var analysisStr = webPath + "/cusFinRatioData/updateRatioAnalysisAjax?finRptType=" + cusFinMain.reportType + "&finRptDate=" + cusFinMain.weeks + "&cusNo=" + cusFinMain.cusNo;
                var delStr = "/cusFinMain/deleteAjax?finRptType=" + cusFinMain.reportType + "&finRptDate=" + cusFinMain.weeks + "&cusNo=" + cusFinMain.cusNo;
                var zcStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>',
                    proStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>',
                    cashStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>',
                    subjectStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>';
                var analysisOptStr = '<a id="finDataAnalysis" class="" onclick="MfCusFinData_Detail.cusFinRatioAnalysis(this,\'' + analysisStr + '\');return false;" href="javascript:void(0);">指标分析</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                var opStr = '<span class="listOpStyle">数据确认</span>&nbsp;&nbsp;&nbsp;&nbsp;'+analysisOptStr;
                    if (cusFinMain.reportSts != 2) {
                        opStr = '<a id="finDataFirm" class="abatch" onclick="MfCusFinData_Detail.confirmFinMain(this,\'' + confirStr + '\');return false;" href="javascript:void(0);">数据确认</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                        opStr = opStr + analysisOptStr;
                    } else {
                        zcStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>',
                            proStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>',
                            cashStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>',
                            subjectStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>';
                    }
                if(cusFinMain.isUsed != 1){
                    opStr = opStr + '<a id="finDataDel" class="abatch_del" onclick="FormFactor.deleteTrAjax(this,\'' + delStr + '\');return false;" href="javascript:void(0);">删除</a>';
                }
                if(cusFinMain.isUsed == 1){
                    opStr = opStr + '<span class="listOpStyle">删除</span>';
                }

                if(cusFinMain.assetsDataId == null||cusFinMain.assetsDataId == ""){
                    zcStr = '<a class="abatch" href="javascript:void(0);" onclick="MfCusFinData_Detail.getPfsDialog();return false;">上传</a>';
                    if (cusFinMain.reportSts == 2) {
                        zcStr = "上传";
                    }
                }
                if(cusFinMain.incomeDataId==null || cusFinMain.incomeDataId==""){
                    proStr = '<a class="abatch" href="javascript:void(0);" onclick="MfCusFinData_Detail.getPfsDialog();return false;">上传</a>';
                    if (cusFinMain.reportSts == 2) {
                        proStr = "上传";
                    }
                }
                if(cusFinMain.cashDataId==null || cusFinMain.cashDataId==""){
                    cashStr = '<a class="abatch" href="javascript:void(0);" onclick="MfCusFinData_Detail.getPfsDialog();return false;">上传</a>';
                    if (cusFinMain.reportSts == 2) {
                        cashStr = "上传";
                    }
                }
                if(cusFinMain.balanceDataId==null || cusFinMain.balanceDataId==""){
                    subjectStr = '<a class="abatch" href="javascript:void(0);" onclick="MfCusFinData_Detail.getPfsDialog();return false;">上传</a>';
                    if (cusFinMain.reportSts == 2) {
                        subjectStr = "上传";
                    }
                }
                htmlStr += '<tr>' +
                    '<td align="center" width="10%">' +
                    '<a class="abatch" onclick="MfCusFinData_Detail.reportView(this,\'' + viewStr + '\');return false;" href="javascript:void(0);">' + cusFinMain.weeks + '</a>' +
                    '</td>' +
                    '<td align="center" width="15%">' +
                    zcStr +
                    '</td>' +
                    '<td align="center" width="15%">' +
                    proStr +
                    '</td>' +
                    '<td align="center" width="15%">' +
                    cashStr +
                    '</td>' +
                    '<td align="center" width="15%">' +
                    subjectStr +
                    '</td>' +
                    '<td align="center" width="20%">' +
                    opStr +
                    '</td>' +
                    '</tr>';
            });
            $("#cusFinMainList tbody").html(htmlStr);
        }
	};


	var _getPfsDialog = function(){
        top.isUpload = false;
        top.openBigForm(webPath+'/cusFinMain/getListPage1?cusNo='+cusNo,'财务报表', function() {
            if(top.isUpload){  //财务报表上传成标志
                $.ajax({
                    url : webPath+"/cusFinMain/queryCusFinDataAjax",
                    data : {cusNo : cusNo},
                    success : function(data) {
                        _getFinDataHtml(data);
                    }
                });
            }
        });
    };

	//数据确认
	var _confirmFinMain = function(obj,url){
        var parm = "?"+url.split("?")[1];
        $.ajax({
            type:"post",
            url:webPath+"/cusFinMain/checkFinDataAjax"+parm,
            dataType:"json",
            success:function(data){
                if(data.flag=="success"){
                    if(data.checkFlag == "success"){
                        alert(top.getMessage("CONFIRM_OPERATION","数据确认"),2,function(){
                            _doCofrimData(obj,url);
                        });
                    }else{
                        alert(top.getMessage("CONFIRM_FIN_VERIFY"),2,function(){
                            _doCofrimData(obj,url);
                        });

                    }
                }else if(data.flag=="error"){
                    alert(top.getMessage("ERROR_FIN_VERIFY"),0);
                }
            },error:function(){
                window.top.aler;t(top.getMessage("ERROR_FIN_VERIFY"),0);
            }
        });
    };

    //执行确认操作
    var _doCofrimData = function(obj,url){
        var tdObj = $(obj).parent();
        $.ajax({
            url:url,
            success:function(data){
                if(data.flag=="success"){
                    $(tdObj).parent().find('td:not(:first)').find(".color_theme").css("color","gray");
                    $(tdObj).parent().find('td:not(:first)').find('.abatch').css("color","gray");
                    $(tdObj).parent().find('td:not(:first)').find('.abatch').removeAttr('href');//去掉a标签中的href属性
                    $(tdObj).parent().find('td:not(:first)').find('.abatch').removeAttr('onclick');//去掉a标签中的onclick事件
                }else if(data.flag=="error"){
                    window.top.alert(top.getMessage("ERROR_FIN_VERIFY"),0);
                }
            },error:function(){
                window.top.alert(top.getMessage("ERROR_FIN_VERIFY"),0);
            }
        });

    };

	var _reportView = function (obj,url) {
	    top.openBigForm(url,'财务报表',false);
    };

    /**
     * 方法描述：财务指标分析
     * @param obj
     * @param url
     * @private
     */
	var _cusFinRatioAnalysis = function (obj,url) {
        $.ajax({
            url:url,
            success:function(data){
                if(data.flag=="success"){
                    window.top.alert(data.msg,1);
                }else if(data.flag=="error"){
                    window.top.alert(data.msg,0);
                }
            },error:function(){
                window.top.alert(top.getMessage("FAILED_OPERATION","指标分析"),0);
            }
        });
    };

    /**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
        getCusFinMainList : _getCusFinMainList,
        reportView : _reportView,
        getPfsDialog : _getPfsDialog,
        confirmFinMain : _confirmFinMain,
        cusFinRatioAnalysis : _cusFinRatioAnalysis,
	};
	
}(window, jQuery);

