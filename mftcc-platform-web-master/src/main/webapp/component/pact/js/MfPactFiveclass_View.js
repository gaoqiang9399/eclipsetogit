var MfPactFiveclass_View = function(window, $){
	var _init = function(){
		var level;
		$(".list-div-table p[mytitle]:contains('...')").initMytitle();
		var len = $("#list-div tbody tr").length;
			$(".pageCount").text(len);
			$(".loadCount").text(len);
		    $("#list-div").mCustomScrollbar({
				advanced : {
					updateOnContentResize : true
				}
			});	 
			//处理暂无数据的情况
			if ($('#list-div tbody tr').length == 0) {
				var thCount = $('.notice-div thead th').length;
				$('#list-div tbody')
						.append(
								'<tr><td style="text-align: center;padding-top:20px;font-size:18px;">暂无人工调整历史</td></tr>');
			};		
			if (fiveclass == '1') {
				level = "L4";
			} else if (fiveclass == '2') {
				level = "L4";
			} else if (fiveclass == '3') {
				level = "L3";
			} else if (fiveclass == '4') {
				level = "L3";
			}else if (fiveclass == '5') {
				level = "L2";
			}		
			else{
				level = "L1";
			}
			$(".cus-integrity").attr("level", level);
	};
	//五级分类审批文档
	var _openFiveClass = function(){
		var templateNo = "17083114442837210";//五级分类文档
		$.ajax({
			url: webPath+"/mfTemplateModelRel/getIfSaveTemplateInfo",
			type:"post",
			data:{"relNo":fiveclassId,templateNo:templateNo},
			async: false,
			dataType:"json",
			success:function(data){
				var filepath = "";
				var fileName = "fiveClassaudit.docx";
				var saveBtn = "1";
				if(data.saveFlag !="0"){
					filepath = "/component/model/docword/";
                    fileName = data.fileName;
					saveBtn = "0";
				}
				var saveUrl = webPath+"/component/model/saveModelInfo.jsp?relNo="+fiveclassId+"&filename="+fileName+"&templateNo="+templateNo;
				var poCntJson = {
						filePath : filepath,
						fileName : fileName,
						cusNo : cusNo,
						appId : appId,
						pactId : pactId,
						fincId : fincId,
						fiveclassId :fiveclassId,
						templateNo :templateNo,
						saveUrl :saveUrl,
						saveBtn : saveBtn,
						fileType : 0
				};
				mfPageOffice.openPageOffice(poCntJson);
			},error:function(){alert('error');},
		});
	};
	return {
		init : _init,
		openFiveClass : _openFiveClass
	};
}(window, jQuery);