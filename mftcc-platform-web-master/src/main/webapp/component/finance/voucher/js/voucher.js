var proofs;//凭证字
var count=0;//附单据
var now_date;//当期那日期
var tx_date;//凭证日期
var bes;//凭证主表信息
var dc;//凭证分录表信息
var def_row=4;//默认分录行数
var row_count=4;//分录行数
var remarkData;//摘要
var comData;//科目
var assistData;//科目挂有辅助核算数据
var prevNo = '', nextNo = '';//凭证的上一张下一张凭证编号
var allVchNo = '';//所有凭证编号 以@隔开
var which = '';//所有业务类型
var businessNo = '';//所有业务编号

var sflag = 1; //做控制 2017年6月13日
var zyflag = 1;//做控制 2017年7月7日 lzs 控制摘要的下拉框
var kmflag = 1;//控制科目是否填写，如果填写为1，没填写就是0 2017年7月25日 修改

var cash_data=[];//现金流量分析数据[{2001:[10035,自由,100.00]},...] 科目号，现金流量项编号，主表项名称，金额
var cash_data_km_nos=[];//[2001,...]需要现金流量分析的科目号
var allow_negate=true,//允许负数标识
negate_red=true;//恒等于true
;(function($) {
	$.fn.voucher=function (ops){
		ops = $.extend({},$.fn.voucher.defaults, ops);
		$.fn.voucher.outer={};
		$.fn.voucher.outops=ops;
		var div_id=$(this).attr('id');
		//初始化凭证数据//用于哪种界面 add：新增，edit：可编辑， view：展示-不可编辑
		function pz_init(tt){
			jQuery.ajax({
				url: webPath+"/cwVoucherMst/getVoucherByNoAjax",
				data:{"voucherNo":ops.pzno, "which":ops.for_which},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						if(ops.for_which=="add"){
							proofs = data.formData.proofs;
							bes = data.formData.mst;
							comData = eval(data.formData.comData);
							remarkData = eval(data.formData.remarkData);
						}else if(ops.for_which=="edit"){
							proofs = data.formData.proofs;
							bes = data.formData.mst;
							dc = data.formData.dcl;
							row_count=data.formData.dclSize;
							comData = eval(data.formData.comData);
							remarkData = eval(data.formData.remarkData);
						}else if(ops.for_which=="view"){
							bes = data.formData.mst;
							dc = data.formData.dcl;
							row_count=data.formData.dclSize;
						}
						//初始化页面
						page_init($('#'+div_id), row_count);
					}else if(data.flag == "error"){
						alert(data.msg,0);
					}
				},error:function(data){
					alert(top.getMessage("FAILED_SAVE"),0);
				}
			});
			return true;
		}
		
		this.each(function(){
			LoadingAnimate.start();
			pz_init();
			LoadingAnimate.stop();
		});
	};
	
	$.fn.voucher.defaults={
		editable:"",//凭证是否可编辑，非登记，标错，自动，冲销，被冲销凭证不可编辑
		is_edit_stat:false,//当前是否编辑状态 -- false：不能编辑，显示编辑，取消按钮； true ：能编辑保存，显示保存，取消按钮。
		is_show_audit: false,//当前是否显示审核按钮。 flase：默认不显示， true： 显示审核按钮
		show_next_btn: false,//当前是否显示上一张下一张凭证按钮。 flase：默认不显示， true： 显示审核按钮
		is_JzVch:'N',//当前凭证是否记账， N:默认未记账， Y：已记账
		pzno:"",//凭证编号
		version:1,//1：老版本，制单和审批岗位分开；2：制单和审批岗位合并
		tel:"",//操作员编号
		is_used_by_pz:true,//是否在凭证中使用此插件
		for_which:"add",//用于那种界面 add：新增，edit：可编辑， view：展示-不可编辑,
		tmp:'undefined',
		cash_flow_analisys_only:false,//是否只做现金流分析用，
		for_cx:false,//是否用于冲销
		id:"pzPlugin",
		cb:function(){}
	};
})(jQuery);

//辅助核算项
var nameIndex=["ZY","BM","KH","ZDY"];
var assistantItem = [{"id":"1000001","name":"职员","status":"1","iscustom":"0"},
					 {"id":"1000002","name":"部门","status":"1","iscustom":"0"},
					 {"id":"1000003","name":"客户","status":"1","iscustom":"0"},
					 {"id":"100000x","name":"自定义","status":"1","iscustom":"1"}];

 //初始化客户、职员、项目、存货、供应商、部门、自定义选项

//初始化科目分类信息
function initSubInfo(){
	var itemStr="";
	for(var i=0;i<assistantItem.length;i++){
		itemStr+="<li style='display:none;'><label>"+assistantItem[i].name+":</label><span class='ui-combo-wrap ui-combo-active' id='item"+nameIndex[i]+"'>"
				+"<input type='text' class='input-txt' items='"+assistantItem[i].id+"' itemName='"+assistantItem[i].name+"' data-value='' autocomplete='off' readonly />"
				+"<i class='glyphicon glyphicon-search combo_query'></i></span></li>";
	}
//	itemStr+="<li style='display:none;'><label>自定义</label><span class='ui-combo-wrap ui-combo-active' id='itemZDY'>"
//				+"<input type='text' class='input-txt' autocomplete='off'/>"
//				+"<i class='trigger'></i></span></li>";
	$("#isItem ul").append(itemStr);
}

//初始化页面元素
function page_init(tt,row_count){
	$div_outer=$('<div class="wrapper" id="'+$.fn.voucher.outops.id+'"></div>');
	// 清理HTML
	$div_outer.html("");
	tt.append($div_outer);
	var topTools = createVchTop();//创建顶部工具mod-toolbar-top
	$div_outer.append(topTools);
	createVchWrap($.fn.voucher.outops.id,row_count);
	var btmTools = createVchBtn();//创建按钮工具 mod-toolbar-bottom	
	$div_outer.append(btmTools);
	//添加事件
	if($.fn.voucher.outops.for_which!='view'){
		//辅助核算弹出层
		var itemStr = $('<div id="isItem" style="top: 255px; left: 251px;display: none;"><ul></ul></div>')
		tt.append(itemStr);
		
		//摘要与科目弹出层
		var combowrap = $('<div id="COMBO_WRAP"></div>');
		var summarylist = $('<div class="ui-droplist-wrap ui-summary-wrap" style="">'
				+ '<div class="droplist" id="selSum"></div>'
				+ '<div class="extra-list-ctn">'
				+ '	<a href="javascript:void(0);" id="quickAddSummary" class="quick-add-link"> <i class="ui-icon-add"></i>新增摘要</a>'
				+ '</div>'
				+ '</div>');
		var subjectlist = $('<div class="ui-droplist-wrap ui-subject-wrap" style="">'
				+ '<div class="droplist" id="selSubject"></div>'
				+ '<div class="extra-list-ctn">'
				+ '<a href="javascript:void(0);" id="quickAddSubject" class="quick-add-link"><i class="ui-icon-add"></i>新增科目</a>'
				+ '</div>'
				+ '</div>');
		combowrap.append(summarylist).append(subjectlist);
		tt.append(combowrap);
		
		//初始化科目分类信息
		initSubInfo();
		//初始化凭证相关事件
		addVchEvent();
		//初始金额汇总事件
		addAmtEvend();
	}else{
		//初始金额汇总事件
		addAmtEvend();
	}
	fill_data();
	return true;
}

function createVchTop(){
	var topTool = $('<div class="mod-toolbar-top" id="toolTop"></div>');
	var topBtn = $('<div class="left" style="float:left;"></div>');
	if($.fn.voucher.outops.for_which=='add'){
		topBtn.append('<a class="ui-btn ui-btn-sp" id="renewB">保存并新增</a>');
		topBtn.append('<a class="ui-btn" id="saveB">保存</a>');
	}else if($.fn.voucher.outops.for_which=='edit'){
		topBtn.append('<a class="ui-btn ui-btn-sp editB" id="">保存</a>');
	}
//	topBtn.append('<a class="ui-btn"  onclick="doPrint(\'打印预览...\')">打印</a>');
	if($.fn.voucher.outops.is_show_audit && which==''){
		if($.fn.voucher.outops.is_JzVch=='Y'){
			//2017年8月17日  将审核修改成记账，将反审核修改成反记账
			topBtn.append('<a class="ui-btn" id="revAutit" onclick="voucherRevAudit();">反记账</a>');
		}else{
			topBtn.append('<a class="ui-btn" id="vchAudit" onclick="voucherAudit();">记账</a>');
		}
	}
	topTool.append(topBtn);
	
	var moreBtn = $('<div class="ui-btn-menu fl"></div>');
	var more = $('<a id="more" class="ui-btn menu-btn">更多<b></b></a>');
	var lis = '<li><a href="javascript:void(0);" id="saveAsTemp">保存为凭证模板</a></li>';
	if($.fn.voucher.outops.for_which=='add'){
		lis += '<li><a href="javascript:void(0);" id="createWithTemp">从模板生成凭证</a></li>';
	}
//	lis += '<li><a href="javascript:void(0);" id="userSetting">选项</a></li>';
	var moreCon = $('<div class="con more-operate-con"><ul class="more-operate">'+lis+'</ul></div>');
	moreBtn.append(more).append(moreCon);
	topTool.append(moreBtn);
	
	var rightBtn = $('<div class="right" style="float:right;" id="prevNextDiv"></div>');//20170608 yht加style，调整ie9兼容
	var keybd = $('<div id="keyboardshow" class="fl" style="display: none;"><a href="javascript:void(0);" id="keyboard" title="如何快捷录入"></a><img src="'+webPath+'/component/finance/voucher/images/vchKeyBoard.png" id="imgKeyBoard"></div>');
	var sxBtn = $('<a class="ui-btn-prev ui-btn-prev-dis" id="prev" title="上一张"><b></b></a><a class="ui-btn-next ui-btn-next-dis" id="next" title="下一张"><b></b></a>');
	if($.fn.voucher.outops.show_next_btn){
		rightBtn.append(keybd).append(sxBtn);
	}
	topTool.append(rightBtn);
	
	return topTool;
}

