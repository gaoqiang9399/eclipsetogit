;
var MfMoveableAccountCheckInfoList=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		_showList();
	};
	//生成对账单
	var _produceAccountCheckBill=function(type){
		var filename = "";
		if (type == "day") {
			filename = "reconciliationBillDay.xls";
		} else if (type == "stage") {
			filename = "reconciliationBillStage.xls";
		}
		var poCntJson = {
				filePath : "",
				fileName : filename,
				appId : appId,
				pactId : pactId,
				cusNo : cusNo,
				saveBtn : "0",
				fileType : 0
			};
		mfPageOffice.openPageOffice(poCntJson);
	};
	// 展示历史对账记录
	var _showList=function(){
		myCustomScrollbar({
			obj : "#content", //页面内容绑定的id
			url : webPath+"/mfMoveableAccountCheckInfo/findByPageAjax", //列表数据查询的url
			tableId : "tableaccountcheck0001", //列表数据查询的table编号
			tableType : "thirdTableTag", //table所需解析标签的种类
			pageSize:30, //加载默认行数(不填为系统默认行数)
			data:{
				busPleId:busPleId
			}
		    });
	};
	//打开对账情况登记页面
	var _accountCheckInput=function(){
		dialog({
			id:"showDialog",
    		title:'对账情况登记',
    		url: webPath+'/mfMoveableAccountCheckInfo/input?busPleId='+busPleId,
    		width:1000,
    		height:500,
    		backdropOpacity:0,
    		onshow:function(){
    			this.returnValue = null;
    		},onclose:function(){
    			if(this.returnValue){
    				updateTableData();
    			}
    		}
    	}).showModal();
	};
	//打开异常跟踪登记页面
	var _unusualTailInput=function(obj,url){
		dialog({
			id:"showDialog",
			title:'异常跟踪登记',
			url: url+"&busPleId="+busPleId,
			width:1000,
			height:500,
			backdropOpacity:0,
			onshow:function(){
				this.returnValue = null;
			},onclose:function(){
				if(this.returnValue){
					updateTableData();
				}
			}
		}).showModal();
	};
	//打开对账情况详情页面
	var _accountCheckDeatil=function(obj,url){
		dialog({
			id:"showDialog",
			title:'对账情况详情',
			url: url+"&busPleId="+busPleId,
			width:1000,
			height:500,
			backdropOpacity:0,
			onshow:function(){
				this.returnValue = null;
			},onclose:function(){
			}
		}).showModal();
	};
	return{
		init:_init,
		accountCheckInput:_accountCheckInput,
		unusualTailInput:_unusualTailInput,
		produceAccountCheckBill:_produceAccountCheckBill,
		accountCheckDeatil:_accountCheckDeatil
	};
}(window,jQuery);