;
var MfBusTemplate_fileListByPage = function(window, $) {
	var _init = function(){
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                updateOnContentResize : true
            }
        });
		/* myCustomScrollbar({
		    	obj:"#content",//页面内容绑定的id
		    	url:webPath+"/mfTemplateModelConfig/getTemplateByListAjax",//列表数据查询的url
		    	tableId:"tabletemplatemodel",//列表数据查询的table编号
		    	tableType:"thirdTableTag",//table所需解析标签的种类
		    	pageSize:30,//加载默认行数(不填为系统默认行数)
		    	data:{appId:appId}//指定参数 (过滤掉已经封挡的数据)
		    });*/
//		 myCustomScrollbarForForm({
//				obj:".scroll-content",
//				advanced : {
//					updateOnContentResize : true
//				}
//			});
		//绑定全选事件
		$("th[name='sel_all']").click(function(){
			_checkAll();
		});
		//加载文档图标
		$(".docType").each(function(){
			var innerVal = $(this).html();
			if(innerVal.indexOf(".xls")>-1){
				$(this).html("<img src = '"+webPath+"/component/doc/webUpload/image/xls.png' height='20px' width='20px' style='margin-top:10px;'>");
			}else if(innerVal.indexOf(".doc")>-1){
				$(this).html("<img src = '"+webPath+"/component/doc/webUpload/image/word.png' height='20px' width='20px'style='margin-top:10px;'>");
			}
		});
		
	};
	
	var _checkAll= function(){
        $("#template_div").find("input[type='checkbox']").each(function(i,n){
			if ($(n).is(":checked")) {
				$(n).prop('checked', false);
			}else{
				$(n).prop('checked', true);
			}
		});
	};	
	//生成二维码demo后续可以传业务参数
	var _produceQRCode = function(obj, url, tilte){
		var qrcode = new QRCode("qrcode");
		var parm = webPath+"/cusConform/viewer." + url;
		qrcode.makeCode(parm);
		dialog({
			id : "templateQrcode",
			title : tilte || "请扫描二维码查看文件",
			padding : 45 ,
			onclose : function (){
				$("#qrcode").empty();
			},
			okValue : "确定",
			ok : function () {
				this.close();
			},
//			quickClose: true,
			content : $("#qrcode")
		}).showModal();
	};
	//打开文档
	var _openTemplate = function(obj,url){
		var templateBizConfigId = url.split("=")[1];
		_printFile(templateBizConfigId);
	};
	//选中文档打包下载,获取参数
	var _downloadSelTemplateZip = function(){
		//选中的id
		var templateId = "";
		$('#template_div input:checkbox:checked').each(function(){
			templateId += "," + $(this).val().split("=")[1];
		});
		if(templateId.length > 0){
			try{ 
	    		var url = webPath+"/docUpLoad/getZipFileDownloadForSelectTemplet?appId="+appId+"&cusNo="+cusNo+"&docBizNo="+templateId;
	    		var elemIF = document.createElement("iframe");   
	    		elemIF.src = url;   
	    		elemIF.style.display = "none";   
	    		document.body.appendChild(elemIF);   
		    }catch(e){ 
		 
			}
		}else{
			alert('没有可下载的模板',1);
		}
	};
	
	// 显示文档
	var _printFile = function(templateBizConfigId) {
		var url = webPath+"/mfTemplateBizConfig/getOfficeUrlAjax?templateBizConfigId=" + templateBizConfigId;
		var backUrl = encodeURIComponent(location.href);// 关闭office文档时返回目前的页面

		$.ajax({
			url : url + "&" + temParm,
			data : {
				"returnUrl" : backUrl,
				"functionPoint" : "wjdy"
			},
			success : function(data) {
				var poCntObj = $.parseJSON(data.poCnt);
				mfPageOffice.openPageOffice(poCntObj);
			}
		});
	};

	//删除电子文档的保存信息
	var _deleteTemplateSaveInfo = function (obj,url) {
        DIALOG.confirm(top.getMessage("CONFIRM_OPERATION_ASK","删除已保存的信息"),function(){
            $.ajax({
                url : url,
                success : function(data) {
                    DIALOG.msg(data.msg);
                    templateIncludePage.init();
                }
            });

		});
    };
    function getTemplateBizConfigId(obj , id){
        id = id.substring(10);
        $.ajax({
            url:webPath+"/mfBusCollateralDetailRel/getTemplateBizConfigIdAjax",
            data:{id:id},
            type:'post',
            dataType:'json',
            success:function(data){
                if(data.flag == "success"){
                    printFollowPactFile(data.templateBizConfigId ,data.repayDetailId);
                }else{
                    window.top.alert(data.msg,0);
                }
            },error:function(){
                window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
            }
        });
    }
    var printFollowPactFile = function(templateBizConfigId , repayDetailId) {
        var url = webPath+"/mfTemplateBizConfig/getOfficeUrlAjax?templateBizConfigId=" + templateBizConfigId;
        var backUrl = encodeURIComponent(location.href);// 关闭office文档时返回目前的页面
        var temParm = 'cusNo=' + cusNo + '&appId=' + appId + '&pactId=' + pactId + '&repayDetailId=' + repayDetailId;
        $.ajax({
            url : url + "&" + temParm,
            data : {
                "returnUrl" : backUrl
            },
            type : 'post',
            dataType : 'json',
            beforeSend : function() {
                LoadingAnimate.start();
            },
            complete : function() {
                LoadingAnimate.stop();
            },
            error : function() {
                alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
            },
            success : function(data) {
                var poCntObj = $.parseJSON(data.poCnt);
                mfPageOffice.openPageOffice(poCntObj);
            }
        });
    };
	
	return {
		init : _init,
		produceQRCode : _produceQRCode,
		openTemplate : _openTemplate,
		downloadSelTemplateZip : _downloadSelTemplateZip,
		checkAll : _checkAll,
        deleteTemplateSaveInfo  : _deleteTemplateSaveInfo
	};
}(window, jQuery);

