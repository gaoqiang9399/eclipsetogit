var SynchronousLoadding = true;
var taskNo;
var taskNoStr=new Array();
function getSysUserList(obj,type,value) {
	taskNoStr=[];
    if ((value != null || value != '' || value != undefined)&&type=='1') {//单独指派
        taskNo = value.split("=")[1];
        $(obj).parents("td").parents("tr").find(".selected").removeClass("selected");
        $(obj).parents("td").parents("tr").addClass("selected");
    } else if(type=='2'){
    	var val="";
    	$('.table_content #tab').find($('input[type=checkbox]:checked')).each(function () {
    	    val = this.value.split('=') [1] ;
    	    taskNoStr.push(val);
    	  });
    	if(taskNoStr.length==0){
    		//$.myAlert.Alert("请选择要改派的任务");
    		alert(top.getMessage("FIRST_SELECT_FIELD","要改派的任务"), 0);
    	}
    }else if(type=='3'){
    	 taskNo = '';
    }
    $('#gridSystemModal').modal('show');
    $("#gridSystemModal").find("input[name=mgrNo]").prop("value","");
	$("#gridSystemModal").find("input[name=mgrName]").prop("value","");
	$("#gridSystemModal").find("input[name=taskNo]").prop("value",taskNo);

}
function updateTableContent($obj) {
    var pager = getPager($obj);
    if (pager.pageNo < pager.pageSum) {
        pager.pageNo = pager.pageNo + 1;
        var url;
        var tableType = 'thirdTableTag';
        var $tableParent = $('.table_content.check_user').find('table.ls_list');
        var tableId = $tableParent.attr('title');
        url = webPath+'/sysUser/findByPageAjax';
        var dataParam = [{
            customQuery: ''
        }];
        var data = {
            ajaxData: JSON.stringify(dataParam),
            tableType: tableType,
            tableId: tableId,
        };
        data = $.extend({},data, pager);
        $.ajax({
            type: 'post',
            url: url,
            data: data,
            success: function(data) {
                $obj.find('table.ls_list tbody').append($(data.ipage.result).find('tbody').html());
                initPageShow($obj, data.ipage);
                setPager($obj, data.ipage);
            }
        });
    } else {}
}
function getPager($obj) {
    var pager = {};
    pager.pageSize = parseInt($obj.find('.pageer').attr('pageSize'));
    pager.pageNo = parseInt($obj.find('.pageer').attr('pageNo'));
    pager.pageSum = parseInt($obj.find('.pageer').attr('pageSum'));
    return pager;
}
function setPager($obj, pager) {
    $obj.find('.pageer').attr('pageSize', pager.pageSize);
    $obj.find('.pageer').attr('pageNo', pager.pageNo);
    $obj.find('.pageer').attr('pageSum', pager.pageSum);
}
function initPageShow($obj, pager) {
    var flag = $obj.parent().find('.pagerShow').html();
    if (flag === undefined) {
        var pageShowHtml = '<div class="pagerShow" style="margin-top:-9px">';
        pageShowHtml += '<span style="position:absolute;width:117px;left:225px">';
        pageShowHtml += '共计<span class="pageCount" >' + pager.pageCounts + '</span>条';
        pageShowHtml += '<span class="split"></span>已加载<span class="loadCount">' + pager.endNum + '</span>条';
        pageShowHtml += '</span>';
        pageShowHtml += '</div>';
        $obj.parent().append(pageShowHtml);
    } else {
        var $pageShow = $obj.parent().find('.pagerShow');
        $pageShow.find('.pageCount').html(pager.pageCounts);
        $pageShow.find('.loadCount').html(pager.endNum);
    }
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

function SelectedUser() {

    var mgrNo=$("#gridSystemModal").find("input[name=mgrNo]").val();
	var mgrName=$("#gridSystemModal").find("input[name=mgrName]").val();
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
	            	 alert(data.msg,1);
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
$('#gridSystemModal').on('hidden.bs.modal',
function(e) {
    if (submitFilter()) {
        updateTableData();
    }
});
function sysUserCallBack(data){//个人的回调
	$("input[name=mgrNo]").val(data.opNo);
}