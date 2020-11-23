
var repayPlan_ListForAccount = function(window, $) {
	var _init = function() {
        $("#plan_content").html(ajaxData.tableHtml);
        $("#tablist").show();
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                updateOnContentResize : true
            }
        });
	};
	//保存还款计划
	var _repayPlanAjax=function(obj){
		//连连使用放款复核真实打款，控制按钮只能点击一次
		$(obj).attr("disabled","disabled");
        var dataForm = JSON.stringify($(obj).serializeArray());
        var fincList = [];
        $("#tab").find("tr").each(function(){
            var tdArr = $(this).children();
            var fincId = tdArr.find("input[name='fincId']").val();
            var fincInfo = new Object();
            fincInfo.fincId = fincId;
            fincList.push(fincInfo);
        });
        var jsonString = JSON.stringify(fincList);
        $.ajax({
            url:webPath+'/mfRepayPlan/repayPlanAjaxForAccount',
            data:{"ajaxData":dataForm,"fincListData":jsonString},
            type:"POST",
            dataType:"json",
            beforeSend:function(){
            },
            success:function(data){
                LoadingAnimate.stop();
                alert(data.msg,3,function(){//生成还款计划成功之后要提示一下，再执行原来的方法
                    top.flag=true;
                    top.putoutReviewFlag=true;
                    top.tableHtml=data.tableHtml;
                    myclose_click();
                });
                $(obj).removeAttr("disabled");
            },error:function(data){
                LoadingAnimate.stop();
                alert(top.getMessage("FAILED_OPERATION"," "),0);
                $(obj).removeAttr("disabled");
            }
        });
	};
    var _repayPlanDetail= function (obj,url){//
        top.createShowDialog(url,"还款计划",'90','90',function(){

        });
    }
	return {
		init : _init,
		repayPlanAjax:_repayPlanAjax,
        repayPlanDetail:_repayPlanDetail
	};
}(window, jQuery);

