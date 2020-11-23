var MfBusRiskAudit_accurateList=function(window,$){
	//初始化列表
	var _init=function(){
		myCustomScrollbar({
            obj:"#content",//页面内容绑定的id
            url:webPath+"/mfBusRiskAudit/findAccurateByPageAjax?busModel="+busModel,//列表数据查询的url
            tableId:"tableriskAuditAccurateList",//列表数据查询的table编号
            tableType:"tableTag",//table所需解析标签的种类
            pageSize:30,//加载默认行数(不填为系统默认行数)
            topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
        });
	};
    var _initForNotice=function(){
        myCustomScrollbar({
            obj:"#content",//页面内容绑定的id
            url:webPath+"/mfBusRiskAudit/findAccurateByPageAjaxForNotice?busModel="+busModel,//列表数据查询的url
            tableId:"tableriskAuditAccurateListNotice",//列表数据查询的table编号
            tableType:"tableTag",//table所需解析标签的种类
            pageSize:30,//加载默认行数(不填为系统默认行数)
            topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
        });
    };
	var _getDetailPage = function (obj,url){
        if(busModel=="12"){
            if(url!=""&&url!=null) {
                var parm = url.split("?")[1];
                var appId = parm.split("&")[0].split("=")[1];
                var cusNo = parm.split("&")[1].split("=")[1];
            }
                if(appId.indexOf("SX")>-1){
                url = "/mfCusCreditApply/getCusCreditView?creditAppId="+appId+"&cusNo="+cusNo+"&busEntrance=credit"+"&entrance=credit";
            }
        }
	    if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		// top.LoadingAnimate.start();
		// window.location.href=url;
        top.window.openBigForm(url,"详情",function(){
            // updateTableData();//重新加在列表数据
        });
	};




    //选择稽核类型
    function _chooseAuditType(obj,url){
        if(url!=""&&url!=null) {
            var parm = url.split("?")[1];
            var appId = parm.split("=")[1];
        }
        if(busModel=="12"){
            alert("确认发送通知",2,function(){
                $.ajax({
                    url: webPath+"/mfBusRiskAudit/pushMessage",
                    type:"post",
                    dataType:"json",
                    data:{appId:appId,auditType:''},
                    error:function(){
                        alert('发送通知发生异常', 0);
                    },
                    success:function(data){
                        window.top.alert(data.msg, 1);
                    }
                });
            });
        }else {
            //url = webPath+'/mfBusRiskAudit/chooseAuditType?appId='appId;
            top.addFlag = false;
            top.htmlStrFlag = false;
            top.createShowDialog(webPath+url,"选择稽核类型",'50','50',function(){
            });
        }
    };

    //选择稽核类型
    function _chooseAuditConfig(obj,url){
        if(url!=""&&url!=null) {
            var parm = url.split("?")[1];
            var appId = parm.split("=")[1];
        }
        //url = webPath+'/mfBusRiskAudit/chooseAuditType?appId='appId;
        top.addFlag = false;
        top.htmlStrFlag = false;
        top.createShowDialog(webPath+url,"选择稽核类型",'50','50',function(){
        });
    };

	//打开登记页面
    function _input(obj,url){
        if(url!=""&&url!=null) {
            var parm = url.split("?")[1];
            var appId = parm.split("=")[1];
        }
        url = '/mfBusRiskAudit/chooseAuditConfig?appId='+appId+'&busModel='+busModel;
		top.window.openBigForm(webPath+url,"稽核登记",function(){});
    };

    //批量通知
    var _auditTaskBatch=function(){
        var appIdStr = "";
        $('.table_content #tab').find($('input[type=checkbox]:checked')).each(function () {
            val = this.value.split('&') [0] ;
            appIdStr=appIdStr+","+val.split("=")[1];
        });
        if(appIdStr==""){
            window.top.alert(top.getMessage("FIRST_SELECT_FIELD","稽核通知的数据"), 0);
            return false;
        }
        appIdStr=appIdStr.substr(1);
        if(busModel=="12"){
            alert("确认发送通知",2,function(){
                $.ajax({
                    url: webPath+"/mfBusRiskAudit/pushMessage",
                    type:"post",
                    dataType:"json",
                    data:{appId:appIdStr,auditType:''},
                    error:function(){
                        alert('发送通知发生异常', 0);
                    },
                    success:function(data){
                        window.top.alert(data.msg, 1);
                    }
                });
            });
        }else {
            var url = '/mfBusRiskAudit/chooseAuditType?appId='+appIdStr;
            top.addFlag = false;
            top.htmlStrFlag = false;
            top.createShowDialog(webPath+url,"选择稽核类型",'50','50',function(){
            });
        }
    };
    var _exportExcel = function(){
        var appIdStr = "";
        $('.table_content #tab').find($('input[type=checkbox]:checked')).each(function () {
            val = this.value.split('&') [0] ;
            appIdStr=appIdStr+","+val.split("=")[1];
        });
        if(appIdStr==""){
            window.top.alert(top.getMessage("FIRST_SELECT_FIELD","需要导出的数据"), 0);
            return false;
        }
        var tableId = "tableriskAuditAccurateList";
        var url = webPath + "/mfBusRiskAudit/exportExcelAjax";
        window.top.location.href = encodeURI(url +  "?tableId=" + tableId+'&appId='+appIdStr+'&busModel='+busModel);
    };

	return{
		init:_init,
        initForNotice:_initForNotice,
        exportExcel:_exportExcel,
		getDetailPage:_getDetailPage,
        chooseAuditType:_chooseAuditType,
        auditTaskBatch:_auditTaskBatch,
        input:_input
	};
}(window,jQuery)
