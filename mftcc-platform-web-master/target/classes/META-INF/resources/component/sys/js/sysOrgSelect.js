var setting = {
		data : {
			simpleData : {
				enable : true
			}
		},
		view : {
			showLine : false,
			dblClickExpand : false,
			addDiyDom : addDiyDom,
			selectedMulti: false
		},
		callback : {
			onClick : zTreeOnClick
		}
	};

function addDiyDom(treeId, treeNode) {
	var aObj = $("#" + treeNode.tId + "_a");
	aObj.find("span").eq(0).remove();
	if ($("#diyBtn_"+treeNode.id).length>0) return;
	var imagesStr ="<img src="+webPath+'/themes/factor/images/dept.png'+' tabindex=1 style=margin-left: 4px; width=16 height=16'+">";
	if(treeNode.upOne=="0"){
		imagesStr ="<img src="+webPath+'/themes/factor/images/org.png'+' tabindex=1 style=margin-left: 4px; width=16 height=16'+">";
		aObj.find("span").eq(0).before(imagesStr);
	}else{
		aObj.find("span").eq(0).before(imagesStr);
	}
}

function zTreeOnClick(event, treeId, treeNode) {
	var sysOrg = new Object();
	sysOrg.brNo=treeNode.brNo;
	sysOrg.brName=treeNode.brName;
	sysOrg.upTwo = treeNode.upTwo;
	parent.dialog.get("orgSelectDialog").close(sysOrg).remove();
};

var zTree, rMenu;
$(document).ready(function(){
	$.fn.zTree.init($("#sysOrgTree"), setting, ajaxData);
	zTree = $.fn.zTree.getZTreeObj("sysOrgTree");
	rMenu = $("#rMenu");
	//$("#saveBtn").bind("click",function(){});
	$(".scroll-content").mCustomScrollbar({
		advanced:{
			theme:"minimal-dark",
			updateOnContentResize:true
		}
	});
});