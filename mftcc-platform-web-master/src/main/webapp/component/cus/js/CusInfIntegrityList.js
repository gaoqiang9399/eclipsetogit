;
var CusInfIntegrityList=function(window,$){
	//初始化
	var _init=function(){
		$(".contentB").mCustomScrollbar({
			advanced:{
				updateOnContentResize:true,
			},
		});
	};
	
	//保存方法
	var _addCusInfo = function(action,title,isMust,showType,sort,tableName){
		if(action == "MfTrenchShareProfitRateAction"){//添加分润规则之前先添加分润配置信息
			var flag = false;
			jQuery.ajax({
	            url:webPath+"/mfShareProfitConfig/getByIdAjax",
	            data:{cusNo:cusNo},
	            type:"post",
	            async:false,
	            success:function(data){
	                if(data.flag == "success"){
	                	
	                }else{
	                	flag = true;
	                	alert(top.getMessage("FIRST_OPERATION_ADD","分润配置信息"),3);
	                }
	            },
	            error:function(){
	            	flag = true;
	            	alert(top.getMessage("FAILED_OPERATION"),0);
	            }
	        });
			if(flag){
				return;
			}
		}
		top.action = action;
		top.title = title;
		top.isMust = isMust;
		top.showType = showType;
		top.sort = sort;
		action = webPath+"/"+action.substring(0,1).toLowerCase()+action.substring(1);
		action =action.replace("Action","");
		var inputUrl = action + "/input?cusNo="+cusNo+"&relNo="+cusNo;
		top.addFlag = false;
		top.htmlStrFlag = false;//标识是否将客户表单信息的html字符串放在top下
		top.htmlString = null;
		top.tableName = tableName;
		$("#myModalLabel", window.parent.document).text("登记" + title);
		window.location.href=  inputUrl;
	};
	//保存方法
	var _addCusFinMain = function(){
		top.ifCusFinMain = true;
		$("#myModalLabel", window.parent.document).text("财务报表");
		window.location.href=  webPath+'/cusFinMain/getListPage1?cusNo='+cusNo;
	};
	return{
		init:_init,
		addCusInfo:_addCusInfo,
		addCusFinMain:_addCusFinMain
	}
}(window,jQuery);