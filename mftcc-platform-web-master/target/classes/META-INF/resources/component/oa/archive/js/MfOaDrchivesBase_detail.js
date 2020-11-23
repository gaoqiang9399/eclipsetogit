;
var OaArchivesDetail = function (window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		_headImg();
		_cen();
	};
	//处理头像信息
	var _headImg = function(){
		var data=archivesbase.headImg;
		if(archivesbase.ifUploadHead!="1"){
			data = "themes/factor/images/"+archivesbase.headImg;
		} 
		data = encodeURIComponent(encodeURIComponent(data));
		document.getElementById('headImgShow').src=webPath+"/uploadFile/viewUploadImageDetail?srcPath="+data+"&fileName=op_user.jpg";
	};
	var _cen = function(){
		$(".myback").bind("click", function(event) {
			myclose_click();
		});
	};
	//基本信息表单切换
	var _updateArchivesBase = function() {
		url=webPath+"/mfOaArchivesBase/getByBaseId?baseId=" + baseId;
		if("undefined" != typeof from){
			url=webPath+"/mfOaEntryManagement/input?baseId=" + baseId+"&entryManagementId="+entryManagementId;
		}
		top.baseFlag = false;
		top.openBigForm(url,"基本信息登记",rollback,"80","80");
	};
	//点击fam“+” 弹出新表单
	var _familyadd = function(url) {
		if("undefined" == typeof url){
			url = webPath+"/mfOaArchivesFamily/input?baseId="+baseId;
		}
		top.familyFlag = false;
		top.openBigForm(url,"家庭成员信息登记", function(){
			if(top.familyFlag){
				$("#MfOaArchivesFamilyAction").html(top.familyTableHtml);
			}
		},"80","80");
	};
	//点击work“+” 弹出新表单
	var _workadd = function(url) {
		if("undefined" == typeof url){
			url = webPath+"/mfOaArchivesWork/input?baseId="+baseId;
		}
		top.workFlag = false;
		top.openBigForm(url,"工作经历信息登记", function(){
			if(top.workFlag){
				$("#MfOaArchivesWorkAction").html(top.workTableHtml);
			}
		},"80","80");
	};
	//点击edu“+” 弹出新表单
	var _educationadd = function(url) {
		if("undefined" == typeof url){
			url = webPath+"/mfOaArchivesEducation/input?baseId="+baseId;
		}
		top.educationFlag = false;
		top.openBigForm(url,"教育经历信息登记", function(){
			if(top.educationFlag){
				$("#MfOaArchivesEducationAction").html(top.educationTableHtml);
			}
		},"80","80");
	};
	//点击reward“+” 弹出新表单
	var _rewardsadd = function(url) {
		if("undefined" == typeof url){
			url = webPath+"/mfOaArchivesRewards/input?baseId="+baseId;
		}
		top.rewardsFlag = false;
		top.openBigForm(url,"奖惩记录信息登记", function(){
			if(top.rewardsFlag){
				$("#MfOaArchivesRewardsAction").html(top.rewardsTableHtml);
			}
		},"80","80");
	};
	//点击work删除记录
	var _workdelete = function(obj,url) {
		window.top.alert(top.getMessage("CONFIRM_DELETE"),2,function(){
			jQuery.ajax({
				url:url,
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						window.top.alert(top.getMessage("SUCCEED_OPERATION"),1);
						$("#MfOaArchivesWorkAction").html(data.workTableHtml);
					}else if(data.flag == "error"){
						alertFlag.Alert(data.msg);
					}
				},error:function(data){
					window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		});
	};
	//点击EDU“垃圾桶”删除记录
	var _educationdelete = function(obj,url) {
		window.top.alert(top.getMessage("CONFIRM_DELETE"),2,function(){
			jQuery.ajax({
				url:url,
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						window.top.alert(top.getMessage("SUCCEED_OPERATION"),1);
						$("#MfOaArchivesEducationAction").html(data.educationTableHtml);
					}else if(data.flag == "error"){
						alertFlag.Alert(data.msg);
					}
				},error:function(data){
					window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		});
	};
	//点击red“垃圾桶”删除记录
	var _rewardsdelete = function(obj,url) {
		window.top.alert(top.getMessage("CONFIRM_DELETE"),2,function(){
			jQuery.ajax({
				url:url,
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						window.top.alert(top.getMessage("SUCCEED_OPERATION"),1);
						$("#MfOaArchivesRewardsAction").html(data.rewardsTableHtml);
					}else if(data.flag == "error"){
						alertFlag.Alert(data.msg);
					}
				},error:function(data){
					window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		});
	};
	//点击fam删除记录
	var _familydelete = function(obj,url) {
		window.top.alert(top.getMessage("CONFIRM_DELETE"),2,function(){
			jQuery.ajax({
				url:url,
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						window.top.alert(top.getMessage("SUCCEED_OPERATION"),1);
						$("#MfOaArchivesFamilyAction").html(data.familyTableHtml);
					}else if(data.flag == "error"){
						alertFlag.Alert(data.msg);
					}
				},error:function(data){
					window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		});
	};

	
	//回调函数
	function rollback(){
		if(top.baseFlag ){
			if(top.mfOaArchivesBase!=""){
				$("select").removeAttr("disabled");
				_freshBaseDiv();
				_freshheadDiv("#headMessage [name]");
				$("#headMessage [name=education]").text(top.mfOaArchivesBase.education);
				$("#headMessage [name=birthday]").text(_formatDate(top.mfOaArchivesBase.birthday));
				$("#headMessage [name=email]").text(top.mfOaArchivesBase.email);
			}
		}
	}
	var _freshBaseDiv = function(obj) {
		$("#MfOaArchivesBaseAction").html(top.htmlStr);
		dblclickflag();
	};
	var _freshheadDiv = function(obj) {
		$(obj).each(function(){
			$(this).text(top.mfOaArchivesBase[$(this).attr("name")]);
		});
	};
	
	var _formatDate = function(startDate){
		if(startDate!=""){			
			var date = startDate.substr(0,4)+"-"+startDate.substr(4,2)+"-"+startDate.substr(6,2);
			return date;
		}else{
			return ;
		}
	};
	
	
	var _bindInsertAjax = function(obj,button_class){
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				$.ajax({
					url : url,
					data : {
						ajaxData:dataParam
						},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
								window.top.alert(data.msg, 1);
								$(button_class)[0].style.display="none";
								$(obj+" input[type='text']").removeClass("type_text");
								$(obj+" input[type='text']").attr("readonly",true);
						} else {
							window.top.alert(data.msg, 0);
						}
					},
					error : function() {
						loadingAnimate.stop();
						
					}
				});
			}
		//});
	};
	return {
		init:_init,
		bindInsertAjax:_bindInsertAjax,
		updateArchivesBase:_updateArchivesBase,
		familyadd:_familyadd,
		familydelete:_familydelete,
		workadd:_workadd,
		workdelete:_workdelete,
		educationadd:_educationadd,
		educationdelete:_educationdelete,
		rewardsadd:_rewardsadd,
		rewardsdelete:_rewardsdelete,
	};
}(window,jQuery);
function uploadHeadImg(){
	window.parent.openBigForm(webPath+'/mfOaArchivesBase/uploadHeadImg?baseId='+baseId,'员工头像',showNewHeadImg);
};
function showNewHeadImg(){
	$.ajax({
		url:webPath+"/mfOaArchivesBase/getIfUploadHeadImg",
		data:{baseId:baseId},
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.flag == "1"){
				data = encodeURIComponent(encodeURIComponent(data.headImg));
				document.getElementById('headImgShow').src=webPath+"/uploadFile/viewUploadImageDetail?srcPath="+data+"&fileName=op_user.jpg";;
				top.view.showNewHeadImg();
			}else{
				data = "themes/factor/images/"+data.headImg;
				document.getElementById('headImgShow').src=webPath+"/uploadFile/viewUploadImageDetail?srcPath="+data+"&fileName=op_user.jpg";;
				top.view.showNewHeadImg();
			}
		},error:function(){
			alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
		}
	});
	delFile();
};
//删除文件
function delFile(){
	var srcPath = "/tmp/";
	$.ajax({
		url:webPath+"/uploadFile/delFile",
		data:{srcPath:srcPath},
		type:'post',
		dataType:'json',
		success:function(data){
			
		},error:function(){
			alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
		}
	});
};