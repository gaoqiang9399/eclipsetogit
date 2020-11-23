;
var MfCreditDocConfig=function(window, $){
	//初始化产品影像配置信息
	var _init = function(data){
		var htmlStr = getPrdctDocConfigHtml(data);
		$(".nav-content-div").html(htmlStr);
		$(".config-div").mCustomScrollbar("scrollTo","top"); // 滚动到顶部（垂直滚动条）
		$(".vertical-bar").css("height",$(".nav-content-div").height()-40);
	};
	//产品影像配置
	var getPrdctDocConfigHtml = function(data){
        var mfSysKind = data.mfSysKind;
		var nodeList = data.nodeList;
		var nodeConfigMap = data.nodeConfigMap;
		var htmlStr="";
		htmlStr=htmlStr+'<div class="content-div docConfig special-config"><div class="sub-content-div padding_left_15">'
		+'<div class="vertical-bar"><i class="i i-yuan"></i></div>'
		+'<div class="sub-title">'
		+'<span class="font_size_16">影像配置</span>'
		+'<button  onclick="MfSysCreditConfig11.addBusNode(\'' + mfSysKind.busModel + '\',\'doc\');" class="margin_left_15  btn add-node pointer">节点定制</button>'
		+'</div>'
		+'<div class="sub-content padding_left_45 margin_top_30">';
		htmlStr = htmlStr + getPrdctDocContentHtml(nodeList,nodeConfigMap,mfSysKind);
		
		htmlStr=htmlStr+'</div></div></div>';
		return htmlStr;
	};
	
	var  getPrdctDocContentHtml = function(nodeList,nodeConfigMap,mfSysKind){
        var htmlStr = "";
        $.each(nodeList, function (i, kindNode) {
                // 重构div
                var docList = nodeConfigMap[kindNode.nodeNo]["docList"];
                var nodeDocContenHtml = getNodeDocContentHtml(docList, kindNode.nodeNo);

                var box1 = $("<div></div>").addClass("sub-content-div padding_left_5")
                    .css({"position": "relative"});
                var $li = $("<i></i>").addClass('i i-yuan');

                var box2 = $("<div></div>").addClass("horizontal-bar").append($li);
                var $subTile = $("<div></div>").addClass('sub-title');
                var $nodeName = $("<span></span>").addClass("font_size_14").text(kindNode.nodeName);
                $subTile.append($nodeName);
                //获取该节点的节点编号
                var nodeNo = kindNode.nodeNo;
                //获取节点名称
                var nodeName = kindNode.nodeName;
                //该节点下是否存在子流程  0不存在  1存在
                var isExistDoc = nodeConfigMap[kindNode.nodeNo]["isExistDoc"];
                //该节点是主流程节点还是子流程节点  0是主流程  1是子流程
                var mainApprove = nodeConfigMap[kindNode.nodeNo]["mainApprove"];

            	var box5,box7;
                if (isExistDoc == "1") {
                    var approveDocMap = nodeConfigMap[kindNode.nodeNo]["approveDocMap"];
                    var $button = $("<button></button>").addClass("margin_left_15  btn add-node pointer").text(nodeName + "节点定制");
                    $button.attr("onclick", "MfSysCreditConfig11.addBusNode('" + mfSysKind.busModel + "','doc','" + nodeNo + "','" + mainApprove + "');");
                    $nodeName.append($button);


                    var boxApprove1 = $("<div></div>").addClass("boxApprove-1").css({
                        "position": "absolute",
                        "top": "12px",
                        "left": "-23px",
                        "width": "1px",

                        "border-left": "1px dashed  #A5B5C8",
                        "font-size": "20px"
                    });
                    box1.append(box2);
                    box1.append($subTile).append(boxApprove1);

                    var nodeListApprove = approveDocMap["nodeList"];
                    var nodeConfigMapApprove = approveDocMap["nodeConfigMap"];

                    //要件的总行数
                    var docNumTotal = approveDocMap["docNumTotal"];
                    //要件的节点数
                    var nodeListNum = approveDocMap["nodeListNum"];
                    //需要增加的行数
                    var addNum = docNumTotal - nodeListNum;

                    var myheight = nodeListNum * 204 + addNum * 90 + 90;

                    boxApprove1.height(myheight);

                    var htmlStrNew = getPrdctDocContentHtml(nodeListApprove, nodeConfigMapApprove, mfSysKind);
                    box5 = $("<div></div>").addClass("font_size_14 docNode" + nodeNo).attr("data-scno", nodeNo);
                    box7 = $("<div></div>").addClass("main-content-div   lp").css({"margin-left": "30px"}).append(htmlStrNew);

                } else {
                    box5 = $("<div></div>").addClass("item-div docNode" + nodeNo).attr("data-scno", nodeNo);
                    box7 = $("<div></div>").addClass("main-content-div").append(nodeDocContenHtml);
                }
                var box4 = $("<div></div>").addClass("sub-content");

                var box6 = $("<div></div>").addClass("item-content padding_bottom_20");
                //div内部存放标签之后  包括本身的是outerHTML
                box6.html(box7[0].outerHTML);
                box5.html(box6[0].outerHTML);
                box4.html(box5[0].outerHTML);
                //div追加div
                box1.append(box2);
                box1.append($subTile).append(box4);

                htmlStr = htmlStr + box1[0].outerHTML;

            });
        return htmlStr;
	};
	
	var getNodeDocContentHtml = function(docList,scNo){
		var htmlStr = "";
		$.each(docList,function(i,docConfig){
			var spanStr = "";
			var inputStr="";
			if(docConfig.ifMustInput == "1"){
                spanStr=spanStr+'<span style="background: #85B7EE;"><input class="pointer" onclick="MfCreditDocConfig.changeIsMust(this);" type="checkbox" checked="true" >必填</span>'
			}else{
                spanStr=spanStr+'<span style="background: #85B7EE;"><input class="pointer" onclick="MfCreditDocConfig.changeIsMust(this);" type="checkbox">必填</span>'
			}
			htmlStr = htmlStr+'<div class="block-item" data-doctype="'+docConfig.docType+'" data-docno="'+docConfig.docSplitNo+'" data-formno="'+docConfig.formId+'" data-ismust="'+docConfig.ifMustInput+'">'
			+'<div class="checkbox-div">'
			+ spanStr
			+'</div>'
			+'<div class="item">'
			+'<i class="i i-tupian item-icon" ></i><span>'+docConfig.docSplitName+'</span>'
			+'</div>'
			+'<i class="i i-x5 item-del pointer" onclick="MfCreditDocConfig.deleteDocTypeItem(this);"></i>'
			+'</div>';
		});
		//添加按钮
		htmlStr = htmlStr+'<div class="add-item-div" onclick="MfCreditDocConfig.getDocTypeData(this);">'
		+'<div class="add-item">'
		+'<div class="item">'
		+'</div>'
		+'</div>'
		+'</div>';
		return htmlStr;
	};
	
	//新增要件配置弹层
	var _getDocTypeData =function(obj){
		var doctype="";
		var docSplitNo = "";
		var length = $(obj).parents(".item-div").find(".block-item").length;
		$(obj).parents(".item-div").find(".block-item").each(function(i,o){
			if(i == length-1){
				docSplitNo = docSplitNo + $(this).data("docno");
				doctype = doctype + $(this).data("doctype");
			}else{
				docSplitNo = docSplitNo + $(this).data("docno")+"@";
				doctype = doctype + $(this).data("doctype")+"@";
			}
		});
		selectDocTypeDialog(docTypeSelCall,docSplitNo,doctype,obj);
	};
	
	//文档模型选择后 回调函数
	function docTypeSelCall(doc,obj) {
		var $itemDiv = $(obj).parents(".item-div");	
		var dataParmList = [];	
		var scNo = $itemDiv.data("scno");
		if(doc!=""){
			var docNo = doc.docNo.split("@");
			var docName = doc.docName.split("@");
			var docType = doc.docType.split("@");
			var formNo = doc.formNo.split("@");
			$.each(docName, function(i, docThis) {
				if (docNo[i]) {
					var entity = {};
					entity.dime1 = kindNo;
					entity.scNo = scNo;
					entity.docType = docType[i];
					entity.docSplitNo = docNo[i];
					entity.formId = formNo[i];
					entity.docName = docName[i];
					dataParmList.push(entity);
				}
			});
            $.ajax({
                url : webPath+"/docBizSceConfig/insertDocsAjax",
                data:{ajaxData : JSON.stringify(dataParmList),dime1:kindNo,scNo:scNo},
                success:function(data){
                    if(data.flag == "success"){
                        var docList = data.docBizSceConfigList;
                        var htmlStr = getNodeDocContentHtml(docList,scNo);
                        $(".docNode"+scNo).html(htmlStr);
                    }
                },error:function(){
                    alert(top.getMessage("ERROR"),0);
                }
            });
        }
	};
	//删除要件配置
	var _deleteDocTypeItem=function(obj) {
		var $blockItem =$(obj).parents(".block-item");
		//异步删除配置的文档信息
		var dime1 = $blockItem.data("kindno");
		var scNo = $blockItem.data("scno");
		var docType = $blockItem.data("doctype");
		var docSplitNo = $blockItem.data("docno");
		alert(top.getMessage("CONFIRM_OPERATION","删除"),2,function(){
			$.ajax({
				url : webPath+"/docBizSceConfig/deleteDocAjax",
				data:{dime1:dime1,scNo:scNo,docType:docType,docSplitNo:docSplitNo},
				success:function(data){
					if(data.flag == "success"){
						$blockItem.hide("slow",function(){
							$blockItem.remove();
						});
					}else{
						window.top.alert(top.getMessage("FAILED_DELETE"),1);
					}
				},error:function(){
					alert(top.getMessage("FAILED_DELETE"),0);
				}
			}); 
		});
	};
	
	//必填设置
	var _changeIsMust = function(obj){
		var $blockItem = $(obj).parents(".block-item");
		var docBizSceConfig ={};
		var btspan = '<span class="i i-jiaobiao btspan0"></span><span class="btspan1">必填</span>';
		if ($(obj).is(":checked")) {
			docBizSceConfig.ifMustInput="1";
		} else {
			docBizSceConfig.ifMustInput="0";
		}
	
		docBizSceConfig.dime1 = kindNo;
		docBizSceConfig.scNo = $blockItem.parents(".item-div").data("scno");
		docBizSceConfig.docType = $blockItem.data("doctype");
		docBizSceConfig.docSplitNo = $blockItem.data("docno");
		var ajaxData = JSON.stringify(docBizSceConfig);
		//更新必填状态
		$.ajax({
			url : webPath+"/docBizSceConfig/updateAjax",
			data:{ajaxData:ajaxData},
			success:function(data){
				if(data.flag == "success"){
					if ($(obj).is(":checked")) {
						$blockItem .data("ismust", "1");
						
					} else {
						$blockItem.data("ismust", "0");
					}
				}else{
					window.top.alert(data.msg,1);
				}
			},error:function(){
				alert(top.getMessage("FAILED_UPDATE"),0);
			}
		}); 
	};
	return{
		init:_init,
		getDocTypeData:_getDocTypeData,
		changeIsMust:_changeIsMust,
		deleteDocTypeItem:_deleteDocTypeItem,
	};
}(window, jQuery);