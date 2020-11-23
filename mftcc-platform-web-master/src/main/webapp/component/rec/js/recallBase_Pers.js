var revType;
var c ;
var tr;
$(function(){
	
});
//修改
function func_getById(obj ,url){
	 tr = $(obj).parents("tr");
	$(tr).parents("table").find(".selected").removeClass("selected");
	$(tr).addClass("selected");
	c =	new RightForm(
		{actionUrl:url,formUrl:webPath+"/recallBase/updateAjax",title:"人工催收任务"
		,btns:[{value:"保存",type:"button",onClick:"ajaxTrUpdate(this,reload)","data-elem" : tr}]}
	);
	$("select[name=startRecallWay]").prop("disabled",true);
	$("select[name=recallType]").prop("disabled",true);
	$("select[name=recallWay] option[value=1]").remove();
}
//新增
function func_input(url){
	//alert($);
	c =	new RightForm(
		{actionUrl:url,formUrl:webPath+"/recallBase/insertAjax",title:"人工催收任务",btns:[{value:"保存",type:"button",onClick:"ajaxInsert(this.form)"}]}
	);
	$("select[name=startRecallWay]").val("2");
	$("select[name=startRecallWay]").prop("disabled",true);
	$("select[name=recallType]").val("3");
	$("select[name=recallType]").prop("disabled",true);
	$("select[name=recallWay] option[value=1]").remove();
	
}
function reload(){
	c.close();
	$("table").tableRcswitcher({
    	name:"useFlag"});
	$(tr).parents("table").find(".selected").removeClass("selected");
}
function ajaxInsert(obj){
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
					  //$.myAlert.Alert("操作成功！");
					  alert(top.getMessage("SUCCEED_OPERATION"),1);
					  if(typeof(callback)=="function"){
						 	callback.call(this);
						 }
					  reload();
					  myCustomScrollbar({
					    	obj:"#content",//页面内容绑定的id
					    	url:webPath+"/recallBase/findByPageAjax",//列表数据查询的url
					    	tableId:"tablerec0002",//列表数据查询的table编号
					    	tableType:"thirdTableTag",//table所需解析标签的种类
					    	myFilter : true ,//是否有我的筛选
					    	data:{}
					    });
				}
			},error:function(data){
				 //$.myAlert.Alert("操作失败！");
				 alert("操作失败！",0);
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
						// window.parent.window.$.myAlert.Alert("操作成功！");
						 window.top.alert(top.getMessage("SUCCEED_OPERATION"),1);
					}else{
						//$.myAlert.Alert("操作成功！");
						alert(top.getMessage("SUCCEED_OPERATION"),1);
					}
					 if(data.tableData!=undefined&&data.tableData!=null){
						var trHtml = $(data.tableData).find("tbody tr").html();
						tr.html(trHtml);
					 }
					 if(typeof(callback)=="function"){
					 	callback.call(this);
					 }
					 myCustomScrollbar({
					    	obj:"#content",//页面内容绑定的id
					    	url:webPath+"/recallBase/findByPageAjax",//列表数据查询的url
					    	tableId:"tablerec0002",//列表数据查询的table编号
					    	tableType:"thirdTableTag",//table所需解析标签的种类
					    	myFilter : true ,//是否有我的筛选
					    	data:{}
					    });
				}else if(data.flag=="error"){
					if(alertFlag){
						 //window.parent.window.$.myAlert.Alert(data.msg);
						 window.top.alert(data.msg,0);
					}else{
						//$.myAlert.Alert(data.msg);
						alert(data.msg,0);
						
					}
				}
			},error:function(data){
				if(alertFlag){
					 //window.parent.window.$.myAlert.Alert("操作失败！");
					 window.top.alert("操作失败！",0);
				}else{
					//$.myAlert.Alert("操作失败！");
					alert("操作失败！",0);
				}
			}
		});
	}
}


function taskNoCallback(data){
	$("input[name=taskNo]").val(data.taskNo);
}

/**租后检查完成操作按钮**/
function updateOverSts(idStr){
	alert("是否继续进行此操作!",2,function(){
		var idValue=idStr.split("=")[1];
		var dataParam={id:idValue};
		jQuery.ajax({
			url:webPath+"/recallBase/updateOverStsAjax",
			data:{ajaxData:JSON.stringify(changeDateParm(dataParam))},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					  //$.myAlert.Alert("操作成功！");
					  alert(top.getMessage("SUCCEED_OPERATION"),1);
					  location.reload();
				}
			},error:function(data){
				 //$.myAlert.Alert("操作失败！");
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	});

}
function changeDateParm(json) {
	  var jsonArray = [
	  ];
	  $.each(json, function (name, value) {
	    var jsonObj = {
	    };
	    jsonObj.name = name;
	    jsonObj.value = value;
	    jsonArray.push(jsonObj);
	  });
	  return jsonArray;
	}
function sysUserCallBack(data){//个人的回调
	$("input[name=mgrNo]").val(data.opNo);
}
function appProjectContCallback(data){
	$("input[name=cusNo]").val(data.cusNo);
	$("input[name=cusName]").val(data.cusName);
	$("input[name=appName]").val(data.appName);
	$("select[name=prodType]").val(data.busModel);
	/**	
	$("input[name=curOverDays]").val(data.curOverDays);
	$("input[name=delayIntCumu]").val(data.delayIntCumu);
	$("input[name=brcContAmt]").val(data.brcContAmt);
	$("input[name=termLimit]").val(data.termLimit);
	$("input[name=repayTerm]").val(data.termMon-data.termLimit);
	$("input[name=balSum]").val(data.surInstmAmt);
	$("input[name=prodType]").val(data.prodType);	
	$("input[name=finaNo]").val(data.finaNo);
	$("input[name=finaName]").val(data.finaName);
	$("input[name=leaseType]").val(data.leaseType);
	$("input[name=supplierNo]").val(data.supplierNo);
	$("input[name=supplierName]").val(data.supplierName);
	$("input[name=proxyNo]").val(data.proxyNo);
	$("input[name=proxyName]").val(data.proxyName);
	$("input[name=areaId]").val(data.areaId);
	$("input[name=areaNo]").val(data.areaNo);
	**/
}

function openConSelectTableList(path){
	top.createShowDialog(webPath+'path/recallBase/openConSelectTableList','新增人工任务','90','90',closeCallBack);
}

function closeCallBack(){
	
}
