;
var CertiInfoList = function(window, $){
    var _init = function(){
        // myCustomScrollbar({
        //     obj : "#content",//页面内容绑定的id
        //     url : webPath+"/certiInfo/findByPageAjax?appId=" + appId+"&collateralType=account",//列表数据查询的url
        //     tableId : "tablecertiReceTransBaseList",//列表数据查询的table编号
        //     tableType : "tableTag",//table所需解析标签的种类
        //     pageSize : 30,//加载默认行数(不填为系统默认行数)
        //     bottomHeight:250
        // });

        $("#scroll-content").mCustomScrollbar({
			advanced:{
				updateOnContentResize:true
			}
		});
    };

    //账款转让
    var _insertCertiInfo = function(){
        top.addFlag = false;
        top.htmlStr = $("#accountList").html();
        top.msg=top.getMessage("FAILED_OPERATION"," ");
        top.openBigForm(webPath+"/certiInfo/inputCertiInfo?appId="+appId,'账款转让',function() {
            if(top.addFlag){
                if(top.msg!=""){
                    alert(top.msg,3);
                }
               $("#accountList").html(top.htmlStr);
            }
        });
    };

    var _getDetailPage = function(obj,url){
        top.openBigForm(url,'账款转让详情',myclose);
    };

    //提交审批流程
    var _submitProcess = function(){
        var certiIds="";
        $("#accountList").find("tbody tr").each(function(index) {
            certiIds = certiIds+$(this).find("input[name=certiId]").val()+","
        });
        if(certiIds!=""){
            certiIds = certiIds.substring(0,certiIds.length-1);
        }

        $.ajax({
            url : webPath+"/certiInfo/submitProcess",
            data:{appId:appId,certiIds:certiIds},
            success:function(data){
                if(data.flag=="success"){
                    alert(data.msg,3);
                    $("#accountList").html(data.htmlStr);
                }
            }
        });
    };

    return {
        init:_init,
        insertCertiInfo:_insertCertiInfo,
        getDetailPage:_getDetailPage,
        submitProcess:_submitProcess,
    };
}(window, jQuery);