function createVchBtn(){
	var btmTools = $('<div class="mod-toolbar-bottom" id="toolBottom" style="overflow:hidden;"></div>');//20170608 yht加style，调整ie9兼容
	var fr = $('<div class="fr" style="float:right;margin-top:10px;"></div>');//20170608 yht加style，调整ie9兼容
	var btn = '';
	
	if($.fn.voucher.outops.for_which=='add'){
		btn += '<a class="ui-btn ui-btn-sp" id="renewB">保存并新增</a>';
		btn += '<a class="ui-btn m0" id="saveB">保存</a>';
	}else if($.fn.voucher.outops.for_which=='edit'){
		btn += '<a class="ui-btn ui-btn-sp editB" id="">保存</a>';
	}
	if($.fn.voucher.outops.is_show_audit && which==''){
		// 将反审核修改成反记账，将审核修改成记账 2017年8月17日 
		if($.fn.voucher.outops.is_JzVch=='Y'){
			btn += '<a class="ui-btn m0" id="revAutit" onclick="voucherRevAudit();">反记账</a>';
		}else{
			btn += '<a class="ui-btn m0" id="vchAudit" onclick="voucherAudit();">记账</a>';
		}
	}
	fr.append(btn);
	btmTools.append(fr);
	return btmTools;
}

//新增凭证表结构数据
function createVchWrap(tagId, rownum){
	var vchwrap = $('<div class="voucher_wrap"></div>');
	//凭证顶部标题
	var vchtop = $('<div class="voucher_top"></div>');
	//凭证字、凭证日期
	var vchmark = $('<div class="mark_wrap"></div>');
	var mark1;
	var mark2;
	var mark3;
	var vchweek;
	if($.fn.voucher.outops.for_which=='view'){
		mark1 = $('<span class="txt">凭证字</span><span class="txt" id="pzPrefix"></span><input type="hidden" id="pzProofNo" name="pzProofNo"><input type="hidden" id="which" name="which"><input type="hidden" id="businessNo" name="businessNo">');
		mark2 = $('');
		mark3 = $('<span class="date_wrap"><span class="txt">日期</span><span class="txt" id="vch_date"></span><span class="paddrl60_span"></span></span>');
		//标题、期、附件数
		vchweek = $('<div class="tit_wrap"><h1 class="voucher_tit">记账凭证</h1><span id="vch_year"></span>年第<span id="vch_period"></span>期</div><span class="attach_wrap"><span class="txt">附单据</span><span class="txt" id="vch_attach">0</span><span class="txt"> 张</span></span>');
	}else{
		mark1 = $('<span class="txt">凭证字</span><select class="ui-combo-wrap" id="pzProofNo" name="pzProofNo" style="width: 58px;"></select><input type="hidden" id="voucherNo" name="voucherNo"><input type="hidden" id="which" name="which"><input type="hidden" id="businessNo" name="businessNo">');
		mark2 = $(' <span class="ui-spinbox-wrap"><input type="text" class="ui-spinbox input-txt" value="0" id="vch_num" autocomplete="off"><span class="btn-wrap"><a class="btn-up"></a><a class="btn-down"></a></span></span><span class="txt">号</span>');
		mark3 = $('<span class="date_wrap"><span class="txt">日期</span><input type="text" class="ui-input ui-datepicker-input" id="vch_date" name="vch_date" readonly></span>');
		//标题、期、附件数
		vchweek = $('<div class="tit_wrap"><h1 class="voucher_tit">记账凭证</h1><span id="vch_year"></span>年第<span id="vch_period"></span>期</div><span class="attach_wrap"> 附单据 <input type="text" class="ui-input" id="vch_attach" value="0"> 张 </span>');
	}
	vchmark.append(mark1).append(mark2).append(mark3);
	vchtop.append(vchmark).append(vchweek);
	vchwrap.append(vchtop);
	
	//凭证分录主体
	var vchTab = $('<table class="voucher" id="voucher"></table>');
	var unit = '<div class="money_unit"><span>亿</span> <span>千</span> <span>百</span> <span>十</span> <span>万</span><span>千</span> <span>百</span> <span>十</span> <span>元</span> <span>角</span><span class="last">分</span></div>';
	var thead = $('<thead><tr><th class="col_operate"></th><th class="col_summary" colspan="2">摘要</th><th class="col_subject" colspan="2">会计科目</th><th class="col_money"><strong class="tit">借方金额</strong>'+unit+'</th><th class="col_money col_credit"><strong class="tit">贷方金额</strong>'+unit+'</th></tr></thead>');
	var tbody = $('<tbody></tbody>');
	// 生成数据行
	if(rownum<=def_row){
		rownum=def_row;
	}
	for(var i=1;i<=rownum;i++){
		var vchTr = addVchTr();
		tbody.append(vchTr);
	}
	var tfoot = $('<tfoot><tr><td class="col_operate"></td><td colspan="4" class="col_total">合计：<span id="capAmount"></span></td><td class="col_debite"><div class="cell_val debit_total" id="debit_total"></div></td><td class="col_credit"><div class="cell_val credit_total" id="credit_total"></div></td></tr></tfoot>');
	vchTab.append(thead).append(tbody).append(tfoot);
	//凭证下部
	var vchfoot = $('<div class="vch_ft"></div>');
	if($.fn.voucher.outops.for_which=='add'){
		vchfoot.append('制单人：<span id="vch_people"></span>');
	}else{//yht浏览器兼容修改 style="float:right;"
		var mtime = $('<span class="fr f12 g6" style="float:right;" id="modifyTime">最后修改时间：<span></span></span> <span class="fr f12 g6" style="float:right;" id="createTime">录入时间：<span></span></span> <span class="fr f12 g6" style="float:right;" id="ldr_people">记账人：<span></span></span> 制单人：<span id="vch_people"></span>');
		vchfoot.append(mtime);
	}
	var auditTag = $('<div id="auditTag" class="i i-warehouse cw-chapter-font hidden"><div class="chapter-name-div">已记账</div></div>');
	vchwrap.append(vchtop).append(vchTab).append(vchfoot).append(auditTag);	
			
	$("#"+tagId).append(vchwrap);
}

/**
 * 添加凭证分录行
 * @param obj
 * @returns
 */
function addVchTr(){
	var operate_td = '';
	var option_zy = '';
	var option_km = '';
	if($.fn.voucher.outops.for_which!='view'){
		operate_td = "<div class='operate'><a title='增加分录' class='add'></a><a title='删除分录' class='del'></a></div>";
		option_zy = "<div class='option'><a class='selSummary'>摘要</a></div>";
		option_km = "<div class='option'><a class='selSub'>科目</a></div>";
	}
	var trStr = $("<tr class='entry_item'><td class='col_operate'>"+operate_td+"</td><td class='col_summary' data-edit='summary'><div class='cell_val summary_val'></div></td><td class='col_option'>"+option_zy+"</td><td class='col_subject' data-edit='subject'><div class='subject-dtl'><div class='cell_val subject_val'></div></div></td><td class='col_option'>"+option_km+"</td><td class='col_debite' data-edit='money'><div class='cell_val debit_val'></div></td><td class='col_credit' data-edit='money'><div class='cell_val credit_val'></div></td></tr>");
	trStr.on({
		mouseenter: function() {
			$(this).siblings("tr").removeClass("current");
			$(this).addClass("current");
		},
		mouseleave: function() {
			$(this).removeClass("current");
		}
	});
	return trStr;
}

