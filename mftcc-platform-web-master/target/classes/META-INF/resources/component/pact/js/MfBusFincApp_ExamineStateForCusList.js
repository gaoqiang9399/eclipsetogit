;
var MfBusFincApp_ExamineStateForCusList=function(window,$){
	//初始化在履行借据贷后检查状态列表
	var _init=function(){
		myCustomScrollbar({
	    	obj:"#content",//页面内容绑定的id
	    	url:webPath+"/mfBusFincApp/findExamineStateForCusByPageAjax",//列表数据查询的url
	    	tableId:"tablefincExamineStateForCusList",//列表数据查询的table编号
	    	tableType:"thirdTableTag",//table所需解析标签的种类
	    	pageSize:30//加载默认行数(不填为系统默认行数)
	    });
	};
	//单笔指派贷后检查任务
	var _assignExamineTaskSingle=function(obj,url){
		if(url!=""&&url!=null){
			var parm=url.split("?")[1];
			cusNoStr=parm.split("&")[0].split("=")[1];
		};
		assignType="single";
		selectUserCustomTitleDialog("选择任务执行人",_assignExamineTask);
	};
	var _loanAfterExamine=function(obj,url){

		var urlStr = webPath+url;
		top.window.openBigForm(urlStr,"贷后检查",function(){
            updateTableData();//重新加在列表数据

		});
	};
	//批量指派贷后检查任务
	var _assignExamineTaskBatch=function(){
		$('.table_content #tab').find($('input[type=checkbox]:checked')).each(function () {
			val = this.value.split('&') [0] ;
			cusNoStr=cusNoStr+"|"+val.split("=")[1];
    	  });
		if(cusNoStr==""){
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD","指派检查任务的借据"), 0);
			return false;
		}
		assignType="batch";
		selectUserCustomTitleDialog("选择任务执行人",_assignExamineTask);
	};
	//指派任务
	var _assignExamineTask=function(noticeScorpInfo){
		var executorsStr=noticeScorpInfo.brNo;
		LoadingAnimate.start();
		$.ajax({
			url : webPath+"/mfExamineHis/assignExamineTaskAjax",
			data : {
				executorsStr : executorsStr,
				assignType:assignType,
				cusNo:cusNoStr
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				LoadingAnimate.stop();
				cusNoStr="";
				assignType="";
				if(data.flag=="success"){
					window.top.alert(data.msg, 1);
				}else if(data.flag=="error"){
					window.top.alert(data.msg, 0);
				}
			},
			error : function() {
				LoadingAnimate.stop();
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	};
	var _getDetailPage = function (obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.LoadingAnimate.start();		
		window.location.href=url;			
	};
	return{
		init:_init,
		assignExamineTask:_assignExamineTask,
		assignExamineTaskSingle:_assignExamineTaskSingle,
		assignExamineTaskBatch:_assignExamineTaskBatch,
		getDetailPage:_getDetailPage,
		loanAfterExamine:_loanAfterExamine
	};
}(window,jQuery)