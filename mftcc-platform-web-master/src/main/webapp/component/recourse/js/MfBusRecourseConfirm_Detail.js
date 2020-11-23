;
var MfBusRecourseConfirmDetail = function(window,$){
	var _init = function(){

		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};

			//更新操作
	var _ajaxInsert = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
            var dataParam = JSON.stringify($(obj).serializeArray());
            $.ajax({
                url:webPath+"/mfBusRecourseConfirm/insertAjax",
                data:{
                    ajaxData : dataParam,
                },
                type:'post',
                dataType:'json',
                success:function(data){
                    if(data.flag == "success"){
                        window.top.alert(data.msg,1);
                        myclose_click();
                    }else{
                        alert(top.getMessage("ERROR_INSERT"),0);
                    }
                },error:function(){
                    alert(top.getMessage("ERROR_INSERT"),0);
                }
            });
        }
	};

    var _getSts = function(){
        var sts;
        var obj;
        var fincId;
        var compensatoryId;
        var recourseId;
        //循环table
        $("#tablist").find("tbody").find("tr").each(function (i) {
            obj = $(this);
            fincId = obj.find("td").eq(8).find("input[name=fincId]").val();
            sts = obj.find("td").eq(6).find("input[name=ext5]").val();//获取代偿状态
            compensatoryId =  obj.find("td").eq(9).find("input[name=ext4]").val();
            if(sts=="1" || sts=="2"){
                obj.find("td").eq(7).text("追偿中");
            }else{
                obj.find("td").eq(7).text("");
                obj.find("td").eq(7).append("<a href=\"javascript:void(0);\" onclick=\'MfBusRecourseConfirmDetail.getCompensatoryConfirm("+"\""+fincId+"\","+"\""+compensatoryId+"\")\';return false;\" class=\"abatch\">追偿登记</a>");
            }
        });
    };

    var _getCompensatoryApply = function(fincId,recourseId){
        top.flag=false;
        top.window.openBigForm(webPath+"/mfBusRecourseConfirm/inputInfo?fincId="+fincId+"&recourseId="+recourseId,'追偿信息',function(){
            window.location.reload();
        });
    }

    var _getCompensatoryConfirm = function(fincId,compensatoryId){
        top.flag=false;
        top.window.openBigForm(webPath+"/mfBusRecourseApply/inputRec?fincId="+fincId+"&compensatoryId="+compensatoryId,'追偿确认',function(){
            // MfBusCompensatory.getCompensatoryConfirmBack();
            MfBusRecourseApplyDetail.getRecourseStatus();
        });
    }

    var _getRecourseConfirm = function(){
        top.flag=false;
        top.window.openBigForm(webPath+"/mfBusRecourseConfirm/input?fincId="+fincId+"&recourseId="+recourseId,'追偿收回',function(){
            MfBusRecourseApplyDetail.recourseConfirmBack();
            MfBusRecourseApplyDetail.getRecourseStatus();
        });
    };
    var _getDetailPage = function (obj, url) {
        top.LoadingAnimate.start();
        top.window.openBigForm(url, '追偿详情', function() {
            window.location.reload();
        });
    };
    return{
        init:_init,
        ajaxInsert:_ajaxInsert,
        getSts : _getSts,
        getCompensatoryApply : _getCompensatoryApply,
        getCompensatoryConfirm : _getCompensatoryConfirm,
        getRecourseConfirm : _getRecourseConfirm,
        getDetailPage : _getDetailPage
	};
}(window,jQuery);