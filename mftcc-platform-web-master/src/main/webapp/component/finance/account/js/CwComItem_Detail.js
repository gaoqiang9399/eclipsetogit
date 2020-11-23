/**
 * 修改的方法
 * @param obj
 */
function ajaxUpdateThis(obj){
	
	var selselct = $("#seqSelect1").val();
	$("input[name='seqSelect']").val(selselct);
	
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			LoadingAnimate.start();
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray()); 
			$.ajax({
				url:url,
				data:{ajaxData:dataParam,tableId:top.tableId},
				type:'post',
				dataType:'json',
				success:function(data){
				LoadingAnimate.stop();
				if(data.flag == "success"){
				
						  top.addFlag = true;
						 /* if(data.htmlStrFlag == "1"){
							  top.htmlStrFlag = true;
							  top.htmlString = data.htmlStr;
							  //alert(top.htmlString);
						  }*/
						  myclose_showDialogClick();
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				},error:function(){
					alert(top.getMessage("FAILED_UPDATE"),0);
				}
			}); 
		}
	};
	
	/**
	 * 修改科目级别的属性
	 * @param type
	 * @param kemuLvl
	 */
	function dealkemuLvl(type,kemuLvl,listbean){
	/*	var acclvlhtml = $("input[name='accLvl']").parent();
		$("input[name='accLvl']").parent().html("");
		acclvlhtml.append("<input type=\"text\" title=\"科目级别：\" name=\"accLvshou\" datatype=\"0\" readonly=\"\" class=\"BOTTOM_LINE form-control\" value="+kemuLvl+">");
		acclvlhtml.append("<input type=\"hidden\" title=\"科目级别：\" name=\"accLvl\" datatype=\"0\" mustinput=\"\" readonly=\"\" onblur=\"func_uior_valTypeImm(this);\" onmousedown=\"enterKey()\" onkeydown=\"enterKey();\" class=\"BOTTOM_LINE form-control\" value="+type+">");
		*/
		
		var cashhtml = $("input[name='seqNum']").parent();
		//$("input[name='seqNum']").parent().html("");
		var seqselect = $('<select id=\"seqSelect1\" style=\"margin-left: 5px;border: 1px solid #d2d3d6;width: 100px;\"></select>');
		cashhtml.append(" <span class=\"seqspan\">自定义项：</span>").append(seqselect);
		seqselect.append("<option value=\"00\">(空白)</option>");
		
		var listbeanJson = $.parseJSON(listbean);
		//查询科目与辅助核算的数据
		$.ajax({
			url:webPath+'/cwFication/getFicationDataAjax',
			type:'post',
			//data:null,
			async:false,
			dataType:'json',
			error:function(){
				alert(top.getMessage("ERROR_SELECT"));
			},
			success:function(data){
				console.log(data);
				if (data.flag == 'success') {
					var st = data.listmap;
					length = st.length;
					if (length == 0) {
						return;
					}
					/*for ( var i = 0; i < st.length; i++) {
						cashhtml.append("<input type=\"checkbox\" name=\"seqNum\" title=\"辅助核算\" value=\""+st[i].tx_type+"\" onblur=\"func_uior_valTypeImm(this);\" onmousedown=\"enterKey()\" onkeydown=\"enterKey();\">"+st[i].type_name);
					}*/
					for ( var i = 0; i < st.length; i++) {
						seqselect.append("<option value=\""+st[i].tx_type+"\">"+st[i].type_name+"</option>");
					}
					
				}
			}
			
		})
		//添加默认的复选框
		var unCheckedBoxs = $("input[name='seqNum']").not("input:checked")
		unCheckedBoxs.each(function(){
			 var txtype = $(this).val();
			 for(var j=0;j<listbeanJson.length;j++){
				 var temtype = listbeanJson[j].txType;
				 if(txtype==temtype){
					 $(this).attr({checked:true});
				 }
				}
		 });
		//添加默认的下拉框
		 for(var j=0;j<listbeanJson.length;j++){
			 var temtype = listbeanJson[j].txType;
			 $("#seqSelect1").find("option[value='"+temtype+"']").attr("selected",true);
			}
		
	}
	