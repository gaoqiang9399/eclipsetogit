var userInfo = function(window, $){
	var tipsTimeoutId;
	var _init=function(){
		
		$("#content").mCustomScrollbar({
			advanced : {
				updateOnContentResize : true
			}
		});
		if(homePage=="2"){
			$("#home-page").show();
		}
		
		//今日注册人数
		$("#regCount").bind("click", function(event){
			top.itemId="";
			top.flag=false;
			top.openBigForm(webPath+"/webCusLineReg/getCusListPage?dataType=regCount","注册客户列表",function(){
				if(top.flag){
					_addItemCallBack(top.itemId);
				}
			});
			top.LoadingAnimate.stop();
		});
		
		//今日在线人数
		$("#onlineCount").bind("click", function(event){
			top.itemId="";
			top.flag=false;
			top.openBigForm(webPath+"/webCusLineReg/getCusListPage?dataType=onlineCount","在线人数列表",function(){
				if(top.flag){
					_addItemCallBack(top.itemId);
				}
			});
			top.LoadingAnimate.stop();
		});
		
		//借款申请
		$("#applyCount").bind("click", function(event){
			top.itemId="";
			top.flag=false;
			top.openBigForm(webPath+"/mfBusApply/getCashListPage?dataType=applyCount","借款申请列表",function(){
				if(top.flag){
					_addItemCallBack(top.itemId);
				}
			});
			top.LoadingAnimate.stop();
		});
		
		//借款否决数
		$("#vetoCount").bind("click", function(event){
			top.itemId="";
			top.flag=false;
			top.openBigForm(webPath+"/mfBusApply/getCashListPage?dataType=vetoCount","借款否决列表",function(){
				if(top.flag){
					_addItemCallBack(top.itemId);
				}
			});
			top.LoadingAnimate.stop();
		});
		
		
		//还款量
		$("#repayAmt").bind("click", function(event){
			top.itemId="";
			top.flag=false;
			top.openBigForm(webPath+"/mfBusRepayInfo/getCashListPage?dataType=repayAmt","今日还款列表",function(){
				if(top.flag){
					_addItemCallBack(top.itemId);
				}
			});
			top.LoadingAnimate.stop();
		});
		
		//未还款量
		$("#repaySumAmt").bind("click", function(event){
			top.itemId="";
			top.flag=false;
			top.openBigForm(webPath+"/mfBusFincApp/getCashListPage?dataType=repaySumAmt","待还金额列表",function(){
				if(top.flag){
					_addItemCallBack(top.itemId);
				}
			});
			top.LoadingAnimate.stop();
		});
		
		//逾期还款量
		$("#overdueSumAmt").bind("click", function(event){
			top.itemId="";
			top.flag=false;
			top.openBigForm(webPath+"/mfBusFincApp/getCashListForOverduePage?dataType=overdueSumAmt","逾期金额列表",function(){
				if(top.flag){
					_addItemCallBack(top.itemId);
				}
			});
			top.LoadingAnimate.stop();
		});
		//放款量
		$("#loanCount").bind("click", function(event){
			top.itemId="";
			top.flag=false;
			top.openBigForm(webPath+"/mfBusFincApp/getTodayPutOutListCountPage?","今日放款列表",function(){
				if(top.flag){
					_addItemCallBack(top.itemId);
				}
			});
			top.LoadingAnimate.stop();
		});
		
		//还款量
		$("#repayAmt").bind("click", function(event){
			top.itemId="";
			top.flag=false;
			top.openBigForm(webPath+"/mfBusRepayInfo/getCashListPage?dataType=repayAmt","今日还款列表",function(){
				if(top.flag){
					_addItemCallBack(top.itemId);
				}
			});
			top.LoadingAnimate.stop();
		});
		
		//处理头像信息
		var data=headImg;
		if(ifUploadHead!="1"){
			data = "themes/factor/images/"+headImg;
		}
		data = encodeURIComponent(encodeURIComponent(data));
		document.getElementById('headImgShow').src=webPath+"/uploadFile/viewUploadImageDetail?srcPath="+data+"&fileName=op_user.jpg";

		//上传/更新头像事件
		$("#headImgShow").bind("click", function(event){
			//上传头像
			window.parent.openBigForm(webPath+'/sysUser/uploadHeadImg?opNo='+opNo+'&fromPage=perCenter','员工头像',_showNewHeadImg);
			
		});

		$("#setHomePageClose").bind("click", function(event){
			$("#home-page").hide();		
		});
		var $outerWest = $("#a_iframe", window.top.document).parent().parent().parent().next();//左侧菜单
		var _addItemCallBack = function(itemId){ 
			$.ajax({
				url: webPath+"/mfDeskMsgItem/updateUseFlagAjax?itemId="+encodeURI(itemId)+"&opNo="+opNo,
				type:'post',
				dataType:'json',
				success: function(data) {
					if(data.flag=="success"){
						$.each($(".move-div"),function(index,item){
							$.each(data.attention,function(i,it){
								if($(item).attr("msgType") == it.msgType){
									$(item).show();
								}
							});
							$.each(data.unattention,function(i,it){
								if($(item).attr("msgType") == it.msgType){
									$(item).hide();
								}
							});
						});
					}else{
						alert(top.getMessage("FAILED_OPERATION","添加关注"),0);
					}
				},error:function(){
					top.LoadingAnimate.stop();
				}
			});
		};
		var _closeCallBack = function(){
			window.location.href = window.location.href;
		};
		
		$(".move-div").mousedown(function(){
			isMousemove = false;
		});
		$(".move-div").bind("click", function(event){
			// 判断鼠标是否被拖动过，如果被拖动过不执行跳转
			if(!isMousemove){
				var url = $(this).attr("url");
				var isShowDialog = $(this).attr("isShowDialog");
				var msgTitle = $(this).attr("msgTitle");
				var menuNo = $(this).attr("menuNo");
				if(url == ""){
					_showTips(this);
				}else{
					if(isShowDialog == "0"){
						window.location.href = url;
						$outerWest.find("li").eq(menuNo).addClass("menu-active");
					}else {
						window.top.createTaskShowDialog(url,msgTitle,"80","50");
					};
				}
			}
			isMousemove = false;
		});
		$("#btn-leave").bind("click", function(event){
			top.window.openBigForm(webPath+'/mfOaLeave/input?query=perCenter','请假申请',_closeLeaveCallBack);
			
		});
		$(".leave-detail").bind("click", function(event){
			top.window.openBigForm(webPath+'/mfOaLeave/getListPag.action','请假详情',function(){});
			
		});
		$("#btn-debt").bind("click", function(event){
			top.window.openBigForm(webPath+'/mfOaDebt/input?query=perCenter','借款申请',_closeCallBack);
			
		});
		$("#btn-baoxiao").bind("click", function(event){
			top.window.openBigForm(webPath+'/mfOaExpense/input?query=perCenter','报销申请',_closeCallBack);
			
		});
		$("#addItem").bind("click", function(event){
			top.itemId="";
			top.flag=false;
			top.openBigForm(webPath+"/mfDeskMsgItem/getDeskMsgItemList?opNo=" + opNo,"添加关注",function(){
				if(top.flag){
					_addItemCallBack(top.itemId);
				}
			});
			top.LoadingAnimate.stop();
		});
	
		_updateHomePage();
		_adjustheight();
	
	};
	
	
	//调整左右两块布局的高度一致
	var  _adjustheight = function(){
		$(".base-info").css("height","auto");
		//调整左边和右边的height，使高度小的适应高度大的
		var leftheight = parseInt($(".base-info").outerHeight());
		var rightheight =  parseInt($(".right-info").outerHeight());
		if(leftheight > rightheight){
			$(".right-info").css("height",leftheight);
		}else if(leftheight < rightheight){
			$(".base-info").css("height",rightheight);
		} 
	};
	
	var _showNewHeadImg = function(){
		var data;
		$.ajax({
			url:webPath+"/sysUser/getIfUploadHeadImg",
			data:{opNo:opNo},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "1"){
					data = encodeURIComponent(encodeURIComponent(data.headImg));
					document.getElementById('headImgShow').src=webPath+"/uploadFile/viewUploadImageDetail?srcPath="+data+"&fileName=op_user.jpg";
					top.view.showNewHeadImg();
				}else{
					data = "themes/factor/images/"+data.headImg;
					document.getElementById('headImgShow').src=webPath+"/uploadFile/viewUploadImageDetail?srcPath="+data+"&fileName=op_user.jpg";
					top.view.showNewHeadImg();
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
		_delFile();
	};
	
	//删除文件
	var _delFile = function(){
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
	
	var _updateHomePage = function(){
		$(".homebtn").bind("click", function(event){
			jQuery.ajax({
				url:webPath+"/sysUser/updateStsAjax?homePage=1",
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						alert("设置首页成功！",1);
						//window.location.reload();
						$("#home-page").hide();
					}else if(data.flag == "error"){
						$.myAlert.Alert(data.msg);
					}
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
	    	});
		});
	};
	var _showTips = function (obj) {
		top.LoadingAnimate.stop();
		var d = dialog({
			id : "oaInBuilding",
			content : "正在建设中，敬请期待。",
			padding : "3px"
		}).show(obj);
		if (tipsTimeoutId) {
			clearTimeout(tipsTimeoutId);
		}
		tipsTimeoutId = setTimeout(function() {
			d.close().remove();
		}, 1000);
	};
	return{
		init:_init
	};
}(window, jQuery);
