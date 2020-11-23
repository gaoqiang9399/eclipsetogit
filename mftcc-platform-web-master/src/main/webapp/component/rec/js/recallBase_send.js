function initPageShow($obj,pager){
	
		var flag = $obj.parent().find(".pagerShow").html();
		if(flag===undefined){
			var pageShowHtml = '<div class="pagerShow">';
	    		pageShowHtml+= '<span>';
	    		pageShowHtml+= '共计<span class="pageCount">'+pager.pageCounts+'</span>条';
	    		pageShowHtml+= '<span class="split"></span>已加载<span class="loadCount">'+pager.endNum+'</span>条';
	    		pageShowHtml+= '</span>';
	    		pageShowHtml+= '</div>';
			$obj.parent().append(pageShowHtml);
		}else{
			var $pageShow = $obj.parent().find(".pagerShow");
			$pageShow.find(".pageCount").html(pager.pageCounts);
			$pageShow.find(".loadCount").html(pager.endNum);
		}
	}
$(function(){
	//var bodyHeight = document.body.clientHeight-100;

//	$(".table_content").height(bodyHeight);
	$(".table_content").each(function(){
		var $tbContObj = $(this);
		$(this).mCustomScrollbar({
			advanced:{ 
				updateOnContentResize:true
			},onTotalScrollOffset:30,//回调距什么位置回调
			callbacks:{ 
				onTotalScroll:function(){
					updateTableContent($tbContObj);
				}
			}
		});
	});
	selectSysUser();
	selectRecallBase();
	$('#gridSystemModal').on('hidden.bs.modal',function(e) {
		
			selectSysUser();
			selectRecallBase();
		
	});
	/*choiceTypeInit({
		column:"cusType",
		array:["2-部分指派","3-全部指派"]});
	*/
});
function selectSysUser(){
	var dataParam = {};
	var $tableUserTask = $(".table_content.userTaskList").find("table.ls_list");
	var tableId = $tableUserTask.attr("title");
	var tableType = "thirdTableTag";
	$.ajax({
		type:"post",
		url:webPath+"/recallBase/findByPageUserTaskAjax",
		async:false,
		data:{
			ajaxData:JSON.stringify(changeDateParm(dataParam)),
			pageSize:25,
			tableId:tableId,
			tableType:tableType
		},success:function(data){
			$tableUserTask.find("tbody").html($(data.ipage.result).find("tbody").html());
			setPager($(".table_content.userTaskList"),data.ipage);
			initPageShow($(".table_content.userTaskList"),data.ipage);
			$(".table_content").mCustomScrollbar("scrollTo","top");
			SynchronousLoadding = true;
			
		},error:function(){
			
		}
	});	
}
function selectRecallBase(){
	var val=$("#searchRb").val();
	var dataParam=JSON.stringify([{customQuery:val}]);
	var $tableRecallBase = $(".table_content.recallBase").find("table.ls_list");
	var tableId = $tableRecallBase.attr("title");
	var tableType = "thirdTableTag";
	var recallSts = "'2','5'";
	$.ajax({
		type:"post",
		url:webPath+"/recallBase/findDealByPageAjax",
		async:false,
		data:{
			ajaxData:dataParam,
			recallSts:recallSts,
			pageSize:25,
			tableId:tableId,
			tableType:tableType
		},success:function(data){
			$tableRecallBase.find("tbody").html($(data.ipage.result).find("tbody").html());
			setPager($(".table_content.recallBase"),data.ipage);
			initPageShow($(".table_content.recallBase"),data.ipage);
			$(".table_content").mCustomScrollbar("scrollTo","top");
			SynchronousLoadding = true;
			
		},error:function(){
			
		}
	});	
}
/**
 * 滚动条滚动加载
 * @param {Object} $obj
 */
