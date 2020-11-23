;
var SysUserTel = function(window, $) {
	var setting = {
			data : {
				simpleData : {
					enable : true
				}
			},
			view : {
				showLine : false,
				dblClickExpand : false,
				/* addDiyDom : addDiyDom */
			},
			check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType: { "Y": "ps", "N": "ps" }
			},
			callback : {
			}
		};


	var _bindTelConfirm = function () {
		$("#sysUserTelConfirm").bind("click", function(event){
			var selectNodes = $.fn.zTree.getZTreeObj("dataTree").getCheckedNodes(true);
			if(selectNodes.length == 0){
				parent.dialog.get("SysUserTelDialog").close();
				return;
			}			
			var cusTel="";
			$.each(selectNodes,function(i,treeNode){
				if(treeNode.isParent){	
				}else{
					if( i == selectNodes.length-1){
						cusTel = cusTel + treeNode.name;
					}else{
						cusTel = cusTel + treeNode.name+";";
					}
				}
			});
			var sysUserTelInfo = new Object();
			sysUserTelInfo.cusTel = cusTel;
			parent.dialog.get("SysUserTelDialog").close(sysUserTelInfo);
		});
	}; 
	
	var _init = function (zNodes){
		
		$.fn.zTree.init($("#dataTree"), setting, zNodes);
	}
	
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		bindTelConfirm : _bindTelConfirm
	};
}(window, jQuery);