//表格填充数据
function fill_data(){
	$('#which').val(which);
	$('#businessNo').val(businessNo);
    var vehmarks,opt,voucherObj;
    var i;
	if($.fn.voucher.outops.for_which=="add"){
		//添加凭证字选择框数据
		if(proofs){
			vehmarks = eval(proofs);
			opt = $('#pzProofNo')[0];
			opt.options.length=0;
			for(i=0; i<vehmarks.length; i++){
				if('Y'==vehmarks[i].isAuto)
					opt.add(new Option(vehmarks[i].pzPrefix, vehmarks[i].pzProofNo, true, true));
				else
					opt.add(new Option(vehmarks[i].pzPrefix, vehmarks[i].pzProofNo));
			}
		}
		$('#vch_num').val(bes.voucherNoteNo);
		$('#vch_date').val(bes.voucherDate);
		$('#vch_year').text(bes.weeks.substring(0, 4));
		$('#vch_period').text(bes.weeks.substring(4));
		$('#vch_people').text(bes.opName);
	}else if($.fn.voucher.outops.for_which=="edit"){
		//添加凭证字选择框数据
		if(proofs){
			vehmarks = eval(proofs);
			opt = $('#pzProofNo')[0];
			opt.options.length=0;
			for(i=0; i<vehmarks.length; i++){
				if(proofs[i].pzProofNo==bes.pzProofNo){
					opt.add(new Option(vehmarks[i].pzPrefix, vehmarks[i].pzProofNo, true, true));
				}else{
					opt.add(new Option(vehmarks[i].pzPrefix, vehmarks[i].pzProofNo));
				}
			}
		}
		$('#voucherNo').val(bes.voucherNo);
		$('#pzProofNo').val(bes.pzProofNo);
		$('#vch_num').val(bes.voucherNoteNo);
		$('#vch_date').val(bes.voucherDate);
		$('#vch_attach').val(bes.counts);
		$('#vch_year').text(bes.weeks.substring(0, 4));
		$('#vch_period').text(bes.weeks.substring(4));
		$('#vch_people').text(bes.opName);
		$('#modifyTime').append(bes.occTime);
		$('#createTime').append(bes.createDate + ' ' + bes.createTime);
		if(bes.pzFlag > 0){
			$('#ldr_people').append(bes.jzTelName);
//			$('#auditTag').addClass('has-audit');
			$('#auditTag').removeClass('hidden');
		}
		voucherObj = $('#voucher>tbody');
		for(i = 0; i < row_count; i++){
			setVchTrValue(voucherObj.find('tr:eq('+i+')'), dc[i]);
		}
		voucherObj.find(".edit_money:last-child").blur();
	}else if($.fn.voucher.outops.for_which=="view"){
		$('#voucherNo').val(bes.voucherNo);
		$('#pzProofNo').val(bes.pzProofNo);
		$('#pzPrefix').text(bes.pzPrefix + '-' + bes.voucherNoteNo);
		$('#vch_date').text(bes.voucherDate);
		$('#vch_attach').text(bes.counts);
		$('#vch_year').text(bes.weeks.substring(0, 4));
		$('#vch_period').text(bes.weeks.substring(4));
		$('#vch_people').text(bes.opName);
		$('#modifyTime').append(bes.occTime);
		$('#createTime').append(bes.createDate + ' ' + bes.createTime);
		if(bes.pzFlag > 0){
			$('#ldr_people').append(bes.jzTelName);
//			$('#auditTag').addClass('has-audit');
			$('#auditTag').removeClass('hidden');
		}
		voucherObj = $('#voucher>tbody');
		for(i = 0; i < row_count; i++){
			setVchTrValue(voucherObj.find('tr:eq('+i+')'), dc[i]);
		}
		voucherObj.find(".edit_money:last-child").blur();
	}else{
		return false;
	}
	// 	加载辅助核算数据
	if($.fn.voucher.outops.for_which!="view"){
		getAssistData();
	}
	
	//回调函数
	if($.fn.voucher.outops.cb){
		$.fn.voucher.outops.cb();
	}
}

/**
 * 获取辅助核算数据
 */
