;
var MfBusInstitutions_List = function(window, $) {
	var _init = function() {
		   myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath+"/mfBusInstitutions/findByPageAjax", //列表数据查询的url
				tableId : "tableinstitutionsbase", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
		    });
	};
	//跳转至新增
	var _applyInsert = function() {
		top.openBigForm(webPath+"/mfBusInstitutions/input","新增机构申请", function(){
 			updateTableData();
 		});	
	};
	//跳转至详情
	var _dimissionDetail = function(obj,url) {
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.openBigForm(url+"&entryFlag=1","机构申请详情", function(){
 			updateTableData();
 		});	
	};
	var _printApproveTable = function(obj,url){
		var oaAppId = url.split("?")[1].split("=")[1];
		var poCntJson = {
				filePath : "",
				fileName : fileName,
				oaAppId : oaAppId,
				saveBtn : "0",
				fileType : 0
			};
		mfPageOffice.openPageOffice(poCntJson);
	};

	var _deleteAjax = function(obj, url) {
        alert(top.getMessage("CONFIRM_DELETE"), 2, function() {
            var ajaxParam = {};
            url=webPath+url;
            $.post(url, ajaxParam, function(data) {
                if (data.flag == "success") {
                    alert(top.getMessage("SUCCEED_OPERATION"), 1);
                    updateTableData();
                }else {
                    alert(top.getMessage("FAILED_OPERATION","操作失败！"),0);
				}
			})
		})

    }

    var _useAjax = function (obj, url) {
        alert(top.getMessage("CONFIRM_OPERATION","禁用"), 2, function() {
            var ajaxParam = {};
            url=webPath+url;
            $.post(url, ajaxParam, function(data) {
                if (data.flag == "success") {
                    alert(top.getMessage("SUCCEED_OPERATION"), 1);
                    updateTableData();
                }else {
                    alert(top.getMessage("FAILED_OPERATION","操作失败！"),0);
                }
            })
		})
    }
    var _changeYesUseFlagAjax = function (obj, url) {
        alert(top.getMessage("CONFIRM_OPERATION","启用"), 2, function() {
            var ajaxParam = {};
            url=webPath+url;
            $.post(url, ajaxParam, function(data) {
                if (data.flag == "success") {
                    alert(top.getMessage("SUCCEED_OPERATION"), 1);
                    updateTableData();
                }else {
                    alert(top.getMessage("FAILED_OPERATION","操作失败！"),0);
                }
            })
        })
    }

	return {
		init : _init,
		dimissionDetail:_dimissionDetail,
		applyInsert:_applyInsert,
		printApproveTable:_printApproveTable,
		deleteAjax : _deleteAjax,
		useAjax : _useAjax,
        changeYesUseFlagAjax :_changeYesUseFlagAjax
	};
}(window, jQuery);


