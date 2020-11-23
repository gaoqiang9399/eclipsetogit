;
var MfRepayPlan_RepayPlan = function(window,$){
	var _init = function(){
		$("body").mCustomScrollbar({
			advanced:{ 
				updateOnBrowserResize:true 
			},
			autoHideScrollbar: true
		});
		//处理上传过的财务报表列表
		//上传操作	
		var uploader = WebUploader.create({
			auto : true,
			// swf文件路径
			swf : webPath+'/UIplug/webuploader/js/Uploader.swf',
			// 文件接收服务端。
			server : webPath+'/mfRementpayPlan/uploadAjax?cusNo='+cusNo,
			// 选择文件的按钮。可选。
			// 内部根据当前运行是创建，可能是input元素，也可能是flash.
			pick : '#picker',
			// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
			fileVal : 'cusFinUpload',
			resize : false,
			fileNumLimit : 1,   //验证文件总数量, 超出则不允许加入队列
			accept : {
				title : 'Excl',
				extensions : 'xls,xlsx',
				mimeTypes : '.xls,.xlsx'
			}
		});
		//当文件被加入队列之前触 此事件的handler返回值为false，则此文件不会被添加进入队列
		uploader.on('beforeFileQueued', function(file) {
			$("#showMsg").empty();
			$("input[name=uploadFile]").val(file.name);
		});
		//当文件被加入队列以后触发
		uploader.on('fileQueued', function(file) {
		});
		//当文件被移除队列后触发
		uploader.on('fileDequeued', function(file) {
			$("#showInfo-div").empty();
			$("#"+file.id).remove();
		});
		//上传过程中触发，携带上传进度
		uploader.on('uploadProgress', function(file,percentage) {
			LoadingAnimate.start();
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
			LoadingAnimate.stop();
			uploader.removeFile(file);
		    if(data.flag=="success"){
		    	var mfRepayPlans = data.mfRepayPlans;
		    	$("#dataMsg").html(data.dataMsg);
		    	$("#repayPlan_tab").html("");
		    	$.each(mfRepayPlans,function(i,repayPlan){
		    		$("#repayPlan_tab").append(
    			       "<tr id='tr_"+repayPlan.termNum+"'>"+
							"<td align='center'>"+
								"<span class='tab_span' id='spanTermNum_"+repayPlan.termNum+"'>"+repayPlan.termNum+"</span>"+
								"<input type='text' name='termNum"+repayPlan.termNum+"' id='termNum_"+repayPlan.termNum+"' class='repayInput' value='"+repayPlan.termNum+"'>"+
							"</td>"+
							"<td align='center'>"+
								"<span class='tab_span' id='spanPlanBeginDate_"+repayPlan.termNum+"'>"+repayPlan.planBeginDateFormat+"</span>"+
								"<input type='text' name='planBeginDate"+repayPlan.termNum+"' id='planBeginDate_"+repayPlan.termNum+"' class='repayInput' value='"+repayPlan.planBeginDateFormat+"' datatype='6'>"+
							"</td>"+
							"<td align='center' id='tdPlanEndDate_"+repayPlan.termNum+"' ondblclick='repayPlan_List.showEndDateInputValue(this)'>"+
								"<span class='tab_span' id='spanPlanEndDate_"+repayPlan.termNum+"'>"+repayPlan.planEndDateFormat+"</span>"+
								"<input type='text' name='planEndDate"+repayPlan.termNum+"' id='planEndDate_"+repayPlan.termNum+"' datatype='6' class='repayInput datelogo BOTTOM_LINE' value='"+repayPlan.planEndDateFormat+"' onfocus='fPopUpCalendarDlg({choose:repayPlan_List.changeEndDateValue});'  onmousedown='enterKey()' onkeydown='enterKey();' datatype='6' mustinput='1' maxlength='10'>"+
							"</td>"+
							"<td align='center'>"+
								"<span class='tab_span' id='spanRepayDate_"+repayPlan.termNum+"'>"+repayPlan.planEndDateFormat+"</span>"+
								"<input type='text' name='repayDate"+repayPlan.termNum+"' id='repayDate_"+repayPlan.termNum+"' class='repayInput datelogo BOTTOM_LINE' value='"+repayPlan.planEndDate+"' datatype='6' mustinput='1' maxlength='10'>"+
							"</td>"+
							"<td  id='tdRepayPrcp_"+repayPlan.termNum+"'  ondblclick='repayPlan_List.showRepayPrcpInputValue(this)'>"+
								"<span class='tab_span money_right' id='spanRepayPrcp_"+repayPlan.termNum+"'>"+repayPlan.repayPrcpFormat+"</span>"+
								"<input type='text' name='repayPrcp"+repayPlan.termNum+"' id='repayPrcp_"+repayPlan.termNum+"' class='repayInput datelogo BOTTOM_LINE' value='"+repayPlan.repayPrcp+"'   onblur='repayPlan_List.changeRepayPrcpValue(this);' onkeyup=\"value=value.replace(/[^\\d.]/g,'')\">"+
							"</td>"+
							"<td  id='tdRepayIntst_"+repayPlan.termNum+"' ondblclick='repayPlan_List.showRepayIntsInputValue(this)'>"+
								"<span class='tab_span money_right' id='spanRepayIntst_"+repayPlan.termNum+"'>"+repayPlan.repayIntstFormat+"</span>"+
								"<input type='text' name='repayIntst"+repayPlan.termNum+"' id='repayIntst_"+repayPlan.termNum+"' class='repayInput datelogo BOTTOM_LINE' value='"+repayPlan.repayIntst+"'   onblur='repayPlan_List.changeRepayIntstValue(this);' onkeyup=\"value=value.replace(/[^\\d.]/g,'')\">"+
							"</td>"+
							"<td >"+
								"<span class='tab_span money_right' id='spanFeeSum_"+repayPlan.termNum+"'>"+repayPlan.feeSumFormat+"</span>"+
								"<input type='text' name='feeSum"+repayPlan.termNum+"' id='feeSum_"+repayPlan.termNum+"' class='repayInput' value='"+repayPlan.feeSum+"'>"+
							"</td>"+
							"<td >"+
								"<span class='tab_span money_right' id='spanRepayPrcpIntstSum_"+repayPlan.termNum+"'>"+repayPlan.repayPrcpIntstSumFormat+"</span>"+
								"<input type='text' name='repayPrcpIntstSum"+repayPlan.termNum+"' id='repayPrcpIntstSum_"+repayPlan.termNum+"' class='repayInput' value='"+repayPlan.repayPrcpIntstSum+"'>"+
							"</td>"+
							"<td >"+
								"<span class='tab_span money_right' id='spanRrepayPrcpBalAfter_"+repayPlan.termNum+"'>"+repayPlan.repayPrcpBalAfterFormat+"</span>"+
								"<input type='text' name='repayPrcpBalAfter"+repayPlan.termNum+"' id='repayPrcpBalAfter_"+repayPlan.termNum+"' class='repayInput' value='"+repayPlan.repayPrcpBalAfter+"' >"+
							"</td>"+
							"<td align='center' style='display:none'>"+
								"<input type='text' name='repayIntstOrig"+repayPlan.termNum+"' id='repayIntstOrig_"+repayPlan.termNum+"'  value='"+repayPlan.repayIntstOrig+"'>"+
							"</td>"+
    			       	  "</tr>"   
			    	  );
			    });

		    }else if(data.flag=="error"){
		    	LoadingAnimate.stop();
		    	top.isUpload = false; //财务报表上传成标志
		    	$("#showMsg").empty();
		    	$("#showMsg").html(data.resMsg);
		    	uploader.removeFile(file.id);
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
	};
	
	//新增
	var _newFinMain = function(){
		top.addFlag = false;
		window.top.window.showDialog(webPath+'/mfRementpayPlan/input?cusNo='+cusNo,'编辑报表',70,80,function(){
			if(top.addFlag){
				window.location.reload();
			}
		});
	};
	
	//编辑
	var _getFinMain = function(obj,url){
		top.updateFlag = false;
		window.top.window.showDialog(url,'编辑报表',70,80,function(){
			if(top.updateFlag){
				window.location.reload();
			}
		});
	};
	

	//数据确认
	var _confirmFinMain = function(obj,url){
		LoadingAnimate.start();
		var parm = "?"+url.split("?")[1];
		$.ajax({
			type:"post",
			url:webPath+"/mfRementpayPlan/checkFinDataAjax"+parm,
			dataType:"json",
			success:function(data){
				if(data.flag=="success"){
					if(data.checkFlag == "success"){
						alert(top.getMessage("CONFIRM_OPERATION","数据确认"),2,function(){
							LoadingAnimate.start();
							cusFinMainList.doCofrimData(url);
						});
					}else{
						LoadingAnimate.stop();
						alert(top.getMessage("CONFIRM_FINC_VERIFY"),2,function(){
							LoadingAnimate.start();
							cusFinMainList.doCofrimData(url);
						});
						
					}
				}else if(data.flag=="error"){
					LoadingAnimate.stop();
					alert(top.getMessage("ERROR_FINC_VERIFY"),0);
				}
			},error:function(){
				LoadingAnimate.stop();
				window.top.alert(top.getMessage("ERROR_FINC_VERIFY"),0);
			}
		});
	};

	

	
	//下载模板
	var _exportExcel = function(downType){
		LoadingAnimate.start();
		$.ajax({
			url:webPath+"/mfRementpayPlan/exportExcelAjax",
			data:{
				downType:downType,
				cusNo:cusNo
			},
			dataType:"json",
			type:"POST",
			success:function(data){
				LoadingAnimate.stop();
				if(data.flag == "success"){
					if(data.exportFlag == "success"){
						window.top.location.href = encodeURI(webPath+"/docUpLoad/getFileDownload_new?path="+data.path+"&cusNo="+cusNo+"&downType="+downType);
					}else{
						alert(data.msg,0);
					}
				}else{
					alert(data.msg,0);
				}
			},error:function(){
				LoadingAnimate.stop();
				alert(data.msg,0);
			}
		});
	};
	
	var _uploadFinReport = function(){
		$("#picker").find("input.webuploader-element-invisible").click();
	}
	
	return{
		init:_init,
		newFinMain:_newFinMain,
		getFinMain:_getFinMain,
		confirmFinMain:_confirmFinMain,
		exportExcel:_exportExcel,
		uploadFinReport:_uploadFinReport
	};
}(window,jQuery);