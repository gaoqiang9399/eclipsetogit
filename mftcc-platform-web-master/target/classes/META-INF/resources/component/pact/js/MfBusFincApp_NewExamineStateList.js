var MfBusFincApp_NewExamineStateList=function(window,$){
	//初始化在履行借据贷后检查状态列表
	var _init=function(){
		myCustomScrollbar({
            obj:"#content",//页面内容绑定的id
            url:webPath+"/mfBusFincApp/findNewExamineStateByPageAjax",//列表数据查询的url
            tableId:"tablefincExamineStateForFincListNew",//列表数据查询的table编号
            // tableType:"thirdTableTag",//table所需解析标签的种类
            tableType:"tableTag",//table所需解析标签的种类
            pageSize:30//加载默认行数(不填为系统默认行数)
        });
	};
	//新增贷后检查任务
	var _addExamineTask = function (url) {
        top.addFlag = false;
        top.createShowDialog(url,"新增贷后检查任务",'90','90',function(){
            if(top.addFlag){
                updateTableData();//重新加载列表数据
            }
        });
    }


	//单笔指派贷后检查任务
	var _assignExamineTaskSingle=function(obj,url){
        pasNoStr = "";
		if(url!=""&&url!=null){
			var parm=url.split("?")[1];
			pasNoStr=parm.split("&")[0].split("=")[1];
		};
        selectUserDialog(_assignExamineTask);
	};
	var _loanAfterExamine=function(obj,url){

		var urlStr = webPath+url+"&examEntrance=loanAfter";
		top.window.openBigForm(urlStr,"贷后检查",function(){
            updateTableData();//重新加在列表数据taskShowDialogiframe
		});
	};
	//批量指派贷后检查任务
	var _assignExamineTaskBatch=function(){
        pasNoStr = "";
		$('.table_content #tab').find($('input[type=checkbox]:checked')).each(function () {
			val = this.value.split('&') [0] ;
            pasNoStr=pasNoStr+"|"+val.split("=")[1];
    	  });
		if(pasNoStr==""){
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD","指派检查任务的数据"), 0);
			return false;
		}
        selectUserDialog(_assignExamineTask);
	};
	//指派任务
	var _assignExamineTask=function(user){
		var executorsStr=user.opNo;
		LoadingAnimate.start();
		$.ajax({
			url : webPath+"/mfExamineHis/assignExamineTaskAjaxNew",
			data : {
				executorsStr : executorsStr,
                pasNo:pasNoStr
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				LoadingAnimate.stop();
				pasNoStr="";
				if(data.flag=="success"){
					window.top.alert(data.msg, 1);
					updateTableData();//重新加载列表数据
				}else if(data.flag=="error"){
					window.top.alert(data.msg, 0);
				}
			},
			error : function() {
				LoadingAnimate.stop();
				alert(top.getMessage("ERROR_REQUEST_URL"));
			}
		});
	};
//指派任务
    var _deleteExamine=function(obj,url){
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        $.ajax({
            url : webPath+"/mfExamineHis/assignExamineTaskAjaxNew",
            data : {
                executorsStr : executorsStr,
                pasNo:pasNoStr
            },
            type : 'post',
            dataType : 'json',
            success : function(data) {
                LoadingAnimate.stop();
                pasNoStr="";
                if(data.flag=="success"){
                    window.top.alert(data.msg, 1);
                    updateTableData();//重新加载列表数据
                }else if(data.flag=="error"){
                    window.top.alert(data.msg, 0);
                }
            },
            error : function() {
                LoadingAnimate.stop();
                alert(top.getMessage("ERROR_REQUEST_URL"));
            }
        });
    };

	var _getDetailPage = function (obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		// top.LoadingAnimate.start();
		// window.location.href=url;
        top.window.openBigForm(url,"客户详情",function(){
            // updateTableData();//重新加在列表数据
        });
	};
	//单笔忽略贷后检查任务
	var _ignoreExamineTaskSingle = function (obj,url) {
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        top.addFlag = false;
        top.createShowDialog(url,"忽略贷后检查任务",'50','40',function(){
            if(top.addFlag){
                updateTableData();//重新加载列表数据
            }
        });
    }
    //批量忽略贷后检查任务
    var _ignoreExamineTaskBatch = function (url) {
		var pasNo ="";
        $('.table_content #tab').find($('input[type=checkbox]:checked')).each(function () {
            val = this.value.split('&') [0] ;
            pasNo=pasNo+","+val.split("=")[1];
        });
        if(pasNo==""){
            window.top.alert(top.getMessage("FIRST_SELECT_FIELD","忽略检查任务的数据"), 0);
            return false;
        }
        url = url+"?pasNo="+pasNo.substring(1);
        top.addFlag = false;
        top.createShowDialog(url,"忽略贷后检查任务",'50','30',function(){
            if(top.addFlag){
                updateTableData();//重新加载列表数据
            }
        });
    }
	//跳转到详情或者是忽略详情页面
    var _loanAfterExamineDetailNew = function(obj,url){
		var pasSts='';
        if(url!=""&&url!=null){
            var parm=url.split("?")[1];
            pasSts=parm.split("&")[1].split("=")[1];
            if("1"==pasSts){
            	//跳转到详情页面
                var urlStr = webPath+"/mfExamineHis/examineApplyNew?"+parm+"&examEntrance=loanAfter";
                top.window.openBigForm(urlStr,"贷后检查详情",function(){});
			}else if("2"==pasSts){
            	//跳转到忽略信息页面
				url =webPath+"/mfExamineHis/ignoreExamineDetail?"+parm;
                top.createShowDialog(url,"忽略贷后检查任务",'50','50');
			}
        };
	}

	return{
		init:_init,
		assignExamineTask:_assignExamineTask,
		assignExamineTaskSingle:_assignExamineTaskSingle,
		assignExamineTaskBatch:_assignExamineTaskBatch,
		getDetailPage:_getDetailPage,
		loanAfterExamine:_loanAfterExamine,
        addExamineTask:_addExamineTask,
        ignoreExamineTaskSingle:_ignoreExamineTaskSingle,
        ignoreExamineTaskBatch:_ignoreExamineTaskBatch,
        loanAfterExamineDetailNew:_loanAfterExamineDetailNew,
        deleteExamine:_deleteExamine,
	};
}(window,jQuery)
