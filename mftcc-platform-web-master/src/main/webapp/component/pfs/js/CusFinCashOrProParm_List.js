var cusFinCashOrProParmList = function(window,$){
	var _init = function(){
		$("#newParm-div").find("select[name=zcfzType]").parents(".rows:eq(0)").hide();
		if(reportType == "3"){//现金流方向
			$("#newParm-div").find("select[name=cashDirection]").parents(".rows:eq(0)").show();
		}else if(reportType != "3"){
			$("#newParm-div").find("select[name=cashDirection]").parents(".rows:eq(0)").hide();
		}
	 	$(".scroll-content").mCustomScrollbar({
			advanced:{
				theme:"minimal-dark",
				updateOnContentResize:true
			}
		}); 
	 	$("#parmShow-div").height($("body").height()-65);
	 	 $("#parmShow-div").mCustomScrollbar({
			advanced:{
				theme:"minimal-dark",
				updateOnContentResize:false
			}
		}); 
	};
	
	//新增/配置
	var _showNewParm = function(type,codeColumn,codeName,obj){//type是1时是头部的新增，2是,3是配置
		$globalObj = $(obj);
		globalType = type;
		$("#newParm-div").find("select[name=zcfzType]").parents(".rows:eq(0)").hide();
		if(reportType == "3"){//现金流方向
			$("#newParm-div").find("select[name=cashDirection]").parents(".rows:eq(0)").show();
		}else if(reportType != "3"){
			$("#newParm-div").find("select[name=cashDirection]").parents(".rows:eq(0)").hide();
		}
		if(type == "1" || type =="2"){
			$("#newParm-dgcctag input").val("");
			$("#newParm-dgcctag input[name=reportType]").val(reportType);
			$("#newParm-dgcctag select[name=inputType]").val("1");
			$("#ys-div").hide();
			$("#ys-div tbody").empty();
			cusFinCommon.planInput(null);
		}
		if(type == "1"){
			$("#newParm-div").find("input[name=parentCodeName]").parents(".rows:eq(0)").show();
			$("#newParm-div").find("input[name=parentCodeName]").val(reportName);
			globalUrl =webPath+"/cusFinParm/insertAjax";
		}else if(type == "2"){
			$("#newParm-div").find("input[name=parentCodeName]").parents(".rows:eq(0)").show();
			$("#newParm-div").find("input[name=parentCodeName]").val(codeName);
			$("#newParm-div").find("input[name=parentCodeColumn]").val(codeColumn);
			globalUrl =webPath+"/cusFinParm/insertAjax";
		}else if(type == "3"){  //具体项配置
			$("#newParm-div").find("input[name=parentCodeName]").parents(".rows:eq(0)").show();
			$.ajax({
				url:webPath+"/cusFinParm/getByIdAjax?codeColumn="+codeColumn+"&reportType="+reportType,
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag=="success"){
						$("#newParm-dgcctag").empty();
						$("#newParm-dgcctag").html(data.formHtml);
						$("#newParm-div").find("select[name=zcfzType]").parents(".rows:eq(0)").hide();
						if(reportType == "3"){//现金流方向
							$("#newParm-div").find("select[name=cashDirection]").parents(".rows:eq(0)").show();
						}else if(reportType != "3"){
							$("#newParm-div").find("select[name=cashDirection]").parents(".rows:eq(0)").hide();
						}
						if(data.cusFinParm.inputType=="2"){//运算项
							$("#ys-div").show();
							$("#ys-div").find("tbody").empty();
							$.each(data.cusFinFormulaList,function(i,cusFinFormula){
								var htmlStr = '<tr> <td style="width: 50%;"><input value="'+cusFinFormula.formulaName+'" name="formulaName" onclick="cusFinCommon.selectParm(this);" style="width: 70%;"><input value="'+cusFinFormula.formulaColumn+'" name="formulaColumn" style="display: none;"></td>'
												+ '<td style="width: 30%;"><select  name="formulaType" style="width: 60%;">'
												if(cusFinFormula.formulaType==1){
													htmlStr = htmlStr+'<option value="1" selected="selected">+</option><option value="2">-</option></select></td>';
												}else{
													htmlStr = htmlStr+'<option value="1" >+</option><option value="2" selected="selected">-</option></select></td>';
												}
								htmlStr= htmlStr + '<td ><button type="button" name="addBtn" class="btn addRow btn-mini showBtn curSelectedBtn showBtna" onclick="cusFinCommon.planInput(this);"></button><button type="button" name="delBtn" class="btn delRow btn-mini showBtn curSelectedBtn showBtnb" onclick="cusFinCommon.planCancel(this);"></button></td>'
												+'</tr>';
								$("#ys-table tbody").append(htmlStr);
							});
						}else{
							$("#ys-div").hide();
							$("#ys-div").find("tbody").empty();
						}
						dialog({
							title:'报表项配置',
							id:"newParmDialog",
							backdropOpacity:0,
							height:400,
							content:$("#newParm-div")
						}).showModal();
					}else{
						alert("查询信息出错",0);
					}
				},error:function(){
					alert("查询信息出错",0);
				}
			});
			globalUrl =webPath+"/cusFinParm/updateAjax";
		}
		if(type =="1" || type=="2"){
			dialog({
				title:'报表项配置',
				id:"newParmDialog",
				backdropOpacity:0,
				height:400,
				content:$("#newParm-div")
			}).showModal();
		}
	};
	
	//保存
	var _ajaxInsertThis = function(obj){
		var dataParam = JSON.stringify($(obj).serializeArray());
		var dataParmList = [];
		var flag = true;
		if($("#newParm-div").find("select[name=inputType]").val() == "2"){
			$("#ys-table tbody tr").each(function(i,o){
				var $thisTr = $(this);
				if($thisTr.find("input[name=formulaColumn]").val() == ""){
					flag = false;
					return false;
				}
				var entity = {};
				entity.formulaColumn = $thisTr.find("input[name=formulaColumn]").val();
				entity.formulaName = $thisTr.find("input[name=formulaName]").val();
				entity.formulaType = $thisTr.find("select[name=formulaType]").val();
				entity.codeColumn = $("#newParm-div").find("input[name=parentCodecodeColumn]").val();
				dataParmList.push(entity);
			});
			if(!flag){
				alert("请配置完整计算公式",0);
				return false;
			}
		}
		$.ajax({
			url:globalUrl,
			data:{ajaxData:dataParam,ajaxDataList:JSON.stringify(dataParmList)},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					 dialog.get("newParmDialog").close(); 
					 var tdcss = "td-des";
					 if(data.cusFinParm.cnt!=null&&data.cusFinParm.cnt.length == 2){
						 tdcss = "td-des1";
					 }else if(data.cusFinParm.cnt!=null&&data.cusFinParm.cnt.length == 3){
						 tdcss = "td-des2";
					 }
					var htmlStr = '<td class="'+tdcss+'">'+data.cusFinParm.codeName+'</td>'
									+'<td class="td-caozuo"><span class="span-this" onclick="cusFinCashOrProParmList.showNewParm(\'2\',\''+data.cusFinParm.codeColumn+'\',\''+data.cusFinParm.codeName+'\',this);">新增</span>'
									+	'<span class="span-this" style="margin-left: 20px;" onclick="cusFinCashOrProParmList.showNewParm(\'3\',\''+data.cusFinParm.codeColumn+'\',\''+data.cusFinParm.codeName+'\',this);" >配置</span>'
									+'</td>';
					if(globalType=="1"){
						htmlStr = '<tr>' +htmlStr + '</tr>'; 
						$("#parmShow-div").find("tbody").append(htmlStr);
					}else if(globalType=="2"){
						htmlStr = '<tr>' +htmlStr + '</tr>'; 
						$globalObj.parents("tr:eq(0)").after(htmlStr);
					}else if(globalType=="3"){
						$globalObj.parents("tr:eq(0)").html(htmlStr);
					}
				}else if(data.flag == "error"){
					 alert(data.msg,0);
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	
	return{
		init:_init,
		showNewParm:_showNewParm,
		ajaxInsertThis:_ajaxInsertThis
	};
}(window,jQuery);