function getTemplateBizConfigId(obj , id){
    id = id.substring(10);
    $.ajax({
        url:webPath+"/mfBusCollateralDetailRel/getTemplateBizConfigIdAjax",
        data:{id:id},
        type:'post',
        dataType:'json',
        success:function(data){
            if(data.flag == "success"){
                printFollowPactFile(data.templateBizConfigId ,data.repayDetailId);
            }else{
                window.top.alert(data.msg,0);
            }
        },error:function(){
            window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
        }
    });
}
var printFollowPactFile = function(templateBizConfigId , repayDetailId) {
    var url = webPath+"/mfTemplateBizConfig/getOfficeUrlAjax?templateBizConfigId=" + templateBizConfigId;
    var backUrl = encodeURIComponent(location.href);// 关闭office文档时返回目前的页面
    var temParm = 'cusNo=' + cusNo + '&appId=' + appId + '&pactId=' + pactId + '&repayDetailId=' + repayDetailId;
    $.ajax({
        url : url + "&" + temParm,
        data : {
            "returnUrl" : backUrl
        },
        type : 'post',
        dataType : 'json',
        beforeSend : function() {
            LoadingAnimate.start();
        },
        complete : function() {
            LoadingAnimate.stop();
        },
        error : function() {
            alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
        },
        success : function(data) {
            var poCntObj = $.parseJSON(data.poCnt);
            mfPageOffice.openPageOffice(poCntObj);
        }
    });
};
function editFollowPactNo(obj , id){
    id = id.substring(10);
    $(obj).hide();
    if(followPactNoShowSts == "1"){
        $(obj).after("<input name=\"followPactNoShow\" style=\"width:165px;text-align: center;\" value=\"\" maxlength=\"30\" type=\"text\" onblur=\"updateFollowPactNoShow(this,'" + id + "');\">");
    }else{
        $(obj).after("<input name=\"followPactNo\" style=\"width:165px;text-align: center;\" value=\"\" maxlength=\"30\" type=\"text\" onblur=\"updateFollowPactNo(this,'" + id + "');\">");
    }

    $("input[name='followPactNo']")[0].focus();
};
function updateFollowPactNo(obj , id){
    var followPactNo = $(obj).val();
    if(followPactNo != $(obj).prev().text() && "" != followPactNo.replaceAll(" ","")){
        $.ajax({
            url:webPath+"/mfBusCollateralDetailRel/updateFollowPactNoAjax",
            data:{id:id,followPactNo:followPactNo},
            type:'post',
            dataType:'json',
            success:function(data){
                if(data.flag == "success"){
                    $(obj).hide();
                    $(obj).prev().text(followPactNo);
                    $(obj).prev().show();
                    $(obj).remove();
                }else{
                    $(obj).hide();
                    $(obj).prev().show();
                    $(obj).remove();
                    window.top.alert(data.msg,0);

                }
            },error:function(){
                window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
            }
        });
    }else{
        $(obj).hide();
        $(obj).prev().show();
        $(obj).remove();
    }
};
function updateFollowPactNoShow(obj , id){
    var followPactNoShow = $(obj).val();
    if(followPactNoShow != $(obj).prev().text() && "" != followPactNoShow.replaceAll(" ","")){
        $.ajax({
            url:webPath+"/mfBusCollateralDetailRel/updateFollowPactNoShowAjax",
            data:{id:id,followPactNoShow:followPactNoShow},
            type:'post',
            dataType:'json',
            success:function(data){
                if(data.flag == "success"){
                    $(obj).hide();
                    $(obj).prev().text(followPactNoShow);
                    $(obj).prev().show();
                    $(obj).remove();
                }else{
                    $(obj).hide();
                    $(obj).prev().show();
                    $(obj).remove();
                    window.top.alert(data.msg,0);

                }
            },error:function(){
                window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
            }
        });
    }else{
        $(obj).hide();
        $(obj).prev().show();
        $(obj).remove();
    }
}