function getAssistData(){
	jQuery.ajax({
		url: webPath+"/cwRelation/getRelaForVchAjax",
		data:{},
		type:"POST",
		dataType:"json",
		beforeSend:function(){  
		},success:function(data){
			if(data.flag == "success"){
				assistData = data;
			}else if(data.flag == "error"){
				alert(data.msg,0);
			}
		},error:function(data){
			alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	});
}

/**
 * 封装凭证分录数据
 * @param obj
 * @param data
 */
function setVchTrValue(obj, data){
	//摘要
	var summary = $(obj).children('.col_summary');
	summary.children(".cell_val").text(data.txDesc);
	
	//科目
	var subject = $(obj).children('.col_subject').children('.subject-dtl');
	subject.children(".cell_val").text(data.accHrt);
	if(data.itemsValueName){
		subject.children(".cell_val").append('<span>' + data.itemsValueName + '</span>');
	}
	
	//可编辑
	var summaryCount = summary.children(".edit_summary").length;
	if(summaryCount == 0) {
		var inSummary = $("<input type='text' class='edit_summary' autocomplete='off'>");
		summary.append(inSummary.css("display", "none"));
	}
	summary.children(".edit_summary").val(data.txDesc);
	
	var subjectCount = subject.children(".edit_subject").length;
    var dtlStr;
	if(subjectCount == 0) {
		dtlStr = $("<input type='text' class='edit_subject' autocomplete='off'>");
		subject.append(dtlStr.css("display", "none"));
	}
	subject.children(".edit_subject").val(data.accHrt);
	//辅助核算
	var itemCount = subject.siblings(".edit_items").length;
	if(itemCount == 0) {
		dtlStr = $("<input type='hidden' class='edit_items' autocomplete='off'>");
		subject.after(dtlStr);
	}
	subject.siblings(".edit_items").val(data.itemsValueNo);
	//金额
	var money;
	if(data.dcInd == '1'){
		money = $(obj).children('.col_debite');
	}else{
		money = $(obj).children('.col_credit');
	}
	var moneyCount = subject.children(".edit_money").length;
	if(moneyCount == 0) {
		var inputStr = $("<input type='text' class='edit_money'  datatype='12' maxlength='12'  autocomplete='off'>");
		money.append(inputStr.css("display", "none"));
	}
	var editMoney = data.txAmt;
	if(Number(editMoney)!=0){
		if(editMoney.indexOf(".") !=-1) {
			if(editMoney.charAt(0)=="."){
				money.children(".cell_val").html(editMoney.substring(1,3));
				money.children(".edit_money").val(editMoney.substring(0,3));
			}else{
				var intMoney = editMoney.split(".")[0];
				var subMoney = editMoney.split(".")[1].substring(0, 2);
				money.children(".cell_val").html(intMoney + subMoney);
				money.children(".edit_money").val(intMoney + '.' + subMoney);
			}
		} else {
			money.children(".cell_val").html(editMoney + "00");
			money.children(".edit_money").val(editMoney);
		}
	}
}

/**
 * 添加凭证触发事件
 * @param obj
 * @returns
 */
function addVchEvent(){
	
	//计数器增加单击事件
	$(".ui-spinbox-wrap .btn-up").click(function() {
		$(".ui-spinbox-wrap").addClass("ui-spinbox-active");
		var countValue = $(this).parent().siblings().val();
		if(countValue < 500)
			countValue++;
		$("#vch_num").val(countValue);
		$("#vch_num").focus();
	});

	//计数器减少单击事件
	$(".ui-spinbox-wrap .btn-down").click(function() {
		$(".ui-spinbox-wrap").addClass("ui-spinbox-active");
		var countValue = $(this).parent().siblings().val();
		if(countValue > 1)
			countValue--;
		$("#vch_num").val(countValue);
		$("#vch_num").focus();
	});

	//计数器焦点事件
	$(".ui-spinbox-wrap").hover(function() {
		$(this).addClass("ui-spinbox-hover");
		$(this).children(".btn-wrap").addClass("btn-wrap-hover");
	}, function() {
		$(this).removeClass("ui-spinbox-hover");
		$(this).children(".btn-wrap").removeClass("btn-wrap-hover");
	});
	
	//日期插件
	$('#vch_date').bind('click', function(){
		var menu = this;
		fPopUpCalendarDlg({
			isclear: false,
			choose:function(data){
				$(menu).change();
			}	
		});
	});
	
	//日期、凭证字变更事件 
	$("#pzProofNo,#vch_date").on("change", function(event) {
		var pzProofNo = $('#pzProofNo').val();
		var vchDate = $('#vch_date').val();
		var ajaxData = "{'pzProofNo':'"+pzProofNo+"', 'vchDate':'"+vchDate+"'}";
		$.get(webPath+'/cwVoucherMst/getVchMaxNoteNoAjax', {ajaxData:ajaxData}, function(data){
			if(data){
				$('#vch_num').val(data.vchData.voucherNoteNo);
				$('#vch_year').text(data.vchData.weeks.substring(0, 4));
				$('#vch_period').text(data.vchData.weeks.substring(4));
			}
		});
	});
	
	//tr 获取焦点事件	
	$("#voucher tbody tr").on({
		mouseenter: function() {
			$(this).siblings("tr").removeClass("current");
			$(this).addClass("current");
		},
		mouseleave: function() {
			$(this).removeClass("current");
		}
	});
	
	//文本框键盘按下事件
	$("#voucher").on("keyup", ".edit_summary,.edit_subject", function(event) {
	
		var cls = $(this).attr('class');
		var query = $(this).val();
        var search,item;
		if('edit_summary'==cls){
			search = _search(query, remarkData, 7, 'voucherRemark');
			if (search.length) {
				$("#selSum").empty();
				for (item =0; item <search.length;item++){
					var itemStr = "<div class='list-item' data-value='" + search[item].voucherRemark + "'>" + search[item].voucherRemark + "</div>";
					$("#selSum").append(itemStr);
				}
			}
		}else if('edit_subject'==cls){
			search = _search(query, comData, 7, 'accNo', 'accName');
			if (search.length) {
				$("#selSubject").empty();
				// for (var item in search)
				for (item =0; item <search.length;item++){
					var itemValue = "<div class='list-item' data-value='" + search[item].accNo + "'>" + search[item].accNo + "/" + search[item].accName + "</div>";
					$("#selSubject").append(itemValue);
				}
	        }
		}
	});
	
	//文本框光标离开事件
	$("#voucher").on("blur", ".edit_summary,.edit_subject", function(event) {
		var cls = $(this).attr('class');
		var query = $(this).val();
        var subObj;
		if('edit_subject'==cls){
			//验证是否输入正确
			var subCode = query.split("/")[0];//科目编码
			var comBean = checkComItem(subCode);
			if(comBean){
				//防止只输入科目号
				if(String(query).indexOf('/')>0){
					$(this).siblings('.cell_val').html(query);
				}else{
					$(this).val(subCode + '/' + comBean.accName);
					$(this).siblings('.cell_val').html(subCode + '/' + comBean.accName);
					subObj = assistData[subCode];//科目实体
					if(subObj!=null){
						$(this).parent().click();
					}
				}
			}else{
				query = '';
				$(this).val('');
				$(this).siblings('.cell_val').html('');
			}
			var target  = event.target || event.srcElement;
			var subject_dtl = $('.subject-dtl');
			if($(target).closest(subject_dtl).length > 0){
				if(query == ''){
					var trIndex = $(this).closest('tr').attr('trIndex');
					var itemIndex = $("#isItem").attr('trIndex');
					if(trIndex == itemIndex){
						$("#isItem").css("display","none");
					}
				}else{
					subObj = assistData[query.split("/")[0]];//科目实体
					if(subObj==null){
						$("#isItem").css("display","none");
						zyflag=1;//无辅助核算
					}
				}
			}
		}else{
			$(this).siblings('.cell_val').html(query);
		}
	});
	
	//摘要、科目对应选项hover事件	
	$('#selSum div.list-item,#selSubject div.list-item').on({
		mouseenter: function() {
			$(this).addClass("on");
			$(this).siblings().removeClass("on");
		},
		mouseleave: function() {
			$(this).removeClass("on");
		}
	});
	
	//摘要选项单击事件
	$("#selSum").on("click", ".list-item", function() {
		var trIndex = $(this).parent().parent().attr("name");
		$(this).addClass("selected");
		$(this).siblings().removeClass("selected");
		$("#COMBO_WRAP").children("div").eq(0).css("display", "none");
		var $tr = $("#voucher tbody tr").eq(trIndex).children(".col_summary");
		$tr.children("div").html($(this).html());
		$tr.children(".edit_summary").val($(this).attr("data-value"));
		$tr.children(".cell_val").html($(this).html());
		$tr.children(".cell_val").css("display", "block");
		$tr.children(".edit_summary").css("display", "none");
		$("#COMBO_WRAP").children("div").eq(0).css({"display": "none"});
		$("#COMBO_WRAP").children("div").eq(0).removeAttr("name");
		$("#selSum div").removeClass("selected");
	});

	//弹出摘要
	$("#voucher .selSummary").on("click", function() {
		//判断摘要文本框是否存在,不存在则添加
		var inputCount = $(this).parents("td").prev().children(".edit_summary").length;
		if(inputCount == 0) {
			var inSummary = "<input type='text' class='edit_summary' autocomplete='off' readonly>";
			$(this).parents("td").prev().append(inSummary);
			$(this).parents("td").prev().children(".edit_summary").hide();
		}
//		var trIndex = $(this).parents("tr").index();
		var summaryo = $(this).parents("td").prev();
		openRemarksDialog(function(data){
			if(data){
				summaryo.children(".cell_val").text(data);
				summaryo.children(".edit_summary").val(data);
			}
		});
	});
	
	//弹出摘要新增框
	$(".ui-summary-wrap").on("click", "#quickAddSummary", function(a) {
		var trIndex = $("#COMBO_WRAP").children("div").eq(0).attr("name");
		var summaryo = $('#voucher>tbody>tr:eq('+trIndex+')').children('.col_summary');
		openRemarksDialog(function(data){
			if(data){
				summaryo.children(".cell_val").text(data);
				summaryo.children(".edit_summary").val(data);
			}
		});
	});

	//科目下拉框单击事件
	$("#voucher").on("click", ".subject-dtl", function() {
		
		var divHtml = $(this).children(".cell_val").html();
		$("#COMBO_WRAP").children("div").eq(0).hide();
		$("input.edit_summary,input.edit_subject").hide();
		$("div.curdiv").show().removeClass("curdiv");
		var inputCount = $(this).children(".edit_subject").length;
        var dtlStr;
		if(inputCount == 0) {
			dtlStr = "<input type='text' class='edit_subject' autocomplete='off'>";
			$(this).append(dtlStr);
		}
		//辅助核算
		var Count = $(this).siblings(".edit_items").length;
		if(Count == 0) {
			dtlStr = "<input type='hidden' class='edit_items' autocomplete='off'>";
			$(this).after(dtlStr);
		}
		$(this).children(".edit_subject").css("display", "table-cell");
		$(this).children(".cell_val").css("display", "none");
		$(this).children(".cell_val").addClass("curdiv");
		var trIndex = $(this).parents("tr").index();
		
		var subCode = divHtml.split("/")[0];//科目编码

		var subObj = assistData[subCode];//科目实体
		if(subObj!=null){
//			return false;
			showSubDetailInfo(trIndex,subObj);
			var subAll=$(this).siblings(".edit_items").val();
			var items = subAll.split('|');
			var itemCount = items.length;
			$('#isItem').find('input').each(function(index){
				for (var i = 0; i < itemCount; i++) {
				//for(var i in items){
					var item = items[i].split('@');
					if(item[0]==$(this).attr('items')){
						$(this).attr('data-value', item[2]);
						$(this).val(item[3]);
					}
				}
			});
		}else{
			var subVal=$(this).children(".edit_subject").val();
			$(this).children(".edit_subject").val($(this).children(".cell_val").html());
			
			//获取当前元素的绝对位置
			var tdTop = parseInt($(this).position().top);
			var tdLeft = parseInt($(this).position().left);
			var indexSz = $("#voucher > tbody").children("tr").length;
			var topOrBot = 0;
			if(trIndex == (indexSz -1))
				topOrBot = tdTop - 214;
			else
				topOrBot = 60 + tdTop;
			
			$("#COMBO_WRAP").children("div").eq(1).css({
				"display": "block",
				"left": (tdLeft+1)+"px",
				"top": "" + topOrBot + "px"
			});
			$("#COMBO_WRAP").children("div").eq(1).attr("name", trIndex);
			$("#selSubject div").removeClass("selected");
			$("#selSubject div[data-value='"+subVal+"']").addClass("selected");
			var search = _search(subVal, comData, 7, 'accNo', 'accName');
			if (search.length) {
				$("#selSubject").empty();
				//for (var item in search)
				for (var item =0; item <search.length;item++)  {
					var itemValue = "<div class='list-item' data-value='" + search[item].accNo + "'>" + search[item].accNo + "/" + search[item].accName + "</div>";
					$("#selSubject").append(itemValue);
				}
			}
			//$(this).children(".edit_subject").focus();
			$(this).children(".edit_subject").focusEnd();
		}
	});
	
	//摘要键盘按需事件
	/****
	 * 
	 */
	
	//科目下拉框选项单击事件
	$("#selSubject").on("click", ".list-item", function() {

		var trIndex = $(this).parent().parent().attr("name");//行索引
		$(this).addClass("selected");
		$(this).siblings().removeClass("selected");
		$("#COMBO_WRAP").children("div").eq(1).css("display", "none");
		var $tr = $("#voucher tbody tr").eq(trIndex).children().children(".subject-dtl");
		$tr.children(".edit_subject").val($(this).html());
		$tr.children(".cell_val").html($(this).html()); 
		$("#COMBO_WRAP").children("div").eq(1).removeAttr("name");
		$("#selSubject div").removeClass("selected");
		var subCode=$(this).html().split("/")[0];//科目编码
		var subObj = assistData[subCode];//科目实体
		if(subObj!=null){
			showSubDetailInfo(trIndex,subObj);
		}else{
			$tr.children(".cell_val").css("display", "table-cell");
			$tr.children(".edit_subject").css("display", "none");
			zyflag=1;
		}
	});

	//弹出科目框
	$("#voucher").on("click", ".selSub", function(a) {
		//判断摘要文本框是否存在,不存在则添加
		var $prevTd = $(this).parents("td").prev().children(".subject-dtl");
		var inputCount = $prevTd.children(".edit_subject").length;
		if(inputCount == 0) {
			var dtlStr = "<input type='text' class='edit_subject' autocomplete='off' readonly>";
			$prevTd.append(dtlStr);
			$prevTd.children(".edit_subject").hide();
		}
		openComItemDialog('0', function(data){
			if(data){
				$prevTd.children(".cell_val").text(data.showName);
				$prevTd.children(".edit_subject").val(data.showName);
				var subObj = assistData[data.id];//科目实体
				if(subObj!=null){
					$prevTd.click();
				}
			}
		});
	});
	
	//弹出科目新增框
	$(".ui-subject-wrap").on("click", "#quickAddSubject", function(a) {
		var trIndex = $("#COMBO_WRAP").children("div").eq(1).attr("name");
		var $prevTd = $('#voucher>tbody>tr:eq('+trIndex+')').children('.col_subject').children('.subject-dtl');
		top.addFlag = false;
		top.showName = '';
		top.createShowDialog(encodeURI(webPath+"/cwComItem/input?accType=1"),"添加一级科目",'70','70',function(){
			if(top.addFlag){
				$prevTd.children(".cell_val").text(top.showName);
				$prevTd.children(".edit_subject").val(top.showName);
			}
		});
	});

	//插入凭证行
	$("#voucher").on("click", ".add", function(a) {
		var trIndex = $(this).parents('tr').index();
		var trStr = $("<tr class='entry_item'><td class='col_operate'><div class='operate'><a title='增加分录' class='add'></a><a title='删除分录' class='del'></a></div></td><td class='col_summary' data-edit='summary'><div class='cell_val summary_val'></div></td><td class='col_option'><div class='option'><a class='selSummary'>摘要</a></div></td><td class='col_subject' data-edit='subject'><div class='subject-dtl'><div class='cell_val subject_val'></div></div></td><td class='col_option'><div class='option'><a class='selSub'>科目</a></div></td><td class='col_debite' data-edit='money'><div class='cell_val debit_val'></div></td><td class='col_credit' data-edit='money'><div class='cell_val credit_val'></div></td></tr>");
		trStr.on({
			mouseenter: function() {
				$(this).siblings("tr").removeClass("current");
				$(this).addClass("current");
			},
			mouseleave: function() {
				$(this).removeClass("current");
			}
		});
		$(this).parents('tr').before(trStr);
		$("#voucher tbody tr").eq(trIndex).addClass("current");
		$(this).parents('tr').removeClass("current");
	});

	//删除凭证行
	$("#voucher").on("click", ".del", function(a) {
		var trCount = $("#voucher .entry_item").length;
		if(trCount > 2) {
			var index = $(this).index();
			$(this).parents('tr').remove();
			$("#voucher .entry_item:eq(" + index + ")").addClass("current");
		} else {
			//layer.alert("请至少保留二条分录！");
			//layer.alert(top.getMessage("ONLY_CW_VOUCHER_TWOENTRY", ""));
			alert(top.getMessage("ONLY_CW_VOUCHER_TWOENTRY", ""),0);
		}
	});

	//借方金额单击事件
	$("#voucher").on("click", "tbody tr .col_debite,tbody tr .col_credit", function() {
		$("#COMBO_WRAP").children("div").eq(1).hide();
		$("#COMBO_WRAP").children("div").eq(0).hide();
		$("input.edit_summary,input.edit_subject").hide();
		$("div.curdiv").show();
		var inputCount = $(this).children(".edit_money").length;
		if(inputCount == 0) {
			var inputStr = $("<input type='text' class='edit_money' datatype='12' maxlength='12' autocomplete='off'>");
			$(this).append(inputStr);
			$(this).children(".cell_val").hide();
		} else {
			$(this).children(".edit_money").show();
			$(this).children(".cell_val").hide();
		}
		//$(this).children(".edit_money").focus();
		$(this).children(".edit_money").focusEnd();
		
	});

	//文本框键盘按下事件
	$("#voucher").on("keyup", ".col_debite,.col_credit", function(event) {
		//onpaste="return !clipboardData.getData('text').match(//D/)" ondragenter="return false" style="ime-mode:Disabled"  
		//若是输入借方金额，贷方金额值删除
		$(this).siblings(".col_debite").children(".cell_val").html("");
		$(this).siblings(".col_debite").children(".edit_money").val("");

		//若是输入贷方金额，借方金额值删除
		$(this).siblings(".col_credit").children(".cell_val").html("");
		$(this).siblings(".col_credit").children(".edit_money").val("");

		var keyCode = event.keyCode; //键盘输入值
		var editMoney = $(this).children(".edit_money").val();
		if(typeof(editMoney) == "undefined" || editMoney == "" || editMoney.length == 0) {
			if(!isAmtKeyCode(keyCode)) {
				$(this).children(".edit_money").val("");
			}
		} else {
			if(isAmtKeyCode(keyCode)) {
				//如果存在小数点，且还输入小数点
				var isPoint = editMoney.substring(0, editMoney.length - 1).indexOf(".") > -1;
				if((isPoint && keyCode == 190) || (isPoint && keyCode == 110)) {
					$(this).children(".edit_money").val(editMoney.substring(0, editMoney.length - 1));
				}
				//如果不是第一个输入负号
				if((keyCode == 189 ||keyCode == 109) && editMoney.indexOf('-') != 0) {
					$(this).children(".edit_money").val(editMoney.substring(0, editMoney.length - 1));
				}
			}else{
				if(keyCode==8){//backspace键
					//$(this).children(".edit_money").val(editMoney.substring(0, editMoney.length - 1));
//					$(this).children(".edit_money").val(editMoney.substring(0, editMoney.length));
				}else if(keyCode==13){//enter键
					$(this).children(".edit_money").blur();
				}else if(keyCode==229){//enter键   针对win10浏览器
					//$(this).children(".edit_money").blur();
					var win10num = $(this).children(".edit_money").val();
					var win10flag = isNaN(win10num);//判断是否未数字，是数字返回false,不是数字返回true
					if(win10flag){
						$(this).children(".edit_money").val(editMoney.substring(0, editMoney.length - 1));
					}
				}else{
					
					$(this).children(".edit_money").val(editMoney.substring(0, editMoney.length - 1));
				}
				
			}
		}
	});
	
	//科目分类信息单击事件
	$("#isItem .combo_query").on("click",function(){
//		var trTop = parseInt($(this).parent().offset().top);
//		var trLeft = parseInt($(this).parent().offset().left);
//		var indexStr = $(this).parent().attr("id").replace("item","");
//		var showCount = parseInt($(this).parent().attr("showCount"))+1;
//		alert("showCount=="+showCount);
//		if($("#div"+indexStr).is(":hidden")){
//			$("div[id^='div']").hide();
//			$("#div"+indexStr).css({"display":"block","top":(trTop+29)+"px","left":trLeft+"px"});
//		}else{
//			$("#div"+indexStr).css("display","none");
//		}
		var prevInput = $(this).prev('input');
		var txType = prevInput.attr('items');
		openAssistDialog(txType, function(data){
			if(data){
				prevInput.val(data.txName);
				prevInput.attr('data-value', data.txCode);
			}
		});
	});
	
	//科目分类信息选项单击事件
	$("div[id^='div']").on("click",".list-item",function(){
		var indexStr=$(this).parent().parent().attr("id").replace("div","");
		$("#item"+indexStr).children("input").val($(this).html());		
		$(this).parent().parent().css("display","none");
	});
	
	//页面单击事件
	$(".wrapper").click(function(e){

		var target  = e.target || e.srcElement;
		var subject_dtl = $('.subject-dtl');
		if(!$("#isItem").is(":hidden")){
			var trIndex = $("#isItem").attr("trIndex");
			var $editTd = $("#voucher tbody tr").eq(trIndex).children(".col_subject");	
			//显示辅助核算行科目信息
			var indexVal = $editTd.children(".subject-dtl").children("input").val();
			if(indexVal == ''){
				$editTd.children(".subject-dtl").children(".cell_val").html('');
			}
			var indexHm = $editTd.children(".subject-dtl").children(".cell_val").html();
			var elem = false;
			if($(target).closest(subject_dtl).length == 0){
				elem = true;
			}else{
				//点击的行科目信息
				var subHm = $(target).closest(subject_dtl).children(".cell_val").html();
				if(indexHm == ''){
					$("#isItem").css("display","none");
				}else{
					var subObj = assistData[indexHm.split("/")[0]];//科目实体
					if(subObj==null){
						$("#isItem").css("display","none");
					}else{
						//如果点击本身 不检查
						if(subHm == indexHm){
							elem = false;
						}else{
							elem = true;
						}
					}
				}
			}
			if(elem){
				var flag = true;
				var subInfo = "";
				var subAll = "";
				$("#isItem>ul>li:visible input").each(function(){
					if($(this).val()==""){
						//layer.alert(top.getMessage("NOT_FORM_EMPTY", "核算项目"));
						zyflag=0;
						$(".ui-summary-wrap").hide();//摘要的框
						//$(".edit_summary").blur();//摘要的框
						$(".ui-subject-wrap").hide();//科目的框
						$(".edit_subject").blur();
						alert(top.getMessage("NOT_FORM_EMPTY", "核算项目"),0);
						flag=false;
						return false;
					}else{
						zyflag=1;
						subInfo += "_"+$(this).val();
						subAll += "|" + $(this).attr('items') + "@" + $(this).attr('itemName') + "@" + $(this).attr('data-value') + "@" + $(this).val();
					}
				});
				
				if(flag){
					var subValue = $("#isItem").attr("subValue");			
					var totalStr = subValue + "<span>"+subInfo+"</span>";
					
					$editTd.children("input").val(subAll.substring(1));
					$editTd.children(".subject-dtl").children(".cell_val").html(totalStr);
					$editTd.children(".subject-dtl").children(".cell_val").css("display","table-cell");
					$editTd.children(".subject-dtl").children("input").css("display","none");
					$("#isItem").css("display","none");
					$("#voucher tbody tr").eq(trIndex).attr("subValue",subValue);
				}
			}
		}
	});

	//摘要字段单击事件
	$("#voucher").on("click", ".col_summary", function() {
		//优先判断辅助核算项
		if(zyflag==0){
			$(".ui-summary-wrap").hide();
			return;
		}
		//补充摘要的内容
		var pretext = $(this).parents("tr").prevAll("tr");//获取所有的tr
		var edittext = '';
		pretext.each(function(){
		  var temp = $(this).find("td").eq(1).find(".edit_summary").val();
		  if(temp){
			  edittext=temp;
			  return false; 
		  }
		});
		
		$("#COMBO_WRAP").children("div").eq(1).hide();
		$("input.edit_summary,input.edit_subject").hide();
		$("div.curdiv").show().removeClass("curdiv");
		$(this).parents("tr").addClass("edit-row");
		$(this).parents("tr").siblings().removeClass("edit-row");
		//获取当前元素的绝对位置
		var inputCount = $(this).children(".edit_summary").length;
		if(inputCount == 0) {
			var inSummary = "<input type='text' class='edit_summary' autocomplete='off'>";
			$(this).append(inSummary);
			$(this).children(".cell_val").css("display", "none");
		}
		
		var editSumVal = $(this).children(".edit_summary").val();
		
		$(this).children(".edit_summary").css("display", "block");
		$(this).children(".edit_summary").val($(this).children(".cell_val").html());
		$(this).children(".cell_val").css("display", "none");
		$(this).children(".cell_val").addClass("curdiv");
		
		var trIndex = $(this).parents("tr").index();
		//获取当前元素的绝对位置
		var tdTop = parseInt($(this).position().top);
		var tdLeft = parseInt($(this).position().left);
		var indexSz = $("#voucher > tbody").children("tr").length;
		var topOrBot = 0;
		if(trIndex == (indexSz -1))
			topOrBot = tdTop - 214;
		else
			topOrBot = 60 + tdTop;
		$("#COMBO_WRAP").children("div").eq(0).css({
			"display": "block",
			"left": (tdLeft+1)+"px",
			"top": "" + topOrBot + "px"
		});
		$("#COMBO_WRAP").children("div").eq(0).attr("name", trIndex);
		$("#selSum div").removeClass("selected");
		$("#selSum div[data-value='"+editSumVal+"']").addClass("selected");
		//$("#selSum div[data-value!='"+editSumVal+"']").siblings().removeClass("selected");
		var search = _search(editSumVal, remarkData, 7, 'voucherRemark');
		if (search.length) {
			$("#selSum").empty();
			//(var item in search) 
			for (var item =0; item <search.length;item++) {
				var itemStr = "<div class='list-item' data-value='" + search[item].voucherRemark + "'>" + search[item].voucherRemark + "</div>";
				$("#selSum").append(itemStr);
			}
		}
		//为摘要赋值的
		var zyval =  $(this).find(".edit_summary").val();
		if(!zyval){
			$(this).find(".edit_summary").val(edittext);
		}
		//$(this).children(".edit_summary").focus();
		$(this).children(".edit_summary").focusEnd();//为了兼容IE9内容，foucusEnd函数是自定义的
	});
	
	
	var  rept = 0;
	//保存、保存并新增按钮单击事件
	$("#renewB,#saveB").click(function() {
		//优先辅助核算项 
		if(zyflag==0){
			alert(top.getMessage("NOT_FORM_EMPTY", "核算项目"),0);
			return false;
		}
		if(rept > 0){
			//layer.alert("正在处理，请勿重复操作！");
			//layer.alert(top.getMessage("WAIT_OPERATION", "处理"));
			alert(top.getMessage("WAIT_OPERATION", "处理"),0);
			return false;
		}else{
			var butAttrId = $(this).attr("id");
			var dataMap = pkgVchSaveData('0');
			
			if(kmflag==0){
				return false;
			}
			rept++;
			if(sflag == 0){//控制
				rept = 0;
				return false;
			}
			
			if(dataMap){
				jQuery.ajax({
					url: webPath+"/cwVoucherMst/addVoucherAjax",
					data:{ajaxData : JSON.stringify(dataMap)},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						if(data.flag == "success"){
							if(butAttrId=="renewB"){
//								window.location.reload();
								window.location.href = webPath+"/cwVoucherMst/toVoucherAdd";
							}else{
								if($('#which').val()!=''){
									myclose_click();
								}else{
									window.location.href = webPath+"/cwVoucherMst/getListPage";
								}
							}
						}else if(data.flag == "error"){
							alert(data.msg,0);
						}
						rept--;
					},error:function(data){
						alert(top.getMessage("FAILED_OPERATION"," "),0);
						rept--;
					}
				});
			}else{
				rept--;
			}
		}
	});
	//修改按钮单击事件
	$(".editB").click(function() {
		if(rept > 0){
			//layer.alert("正在处理，请勿重复操作！");
			//layer.alert(top.getMessage("WAIT_OPERATION", "处理"));
			alert(top.getMessage("WAIT_OPERATION", "处理"),0);
			return false;
		}else{
			rept++;
			var dataMap = pkgVchSaveData('0');
			if(dataMap){
				jQuery.ajax({
					url: webPath+"/cwVoucherMst/updateVoucherAjax",
					data:{ajaxData : JSON.stringify(dataMap)},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						if(data.flag == "success"){
							if($('#which').val()!=''){
								myclose_click();
							}else{
								//layer.alert(top.getMessage("SUCCEED_SAVE"));
								alert(top.getMessage("SUCCEED_SAVE"),1);
							}
						}else if(data.flag == "error"){
							alert(data.msg,0);
						}
						rept--;
					},error:function(data){
						alert(top.getMessage("FAILED_OPERATION"," "),0);
						rept--;
					}
				});
			}else{
				rept--;
			}
		}
	});
	
	//从模版生成凭证按钮单击事件
	$("#createWithTemp").click(function() {
		openVchPlateDialog(function(data){
			var plateNo = data;
			if(plateNo!=''){
				jQuery.ajax({
					url: webPath+"/cwVchPlateMst/getVchPlateByNoAjax",
					data:{"plateNo":plateNo},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						if(data.flag == "success"){
							$('#pzProofNo').val(data.formData.mst.pzProofNo).change();
							dc = data.formData.dcl;
							row_count = data.formData.dclSize;
							var voucherObj = $('#voucher>tbody');
							var count = row_count;
							if(count < def_row){
								count = def_row;
							}
							voucherObj.html('');
							for(var i = 0; i < count; i++){
								var vchTr = addVchTr();
								voucherObj.append(vchTr);
								if(i < row_count){
									setVchTrValue(vchTr, dc[i]);
								}
							}
						}else if(data.flag == "error"){
							alert(data.msg,0);
						}
					},error:function(data){
						alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
				});
			}
		})
	});
}
/**
 * 金额汇总事件
 */
