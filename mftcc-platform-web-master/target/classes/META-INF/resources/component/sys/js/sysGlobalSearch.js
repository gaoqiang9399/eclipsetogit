 var c,tr;
 function func_getById(obj ,url){
	 if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
	 tr = $(obj).parents("tr");
	$(tr).parents("table").find(".selected").removeClass("selected");
	$(tr).addClass("selected");
	c =	new RightForm(
		{actionUrl:url,formUrl:webPath+"/sysGlobalSearch/updateAjax",title:"全局搜索配置"
		,btns:[{value:"保存",type:"button",onClick:"ajaxTrUpdate(this,reload)","data-elem" : tr}]}
	);
}
 function ajaxGetById(obj ,url){
	 if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
	 tr = $(obj).parents("tr");
	$(tr).parents("table").find(".selected").removeClass("selected");
	$(tr).addClass("selected");
	c =	new RightForm(
		{actionUrl:url,title:"全局搜索配置"}
	);
}
function reload(){
	c.close();
	if($(tr)){
		$(tr).parents("table").find(".selected").removeClass("selected");
		$("#tablist td[mytitle]").initMytitle();
		$(tr).parents("table").tableRcswitcher({name:"sts"});
	}
}
//新增
function func_input(url){
	c =	new RightForm(
			{actionUrl:url,formUrl:webPath+"/sysGlobalSearch/insertAjax",title:"全局搜索配置"
			,btns:[{value:"保存",type:"button",onClick:"ajaxInsert(this.form,reload)"}]}
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
					 alert(data.msg,1);
					 if(typeof(callback)=="function"){
					 	callback.call(this);
					 }
					 myCustomScrollbarOpt.reload();
				}else if(data.flag == "error"){
					 alert(data.msg,0);
				}
			},error:function(data){
				alert(top.getMessage("FAILED_OPERATION"," "),0);
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
		jQuery.ajax({
			url:ajaxUrl,
			data:{ajaxData:dataParam,tableId:tableId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					 alert(data.msg,1);
					 if(data.tableData!=undefined&&data.tableData!=null){
						var trHtml = $(data.tableData).find("tbody tr").html();
						tr.html(trHtml);
					 }
					 if(typeof(callback)=="function"){
					 	callback.call(this);
					 }
				}else if(data.flag=="error"){
					 alert(data.msg,0);
				}
			},error:function(data){
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	}
}