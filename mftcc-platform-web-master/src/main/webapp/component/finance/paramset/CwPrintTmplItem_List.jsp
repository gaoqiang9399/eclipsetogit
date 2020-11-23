<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>

<html>
	<head>
		
		<script type="text/javascript" src="${webPath}/component/finance/paramset/js/CwLodopFuncs.js"></script>
		
			
<style>
.inline_block {
	display: inline-block;
}

.swfupload_x {
	display: inline-block;
	position: relative;
	top: 10px;
	cursor: pointer;
}
.inputSty1{
	border-radius: 4px !important;
	border: 1px solid #ccc;
    line-height: inherit;
    width:80px;
    height: 34px;
    padding-left: 10px;
}
.spansty1{
	padding-right: 30px;
	font-size: 14px;
	
}

</style>
		<script type="text/javascript">
//var LODOP; // 插件声明为全局变量
var is360 = _mime("type", "application/vnd.chromium.remoting-viewer");//查看浏览器是否是360
//var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
var LODOP;

var t_height = 0, t_width = 0;// 模板高、宽。单位mm
var eval_code;// 模板代码
var r_replace_tmpl_5 = /"([^"]*)"/
var r_replace_tmpl_1 = /A\("([^"]*)",/
var r_replace_tmpl_delete_end = /,"[^"]*"\)/