function addAmtEvend(){
	
	$('.ui-btn-menu .menu-btn').bind('mouseenter.menuEvent',function(e){
		if($(this).hasClass("ui-btn-dis")) {
			return false;
	    }
		$(this).parent().toggleClass('ui-btn-menu-cur');
	    $(this).blur();
	    e.preventDefault();
	});

	//点击其他位置关闭更多查询
	$(document).bind('click.menu',function(e){
		var target  = e.target || e.srcElement;
		$('.ui-droplist-wrap').each(function(){
			var menu = $(this);
			var col_summary = $('.col_summary');
			var col_subject = $('.col_subject');
			if($(target).closest(menu).length == 0 
					&& $(target).closest(col_summary).length == 0 
					&& $(target).closest(col_subject).length == 0 
					&& $(menu).is(':visible')){
				menu.css({"display": "none"});
			}
		});
		
		$('.ui-btn-menu').each(function(){
			var menu = $(this);
			if($(target).closest(menu).length == 0 && $('.con',menu).is(':visible')){
				menu.removeClass('ui-btn-menu-cur');
			}
		});
	});
	
	//保存凭证模版按钮单击事件
	$("#saveAsTemp").click(function() {
		var dataMap = pkgVchSaveData('1');
		if(dataMap.detils.length == 0){
			if(typeof(dataMap.firstSumm) != "undefined" && dataMap.firstSumm != ""){
				//layer.alert("请录入至少一条有效分录！");
				//layer.alert(top.getMessage("ONLY_CW_VOUCHER_ONEENTRY", ""));
				alert(top.getMessage("ONLY_CW_VOUCHER_ONEENTRY", ""),0);
			}
		}else{
			var $openDiv = $('<div class="manage-wrap word-mange" id="manage-wrap" style="margin: 0 auto;"></div>');
			var $form = $('<form id="plateSaveForm"></form>');
			var $ul = $('<ul class="mod-form-rows"></ul>');
			
			var $item1 = $('<li class="row-item">'
					+ '<div class="label-wrap"><label for="tempName">模板名称:</label></div>'
					+ '<div class="ctn-wrap"><input type="text" id="plateName" name="plateName" autocomplete="off" class="ui-input"></div>'
					+ '</li>');
			var $item2 = $('<li class="row-item">'
					+ '<div class="label-wrap"><label for="saveMoney">保存金额:</label></div>'
					+ '<div class="ctn-wrap"><input type="checkbox" id="saveMoney" name="saveMoney" value="1" style="margin-top: 9px;"></div>'
					+ '</li>');
			var $item3 = $('<li class="row-item dn" id="showProject" style="display: list-item;">'
					+ '<div class="label-wrap"><label for="saveProject">保存核算项目:</label></div>'
					+ '<div class="ctn-wrap"><input type="checkbox" id="saveProject" name="saveProject" value="1" style="margin-top: 9px;"></div>'
					+ '</li>');
			$ul.append($item1).append($item2);//.append($item3);
			$form.append($ul);
			$openDiv.append($form);
			dialog({
				id:"userDialog",
				title:'新增模版',
//	    		url: webPath+'/mfSysParmCtrl/getSysUserListPage',
				content: $openDiv,
				width:400,
				height:180,
				backdropOpacity:0,
				okValue: '确定',
			    ok: function () {
			    	saveVchPlate('plateSaveForm');
			    },
			    cancelValue: '关闭',
			    cancel: function () {},
				onshow:function(){
					this.returnValue = null;
				},onclose:function(){}
			}).showModal();
		}
	});
	
	//失去焦点事件
	$("#voucher").on("blur", ".col_debite .edit_money,.col_credit .edit_money", function() {
		var editMoney = $(this).val();
		var numflag = isNaN(editMoney);//是数字返回false,不是数字返回true
		if(numflag){
			editMoney = '';
		}
		var $parent = $(this).parent();
		var isNega = false;
		if(editMoney==""){
			$parent.children(".edit_money").val("");
			$parent.children(".cell_val").html("");
		}
		if(typeof(editMoney) != "undefined" && editMoney != "") {
			//控制最大金额
			if(Number(editMoney) > 999999999.99){
				editMoney = "999999999.99";
				$parent.children(".edit_money").val("999999999.99");
			}
			if(editMoney.indexOf("-") ==0) {
				isNega = true;
				editMoney = editMoney.substring(1);
				$parent.children(".cell_val").addClass('red');
			}else{
				$parent.children(".cell_val").removeClass('red');
			}
			if(editMoney.indexOf(".") !=-1) {
                var subMoney;
				if(editMoney.charAt(0)=="."){
					subMoney = editMoney.substring(1,3);
					if(subMoney.length==1) subMoney = subMoney + "0";
					$parent.children(".cell_val").html(subMoney);
					$parent.children(".edit_money").val((isNega ? "-" : "") + "0." + subMoney);
				}else{
					var intMoney = editMoney.split(".")[0];
					subMoney = editMoney.split(".")[1].substring(0, 2);
					if(subMoney.length==1) subMoney = subMoney + "0";
					$parent.children(".cell_val").html((Number(intMoney) > 0 ? intMoney : "") + subMoney);
					editMoney = (Number(intMoney) > 0 ? intMoney : "0") + '.' + subMoney;
					$parent.children(".edit_money").val((isNega ? "-" : "") + editMoney);
				}
			} else {
				$parent.children(".cell_val").html(editMoney + "00");
			}
		}
		$parent.children(".cell_val").css("display", "block");
		$parent.children(".edit_money").css("display", "none");

		//汇总借贷方金额
		var debiteBalSum = 0;
		var creditBalSum = 0;
		var jieBalSum = 0;
		var daiBalSum = 0;
		$("#voucher tbody tr").each(function() {
			//借方金额
			var debiteBal = $(this).children().eq(5).children(".edit_money").val();
			if(typeof(debiteBal) != "undefined" && debiteBal != "") {
				debiteBalSum += Number(debiteBal);
				//jieBalSum = Math.round(debiteBalSum * 100) / 100;//2017年8月30日 lzs 保留两位小数 (修改之前出现的问题500+25.67+14.67)
			}

			//贷方金额
			var creditBal = $(this).children().eq(6).children(".edit_money").val();
			if(typeof(creditBal) != "undefined" && creditBal != "") {
				creditBalSum += Number(creditBal);
				//daiBalSum = Math.round(creditBalSum * 100) / 100;//2017年8月30日 lzs 保留两位小数
			}
		});
		//控制最大金额
		if(parseFloat(debiteBalSum) > 999999999.99){
			debiteBalSum = "999999999.99";
		}
		if(parseFloat(creditBalSum) > 999999999.99){
			creditBalSum = "999999999.99";
		}
        var intStr,subStr;
		if(debiteBalSum == 0) {
			$("#debit_total").html("");
		} else {
//			var debiteSumToStr = debiteBalSum.toString();
			jieBalSum = Math.round(debiteBalSum * 100) / 100;//2017年8月30日 lzs 保留两位小数 (修改之前出现的问题500+25.67+14.67)
			var debiteSumToStr = jieBalSum.toString();//2017年8月31日 修改 lzs
			if(debiteSumToStr.indexOf("-") ==0) {
				debiteSumToStr = debiteSumToStr.substring(1);
				$("#debit_total").addClass('red');
			}else{
				$("#debit_total").removeClass('red');
			}
			if(debiteSumToStr.indexOf(".") != -1) {
				intStr = debiteSumToStr.split(".")[0];
				subStr = debiteSumToStr.split(".")[1];
				if(intStr=="0")
					intStr="";
				if(subStr.length==1)
					subStr = subStr+"0";
				$("#debit_total").html(intStr + subStr.substring(0, 2));
			} else {
				$("#debit_total").html(debiteSumToStr + "00");
			}
		}

		if(creditBalSum == 0) {
			$("#credit_total").html("");
		} else {
			//var creditSumToStr = creditBalSum.toString();
			daiBalSum = Math.round(creditBalSum * 100) / 100;//2017年8月30日 lzs 保留两位小数
			var creditSumToStr = daiBalSum.toString();//2017年8月31日 lzs 修改
			if(creditSumToStr.indexOf("-") ==0) {
				creditSumToStr = creditSumToStr.substring(1);
				$("#credit_total").addClass('red');
			}
			if(creditSumToStr.indexOf(".") != -1) {
				intStr = creditSumToStr.split(".")[0];
				subStr = creditSumToStr.split(".")[1];
				if(intStr=="0")
					intStr="";
				if(subStr.length==1)
					subStr = subStr+"0";
				$("#credit_total").html(intStr + subStr.substring(0, 2));
			} else {
				$("#credit_total").html(creditSumToStr + "00");
			}
		}
		//如果借方金额和贷方金额相同，显示合计大写金额
		if(debiteBalSum==creditBalSum){
			$("#capAmount").html(chineseNumber(debiteBalSum));
		}else{
			$("#capAmount").html("");
		}	
	});
	//跳转到上一张凭证
	$('#prev').on("click", function(a) {
		if(prevNo == '')
			//layer.alert("囧，已经没有上一张了！");
			//layer.alert(top.getMessage("NO_CW_VOUCHER_MOREUP", ""));
			alert(top.getMessage("NO_CW_VOUCHER_MOREUP", ""),0);
		else
			getPrevOrNextVch(prevNo);
	});
	//跳转到下一张凭证
	$("#next").on("click", function(a) {
		if(nextNo == '')
			//layer.alert("囧，已经没有下一张了！");
			//layer.alert(top.getMessage("NO_CW_VOUCHER_MOREDOWN", ""));
			alert(top.getMessage("NO_CW_VOUCHER_MOREDOWN", ""),0);
		else
			getPrevOrNextVch(nextNo);
	});
}



