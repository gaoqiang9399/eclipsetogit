var Reassignment_List = function(window, $) {
	var _init = function() {
		var flowApprovalNo = $("#flowApprovalNo").val();// 数据来源, 多个用逗号分隔, 赋值详见MfQueryEntrance.js. 融资审批:apply_approval, 合同审批:contract_approval, 放款审批:putout_approval
		myCustomScrollbar({
			obj : "#content", // 页面内容绑定的id
			url : webPath+"/reassignment/findByPageAjax?flowApprovalNo=" + flowApprovalNo, // 列表数据查询的url
			tableId : "tablereassignment", // 列表数据查询的table编号
			tableType : "thirdTableTag", // table所需解析标签的种类
			pageSize : 30 // 加载默认行数(不填为系统默认行数)
			// ,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
		});
	};

	/** 改派 */
	var _reAssign = function(parm) {
		var wkfTaskNo = parm.split("?")[1].split("&")[0].split("=")[1];
		var bizPkNo = parm.split("?")[1].split("&")[1].split("=")[1];
		var pasNo = parm.split("?")[1].split("&")[2].split("=")[1];
		// var url = location.href;
		// window.top.openBigForm(webPath+"/reassignment/changeUser?appId=" + appId + "&taskId=" + taskId, "重新分派", url, "50%", "50%");
		top.autoFlag=false;
		top.window.openBigForm(webPath+"/reassignment/changeUser?wkfTaskNo=" + wkfTaskNo + "&bizPkNo=" + bizPkNo+ "&pasNo=" + pasNo, "重新分派", function() {
            if(top.autoFlag){
                updateTableData();
            }
		});
	};

    var _getDetailPage = function(obj,url){
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        top.LoadingAnimate.start();
        window.location.href=url;
        event.stopPropagation();
    }
    return {
        init : _init,
        reAssign : _reAssign,
        getDetailPage:_getDetailPage
    };
}(window, jQuery);



function updateInfo(obj) {
	var flag = submitJsMethod(obj, '');
	if (flag) {
		var url = webPath+"/task/reAssignAjax";
		var dataParam = JSON.stringify($(obj).serializeArray());
		jQuery.ajax({
			url : url,
			data : {
				ajaxData : dataParam
			},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					alert(top.getMessage("SUCCEED_OPERATION"), 1);
				} else if (data.flag == "error") {
					if (data.flag !== undefined && data.flag != null && data.flag != "") {
						alert(data.msg, 0);
					} else {
						alert(top.getMessage("FAILED_OPERATION", " "), 0);
					}
				}
			},
			error : function(data) {
				alert(top.getMessage("FAILED_OPERATION", " "), 0);
			}
		});
	}
}
