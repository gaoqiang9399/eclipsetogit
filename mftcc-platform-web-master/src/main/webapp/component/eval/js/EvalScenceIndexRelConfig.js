;
var editAppProA = "<a onclick='editAppProperty(this)' href='javascript:void(0)' title = '修改评级指标'><i class='i i-bianji2'></i></a>";
var EvalScenceIndexRelConfig = function(window,$){
	var _init=function(){
		_getAppPropertyData();
		_getEvalScenceIndexRel();
		navLine.createNavLine();
	};
	//获得评级指标数据源
	var _getAppPropertyData = function(){
		$.ajax({
			type:"post",
			data:{ajaxData:JSON.stringify({scenceNo:evalScenceNo})},//评级模型编号
			url:webPath+"/appProperty/getAppPropertyForList",
			async:false,
			success:function(data){
				if(data.flag == "success"){
					var indexDatas = {};
					$.each(data.appPropertyData, function(index,entity) {
						var propertyValueType = entity.propertyValueType;
						var vals = {"val":entity.propertyName};
						if(entity.propertyValueType=="02"){//数据字典项
							var data = {"type":entity.propertyValueType,"keyName":entity.propertyKeyName};
							vals["data"] = data;
						}
						indexDatas[entity.propertyNo] = vals;
					});
					AppPropertyDatas={
							indexDatas:indexDatas
					};
					
				}else if(data.flag == "error"){
					alert(data.msg,0);
				}
			},error:function(){
				
			}
		});
	};
	//获得评级模型与指标关系及展示模型的评分卡、指标信息
	var _getEvalScenceIndexRel = function(){
		$obj = $("#configDiv");
		$.ajax({
			type:"post",
			url:webPath+"/evalScenceConfig/getEvalScenceIndexRelListAjax?evalScenceNo="+evalScenceNo,
			async:false,
			success:function(data){
				if(data.flag=="success"){
					var saveData = {};
					if(data.listData == null){
						
					}else{
						$.each(data.listData, function(key,obj) {
							saveData[key] = "1";
						});
						_successToinitUpdate($obj,saveData,data.scenceNo,data.listData);
					}
				}else if(data.flag=="error"){
					alert(data.msg,0);
				}
			},error:function(){
				alert("查询数据链接出错",0);
			}
		});
	};
	var _successToinitUpdate = function($obj,saveData,scenceNo,listData){
		_initSettings($(".content .content_setting .settings"),entityData,saveData,scenceNo,listData);
		var $content_config = $obj.parents(".content").find(".row_content").find(".content_config");
		var $content_setting = $obj.parents(".content").find(".row_content").find(".content_setting");
		var $content_type  = $obj.parents(".content").find(".row_content").find(".content_type");
		var $selectBorder = $obj.parents(".content").find(".select-border");
		var selectBorderHeight = $selectBorder.height();
		if(selectBorderHeight!="68"&&selectBorderHeight!="70"){//高度70系统获取的就是68
			$selectBorder.animate({height:"70px"});
			var $tableContent = $(".table_content");
			$tableContent.animate({height:$tableContent.height()-22},function(){
				$(".table_content").mCustomScrollbar("update");
			});
		}
		_hiddenTd($content_config.find("table"),"evalScenceNo");
	};
	
	var _initSettings = function(obj,datas,filter,scenceNo,listData){
		var $obj = $(obj);
		var types = {};
		var $settingUl = $('<ul class="data-content"></ul>');
		var evalGradeCardList=listData["evalGradeCardList"];
		$.each(evalGradeCardList,function(i,obj){
			$.each(datas,function(entityKey,data){
				var cardTitle = obj.gradeCardName;
				var cardId = obj.gradeCardId;
				var gradeCardType="";
				var flag = true;
				types[entityKey]=cardTitle;
				var $li = $("<li id='"+cardId+"' style='margin-top: 20px;'></li>");
				var $lititle = $('<div class="title_btn"></div>');
				$lititle.append('<ol id='+entityKey+' class="li_title color_theme" style="font-size: 14px;margin-left:-8px">'+
						'<li class="active"><span class="config-item"><span name="title">'+cardTitle+'</span><i class="i i-bianji2 item-delete" onclick="MfEvalGradeCardComm.editGradeCard(\''+cardId+'\');"></i><i class="i i-lajitong item-delete" onclick="MfEvalGradeCardComm.deleteGradeCard(\''+cardId+'\');"></i></span></li></ol>');
				$lititle.append('<div class="li_btn colse">'+
						'<input type="button" value="添加分组" onclick="EvalScenceIndexRelConfig.addfirstLev(this,\''+getUrlSplit(datas[entityKey].insertUrl)+'\')"/>'+
				'</div>');
				$li.attr("data-name",entityKey);
				var $table = $('<table class="ls_list"><thead><tr></tr></thead><tbody></tbody></table>');
				var colKeyIndex = 0;
				$.each(data.data,function(colKey,detail){
					if(data.display.indexOf(colKey)==-1&&colKey!="ctrl_btn"){
						if(colKeyIndex>0){
							$table.find("thead tr").append('<th style="width:50%" name="'+colKey+'">'+detail+'</th>');
						}else{
							$table.find("thead tr").append('<th style="width:30%" name="'+colKey+'">'+detail+'</th>');
						}
						colKeyIndex++;
					}else if(colKey=="ctrl_btn"){
						$table.find("thead tr").append('<th style="width:20%" name="'+colKey+'">'+detail+'</th>');
					}
				});
				if(listData!==undefined){
					var  thLength = $table.find("thead tr th").length;
					$.each(listData[entityKey+cardId], function(index,entityObj) {
						initSeting.initGradeIndexRel(datas,$table,entityObj,thLength,entityKey,scenceNo);
					});
				}
				$li.append($lititle);
				$li.append('<div class="li_content"></div>');
				$li.find(".li_content").append($table);
				$settingUl.append($li);
			});
		});
		$obj.html($settingUl);
		$obj.data("scenceNo",scenceNo);
		_initTypes($obj,types);
	};
	/**
	 * 业务解释：评级评分指标项关联
	 */
	var entityData = {
		"evalScenceIndexRel":{
				title:"评分指标",
				data:{
					"scenceNo":"评级场景编号",
					"indexNo":"指标编号",
					"indexValue":"对应指标基础表中property_no",
					"indexName":"指标名称",
					"indexDesc":"指标描述",
					"level":"指标级别",
					"upIndexNo":"上级指标级别",
					"useFlag":"是否可用",
					"ctrl_btn":"操作按钮"
				},
				display:"scenceNo,indexNo,indexValue,level,upIndexNo,useFlag",//不显示
				insertUrl:webPath+"/mfEvalScenceIndexRel/insertAjax;indexNo-indexNo;scenceNo-scenceNo",
				updateUrl:webPath+"/mfEvalScenceIndexRel/updateAjax;indexNo-indexNo;scenceNo-scenceNo",
				deleteUrl:webPath+"/mfEvalScenceIndexRel/deleteAjax;relId-relId;indexNo-indexNo;scenceNo-scenceNo"
			},
	};
	
	
	var initSeting = {
			initGradeIndexRel:function(datas,$table,entityObj,thLength,entityKey,scenceNo){//初始化定性评分
				var $tr = $("<tr></tr>");
				$tr.data("entityObj",entityObj);
				var level = entityObj.level;
				var entityIndex = 0;
				$table.find("thead tr th").each(function(index) {
					var key = $(this).attr("name");
					var val = entityObj[key];
                    var $td;
					if(level==1){
						if(key=="indexName"){
							val = entityObj["indexName"];
							$tr.append('<td class="font_weight" name="indexName">'+val+'</td>');
						}else if(key=="ctrl_btn"){
							$td = $('<td class="ctrl_btn"></td>');
							$td.append('<a onclick="EvalScenceIndexRelConfig.addsecondtarget(this,\''+datas[entityKey].insertUrl+'\')" href="javascript:void(0)">添加</a>');
							$td.append('<a onclick="EvalScenceIndexRelConfig.savefirst(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">保存</a> ');
							$td.append('<a onclick="EvalScenceIndexRelConfig.updatefirsttarget(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">修改</a>');
							$td.append('<a onclick="EvalScenceIndexRelConfig.delfirsttarget(this,\''+datas[entityKey].deleteUrl+'\')" href="javascript:void(0)">删除</a>');
							$tr.append($td);
						}else{
							$tr.append('<tdname="'+key+'"></td>');
						}
					}else if(level==2){
						if(key=="indexName"){
							$tr.append('<td class="" name="'+key+'">--'+val+'</td>');
						}else if(key=="ctrl_btn"){
							$td = $('<td class="ctrl_btn"></td>');
							$td.append('<a onclick="EvalScenceIndexRelConfig.updatesecondtarget(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">修改</a>');
							$td.append('<a onclick="EvalScenceIndexRelConfig.saveEvalIndecRel(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">保存</a> ');
							$td.append('<a onclick="EvalScenceIndexRelConfig.delsecondtarget(this,\''+datas[entityKey].deleteUrl+'\')" href="javascript:void(0)">删除</a>');
							$tr.append($td);
						}else if(key=="indexDesc"){
							$td = $('<td><span name="'+key+'">'+val+'</span><input name="'+key+'" style="width: 80%; display: none;" class="center" type="text" value="'+val+'"/></td>');
							$tr.append($td);
						}
						return true;
					}else if(level==3){
						$td = $("<td></td>");
						if(key=="javaItem"){
							val = entityObj["indexName"];
				    		$td = $('<td name="indexName">----'+val+'</td>');
				    	}else if(key=="ctrl_btn"){
							$td = $('<td class="ctrl_btn"></td>');
							$td.append('<a onclick="updatethirdtarget(this,\''+datas[entityKey].insertUrl+'\')" href="javascript:void(0)">修改</a>');
							$td.append('<a onclick="savethird(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">保存</a> ');
							$td.append('<a onclick="delthirdtarget(this,\''+datas[entityKey].deleteUrl+'\')" href="javascript:void(0)">删除</a>');
						}else{
							$td = $('<td><input name="'+key+'" class="center" type="text"/></td>');
							if(key=="stdCore"){
								$td.find("input[type='text']").val(changeDoubleForStr(val)).hide();
							}else{
								$td.find("input[type='text']").val(val).hide();
							}
							$td.append("<span name='"+key+"'>"+changeDoubleForStr(val)+"</span>");
							//$td.append('<input type="button" onclick="cancelOperation(this);" value="取消" style="display:none">');
							$td.find("input[type='text']").attr("size","10");
						}
						$tr.append($td);
						
					}
					entityIndex++;
				});
				$tr.data("relId",entityObj.relId);
				$tr.data("indexNo",entityObj.indexNo);
				$tr.data("indexValue",entityObj.indexValue);
				$tr.data("level",entityObj.level);
				$tr.data("upIndexNo",entityObj.upIndexNo);
				if(level==1){
					$table.find("tbody").append($tr);
				}else{//level=2  或 level=3
					var $targetTr = $table.find("tbody").find("tr").eq(0);
					var existeParentFlag = false;
					$table.find("tbody tr").each(function(){
						if($(this).data("indexNo")==entityObj.upIndexNo){
							$targetTr = $(this);
							existeParentFlag = true;
						}else if($(this).data("upIndexNo")==entityObj.upIndexNo){
							$targetTr = $(this);
						}
					});
					if(existeParentFlag){
						$targetTr.after($tr);
					}
				}
			},
		};
	
	//添加分组
	var _addfirstLev = function(obj,url){
		var $obj = $(obj);
		var $table =  $obj.parents("li").find(".ls_list");
		var ctrlFalg = $table.find("tr[ctrl=insert]");
		var entityKey = $obj.parents("li").attr("data-name");
		if(ctrlFalg.length==0){
			$obj.parents(".li_btn").removeClass("colse");
			var thlength = $table.find("thead").find("th").length;
			var scenceType = $obj.parents("li").attr("data-name");
			$table = $obj.parents("li").find(".li_content").find(".ls_list");
			var $tr = $('<tr ctrl="insert"></tr>');
			$tr.data("url",url);
			$tr.data("level","1");
			$table.find("thead th").each(function(index){
				var name = $(this).attr("name");
				if(name == "ctrl_btn"){   
					var btn = '<td style="text-align: center;">';
					btn+='<a class="abatch" onclick="EvalScenceIndexRelConfig.addsecondtarget(this,\''+entityData[scenceType].insertUrl+'\');return false;" href="javascript:void(0);">添加</a>';
					btn+='<a class="abatch" onclick="EvalScenceIndexRelConfig.savefirst(this,\''+entityData[scenceType].insertUrl+'\');return false;" href="javascript:void(0);">保存</a>'+
						  '<a class="abatch" onclick="EvalScenceIndexRelConfig.updatefirsttarget(this,\'\');return false;" href="javascript:void(0);">修改</a>'+
						  '<a class="abatch" onclick="EvalScenceIndexRelConfig.delfirsttarget(this,\''+entityData[scenceType].deleteUrl+'\');return false;" href="javascript:void(0);">删除</a>'+
						  '</td>';
					$tr.append(btn);
				}else{
					if(index==0){
						$tr.append('<td colspan="'+(thlength-1)+'"><input type="text" name="indexName"/></td>');
					}
				}
			});
			$table.find("tbody").prepend($tr);
			$obj.parents("li").find(".li_content").slideDown();
		}else{
			alert("已有条新增未保存",0);
		}
	};
	//保存分组
	var _savefirst = function(obj,url){
		var $obj = $(obj);
		var $ThisTr = $obj.parents("tr");
		var saveFlag = $ThisTr.attr("ctrl");
		var scenceType = $obj.parents("li").attr("data-name");
		if(saveFlag != undefined && saveFlag != ""){
			/*if(saveFlag=="insert"){
				url = $ThisTr.data("url");
			}*/
			var scenceNo = $obj.parents(".settings").data("scenceNo");
			var relId = $obj.parents("tr").data("relId");
			var indexNo = $obj.parents("tr").data("indexNo");
			var level = $obj.parents("tr").data("level");
			var gradeCardId = $obj.parents("li").attr("id");
			var dataParm = {relId:relId,scenceNo:scenceNo,level:level,indexNo:indexNo,gradeCardId:gradeCardId};
			var postFlag = true;
			$ThisTr.find("input[type=text]").each(function(){
				var name = $(this).attr("name");
				dataParm[name] = $(this).val();
				if(name =="indexName"&&($(this).val()==null||$(this).val()=="")){
					postFlag = false;
				}
			});
			$ThisTr.find("select").each(function(){
				var name = $(this).attr("name");
				dataParm[name] = $(this).val();
			});
			if(postFlag){
				//var indexNo;
				$.ajax({
					type:"post",
					data:{ajaxData:JSON.stringify(dataParm)},
					url:getUrlSplit(url),
					async:false,
					success:function(data){
						if(data.flag=="success"){
							$ThisTr.data("entityData",data.entityData);
							$ThisTr.data("indexNo",data.entityData.indexNo);
							$ThisTr.data("level",data.entityData.level);
							$ThisTr.data("upIndexNo",data.entityData.indexNo);
							if(data.entityData.level=="1"){
								$ThisTr.find("input[name='indexName']").parent("td").addClass("font_weight");
							}
							$ThisTr.find("input[name='indexName']").parent("td").html(data.entityData.indexName);
							$ThisTr.removeAttr("ctrl");
							alert(data.msg,1);
							console.log($ThisTr.data("entityData"));
						}else if(data.flag=="error"){
							alert(data.msg,0);
						}
					},error:function(){
						alert(  top.getMessage("FAILED_SAVE"),0);
					}
				});
			}else{
				alert(top.getMessage("NOT_FORM_EMPTY", "指标名称"),0);
			}
		}else{
			alert("保存失败,新增或保存不明确",0);
		}
	};
	//分组下添加指标
	var _addsecondtarget = function(obj,url){
		var $obj = $(obj);
		var $table =  $obj.parents("li").find(".ls_list");
		var ctrlFalg = $table.find("tr[ctrl=insert]");
		if(ctrlFalg.length==0){
			var scenceNo = $obj.parents(".settings").data("scenceNo");
			var upIndexNo = $obj.parents("tr").data("indexNo");
			var scenceType = $obj.parents("li").attr("data-name");
			var level = 2;
			var thlength = $table.find("thead").find("th").length;
			$table = $obj.parents("li").find(".li_content").find(".ls_list");
			$tr = $('<tr ctrl="insert"></tr>');
			$tr.data("url",url);
			$tr.data("upIndexNo",upIndexNo);
			$tr.data("level",level);
			$table.find("thead th").each(function(index){
				var name = $(this).attr("name");
                var $td;
				if(name == "ctrl_btn"){   
					$td = $('<td class="ctrl_btn"></td>');
						$td.append('<a onclick="EvalScenceIndexRelConfig.updatesecondtarget(this,\''+entityData[scenceType].insertUrl+'\')" href="javascript:void(0)">修改</a>');
						$td.append('<a onclick="EvalScenceIndexRelConfig.saveEvalIndecRel(this,\''+entityData[scenceType].insertUrl+'\')" href="javascript:void(0)">保存</a> ');
						$td.append('<a onclick="EvalScenceIndexRelConfig.delsecondtarget(this,\''+entityData[scenceType].deleteUrl+'\')" href="javascript:void(0)">删除</a>');
						$tr.append($td);
				}else{
					$td = $('<td></td>');
					if(scenceType=="evalScenceIndexRel"&&name=="indexName"){
						var AppPropertyData = AppPropertyDatas.indexDatas;
						/*$table.find("tbody").find("tr").each(function(){
							var entityObj = $(this).data("entityObj");
							if(entityObj!==undefined&&entityObj.finCode!==undefined&&entityObj.finCode!=""){
								delete AppPropertyData[entityObj.finCode];
							}
						});*/
						$td.append(getSelectTargHtml(AppPropertyDatas,name,true));
						$td.append(editAppProA);
					}else{
						$td.append('<input type="text" name="'+name+'" style="width:80%"/></td><span name="'+name+'" style="display:none"></span>');
					}
					if(index!=0&&name != "ctrl_btn"){
						$td.find("input[type=text]").addClass("center");
						$td.find("input[type=text]").attr("size","10");
					}
					$tr.append($td);
				}
			});
			var $nextObj = $obj.parents("tr");
			//寻找二级节点位置
			$nextObj.after($tr);
			$obj.parents("li").find(".li_content").slideDown();
		}else{
			alert("已有条新增未保存",0);
		}
	};
	//修改分组
	var _updatefirsttarget = function(obj,url){
		var $obj = $(obj);
		var saveFlag = $obj.parents("tr").attr("ctrl");
		var scenceType = $obj.parents("li").attr("data-name");
		if(saveFlag!==undefined&&saveFlag!=""){
			if(saveFlag=="insert"){
				alert("为新增状态还未保存",0);
			}else if(saveFlag=="update"){
				alert("已为修改状态",0);
			}
		}else{
			if(scenceType=="evalScenceDxRel"){
				$obj.parents("tr").find("span").hide();
				$obj.parents("tr").find("td").eq(0).find("select,a,input").show();
			}else{
				var $td = $obj.parents("tr").find("td").eq(0);
				var name = $obj.parents("table").find("thead tr th").eq(0).attr("name");
				var text = $td.text();
				$td.data("value",text);
				var val = text.replace("--","");
				$td.html('<input type="text" name="'+name+'" value="'+val+'"/><input type="button" onclick="EvalScenceIndexRelConfig.cancelOperation(this);" value="取消"/>');
				$obj.parents("tr").attr("ctrl","update");
				$obj.parents("tr").data("url",url);
			}
		}
		
	};
	//删除分组
	var _delfirsttarget = function(obj,url){
		var $obj = $(obj);
		alert("是否确定删除",2,function(){
			var $TrObj = $obj.parents("tr");
			var indexNo = $TrObj.data("indexNo");
			var relId = $TrObj.data("relId");
			if(indexNo!=undefined){
				var scenceNo = $obj.parents(".settings").data("scenceNo");
				url = gerUrlForData(url,{"relId":relId,"indexNo":indexNo,"scenceNo":scenceNo});
				$.ajax({
					type:"get",
					url:url,
					async:false,
					success:function(data){
						if(data.flag == "success"){
							alert(data.msg,1);
							$obj.parents("tr").remove();
						}else if(data.flag == "error"){
							alert(data.msg,0);
						}
					},error:function(){
						alert(top.getMessage("FAILED_DELETE"),0);
					}
				});
			}else{
				alert(top.getMessage("SUCCEED_DELETE"),1);
				$obj.parents("tr").remove();
			}
		});
	};
	
	/**
	 * 保存评分卡评级指标关系 
	 * @param obj
	 * @param url
	 * @returns
	 */
	var _saveEvalIndecRel = function(obj,url){
		var $obj = $(obj);
		var $tr = $obj.parents("tr");
		var saveFlag = $tr.attr("ctrl");
		var scenceNo = $obj.parents(".settings").data("scenceNo");
		var relId = $obj.parents("tr").data("relId");
		var upIndexNo = $obj.parents("tr").data("upIndexNo");
		var indexNo = $obj.parents("tr").data("indexNo");
		var level = $obj.parents("tr").data("level");
		var gradeCardId = $obj.parents("li").attr("id");
		var dataParm = {relId:relId,scenceNo:scenceNo,level:level,indexNo:indexNo,upIndexNo:upIndexNo,gradeCardId:gradeCardId};
		var postFlag = true;
		$tr.find("input[type=text]").each(function(){
			var name = $(this).attr("name");
			dataParm[name] = $(this).val();
			$tr.find("span[name="+name+"]").html($(this).val());
			if(name =="indexName"&&($(this).val()==null||$(this).val()=="")){
				postFlag = false;
			}
		});
		$tr.find("select").each(function(){
			var name = $(this).attr("name");
			if(name =="indexName"){
				dataParm[name] = $(this).find("option:selected").text();
				dataParm["indexValue"] = $(this).find("option:selected").val();;
			}else{
				dataParm[name] = $(this).val();
			}
		});
		var scenceType = $obj.parents("li").attr("data-name");
		if(postFlag){
			//var indexNo;
			$.ajax({
				type:"post",
				data:{ajaxData:JSON.stringify(dataParm)},
				url:getUrlSplit(url),//getUrlSplit(url)
				async:false,
				success:function(data){
					if(data.flag=="success"){
						$tr.data("indexNo",data.entityData.indexNo);
						$tr.data("level",data.entityData.level);
						$tr.data("upIndexNo",data.entityData.upIndexNo);
						$tr.data("entityObj",data.entityData);
						$tr.find("input[type=text]").each(function(){
							var name = $(this).attr("name");
							if(name=="stdCore"){
								$(this).val(changeDoubleForStr(data.entityData[name]));
							}else{
								$(this).val(data.entityData[name]);
							}
						});
						$tr.find("span").each(function(){
							var name = $(this).attr("name");
							if(scenceType=="evalScenceIndexRel"){
								if(name=="indexDesc"){
									$(this).html(data.entityData[name]);
								}else{
									$(this).html(changeDoubleForStr(data.entityData[name]));
								}
							}
						});
						
						$tr.find("input[name='indexName']").parent("td").html("--"+data.entityData.indexName);
						$tr.find("select[name='indexName']").parent("td").html("--"+data.entityData.indexName);
						$tr.each(function(index,obj){
							$(obj).find("span").show();
							$(obj).find("input").hide();
							$(obj).find("select").hide();
						});
						$tr.find("td").eq(0).find("a").hide();
						$tr.removeAttr("ctrl");
						alert(top.getMessage("SUCCEED_SAVE"),1);
					}else if(data.flag=="error"){
						alert(data.msg,0);
					}
				},error:function(){
					alert(  top.getMessage("FAILED_SAVE"),0);
				}
			});
		}else{
			alert(top.getMessage("NOT_FORM_EMPTY", "指标名称"),0);
		}
	};
	//修改分组下评级指标
	var _updatesecondtarget = function(obj,url){
		var $obj = $(obj);
		var level = $obj.parents("tr").data("level");
		var saveFlag = $obj.parents("tr").attr("ctrl");
		if(saveFlag!==undefined&&saveFlag!=""){
			if(saveFlag=="insert"){
				alert("为新增状态还未保存",0);
			}else if(saveFlag=="update"){
				alert("已为修改状态",0);
			}
		}else{
			var $td = $obj.parents("tr").find("td").eq(0);
			var name = $obj.parents("table").find("thead tr th").eq(0).attr("name");
			var scenceType = $obj.parents("li").attr("data-name");
			var scenceNo = $obj.parents(".settings").data("scenceNo");
			var text = $td.text();
			$td.data("value",text);
			var val;
			if(scenceType=="evalScenceIndexRel"){
				var $thisTrObj = $obj.parents("tr");
				$.each($thisTrObj.find("td"),function(index,obj){
					$(obj).find("span").hide();
					$(obj).find("input").show();
				});
				var thisFincode = $thisTrObj.data("entityObj").indexNo;
				var indexValue = $thisTrObj.data("entityObj").indexValue;
				$obj.parents("table tbody").find("tr").each(function(){
					var entityObj = $(this).data("entityObj");
					if(entityObj.indexNo!==undefined&&entityObj.indexNo!=""){
						if(thisFincode!==entityObj.indexNo){
							delete AppPropertyDatas[entityObj.indexNo];
						}
					}
				});
				$td.html(getSelectTargHtml(AppPropertyDatas,name,true,indexValue));
				$td.append(editAppProA);
				$td.append('<input type="button" onclick="EvalScenceIndexRelConfig.cancelOperation(this);" value="取消"/>');
			}
			$obj.parents("tr").attr("ctrl","update");
			$obj.parents("tr").data("url",url);
		}
	};
	//删除分组下评级指标
	var _delsecondtarget = function(obj,url){
		var $obj = $(obj);
		var $TrObj = $obj.parents("tr");
		var relId = $TrObj.data("relId");
		var indexNo = $TrObj.data("indexNo");
		var scenceNo = $obj.parents(".settings").data("scenceNo");
		url = gerUrlForData(url,{"relId":relId,"indexNo":indexNo,"scenceNo":scenceNo});
		var $ThisTr = $obj.parents("tr");
		var saveFlag = $ThisTr.attr("ctrl");
		alert("是否确定删除",2,function(){
			if(saveFlag!==undefined&&saveFlag=="insert"){
				$obj.parents("tr").remove();
				alert(top.getMessage("SUCCEED_DELETE"),1);
			}else{
				$.ajax({
					type:"get",
					url:url,
					async:false,
					success:function(data){
						if(data.flag == "success"){
							$.each($obj.parents("tr").nextAll(),function(i,obj){
								if(indexNo==$(obj).data("upIndexNo")){
									$(obj).remove();
								}
							})
							$obj.parents("tr").remove();
							alert(data.msg,1);
						}else if(data.flag == "error"){
							alert("data.msg",0);
						}
					},error:function(){
						alert(top.getMessage("FAILED_DELETE"),0);
					}
				});
			}
		});
	};
	//隐藏列
	var _hiddenTd = function(obj,data){
		var $obj = $(obj);
		var array = data.split(",");
		$.each(array, function(index,val) {
			var colIndex = $obj.find("thead").find("th[name="+val+"]").index();
			$obj.find("thead").find("th").each(function(tdIndex,value){
				if($(this).attr("name")==val){
					$(this).show();
					$obj.find("tbody").find("tr").each(function(){
						$(this).find("td").eq(tdIndex).show();
					});
				}else{
					$(this).hide();
					$obj.find("tbody").find("tr").each(function(){
						$(this).find("td").eq(tdIndex).hide();
					});
				}
			});
		});
	};
	//初始化评级类型
	var _initTypes=function(obj,array){
		var $obj = $(obj);
		var $types = $obj.parents(".content").find(".content_type").find(".types");
		var $table = $('<table class="ls_list"><tbody></tbody></table>');
		var index = 0;
		$.each(array, function(key,val) {
			var $tr = $('<tr></tr>');
			if(index==0){
				$tr.addClass("ckecked");
			}
			$tr.append('<td onclick="showType(this,\''+key+'\')">'+val+'</td>"');
			$table.find("tbody").append($tr);
			index++;
		});	
		$types.html($table);
	};
	/**
	 * 拆分获取url(不带参数)
	 * @param {Object} url
	 */
	var getUrlSplit = function(url){
		return url.split(";")[0];
	};
	/**
	 * 根据数据创建select 标签
	 * @param {Object} data
	 * @param {Object} name
	 * @param {Object} name
	 */
	var getSelectTargHtml = function(data,name,flag,value){
		var $select  = $('<select name="'+name+'" style="width:75%"></select>');
		if(flag!==undefined&&flag==true){
			$select.attr("onchange","selectOnchange(this);");
			$select.append('<option></option>');
		}else if(flag=="this"){
			$select.attr("onchange","selectOnchangeThis(this);");
			$select.append('<option></option>');
		}
		$.each(data.indexDatas, function(val,cdata) {
			var text = cdata.val;
			var type = "";
			var $option = $('<option type ="'+type+'" value="'+val+'">'+text+'</option>');
			if(cdata["data"]!==undefined){
				//向option存放隐藏数据
				$option.data("data",cdata["data"]);
			}
			if(value!==undefined&&value!=""&&val==value){
				$option.attr("selected",true);
			}
			$select.append($option);
		});
		return $select;
	};
	/**
	 * 拆分获取url(带参数)
	 * @param {Object} url
	 * @param {Object} data
	 */
	var gerUrlForData = function(url,data){
		var array = url.split(";");
		url = array[0];
		$.each(array, function(index,parms) {
			if(index>0){
				var parm = parms.split("-")[0];
				var parmVal = parms.split("-")[1];
				if(url.indexOf("?")==-1){
					url+="?";
				}else{ 
					url+="&";
				}
				url+=parm+"="+data[parmVal];
			}
		});
		return url;
	};
	//取消
	var _cancelOperation = function(obj){
		var $obj = $(obj);
		$obj.parents("tr").removeAttr("ctrl");
		$obj.parents("tr").data("url","");
		var $td = $obj.parents("tr").find("td").eq(0);
		$.each($obj.parents("tr").find("td"),function(index,obj){
			$(obj).find("input,select").hide();
			$(obj).find("span").show();
		});
		$td.find("a").hide();
		var scenceType = $obj.parents("li").attr("data-name");
		var level = $obj.parents("tr").data("level");
		if(scenceType=="evalScenceDlRel"||scenceType=="evalScenceRestrictRel"||scenceType=="evalScenceDxRel"||scenceType=="evalScenceAdjRel"){
			if(level=="1"){
				$td.html($td.data("value"));
			}
		}else{
			$td.html($td.data("value"));
		}
	}
	return{
		init:_init,
		addfirstLev:_addfirstLev,
		addsecondtarget:_addsecondtarget,
		savefirst:_savefirst,
		updatefirsttarget:_updatefirsttarget,
		delfirsttarget:_delfirsttarget,
		updatesecondtarget:_updatesecondtarget,
		saveEvalIndecRel:_saveEvalIndecRel,
		delsecondtarget:_delsecondtarget,
		getAppPropertyData:_getAppPropertyData,
		cancelOperation:_cancelOperation
	};
}(window,jQuery);