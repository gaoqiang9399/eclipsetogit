var year = [ "2008", "2009", "2010", "2011", "2012", "2013", "2014",
			  "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022",
 		      "2023", "2024","2025" ];
var selectYearHtml = '<select name="finRptYear">';
var i;
for ( i = 0; i < year.length; i++) {
	selectYearHtml = selectYearHtml + '<option value="' + year[i] + '">'+ year[i] + '</option>';
}
selectYearHtml = selectYearHtml + '</select>年';

var month = [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11","12" ];
var selectMonthHtml = '<select name="finRptMonth" style="margin-left:10px;">';
for ( i = 0; i < month.length; i++) {
	selectMonthHtml = selectMonthHtml + '<option value="' + month[i] + '">'+ month[i] + '</option>';
}
selectMonthHtml = selectMonthHtml + '</select>月';

var quater = [ "01", "02", "03", "04" ];
var selectQuaterHtml = '<select name="finRptQuater" style="margin-left:10px;">';
for ( i = 0; i < quater.length; i++) {
	selectQuaterHtml = selectQuaterHtml + '<option value="' + quater[i] + '">'+ quater[i] + '</option>';
}
selectQuaterHtml = selectQuaterHtml + '</select>季';
$(function() {  
	$("input[name=finRptDate]").css("display", "none");
	finRptTypeChange();
	ifAudChange();
//	$(".scroll-content").mCustomScrollbar({
//		advanced : {
//			theme : "minimal-dark",
//			updateOnContentResize : true
//		}
//	});
	myCustomScrollbarForForm({
		obj:".scroll-content",
		advanced : {
			theme : "minimal-dark",
			updateOnContentResize : true
		}
	});
	var cusNo = $("input[name=cusNo]").val();
	var cusName = $("input[name=cusName]").val(); 
	var finRptType = $("select[name=finRptType]").val();  //报表类型
	var finRptDate = null;
	
	//上传操作	
	var uploader = WebUploader.create({
		auto : true,
		// swf文件路径
		swf : '${webPath}/UIplug/webuploader/js/Uploader.swf',
		// 文件接收服务端。
		server : webPath+'/cusFinUploadFiles/uploadAjax?cusNo='+cusNo,
		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick : '#picker',
		// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
		fileVal : 'cusFinUpload',
		resize : false,
		fileNumLimit : 3,
		accept : {
			title : 'Excl',
			extensions : 'xls,xlsx',
			mimeTypes : '.xls,.xlsx'
		}
	});
	var $list = $("#thelist"),$finData = $("#finData").find("input[name=finData]");
	//当文件被加入队列之前触 此事件的handler返回值为false，则此文件不会被添加进入队列
	uploader.on('beforeFileQueued', function(file) {});
	//当文件被加入队列以后触发
	uploader.on('fileQueued', function(file) {
	    $list.append( '<div id="' + file.id + '" class="item">' +
					        '<div class="info">' + file.name + '</div>' +
					        '<a class="delFile">删除</a>'+
					    '</div>' );
	    $(".delFile").click(function(){
	    	var fileName = $('#'+file.id ).data("fileName");
	    	if(fileName===undefined){
	    		uploader.removeFile(file.id);
	    	}else{
	    		$.ajax({
					type:"post",
					url:webPath+"/cusFinUploadFiles/deletUploadFileAjax",
					async:false,
					data:{ajaxData:fileName},
					success:function(data){
						if(data.flag=="success"){
							alert(data.msg,1);
							uploader.removeFile(file.id);
						}else if(data.flag=="error"){
							alert(data.msg,0);
						}
					},error:function(){
						alert(top.getMessage("FAILED_DELETE"),0);
					}
				});
	    	}
	    });
	});
	//当文件被移除队列后触发
	uploader.on('fileDequeued', function(file) {
		$("#showInfo-div").empty();
		$("#"+file.id).remove();
	});
	//上传过程中触发，携带上传进度
	uploader.on('uploadProgress', function(file,percentage) {
	    var $li = $( '#'+file.id ),
	        $percent = $li.find('.progress .progress-bar');
	    // 避免重复创建
	    if (!$percent.length ) {
	        $percent = $('<div class="progress progress-striped active">' +
	          '<div class="progress-bar" role="progressbar" style="width: 0%">' +
	          '</div>' +
	        '</div>').appendTo( $li ).find('.progress-bar');
	    }
	    $percent.css( 'width', percentage * 100 + '%' );
	});
	//当文件上传成功时触发
	uploader.on('uploadSuccess', function(file,data) {
	    if(data.flag=="success"){
	    	$(".submitdata").addClass("success");
	    	var fileName = data.fileName;
	    	var finDataVal = $finData.val();
	    	if(finDataVal!==""){
	    		$finData.val(finDataVal+"@@@@@"+fileName);
	    	}else{
	    		$finData.val(fileName);
	    	}
	    	$('#'+file.id ).data("fileName",fileName);
	    }else if(data.flag=="error"){
	    	$("#showInfo-div").html(data.resMsg);
	    	if(data.resMsg != null && data.resMsg != ""){
	    		dialog({
					title:'财务报表表内关系检查结果',
					id:"showInfoDialog",
					backdropOpacity:0,
					height:500,
					content:$("#showInfo-div")
				}).showModal();
	    	}
	    	$('#'+file.id).find('p.state').text('上传出错');
	    }
	});
	//当文件上传出错时触发
	uploader.on('uploadError', function(file) {
	    $('#'+file.id ).find('p.state').text('上传出错');
	});
	//不管成功或者失败，文件上传完成时触发
	uploader.on('uploadComplete', function(file) {
	    $( '#'+file.id ).find('.progress').fadeOut();
	});
	
	//保存
	$(".insertAjax").click(function(){
		//当表单保存时，判断是否上传了财务报表，
		//	如果上传了必须是检查通过的才可以保存，如果没有上传，继续往下走
		var checkMsg = $("#showInfo-div").html();
		if(checkMsg != ""){
			alert("您上传的财务报表必须满足表内的关系,才可以保存",0);
			return;
		}
		if($("select[name=finRptType]").val() == "1"){//月报
			$("input[name=finRptDate]").val($("select[name=finRptYear]").val()+$("select[name=finRptMonth]").val());
		}else if($("select[name=finRptType]").val() == "2"){//季报
			$("input[name=finRptDate]").val($("select[name=finRptYear]").val()+$("select[name=finRptQuater]").val());
		}else if($("select[name=finRptType]").val() == "3"){//年报
			$("input[name=finRptDate]").val($("select[name=finRptYear]").val());
		}
		finRptDate = $("input[name=finRptDate]").val();
		var flag = submitJsMethod($("#cusFinInsertForm").get(0), '');
		if(flag){
			var url = $("#cusFinInsertForm").attr("action");
			var dataParam = JSON.stringify($("#cusFinInsertForm").serializeArray()); 
			LoadingAnimate.start();
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						 top.addFlag = true;
						 uploadAndSubmitData();
						 $(top.window.document).find("#showDialog .close").click();
						 window.location.reload();
					}else if(data.flag == "error"){
						alert(top.getMessage("FAILED_OPERATION"," "),0);
					}else if(data.flag == "exist"){
						window.top.alert(data.msg,2,function(){
			      			jQuery.ajax({
			      				url:webPath+'/cusFinMain/insertAjax?ifcover=1',
			      				data:{
			      					ajaxData:dataParam
			      				},
			      				type:"POST",
			      				dataType:"json",
			      				beforeSend:function(){  
			      				},success:function(data){
			      					if(data.flag == "success"){
			      						uploadAndSubmitData();
			      						$(top.window.document).find("#showDialog .close").click();
			      						window.location.reload();
			      					}else if(data.flag == "error"){
			      						alertFlag.Alert(data.msg);
			      					}
			      				},error:function(data){
			      					window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
			      				}
			      			});
			      		});
					}
				},error:function(data){
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	});
	
	//保存表单时保存上传的excel文件中的数据到数据库中
	var uploadAndSubmitData = function() {
		finRptType = $("select[name=finRptType]").val();
    	var finDataVal = $finData.val();
    	var url =webPath+"/cusFinMain/insertFormMoreExcelAjax";
         $.ajax({
         	type:"post",
         	url:url,
         	async:false,
         	data:{
         		allPath:finDataVal,
         		cusNo:cusNo,
         		finRptType:finRptType,
         		finRptDate:finRptDate,
         		cusName:cusName
         	},
         	success:function(data){
         		if(data.flag=="success"){
         			top.addFlag = true;
         			 //触发回调函数
					 $(top.window.document).find("#showDialog .close").click();
         		}else if(data.flag=="error"){
         			alert(data.msg,0);
         		}
         	}
         });
    };
}); 

//报表类型改变时动态改变报表日期
function finRptTypeChange() {
	var $finRptDateParent = $("input[name=finRptDate]").parent();
	$finRptDateParent.empty();
	$finRptDateParent.append('<input name="finRptDate" style="display:none;">');
	if ($("select[name=finRptType]").val() == "1") {//月报
		$finRptDateParent.append(selectYearHtml + selectMonthHtml);

	} else if ($("select[name=finRptType]").val() == "2") {//季报
		$finRptDateParent.append(selectYearHtml + selectQuaterHtml);

	} else if ($("select[name=finRptType]").val() == "3") {//年报
		$finRptDateParent.append(selectYearHtml);
	}
	$("select[name=finRptYear]").val(yearThis);
};

function ajaxInsertCallBack() {
	var obj = new Object();
	obj.finRptType = $("select[name=finRptType]").val();
	obj.finRptSts = '0';
	obj.finRptDate = $("input[name=finRptDate]").val();
	obj.cusNo = $("input[name=cusNo]").val();
	obj.cusName = $("input[name=cusName]").val();
	obj.accRule = $("select[name=accRule]").val();
	top.flag = true;
	top.obj = obj;
};
//是否审计变化时
function ifAudChange() {
	if ($("select[name=ifAud]").val() == "1") {
		$("input[name=audOrg]").attr("mustinput", "1");	
		var audOrgtext	= $("input[name=audOrg]").parents("tr").find(".control-label   ").text(); 
		$("input[name=audOrg]").parents("tr").find(".control-label  ").html('<font color="#FF0000">*</font>'+audOrgtext); 						
		$("input[name=audIdea]").attr("mustinput", "1");
		var audIdeatext = $("input[name=audIdea]").parents("tr").find(".control-label ").text();
		$("input[name=audIdea]").parents("tr").eq(0).find(".control-label ").html('<font color="#FF0000">*</font>' + audIdeatext);
		$("input[name=audDate]").attr("mustinput", "1");
		var audDatetext = $("input[name=audDate]").parents("tr").find(".control-label ").text();
		$("input[name=audDate]").parents("tr").eq(0).find(".control-label ").html('<font color="#FF0000">*</font>' + audDatetext);
	} else {
		$("input[name=audOrg]").attr("mustinput", "0");
		$("input[name=audOrg]").parents("tr").eq(0).find(".control-label ").find("font").remove();

		$("input[name=audIdea]").attr("mustinput", "0");
		$("input[name=audIdea]").parents("tr").eq(0).find(".control-label ").find("font").remove();

		$("input[name=audDate]").attr("mustinput", "0");
		$("input[name=audDate]").parents("tr").eq(0).find(".control-label ").find("font").remove();
	}
};