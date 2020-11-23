
var ArchiveInfoVoucer_Insert=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj : ".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	//选择客户
    var _selectCusDialog=function(archiveStatus){
        selectArchiveCusDialog(_selectCusBack,archiveStatus);
    };
	//选择客户回调
	var _selectCusBack=function(cus){
        //首先清空项目和资料
        $("input[name=appId]").val();
        $("input[name=appName]").val();
        $("input[name=pactNo]").val();
        $("input[name=pactId]").val();
        $("input[name=docNo]").val();
        $("input[name=docName]").val();

        $("input[name=cusNo]").val(cus.cusNo);
        $("input[name=cusName]").val(cus.cusName);
	};

    //选择归档项目
   /* var _selectArchiveAppNameDialog=function(archiveStatus){
        var cusNo = $("input[name=cusNo]").val();
        if(cusNo == null || cusNo == ""){
            alert("请选选择客户！", 0);
            return false;
        }
        var dealMode = $("input[name=dealMode]").val();
        selectArchiveAppNameDialog(_selectArchiveAppNameBack,cusNo,archiveStatus, dealMode);
    };*/

    //选择归档项目
    var _selectArchiveAppNameDialog=function(archiveStatus){
        var cusNo = $("input[name=cusNo]").val();
        var archivePactStatus = $("[name=archivePactStatus]").val();
        if(cusNo == null || cusNo == ""){
            alert("请选选择客户！", 0);
            return false;
        }
        if(archivePactStatus == "01"){
            selectArchiveCreditDialog(_selectArchiveCreditBack,cusNo,archiveStatus,archivePactStatus);
        }else{
            selectArchiveAppNameDialog(_selectArchiveAppNameBack,cusNo,archiveStatus,archivePactStatus);
        }
    };

    //选择项目回调
    var _selectArchiveAppNameBack=function(archiveInfoMain){
        //首先清空项目和资料
        $("input[name=docNo]").val();
        $("input[name=docName]").val();

        var appId=archiveInfoMain.appId;
 //       window.location.href = webPath+"/archiveInfoVoucherReturn/inputForBus?apId="+appId;

        var appName=archiveInfoMain.appName;
        var pactNo=archiveInfoMain.pactNo;
        var pactId=archiveInfoMain.pactId;
        var applyNum=archiveInfoMain.applyNum;

        $("input[name=appId]").val(appId);
        $("input[name=appName]").val(appName);
        $("input[name=pactNo]").val(pactNo);
        $("input[name=pactId]").val(pactId);
        $("input[name=pactId]").val(pactId);
        $("input[name=applyNum]").val(applyNum);
    };

    //选择授信回调
    var _selectArchiveCreditBack=function(archiveInfoMain){
        //首先清空项目和资料
        $("input[name=docNo]").val();
        $("input[name=docName]").val();
        var creditAppId = archiveInfoMain.creditAppId;
//        window.location.href = webPath+"/archiveInfoVoucherReturn/inputForCredit?creditAppId="+creditAppId;
        $("input[name=creditAppId]").val(archiveInfoMain.creditAppId);
        $("input[name=creditAppNo]").val(archiveInfoMain.creditAppNo);
    };

    //选择归档项目下的资料
    var _selectArchiveDocDialog=function(){
        var cusNo = $("input[name=cusNo]").val();
        if(cusNo == null || cusNo == ""){
            alert("请选选择客户！", 0);
            return false;
        }
        var docType = "3";
        var archivePactStatus = $("[name=archivePactStatus]").val();
        if(archivePactStatus == "01"){
            var creditAppId = $("input[name=creditAppId]").val();
            if(creditAppId == null || creditAppId == ""){
                alert("请选选择归档授信！", 0);
                return false;
            }
            selectArchiveVoucherDialog(_selectArchiveDocBack,archivePactStatus,creditAppId,docType);
        }else{
            var appId = $("input[name=appId]").val();
            if(appId == null || appId == ""){
                alert("请选选择归档项目！", 0);
                return false;
            }
            selectArchiveVoucherDialog(_selectArchiveDocBack,archivePactStatus,appId,docType);
        }

    };

    //选择资料回调
    var _selectArchiveDocBack=function(res){
        var docName=res.docNames;
        var docNo =res.docNos;
        var archiveMainNo = res.archiveMainNos;
        var archiveDetailNo = res.archiveDetailNos;
        var archivePactStatus = $("[name=archivePactStatus]").val();
        if(archivePactStatus == "01"){
            var creditAppId = $("[name=creditAppId]").val();
            window.location.href = webPath+"/archiveInfoVoucherReturn/inputForCredit?creditAppId="+creditAppId+"&docNo="+docNo+"&docName="+docName+"&archiveMainNo="+archiveMainNo+"&archiveDetailNo="+archiveDetailNo;
        }else{
            var appId = $("[name=appId]").val();
            window.location.href = webPath+"/archiveInfoVoucherReturn/inputForBus?appId="+appId+"&docNo="+docNo+"&docName="+docName+"&archiveMainNo="+archiveMainNo+"&archiveDetailNo="+archiveDetailNo;
        }

        // $("input[name=docNo]").val(docNo);
        // $("input[name=docName]").val(docName);
        // $("input[name=archiveMainNo]").val(archiveMainNo);
        // $("input[name=archiveDetailNo]").val(archiveDetailNo);
    };

	return{
		init:_init,
		selectCusDialog:_selectCusDialog,
        selectArchiveAppNameDialog:_selectArchiveAppNameDialog,
        selectArchiveDocDialog:_selectArchiveDocDialog
	}
}(window,jQuery);
function selectArchiveCusDialog(callback,archiveStatus){
    var url = "/archiveInfoMain/getArchiveCusList?archiveStatus="+archiveStatus;
    dialog({
        id:'archiveCusDialog',
        title:"选择归档客户",
        url:webPath+url,
        width:900,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:实体类MfCusCustomer中的所有属性
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }
            }
        }
    }).showModal();
};
function selectArchiveCreditDialog(callback,cusNo,archiveStatus,archivePactStatus){
    var url = "/archiveInfoMain/getArchiveCreditListForBorrow?cusNo="+cusNo+"&archiveStatus="+archiveStatus+"&archivePactStatus="+archivePactStatus;
    dialog({
        id:'archiveCreditAppDialog',
        title:"选择归档项目",
        url:webPath+url,
        width:900,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:实体类MfCusCustomer中的所有属性
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }
            }
        }
    }).showModal();
};

function selectArchiveAppNameDialog(callback,cusNo,archiveStatus,archivePactStatus,dealMode){
    var url = "/archiveInfoMain/getArchiveAppNameList?cusNo="+cusNo+"&archiveStatus="+archiveStatus+"&archivePactStatus="+archivePactStatus;
    dialog({
        id:'archiveAppNameDialog',
        title:"选择归档项目",
        url:webPath+url,
        width:900,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:实体类MfCusCustomer中的所有属性
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }
            }
        }
    }).showModal();
};

function selectArchiveVoucherDialog(callback,archivePactStatus,relationId,docType){
    var url = "/archiveInfoDetail/getArchiveVoucherList?relationId="+relationId+"&docType="+docType+"&archivePactStatus="+archivePactStatus;
    dialog({
        id:'archiveDocDialog',
        title:"选择凭证",
        url:webPath+url,
        width:900,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }
            }
        }
    }).showModal();
};