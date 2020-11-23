var $properties = {};
var $count = null;
var $stencilsData = null;
var $properyPackagesData = null;
var $processInfoData = null;
var $lineInfoData = null;
var stencilset = null;

$(document).ready(function(){
//	var modelId = window.location.search.substring(4);//获取当前地址栏流程模板id
	var modelId = GetQueryString("modelId");
	var optState = GetQueryString("optState");
	var deployId = GetQueryString("deployId");
	$.getJSON("./stencilsetV2.json", function(data){ 
		$processInfoData = data.processInfo;
		$stencilsData = data.stencils;
		$properyPackagesData = data.properyPackages;
		$lineInfoData = data.lines;
		var $property = $('#property');
		$property.append('<div style ="margin-bottom: 1px;font: bold 11px/15px tahoma,arial,verdana,sans-serif;background:url(./ztree/img/white-top-bottom.gif);color:white;height:25px;line-height:25px" id="title" align="left"></div>');
		$property.append('<table cellspacing="0.5" id="propertyTable" class="propertyTable" ><thead style="margin-bottom: 1px;background:url(./ztree/img/grid3-hrow.gif);height:25px;font: bold 11px/15px tahoma,arial,verdana,sans-serif;color:white;" align="left"><th style="line-height:25px;border-right: 1px solid #d0d0d0;">&nbsp属性名</th><th style="line-height:25px;border-right: 1px solid #d0d0d0;">&nbsp属性值</th></thead><tbody id = "tbody"></tbody></table>');
		$('#propertyTable').hide();
		$('#title').hide();
		//编辑修改
		 if(modelId!=null&&optState!=null){
			 jQuery.ajax( {
				type : "POST",
				cache:false,
				url : "../process/getModelJsonData.do",
				data :{modelId:modelId,optState:optState,deployId:deployId},
				success : function(data) {
					data = eval("("+data+")");
					golFlow.loadData(data);
				}
			});
		 }else{
			 processProperties(golFlow.$process);
		 }
	}); 
});
//流程属性信息
function processProperties($process){
	golFlow.blurItem();
	$('#tbody').html("");
	$count=0;
	$('#title').html('&nbsp属性:工作流');
	var flag=false;
//	alert("$process.properties:"+$process.properties)
	if($process.properties==null){
		$process.title="模板设计器";
		$process['properties']=[];
		flag=true;
	}
    var i;
    var input,data;
//	alert('$processInfoData:'+$processInfoData)
	for(i = 0 ; i <$processInfoData.length;i++){
		if($processInfoData[i].visibility){
			$count +=1;
			$('#tbody').append('<tr name = "'+$processInfoData[i].title+'" style="font: 11px/15px tahoma,arial,verdana,sans-serif;" title="'+$processInfoData[i].description+'"><td style="border-right: 1px solid #F1F1F1;line-height:20px;width:40%;">&nbsp'+$processInfoData[i].title+'</td></tr>');
			if($count%2==1)
				$('#tbody').find('tr[name='+$processInfoData[i].title+']').css('background-color','#E1E1E1');
			
			var td = $('<td  id="'+$processInfoData[i].id+'" name = '+$processInfoData[i].title+' class="td" style="position:relative;width:60%"></td>');
			if($processInfoData[i].type=="String"){
				td.append('<input style="background-color:transparent;outline:0;border:0;height:20px" type="text" />');
				if($processInfoData[i].id=="Wf_name"){
					$processInfoData[i].value=golFlow.getTitle();
					td.find('input').val(golFlow.getTitle());
				}
			}else if($processInfoData[i].type=="Text"){
				td.append('<input style="background-color:transparent;outline:0;border:0;height:20px" type="text" />');
				input = td.find('input');
				data = $processInfoData[i];
				input.bind('click',function(){
					var title = $(this).parent().attr('name');
					initComplexDiv($process,this,title,data);
				});
			}else if($processInfoData[i].type=="Choice"){
				td.append('<select style="background-color:transparent;outline:0;border:0;height:20px;width:100%"><option value="" select="selected">&nbsp</option></select>');
				if($processInfoData[i].id=="rollbacknode"){//默认回退节点
					rollBackNode(td.find('select'));
				}else{
					var select = td.find('select');
					if(typeof($processInfoData[i].items)!='undefined')
						if($processInfoData[i].items.length!=0){
							for(var j =0;j<$processInfoData[i].items.length;j++){
								var temp = $("<option></option>");
								temp.html($processInfoData[i].items[j].title);
								temp.attr("value",$processInfoData[i].items[j].id);
								select.append(temp);
							}
						}
				}
			}else if($processInfoData[i].type=="Complex"){
				td.append('<input style="background-color:transparent;outline:0;border:0;height:20px" type="text" />');
				input = td.find('input');
				data = $processInfoData[i];
				input.bind('click',{data:data},function(evt){
					var title = $(this).parent().attr('name');
					initComplexDiv($process,this,title,evt.data.data);
				});
			}
			$('#tbody').find('tr[name='+$processInfoData[i].title+']').append(td);
			if(flag){
				initProperties($process,$processInfoData[i]);
			}
		}
	}
	$('#title').show();
	$('#propertyTable').show();
	if(!flag){
		var properties = $process.properties;
		for(i=0;i<properties.length;i++){
			if(properties[i].type=="Choice"){
				$('#'+properties[i].id+" select").val(properties[i].value);
			}else{
				$('#'+properties[i].id+" input").val(properties[i].value);
			}
		}
	}
	
	$('.td input').bind('change',function(){
		var pid = $(this).parent().attr('id');
			for(var i=0;i<$process.properties.length;i++){
				if(pid=="Wf_name"){
					golFlow.setTitle($(this).val());
					$processInfoData[i].value=$(this).val();
				}
				if($process.properties[i].id==pid){
					$process.properties[i].value = $(this).val();
					break;
				}
			}
	});
	$('.td select').bind('change',function(){
		var pid = $(this).parent().attr('id');
		var data = $(this).children('option:selected').val();
		for(var i=0;i<$process.properties.length;i++){
			if($process.properties[i].id==pid){
				$process.properties[i].value = data;
				break;
			}
		}
	});
}
//节点属性信息
function nodeProperties(This){
	var type = This.className;
	var nodeJson = golFlow.getNodeJson(This.id);
	var flag = false;
//	alert("nodeJson.properties:"+nodeJson.properties)
	if(nodeJson.properties==null){
		nodeJson['properties']=[];
		flag=true;
	}
	stencilset = This;
	$('#tbody').html("");
	$count=0;
    var i;
	for(i = 0 ; i <$stencilsData.length;i++){
		var Type = $stencilsData[i].type;
		if(type.indexOf(Type)!=-1){
			 $('#title').html('&nbsp属性:'+$stencilsData[i].title);
			var ProperPackages = $stencilsData[i].properPackages;
			for(var j =0;j<ProperPackages.length;j++){
				var packageName = ProperPackages[j];
				choicePropertiesUI(packageName,nodeJson,flag);
			}
			break;
		}
	}
	if(!flag){
		var properties = nodeJson.properties;
		for(i=0;i<properties.length;i++){
			if(properties[i].type=="Choice"){
				$('#'+properties[i].id+" select").val(properties[i].value);
			}else{
//				if(properties[i].id=="checkfunlst"){
//					alert(properties[i].value)
//				}else{
					$('#'+properties[i].id+" input").val(properties[i].value);
//				}
			}
		}
	}
	$('.td input').bind('change',function(){
		var id = $(this).parent().attr('id');
			for(var i=0;i<nodeJson.properties.length;i++){
				if(id=="description"){
					golFlow.setName(stencilset.id,$(this).val(),"node");
				}
//				else if(id=="overrideid"){
//					golFlow.transNewId(stencilset.id,$(this).val(),"node");
//				}
				if(nodeJson.properties[i].id==id){
					nodeJson.properties[i].value = $(this).val();
					break;
				}
			}
	});
	$('.td select').bind('change',function(){
		var id = $(this).parent().attr('id');
		var data = $(this).children('option:selected').val();
		if("appType"==id){
			if(data=="role"){

			}else{
				
			}
		}else{
			for(var i=0;i<nodeJson.properties.length;i++){
				if(nodeJson.properties[i].id==id){
					nodeJson.properties[i].value = data;
					break;
				}
			}
		}
	});
}
//连接属性定义
function lineProperties($lineData,This){
	var id = This.id;
	$lineData = $lineData[id];
	$('#tbody').html("");
	$count=0;
	$('#title').html('&nbsp属性:'+$lineInfoData.title);
	var flag = false;
//	alert("$lineData.properties:"+$lineData.properties)
	if($lineData.properties==null){
		$lineData['properties']=[];
		flag=true;
	}
    var i;
	for(i=0;i<$lineInfoData.properPackages.length;i++){
		var properydata = $properyPackagesData[$lineInfoData.properPackages[i]];
		for(var j = 0 ; j<properydata.length;j++){
			if(properydata[j].visibility){
				$count +=1;
				$('#tbody').append('<tr name = "'+properydata[j].title+'" style="font: 11px/15px tahoma,arial,verdana,sans-serif;" title="'+properydata[j].description+'"><td style="border-right: 1px solid #F1F1F1;line-height:20px;width:40%;">&nbsp'+properydata[j].title+'</td></tr>');
				if($count%2==1)
					$('#tbody').find('tr[name='+properydata[j].title+']').css('background-color','#E1E1E1');
				
				var td = $('<td  id="'+properydata[j].id+'" name = '+properydata[j].title+' class="td" style="position:relative;width:60%"></td>');
                var input,data;
				if(properydata[j].type=="String"){
					td.append('<input style="background-color:transparent;outline:0;border:0;height:20px" type="text" />');
					if(properydata[j].id=="overrideid"){
						properydata[j].value=id;
						td.find('input').val(id);
					}else if(properydata[j].id=="name"){
						properydata[j].value= $lineData.name;
						td.find('input').val($lineData.name);
					}
				}else if(properydata[j].type=="Text"){
					td.append('<input style="background-color:transparent;outline:0;border:0;height:20px" type="text" />');
					input = td.find('input');
					data = properydata[j];
					input.bind('click',function(){
						var title = $(this).parent().attr('name');
						initComplexDiv($lineData,this,title,data);
					});
				}else if(properydata[j].type=="Choice"){
					td.append('<select style="background-color:transparent;outline:0;border:0;height:20px;width:100%"><option value="" select="selected">&nbsp</option></select>');
					var select = td.find('select');
					if(typeof(properydata[j].items)!='undefined')
						if(properydata[j].items.length!=0){
							for(var z =0;z<properydata[j].items.length;z++){
								var temp = $("<option></option>");
								temp.html(properydata[j].items[z].title);
								temp.attr("value",properydata[j].items[z].id);
								select.append(temp);
							}
						}
				}else if(properydata[j].type=="Complex"){
					td.append('<input style="background-color:transparent;outline:0;border:0;height:20px" type="text" />');
					input = td.find('input');
					data = properydata[j];
					input.bind('click',function(){
						var title = $(this).parent().attr('name');
						initComplexDiv($lineData,this,title,data);
					});
				}
				$('#tbody').find('tr[name='+properydata[j].title+']').append(td);
				if(flag){
					initProperties($lineData,properydata[j]);
				}
			}
		}
	}
	$('#title').show();
	$('#propertyTable').show();
	if(!flag){
		var properties = $lineData.properties;
		for(i=0;i<properties.length;i++){
			if(properties[i].type=="Choice"){
				$('#'+properties[i].id+" select").val(properties[i].value);
			}else{
				$('#'+properties[i].id+" input").val(properties[i].value);
			}
		}
	}
	
	$('.td input').bind('change',function(){
		var pid = $(this).parent().attr('id');
			for(var i=0;i<$lineData.properties.length;i++){
				if(pid=="name"){
					golFlow.setName(id,$(this).val(),"line");
				}
//				else if(pid=="overrideid"){
//					golFlow.transNewId(id,$(this).val(),"line");
//				}
				if($lineData.properties[i].id==pid){
					$lineData.properties[i].value = $(this).val();
					break;
				}
			}
	});
	$('.td select').bind('change',function(){
		var pid = $(this).parent().attr('id');
		var data = $(this).children('option:selected').val();
		for(var i=0;i<$lineData.properties.length;i++){
			if($lineData.properties[i].id==pid){
				$lineData.properties[i].value = data;
				break;
			}
		}
	});

}