function getPrevOrNextVch(vchNo){
	window.location.href = webPath+"/cwVoucherMst/toVoucherEdit?voucherNo=" + vchNo + "&params="+allVchNo;
}

//显示科目分类信息
function showSubDetailInfo(trIndex,subObj) {
	var $tr = $("#voucher").children("tbody").children("tr").eq(trIndex).children();
//	$tr.children(".subject-dtl").children(".edit_subject").val(subObj.code + " " + subObj.name);
	var isShowFlag = [subObj.employ, subObj.dept, subObj.custom, subObj.item];
	//初始化科目的分类信息
	$("#isItem").children("ul").children("li").css("display","none");
	$("div[id^='div']").children("div").html("");
	$("span[id^='item']").children("input").val("");
	var showCount = 0;
	for(var i=0;i<nameIndex.length;i++){
		if(isShowFlag[i]){
			$("#item"+nameIndex[i]).parent().css("display","block");
			$("#item"+nameIndex[i]).attr("showCount",showCount);
			$("#item"+nameIndex[i]).children("input").attr("data-value", '').val('');
			showCount++;
		}
	}
	
	//是否增加自定义分类
	if(subObj.itemClassName!="" && typeof(subObj.itemClassName)!="undefined"){
		$("#itemZDY").siblings("label").html(subObj.itemClassName+":");
		$("#itemZDY").children("input").attr("items", subObj.itemClassId).attr("itemName", subObj.itemClassName);
	}	
	$("#selSubject").parent().css("display", "none");

	//改变分类信息框位置
	var trTop = $tr.eq(3).offset().top;
	var trLeft = $tr.eq(3).offset().left;
	$("#isItem").css({
		"display": "block",
		"top": (trTop + 60) + "px",
		"left": trLeft + "px"
	});
	var subValue = $tr.children(".subject-dtl").children(".edit_subject").val();
	$("#isItem").attr("subValue", subValue).attr("trIndex", trIndex);
	$tr.parent().attr("trIndex", trIndex);
}

