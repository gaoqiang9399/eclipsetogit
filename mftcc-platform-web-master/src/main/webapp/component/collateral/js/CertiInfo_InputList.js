;
var CertiInfo_InputList = function(window, $) {
	var _init = function () {
        if(flag=='error'){
            DIALOG.msg("权证信息已提交，等待审批中！",function () {
                myclose_click();
            });

            return;
        }
        //滚动条
        myCustomScrollbarForForm({
            obj: ".scroll-content",
            advanced: {
                updateOnContentResize: true
            }
        });
    };

	//新增数据
	var _inputNew = function(){
        top.addReceInfoFlag=false;
        top.window.openBigForm(webPath+"/certiInfo/inputForList?appId="+appId+"&cusNo="+cusNo+"&collateralType="+collateralType,'新增权证',function() {
            if(top.addReceInfoFlag){
                _getCertiListHtmlAjax();
            }
        });
    }
//新增数据
    var _editNew = function(obj,url){
        top.addReceInfoFlag=false;
        top.window.openBigForm( webPath + url + "&appId="+appId+"&cusNo="+cusNo+"&collateralType="+collateralType,'编辑权证',function() {
            if(top.addReceInfoFlag){
                _getCertiListHtmlAjax();
            }
        });
    }
    //刷新权证列表数据
    var _getCertiListHtmlAjax = function(){
        $.ajax({
            url: webPath + "/certiInfo/getCertiListHtmlAjax",
            data:{appId:appId,
                collateralType:collateralType,
                tableId:"tablecertiInfoInputList"},
            type:'post',
            dataType:'json',
            success: function (data) {
                if (data.flag == "success") {
                    $("#receAccountList").html(data.tableData);
                    $("#pledgeBaseInfoList").html(data.tableData2);
                } else {

                }
            }, error: function () {
                LoadingAnimate.stop();
            }
        });
    };

    var _deleteCertiInfoAjax = function(obj,url){
        alert(top.getMessage("CONFIRM_DELETE"),2,function() {
            $.ajax({
                url: webPath + url + "&appId=" + appId+ "&collateralType=" + collateralType,
                success: function (data) {
                    if (data.flag == "success") {
                        $("#receAccountList").html(data.tableData);
                        $("#pledgeBaseInfoList").html(data.tableData2);
                    } else {
                    }
                }, error: function () {

                }
            });
        });
    };
    var _shouLiPledgeAjax = function(obj,url){
        //新增数据
        top.addReceInfoFlag=false;
        top.window.openBigForm( webPath + url + "&appId="+appId+"&cusNo="+cusNo+"&collateralType="+collateralType,'新增权证',function() {
            if(top.addReceInfoFlag){
                _getCertiListHtmlAjax();
            }
        });
    };
    var _shouLiAjax = function(obj,url){
        DIALOG.confirm("是否确认受理该反担保?",function(){
            $.ajax({
                url:webPath+url+"&appId="+appId,
                success:function(data){
                    if(data.flag=="success"){
                        alert("受理成功！",1);
                        _getCertiListHtmlAjax();
                    }else{
                        alert(data.msg,0);
                    }
                }
            });
        });
    };
	var _myclose = function (obj) {
        myclose_click();
    }
	return {
		init : _init,
        deleteCertiInfoAjax : _deleteCertiInfoAjax,
        inputNew:_inputNew,
        myclose : _myclose,
        shouLiPledgeAjax : _shouLiPledgeAjax,
        editNew : _editNew,
        shouLiAjax : _shouLiAjax,
    };
}(window, jQuery);
