//编辑评级级别
function editAssess(obj){
	var $obj=$(obj);
	var evalLevel = $obj.prev().val();
	if(evalLevel!=null){
		$.ajax({
			url:webPath+"/evalScoreGradeConfig/getIfEvalLevelConfigAjax",
			data:{evalScenceNo:evalScenceNo.split("=")[1],evalLevel:evalLevel},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					window.parent.openBigForm(webPath+"/evalScoreGradeConfig/getById?evalLevel="+evalLevel+"&evalScenceNo="+evalScenceNo.split("=")[1],"评级场景",function(){});
				}else{
					alert("请先配置分数等级！",0);
				}
			},error:function(){
				 window.top.alert(top.getMessage("ERROR_REQUEST_URL", getUrl),0);
			}
		});
	}else{
		alert("请选择级别！",0);
	}
}
//编辑评级指标项
function editAppProperty(obj){
	var $obj=$(obj);
	var value = $obj.prev().val();
	var entityKey = $obj.parents("li").attr("data-name");
	if(value!=null&&value!=""){
		window.parent.openBigForm(webPath+"/appProperty/getById?propertyId=&propertyNo="+value,"评级指标项配置",function(){
			/*var tableFlag = "";
			if(entityKey=="evalScenceAdjRel"||entityKey=="evalScenceDxRel"){
				tableFlag ="DX";
			}else{
				tableFlag ="FIN";
			}
			var scenceNo = $obj.parents(".settings").data("scenceNo");
			parmData = {
				tableFlag:tableFlag,
				scenceNo:scenceNo
			};
			$.ajax({
				type:"post",
				data:{ajaxData:JSON.stringify(parmData)},
				url:webPath+"/appProperty/getAppPropertyForList",
				async:false,
				success:function(data){
					if(data.flag == "success"){
						dataEntity = data.dxAppPropertyData;
						var datas = {},
						propertyTypeTemp = "00",
						propertyValueTypeTmp = "00";
						if(tableFlag=="FIN"||tableFlag=="DL"||tableFlag=="RES"){
							//propertyValueTypeTmp = "02";//财务产生
							propertyTypeTemp="1";
						}else if(tableFlag=="DX"||tableFlag=="ADJ"){
							propertyValueTypeTmp = "01";//财务产生
							propertyTypeTemp="2";//定性
						}
						$.each(dataEntity, function(index,entity) {
							var propertyAppType = entity.propertyAppType;
							var propertyValueType = entity.propertyValueType;
							if(propertyTypeTemp==propertyAppType&&propertyValueType!=propertyValueTypeTmp){
								var vals = {"val":entity.propertyName};
								if(entity.propertyValueType=="02"){//数据字典项
									var data = {"type":entity.propertyValueType,"keyName":entity.propertyKeyName};
									vals["data"] = data;
								}
								datas[entity.propertyNo] = vals;
							}
						});
						var $select = $obj.prev();
						$select.empty();
						$.each(datas, function(val,cdata) {
							var text = cdata.val;
							var $option = $('<option value="'+val+'">'+text+'</option>');
							if(cdata["data"]!==undefined){
								//向option存放隐藏数据
								$option.data("data",cdata["data"]);
							}
							$select.append($option);
						});
					}else if(data.flag == "error"){
						alert(data.msg,0);
					}
				},error:function(){
					
				}
			});*/
			
		});
	}else{
		alert("请选择指标项！",0);
	}
}

//新增评级指标
function addPropertyItem(obj){
	var $obj = $(obj);
	var scenceType = $obj.parents("li").attr("data-name");
	var propertyAppType="";
	var propertyType="";
	var title = "";
	if(scenceType=="evalScenceFinRel"||scenceType=="evalScenceDlRel"||scenceType=="evalScenceRestrictRel"){
		propertyType="01";
		propertyAppType="1";
		title = "定量指标配置";
	}
	if(scenceType=="evalScenceDxRel"||scenceType=="evalScenceAdjRel"){
		propertyType="02";
		propertyAppType="2";
		title = "定性指标配置";
	}
	top.window.openBigForm(webPath+'/appProperty/input?propertyAppType='+propertyAppType+"&propertyType="+propertyType,title,function(){
		getListForAppPropertyData();
	});
}

function addEvalItem(){
	top.window.openBigForm(webPath+'/appProperty/inputAppProperty',"新增指标项",function(){
		EvalScenceIndexRelConfig.getAppPropertyData();
	});
}
//返回
function back(){
	var url= webPath+"/mfCusFormConfig/getMfCusConfig";
	$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
}
