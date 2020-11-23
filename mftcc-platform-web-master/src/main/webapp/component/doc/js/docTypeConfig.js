 function func_getById(obj ,url){
	 tr = $(obj).parents("tr");
	$(tr).parents("table").find(".selected").removeClass("selected");
	$(tr).addClass("selected");
	c =	new RightForm(
		{actionUrl:url,formUrl:webPath+"/docTypeConfig/updateAjax",title:"单表维护"
		,btns:[{value:"保存",type:"button",onClick:"ajaxTrUpdate(this,reload)","data-elem" : tr}]}
	);
}
 function ajaxGetById(obj ,url){
	 tr = $(obj).parents("tr");
	$(tr).parents("table").find(".selected").removeClass("selected");
	$(tr).addClass("selected");
	rightForm =	new RightForm(
		{actionUrl:url,title:"单表维护"}
	);
}
function reload(){
	c.close();
	$(tr).parents("table").find(".selected").removeClass("selected");
	$("#tablist td[mytitle]").initMytitle();
}
//新增
function func_input(url){
	c =	new RightForm(
			{actionUrl:url,formUrl:webPath+"/docTypeConfig/insertAjax",title:"单表维护"
			,btns:[{value:"保存",type:"button",onClick:"ajaxInsert(this.form)"}]}
		);
}
function ajaxInsert(obj,callback){
	var flag = submitJsMethod(obj, '');
	if(flag){
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray()); 
		jQuery.ajax({
			url:url,
			data:{ajaxData:dataParam},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					 $.myAlert.Alert(top.getMessage("SUCCEED_OPERATION"));
					 if(typeof(callback)=="function"){
					 	callback.call(this);
					 }
					 myCustomScrollbar({
				    	obj:"#content",//页面内容绑定的id
				    	url:webPath+"/docTypeConfig/findByPageAjax",//列表数据查询的url
				    	tableId:"tabledoc0002",//列表数据查询的table编号
				    	tableType:"thirdTableTag",//table所需解析标签的种类
				    	myFilter:false, //是否有我的筛选(列表列动态切换)
				    	pageSize:30,//加载默认行数(不填为系统默认行数)
				    	data:{},//指定参数
				    	callback:function(){
				    		$("table").tableRcswitcher({
					    		name:"sts"});
				    	}//方法执行完回调函数（取完数据做处理的时候）
				    });
				}
			},error:function(data){
				 $.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
			}
		});
	}
}

function ajaxTrUpdate(obj,callback){
	var contentForm = $(obj).parents("#rightFormInfo");
	var tr = $(obj).data("elem");
	var table = tr.parents("table");
	var tableId = table.attr("title");
	var flag = submitJsMethod(contentForm[0], '');
	if(flag){
		var ajaxUrl = contentForm.attr("action");
		var dataParam = JSON.stringify(contentForm.serializeArray()); 
		var alertFlag = window.parent.window.$.myAlert;
		jQuery.ajax({
			url:ajaxUrl,
			data:{ajaxData:dataParam,tableId:tableId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					if(alertFlag){
						 window.parent.window.$.myAlert.Alert(top.getMessage("SUCCEED_OPERATION"));
					}else{
						$.myAlert.Alert(top.getMessage("SUCCEED_OPERATION"));
					}
					 if(data.tableData!=undefined&&data.tableData!=null){
						var trHtml = $(data.tableData).find("tbody tr").html();
						tr.html(trHtml);
					 }
					 if(typeof(callback)=="function"){
					 	callback.call(this);
					 }
				}else if(data.flag=="error"){
					if(alertFlag){
						 window.parent.window.$.myAlert.Alert(data.msg);
					}else{
						$.myAlert.Alert(data.msg);
					}
				}
			},error:function(data){
				if(alertFlag){
					 window.parent.window.$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
				}else{
					$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
				}
			}
		});
	}
}