/**匹配
 * query：查询参数
 * data：数据源
 * limit：最多显示行数
 */
function _search(query, data, limit, sch1, sch2) {
	var source = _clone(data)
	var response = [];
//	query = query.toUpperCase();
	if (source.length) {
		for (var i = 0; i < 2; i++) {
			for (var item in source) {
				var lable = '';
				if(typeof(sch1) != "undefined" && sch1 != ""){
					lable += source[item][sch1];
				}
				
				if(typeof(sch2) != "undefined" && sch2 != ""){
					lable += source[item][sch2];
				}
				if (response.length < limit) {
					 switch (i) {
	                     case 0:
	                         if (lable.search(query) === 0) {
	                             response.push(source[item]);
	                             delete source[item];
	                         }
	                         break;
	
	                     case 1:
	                         if (lable.search(query) !== -1) {
	                             response.push(source[item]);
	                             delete source[item];
	                         }
	                         break;
					 }
				}
			}
		}
	}
	return response;
}

/**
 * copy 对象
 * @param obj
 * @returns
 */
function _clone(obj) {
    if (null === obj || "object" !== typeof obj) {
        return obj;
    }
    var copy = obj.constructor();
    for (var attr in obj) {
        if (obj.hasOwnProperty(attr)) {
            copy[attr] = obj[attr];
        }
    }
    return copy;
}
//检测是否满足科目数据
function checkComItem(subCode){
	for(var item in comData){
		if(comData[item].accNo==subCode){
			return comData[item];
		}
	}
	return false;
}

