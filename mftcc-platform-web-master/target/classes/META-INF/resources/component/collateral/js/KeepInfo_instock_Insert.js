;
var KeepInfo_instock_Insert=function(window,$){
    var _init=function(){
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                updateOnContentResize : true
            }
        });
        _refreshGoodsDetailList();
    };
    var _refreshGoodsDetailList=function(){
        var pledgeNo=$("input[name=collateralId]").val();
        jQuery.ajax({
            url : webPath+"/pledgeBaseInfoBill/getTableDataByPledgeNoAjax",
            type : "POST",
            dataType : "json",
            data:{tableId:"tablepledgebaseinfobillselect",pledgeNo:pledgeNo,isDealing:"1",pledgeBillSts:"0"},//tabledlpledgebaseinfobill0006
            beforeSend : function() {
            },
            success : function(data) {
                if (data.flag == "success") {
                    haveGoodsFlag="1";
                    var pledgeBillNoStr ="";
                    $("#goodsDetailList").html(data.tableData);
                    if($('.table_content #tab').find($('input[type=checkbox]'))>0){
                    }
                    $("#goodsDetailListdiv").show();
                    $("th[name=pledgeBillNo]").html('<a href="javascript:void(0);" onclick="MfMoveableClaimGoodsApply.checkAllGoodsDetail()">全选</a>');
                    $('.table_content #tab').find($('input[type=checkbox]')).each(function () {
                        $(this).bind("click",function(){
                            var val=this.value;
                            /*var unitPriceStr=val.split("&")[1];
                            var unitPriceStr=val.split("&")[1];
                            var unitPrice=unitPriceStr.split("=")[1];
                            var countStr=val.split("&")[2];
                            var count=countStr.split("=")[1];
                            var goodsNum=$("input[name=goodsNum]").val();
                            var claimGoodsAmt=$("input[name=claimGoodsAmt]").val();
                            if(goodsNum==""){
                                goodsNum=0;
                            }
                            if(claimGoodsAmt==""){
                                claimGoodsAmt=0;
                            }
                            if($(this).prop("checked")){
                                goodsNum=parseInt(goodsNum)+parseInt(count)
                                claimGoodsAmt=parseFloat(claimGoodsAmt)+parseFloat(unitPrice)*parseInt(count);
                            }else{
                                goodsNum=parseInt(goodsNum)-parseInt(count)
                                claimGoodsAmt=parseFloat(claimGoodsAmt)-parseFloat(unitPrice)*parseInt(count);
                            }
                            $("input[name=goodsNum]").val(goodsNum);
                            $("input[name=claimGoodsAmt]").val(claimGoodsAmt);
                            $("input[name=claimGoodsBal]").val(claimGoodsBal-claimGoodsAmt);*/
                        });
                    });
                }
            },
            error : function(data) {
                LoadingAnimate.stop();
                window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
            }
        });
    };
    var _checkAllGoodsDetail=function(){
        var claimGoodsAmt = 0.00;
        var goodsNum = 0;
        $('.table_content #tab').find($('input[type=checkbox]')).each(function () {
            if($(this).prop("checked")){
                $(this).prop("checked",false);
            }else{
                $(this).prop("checked",true);
                var val=this.value;
                var unitPriceStr=val.split("&")[1];
                var unitPrice=unitPriceStr.split("=")[1];
                var countStr=val.split("&")[2];
                var count=countStr.split("=")[1];
                claimGoodsAmt=parseFloat(claimGoodsAmt)+parseFloat(unitPrice)*parseInt(count);
                goodsNum=parseInt(goodsNum)+parseInt(count);
            }
        });
        $("input[name=claimGoodsAmt]").val(claimGoodsAmt);
        $("input[name=goodsNum]").val(goodsNum);
        $("input[name=claimGoodsBal]").val(claimGoodsBal-claimGoodsAmt);
    };

    var _getGoodsDetailList=function(){
        var pledgeNo=$("input[name=collateralId]").val();
        jQuery.ajax({
            url : webPath+"/keepInfo/getGoodsDetailList",
            type : "POST",
            dataType : "json",
            data:{tableId:"tablepledgebaseinfobillselect",keepId:keepId},
            beforeSend : function() {
            },
            success : function(data) {
                if (data.flag == "success") {
                    var pledgeBillNoStr ="";
                    $("#goodsDetailList").html(data.tableData);
                    if($('.table_content #tab').find($('input[type=checkbox]'))>0){
                    }
                    $("#goodsDetailListdiv").show();
                    // $("th[name=pledgeBillNo]").html('<a href="javascript:void(0);" onclick="MfMoveableClaimGoodsApply.checkAllGoodsDetail()">全选</a>');
                    $('.table_content #tab').find($('input[type=checkbox]')).each(function () {
                        $(this).bind("click",function(){
                            var val=this.value;

                        });
                    });
                }
            },
            error : function(data) {
                LoadingAnimate.stop();
                window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
            }
        });
    };

    return{
        init:_init,
        refreshGoodsDetailList:_refreshGoodsDetailList,
        checkAllGoodsDetail:_checkAllGoodsDetail,
        getGoodsDetailList:_getGoodsDetailList,
    };
}(window,jQuery);