function updateTableContent($obj){
	var pager = getPager($obj);
	if(pager.pageNo<pager.pageSum){
		pager.pageNo = pager.pageNo+1;
		var url,tableId;
		var tableType = "thirdTableTag";
		var flag = $obj.hasClass("recallBase");
		var data;
        var dataParam;
		if(flag){
			var $tableParent = $(".table_content.recallBase").find("table.ls_list");
			 tableId = $tableParent.attr("title");
			url = webPath+"/recallBase/findByPageAjax";
			var val=$("#searchRb").val();
			dataParam=JSON.stringify([{customQuery:val}]);
			data = {
					ajaxData:JSON.stringify(changeDateParm(dataParam)),
					recallSts:1,
					tableType:tableType,
					tableId:tableId,
				};
		}else{
			var $tableChild = $(".table_content.userTaskList").find("table.ls_list");
			tableId = $tableChild.attr("title");
			url = webPath+"/recallBase/findByPageUserTaskAjax";
			dataParam=JSON.stringify([{customQuery:""}]);
			data = {
					ajaxData:JSON.stringify(changeDateParm(dataParam)),
					tableType:tableType,
					tableId:tableId,
				};
		}

		data = $.extend({}, data,pager);
		$.ajax({
			type:"post",
			url:url,
			data:data,
			success:function(data){
				$obj.find("table.ls_list tbody").append($(data.ipage.result).find("tbody").html());
				initPageShow($obj,data.ipage);
				setPager($obj,data.ipage);
			}
		});
	}else{
	}
}
var SynchronousLoadding = true;
var taskNo;
var taskNoStr=new Array();
function getSysUserList(obj,type,value) {
	taskNoStr=[];
    var val;
    if ((value != null || value != '' || value != undefined)&&type=='1') {//单独指派
        taskNo = value.split("=")[1];
        $(obj).parents("td").parents("tr").find(".selected").removeClass("selected");
        $(obj).parents("td").parents("tr").addClass("selected");
    } else if(type=='2'){
    	val="";
    	taskNo = '';
    	$('.table_content #tab').find($('input[type=checkbox]:checked')).each(function () {
    	    val = this.value.split('=') [1] ;
    	    taskNoStr.push(val);
    	  });
    	if(taskNoStr.length==0){
    		//$.myAlert.Alert("请选择要改派的任务");
    		alert(top.getMessage("FIRST_SELECT_FIELD","要改派的任务"), 0);
    		return;
    	}
    }else if(type=='3'){
    	 taskNo = '';
    	 val="";
     	 $('.table_content #tab').find($('input[type=checkbox]')).each(function () {
     	    val = this.value.split('=') [1] ;
     	    taskNoStr.push(val);
     	  });
    }
    /**
    $('#gridSystemModal').modal('show');
    $("#gridSystemModal").find("input[name=mgrNo]").prop("value","");
	$("#gridSystemModal").find("input[name=mgrName]").prop("value","");
	$("#gridSystemModal").find("input[name=taskNo]").prop("value",taskNo);
	**/
    
    //alert("taskNo="+taskNo+",taskNoStr="+taskNoStr);
    top.addFlag = false;
	top.createShowDialog(webPath+"/recallBase/showMgrNameDialog?taskNo="+taskNo+"&taskNoStr="+taskNoStr,"选择执行人",'90','90',function(){
		if(top.addFlag){			
   			window.location.reload();//刷新页面
   		}
	});
	
}
function getSysUserListNew() {
	taskNoStr=[];
    
	var val="";
	$('.table_content #tab').find($('input[type=checkbox]:checked')).each(function () {
	    val = this.value.split('=') [1] ;
	    taskNoStr.push(val);
	  });
	if(taskNoStr.length==0){
		//$.myAlert.Alert("请选择要改派的任务");
		alert(top.getMessage("FIRST_SELECT_FIELD","要改派的任务"), 0);
    		return;
    }
    top.addFlag = false;
	top.createShowDialog(webPath+"/recallBase/showMgrNameDialog?taskNo="+taskNo+"&taskNoStr="+taskNoStr,"选择执行人",'75','60',function(){
		if(top.addFlag){			
   			window.location.reload();//刷新页面
   		}
	});
	
}

