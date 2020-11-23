	
	//var LODOP; // 插件声明为全局变量
	var t_height = 0, t_width = 0;// 模板高、宽。单位mm
	var eval_code;// 模板代码
	
	var r_replace_tmpl_5 = /"([^"]*)"/
	var r_replace_tmpl_1 = /A\("([^"]*)",/
	var r_replace_tmpl_delete_end = /,"[^"]*"\)/
	
		//选择套打
		function taoDaPrintType(obj){
			//请求还款的操作，获取data数据
			var dataN ;
			//alert("busType======"+busType);
			
			$.ajax({
				url:webPath+"/cwPrintTmplItem/getPrintMessageByIdAjax",
				data:{'busType':busType,'fincId':fincId,'repayId':repayId},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
				 if(data){
					 dataN = data.datas;
					//console.log(dataN);
					//dataN = {"total":"","hkAmt1":"30.0","durationMonth":"","breachAmt":"0.0","hkAmount":"4444444","totalDays":"","hkAmt":"30.00","interest1":"0.0","totoalAmtInWords":"","totalAmt":"","pactNo":"MFT20170913005627","loanAccount":"","interestRate":"","durationDay":"","startYear":"","capitalAmt":"0.0","startDay":"","interest":"","month":"09","year":"2017","day":"13","startMonth":"","discountAmt":"张飞","capitalAmtW":""}
					public_lodop_print(busType,$.parseJSON(dataN));
				 }
						
				},error:function(data){
					alert("error");
				}
			}); 
			//dataN = {"total":"100000","hkAmt1":"683.33","durationMonth":1,"breachAmt":0.0,"hkAmt":"68333","endDay":"04","totalDays":31,"interest1":"316.66","totalAmt":"1,000.00","pactNo":"LSBL201707171148072540","loanAccount":"LSBL201707171148072540","durationDay":0,"capitalAmt":"50,000.00","interest":"31666","startDay":"04","year":"2017","discountAmt":200.0,"endYear":"2017","totoalAmtInWords":" 壹仟圆整 ","interestRate":"10.00000","cifName":"周五","startYear":"2017","month":"07","day":"17","startMonth":"07","capitalAmtW":"5.000000","endMonth":"08"};
			//dataN = {"total":"","hkAmt1":"30.0","durationMonth":"","breachAmt":"0.0","hkAmount":"4444444","totalDays":"","hkAmt":"30.00","interest1":"0.0","totoalAmtInWords":"","totalAmt":"","pactNo":"MFT20170913005627","loanAccount":"","interestRate":"","durationDay":"","startYear":"","capitalAmt":"0.0","startDay":"","interest":"","month":"09","year":"2017","day":"13","startMonth":"","discountAmt":"张飞","capitalAmtW":""}
			
		}
     function needlePrint(){
         //parent.location.href=webPath+"/mfBusPutoutNeedlePrint/getLoanInfo?fincId=" + fincId+"&cusNo="+cusNo+"&appId="+appId;
        // $("#a_iframe" , parent.document).attr('src',webPath+"/mfBusPutoutNeedlePrint/getLoanInfo?fincId=" + fincId+"&cusNo="+cusNo+"&appId="+appId);
         top.openBigForm (webPath+"/mfBusPutoutNeedlePrint/getLoanInfo?fincId=" + fincId+"&cusNo="+cusNo+"&appId="+appId,'打印',function(){
         });
         $("#showDialog",parent.document).remove();
	};
		
		function init_lodop(){
			/*if($("#LODOP_OB").size()==0){
				var html='<div style="border: 1 solid red;width:10px;><object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=10 height=10 > <embed id="LODOP_EM" type="application/x-print-lodop" width=50 height=50 pluginspage="install_lodop.exe"></embed></object></div>';
				$("body").append(html);
			}*/
		}
		
		
		// 打印接口，参数 模板ID，业务数据
		function public_lodop_print(id, obj) {
			init_lodop();
			if(!LODOP){
				LODOP=getLodop();  
			}
			/*LODOP = getLodop(document.getElementById('LODOP_OB'), document
					.getElementById('LODOP_EM'));*/
			try{
				if(!LODOP)return;
				LODOP.SET_SHOW_MODE("PREVIEW_IN_BROWSE", 1);
			}catch(ex){
				return;
			}
			get_tmpl(id, function(tmpl_objs) {
				get_code(id, function(code) {
					if(!code)alert("模板为空，请配置套打模板",0);
					var is_code_empty=true;
					var lines = code.split(';');
					for ( var i = 0; i < lines.length; i++) {
						var line = lines[i];
						if (!line)
							continue;
						if (line.indexOf('ADD_PRINT_SETUP_BKIMG') > -1) {
						var sflag =/src='([^']*)'/.test(line);
							if(sflag)
							{
								line=line.replace(/(.*)ajax/.exec(/src='([^']*)'/.exec(line)[1])[1],"/");
							}
						}
						if (line.indexOf('ADD_PRINT_TEXTA') > -1) {
							is_code_empty=false;
							// 是新增字段的语句
							line = line.replace(r_replace_tmpl_delete_end, ',"")');// 
							var tmp = line.split(',');
							var re_tmp = r_replace_tmpl_5.exec(tmp[0]);
							tmp[5] = tmp[5].replace(r_replace_tmpl_5, '"'
									+ (obj[re_tmp[1]] != undefined ? obj[re_tmp[1]]
											: "") + '"');
							line = tmp.join(',');
							line = line.replace(r_replace_tmpl_1, '(');// 实际打印时不能用
							// ADD_PRINT_TEXTA 应该用
							// ADD_PRINT_TEXT
							
						} 
						eval(line);
					}
					 if(is_code_empty)alert("模板中未保存任何控件，请保存至少一个控件",0);
					 else LODOP.PREVIEW(); 
					 //else LODOP.PRINT_DESIGN(); 
					// else LODOP.PRINT_SETUP(); 
					
				});
			});
	// window.location.reload();
		}
		
		// 根据模板ID获取模板字段
		function get_tmpl(tmpl_id,fn) {
			
			jQuery.ajax({
				url:webPath+"/cwPrintTmplItem/getTmplObjByTypeAjax",
				data:{"busType":tmpl_id},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if (!data) {
						data = [];
					}
					var items = data.itemPlate;
					items = $.parseJSON(items);
					fn(items);		
				},error:function(data){
					alert("error");
				}
			}); 
			
			
			/*js_printDWR.getTmplObjByType(tmpl_id,function(data){
//					alert(data);
				if(!data){
					data=[];
				}
				data=$.parseJSON(data);
				fn(data);
			});*/
		}
		function get_code(tmpl_id,fn) {
			
			$.ajax({
				url:webPath+"/cwPrintTmplItem/getTmplCodeByTypeAjax",
				data:{'busType':tmpl_id},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
				 if(data){
				 	var codeval = data.mapobj;
				 	codeval = $.parseJSON(codeval);
					$('#t_height').val(codeval.height||0);
					$('#t_width').val(codeval.width||0);
					fn(codeval.code);	
				 }
						
				},error:function(data){
					alert("error");
				}
			}); 
			/*js_printDWR.getTmplCodeByType(tmpl_id,function(data){
//					alert(data);
				data=$.parseJSON(data);
				$('#t_height').val(data.height);
				$('#t_width').val(data.width);
				fn(data.code);
			});*/
		}
	function get_cur_code() {
		return LODOP.GET_VALUE("ProgramCodes", 0)
	}
	//还款凭证打印
	var repayPrintDoc = function(){
		var templateNo = "";
		var filename = "";
		var relNo = "";
		//fk : 放款凭证打印 ，否则还款凭证打印
		var docInfoConfig = "";
		if(busType == "fk"){
			docInfoConfig = queryTemplateConfig("10"); 
			relNo = fincId;
		}else{
			docInfoConfig = queryTemplateConfig("9");
			relNo = repayId;
		}
		var flag = docInfoConfig.flag;
		var result = "";
		if(flag == "success"){
			result = $.parseJSON(docInfoConfig.ajaxData)[0];
			templateNo = result.templateNo;
			filename = result.ext3;//文件名称
		}else{
			alert(docInfoConfig.msg,0);
			return;
		}
		$.ajax({
			url: webPath+"/mfTemplateModelRel/getIfSaveTemplateInfo",
			type:"post",
			data:{"relNo":relNo,templateNo:templateNo},
			async: false,
			dataType:"json",
			success:function(data){
				var filepath = "";
				var saveBtn = "1";
				var saveUrl = "";
				if(data.saveFlag !="0"){
					saveBtn = "0";
					filepath = data.filePath;
					filename = data.fileName;
				}else{
					saveUrl = webPath+"/component/model/saveModelInfo.jsp?relNo="+relNo+"&filename="+filename+"&templateNo="+templateNo;
				}
				var poCntJson = {
						filePath : filepath,
						fileName : filename,
						cusNo : cusNo,
						appId : appId,
						pactId : pactId,
						fincId : fincId,
						repayId :repayId,
						templateNo :templateNo,
						saveUrl :saveUrl,
						saveBtn : saveBtn,
						fileType : 0
				};
				mfPageOffice.openPageOffice(poCntJson);
			},error:function(){alert('error');}
		});
	};
	//查询配置在产品下的对应类型模板
	var queryTemplateConfig = function(templateType){
		var result = "";
		$.ajax({
			url: webPath+"/mfTemplateModelRel/getTemplateConfigInfo",
			type:"post",
			data:{"modelNo":pactModelNo,templateType:templateType},
			async: false,
			dataType:"json",
			success:function(data){
				result = data;
			},error:function(){alert('error');}
		});
		return result;
	};
