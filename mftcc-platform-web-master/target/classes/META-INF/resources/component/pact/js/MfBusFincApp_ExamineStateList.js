;
var MfBusFincApp_ExamineStateList=function(window,$){
	//初始化在履行借据贷后检查状态列表
	var _init=function(){
		myCustomScrollbar({
	    	obj:"#content",//页面内容绑定的id
	    	url:webPath+"/mfBusFincApp/findExamineStateByPageAjax",//列表数据查询的url
	    	tableId:"tablefincExamineStateList",//列表数据查询的table编号
	    	tableType:"tableTag",//table所需解析标签的种类
            pageSize:30,//加载默认行数(不填为系统默认行数)
            topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
	    });
	};
	//单笔指派贷后检查任务
	var _assignExamineTaskSingle=function(obj,url){
		if(url!=""&&url!=null){
			var parm=url.split("?")[1];
			fincIdStr=parm.split("=")[1];
		};
		assignType="single";
		selectUserCustomTitleDialog("选择任务执行人",_assignExamineTask);
	};
	//批量指派贷后检查任务
	var _assignExamineTaskBatch=function(){
		$('.table_content #tab').find($('input[type=checkbox]:checked')).each(function () {
			val = this.value.split('&') [0] ;
			fincIdStr=fincIdStr+"|"+val.split("=")[1];
    	  });
		if(fincIdStr==""){
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
				fincId:fincIdStr
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				LoadingAnimate.stop();
				fincIdStr="";
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
    var _getDetailPageInvoice = function (obj,url){
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        top.LoadingAnimate.start();
        window.parent.openBigForm(url, function() {
            updateTableData();
        });
    };
	return{
		init:_init,
		assignExamineTask:_assignExamineTask,
		assignExamineTaskSingle:_assignExamineTaskSingle,
		assignExamineTaskBatch:_assignExamineTaskBatch,
		getDetailPage:_getDetailPage,
        getDetailPageInvoice:_getDetailPageInvoice
	};
}(window,jQuery)