var MfWorkFlowDyForm =  function(window, $) {
	
	var _setdynBlock = function(dynTableList){
		var dynHtmlStr = "";
		$.each(dynTableList, function(i, cusTable) {
			/**
			dynHtmlStr = "<div class=\"dynamic-block\" title=\""+cusTable.tableDes+"\" name=\""+cusTable.action+"\" data-sort=\""+cusTable.sort+"\" data-tablename=\""+cusTable.tableName+"\">"
					+ "<div class=\"list-table\">"
					+ "<div class=\"title\"><span class=\"formName\"><i class=\"i i-xing blockDian\"></i>"+cusTable.tableDes+"</span>"
					+ "<button class=\"btn btn-link formAdd-btn\" onclick=\""+cusTable.ext1+"();\" title=\"新增\"><i class=\"i i-jia3\"></i></button>"
					+ "<button class=\" btn btn-link pull-right formAdd-btn\" data-toggle=\"collapse\" aria-expanded='true' data-target=\"#"+cusTable.action+"\"></i><i class=\"i i-close-up\"><i class=\"i i-open-down\"></i></button>"
					+ "</div>"
					+ "<div class=\"content collapse in\" id=\""+cusTable.action+"\">"
					+ cusTable.htmlStr
					+ "</div>"
					+ "</div>"
					+ "</div>";
			**/
			dynHtmlStr = dynHtmlStr
			+ "<div class='dynamic-block' title='"+ cusTable.tableDes + "' name='"+ cusTable.action + "' data-sort='"+ cusTable.sort + "' data-tablename='"+ cusTable.tableName + "'>"
			+ "<div class='list-table'>"
			+ "<div class='title' >" 
			+"<span class='formName'><i class='i i-xing blockDian'></i>"+ cusTable.tableDes + "</span>"
			+ "<button class='btn btn-link formAdd-btn' onclick='"+ cusTable.ext1 +"();' title='新增'><i class='i i-jia3'></i></button>"
			+ "<button  class=' btn btn-link pull-right formAdd-btn' data-toggle='collapse' data-target='#"
			+ cusTable.action + "'><i class='i i-close-up'></i><i class='i i-open-down'></i></button>" 
			+ "</div>"
			+ "<div class='content collapse in' id='" + cusTable.action + "' >"
			+ cusTable.htmlStr 
			+ "</div>" 
			+ "</div>" 
			+ "</div>";
		});
		//console.log("dynHtmlStr:"+dynHtmlStr);
		$("#dynTable").before(dynHtmlStr);
	};
	
	var _addCallCenterTelVisit = function(){
		var url = webPath + "/workFlowDyForm/inputCallCenterTelVisit?cusNo="+cusNo+"&pactId="+pactId;
		top.window.openBigForm(url,"新增电话回访记录",function(){
			$.ajax({
				url : webPath + "/workFlowDyForm/getCallCenterTelVisitList?pactId="+pactId,
				data : {
					
				},
				type : 'POST',
				dataType : 'json',
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						//console.log($("#MfWorkFlowDyFormController").children("#tablist").html());
						$("#MfWorkFlowDyFormController").children("#tablist").remove();
						$("#MfWorkFlowDyFormController").children(".tableMessage").remove();
						$("#MfWorkFlowDyFormController").append(data.tableHtml);
					}
				},
				error : function() {
					LoadingAnimate.stop();
					alert(top.getMessage("ERROR_REQUEST_URL", url));
				}
			});
		});
	};
	
	function _ajaxUpdateCallCenterTelVisit(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam,pactId:pactId},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						window.top.alert(data.msg, 1);
						myclose_click();
					}else if(data.flag == "error"){
						window.top.alert(data.msg,0);
					}
				},error:function(data){
					LoadingAnimate.stop();
					window.top.alert(data.msg,0);
				}
			});
	
		}
	
	};
	
	var _ajaxDeleteCallCenterTelVisit = function(url){
		if (url.substr(0, 1) == "/") {
			url = webPath + url;
		} else {
			url = webPath + "/" + url;
		}
		alert(top.getMessage("CONFIRM_DELETE"),2,function(){
			$.ajax({
				url : url,
				type : "post",
				dataType : "json",
				success : function(data) {
					if (data.flag == "success") {
						window.top.alert(data.msg, 1);
						
						$.ajax({
							url : webPath + "/workFlowDyForm/getCallCenterTelVisitList?pactId="+pactId,
							data : {
								
							},
							type : 'POST',
							dataType : 'json',
							success : function(data) {
								LoadingAnimate.stop();
								if (data.flag == "success") {
									//console.log($("#MfWorkFlowDyFormController").children("#tablist").html());
									$("#MfWorkFlowDyFormController").children("#tablist").remove();
									$("#MfWorkFlowDyFormController").children(".tableMessage").remove();
									$("#MfWorkFlowDyFormController").append(data.tableHtml);
								}
							},
							error : function() {
								LoadingAnimate.stop();
								alert(top.getMessage("ERROR_REQUEST_URL", url));
							}
						});
						
					}
				},
				error : function() {
					alert(top.getMessage("ERROR_SERVER"), 0);
				}
			});
		});
	};
	
	var _getCallCenterTelVisitDetailPage = function(url){
		if (url.substr(0, 1) == "/") {
			url = webPath + url;
		} else {
			url = webPath + "/" + url;
		}	
		window.parent.openBigForm(url, '电话回访记录详情', function() {
			$.ajax({
				url : webPath + "/workFlowDyForm/getCallCenterTelVisitList?pactId="+pactId,
				data : {
					
				},
				type : 'POST',
				dataType : 'json',
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						//console.log($("#MfWorkFlowDyFormController").children("#tablist").html());
						$("#MfWorkFlowDyFormController").children("#tablist").remove();
						$("#MfWorkFlowDyFormController").children(".tableMessage").remove();
						$("#MfWorkFlowDyFormController").append(data.tableHtml);
					}
				},
				error : function() {
					LoadingAnimate.stop();
					alert(top.getMessage("ERROR_REQUEST_URL", url));
				}
			});
		});
	};
	//电核人名称数据源的选择
	var  _selectVerifyNamePersonList = function(){
		var 	cusNo = $("input[name=cusNo]").val();
		var obj= $('input[name=verifyName]');
	    var element = $(obj).attr('name');
	    $("input[name=verifyName]").parent().find('div').remove();
		$('input[name=verifyNameHid]').popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl : webPath+"/mfCusFamilyInfo/findListByPage?cusNo=" + cusNo+"&pactId="+pactId,// 请求数据URL
            valueElem:"input[name='callName']",//真实值选择器
            title: "选择人员",//标题
            changeCallback:function(elem){//回调方法
            	var tmpData = elem.data("selectData");
                BASE.removePlaceholder(obj);
				$("input[name=verifyName]").val(tmpData.relName);
				$("input[name=phoneNo]").val(tmpData.relTel);
			    $('.hidden-content').find('div[class=pops-value]').hide();
            },
            tablehead:{//列表显示列配置
                "relName":"人员名称",
                 "relTel":"联系方式"
            },
            returnData:{//返回值配置
                disName:"relName",//显示值
                value:"relName"//真实值
            }
        });
		$("input[name=verifyNameHid]").next().click();
	}
	
	return {
		ajaxUpdateCallCenterTelVisit : _ajaxUpdateCallCenterTelVisit,
		addCallCenterTelVisit : _addCallCenterTelVisit,
		ajaxDeleteCallCenterTelVisit : _ajaxDeleteCallCenterTelVisit,
		getCallCenterTelVisitDetailPage : _getCallCenterTelVisitDetailPage,
		setdynBlock : _setdynBlock,
		selectVerifyNamePersonList:_selectVerifyNamePersonList
	};
}(window, jQuery);