function selectSysUserList() {

    var dataParam = [{
        customQuery: ''
    }];
    var $tableParent = $('.table_content.check_user').find('table.ls_list');
    var tableId = $tableParent.attr('title');
    var tableType = 'thirdTableTag';
    $.ajax({
        type: 'post',
        url: webPath+'/sysUser/findByPageAjax',
        async: false,
        data: {
            ajaxData: JSON.stringify(dataParam),
            pageSize: 20,
            tableId: tableId,
            tableType: tableType
        },
        success: function(data) {
            $tableParent.find('tbody').html($(data.ipage.result).find('tbody').html());
            setPager($('.table_content.check_user'), data.ipage);
            initPageShow($('.table_content.check_user'), data.ipage);
            $('.table_content.check_user').mCustomScrollbar('scrollTo', 'top');
            SynchronousLoadding = true;
        },
        error: function() {}
    });
}

function SelectedUser(taskNo,taskNoStr) {	
    //var mgrNo=$("#gridSystemModal").find("input[name=mgrNo]").val();
	//var mgrName=$("#gridSystemModal").find("input[name=mgrName]").val();
	var mgrNo = $("input[name=mgrNo]").val();
	var mgrName = $("input[name=mgrName]").val();
	
	if(mgrName!=null||mgrName!=''||mgrName!=undefined){
		var dataParam = [];
	    var beanParam = {
	        taskNo: taskNo,
	        mgrNo: mgrNo,
	        mgrName: mgrName,
	        taskNoStr:taskNoStr
	    };
	    var rs = {};
	    $(".filter-val").each(function() {
	        if (eval($(this).find("input[type=hidden]").val())[0].treeId != "my_filter") {
	            rs[eval($(this).find("input[type=hidden]").val())[0].treeId] = [];
	        }
	    });
	    $(".filter-val").each(function() {
	        if (eval($(this).find("input[type=hidden]").val())[0].treeId != "my_filter") {
	            rs[eval($(this).find("input[type=hidden]").val())[0].treeId].push(eval($(this).find("input[type=hidden]").val())[0]);
	        } else {
	            dataParam.push(eval($(this).find("input[type=hidden]").val()));
	        }
	    });
	    $.each(rs,
	    function(i, obj) {
	        dataParam.push(obj);
	    });
	    var customQuery = {
	        customQuery: $("#filter_in_input").val()
	    };
	    dataParam.push(customQuery);
	    jQuery.ajax({
	        url: webPath+"/recallBase/selectdUserToTaskAjax",
	        data: {
	            ajaxData: JSON.stringify(dataParam),
	            beanAjaxData: JSON.stringify(beanParam)
	        },
	        type: "POST",
	        dataType: "json",
	        beforeSend: function() {},
	        success: function(data) {
	            if (data.flag == "success") {
	            	 //$.myAlert.Alert(data.msg);
	            	top.addFlag = true;
	            	alert(data.msg,1);
	            	$(top.window.document).find("#showDialog .close").click();
	            }else{
	            	//$.myAlert.Alert(data.msg);
	            	alert(data.msg,0);
	            }
	        },
	        error: function(data) {
	            //$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
	            alert(top.getMessage("FAILED_OPERATION"," "),0);
	        }
	    });
	}else{
		//$.myAlert.Alert("请选择执行人！");
		alert("请选择执行人！",0);
	}
    
}

function sysUserCallBack(data){//个人的回调
	$("input[name=mgrNo]").val(data.opNo);
}

function getPager($obj){
	var pager = {};
	pager.pageSize = parseInt($obj.find(".pageer").attr("pageSize"));
	pager.pageNo = parseInt($obj.find(".pageer").attr("pageNo"));
	pager.pageSum = parseInt($obj.find(".pageer").attr("pageSum"));
	return pager;
}
function setPager($obj,pager){
	$obj.find(".pageer").attr("pageSize",pager.pageSize);
	$obj.find(".pageer").attr("pageNo",pager.pageNo);
	$obj.find(".pageer").attr("pageSum",pager.pageSum);
}
/**
 * @param {Object} json
 * return jsonArray
 */
function changeDateParm(json){
	var jsonArray = [];
	$.each(json,function(name,value){
		var jsonObj = {};
		jsonObj.name = name;
		jsonObj.value = value;
		jsonArray.push(jsonObj);
	});
	
	return jsonArray;
}