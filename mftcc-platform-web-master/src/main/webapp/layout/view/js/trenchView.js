;
var view = function(window, $){
	var _init=function(){
		_bindmouse();
		_getSysUserInfo(url);
		_showNewHeadImg();
	};
	_bindmouse=function(){
		$("#perDa").mouseleave(_myInfoHide);
		$("#perDa").mouseover( _myInfoShow);
		$("#my-info").mouseover(_myInfoShow);
		$("#my-info").mouseleave(_myInfoHide);
		//打开个人信息页面
		$("#u-pic").bind("click",_openUserInfo);
		if(viewFlag!="set"){
			$("#perDa").bind("click",_openUserInfo);
		}
	};
	var _myInfoHide=function(){
		$(".my-info").hide();
		$("#perDa").css("background","#32B5CB");
	};
	var _myInfoShow=function(){
		var perDaWidth = parseInt($("#perDa").outerWidth());
		var perDaRight = parseInt($(".toolbox-li").outerWidth());
		$(".my-info").css("width",perDaWidth - 1);
		$(".my-info").css("right",perDaRight);
		$(".my-info").show();
		$("#perDa").css("background","#008FA7");
		
	};
	//打开渠道中心页面
	var _openUserInfo=function(){
		var url = webPath+"/mfTrenchUser/getTrenchCenterInfo?opNo="+opNo;
		rzzl.skipPage(url);
		$(".menu-active").removeClass("menu-active");
		$("#messagePage").removeClass("messagePageSel");
		$("#perDa").addClass("messagePageSel");
	};
function _getSysUserInfo(url) {
	
		$.ajax({
			type : "POST",
			url : url,
			dataType : "json",
			success : function(data) {
				var formData = data.formData;
				if(data.count>=100){
					htmlStr = "<span class='badge task-count' id='task_count'>99+</span><input type=hidden id='count_input' value='"+data.count+"'/>";
			 		$("#messagePage").append(htmlStr);
			 	}else if(data.count>=1&&data.count<100){
			 		htmlStr = "<span class='badge task-count' id='task_count' style='width:18px;'>"+data.count+"</span><input type=hidden id='count_input' value='"+data.count+"'/>";
			 		$("#messagePage").append(htmlStr);
			 	}	
			},
			error : function(xmlhq, ts, err) {
				alert(xmlhq + "||" + ts + "||" + err);
			}
		});
	}

	
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
					document.getElementById('headImgShow1').src=webPath+"/uploadFile/viewUploadImageDetail?srcPath="+data+"&fileName=op_user.jpg";
					
				}else{
					data = "themes/factor/images/"+data.headImg;
					document.getElementById('headImgShow').src=webPath+"/uploadFile/viewUploadImageDetail?srcPath="+data+"&fileName=op_user.jpg";
					document.getElementById('headImgShow1').src=webPath+"/uploadFile/viewUploadImageDetail?srcPath="+data+"&fileName=op_user.jpg";
				}
			},error:function(){
				// alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
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
				// alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	};
	//修改比例
	var _editPasswordInput=function(){
		top.editPasswordFlag = false;
		window.parent.openBigForm(webPath+'/mfTrenchUser/editPasswordInput', '修改密码', function() {
			if(top.editPasswordFlag){
				
			}
		});
	}
	return {
		init :_init,
		myInfoShow:_myInfoShow,
		myInfoHide:_myInfoHide,
		showNewHeadImg:_showNewHeadImg,
		editPasswordInput:_editPasswordInput
	};
}(window, jQuery);