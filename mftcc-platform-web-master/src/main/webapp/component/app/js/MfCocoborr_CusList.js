;
var MfCocoborr_CusList = function(window, $) {
	var _init = function () {
        var coborrNos = "";
        if (coborrNo!=""){
            coborrNos = coborrNo.split("|");
        }
        myCustomScrollbar({
            obj : "#content",//页面内容绑定的id
            url : webPath+"/mfUserPermission/selectCocoboListAjax",//列表数据查询的url
            data:{
                cusNo:cusNo,
                formId:"initLoanApplyBase",
                element:"coborrName",
                cusBaseType:"",
                cusType:"",
            },
            tableId : "tableselectcocoborrlist",//列表数据查询的table编号
            tableType : "thirdTableTag",//table所需解析标签的种类
            pageSize : 30,//加载默认行数(不填为系统默认行数)
            topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
            callback:function(){//方法执行完回调函数（取完数据做处理的时候）
                if (coborrNo!=""){
                    $("#tablist>tbody tr").each(function(trIndex, tr){
                        var trObj = $(tr);
                        var cusNo = trObj.find("input[name=cusNo]").val();
                        for(var i=0;i<coborrNos.length;i++){
                            if (coborrNos[i]!=""){
                                if (cusNo.indexOf(coborrNos[i])!=-1){
                                    trObj.find("input[name=cusNo]").prop("checked","checked");
                                }
                            }
                        }
                    });
                }

            }
        });
	};
	var _selectCusInfo = function (){
        var coborrNo="";
        var coborrName="";
        var cusInfo = new Object();
        if($("#content input:checkbox[name='cusNo']:checked").length>0){
            $("#content input:checkbox[name='cusNo']:checked").each(function(){
                var cusInfo = $(this).val();
                var cusObj = StringUtil.urlParamsToObj(cusInfo);
                coborrNo = coborrNo+cusObj.cusNo+"|";
                coborrName = coborrName +cusObj.cusName+"|";
            });
        }
        cusInfo.coborrNo = coborrNo;
        cusInfo.coborrName = coborrName;
        parent.dialog.get("cocoborrMutiDialog").close(cusInfo);
	}

	var _closeDialog = function(){
        parent.dialog.get("cocoborrMutiDialog").close();
	}
    var _inputCus = function(){
        top.window.openBigForm(webPath+"/mfCusCustomer/inputCoborr","新增客户", function(){
            _init();
        });
    }
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		selectCusInfo:_selectCusInfo,
		closeDialog:_closeDialog,
        inputCus:_inputCus
	};
}(window, jQuery);