/**
 * 证、凭证模版保存，数据封装
 * @param plate 0:凭证保存；1：模版保存
 */
function pkgVchSaveData(plate){
	//获取凭证字、号、日期
	if(voucherNos){
		$("#voucherNo").val(voucherNos);
	}
	
	var voucherNo = $("#voucherNo").val();
	var pzProofNo = $("#pzProofNo").val();
	var vchNum = $("#vch_num").val();
	var vchDate = $("#vch_date").val();
	var vchAttach = $("#vch_attach").val();//附件数
	var which = $("#which").val();//不同业务类型
	var businessNo = $("#businessNo").val();//不同业务编号
	
	var dataMap = {};
	dataMap.voucherNo=voucherNo||'';
	dataMap.pzProofNo=pzProofNo;
	dataMap.voucherNoteNo=vchNum;
	dataMap.voucherDate=vchDate;
	dataMap.counts=vchAttach;
	dataMap.which=which||'';//不同业务类型
	dataMap.businessNo=businessNo||'';//不同业务编号
	
	//循环每一条会计科目信息
	var isBw = false;
	var detils = [];
	$("#voucher tbody tr").each(function(i) {
		//判断是否输入摘要
		var summStr = $(this).children(".col_summary").children(".edit_summary").val();
		if(i == 0){
			if(summStr == "" || typeof(summStr) == "undefined") {
				//layer.alert("第" + (i + 1) + "条分录摘要不能为空!");
				//layer.alert(top.getMessage("NOT_FORM_EMPTY","第" + (i + 1) + "条分录摘要" ), 1);
				alert(top.getMessage("NOT_FORM_EMPTY","第" + (i + 1) + "条分录摘要" ),0);
				sflag = 0;
				return false;
			}else{
				sflag = 1;
				dataMap.firstSumm=summStr;//第一条摘要
			}
		}
		var summty = typeof(summStr) == "undefined" || summStr == "";
        var vchBody = $(this).children(".col_summary").children(".edit_vchBody").val();
        //判断是否选择科目
		var subStr = $(this).children(".col_subject").children().children(".edit_subject").val();
		var subempty = typeof(subStr) == "undefined" || subStr == "";
		

		//判断是否输入借方金额、贷方金额
		var debiteNum = $(this).children(".col_debite").children(".edit_money").val();
		var creditNum = $(this).children(".col_credit").children(".edit_money").val();
		var dempty = typeof(debiteNum) == "undefined" || debiteNum == "";
		var cempty = typeof(creditNum) == "undefined" || creditNum == "";
		
		if(!subempty) {
			kmflag = 1;
			if(Number(subStr.charAt(0)) > 6){
				isBw = true;
			}
//			if(subempty){
//				layer.alert("第" + (i + 1) + "条分录科目不能为空!");
//				return false;
//			}
			//辅助核算
			var itemStr = $(this).children(".col_subject").children(".edit_items").val();
			
			if(dempty && cempty && plate=='0') {
				//layer.alert("第" + (i + 1) + "条分录借方和贷款金额不能都为空!");
				//layer.alert(top.getMessage("NOT_FORM_EMPTY","第" + (i + 1) + "条分录" ), 1);
				sflag = 0;//条件控制
				alert(top.getMessage("NOT_FORM_EMPTY","第" + (i + 1) + "条分录" ),0);
				return false;
			} else {
				sflag = 1;
				if(dempty) {
					debiteNum = "";
				}
				if(cempty) {
					creditNum = "";
				}
			}
			var detil = {};
			detil.summStr=summStr;
			detil.vchBody=vchBody;
			detil.subStr=subStr;
			detil.itemStr=itemStr;
			detil.debiteNum=debiteNum;
			detil.creditNum=creditNum;
			detils.push(detil);
		}else {
			if(!summty && (!dempty ||!cempty)){
				kmflag = 0;
				alert(top.getMessage("NOT_FORM_EMPTY","会计科目" ),0);
				return false;
				
			}
			/*i++;
			alert(i);*/
		}
	});
	dataMap.detils=detils;
	var creditTotal = $("#credit_total").html();
	var debitTotal = $("#debit_total").html();
	if((creditTotal != debitTotal) && plate=='0' && !isBw) {
		//layer.alert("录入借贷不平衡!");
//		layer.alert(top.getMessage("NO_CW_ACCOUNT_BALANCE","录入" ), 1);
		alert(top.getMessage("NO_CW_ACCOUNT_BALANCE","录入" ), 0);
		
		return false;
	}
	return dataMap;
}
/**
 * 保存凭证模版
 * @param plateName
 */
function saveVchPlate(formId){
	var myform = $('#'+formId);
	var pName = myform.find('#plateName').val();
	if(pName==''){
		alert(top.getMessage("NOT_FORM_EMPTY", "模板名称"), 1);
		return false;
	}else{
		var dataMap = pkgVchSaveData('1');
		if(dataMap){
			dataMap.plateName = pName;
			dataMap.saveMoney = myform.find('#saveMoney')[0].checked ? '1' : '';
			jQuery.ajax({
				url: webPath+"/cwVchPlateMst/addVchPlateAjax",
				data:{ajaxData : JSON.stringify(dataMap)},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
//					parent.dialog.get("userDialog").close();
						alert(top.getMessage("SUCCEED_SAVE_CONTENT","模版"),1);
//						alert('保存模版成功', 1);
					}else if(data.flag == "error"){
//					parent.dialog.get("userDialog").close();
						alert(data.msg,0);
					}
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	}
}

//批量审核，批量记账
function voucherAudit(){
	var url = webPath+'/cwVoucherMst/voucherAuditAjax';
	jQuery.ajax({
		url:url,
		data:{"voucherNo":voucherNo},
		type:"POST",
		dataType:"json",
		beforeSend:function(){  
		},success:function(data){
			if(data.flag == "success"){
				 alert(top.getMessage("SUCCEED_OPERATION"),1);
				 window.location.reload();
			}else if(data.flag == "error"){
				 alert(data.msg,0);
			}
		},error:function(data){
			 alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	});
}

//批量反审核    ，批量反记账
function voucherRevAudit(){
	var url = webPath+'/cwVoucherMst/voucherRevAuditAjax';
	jQuery.ajax({
		url:url,
		data:{"voucherNo":voucherNo},
		type:"POST",
		dataType:"json",
		beforeSend:function(){  
		},success:function(data){
			if(data.flag == "success"){
				 alert(top.getMessage("SUCCEED_OPERATION"),1);
				 window.location.reload();
			}else if(data.flag == "error"){
				 alert(data.msg,0);
			}
		},error:function(data){
			 alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	});
}
/////////////////////////////////以下代码是在IE9上设置光标的问题｜、、////////
$.fn.setCursorPosition = function(position){  
    if(this.lengh == 0) return this;  
    return $(this).setSelection(position, position);  
}  
  
$.fn.setSelection = function(selectionStart, selectionEnd) {  
    if(this.lengh == 0) return this;  
    input = this[0];  
  
    if (input.createTextRange) {  
        var range = input.createTextRange();  
        range.collapse(true);  
        range.moveEnd('character', selectionEnd);  
        range.moveStart('character', selectionStart);  
        range.select();  
    } else if (input.setSelectionRange) {  
        input.focus();  
        input.setSelectionRange(selectionStart, selectionEnd);  
    }  
  
    return this;  
}  

$.fn.focusEnd = function(){  
    this.setCursorPosition(this.val().length);  
}  
/////////////////////////////////以上代码是在IE9上设置光标的问题｜、、////////
