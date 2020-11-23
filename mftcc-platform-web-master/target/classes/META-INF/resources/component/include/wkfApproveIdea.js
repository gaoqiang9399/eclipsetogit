function Wkf_addDiyDom(treeId, treeNode) {
	var liObj = $("#" + treeNode.tId).empty();
	var className="",color;
	if(treeNode.optName=="同意"){
		className = "yes";
		color = "#80c45a";
	}else if(treeNode.optName=="不同意"){
		className = "no";
		color = "#80c45a";
	}
		
	var icon = "<span id='" + treeNode.tId + "_icon' class='" + treeId
			+ "_icon'><div class='"+className+"'></div><i style='background:"+color+"'></i></span>";
	var line = "<span id='" + treeNode.tId + "_line' class='" + treeId
			+ "_line'></span>";
	var endDate = "<span id='" + treeNode.tId + "_date' class='" + treeId
			+ "_date'>" + treeNode.end.split(" ")[0] +" "+ treeNode.end.split(" ")[1].substring(0,8) + "</span>";
	var description;
	/**
	 * 注释原因：审批意见时间的后面出现不明的数字（根据数据库文档中的注释duration代表持续时间，展示没有意义）
//	if(treeNode.duration.split(" ").length>1){
//		description = "<span id='" + treeNode.tId + "_description' class='"
//			+ treeId + "_description'>" + treeNode.duration.split(" ")[0]+":	 "+treeNode.duration.split(" ")[1] + "</span>";
//	}else{
//		description = "<span id='" + treeNode.tId + "_description' class='"
//			+ treeId + "_description'>" + treeNode.duration + "</span>";
//	}
	**/
	description="";
	var optName = "<span id='" + treeNode.tId + "_optName' class='" + treeId
			+ "_optName'><span>经["+ treeNode.description +"] 审批节点 审批</span><span style='color:"+ color +"'>" + treeNode.optName + "!</span></span>";
	/*
	 * var result = "<span id='" +treeNode.tId+"_result' class='"
	 * +treeId+"_result'>"+treeNode.result+"</span>";
	 */
	var approveIdea = "<span id='" + treeNode.tId + "_approveIdea' class='"
			+ treeId + "_approveIdea' >" + treeNode.approveIdea + "</span>";
	liObj.append(icon + line + endDate + description + optName
			+ approveIdea);
	liObj.css({
		"width": "calc(100% - 50px)",
    	"margin-left": "50px"
	});
	$("#" + treeNode.tId + "_line").css("height",
			liObj.outerHeight() - 10 + "px");

};
var Wkf_zTreeObj, Wkf_setting = {
	view : {
		selectedMulti : false,
		showIcon : false,
		addDiyDom : Wkf_addDiyDom
	}
}, Wkf_zTreeNodes = [];