;
var MfCreditQueryRecordInfo=function(window, $){
	//客户详情页面初始化征信查询按钮
	var _init=function(){
		//客户详情中默认按钮不可操作
		top.creditQueryFlag="0";//标识是否征已信查询0未查询1已查询
			$("#creditQuery").show();
		$.ajax({
	 		url:webPath+"/mfCreditQueryRecordInfo/getCreditQueryByCusNoAjax",
	 		dataType:'json',
	 		type:'post',
	 		data:{cusNo:cusNo},
	 		success : function(data){
	 			if (data.flag == "success") {//有存在征信查询成功的历史，按钮两色
	 				top.creditQueryFlag="1";
	 				$("#creditQuery").attr("class","btn btn-view btn-dodgerblue");
				}
	 		}
	 	});
	};
	//征信结果页面初始化
	var _initCreditContent=function(){
		_showCreditHisListByPage();
		$('input[name=queryCusNo]').popupSelection({
			inline: true, //下拉模式
			multiple: false, //单选
			searchOn:true,
			items:ajaxData.creditCusArr,
			changeCallback : function (obj, elem) {
				var cusNoStr=obj.val();
				var cusNoTemp=cusNoStr.split("@")[0];
				var creditCusType=cusNoStr.split("@")[1];
				if(creditCusType=="1"){//借款客户
					cusNo=cusNoTemp;
				}else if(creditCusType=="2"){//担保人
					//将当前借款客户的客户号放到relId中
					$("input[name=relId]").val(cusNo);
					cusNo=cusNoTemp;
				}
				$.ajax({
					url:webPath+"/mfCreditQueryRecordInfo/getCreditQueryCusInfoAjax",
					dataType:'json',
					type:'post',
					data:{cusNo:cusNoTemp,creditCusType:creditCusType},
					success : function(data){
						if (data.flag == "success") {
							var docSplitNoArr=ajaxData.docSplitNoArr
							if(creditCusType=="3"){//配偶
								docSplitNoArr=data.docSplitNoArr;
								$("input[name=familyId]").val(data.familyId);
							}
							$("input[name=idNum]").val(data.idNum);
							$("input[name=idType]").val(data.IdTypeName);
							$("input[name=queryCusType]").val(creditCusType);
							/*//重新加载要件信息
							dataDocParm={
									relNo:cusNo,
									docType:"collateralDoc",
									docTypeName:"征信资料",
									docSplitName:"征信资料",
									query:'query',
									docSplitNoArr:docSplitNoArr
								};
							initDocNodes();*/
						} else {
							window.top.alert(data.msg, 0);
						}
					}
				});
			}
		});
	};
	//分页展示征信查询历史
	var _showCreditHisByPage=function(){
		myCustomScrollbar({
			obj : "#creditQueryHisContentList", //页面内容绑定的id
			url : webPath+"/mfCreditQueryRecordInfo/findByPageAjax", //列表数据查询的url
			tableId : "tablecreditquery0001", //列表数据查询的table编号
			tableType : "thirdTableTag", //table所需解析标签的种类
			pageSize:30, //加载默认行数(不填为系统默认行数)
			data:{
				cusNo:cusNo
			}
		});
		$(".footer_loader").show();
	};
	
	//分页展示征信查询历史
	var _showCreditHisListByPage=function(){
		myCustomScrollbar({
			obj : "#creditQueryHisContentList", //页面内容绑定的id
			url : webPath+"/mfCreditQueryRecordInfo/findByPageAjax", //列表数据查询的url
			tableId : "tablecreditquery0001", //列表数据查询的table编号
			tableType : "thirdTableTag", //table所需解析标签的种类
			pageSize:12, //加载默认行数(不填为系统默认行数)
			topHeight:235,
			data:{
				appId:appId,
				cusNo:cusNo
			}
		});
		$(".footer_loader").show();
	};
	//征信查询
	var _creditQuery=function(obj){
		var docMangeNoStr ="";
		$('.upload_body .upload-li').find($('input[type=checkbox]:checked')).each(function () {
			var $this=$(this);
			var docNo=$this.parents("li").attr("id");
			var docSplitNo=$this.parents("li").attr("class");
			docSplitNo=docSplitNo.split("_")[1];
			docMangeNoStr=docNo+"@"+docSplitNo+"&"+docMangeNoStr;
		});
		if(docMangeNoStr==""){
			alert(top.getMessage("FIRST_SELECT_FIELD", "上传的征信资料！"),0);
			return;
		}
		$("input[name=docMangeNoStr]").val(docMangeNoStr);
		alert(top.getMessage("CONFIRM_DETAIL_OPERATION", {"content":"请核查客户身份证、授权书信息是否正确","operation":"查询"}), 2, function() {
			var flag = submitJsMethod($(obj).get(0), '');
			if(flag){
				var dataParam = JSON.stringify($(obj).serializeArray());
				$.ajax({
					url:webPath+"/mfCreditQueryRecordInfo/creditQueryAjax",
					dataType:'json',
					type:'post',
					data:{cusNo:cusNo,appId:appId,ajaxData:dataParam},
					success : function(data){
						if (data.flag == "success") {
							var queryId=data.queryId;
							//_getCreditContent(queryId);
							top.window.openBigForm(webPath+"/mfCreditQueryRecordInfo/showCreditResultContentByQueryId?queryId="+queryId,"征信结果",function(){
								
							});
						} else {
							window.top.alert(data.msg, 0);
						}
					}
				});
			}
		});
	};
	
	//业务详情中打开客户最新的征信查询结果页面
	var _openCreditQuery=function(){
		top.window.openBigForm(webPath+"/mfCreditQueryRecordInfo/getCreditContentById?cusNo="+cusNo+"&appId="+appId+"&busEntrance="+busEntrance+"&creditQueryFlag=1","征信结果",function(){
			
		});
	};
	//百行征信查询
	var _creditQueryForBaiHang=function(){
        $.ajax({
            url:webPath+"/mfCreditQueryRecordInfo/queryCreditForBaiHang",
            dataType:'json',
            type:'post',
            data:{cusNo:cusNo},
            success : function(data){
                if (data.flag == "success") {
                    $("#creditContent").html(data.creditContent);
                    $("#explainheader").parent().attr("style","width:720px;background-color:#ffffff;margin-right:auto;margin-left:auto;");
                    $("a").attr("onclick","");
                    $("#creditContent").show();
                    $("#cusAppLyInfo").hide();
                    $("#creditQueryForm").hide();
                    $("#againButton").show();
                    $("#closeButton").hide();
                    $("#backButton").show();
                    $(".footer_loader").hide();
                    $(".table-float-head").hide();
                    _refreshCreditQueryHis();
                } else {
                    window.top.alert(data.msg, 0);
                }
            }
        });
	};
	//根据查询编号获得征信结果并将征信结果展示页面中
	var _getCreditContent=function(queryId){
		$.ajax({
	 		url:webPath+"/mfCreditQueryRecordInfo/getCreditContentAjax",
	 		dataType:'json',
	 		type:'post',
	 		data:{queryId:queryId},
	 		success : function(data){
	 			if (data.flag == "success") {
	 				$("#creditContent").html(data.creditContent);
                    var authCode=data.authCode;
                    MfCreditQueryRecordInfo.showpdf(authCode);
                    $("#creditContent").show();
				} else {
					window.top.alert(data.msg, 0);
				}
	 		}
	 	});
	};
	
	//展示征信结果
	var _showCreditContent=function(obj,url){
		var queryId=url.split("?")[1].split("=")[1];
		//_getCreditContent(queryId);
		top.window.openBigForm(webPath+"/mfCreditQueryRecordInfo/showCreditResultContentByQueryId?queryId="+queryId,"征信结果",function(){
				
		});
	};
    var _showpdf = function (authCode) {
        $("#exportToPdf").click(function() {
            top.openBigForm(webPath+'/mfCreditQueryRecordInfo/showbaihangpdf?authCode='+authCode,'pdf打印',function() {});
        });
    }
	//展示征信查询页面
	var _showCreditQueryForm=function(){
		$("#creditContent").hide();
		$("#cusAppLyInfo").hide();
		$("#againButton").hide();
		$("#bussButton").show();
		$("#showCreditQueryButton").show();
		$("#creditQueryForm").show();
		$(".footer_loader").hide();
		$(".table-float-head").hide();
		//遍及要件信息,并添加checkbox
		$(".upload-li").each(function(i,obj){
			var $this=$(obj);
			var fileName=$this.find(".doc-title").html();
			var htmlStr='<label style="cursor: pointer;"><input type="checkbox">'+fileName+'</label>';
			$this.find(".doc-title").html(htmlStr);
		});
	};
	
	//征信结果页面返回到客户业务汇总信息
	var _back=function(){
		//刷新查询历史列表数据
		$("#creditContent").hide();
		$("#cusAppLyInfo").show();
		$("#againButton").hide();
		$("#bussButton").hide();
		$("#creditQueryForm").hide();
		$(".table-float-head").show();
		_showCreditHisListByPage();
		$(".upload-li").each(function(i,obj){
			var $this=$(obj);
			var fileName=$this.find(".doc-title").attr("title");
			$this.find(".doc-title").html(fileName);
		});
	};
	
	//打开客户最新的征信查询结果页面。客户详情中
	var _openCreditQueryForCusDatail=function(){
		if(top.creditQueryFlag=="0"){
			// return;
		}
		top.window.openBigForm(webPath+"/mfCreditQueryRecordInfo/getCreditHisByCusNo?cusNo="+cusNo+"&appId=&busEntrance="+busEntrance+"&creditQueryFlag=1","征信结果",function(){
				
		});
	};
	
	//重新进行征信查询
	var _creditQueryAgain=function(){
		//$("#cusAppLyInfo").show();
		$("#creditQueryForm").show();
		$("#creditContent").hide();
		$("#againButton").hide();
	};
	
	//展示要件信息。暂时不用
	var _showCreditCreditDoc=function(){
		$.ajax({
	 		url:webPath+"/mfCreditQueryRecordInfo/getCreditCreditDocAjax",
	 		dataType:'json',
	 		type:'post',
	 		data:{cusNo:cusNo,cusBaseType:cusBaseType},
	 		success : function(data){
	 			if (data.flag == "success") {
	 				var htmlStr="";
	 				$.each(data.otherList,function(i,docMange){
	 					$("li[id="+docMange.docNo+"]").hide();
	 					$(".title-name").hide();
	 					$("#creditDocInfo").show();
	 					/*var docHtml='<img src=webPath+"/docUpload/viewCompressImage?docNo='+docMange.docNo+'&amp;docBizNo='+docMange.docBizNo+'" class="viewer-toggle">'
	 					+'<div class="doc-title" title="'+docMange.docName+'">'+docMange.docName+'</div>'
	 					htmlStr=htmlStr+docHtml;*/
	 				});
	 				//$("#creditDocInfo").html(htmlStr);
				} else {
					
				}
	 		}
	 	});
	};
	//刷新征信查询历史列表
	var _refreshCreditQueryHis=function(){
		$.ajax({
	 		url:webPath+"/mfCreditQueryRecordInfo/getCreditQueryHisHtmlAjax",
	 		dataType:'json',
	 		type:'post',
	 		data:{appId:appId},
	 		success : function(data){
	 			if (data.flag == "success") {
	 				var htmlStr=data.htmlStr;
	 				$("#creditQueryHisContentList").html(htmlStr);
				} else {
					
				}
	 		}
	 	});
	};
	return{
		init:_init,
		creditQuery:_creditQuery,
		openCreditQuery:_openCreditQuery,
		getCreditContent:_getCreditContent,
		openCreditQueryForCusDatail:_openCreditQueryForCusDatail,
		creditQueryAgain:_creditQueryAgain,
		initCreditContent:_initCreditContent,
		showCreditContent:_showCreditContent,
		back:_back,
		showCreditQueryForm:_showCreditQueryForm,
		showCreditHisByPage:_showCreditHisByPage,
		showCreditHisListByPage:_showCreditHisListByPage,
        creditQueryForBaiHang:_creditQueryForBaiHang,
        showpdf:_showpdf,
	};
}(window, jQuery);