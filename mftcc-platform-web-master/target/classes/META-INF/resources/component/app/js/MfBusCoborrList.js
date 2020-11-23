
var MfBusCoborrList=function(window,$){

    //业务申请数据源及回调
    _selectCoborrNumList=function(appId){
        selectCocoborrListDialog(cusNo,_listCallback);
    }
    _listCallback =function(cus) {
        var coborrNo = $("input[name='coborrNo']").val();
        var coborrName = $("input[name='coborrName']").val();
        var coborrNum = $("input[name='coborrNum']").val();
        var cusMngNo = $("input[name='cusMngNo']").val();
        var cusMngName = $("input[name='cusMngName']").val();
        $.ajax({
            type: "post",
            dataType: 'json',
            url: webPath + "/mfBusApply/getCocoborrNum",
            data: {
                cusNo: cus.cusNo,
                coborrNo: coborrNo,
                coborrName: coborrName,
                coborrNum: coborrNum,
                cusMngNo: cusMngNo,
                cusMngName: cusMngName,
                appId: appId
            },
            async: false,
            success: function (data) {
                if (data.flag == "success") {
                    if (sign == "detail") {
                        $("#applyCusBorrowerList").html(data.tableData);
                        pubApplyDetailInfo.init();
                    } else {
                        $("#tableHtml").empty().html(data.tableData);
                        $("input[name='coborrNum']").val(data.coborrNumNew);
                        $("input[name='coborrName']").val(data.coborrNameNew);
                        $("input[name='coborrNo']").val(data.coborrNoNew);
                        $("input[name='certificateNum']").val(data.coborrNumNew);
                    }
                } else {
                    window.top.alert(data.msg, 0);
                    if (sign == "detail") {
                        $("#applyCusBorrowerList").html(data.tableData);
                        pubApplyDetailInfo.init();
                    } else {
                        $("#tableHtml").empty().html(data.tableData);
                        $("input[name='coborrNum']").val(data.coborrNumNew);
                        $("input[name='coborrName']").val(data.coborrNameNew);
                        $("input[name='coborrNo']").val(data.coborrNoNew);
                        $("input[name='certificateNum']").val(data.coborrNumNew);
                    }
                }
            }
        });
    }
      //业务申请列表中删除共同借款人
        _deleteCoborr = function(ele){
            var cusNos =  ele.toString().split("=");
            var cusNo = cusNos[1].toString();//删除的客户
            var coborrNo = $("input[name=coborrNo]").val();//共借人客户号
            $.ajax({
                type: "post",
                dataType: 'json',
                url:webPath+ "/mfBusApply/deleteCoborr",
                data:{cusNos:coborrNo,cusNo:cusNo,appId:appId},
                async: false,
                success: function(data) {
                    if(data.flag=="success"){
                        if (sign == "detail") {
                            $("#applyCusBorrowerList").html(data.tableData);
                            pubApplyDetailInfo.init();
                        }else{
                            $("#tableHtml").empty().html(data.tableData);
                            $("input[name='coborrNum']").val(data.idNums);
                            $("input[name='certificateNum']").val(data.idNums);
                            $("input[name='coborrNo']").val(data.cusNoss);
                            $("input[name='coborrName']").val(data.coborrName);
                        }
                    }else{
                        window.top.alert(data.msg,0);
                    }
                }
            });
        }
        //业务申请列表
      var _showCocoborrList = function () {
        if(appId!="" && appId!="undefined"){//业务申请
            $.ajax({
                url:webPath+"/mfBusApply/findCoborrNo?appId="+appId,
                type:'post',
                dataType:'json',
                success:function(data){
                    if(data.flag == "success"){
                        if(sign=="detail"){
                            var html = data.tableHtml;
                            $("#applyCusBorrowerList").empty().html(html);
                        }else{
                            $("#tableHtml").empty().html(data.tableHtml);
                            $("#coborrNumName").css("display","block");
                        }
                    }else{
                        if(sign=="detail"){
                            $("#applyCusBorrowerListDiv").remove();
                        }else{
                            $("#coborrNumName").remove();
                        }
                    }
                }
            });
        }
    };
    //业务合同数据源及回调
    _selectCoborrNumPactList=function(appId){
        selectCocoborrListDialog(cusNo,_listPactCallback);
    }
    _listPactCallback =function(cus) {
        var coborrNo = $("input[name='coborrNo']").val();
        var coborrName = $("input[name='coborrName']").val();
        var coborrNum = $("input[name='coborrNum']").val();
        var cusMngNo = $("input[name='cusMngNo']").val();
        var cusMngName = $("input[name='cusMngName']").val();
        $.ajax({
            type: "post",
            dataType: 'json',
            url: webPath + "/mfBusPact/getCocoborrNum",
            data: {
                cusNo: cus.cusNo,
                coborrNo: coborrNo,
                coborrName: coborrName,
                coborrNum: coborrNum,
                cusMngNo: cusMngNo,
                cusMngName: cusMngName,
                appId: appId
            },
            async: false,
            success: function (data) {
                if (data.flag == "success") {
                    if (sign == "detail") {
                        $("#applyCusBorrowerList").html(data.tableData);
                        pubPactDetailInfo.init();
                    } else {
                        $("#tableHtml").empty().html(data.tableData);
                        $("input[name='coborrNum']").val(data.coborrNumNew);
                        $("input[name='coborrName']").val(data.coborrNameNew);
                        $("input[name='coborrNo']").val(data.coborrNoNew);
                        $("input[name='certificateNum']").val(data.coborrNumNew);
                    }
                } else {
                    window.top.alert(data.msg, 0);
                    if (sign == "detail") {
                        $("#applyCusBorrowerList").html(data.tableData);
                         pubPactDetailInfo.init();
                    } else {
                        $("#tableHtml").empty().html(data.tableData);
                        $("input[name='coborrNum']").val(data.coborrNumNew);
                        $("input[name='coborrName']").val(data.coborrNameNew);
                        $("input[name='coborrNo']").val(data.coborrNoNew);
                        $("input[name='certificateNum']").val(data.coborrNumNew);
                    }
                }
            }
        });
    }
    //业务合同列表
    var _showCocoborrPactList = function () {
        $.ajax({
            url:webPath+"/mfBusPact/findCoborrNo?appId="+appId,
            type:'post',
            dataType:'json',
            success:function(data){
                if(data.flag == "success"){
                    if(sign=="detail"){
                        var html = data.tableHtml;
                        $("#applyCusBorrowerList").empty().html(html);
                    }else{
                        $("#tableHtml").empty().html(data.tableHtml);
                        $("#coborrNumName").css("display","block");
                    }
                }else{
                    if(sign=="detail"){
                        $("#applyCusBorrowerListDiv").remove();
                    }else{
                        $("#coborrNumName").remove();
                    }
                }
            }
        });
    };
    //合同列表中删除共同借款人
    _deleteCoborrPact = function(ele){
        var cusNos =  ele.toString().split("=");
        var cusNo = cusNos[1].toString();//删除的客户
        var coborrNo = $("input[name=coborrNo]").val();//共借人客户号
        $.ajax({
            type: "post",
            dataType: 'json',
            url:webPath+ "/mfBusPact/deleteCoborr",
            data:{cusNos:coborrNo,cusNo:cusNo,appId:appId},
            async: false,
            success: function(data) {
                if(data.flag=="success"){
                    if (sign == "detail") {
                        $("#applyCusBorrowerList").html(data.tableData);
                        pubPactDetailInfo.init();
                    }else{
                        $("#tableHtml").empty().html(data.tableData);
                        $("input[name='coborrNum']").val(data.idNums);
                        $("input[name='certificateNum']").val(data.idNums);
                        $("input[name='coborrNo']").val(data.cusNoss);
                        $("input[name='coborrName']").val(data.coborrName);
                    }
                }else{
                    window.top.alert(data.msg,0);
                }
            }
        });
    }
	return{
        selectCoborrNumList:_selectCoborrNumList,
        deleteCoborr:_deleteCoborr,
        showCocoborrList:_showCocoborrList,
        selectCoborrNumPactList:_selectCoborrNumPactList,
        deleteCoborrPact:_deleteCoborrPact,
        showCocoborrPactList:_showCocoborrPactList
	}
}(window,jQuery);

