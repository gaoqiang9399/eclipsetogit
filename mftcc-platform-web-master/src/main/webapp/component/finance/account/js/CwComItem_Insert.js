	
var CwComItemInsertJs = function(window, $) {
	
	var _init = function () {
		dealCashType();
	};
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init
	};
}(window, jQuery);


	/**
	 * 一级科目添加js
	 * ajax保存
	 * @param obj
	 */
	function ajaxInsertThis(obj){//obj是form对象
		/*var amtType = $("select[name='xjFl']").val();
		if(amtType==0){
			alert("现金分类没有选择");
			return;
		}*/
		var accType = $("select[name='accType']").val();
		var accNo = $("input[name='accNo']").val();
		var accName = $("input[name='accName']").val();
		var selselct = $("#seqSelect1").val();
		$("input[name='seqSelect']").val(selselct);
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
	
			var dataParam = JSON.stringify($(obj).serializeArray());
			//loadingAnimate();
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					//loadingAnimateClose();
					if(data.flag == "success"){
						top.showName = accNo + '/' + accName;
						top.addFlag = true;
						myclose_showDialogClick();
						 // window.location.href="${webPath}/cwComItem/getListPage?accType="+accType;
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	};
	
	/**
	 * 处理辅助项，获取值
	 */
	function dealCashType(){
		var ano = $("input[name='accNo']");
		ano.attr("maxlength","4").attr("onkeyup","value=value.replace(/[^0-9]/g,'')");
		var cashhtml = $("input[name='seqNum']").parent();
		var seqselect = $('<select id=\"seqSelect1\" style=\"margin-left: 5px;border: 1px solid #d2d3d6;width: 100px;\"></select>');
		cashhtml.append(" <span class=\"seqspan\">自定义项：</span>").append(seqselect);
		seqselect.append("<option value=\"00\">(空白)</option>");
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
					for ( var i = 0; i < st.length; i++) {
						seqselect.append("<option value=\""+st[i].tx_type+"\">"+st[i].type_name+"</option>");
					}
					
				}
			}
		});
		
	};
	//二级科目以上的新增
	function dealLoadedMethod(type,kemuLvl,upaccno,pvalueType){
		var valType = parseInt(pvalueType);	
		var accnoLenth = 4+valType*(type-1);
		
		//修改科目的显示号
		var kemuhtml = $("input[name='accNo']").parent();
		$("input[name='accNo']").attr({maxlength:accnoLenth});
		$("input[name='accNo']").on('keyup',function(){
			$(this).val($(this).val().replace(/[^0-9]/g,''));
		});
		
		/*kemuhtml.append("<input type=\"text\" title=\"科目号\" name=\"accNo\" datatype=\"0\" mustinput=\"1\" maxlength=\"6\" " +
				"placeholder=\"如：100101\" onblur=\"func_uior_valTypeImm(this);\" onmousedown=\"enterKey()\" onkeyup=\"value=value.replace(/[^0-9]/g,'')\" onkeydown=\"enterKey();\">");
		*/
		//修改科目级别
		/*var acclvlhtml = $("input[name='accLvl']").parent();
		$("input[name='accLvl']").parent().html("");
		acclvlhtml.append("<input type=\"text\" title=\"科目级别：\" readonly=\"\" name=\"accLvshou\" datatype=\"0\"  class=\"form-control\" value="+kemuLvl+">");
		acclvlhtml.append("<input type=\"hidden\" title=\"科目级别：\" name=\"accLvl\" datatype=\"0\" mustinput=\"\" readonly=\"\" onblur=\"func_uior_valTypeImm(this);\" onmousedown=\"enterKey()\" onkeydown=\"enterKey();\" class=\"form-control\" value="+type+">");
*/
		var cashhtml = $("input[name='seqNum']").parent();
		var seqselect = $('<select id=\"seqSelect1\" style=\"margin-left: 5px;border: 1px solid #d2d3d6;width: 100px;\"></select>');
		cashhtml.append(" <span class=\"seqspan\">自定义项：</span>").append(seqselect);
		seqselect.append("<option value=\"00\">(空白)</option>");
		
		//获取科目辅助核算的标签
	//	var cashhtml = $("input[name='seqNum']").parent();
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
					for ( var i = 0; i < st.length; i++) {
						seqselect.append("<option value=\""+st[i].tx_type+"\">"+st[i].type_name+"</option>");
					}
					
				}
			}
		})
		
	}