var tmpl_objs_test = [ {//测试数据
	id : 'accno1',
	name : '科目1',
	tmpl_value : '[100101]现金_库存现金',
	is_money_style : 'no'
}, {
	id : 'accno2',
	name : '科目2',
	tmpl_value : '[100205]银行存款_中国建设银行',
	is_money_style : 'no'
}];

	/**
	360浏览器
	*/
 	function isChrome(){
             var ua = navigator.userAgent.toLowerCase();
             return ua.indexOf("chrome") > 1;
         }
         //测试mime
         function _mime(option, value) {
             var mimeTypes = navigator.mimeTypes;
             for (var mt in mimeTypes) {
                 if (mimeTypes[mt][option] == value) {
                     return true;
                 }
             }
             return false;
         }

	/*上传 插件初始化*/
	var swfu;
	
	function uploadComplete(file) {
		LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW",1);
		//LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='"+"http://"+document.location.host+"/ajaxupload/upload/"+file.name+"'>");
		LODOP.ADD_PRINT_SETUP_BKIMG(document.getElementById('file1').value);
		return;
	}
	 function getImageFileName() {
		//LODOP=getLodop(); 
		if (LODOP.CVERSION) CLODOP.On_Return=function(TaskID,Value){document.getElementById('file1').value=Value;};
		
		//LODOP.ADD_PRINT_SETUP_BKIMG(document.getElementById('file1').value);
		timesInt();
		return  LODOP.GET_DIALOG_VALUE("LocalFileFullName","*.jpg;*.bmp;.jpeg");
		
      } 
 	function myPrintSetup3() {		
		//document.write(document.getElementById('file1').value);
		//LODOP.ADD_PRINT_SETUP_BKIMG(document.getElementById('file1').value);
		LODOP.ADD_PRINT_SETUP_BKIMG($("#file1").val());//添加背景图
		//LODOP.PRINT_SETUP();		
	}; 
	


	/*上传 开始*/
	$(function() {
		init_tmpl_btn();
	});

	function init_tmpl_btn() {// 初始化模板字段
		
		 if (!LODOP) {
			LODOP = getLodop(document.getElementById('LODOP_OB'), document
					.getElementById('LODOP_EM'));
		} 
		if(!LODOP.VERSION){
			return;
		}
		try{
			//LODOP.SET_SHOW_MODE("DESIGN_IN_BROWSE", 1);;
			//alert("这kale");
		}catch(ex){
			return;
		}
		
		$('#tmpl_p').html('');
		
		get_tmpl(function(tmpl_objs) {
			$('#tmpl_control').text('');
			var r_num=/^[0-9]+$/;
			
			for ( var i = 0; i < tmpl_objs.length; i++) {
				var objsid =  tmpl_objs[i].id;
				var _num = parseInt($('#trace_num').val());
				if(objsid.indexOf('remark') > -1 && parseInt(objsid.substring(6)) > _num){
					continue;
				}
				if(objsid.indexOf('accno') > -1 && parseInt(objsid.substring(5)) > _num){
					continue;
				}
				if(objsid.indexOf('pactno') > -1 && parseInt(objsid.substring(6)) > _num){
					continue;
				}
				if(objsid.indexOf('jamt') > -1 && (parseInt(objsid.substring(4)) > _num || parseInt(objsid.substring(6)) > _num)){
					continue;
				}
				if(objsid.indexOf('damt') > -1 && (parseInt(objsid.substring(4)) > _num || parseInt(objsid.substring(6)) > _num)){
					continue;
				}
				$('#tmpl_control').append('<div class="tmpl_div"><input type="checkbox" id="'
						+ tmpl_objs[i].id +'" value="'
						+ tmpl_objs[i].id + '" is_money_style="'
						+ tmpl_objs[i].isMoneyStyle + '" tmpl_value="'
						+ tmpl_objs[i].tmplValue + '" title="'
						+ tmpl_objs[i].remark + '" show_style="'+
						+ tmpl_objs[i].showStyle+'" /><span>' + tmpl_objs[i].name
						+ '</span></div>');
						
				$('#' + tmpl_objs[i].id).click(
						function() {
						//alert(1);
						//LODOP.ADD_PRINT_TEXT(134,90,238,35,"寄件人的详细地址");
							if(!$(this).is(":checked")){
								LODOP.SET_PRINT_STYLEA($(this).attr('id'), 'Deleted', true);
							}else{
								/* alert($(this).attr('id'), top_padding, left_padding, width-0,
											30, $(this).attr('tmpl_value')); */
								var left_padding=random_int()-0+30;
								var top_padding=random_int()-0+56;
								var width=$(this).attr('show_style');
								if(width && r_num.test(width) && width-0 < 300 &&width-0 > 10){//最长不过三百
									LODOP.ADD_PRINT_TEXTA($(this).attr('id'), top_padding, left_padding, width-0,
											30, $(this).attr('tmpl_value'));
								}else{
									LODOP.ADD_PRINT_TEXTA($(this).attr('id'), top_padding, left_padding, 150,
											30, $(this).attr('tmpl_value'));
								}
								if ($(this).attr('is_money_style') == 'yes') {
									LODOP.SET_PRINT_STYLEA($(this).attr('id'),
											"Alignment", 3);
									if($(this).attr('id').indexOf('amt') > -1){
										var letsp = $('#LetSp').val();
										LODOP.SET_PRINT_STYLEA($(this).attr('id'),
												"LetterSpacing", letsp);
									}		
								}
							}
						});
			}
			show_modle();
			/* 
              if (isChrome()) { 
                 //alert("检测到是360浏览器");
                 if(is360){
                 	show_modle();
                 }else{
                // alert("saves");
               // var url = "/factor/cwPrintTmplItem/getListPage";
               // tadaLink(url); 
                 }
             }else{
             	show_modle();
             }   */ 
		});
	}
	/* var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： factor/component/include/mfPageOffice.js
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8080
    var localhostPath=curWwwPath.substring(0,pos);
	
	var tadaLink = function(url){
		$.ajax({
			url : localhostPath+"/pageoffice/pageOfficeFactor.do?method=getPoLink",
			type : 'post',
			async : false,
			dataType : 'json',
			error : function() {
			}, 
			success : function(data) {
				var poLinkUrl="PageOffice://|"+data[0]+url+data[1];
				window.open(poLinkUrl,'_self','');
			}
		});
	} */
	
	// 显示已新增好的模板
	function show_modle() {
		get_code(function(code) {
			/* LODOP = getLodop(document.getElementById('LODOP_OB'), document
						.getElementById('LODOP_EM')); */
						
			if(!LODOP){
				LODOP = getLodop(document.getElementById('LODOP_OB'), document
						.getElementById('LODOP_EM'));
			}
			/* if (isChrome()) { 
                 if(is360){
                 	LODOP = getLodop(document.getElementById('LODOP_OB'), document
						.getElementById('LODOP_EM'));
                 }else{
                 //alert(2);
                 	LODOP=getLodop();  
                 }
             }  */
			//alert(LODOP);
			init_w_h();
			
		//var isChrome = navigator.userAgent.indexOf("Chrome") !== -1;
		/* if(isChrome){
			//alert("显示浏览器");
			//LODOP = getLodop();
			$("#LODOP_OB").remove();
			return;
		} */
			if (code) {
				// alert(code);
				if (code.indexOf('ADD_PRINT_SETUP_BKIMG') > -1) {
					if(/src='([^']*)'/.test(code)){
						code=code.replace(/(.*)ajax/.exec(/src='([^']*)'/.exec(code)[1])[1],"/");
					}
				}
				eval(code);
				var lines = code.split(';');
				for ( var i = 0; i < lines.length; i++) {					
					var line = lines[i];
					if (line.indexOf('ADD_PRINT_SETUP_BKIMG') > -1) {
						if(/src='([^']*)'/.test(line)){
							line=line.replace(/(.*)ajax/.exec(/src='([^']*)'/.exec(line)[1])[1],"/");
						}
					}
					if (line.indexOf('ADD_PRINT_TEXTA') > -1) {
						// 是新增字段的语句。例：LODOP.ADD_PRINT_TEXTA("hkAmt",56,32,175,30,"20000");
						var tmp = line.split(',');
						var re_tmp = r_replace_tmpl_5.exec(tmp[0]);
						$("#"+re_tmp[1]).attr("checked",true);
					}
				}
				
			} else {
				LODOP.PRINT_INITA(0, 0, t_width + 'mm', t_height + 'mm', "凭证套打");
			}
		//	LODOP.SET_SHOW_MODE("DESIGN_IN_BROWSE", 1);//是否内嵌到浏览器中
			LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW", 1);//是否打印背景
			/*
				1---纵向打印，固定纸张； 
				2---横向打印，固定纸张；  
				3---纵向打印，宽度固定，高度按打印内容的高度自适应(见样例18)；
				0(或其它)----打印方向由操作者自行选择或按打印机缺省设置
			*/
			LODOP.SET_PRINT_PAGESIZE(0, t_width + 'mm', t_height + 'mm', '');
			if(isChrome()){
				if(is360){
					LODOP.SET_SHOW_MODE("DESIGN_IN_BROWSE", 1);//是否内嵌到浏览器中
					LODOP.PRINT_DESIGN();
				}else{
					LODOP.PRINT_SETUP();
				}
			}else{
				 LODOP.SET_SHOW_MODE("DESIGN_IN_BROWSE", 1);//是否内嵌到浏览器中
				 LODOP.PRINT_DESIGN();
			}
			
			//LODOP.PREVIEW();	
			//LODOP.PRINT_SETUP();	
		});
	}
	// 重新新增模板s
	function add_new_modle() {
		$(":checkbox").attr("checked",false);
		//alert(1);
		LODOP = getLodop(document.getElementById('LODOP_OB'), document
				.getElementById('LODOP_EM'));
		init_w_h();
		LODOP.PRINT_INITA(0, 0, t_width + 'mm', t_height + 'mm', "凭证套打");
		LODOP.SET_PRINT_PAGESIZE(0, t_width + 'mm', t_height + 'mm', '');
		LODOP.SET_SHOW_MODE("DESIGN_IN_BROWSE", 1);
		LODOP.PRINT_DESIGN();
	}
	
	function init_w_h() {// 初始化高宽
		t_height = document.getElementById('t_height').value
		t_width = document.getElementById('t_width').value
	}
	//产生两位的在0-60之间的整数
	function random_int(){
		var t=new Date().getTime()+"";
		return (t.substr(t.length-2,2)*7/5).toFixed(0);
	}
	/***
	保存模板
	**/
	function save_modle() {

		var map = {};
		map.busType= $("#busType").val();
		map.code=get_cur_code();
	/* alert(get_cur_code()); */
		map.height = $('#t_height').val();
		map.width = $('#t_width').val();
		//data:{'ajaxData': JSON.stringify(map)},
		$.ajax({
			url:webPath+"/cwPrintTmplItem/saveTmplCodeAjax",
			data:{'ajaxData': JSON.stringify(map)},
			type:"post",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if (data.flag=='success') {
					window.location.reload();
				}else{
					alert("保存失败",0);
				}		
			},error:function(data){
				alert("保存失败",0);
				//$('#personManage').remove();
			}
		});
		
		
		/* js_print.saveTmplCode(map, function(data) {
			if (data=='0000') {
				window.location.reload();
			}else{
				alert("保存失败");
			}
		}); */
	}
	
	// 获取设计产生的模板渲染代码
	function get_cur_code() {
		return LODOP.GET_VALUE("ProgramCodes", 0)
	}

	// 覆盖pubprint.js中的方法，参数改为业务类型，不再是模板id
	// 根据模板ID获取模板字段
	function get_tmpl(fn) {
		var busType = $("#busType").val();
		jQuery.ajax({
				url:webPath+"/cwPrintTmplItem/getTmplObjByTypeAjax",
				data:{"busType":busType},
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
	/* 	js_print.getTmplObjByType('pz', function(data) {
			if (!data) {
				data = [];
			}
			data = $.parseJSON(data);
			fn(data);
		}); */
	}
	

	// 覆盖pubprint.js中的方法，参数改为业务类型，不再是模板id
	// 根据模板ID获取模板渲染代码
 	function get_code(fn) {
	
		$.ajax({
			url:webPath+"/cwPrintTmplItem/getTmplCodeByTypeAjax",
			data:{'busType':$("#busType").val()},
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
	
		/* js_print.getTmplCodeByType('pz', function(data) {
			data = $.parseJSON(data);
			$('#t_height').val(data.height||0);
			$('#t_width').val(data.width||0);
			fn(data.code);
		}); */
	}
	//修改凭证分录数
	function upStting(val){
		for(var i = 1; i < 11; i++){
			if(i > parseInt(val)){
				LODOP.SET_PRINT_STYLEA('remark' + i, 'Deleted', true);
				LODOP.SET_PRINT_STYLEA('accno' + i, 'Deleted', true);
				LODOP.SET_PRINT_STYLEA('pactno' + i, 'Deleted', true);
				LODOP.SET_PRINT_STYLEA('jamt' + i, 'Deleted', true);
				LODOP.SET_PRINT_STYLEA('damt' + i, 'Deleted', true);
				LODOP.SET_PRINT_STYLEA('unjamt' + i, 'Deleted', true);
				LODOP.SET_PRINT_STYLEA('undamt' + i, 'Deleted', true);
			}
		}
		var stt = {};
		stt.name1='10041';
		stt.name3=val;
		stt.name4='参数范围1~10';
		js_cwstting.stting_update(stt, function(data){
			if('0000'==data){
				save_modle();
			}else{
				alert("修改凭证分录数失败",0);
			}
		})
	}
	//点击放款
	function fangkuan(){
		$("#busType").val("fk");
		$("#hkmain").addClass('hidden');
		$("#fkmain").removeClass('hidden');
		init_tmpl_btn();
	}
	//点击还款
	function huankuan(){
		$("#busType").val("hk");
		$("#fkmain").addClass('hidden');
		$("#hkmain").removeClass('hidden');
		init_tmpl_btn();
	}
	//设置谷歌
	function guge_modle(){
		LODOP=getLodop(); 
		/* alert(LODOP);  */
		LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_装载背景图");
		for(var i=0;i<5;i++){
			LODOP.ADD_PRINT_TEXT(83,78,75,20,i);
		}
		/* LODOP.ADD_PRINT_TEXT(83,78,75,20,"寄件人姓名");
		LODOP.ADD_PRINT_TEXT(109,137,194,20,"寄件人单位名称");
		LODOP.ADD_PRINT_TEXT(134,90,238,35,"寄件人的详细地址");
		LODOP.ADD_PRINT_TEXT(85,391,75,20,"收件人姓名");
		LODOP.ADD_PRINT_TEXT(108,440,208,20,"收件人单位名称");
		LODOP.ADD_PRINT_TEXT(137,403,244,35,"收件人详细地址");
		LODOP.ADD_PRINT_TEXT(252,33,164,40,"内件品名");
		LODOP.ADD_PRINT_TEXT(261,221,100,20,"内件数量");
		LODOP.ADD_PRINT_TEXT(83,212,100,20,"寄件人电话");
		LODOP.ADD_PRINT_TEXT(80,554,75,20,"收件人电话"); */
		//LODOP.ADD_PRINT_SETUP_BKIMG(document.getElementById('file1').value);
		LODOP.PRINT_SETUP();	
	}
	
</script>
	</head>
	<body style="margin: 0px;">
	
	<div>
		<!-- <span id="fk">放款<button>eee</button></span>
		<span id="hk">还款</span> -->
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12">
				<!-- 列表上部的按钮区域（单独一行的形式），如有需要去掉注释，改为实际需要即可。-->
				<div class="btn-div" id="fkmain">
					<button type="button" class="btn btn-primary" onclick="fangkuan()">放款凭证模版</button>
					<button type="button" class="btn btn-default" onclick="huankuan()">还款凭证模版</button>
				</div>
				<div class="btn-div hidden" id="hkmain">
					<button type="button" class="btn btn-default" onclick="fangkuan()" >放款凭证模版</button>
					<button type="button" class="btn btn-primary" onclick="huankuan()" >还款凭证模版</button>
				</div>
				<input type="text" hidden="hidden" value="fk" id="busType">
			</div>
		</div>
		<!-- <button id="clicksrc" onclick="myPrintSetup3()" hidden="hidden">图片放的位置</button> -->
	<hr>
	<div>
	<div class="tab_Content_new" style="height: auto;margin-top: 10px;">
		<div class="tablecard" id="table" style="height: auto;width: auto;border: 0px;">
			<table style="margin-left:40px;">
				<tr>
					<td>
						<p>
							<span class="txt spansty1">高度：<input type='text' id='t_height' class='input_text inputSty1' value='96' />毫米</span>
							<span class="txt spansty1">宽度：<input type='text' id='t_width' class='input_text inputSty1' value='180' />毫米</span>
							<span class="inline_block" id="span_out_upload" style="display:none;">
								<span id="spanButtonPlaceHolder"></span>
							</span>
							<button type="button" class="btn btn-primary" onclick="add_new_modle()">生成模板</button>
							<button type="button" class="btn btn-primary" onclick="save_modle()">保存模板</button>
					
						</p>
					</td>
				</tr>
				<tr>
					<td>
						<object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=820 height=450>
							<embed id="LODOP_EM" type="application/x-print-lodop" width=820 height=450 pluginspage="install_lodop32.exe"></embed>
						</object>
					</td>
					<td><span class="spansty1"></span></td>
					<td>
						<fieldset style="position: relative; height: 445px; width: 200px; overflow: hidden;">
							<legend>可选项 </legend>
							<div id="tmpl_control" class="scroll-content" style="text-align: left; overflow-y: scroll; width: 195px; height: 380px; background-color: #f0f0f0;">
							</div>
						</fieldset>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript">
	/* $(function(){
		$("#clicksrc").click();
	}) */
		$(document).ready(function(){
			timesInt();
	});
	function timesInt(){
		
			timer = window.setTimeout(function selectedFilter(changeFlag){
		          		if(changeFlag){
							window.clearTimeout(timer);//清除时间间隔的设置
						}
						$("#clicksrc").click();
						
		},1000);
 }
	  /* 	myCustomScrollbarForForm({
						obj:"#tmpl_control",
						advanced : {
							theme : "minimal-dark",
							updateOnContentResize : true
						}
				});	 */  
</script>
</html>