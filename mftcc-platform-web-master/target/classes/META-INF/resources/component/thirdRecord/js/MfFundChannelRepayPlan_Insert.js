;
var MfFundChannelRepayPlan_Insert = function(window,$){
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
			server : webPath+'/mfFundChannelRepayPlan/uploadAjax?cusNo='+cusNo,
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
		    	$("#plan_content").html("");
		    	$("#plan_content").html(data.htmlStr);
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
		
		$("#plan_content").html(ajaxData.html);
		$("#changediv").html('<table style ="font-size:14px;">'
                      +'<tr><td>累计还款总额：&nbsp;&nbsp;'+ajaxData.totalAmt+'元</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>支付息款：&nbsp;&nbsp;'+ajaxData.totalIntstAmt+'元</td></tr></table>');
		_openList('#repayPlanTrial');
		/*var calculateFlag= ajaxData.calculateFlag;
		if(!calculateFlag){
			$("#mfRepayPlanTrial_button").attr("disabled", true);// 拒绝业务
			$("#mfRepayPlanTrial_button").css("background", "rgb(122,122,122)");// 拒绝业务
		}*/
	};
	
	//下载模板
	var _exportPlanList = function(downTypeArgs){
		var downType = '';
		LoadingAnimate.start();
		$.ajax({
			//url:webPath+"/mfRementpayPlan/exportExcelAjax",
			url:webPath+"/mfFundChannelRepayPlan/exportExcelAjax",
			data:{
				//downType:downType,
				pactId:pactId
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
	
	//初始化还款计划列表
	var _openList = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {
					ajaxData : dataParam
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {						
						 $("#plan_content").html(data.htmlStr);
						//页面显示累计还款总额及支付息款 
						$("#changediv").html('');
						$("#changediv").html('<table style ="font-size:14px;">'
		                              +'<tr><td>累计还款总额：&nbsp;&nbsp;'+data.totalAmt+'元</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>支付息款：&nbsp;&nbsp;'+data.totalIntstAmt+'元</td></tr></table>');
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function() {
					loadingAnimate.stop();
				}
			});
		}
	}
	
	//保存还款计划列表
	var _savePlanList = function(){
		var url = webPath+"/mfFundChannelRepayPlan/insertAjax";
		var jsonArray = [];
		var num=0;
		$("#plan_content").find("tr").each(function(){
			if(num!=0){
				var tdArr = $(this).children();
		        var json = new Object;
		        json.term = tdArr.eq(0).html();//期号
		        json.beginDate = tdArr.eq(1).html();//开始时间
		        json.endDate = tdArr.eq(2).html();//结束时间
		        json.repayPrcp = tdArr.eq(3).html();//每期本金（元）
		        json.repayIntst = tdArr.eq(4).html();//每期利息（元）
		        json.repayPrcpIntstSum = tdArr.eq(5).html();//期供金额（元）
		        json.repayPrcpBalAfter = tdArr.eq(6).html();//剩余本金（元）
		        jsonArray.push(json);
			}
	        num++;
	    }); 
		var formParm = JSON.stringify($('#repayPlanTrial').serializeArray());
		var dataParam = JSON.stringify(jsonArray);
		LoadingAnimate.start();
		$.ajax({
			url : url,
			data : {
				ajaxData : dataParam,
				formData : formParm
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				LoadingAnimate.stop();
				if (data.flag == "success") {	
					window.top.alert(data.msg, 1);
					top.flag=true;
					myclose_click();
				} else {
					window.top.alert(data.msg, 0);
				}
			},
			error : function() {
				loadingAnimate.stop();
			}
		});
	}
	
	//跳过银行还款计划
	var _submitBussProcessAjax = function(){
		LoadingAnimate.start();
		jQuery.ajax({
			url :webPath+"/mfFundChannelRepayPlan/submitBussProcessAjax",
			data : {
				appId : appId
			},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				LoadingAnimate.stop();
				if (data.flag == "success") {
					top.flag=true;
					myclose_click();
				} else if (data.flag == "error") {
					window.top.alert(data.msg, 0);
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	}
	//修改结束时间
    var  _updatePactEndDate =  function(){
		var term = $("input[name=loanMon]").val(); //获取合同期限的值
		var intTerm = parseInt(term);
		var provideDate = $("input[name=provideDate]").val(); //合同生效日
		if(term != "" && provideDate != ""){
			var d= new Date(provideDate);  
			d.setMonth(d.getMonth()+intTerm);
			var str = d.getFullYear()+"-"+(d.getMonth()>=9?d.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate()); 
			$("input[name=endDate]").val(str);
		}
	}
	return{
		init:_init,
		exportPlanList:_exportPlanList,
		openList:_openList,
		savePlanList:_savePlanList,
		submitBussProcessAjax:_submitBussProcessAjax,
		updatePactEndDate:_updatePactEndDate
	};
}(window,jQuery);