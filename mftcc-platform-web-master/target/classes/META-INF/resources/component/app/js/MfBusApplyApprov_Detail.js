$(function(){
	var relNo = "cusNo-"+cusNo;
	//申请详情信息，根据期限类型展示月/日
	var termType = $("[name=termType]").val();
	if(termType!=undefined){
		if(termType=="1"){
			$("input[name=term]").parent().prev().html("&nbsp;个月");
		}else if(termType=="2"){
			$("input[name=term]").parent().prev().html("&nbsp;天");
		}
	}					
	//审批信息模块
	if(appSts != '0' && appSts != '1'){
		//获得审批信息
//		getSPInfo();
//		showWkfFlow($("#wj-modeler2"),appId);
		showWkfFlowVertical($("#wj-modeler2"),appId,"5","apply_approval");
	}else{
		$("#spInfo-div").remove();
	}
	
	setBlock("2","费用标准","busfee",webPath+'/mfBusAppFee/getListPage?appId='+appId);
	
	
	$.each(hisTaskList,function(i,hisTask){
		if(hisTask.state = 'completed'){
			
			if(hisTask.approveIdea !="同意" && hisTask.approveIdea !=""){
				var getUrl = hisTask.approveIdea.split('#')[1];
				var showType = hisTask.approveIdea.split('#')[0];
				var title = hisTask.description;
				if(typeof(getUrl) != "undefined" && typeof(getUrl) != null && getUrl != ''){
					
					setBlock(showType,title,hisTask.dbId,getUrl);							
					
				}
			}
		}
		
	}); 
});


function setBlock(showType,title,name,getUrl){
	var htmlStr = "";
	var collapseButtonHtml="<button  class=' btn btn-link pull-right formAdd-btn' data-toggle='collapse' data-target='#"+name+"'><i class='i i-close-up'></i><i class='i i-open-down'></i></button>";
	if(showType == "1"){
		var _name = name;
		if(name!=null&&name!=""){
			_name = "/"+name.substring(0,1).toLowerCase()+name.substring(1);
			_name =_name.replace("Action","");
		}
		/**
		 * 系统添加了映射地址，拼接的URL地址需要添加webPath，如果不添加拼接的URL地址不正确导致方法无法访问（404） 2018年5月4日17:49:11
		 */
		_name = webPath +"/"+_name;
		
//		var formInfo = "<div class='title'><span>"+title+"</span></div>"	
//		 + "<div class='content' name='"+name+"'></div>";
//		
//		if($(".dynamic-block").hasClass("add-block-here")){
//			htmlStr = htmlStr + "<div class='right-block-info'>" + formInfo + "</div>";
//			$(".add-block-here").find(".form-table").append(htmlStr);		
//			$(".add-block-here").removeClass("add-block-here");
//		}else{
//			htmlStr = htmlStr + "<div class='dynamic-block add-block-here'><div class='form-table'>"
//					+ "<div class='left-block-info' >" + formInfo + "</div>"
//					+ "</div></div>";
//			$(".block-new-block").before(htmlStr);
//			$(".block-new-block").before(clearDiv);
//		}
		
		htmlStr = htmlStr + "<div class='dynamic-block'>"
		+ "<div class='form-table'>"
		+ "<div class='title'><span class='formName'><i class='i i-xing blockDian'></i>"+title+"</span>"+collapseButtonHtml+"</div>"
		+ "<div class='content collapse in' id='"+name+"' name='"+name+"'>"
		+"<form  action='"+_name+"/updateAjaxByOne' id='"+name+"Ajax_updateByOne_action'>"+htmlStr+"</form>"
		+"</div>"
		+ "</div>"
		+ "</div>";
		$(".block-new-block").before(htmlStr);
		
	}else if(showType == "2"){
		var tableStr = "";
		htmlStr = htmlStr + "<div class='dynamic-block'>"
		+ "<div class='list-table'>"
		+ "<div class='title'><span><i class='i i-xing blockDian'></i>"+title+"</span>"+collapseButtonHtml+"</div>"
		+ "<div class='content collapse in' id='"+name+"' name='"+name+"'>"+tableStr+"</div>"
		+ "</div>"
		+ "</div>";
		$(".block-new-block").before(htmlStr);
	}
	$.ajax({
		url:getUrl,
		type:'post',
		dataType:'html',
		success:function(data){
			var $html = $(data);
			if(showType == "1"){
				var formStr = $html.find("form").prop("outerHTML");
				$(".content[name='"+name+"']").html(formStr);
			}else if(showType == "2"){
				var tableStr = $html.find("table").prop("outerHTML");
				$(".content[name='"+name+"']").html(tableStr);
			}
		},error:function(){
			alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
		}
	});
};

function setPleInfo(pleInfo){
	
	$(".ple-block").empty();
	var htmlStr = '<div class="i i-qiehuan qiehuan"  onclick="getPleInfo();" ></div>'
				+ '<div style="clear:both;"></div>'
				+ '<div style="margin-top:-10px;">'
				+ '<img src="${webPath}/themes/factor/images/pledge4.png" class="left-img pointer" onclick="getInfoForView(\'ple\',\''+appId +'\');" />'
				+ '<div class="block-content">'
				+ '<p class="content-title">'+ pleInfo.pledgeName + '<span class="margin_left_20">' + pleInfo.pledgeNoShow + '</span></p>'
				+ '<p><span class="myspan2">已引用</span><span class="myspan1">' + pleInfo.envalue +'</span><span class="myspan2">万</span>'
				+ '</p></div></div>'
				+'<div class="line-div"></div>';
	$(".ple-block").html(htmlStr);
	
};



function getSPInfo(){
		$.ajax({
			type: "post",
			data:{appNo:appId},
			dataType: 'json',
			url: webPath+"/wkfApprovalOpinion/getApplyApprovalOpinionList",
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				Wkf_zTreeNodes=data.zTreeNodes;
				Wkf_zTreeObj = $.fn.zTree.init($("#wfTree"), Wkf_setting, Wkf_zTreeNodes);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState+"-"+textStatus);
			}
		});
};



//费用信息编辑
function getFeeById(obj,url){
var $obj = $(obj);
top.obj = $obj.parents(".dynamic-block");
top.htmlStrFlag = false;//标识是否将客户表单信息的html字符串放在top下
top.htmlString = null;
top.window.openBigForm(url,'修改费用项',closeCallBack1);
};

function closeCallBack1(){
var $obj = $(top.obj);
if(top.htmlStrFlag){
	$obj.find(".content").empty();
	$obj.find(".content").html(top.htmlString);
}
};
		