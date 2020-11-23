;
var ArchiveInfoMain_fileList = function(window, $) {
	var _init = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		//
		template_init();
		
		if(zTreeNodes.length == 0 && successTemplateList.length==0){
			$("#paperInfo").hide();
			if(optType=="01" || optType=="03"){//归档
				$("#fileArchive").removeClass("hide");
				$(".file-archive").addClass("hide");
			}else if(optType=="02"){//封档
				$("#fileSeal").removeClass("hide");
				$(".file-seal").addClass("hide");
			}
		}else{
			//保管新版选择组件
			$('input[name=paperKeeperName]').popupList({
				searchOn: true, //启用搜索
				multiple: false, //false单选，true多选，默认多选
				ajaxUrl:webPath+"/sysUser/findByPageForSelectAjax",//请求数据URL
				handle:BASE.getIconInTd($("input[name=paperKeeperName]")),//其他触发控件
				valueElem:"input[name=paperKeeperNo]",//真实值选择器
				title: "选择保管员",//标题
				changeCallback:function(elem){//回调方法
					BASE.removePlaceholder($("input[name=paperKeeperName]"));
				},
				tablehead:{//列表显示列配置
					"opName":"操作员名称",
					"opNo":"操作员编号"
				},
				returnData:{//返回值配置
					disName:"opName",//显示值
					value:"opNo"//真实值
				}
			});
			if(optType=="01" || optType=="03"){//归档
				$(".file-archive").removeClass("hide");
				$("#paperInfo").show();
			}else if(optType=="02"){//封档
				$(".file-seal").removeClass("hide");
			}
		}
	};
	
	//归档操作
	var _fileArchive = function(){
		alert(top.getMessage("CONFIRM_OPERATION","归档"),2,function(){
			var paperAddr = $("input[name=paperAddr]").val();//纸质文件存储位置
			var paperNo = $("input[name=paperNo]").val();//纸质文件编号
			var paperKeeperName = $("input[name=paperKeeperName]").val();//保管人名称
			var paperKeeperNo = $("input[name=paperKeeperNo]").val();//保管人名称
//			archiveBusinessInfo =  JSON.parse(archiveBusinessInfo); 
			if(paperAddr!=""){
				archiveBusinessInfo.paperAddr = paperAddr;
			}
			if(paperNo!=""){
				archiveBusinessInfo.paperNo = paperNo;
			}
			if(paperKeeperName!=""){
				archiveBusinessInfo.paperKeeperName = paperKeeperName;
			}
			if(paperKeeperNo!=""){
				archiveBusinessInfo.paperKeeperNo = paperKeeperNo;
			}
			archiveBusinessInfo = JSON.stringify(archiveBusinessInfo);
			 $.ajax({
					url:webPath+"/archiveInfoMain/archiveAjax",
					data:{ajaxData:archiveBusinessInfo,isDeleteBusinessDoc:false,flag:flag},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
						LoadingAnimate.start();
					},success:function(data){
						if(data.flag == "success"){
							top.alert(data.msg, 3);
							myclose_click(); 
						}else{
							alert(data.msg,0);
						}
					},error:function(data){
					 	
					},complete: function(){
	  					LoadingAnimate.stop();
	  				}
				});
			});
		};
		
	//封档操作
	var _fileSeal =function(){
		alert(top.getMessage("CONFIRM_OPERATION","封档"),2,function(){
			 $.ajax({
					url:webPath+"/archiveInfoMain/sealAjax",
					data:{ajaxData:JSON.stringify(archiveBusinessInfo)},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
						LoadingAnimate.start();
					},success:function(data){
						if(data.flag == "success"){
							alert(data.msg,1);
							myclose();
						}else{
							alert(data.msg,0);
						}
					},error:function(data){
					 	
					},complete: function(){
	  					LoadingAnimate.stop();
	  				}
				});
			});
		};
		
		var template_init = function(){
			if (successTemplateList.length > 0) {
				var htmlStr = getTemplateBizConfigHtml(successTemplateList);
				$("#bizConfigs").html(htmlStr);
			} else {
				$("#template_div").hide();
			}
			
		};
		
		/** 循环添加所有的业务文档 */
		var getTemplateBizConfigHtml = function(mfTemplateBizConfigList) {
			// 循环产品下的模板项
			var subHtmlStr = "";
			$.each(mfTemplateBizConfigList, function(i, bizConfig) {
				var htmlTmp = getTemplateItemHtml(bizConfig);
				subHtmlStr = subHtmlStr + htmlTmp;
			});
			var htmlStr = '<div class="item-div">' + subHtmlStr + '</div>';
			return htmlStr;
		};

		/** 业务文档html */
		var getTemplateItemHtml = function(bizConfig) {
			var imgClass = "item-word";
			if(bizConfig.templateSuffix=="2"){
				imgClass = "item-excel";
			}else if(bizConfig.templateSuffix=="3"){
				imgClass = "item-pdf";
			}
			var htmlStr = '<div id="' + bizConfig.templateBizConfigId + '" class="block-item">';
			htmlStr += '     <div class="item-title ' + imgClass + '" onclick="templateIncludePage.openTemplate(\'' + bizConfig.templateBizConfigId + '\');">';
			htmlStr += '       <span>' + bizConfig.templateNameZh + '</span>';
			htmlStr += '     </div>';
			htmlStr += '   </div>';
			return htmlStr;
		};

	
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		fileArchive : _fileArchive,
		fileSeal : _fileSeal,
	};
}(window, jQuery);