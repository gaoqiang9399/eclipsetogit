
var ArchiveInfo_Insert=function(window,$){
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
    var _selectArchiveAppNameDialog=function(archiveStatus){
        var cusNo = $("input[name=cusNo]").val();
        if(cusNo == null || cusNo == ""){
            alert("请选选择客户！", 0);
            return false;
        }
        var dealMode = $("input[name=dealMode]").val();
        selectArchiveAppNameDialog(_selectArchiveAppNameBack,cusNo,archiveStatus, dealMode);
    };

    //选择项目回调
    var _selectArchiveAppNameBack=function(archiveInfoMain){
        //首先清空项目和资料
        $("input[name=docNo]").val();
        $("input[name=docName]").val();

        var appId=archiveInfoMain.appId;
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

    //档案封存时候选择项目--
    var _selectArchiveBlockAppDialog=function(archiveStatus){
        var cusNo = $("input[name=cusNo]").val();
        if(cusNo == null || cusNo == ""){
            alert("请选选择客户！", 0);
            return false;
        }
        var dealMode = $("input[name=dealMode]").val();
        selectArchiveBlockAppDialog(_selectArchiveAppNameBack,cusNo,archiveStatus, dealMode);
    };

    //选择归档项目下的资料
    var _selectArchiveDocDialog=function(docType){
        var cusNo = $("input[name=cusNo]").val();
        if(cusNo == null || cusNo == ""){
            alert("请选选择客户！", 0);
            return false;
        }
        var appId = $("input[name=appId]").val();
        if(appId == null || appId == ""){
            alert("请选选择归档项目！", 0);
            return false;
        }
        selectArchiveDocDialog(_selectArchiveDocBack,appId,docType);
    };

    //选择资料回调
    var _selectArchiveDocBack=function(res){
        var docName=res.docNames;
        var docNo =res.docNos;

        $("input[name=docNo]").val(docNo);
        $("input[name=docName]").val(docName);
    };

	return{
		init:_init,
		selectCusDialog:_selectCusDialog,
        selectArchiveAppNameDialog:_selectArchiveAppNameDialog,
        selectArchiveBlockAppDialog:_selectArchiveBlockAppDialog,
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
//档案封存--选择项目
function selectArchiveBlockAppDialog(callback,cusNo,archiveStatus,dealMode){
    var url = "/archiveInfoMain/getArchiveBlockAppList?cusNo="+cusNo+"&archiveStatus="+archiveStatus+"&dealMode="+dealMode;
    dialog({
        id:'archiveBlockDialog',
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

function selectArchiveAppNameDialog(callback,cusNo,archiveStatus,dealMode){
    var url = "/archiveInfoMain/getArchiveAppNameList?cusNo="+cusNo+"&archiveStatus="+archiveStatus+"&dealMode="+dealMode;
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

function selectArchiveDocDialog(callback,appId,docType){
    var url = "/archiveInfoDetail/getArchiveDocList?appId="+appId+"&docType="+docType;
    dialog({
        id:'archiveDocDialog',
        title:"选择资料",
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