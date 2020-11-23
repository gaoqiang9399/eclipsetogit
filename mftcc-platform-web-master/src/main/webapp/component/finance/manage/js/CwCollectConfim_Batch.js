
var CwCollectConfim_Batch=function(window,$){
    var _init = function() {
        $("input[name='collectTime']").click(function(){
            fPopUpCalendarDlg();
        });
        $("input[name='collectTime']").attr("maxlength","10");
        $('.docList #tab tr').each(function () {
            var approvalRemarkObj = $(this).find("input[name='approvalRemark']");
            var approvalRemark =$(approvalRemarkObj) .val();
            $(approvalRemarkObj).parent("td").html("<textarea class=\"form-control\" placeholder=\"6000字以内\" name=\"approvalRemark\" title=\"审批意见\"  maxlength=\"6000\" >"+approvalRemark+"</textarea>");
        });
    };


    //工程担保批量到账确认
    var _batchConfimGcdb = function (){
        var idStr = "";
        var collectTimeStr = "";
        var approvalRemarkStr = "";
        var collectTimeFlag = 0;
        var approvalRemarkFlag = 0;
        $('.docList #tab tr').each(function () {
            var idVal = $(this).find("input[name='id']").val();
            idStr = idStr + "," + idVal;
            var collectTimeVal = $(this).find("[name='collectTime']").val();
            collectTimeVal = collectTimeVal.replace(/-/g,"");
            if(collectTimeVal != null && collectTimeVal != ""){
                collectTimeStr = collectTimeStr + "," + collectTimeVal;
            }else{
                collectTimeFlag = 1;
            }
            var approvalRemarkVal = $(this).find("[name='approvalRemark']").val();
            if(approvalRemarkVal != null && approvalRemarkVal != ""){
                approvalRemarkStr = approvalRemarkStr + "," + approvalRemarkVal;
            }else{
                approvalRemarkFlag = 1;
            }
        });
        if(collectTimeFlag == 1){
            window.top.alert("费用确认日期不能为空！", 0);
            return false;
        }
        if(approvalRemarkFlag == 1){
            window.top.alert("审批意见不能为空！", 0);
            return false;
        }
        if(idStr==""){
            window.top.alert(top.getMessage("FIRST_SELECT_FIELD",showName + "的数据"), 0);
            return false;
        }else{
            idStr=idStr.substr(1);
            collectTimeStr=collectTimeStr.substr(1);
            approvalRemarkStr=approvalRemarkStr.substr(1);
            LoadingAnimate.start();
            $.ajax({
                type : "POST",
                data:{
                    ids : idStr,
                    collectTimes : collectTimeStr,
                    approvalRemarks : approvalRemarkStr
                },
                url : webPath+ "/cwCollectConfim/batchConfim",
                dataType : "json",
                success : function(data) {
                    LoadingAnimate.stop();
                    if(data.flag=="success"){
                        window.top.alert(data.msg,1);
                        myclose_click();
                    }else{
                        window.top.alert(data.msg,0);
                    }
                },error : function(xmlhq, ts, err) {
                    LoadingAnimate.stop();
                }
            });
        }
    }

	return{
        init:_init,
        batchConfimGcdb:_batchConfimGcdb
	}
}(window,jQuery);

