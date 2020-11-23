;
var MfKindInterceptConfig=function(window, $){
	//初始化产品基础配置信息
	var _init = function(data){
		var htmlStr = getPrdctInterceptConfigHtml(data);
		$(".nav-content-div").html(htmlStr);
		$(".config-div").mCustomScrollbar("scrollTo","top"); // 滚动到顶部（垂直滚动条）
	};
	//产品风控模型设置
	var getPrdctInterceptConfigHtml = function(data){
		var mfSysKind = data.mfSysKind;
		var nodeList = data.nodeList;
		var nodeConfigMap = data.nodeConfigMap;
		var htmlStr="";
		htmlStr=htmlStr+'<div class="content-div interceptConfig"><div class="sub-content-div padding_left_15">'
		+'<div class="sub-content padding_left_20">';
		
		htmlStr = htmlStr + getPrdctInterceptContentHtml(nodeList,nodeConfigMap);
		
		htmlStr=htmlStr+'</div></div></div>';
		return htmlStr;
	};
	
	var getPrdctInterceptContentHtml = function(nodeList,nodeConfigMap){
		var htmlStr="";
		$.each(nodeList,function(i,kindNode){
			htmlStr =htmlStr+'<div class="sub-content-div padding_left_5" >'
			+'<div class="sub-title">'
			+'<span>'+kindNode.nodeName+'</span>'
			+'</div>'
			+'<div class="sub-content" id="interceptConfig'+kindNode.nodeNo+'">'
			+'<div class="item-div"><div class="item-content">';
			if(kindNode.nodeNo=="cus_apply" || kindNode.nodeNo=="apply"){
				htmlStr =htmlStr +'<div class="main-content-desc">'
				+'<span class="content-desc">允许客户贷款的前提条件任何一条不满足则不能贷款</span>'
				+'<a href="javascript:void(0);" class="padding_left_15 pointer" onclick="MfKindInterceptConfig.addNodeIntercept(\''+kindNode.nodeNo+'\');">配置</a>'
				+'</div>';
			}else{
				htmlStr =htmlStr +'<div class="main-content-desc">'
				+'<span class="content-desc">检查以下风险项，辅助风控</span>'
				+'<a href="javascript:void(0);" class="padding_left_15 pointer" onclick="MfKindInterceptConfig.addNodeIntercept(\''+kindNode.nodeNo+'\');">配置</a>'
				+'</div>';
			}
			htmlStr =htmlStr +'<div class="main-content-div" id="interceptNode'+kindNode.nodeNo+'">';
			var interceptList = nodeConfigMap[kindNode.nodeNo]["interceptList"];
			htmlStr = htmlStr + getNodeInterceptContentHtml(interceptList,kindNode.nodeNo);
			htmlStr =htmlStr +'</div></div></div></div></div>';
		});
		return htmlStr;
	};
	var getNodeInterceptContentHtml = function(interceptList,nodeNo){
		var htmlStr ='';
		$.each(interceptList,function(i,kindNodeIntercept){
			var firstStr="";
			if(i==0){
				firstStr="first";
			}
			htmlStr =htmlStr +'<div class="main-content '+firstStr+'">'
			+'<span class="item-content-span">'
			+'<span class="item-content-desc margin_right_5">'+kindNodeIntercept.itemName+'：'+kindNodeIntercept.itemDesc+'</span>'
			+'<i class="i i-x5 pointer" onclick="MfKindInterceptConfig.deleteNodeIntercept(\''+nodeNo+'\',\''+kindNodeIntercept.kindNodeInterceptId+'\');"></i>'
			+'</span>'
			+'</div>'
		});
		return htmlStr;
	}
	
	//配置拦截项
	var _addNodeIntercept  = function(nodeNo){
		top.flag=false;
		top.itemNos="";
		//var title = ("cus_apply"==nodeNo || "apply"==nodeNo) ? "选择准入项" : "选择拦截项";
		var title = "选择拦截项";
		window.parent.openBigForm(webPath+"/mfKindNodeIntercept/getRiskInterceptList?kindNo="+kindNo+"&nodeNo="+nodeNo, title, function(){
			if(top.flag){
				$.ajax({
					url:webPath+"/mfKindNodeIntercept/insertAjax",
					type:'post',
					data:{kindNo:kindNo,nodeNo:nodeNo,ajaxData:top.itemNos},
					success:function(data){
						if(data.flag=="success"){
							var htmlStr =  getNodeInterceptContentHtml(data.interceptList,nodeNo);
							$("#interceptNode"+nodeNo).html(htmlStr);
						}
					},error:function(){
						alert(top.getMessage("ERROR_REQUEST_URL", getUrl),0);
					}
				});
			}
		});
	};
	
	
	var _deleteNodeIntercept = function(nodeNo,kindNodeInterceptId){
		alert(top.getMessage("CONFIRM_DELETE"),2,function(){
			$.ajax({
				url:webPath+"/mfKindNodeIntercept/deleteAjax",
				type:'post',
				data:{kindNo:kindNo,nodeNo:nodeNo,kindNodeInterceptId:kindNodeInterceptId},
				success:function(data){
					if(data.flag=="success"){
						var htmlStr =  getNodeInterceptContentHtml(data.interceptList,nodeNo);
						$("#interceptNode"+nodeNo).html(htmlStr);
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
		addNodeIntercept:_addNodeIntercept,
		deleteNodeIntercept:_deleteNodeIntercept,
	};
	
}(window, jQuery);