;
var MfGuaranteeRegistration_List = function(window, $) {
	var _init = function() {
        myCustomScrollbar({
            obj : "#content", //页面内容绑定的id
            url : webPath + "/mfGuaranteeRegistration/findByPageAjax", //列表数据查询的url
            tableId : "tableguarantee0001", //列表数据查询的table编号
            tableType : "thirdTableTag", //table所需解析标签的种类
            pageSize:30 ,//加载默认行数(不填为系统默认行数)
            //,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
			callback:function(){
				let td = $('#content tbody tr').find(".guarantee");
                td.each(function (index, ele) {
					let guaSts = $(ele).prev().find("[name='guaStsShow']").val();
					let pactId = $(ele).next().find("[name='pactId']").val();
					if(guaSts == "0"){
						let html = "<a href=\"javascript:void(0);\" onclick=\"MfGuaranteeRegistration_List.insert(this,'/mfGuaranteeRegistration/input?pactId=" + pactId +"&flag=insert');return false;\" class=\"abatch\">保函登记</a>";
                        $(ele).html(html);
					}else if(guaSts == "1"){
                        let html = "<a href=\"javascript:void(0);\" onclick=\"MfGuaranteeRegistration_List.update(this,'/mfGuaranteeRegistration/input?pactId=" + pactId +"&flag=edit');return false;\" class=\"abatch\">编辑</a>";
                        $(ele).html(html);
					}else if(guaSts == "4"){
                        $(ele).html("决策会审批中");
                    }else{
                        let html = "<a href=\"javascript:void(0);\" onclick=\"MfGuaranteeRegistration_List.detail(this,'/mfGuaranteeRegistration/input?pactId=" + pactId +"&flag=detail');return false;\" class=\"abatch\">保函详情</a>";
                        $(ele).html(html);
					}
                });
			}
        });
	};
	//跳转至新增
	var _insert = function(obj,url) {
		top.openBigForm(webPath + url,"保函登记", function(){
 			updateTableData();
 		});	
	};
	//跳转至更新
	var _update = function(obj,url) {
		top.openBigForm(webPath + url,"编辑", function(){
 			updateTableData();
 		});
	};
	//跳转至详情
	var _detail = function(obj,url) {
		top.openBigForm(webPath + url,"保函详情", function(){
 			updateTableData();
 		});	
	};
	//跳转至详情
	var _getDetailPage = function(obj,url) {
		top.openBigForm(webPath + url,"详情", function(){
 		});
	};
	return {
		init : _init,
        insert:_insert,
        update:_update,
        detail:_detail,
        getDetailPage:_getDetailPage,
	};
}(window, jQuery);