function choicePropertiesUI(packageName,nodeJson,flag){
	var properyData = $properyPackagesData[packageName];
	if(properyData==null) return;
	for(var i=0;i<properyData.length;i++){
		if(properyData[i].visibility){
			$count +=1;
			$('#tbody').append('<tr name = "'+properyData[i].title+'" style="font: 11px/15px tahoma,arial,verdana,sans-serif;" title="'+properyData[i].description+'"><td style="border-right: 1px solid #F1F1F1;line-height:20px;width:40%;">&nbsp'+properyData[i].title+'</td></tr>');
			if($count%2==1)
				$('#tbody').find('tr[name='+properyData[i].title+']').css('background-color','#E1E1E1');
			$('#tbody').find('tr[name='+properyData[i].title+']').append(createPPInDiv(nodeJson,properyData[i]));
			if(flag){
				initProperties(nodeJson,properyData[i]);
			}
		}
	}
	$('#propertyTable').show();
	$('#title').show();
}
//创建属性菜单
function createPPInDiv(nodeJson,data){
	var td = $('<td  id="'+data.id+'" name = '+data.title+' class="td" style="position:relative;width:60%"></td>');
	if(data.type=="String"){
		td.append('<input style="background-color:transparent;outline:0;border:0;height:20px" type="text" />');
			if(data.id=="name"){
				data.value=stencilset.id;
				td.find('input').val(stencilset.id);
			}else if(data.id=="description"){
				data.value= nodeJson.name;
				td.find('input').val(nodeJson.name);
			}
	}else if(data.type=="Text"){
		td.append('<input style="background-color:transparent;outline:0;border:0;height:20px" type="text" />');
		var input = td.find('input');
		input.bind('click',function(){
			var title = $(this).parent().attr('name');
			initComplexDiv(nodeJson,this,title,data);
		});
	}else if(data.type=="Choice"){
		td.append('<select style="background-color:transparent;outline:0;border:0;height:20px;width:100%"><option value="" select="selected">&nbsp</option></select>');
		var select = td.find('select');
		if(typeof(data.items)!='undefined')
		if(data.items.length!=0){
			for(var i =0;i<data.items.length;i++){
				var temp = $("<option></option>");
				temp.html(data.items[i].title);
				temp.attr("value",data.items[i].id);
				select.append(temp);
			}
			return td;
		}
		 if(data.id=="approvalRoles"){//审批角色
			AjaxUrlForChoice(approlesUrl,select);
		}
//		else if(data.id=="personnelType"){//人员构成类型
//			AjaxUrlForChoice(personnelTypeUrl,select);
//		}
	}else if(data.type=="Complex"){
		td.append('<input style="background-color:transparent;outline:0;border:0;height:20px" type="text" />');
		td.find('input').bind('click',function(){
			var title = $(this).parent().attr('name');
			initComplexDiv(nodeJson,this,title,data);
		});
	}
	return td;
}

