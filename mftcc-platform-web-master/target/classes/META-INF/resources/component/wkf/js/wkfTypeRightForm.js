var c ;
	//修改
	function func_getById(obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		var tr = $(obj).parents("tr");
		$(tr).parents("table").find(".selected").removeClass("selected");
		$(tr).addClass("selected");
		c =	new RightForm(
			{actionUrl:url,formUrl:webPath+"/wkfType/updateAjax",title:"审批类型"
			,btns:[{value:"保存",type:"button",onClick:"ajaxTrUpdate(this,reload())","data-elem":tr}],
			beforeCallback:function(){
				console.log($("input[name=wkfVpNo]"));
				$("input[name=wkfVpNo]").attr("readonly","readonly");
			}}
		);
	}
	//新增
	function func_input(url){
		c =	new RightForm(
			{actionUrl:url,formUrl:webPath+"/wkfType/insertAjax",title:"审批类型",btns:[{value:"保存",type:"button",onClick:"ajaxInsert(this.form)"}]}
		);
	}
	function reload(){
		c.close();
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
						  //$.myAlert.Alert(top.getMessage("SUCCEED_OPERATION"));
						alert(top.getMessage("SUCCEED_OPERATION"),1);
						  reload();
						  myCustomScrollbar({
							  obj:"#content",//页面内容绑定的id
						    	url:webPath+"/wkfType/findByPageAjax",//列表数据查询的url
						    	tableId:"tablewkf5001",//列表数据查询的table编号
						    	tableType:"thirdTableTag",//table所需解析标签的种类
						    	pageSize:30,//加载默认行数(不填为系统默认行数)
								myFilter : true
							//是否有我的筛选
							});
					}else{
						alert(data.msg,0);
					}
				},error:function(data){
					 //$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	}
	
