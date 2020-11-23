var setting = {
				data: {
					simpleData: {
						enable: true
					}
				},
				check: {
					enable: true
				},
				view: {
					showIcon: false,
					showLine: false,
					addDiyDom: addDiyDom
				},
				callback: {
					onCheck: zTreeOnCheck
				}
			};
$(function(){
	var actionUrl=webPath+"/pmsDataSub/findByPageForConfAjax";
	$.ajax({
		type:"post",
		async:false,
		cache:false,
		url:actionUrl,
		dataType:"json",
		data:{funNo:$("#funNo").val()},
		success:function(jsonData){
			zNodes = jsonData.zNodes;
			$.fn.zTree.init($("#pmsTree"), setting, zNodes);
		},
		error:function(){
			window.top.alert("error",0);
		}
	});
 });

function addDiyDom(treeId, treeNode) {
	var aObj = $("#" + treeNode.tId + "_a");
	$("#"+ treeNode.tId +"_switch").remove();
	var $input =  $('<input type="text" id="'+treeNode.tId+'_pmsField" class="input_pmsField"/>');
	var $boxDiv = $('<div id="'+treeNode.tId+'_pmsStsDiv" class="box_pmsStsDiv"></div>');
	var $box = $('<input type="hidden" id="'+treeNode.tId+'_pmsSts" class="multi-switch box_pmsSts" />');
	aObj.append($input);
	$("#"+treeNode.tId+"_check").attr("class","button chk checkbox_true_full");
	$("#"+treeNode.tId+"_check").hide();
	//aObj.append($boxDiv);
	//$boxDiv.append($box);
	$input.val(treeNode.pmsField);
	$input.attr("maxlength",100);
	//$box.attr("name",treeNode.tId+"_pmsSts");
	//$box.attr("title","是否启用指定字段");
	if(treeNode.pmsSts==""||typeof(treeNode.pmsSts)=="undefined"){
		treeNode.pmsSts = 0;
		//$box.val(0);
		//$box.attr("initial-value",0);
	}else{
		//$box.val(treeNode.pmsSts);
		//$box.attr("initial-value",treeNode.pmsSts);
	}
	/*$box.attr("unchecked-value",0);
	$box.attr("checked-value",1);
	$box.multiSwitch({
		functionOnChange:function($element){
			treeNode.pmsSts=$element.val();
		},
		textChecked: "ON",
        textNotChecked: "OFF"
	});*/
}

function zTreeOnCheck(event, treeId, treeNode) {
	if(treeNode.checked){
		treeNode.funNo=$("#funNo").val();
	}else{
		treeNode.funNo="0";
	}
};


function saveDataRangSub(){
	var treeObj = $.fn.zTree.getZTreeObj("pmsTree");
	var nodes = treeObj.getNodes();
	for ( var x in nodes) {
		nodes[x].pmsField=$("#"+nodes[x].tId+"_pmsField").val();
	}
	var actionUrl=webPath+"/pmsDataSub/insertConfAjax";
	LoadingAnimate.start();
	$.ajax({
		type:"post",
		async:false,
		cache:false,
		url:actionUrl,
		dataType:"json",
		data:{
			ajaxData:JSON.stringify(nodes),
			funNo:$("#funNo").val()
		},
		success:function(jsonData){
			LoadingAnimate.stop();
			if(jsonData.flag=="success"){
				window.top.alert(jsonData.msg,1);
				myclose_click();
			}else{
				window.top.alert(jsonData.msg,0);
			}
		},
		error:function(){
			LoadingAnimate.stop();
			alert("error");
		}
	});
}