//流程默认回退节点
function rollBackNode(select){
	$(select).empty();
	$(select).append('<option value="" select="selected">&nbsp</option>');
	var arr = new Array();
	var id = stencilset.id;
	var data = golFlow.exportData();
	var flag =true;
    var i;
	while(flag){
		flag =false;
		for(i in data['lines']){
			var to = data['lines'][i].to;
			if(to==id){
				id = data['lines'][i].from;
//				alert(data['nodes'][data['lines'][i].from].properties[0].value)
//				alert(data['lines'][i].from)
				arr[arr.length]=data['lines'][i].from+","+data['nodes'][data['lines'][i].from].type;
				flag =true;
				break;
			}
		}
	}
	for(i=0;i<arr.length;i++){
		var tmp = arr[i].split(",")[0];
		var tmp1 = arr[i].split(",")[1];
		if(data['nodes'][tmp].type=="task"||data['nodes'][tmp].type=="node"||data['nodes'][tmp].type=="start round"){
			var temp = $("<option></option>");
			temp.html(data['nodes'][tmp].name);
			temp.attr("value",data['nodes'][tmp].properties[0].value+","+tmp1);
			$(select).append(temp);
		}
	}
}

//回退属性
function rollback(select){
	$(select).empty();
	$(select).append('<option value="" select="selected">&nbsp</option>');
	var arr = new Array();
	var id = stencilset.id;
	var data = golFlow.exportData();
	var flag =true;
    var i;
	while(flag){
		flag =false;
		for(i in data['lines']){
			var to = data['lines'][i].to;
			if(to==id){
				id = data['lines'][i].from;
//				alert(data['nodes'][data['lines'][i].from].properties[0].value)
//				alert(data['lines'][i].from)
				arr[arr.length]=data['lines'][i].from+","+data['nodes'][data['lines'][i].from].type;
				flag =true;
				break;
			}
		}
	}
	for(i=0;i<arr.length;i++){
		var tmp = arr[i].split(",")[0];
		var tmp1 = arr[i].split(",")[1];
		if(data['nodes'][tmp].type=="task"||data['nodes'][tmp].type=="node"||data['nodes'][tmp].type=="start round"){
			var temp = $("<option></option>");
			temp.html(data['nodes'][tmp].name);
			temp.attr("value",data['nodes'][tmp].properties[0].value+","+tmp1);
			$(select).append(temp);
		}
	}
}
function initProperties(nodeJson,properyData){
	if(nodeJson==null) return;
	var tmp = {id:properyData.id,value:properyData.value,type:properyData.type};
	nodeJson['properties'].push(tmp);
}
function initComplexDiv(nodeJson,thisInput,title,data){
	var divWidth = 0;
	$(thisInput).blur();
	var $baseDiv = $('<div></div>');
	$baseDiv.css('width','100%')
	.css('height','100%')
	.css('background-color','white');
//	.css('overflow-y','auto');
	var $toolDiv = $('<div></div>');
	$toolDiv.css('width','100%')
	.css('height','28px')
	.css('background','url(./ztree/img/tool_bg.gif)')
	.css('border-bottom','1px solid #555555');
//	.css('position','absolute');
	var $addBtn = $('<span style="font-size: 11px;line-height:25px;cursor:pointer;float:left;margin-left:3px;color:#F1F1F1"><img style="height:16px;width:16px; margin: 0 2px -4px 0;" src="./ztree/img/add.png"/>增加</span>');
//	$addBtn.bind('click',{data:data},toolAdd);
	$addBtn.bind('click',function(){
		toolAdd(nodeJson,thisInput,title,data);
	});
	var $delBtn = $('<span style="font-size: 11px;line-height:25px;cursor:pointer;float:left;margin-left:5px;color:#F1F1F1"><img style="height:16px;width:16px; margin: 0 2px -4px 0;" src="./ztree/img/delete.png"/>删除</span>');
	$delBtn.bind('click',toolDel);
	var $tableDiv = $('<table  cellspacing="0.5"  class="propertyTable" ><thead style="margin-bottom: 1px;background:url(./ztree/img/grid3-hrow.gif);height:25px;font: bold 11px/15px tahoma,arial,verdana,sans-serif;color:white;" align="left"></thead></table>');
	$tableDiv.find('thead').append('<th style="width:20px;border-right: 1px solid #d0d0d0;"></th>');
    var i;
	if(data.complexItems!=null)
		for(i =0;i<data.complexItems.length;i++){
			divWidth +=data.complexItems[i].width;
			$tableDiv.find('thead').append('<th style="width:'+data.complexItems[i].width+'px;line-height:25px;border-right: 1px solid #d0d0d0;">&nbsp'+data.complexItems[i].name+'</th>');
		}
	var $overDiv = $('<div style="height:224px;overflow:auto"><table id="complexTable"><tbody id = "complexTableBody"></tbody></table></div>');
	var $btnDiv = $('<div style="margin-top:10px;color:white"><span name ="submit" style="cursor:pointer;border-radius: 5px;background:url(./ztree/img/white-top-bottom.gif);display: inline-block;height: 20px;line-height: 20px;width: 80px;margin-left:80px">提交</span><span onclick ="cancel();" style="cursor:pointer;background:url(./ztree/img/white-top-bottom.gif);border-radius: 5px;display: inline-block; height: 20px;line-height: 20px;width: 80px;margin-left:30px">取消</span></div>');
	var $textDiv = $('<div style="height:100%;overflow:auto"><textarea id="TextArea" style="resize:none;width:100%;height:100%"></textarea></div>');
//	if(nodeJson==null){//工作流 弹窗保存事件
//		$btnDiv.find('span[name=submit]').bind('click',{data:data,thisInput:thisInput},submit);
//	}else{//节点弹窗保存事件
		$btnDiv.find('span[name=submit]').bind('click',{data:data,thisInput:thisInput,nodeJson:nodeJson},submit);
//	}
	if(data.type=="Text"){
		divWidth = 350;
		$baseDiv.append($textDiv);
		$baseDiv.append($btnDiv);
	}else{
		$toolDiv.append($addBtn);
		$toolDiv.append($delBtn);
		$baseDiv.append($toolDiv);
		$baseDiv.append($tableDiv);
		$baseDiv.append($overDiv);
		$baseDiv.append($btnDiv);
	}
	//编辑时
	if($(thisInput).val()!=""){
		
		if(data.type=="Text"){
			$textDiv.find('textarea').val($(thisInput).val());
		}else{
			var jsonVal = eval('('+$(thisInput).val()+')');
			for(var j=0;j<jsonVal.length;j++){
				var $tr = $('<tr></tr>');
				if((j+1)%2==0)
					$tr.css('background-color','#ededed');
				$tr.append('<td style="border-right: 1px solid #d0d0d0;"><input style="margin-left: -1px;" name="child" type="checkbox"></td>');
				for(i =0;i<data.complexItems.length;i++){
					var td = $('<td style="width:'+data.complexItems[i].width+'px" class="'+data.complexItems[i].id+'"></td>');
					if(data.complexItems[i].type=="String"){
						td.append('<span style="border-bottom: 1px solid #d0d0d0;border-right: 1px solid #d0d0d0;"><input class = "tval" style="width:100%;height:17px;background-color:transparent;outline:0;border:0;" type="text"/></span>');
						td.find('span input').val(jsonVal[j][data.complexItems[i].id]);
						if(data.complexItems[i].id=="rollbackfun"){
							td.find('span input').bind("click",function(){
								features ="dialogHeight:510px;dialogWidth:560px;center:1;help:no;status:no;dialogHide:1";
								var returnValue=window.showModalDialog("../workflow/formula.jsp?gs="+$(this).val(),"",features);
//								alert(typeof(returnValue))
								if(typeof(returnValue)!="undefined"){
									$(this).val(returnValue);
								}
							});
						}
					}else if(data.complexItems[i].type=="Choice"){
						td.append('<span style="border-bottom: 1px solid #d0d0d0;"><select class = "tval" style="background-color:transparent;outline:0;border:0;height:17px;width:100%"><option value="" select="selected">&nbsp</option></select></span>');
						if(data.complexItems[i].id=="forkpath"){//判断路径分支
							forkpath(td.find('span select'));
						}else if(data.complexItems[i].id=="choiceFun"){//附加功能
							AjaxUrlForChoice(additionalfunctionUrl,td.find('span select'));
						}else if(data.complexItems[i].id=="choiceType"){//意见类型
							wfOptType(data.complexItems[i],td.find('span select'));
						}else if(data.complexItems[i].id=="forkconditions"){//检查条件
							AjaxUrlForChoice(checkFunUrl,td.find('span select'));
						}else if(data.complexItems[i].id=="rollbacknode"){//回退
							rollback(td.find('span select'));
						}else{
							var select = td.find('span select');
							if(typeof(data.complexItems[i].items)!='undefined')
								if(data.complexItems[i].items.length!=0){
									for(var z =0;z<data.complexItems[i].items.length;z++){
										var temp = $("<option></option>");
										temp.html(data.complexItems[i].items[z].title);
										temp.attr("value",data.complexItems[i].items[z].id);
										select.append(temp);
									}
								}
						}
						td.find('span select').val(jsonVal[j][data.complexItems[i].id]);
					}
					$tr.append(td);
				}
				$overDiv.find('#complexTableBody').append($tr);
			}
		}
	}
//	$('#TextArea').focus();
	alertWin(divWidth,350,title, $baseDiv[0]);
}
function toolAdd(nodeJson,thisInput,title,data){
//	var data = e.data.data;
	var $tr = $('<tr></tr>');
	var rowNum = window.complexTable.rows.length+1;
	if(rowNum%2==0)
		$tr.css('background-color','#ededed');
	$tr.append('<td style="border-right: 1px solid #d0d0d0;"><input style="margin-left: -1px;" name="child" type="checkbox"></td>');
	for(var i =0;i<data.complexItems.length;i++){
		var td = $('<td style="width:'+data.complexItems[i].width+'px" class="'+data.complexItems[i].id+'"></td>');
		if(data.complexItems[i].type=="String"){
			td.append('<span style="border-bottom: 1px solid #d0d0d0;border-right: 1px solid #d0d0d0;"><input class = "tval" style="width:100%;height:17px;background-color:transparent;outline:0;border:0;" type="text"/></span>');
			if(data.complexItems[i].id=="rollbackfun"){
				td.find('span input').bind("click",function(){
					features ="dialogHeight:510px;dialogWidth:560px;center:1;help:no;status:no;dialogHide:1";
					var returnValue=window.showModalDialog("../workflow/formula.jsp?gs="+$(this).val(),"",features);
//					alert(typeof(returnValue))
					if(typeof(returnValue)!="undefined"){
						$(this).val(returnValue);
					}
				});
			}
		}else if(data.complexItems[i].type=="Choice"){
			td.append('<span style="border-bottom: 1px solid #d0d0d0;"><select class = "tval" style="background-color:transparent;outline:0;border:0;height:17px;width:100%"><option value="" select="selected">&nbsp</option></select></span>');
			if(data.complexItems[i].id=="forkpath"){//判断路径分支
				forkpath(td.find('span select'));
			}else if(data.complexItems[i].id=="choiceFun"){//附加功能
				AjaxUrlForChoice(additionalfunctionUrl,td.find('span select'));
			}else if(data.complexItems[i].id=="choiceType"){//意见类型
				wfOptType(data.complexItems[i],td.find('span select'));
			}else if(data.complexItems[i].id=="forkconditions"){//检查条件
				AjaxUrlForChoice(checkFunUrl,td.find('span select'));
			}else if(data.complexItems[i].id=="rollbacknode"){//回退
				rollback(td.find('span select'));
			}
			
			else{
				var select = td.find('select');
				if(typeof(data.complexItems[i].items)!='undefined')
					if(data.complexItems[i].items.length!=0){
						for(var j =0;j<data.complexItems[i].items.length;j++){
							var temp = $("<option></option>");
							temp.html(data.complexItems[i].items[j].title);
							temp.attr("value",data.complexItems[i].items[j].id);
							select.append(temp);
						}
					}
			}
		}
		$tr.append(td);
	}
	$('#complexTableBody').append($tr);
}
function toolDel(){
	$(':checkbox[name=child]').each(function(){
		if($(this).attr('checked')){
			$(this).closest('tr').remove();
		}
	});
	var rowNum = window.complexTable.rows.length;
	for(var i =0;i<rowNum;i++){
		if(((i+1)%2)==1){
			$("#complexTable tr").eq(i).css('background-color','white');
		}else
			$("#complexTable tr").eq(i).css('background-color','#ededed');
	}
}
function submit(e){
	var data = e.data.data.complexItems;
	var thisInput = e.data.thisInput;
	var nodeJson = e.data.nodeJson;
    var i;
	if(e.data.data.type=="Text"){
		var textArea=$('#TextArea');
		$(thisInput).val(textArea.val());
	}else{
		 if(data==null) return;
		 var table=document.getElementById('complexTable');
		 var json = "[";
		 if(table.rows.length==0){
			 $(thisInput).val("");
		 }else{
			 for(i=0 ;i<table.rows.length;i++)
		     { 
		    	 json +="{";
		         for(var j=1;j<table.rows[i].cells.length;j++){
		        	 var inputVal = $(table.rows[i].cells[j]).find('span input').val();
		        	 var selectVal = $(table.rows[i].cells[j]).find('span select').val();
		        	 if(inputVal != undefined){
		        		 json +="\""+data[j-1].id+"\":\""+inputVal+"\"";
		        	 }else if(selectVal !=undefined){
		        		 json +="\""+data[j-1].id+"\":\""+selectVal+"\"";
		        	 }
		        	 if(j<table.rows[i].cells.length-1){
		        		 json +=",";
		        	 }
		         }
		         json +="},";
		     }
		     json = json.substring(0, json.length-1);
		     if(json!=""){
		    	 json += "]";
		    	 $(thisInput).val(json);
		     }
		 }
	}
	
 	 var id = $(thisInput).parent().attr('id');
 	if(typeof(nodeJson)=="undefined"){//流程保存信息
 		
 	}else{//节点保存信息
 		for(i=0;i<nodeJson.properties.length;i++){
// 			alert(nodeJson.properties[i].id+","+id)
 	 		 if(nodeJson.properties[i].id==id){
// 	 			 if(id=="checkfunlst")
// 	 				nodeJson.properties[i].value = checkFuns;
// 	 			 else
 	 				 nodeJson.properties[i].value = $(thisInput).val();
 	 			 break;
 	 		 }
 	 	 }
 	}
 	closeDiv();
}
function cancel(){
	closeDiv();
}
//判断分支
function forkpath(select){
	var dataLine = golFlow.exportData();
	var id = stencilset.id;
	var arr = new Array();
    var i;
	for(i in dataLine['lines']){
		var from = dataLine['lines'][i].from;
		if(from==id){
			arr[arr.length]=i;
//			arr[arr.length]=dataLine['lines'][i].properties[0].value;
		}
	}
	for(i=0;i<arr.length;i++){
//		if(dataLine['nodes'][arr[i]].type=="task"){
			var temp = $("<option></option>");
			temp.html(dataLine['nodes'][dataLine['lines'][arr[i]].to].name);
//			temp.attr("value",dataLine['lines'][arr[i]].properties[0].value);
			temp.attr("value",arr[i]);
			select.append(temp);
//		}
	}
}
//意见类型
function wfOptType(data,select){
	for(var i=0;i<data.items.length;i++){
		var temp = $("<option></option>");
		temp.html(data.items[i].title);
		temp.attr("value",data.items[i].id);
		select.append(temp);
	}
}