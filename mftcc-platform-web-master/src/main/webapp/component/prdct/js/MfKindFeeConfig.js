;
var MfKindFeeConfig=function(window, $){
	//初始化产品基础配置信息
	var _init = function(data){
		var htmlStr = getPrdctFeeConfigHtml(data);
		$(".nav-content-div").html(htmlStr);
		$(".config-div").mCustomScrollbar("scrollTo","top"); // 滚动到顶部（垂直滚动条）
		$(".vertical-bar").css("height",$(".nav-content-div").height()-40);
	};
	//产品费用配置
	var getPrdctFeeConfigHtml = function(data){
		var mfSysKind = data.mfSysKind;
		var nodeList = data.nodeList;
		var nodeConfigMap = data.nodeConfigMap;
		var htmlStr="";
		htmlStr=htmlStr+'<div class="content-div feeConfig special-config"><div class="sub-content-div padding_left_15">'
		+'<div class="vertical-bar"><i class="i i-yuan"></i></div>'
		+'<div class="sub-title">'
		+'<span class="font_size_16">费用配置</span>'
		+'<button onclick="MfSysKindConfig.addBusNode(\''+mfSysKind.busModel+'\',\'fee\');" class="margin_left_15 btn add-node pointer">节点定制</button>'
		+'</div>'
		+'<div class="sub-content padding_left_45 margin_top_30">';
		htmlStr=htmlStr+getPrdctFeeContentHtml(nodeList,nodeConfigMap);
		
		htmlStr=htmlStr+'</div></div></div>';
		return htmlStr;
	};
	
	var getPrdctFeeContentHtml = function(nodeList,nodeConfigMap){
		var htmlStr="";
		$.each(nodeList,function(i,kindNode){
			htmlStr =htmlStr+'<div class="sub-content-div" style="position:relative;">'
			+'<div class="horizontal-bar"><i class="i i-yuan"></i></div>'
			+'<div class="sub-title">'
			+'<span class="font_size_14">'+kindNode.nodeName+'</span>'
			+'<span class="margin_left_20"><span class="content-desc">'+kindNode.nodeName+'阶段配置了以下费用项</span>'
			+'<a href="javascript:void(0);" onclick="MfKindFeeConfig.addNodeFeeItem(\''+kindNode.nodeNo+'\');" class="padding_left_15 pointer">配置</a>'
			+'</span>'
			+'</div>'
			+'<div class="sub-content" id="feeConfig'+kindNode.nodeNo+'">'
			+'<div class="item-div"><div class="item-content">';
			var feeList = nodeConfigMap[kindNode.nodeNo]["feeList"];
			htmlStr = htmlStr+getNodeFeeItemHtml(feeList,kindNode.nodeNo,kindNode.nodeName);
			htmlStr =htmlStr +'</div></div></div></div>';
		});
		return htmlStr;
	};
	
	var getNodeFeeItemHtml = function(feeList,nodeNo,nodeName){
		var htmlStr ='<div class="main-content-div">';
		$.each(feeList,function(i,kindNodeFee){
			var takeType = kindNodeFee.ext1;//收取类型：1-百分比 2-固额 
			var spanStr='<span class="padding_left_10">'+kindNodeFee.ext5+'<span class="divider">|</span>'+kindNodeFee.ext4+'<span class="divider">|</span>'+kindNodeFee.ext2+'<span class="divider">|</span>'+kindNodeFee.ext3+kindNodeFee.ext8+'<span class="divider">|</span>操作权限['+kindNodeFee.optPower+']</span>';
			if(takeType=="2"){
				spanStr = '<span class="padding_left_10">'+kindNodeFee.ext5+'<span class="divider">|</span>'+kindNodeFee.ext4+'<span class="divider">|</span>'+'固定金额(分摊)'+kindNodeFee.ext3+'元 <span class="divider">|</span>操作权限['+kindNodeFee.optPower+']</span>';
			}
			if(takeType=="3"){
				spanStr = '<span class="padding_left_10">'+kindNodeFee.ext5+'<span class="divider">|</span>'+kindNodeFee.ext4+'<span class="divider">|</span>'+'固定金额(额定)'+kindNodeFee.ext3+'元 <span class="divider">|</span>操作权限['+kindNodeFee.optPower+']</span>';
			}
			
			if(takeType=="4"){
				spanStr = '<span class="padding_left_10">'+kindNodeFee.ext5+'<span class="divider">|</span>'+kindNodeFee.ext4+'<span class="divider">|</span>'+'利息差额'+'<span class="divider">|</span>操作权限['+kindNodeFee.optPower+']</span>';
			}
			var firstStr="";
			if(i==0){
				firstStr="first";
			}
			htmlStr =htmlStr +'<div class="main-content '+firstStr+'">'
			+'<span class="item-content-span">'
			+'<span class="item-content-desc margin_right_5"><span class="node-name">'+kindNodeFee.itemName+'：</span>'+spanStr+'</span>'
			+'<i class="i i-x5 pointer" onclick="MfKindFeeConfig.deleteNodeFeeItem(this,\''+nodeNo+'\',\''+kindNodeFee.kindNodeFeeId+'\');"></i>'
			+'</span>'
			+'<a href="javascript:void(0);" onclick="MfKindFeeConfig.editNodeFeeItem(this,\''+nodeNo+'\',\''+kindNodeFee.ext7+'\');" class="padding_left_15 pointer">配置</a>'
			+'</div>';
		});
		htmlStr =htmlStr +'</div>';
		return htmlStr;
	};
	
	//配置节点费用
	var _addNodeFeeItem = function(nodeNo){
		top.addFlag=false;
		top.feeData="";
		window.parent.openBigForm(webPath+"/mfSysFeeItem/inputForPrdct?feeStdNo="+kindNo+"&nodeNo="+nodeNo,"新增费用项",function(){
			if(top.addFlag){
				if(top.feeData!=""){
                    MfKindFeeConfig.init(top.feeData);
                }
			}
		});
	};

	//编辑节点费用
	var _editNodeFeeItem = function(obj,nodeNo,id){
		top.addFlag=false;
		top.feeData="";
		window.parent.openBigForm(webPath+"/mfSysFeeItem/getByIdForPrdct?id="+id+"&kindNo="+kindNo+"&nodeNo="+nodeNo,"编辑费用项",function(){
			if(top.addFlag){
                if(top.feeData!=""){
                    MfKindFeeConfig.init(top.feeData);
                }
			}
		});
	};
	
	var getFeeItemHtml = function(kindNodeFee,nodeNo){
		var takeType = kindNodeFee.ext1;//收取类型：1-百分比 2-固额 
		var spanStr='<span class="padding_left_10">'+kindNodeFee.ext5+'<span class="divider">|</span>'+kindNodeFee.ext4+'<span class="divider">|</span>'+kindNodeFee.ext2+'<span class="divider">|</span>'+kindNodeFee.ext3+'% <span class="divider">|</span>操作权限['+kindNodeFee.optPower+']</span>';
		if(takeType=="2"){
			spanStr = '<span class="padding_left_10">'+kindNodeFee.ext5+'<span class="divider">|</span>'+kindNodeFee.ext4+'<span class="divider">|</span>'+'固定金额(分摊)'+kindNodeFee.ext3+'元 <span class="divider">|</span>操作权限['+kindNodeFee.optPower+']</span>';
		}
		if(takeType=="3"){
			spanStr = '<span class="padding_left_10">'+kindNodeFee.ext5+'<span class="divider">|</span>'+kindNodeFee.ext4+'<span class="divider">|</span>'+'固定金额(额定)'+kindNodeFee.ext3+'元 <span class="divider">|</span>操作权限['+kindNodeFee.optPower+']</span>';
		}
		
		if(takeType=="4"){
			spanStr = '<span class="padding_left_10">'+kindNodeFee.ext5+'<span class="divider">|</span>'+kindNodeFee.ext4+'<span class="divider">|</span>'+'利息差额'+'<span class="divider">|</span>操作权限['+kindNodeFee.optPower+']</span>';
		}
		htmlStr ='<span class="node-name">'+kindNodeFee.itemName+'：</span>' + spanStr;
		return htmlStr;
	};
	//删除节点费用
	var _deleteNodeFeeItem = function(obj,nodeNo,kindNodeFeeId){
		alert(top.getMessage("CONFIRM_DELETE"),2,function(){		
			$.ajax({
				url:webPath+"/mfKindNodeFee/deleteForPrdctAjax",
				data:{kindNodeFeeId:kindNodeFeeId,kindNo:kindNo,nodeNo:nodeNo},
				success:function(data){
					if(data.flag=="success"){
						$(obj).parents(".main-content").remove();
					}else{
						alert(data.msg,0);
					}
				},error:function(){
					alert(top.getMessage("ERROR_REQUEST_URL", getUrl),0);
				}
			});
		});
	};
	return{
		init:_init,
		addNodeFeeItem:_addNodeFeeItem,
		editNodeFeeItem:_editNodeFeeItem,
		deleteNodeFeeItem:_deleteNodeFeeItem,
	};
	}(window, jQuery);