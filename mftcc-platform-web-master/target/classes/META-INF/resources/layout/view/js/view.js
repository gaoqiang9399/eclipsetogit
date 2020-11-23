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
		// //打开个人信息页面
		$("#u-pic").bind("click",_openUserInfo);
		if(viewFlag!="set"){
			$("#perDa").bind("click",_openUserInfo);
		}
	};
	var _myInfoHide=function(){
		$("#perDa").removeClass("open");
	};
	var _myInfoShow=function(){
        $("#perDa").addClass("open");
		
	};
	var _openUserInfo=function(){
		var url = webPath+"/sysUser/getPersonalCenterInfo?opNo="+opNo;
		if(opNoType == "2"){//渠道操作员登录
			url = webPath+"/mfBusTrench/getTrenchView?cusNo=" + trenchUid + "&trenchId=" + trenchId + "&busEntrance=cus_trench";
		}else if(opNoType == "3"){//资金机构操作员登录
			url = webPath+"/mfBusAgencies/getAgenciesView?agenciesId=" + agenciesId + "&busEntrance=cus_agencies";
		}else if(opNoType == "4"){//仓储机构操作员登录
            url = webPath+"/mfCusWarehouseOrg/getWarehouseOrgView?id=" + warehouseOrgId + "&busEntrance=cus_warehouse_org";
        }else if(homePage == "3"){//合作机构操作员登录
            url = webPath+"/mfCusCooperativeAgency/getCooperativeAgencyView?orgaNo=" + brNo +"&opNo="+opNo+ "&busEntrance=cus_coopAgency";
		}else if(typeof(opNoType) != "undefined" && opNoType == "5"){//核心企业操作员登录
			url=webPath+"/mfCusCoreCompany/getCoreCompanyView?coreCompanyUid="+coreCompanyUid+"&busEntrance=cus_core_company&baseType="+baseType;
		}
		rzzl.skipPage(url);
		$(".menu-active").removeClass("menu-active");
		//top.window.openBigForm(url,"个人信息中心",null);
		$("#messagePage").removeClass("messagePageSel");
		$("#searchGlobalForNav").removeClass("messagePageSel");
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
				console.error(xmlhq + "||" + ts + "||" + err);
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
					data = "/themes/factor/images/"+data.headImg;
					document.getElementById('headImgShow').src=webPath+"/uploadFile/viewUploadImageDetail?srcPath="+data+"&fileName=op_user.jpg";
					document.getElementById('headImgShow1').src=webPath+"/uploadFile/viewUploadImageDetail?srcPath="+data+"&fileName=op_user.jpg";
				}
			},error:function(){
//				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
//		_delFile();
	};

	//删除文件
	var _delFile = function(){
		var srcPath = "/tmp/";
		$.ajax({
			url:webPath+"/UploadFile/delFile",
			data:{srcPath:srcPath},
			type:'post',
			dataType:'json',
			success:function(data){
				
			},error:function(){
//				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	};

	return {
		init :_init,
		myInfoShow:_myInfoShow,
		myInfoHide:_myInfoHide,
		showNewHeadImg:_showNewHeadImg
	};
}(window, jQuery);