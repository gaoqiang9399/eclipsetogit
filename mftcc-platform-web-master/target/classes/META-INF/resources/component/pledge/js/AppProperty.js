function func_getById(obj, url) {
	tr = $(obj).parents("tr");
	$(tr).parents("table").find(".selected").removeClass("selected");
	$(tr).addClass("selected");
	c = new RightForm(
			{actionUrl:url,formUrl:webPath+"/appProperty/updateAjax",title:"评级信息配置",
				btns:[{value:"保存",type:"button",onClick:"ajaxTrUpdate(this, reload)","data-elem":tr}]}
	);
}

function ajaxGetById(obj, url){
	tr = $(obj).parents("tr");
	$(tr).parents("table").find(".selected").removeClass("selected");
	$(tr).addClass("selected");
	rightForm = new RightForm(
			{actionUrl:url,title:"业务属性配置"}
	);
}

function reload(){
	c.close();
	$(tr).parents("table").find(".selected").removeClass("selected");
	$("#tablist td[mytitle]").initMytitle();
}
function rclose(){
	c.close();
}
function func_input(url) {
	c = new RightForm(
			{actionUrl:url,formUrl:webPath+"/appProperty/insertAjax",title:"业务属性配置",
				btns:[{value:"保存",type:"button",onClick:"ajaxInsert(this.form, rclose)"}]}
		);
}

function ajaxInsert(obj, callback){
	var flag = submitJsMethod(obj, '');
	if(flag) {
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray());
		jQuery.ajax({
			url:url,
			data:{ajaxData:dataParam},
			type:"POST",
			dataType:"json",
			beforeSend:function(){},
			success:function(data){
				if(data.flag == "success"){
					myCustomScrollbarOpt.reload();
					if(typeof(callback)=="function"){
						callback.call(this);
					}
				} else if (data.flag == "error") {
					alert("该信息已存在！",0);
				}
			},error:function(data){
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	}
}

function ajaxTrUpdate(obj, callback){
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
			beforeSend:function(){},
			success:function(data){
				if(data.flag == "success") {
					alert(top.getMessage("SUCCEED_OPERATION"),1);
					if(data.tableData!=undefined&&data.tableData!=null){
						var trHtml = $(data.tableData).find("tbody tr").html();
						tr.html(trHtml);
						$("table").tableRcswitcher({
				    		name:"propertySts",onText:"启用",offText:"停用"});
					}
					if(typeof(callback) == "function"){
						callback.call(this);
					}
				} else if (data.flag == "error"){
					alert(data.msg,0);
				}
			},
			error:function(data){
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		})
	}
}