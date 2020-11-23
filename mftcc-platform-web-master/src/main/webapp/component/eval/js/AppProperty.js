	function initData(){
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				theme:"minimal-dark",
//				updateOnContentResize:true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		var propertyAppType = $("input[name=propertyAppType]").val();
		/*if(propertyAppType=="2"){
			$("input[name=propertyNameReal]").parents("tr").eq(0).hide();
			$("textarea[name=dataSource]").parents("tr").eq(0).hide();
			$("select[name=propertyType]").parents("tr").eq(0).hide();
		}*/
		/*//财务、定量、约束
		if(evalPropertyType=="1"||evalPropertyType=="2"||evalPropertyType=="5"){
			$("#addEvalIndexSub").removeClass("hide");
			$("#addEvalIndexSub").addClass("show");
		}else if(evalPropertyType=="3"||evalPropertyType=="4"){//定性、调整
			$("#addEvalSub").removeClass("hide");
			$("#addEvalSub").addClass("show");
		}*/
		evalPropertyTypeChange();
		var propertyId =$("input[name=propertyId]").val();
		formItems();
	}
	
	function formItems(){
		//表单项
		var titleStr ='<div class="search-title"><div class="btn-group"><ul class="search-tab">';
		$.each(byformEnList,function(i,formItem){
			if(i==0){
				titleStr = titleStr+'<li class="current" onclick="showTags(this)" value="'+formItem.formNameEn+'">'+formItem.formNameZn+'</li>';
			}else{
				titleStr = titleStr+'<li onclick="showTags(this)" value="'+formItem.formNameEn+'">'+formItem.formNameZn+'</li>';
			};
		});
		titleStr = titleStr+'</ul></div></div>';
		$("#titleStr-div").html(titleStr);
		
		var htmlStr ="";
		$.each(byformEnList,function(i,formItemByformEn){
			if(i==0){
				htmlStr = '<table style="margin-left: 15px;margin-top:10px;"><tbody>';
				var hiddenStr = "";
				var showStr ="";
				$.each(allUseablelist,function(j,formItem){
					if(formItem.formNameEn==formItemByformEn.formNameEn){
						hiddenStr = '<input type="hidden" id = "formNameEn"  value="'+formItem.formNameEn+'"><input type="hidden" id = "dataType"  value="'+formItem.dataType+'"><input type="hidden" id = "picParmEn" value="'+formItem.picParmEn+'"><input type="hidden" id = "formNameZn" value="'+formItem.formNameZn+'">';
						showStr = '&nbsp<span onclick="getFincUseByTd(this);">'+formItem.columnNameZn+'</span>';
						//showStr = '<td style="text-align:left;width:20%;" onclick="getFincUseByTd(this);">'+formItem.columnNameZn+'</td>';
						if(allUseablelist.length>=5){
							if((j +1)%5 ==0 ){
								htmlStr = htmlStr + '<td style="text-align:left;width:20%;"><input type="radio" onclick="getFincUse(this);" id="'+formItem.itemId+'"  value="'+formItem.columnNameEn+'" title="'+formItem.columnNameZn+'">'+showStr+'</input>'+hiddenStr+'</td></tr>';
							}else if((j +1)%5 ==1 ){
								htmlStr = htmlStr + '<tr style="height:30px;">'
										+ '<td style="text-align:left;vertical-align: top;width:20%;"> <input type="radio" id="'+formItem.itemId+'"  value="'+formItem.columnNameEn+'" onclick="getFincUse(this);"  title="'+formItem.columnNameZn+'">'+showStr+'</input>'+hiddenStr+'</td>	';
							}else{
								htmlStr = htmlStr + '<td style="text-align:left;width:20%;"> <input type="radio" id="'+formItem.itemId+'"  value="'+formItem.columnNameEn+'" onclick="getFincUse(this);"  title="'+formItem.columnNameZn+'">'+showStr+'</input>'+hiddenStr+'</td>	';
							}
						}else{
							if((j +1)%5 ==0 ){
								htmlStr = htmlStr + '<td style="text-align:left;width:20%;"><input type="radio" onclick="getFincUse(this);" id="'+formItem.itemId+'" value="'+formItem.columnNameEn+'" title="'+formItem.columnNameZn+'">'+hiddenStr+'</td></tr>';
							}else if((j +1)%5 ==1 ){
								htmlStr = htmlStr + '<tr style="height:30px;">'
										+ '<td style="text-align:left;vertical-align: top;width:20%;"> <input type="radio" id="'+formItem.itemId+'"  value="'+formItem.columnNameEn+'" onclick="getFincUse(this);"  title="'+formItem.columnNameZn+'">'+hiddenStr+'</td>	';
							}else{
								htmlStr = htmlStr + '<td style="text-align:left;width:20%;"> <input type="radio" id="'+formItem.itemId+'"  value="'+formItem.columnNameEn+'" onclick="getFincUse(this);"  title="'+formItem.columnNameZn+'">'+hiddenStr+'</td>	';
							}
						}
						if((j +1)%5 !=0 && (j +1)==allUseablelist.length){
							htmlStr = htmlStr + '</tr>';
						}
					}
				});
			htmlStr = htmlStr + '</tbody></table>';
			$("#"+formItemByformEn.formNameEn).html(htmlStr);
			}
		});
	
	}
	
	function showFormItems(formNameEn){
		jQuery.ajax({
			url:webPath+"/appProperty/getByformEnList",
			data:{formNameEn:formNameEn},
			type:"POST",
			dataType:"json",
			success:function(data){
				if(data.flag == "success"){
						htmlStr = '<table style="margin-left: 15px;margin-top:10px;"><tbody>';
						var hiddenStr = "";
						var showStr ="";
						$.each(data.list,function(i,formItem){
							hiddenStr = '<input type="hidden" id = "formNameEn"  value="'+formItem.formNameEn+'"><input type="hidden" id = "dataType" id="'+formItem.itemId+'" value="'+formItem.dataType+'"><input type="hidden" id = "picParmEn" value="'+formItem.picParmEn+'"><input type="hidden" id = "formNameZn" value="'+formItem.formNameZn+'">';
							//showStr = '<td style="text-align:left;width:20%;" onclick="getFincUseByTd(this);">'+formItem.columnNameZn+'</td>';
							showStr = '&nbsp<span onclick="getFincUseByTd(this);">'+formItem.columnNameZn+'</span>';
							if(data.list.length>=5){
								if((i +1)%5 ==0 ){
									htmlStr = htmlStr + '<td style="text-align:left;width:20%;"><input type="radio" onclick="getFincUse(this);" id="'+formItem.itemId+'" value="'+formItem.columnNameEn+'" title="'+formItem.columnNameZn+'">'+showStr+'</input>'+hiddenStr+'</td></tr>';
								}else if((i +1)%5 ==1 ){
									htmlStr = htmlStr + '<tr style="height:30px;">'
											+ '<td style="text-align:right;vertical-align: top;width:20%;"> <input type="radio" id="'+formItem.itemId+'"  value="'+formItem.columnNameEn+'" onclick="getFincUse(this);"  title="'+formItem.columnNameZn+'">'+showStr+'</input>'+hiddenStr+'</td>	';
								}else{
									htmlStr = htmlStr + '<td style="text-align:left;width:20%;"> <input type="radio" id="'+formItem.itemId+'"  value="'+formItem.columnNameEn+'" onclick="getFincUse(this);"  title="'+formItem.columnNameZn+'">'+showStr+'</input>'+hiddenStr+'</td>	';
								}
							}else{
								//alert((i +1)%5);
								if((i +1)%5 ==0 ){
									htmlStr = htmlStr + '<td style="text-align:left;width:20%;"><input type="radio" onclick="getFincUse(this);" id="'+formItem.itemId+'" value="'+formItem.columnNameEn+'" title="'+formItem.columnNameZn+'">'+showStr+'</input>'+hiddenStr+'</td></tr>';
								}else if((i +1)%5 ==1 ){
									htmlStr = htmlStr + '<tr style="height:30px;">'
											+ '<td style="text-align:left;vertical-align: top;width:20%;"> <input type="radio" id="'+formItem.itemId+'"  value="'+formItem.columnNameEn+'" onclick="getFincUse(this);"  title="'+formItem.columnNameZn+'">'+showStr+'</input>'+hiddenStr+'</td>	';
								}else{
									htmlStr = htmlStr + '<td style="text-align:left;width:20%;"> <input type="radio" id="'+formItem.itemId+'"  value="'+formItem.columnNameEn+'" onclick="getFincUse(this);"  title="'+formItem.columnNameZn+'">'+showStr+'</input>'+hiddenStr+'</td>	';
								}
							}
							if((i +1)%5 !=0 && (i +1)==data.list.length){
								htmlStr = htmlStr + '</tr>';
							}
						});
						htmlStr = htmlStr + '</tbody></table>';
						$("#"+formNameEn).html(htmlStr);
				}else{
					 window.top.alert(data.msg,0);
				}
			},error:function(data){
				 window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
		
		
		}
	
	function getFincUse(obj){
		$("input[name=propertyNo]").val($(obj).val());
		$("input[name=itemId]").val($(obj).attr("id"));
		$("input[name=propertyNameReal]").val($(obj).parent().find("span").html());
		$("textarea[name=dataSource]").val($(obj).parent().find("input[id=formNameZn]").val());
		$("input[name=tableName]").val($(obj).parent().find("input[id=formNameEn]").val());
		var dataType = $(obj).parent().find("input[id=dataType]").val();
		$("input[name=propertyValueType]").val(dataType);
		//如果为字典项,添加上数据字典名
		if(dataType=="02"){
			$("input[name=propertyKeyName]").val($(obj).parent().find("input[id=picParmEn]").val());
		}
		dialog.getCurrent().close();
	};
	
	function getFincUseByTd(object){
		var obj = $(object).parent().find("input[type=radio]");
		$("input[name=propertyNo]").val($(obj).val());
		$("input[name=propertyNameReal]").val($(obj).parent().find("span").html());
		$("textarea[name=dataSource]").val($(obj).parent().find("input[id=formNameZn]").val());
		$("input[name=tableName]").val($(obj).parent().find("input[id=formNameEn]").val());
		var dataType = $(obj).parent().find("input[id=dataType]").val();
		$("input[name=propertyValueType]").val(dataType);
		//如果为字典项
		if(dataType=="02"){
			$("input[name=propertyKeyName]").val($(obj).parent().find("input[id=picParmEn]").val());
		}
		dialog.getCurrent().close();
	};
	function showTags(obj){
		var value = $(obj).attr("value");
		showFormItems(value);
		var oldvalue = $("li[class=current]").attr("value");
		$("div[id="+oldvalue+"]").hide();
		$("div[id="+value+"]").show();
		$("li[class=current]").removeClass("current");
		$(obj).attr("class","current");
	}
	//指标类型发生变化
	function propertyTypeChange(){
		var propertyType = $("select[name=propertyType]").val();
		if(propertyType=="01"){//财务指标
			$("input[name=propertyNameReal]").parents(".rows").find(".form-label").text("财务指标");
			$("textarea[name=dataSource]").parents(".rows").find(".form-label").text("计算公式");
			$("input[name=tableName]").val("");
			$("input[name=propertyValueType]").val("");
			$("input[name=propertyKeyName]").val("");
			$("input[name=propertyNo]").attr("readonly","readonly");
		}else if(propertyType=="02"){//表单项
			$("input[name=propertyNameReal]").parents(".rows").find(".form-label").text("表单项");
			$("textarea[name=dataSource]").parents(".rows").find(".form-label").text("数据来源");
		}
		$("input[name=propertyNo]").val("");		    						    						    			
		$("input[name=propertyNameReal]").val("");
		$("textarea[name=dataSource]").val("");
	}
	
	
	//放大镜方法
	function selectPropertyItem(obj){
		var propertyType = $("select[name=propertyType]").val();
		if(propertyType=="01"){//财务指标
				dialog({
					id:"selectCusFinFormDialog",
					url:webPath+"/cusFinForm/getListPageForSelect",
					title:"财务报表",
					width:750,
		    		height:400,
		    		backdropOpacity:0,
		    		onshow:function(){
		    			this.returnValue = null;
		    		},onclose:function(){
		    			if(this.returnValue){
		    				var cusFinForm = this.returnValue;
		    				 $("input[name=propertyNo]").val(cusFinForm.formNo).attr("readonly","readonly");		    						    						    			
		    				 $("input[name=propertyNameReal]").val(cusFinForm.formName);
		    				 $("textarea[name=dataSource]").val(cusFinForm.formDesc);	
		    			}
		    		}
					
				}).showModal();
		}else if(propertyType=="02"){//表单项
				dialog({
					title:'表单项',
					content: $("#formItem-div"),
					width:750,
					height:400,
					backdropOpacity:0,
					onshow:function(){
						$("#formItem-div").find("input[type=radio]").attr("checked",false);
					},onclose:function(){
						if(typeof(this.returnValue) == "undefined" || typeof(this.returnValue) == null || this.returnValue == ''){
							//alert('unde');
						}else{
						}
					}
				}).showModal();
		}
	}
	
	//评级指标新增或修改保存
	function ajaxInsertOrUpdateThis(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
					url:url,
					data:{ajaxData:dataParam},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						LoadingAnimate.stop();
						if(data.flag == "success"){
							$("input[name=propertyId]").val(data.propertyId);
							$("input[name=propertyNo]").val(data.propertyNo);
							$("#addEvalSub").removeAttr("disabled");
							$("#addEvalIndexSub").removeAttr("disabled");
							//refreshEvalSub();
							window.top.alert(data.msg, 1);
						}else{
							window.top.alert(data.msg, 0);
						}
					},error:function(data){
						LoadingAnimate.stop();
						 window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
				});
			
			}
	}
	//新增评级指标子项
	function addEvalSub(){
		var propertyId =$("input[name=propertyId]").val();
		var propertyNo =$("input[name=propertyNo]").val();
		var htmlStr ='<tr><td align="center" style="display:none">'+propertyNo+'</td>'+
		'<td align="center" style="display:none"><input type="hidden" name="subId" value=""></td>'+
		'<td align="center" class="subNo"><input type="text" name="subNo" value=""></td>'+
		'<td align="center" class="subName"><input type="text" name="subName" value=""></td>'+
		'<td align="center" class="remark"><input type="text" name="remark" value=""></td>'+
		'<td align="center" class="editEvalSub"><a href="javascript:void(0);" onclick="cancelAdd(this);return false;" class="abatch" >取消</a></td>'+
		'<td align="center"><a href="javascript:void(0);" onclick="insertEvalSub(this,\'/mfEvalIndexSub/insertAjax?subId=\');return false;" class="abatch" >保存</a></td>'+
		'<td id = "delete" align="center" style="color:#c4c4c4">删除</td></tr>';
		$("tbody[id='tab']").append(htmlStr);
	}
	//新增评级指标子项
	function addEvalIndexSub(){
		var propertyId =$("input[name=propertyId]").val();
		var propertyNo =$("input[name=propertyNo]").val();
		var url = webPath+"/mfEvalIndexSub/input?propertyId="+propertyId+"&propertyNo="+propertyNo;
		top.saveFlag = false;
		window.parent.openBigForm(url,"添加评级指标子项",function(){
			if(top.saveFlag){
				$.ajax({
					url:webPath+"/mfEvalIndexSub/getEvalSubListHtmlAjax",
					data:{propertyId:propertyId},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						LoadingAnimate.stop();
						if(data.flag == "success"){
							$("#evalIndexSubList").html(data.tableHtml);
						}
					},error:function(data){
						LoadingAnimate.stop();
						 window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
				});
			}
		});
	}
	//保存指标子项
	function insertEvalSub(obj,urlArgs){
		var subId = urlArgs.split("?")[1].split("=")[1];
		var url =  webPath + urlArgs.split("?")[0];
		var $tr = $(obj).parents("tr").find("input");
		var subNo =$(obj).parents("tr").find("td").find("input[name=subNo]").val();
		var propertyId = $("input[name=propertyId]").val();
		var propertyNo = $("input[name=propertyNo]").val();
		var evalPropertyType = $("[name=evalPropertyType]").val();
		var dataParm={
				subId:subId,
				subNo:subNo,
				propertyNo:propertyNo,
				propertyId:propertyId,
				subType:evalPropertyType
		};
		dataParm.subName=$(obj).parents("tr").find("input[name=subName]").val();
		dataParm.remark=$(obj).parents("tr").find("input[name=remark]").val();
		var dataParam = JSON.stringify(dataParm);
		LoadingAnimate.start();
		jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						window.top.alert(data.msg, 1);
						$(obj).parents("tr").find("input[name=subId]").val(data.mfEvalIndexSub.subId);
						$(obj).parents("tr").find("input[name=subNo]").hide();
						$(obj).parents("tr").find("input[name=subNo]").parent("td").html(data.mfEvalIndexSub.subNo);
						$(obj).parents("tr").find("input[name=subName]").hide();
						$(obj).parents("tr").find("input[name=subName]").parent("td").html(data.mfEvalIndexSub.subName);
						$(obj).parents("tr").find("input[name=remark]").hide();
						$(obj).parents("tr").find("input[name=remark]").parent("td").html(data.mfEvalIndexSub.remark);
						var deleteThml = '<a href="javascript:void(0);" onclick="deleteEvalSub(this,\'\');return false;" class="abatch">删除</a>';
						$(obj).parents("tr").find("td[id=delete]").html(deleteThml);
						$(obj).parents("tr").find("td[class=editEvalSub]").html('<a href="javascript:void(0);" onclick="editEvalSub(this,\'\');return false;" class="abatch">编辑</a>');
					}
				},error:function(data){
					LoadingAnimate.stop();
					 window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
	}
	
	//删除评级指标子项
	function deleteEvalSub(obj,url){
		if(url==""){
			var subId = $(obj).parents("tr").find("input[name=subId]").val();
			url = webPath+"/mfEvalIndexSub/deleteAjax?subId="+subId;
		};
		ajaxTrDelete(obj,url);
	}
	//编辑评级指标子项
	function editEvalSub(obj,url){
		var $tr=$(obj).parents("tr");
		var subName = $tr.find("td[class=subName]").html();
		var remark = $tr.find("td[class=remark]").html();
		$tr.find("td[class=subName]").html('<input type="text" name="subName" value="'+subName+'">');
		$tr.find("td[class=remark]").html('<input type="text" name="remark" value="'+remark+'">');
		$(obj).parents("td").html('<a href="javascript:void(0);" onclick="cancelEdit(this);return false;" class="abatch">取消</a>');
	}
	//取消编辑
	function cancelEdit(obj){
		var $tr=$(obj).parents("tr");
		var subName =$tr.find("input[name=subName]").val();
		var remark =$tr.find("input[name=remark]").val();
		$tr.find("input[name=subName]").parent().html(subName);
		$tr.find("input[name=remark]").parent().html(remark);
		$(obj).parents("td").html('<a href="javascript:void(0);" onclick="editEvalSub(this,\'\');return false;" class="abatch">编辑</a>');
	}
	//取消新增
	function cancelAdd(obj){
		$(obj).parents("tr").remove();
	}
	//删除评级指标
	function deleteAppProperty(){
		var propertyId = $("input[name=propertyId]").val();
		alert(top.getMessage("CONFIRM_DELETE"),2,function(){
			LoadingAnimate.start();
			jQuery.ajax({
				url:webPath+"/appProperty/deleteAjax?propertyId="+propertyId,
				data:{},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						window.top.alert(data.msg, 1);
						myclose_click();
					}
				},error:function(data){
					LoadingAnimate.stop();
					 window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		});
	}
	
	//刷新评级子项列表
	function refreshEvalSub(){
		var propertyId=$("input[name=propertyId]").val();
		$.ajax({
			url:webPath+"/mfEvalIndexSub/getEvalSubListHtmlAjax",
			data:{propertyId:propertyId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				LoadingAnimate.stop();
				if(data.flag == "success"){
					$("#evalIndexSubList").html(data.tableHtml);
				}
			},error:function(data){
				LoadingAnimate.stop();
				 window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	//指标类型变化事件
	function evalPropertyTypeChange(){
		var evalPropertyType = $("[name=evalPropertyType]").val();
		//财务、定量、约束
		if(evalPropertyType=="1"||evalPropertyType=="2"||evalPropertyType=="5"){
			$("#addEvalIndexSub").removeClass("hide");
			$("#addEvalIndexSub").addClass("show");
			$("#addEvalSub").removeClass("show");
			$("#addEvalSub").addClass("hide");
		}else if(evalPropertyType=="3"||evalPropertyType=="4"){//定性、调整
			$("#addEvalSub").removeClass("hide");
			$("#addEvalSub").addClass("show");
			$("#addEvalIndexSub").removeClass("show");
			$("#addEvalIndexSub").addClass("hide");
		}
	}