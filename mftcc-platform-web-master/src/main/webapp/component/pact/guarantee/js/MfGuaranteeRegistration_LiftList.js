;
var MfGuaranteeRegistration_LiftList = function(window, $) {
	var _init = function() {
        myCustomScrollbar({
            obj : "#content", //页面内容绑定的id
            url : webPath + "/mfGuaranteeRegistration/findLiftByPageAjax", //列表数据查询的url
            tableId : "tableguaranteeLift", //列表数据查询的table编号
            tableType : "thirdTableTag", //table所需解析标签的种类
            pageSize:30 ,//加载默认行数(不填为系统默认行数)
            //,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
			callback:function(){
                let td = $('#content tbody tr').find(".guarantee");
                td.each(function (index, ele) {
                    let guaSts = $(ele).prev().find("[name='guaStsShow']").val();
                    let pactId = $(ele).next().find("[name='pactId']").val();
                    let liftSts = $(ele).next().next().find("[name='liftStsShow']").val();
                    if(guaSts == "2" && liftSts == "0"){
                        let html = "<a href=\"javascript:void(0);\" onclick=\"MfGuaranteeRegistration_LiftList.update(this,'/mfGuaranteeLift/inputLift?pactId=" + pactId +"');return false;\" class=\"abatch\">解保申请</a>";
                        $(ele).html(html);
                    }else{
                        let html = "<a href=\"javascript:void(0);\" onclick=\"MfGuaranteeRegistration_LiftList.detail(this,'/mfGuaranteeLift/getById?pactId=" + pactId +"');return false;\" class=\"abatch\">解保详情</a>";
                        $(ele).html(html);
                    }
                });
			}
        });
	};

	//跳转至解保申请
	var _update = function(obj,url) {
		top.openBigForm(webPath + url,"解保申请", function(){
 			updateTableData();
 		});
	};
	//跳转至解保详情
	var _detail = function(obj,url) {
		top.openBigForm(webPath + url,"解保详情", function(){
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
        update:_update,
        detail:_detail,
        getDetailPage:_getDetailPage,
	};
}(window, jQuery);
