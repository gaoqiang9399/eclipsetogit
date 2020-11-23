<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/include/message.jsp" %>
<%@ page import="app.component.common.SysGlobalParams"%>
<%@ page import="app.component.sys.entity.MfSysCompanyMst"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%
String contextPath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+webPath+"/";
MfSysCompanyMst mfSysCompanyMst=( MfSysCompanyMst) SysGlobalParams.get("COMPANY");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		
		<script type="text/javascript">
		var webPath = "${webPath}";
		var loadingGifPath = "<%=mfSysCompanyMst.getLoadAnimationImg()%>";
		</script>
		
		<script type="text/javascript" src="${webPath}/themes/factor/js/loadingAnimate.js?v=${cssJsVersion}"></script>
		<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
		<%--滚动条js 和鼠标滚动事件js--%>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<script type="text/javascript" src="${webPath}/component/risk/js/RiskPreventSceGen.js"></script>
		<script type="text/javascript" src="${webPath}/component/risk/layer/layer.js"></script>
		
		<%--滚动样式--%>
		<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" type="text/css" />
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<link type="text/css" rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
		<link type="text/css" rel="stylesheet" href="${webPath}/component/risk/css/RiskPreventSceGen.css"/>
		
		<%--字体图标--%>
		<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.min.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/iconmoon/style.css" />
		<!--artdialog插件  -->
		<script type="text/javascript" src="${webPath}/UIplug/artDialog/dist/dialog-plus-min.js"></script>
		<link id="ui-dialog" rel="stylesheet" href="${webPath}/UIplug/artDialog/css/ui-dialog${skinSuffix}.css" />
		<style type="text/css">
			#dimetablist .dimeClick {
				background-color:#e0f2f8;
			}
			 label{
				 font-weight: normal;
			}
			
			.table_content #selectedItems a{
				color:#32b5cb;
				text-decoration: none;
			}
			
			.table_content #selectedItems a:hover{
				color:#009db7;
				text-decoration: none;
			}
			
			.risk_class li span {
			   line-height: 0;
			    margin: -4px 0px 0px 0px;
			    padding: 0px;
			    width: 23px;
			    height: 24px;
			    display: inline-block;
			    vertical-align: middle;
			    border: 0 none;
			    cursor: pointer;
			    outline: none;
			    background-color: #fff;
			}
			
		</style>
		<script type="text/javascript">
		    var pageStr = '${pageStr}';
			var genNo='${genNo}';
			var riskPreventClass="";
			var classList = (eval("(" + '${json}' + ")")).classList;
			$(function(){
				dealRiskPreventClass(classList);
	    	}); 
	    	 
    		//处理拦截项类别的选中状态
    		function dealRiskPreventClass(classList){
				//先删除已有的状态，再重新勾选
				$(".risk_class_item").each(function(){
					var $this = $(this);
					var $thisSpan = $this.find("span");
					if($thisSpan.hasClass("checkbox_true")){
						$thisSpan.removeClass("checkbox_true");
						$thisSpan.addClass("checkbox_false");
						$this.removeClass("item_true");
						$this.addClass("item_false");
					}
				});
    			$.each(classList,function(i,obj){
    				$(".risk_class_item").each(function(){
    					var $this = $(this);
						var $thisSpan = $this.find("span");
						var item_key = $(this).find(".item_key").text();
						if(item_key==obj){
							$thisSpan.removeClass("checkbox_false");
							$thisSpan.addClass("checkbox_true");
							$this.removeClass("item_false");
							$this.addClass("item_true");
						}
					});
				});
    		}	
			function getRiskItem(riskPreventClass) {
				$.ajax({
						url:webPath+"/riskPrevent/getItems",
						data:{
							"riskPrevent.genNo":genNo,"riskPrevent.riskPreventClassStr":riskPreventClass,"pageStr":pageStr
						},
						type:"POST",
						dataType:"json",
						async:false,//关键
						success:function(data){
							if(data.flag=="error"){
								alert(data.msg,0);
							}else{
								
								flag = true;//必须写
								$("#notSelectedItems").find("tbody").empty().append(data.notSelectedTrs);
								$("#selectedItems").find("tbody").empty().append(data.selectedTrs);
								$("#saveNotSelectedItem").attr("genNo",genNo);
								$("#saveSelectedItem").attr("genNo",genNo);
								
								
							}
						},error:function(data){
							//myAlert(top.getMessage("ERROR_SELECT"));
							alert(top.getMessage("ERROR_SELECT"),0);
						}
					});
			}
		
		
		
			function openTip(obj,mes) {
				layer.tips(mes, $(obj),{
					tips: [4, '#73C0EC']
				});
			}	
			
			
			function saveNotSelectedItem(obj) {
// 				if(checkIfCanSave()==false) {
// 					return;
// 				}
// 				var genNo = $(obj).attr("genNo");
				var itemNos = "";
// 				if(genNo=="") {
// 					//myAlert("请选择维度组合项");
// 					alert("请选择维度组合项",0);
// 					return;
// 				}
				var checkBox = $("#notSelectedItems" ).find("input[type='checkbox']:checked");
				if(checkBox.length == 0) {
					//myAlert("没有选中的拦截项");
					alert("没有选中的拦截项",0);
					return;
				}else {
				checkBox.each(function() {
					itemNos+= "|"+$(this).val();
				})
				}
				LoadingAnimate.start();
				$.ajax({
						url:webPath+"/riskPrevent/saveNoSelectedItem",
						data:{
							"riskPrevent.genNo":genNo,
							"notSelectedItemNo":itemNos,
							"riskPrevent.riskPreventClassStr":riskPreventClass,
							"pageStr":pageStr
							
						},
						type:"POST",
						dataType:"json",
						async:false,//关键
						success:function(data){
							LoadingAnimate.stop();
							if(data.flag=="error"){
								//myAlert(data.msg);
								alert(data.msg,0);
							}else{
								
								flag = true;//必须写
								$("#notSelectedItems").find("tbody").empty().append(data.notSelectedTrs);
								$("#selectedItems").find("tbody").empty().append(data.selectedTrs);
								$("#saveNotSelectedItem").attr("genNo",genNo);
								alert(top.getMessage("SUCCEED_SAVE"),1);
							}
						},error:function(data){
							LoadingAnimate.stop();
							//myAlert(top.getMessage("ERROR_SELECT"));
							alert(top.getMessage("ERROR_SELECT"),0);
						}
					});
				
				
			}
			
			
		function saveSelectedItem(obj) {
// 			if(checkIfCanSave()==false) {
// 					return;
// 				}
// 			var genNo = $(obj).attr("genNo");
				var itemNos = "";
// 				if(genNo=="") {
// 					//myAlert("请选择维度组合项");
// 					alert("请选择维度组合项",0);
// 					return;
// 				}
// 			var notCheckBox = $("#selectedItems" ).find("input[type='checkbox']").not("input:checked");
			var checkBox = $("#selectedItems" ).find("input[type='checkbox']:checked");
			if(checkBox.length == 0) {
					//myAlert("没有未选中的拦截项");
					alert("没有未选中的拦截项",0);
					return;
				}else {
				checkBox.each(function() {
					itemNos+= "|"+$(this).val();
					})
				}
			LoadingAnimate.start();
				$.ajax({
						url:webPath+"/riskPrevent/saveSelectedItem",
						data:{
							"riskPrevent.genNo":genNo,
							"notSelectedItemNo":itemNos,
							"riskPrevent.riskPreventClassStr":riskPreventClass
						},
						type:"POST",
						dataType:"json",
						async:false,//关键
						success:function(data){
							LoadingAnimate.stop();
							if(data.flag=="error"){
								//myAlert(data.msg);
								alert(data.msg,0);
							}else{
								
								flag = true;//必须写
								$("#notSelectedItems").find("tbody").empty().append(data.notSelectedTrs);
								$("#selectedItems").find("tbody").empty().append(data.selectedTrs);
								$("#saveSelectedItem").attr("genNo",genNo);
								
								dealRiskPreventClass(data.classList);
								
								alert(top.getMessage("SUCCEED_SAVE"),1)
							}
						},error:function(data){
							LoadingAnimate.stop();
							//myAlert(top.getMessage("ERROR_SELECT"));
							alert(top.getMessage("ERROR_SELECT"),0)
						}
					});
				
			}
			
			function editRel(obj) {
				
// 				if(checkIfCanSave()==false) {
// 					return;
// 				}
	
			
				var tr = $(obj).parent().parent();
				var oldItemDescTd = tr.find("td:nth-child(3)");
				var oldItemRiskLevelTd = tr.find("td:nth-child(4)");
				var oldItemDescVal = oldItemDescTd.attr("itemdesc");
				var oldItemRiskLevel = oldItemRiskLevelTd.attr("risklevel");
				var riskLevelSelete = $("<select style=\"border-color: #D9534F\" name='riskLevelSelect'></select>");
				if(oldItemRiskLevel==1) {
				riskLevelSelete.append("<option value=1 selected>低风险</option>");
				riskLevelSelete.append("<option value=2>高风险</option>");
				riskLevelSelete.append("<option value=3>业务拒绝</option>");
				
				}else if(oldItemRiskLevel==2) {
					riskLevelSelete.append("<option value=1 >低风险</option>");
					riskLevelSelete.append("<option value=2 selected>高风险</option>");
					riskLevelSelete.append("<option value=99 >业务拒绝</option>");
				}else if(oldItemRiskLevel==99){
					riskLevelSelete.append("<option value=1 >低风险</option>");
					riskLevelSelete.append("<option value=2 >高风险</option>");
					riskLevelSelete.append("<option value=99 selected>业务拒绝</option>");
				}
				oldItemDescTd.empty();
				oldItemRiskLevelTd.empty();
				var newItemDesc = $("<input type=text style=\"border-color: #D9534F\"  value="+oldItemDescVal+">");
				
				oldItemDescTd.append(newItemDesc);
				oldItemRiskLevelTd.append(riskLevelSelete);
				
				 var relNo = tr.attr("relNo");
				 $(".selectedButtons").css("left","30%");
				 $("#saveItem").show();
				 $("#cancelsaveItem").show();
				 $("#saveItem").attr("relNo",relNo);
				 
				 //改变操作按钮
				 var optBtnHtml = '<a class="abatch" onclick="saveItemVal(this)">保存</a>&nbsp'+
								 '<a class="abatch" onclick="cancelsaveItemVal(this)">取消</a>';
				
				 var thisTd = $(obj).parent();
				 $(thisTd).empty().append(optBtnHtml);
			}
			
			
			function saveItemVal(obj) {
				var editTr =$(obj).parent().parent();
				var relNo = $(editTr).attr("relNo");
				var newItemDescTd = editTr.find("td:nth-child(3)");
				var newItemRiskLevelTd = editTr.find("td:nth-child(4)");
				var newItemDescVal = newItemDescTd.find("input").val();
				var newItemRiskLevelVal = newItemRiskLevelTd.find("select").val();
				$.ajax({
						url:webPath+"/riskPrevent/saveEditedItem",
						data:{
							"riskPrevent.relNo":relNo,
							"riskPrevent.itemDesc":newItemDescVal,
							"riskPrevent.riskLevel":newItemRiskLevelVal
							
						},
						type:"POST",
						dataType:"json",
						async:false,//关键
						success:function(data){
							if(data.flag=="error"){
								//myAlert(data.msg);
								alert(data.msg,0)
							}else{
							
								flag = true;//必须写
								var newItemRiskLevelChnVal = newItemRiskLevelTd.find("select").find("option:selected").text();
								newItemDescTd.attr("itemdesc",newItemDescVal);
								var newItemDescValEasy =  "";
								if(newItemDescVal.length>6) {
								newItemDescValEasy = newItemDescVal.substr(0,6)+"...";
								}
								var itemDescLabel = $("<label style=display:inline-block;width:100px; onclick=openTip(this,'"+newItemDescVal+"')>"+newItemDescValEasy+"</label>");
								newItemDescTd.empty().append(itemDescLabel);
								newItemRiskLevelTd.empty().text(newItemRiskLevelChnVal);
// 								$(obj).attr("relNo","");
								$(".selectedButtons").css("left","80%");
// 				        		$(obj).hide();
// 				        		var obj1 = $("#cancelsaveItem");
// 				        		obj1.hide();
								//myAlert(top.getMessage("SUCCEED_SAVE"));
								alert(top.getMessage("SUCCEED_SAVE"),1)
							}
						},error:function(data){
							//myAlert(  top.getMessage("FAILED_SAVE"));
							alert(  top.getMessage("FAILED_SAVE"),0)
						}
					});
					
// 					 var optBtnHtml = '<a class="abatch" onclick="editRel(this)">编辑</a>&nbsp'+
// 								 '<a class="abatch" onclick="fzpz(this)">修改阀值</a>';
// 					 var thisTd = $(obj).parent();
// 					 $(thisTd).empty().append(optBtnHtml);
				}
				
				
			function cancelsaveItemVal(obj) {
				var editTr =$(obj).parent().parent();
				var newItemDescTd = editTr.find("td:nth-child(3)");
				var newItemRiskLevelTd = editTr.find("td:nth-child(4)");
				var newItemDescVal = newItemDescTd.find("input").val();
				var newItemRiskLevelVal = newItemRiskLevelTd.find("select").val();
				var newItemRiskLevelChnVal = newItemRiskLevelTd.find("select").find("option:selected").text();
				newItemDescTd.attr("itemdesc",newItemDescVal);
				var newItemDescValEasy =  "";
				if(newItemDescVal.length>6) {
					newItemDescValEasy = newItemDescVal.substr(0,6)+"...";
				}
				var itemDescLabel = $("<label style=display:inline-block;width:100px; onclick=openTip(this,'"+newItemDescVal+"')>"+newItemDescValEasy+"</label>");
				newItemDescTd.empty().append(itemDescLabel);
				newItemRiskLevelTd.empty().text(newItemRiskLevelChnVal);
// 				obj.attr("relNo","");
				$(".selectedButtons").css("left","80%");
// 				obj.hide();
// 				$(obj1).hide();
				
// 				 var optBtnHtml = '<a class="abatch" onclick="editRel(this)">编辑</a>&nbsp'+
// 								 '<a class="abatch" onclick="fzpz(this)">修改阀值</a>';
// 					 var thisTd = $(obj).parent();
// 					 $(thisTd).empty().append(optBtnHtml);
				
				}
			
			
			function checkIfCanSave() {
				
				var saveButtonRelNo = $("#saveItem").attr("relNo");
				if(saveButtonRelNo != "") {
					//myAlert("存在未保存的拦截项");
					alert("存在未保存的拦截项",0)
					return false;
				}else {
					return true;
				}
			}
			
		function editThreadHold(obj) {
// 			if(checkIfCanSave()==false) {
// 					return;
// 				}
			var offset = $(obj).offset();
			var tr = $(obj).parent().parent();
			var itemName = tr.find("td:nth-child(2)").text();
			var relNo = tr.attr("relNo");
			var itemNo = tr.attr("itemNo");
			 layer.open({
    			type: 2,
   				title: itemName+'--阀值配置',
    			shadeClose: true,
    			shade:[0.5 , '#000' , true],
    			offset : ['100px' , offset.left-505],
    			area : ['500px' , '370'],
    			content: webPath+'/riskPrevent/getThreadHoldForItem?riskPrevent.relNo='+relNo+'&riskPrevent.itemNo='+itemNo,
    			loading : {type : 1}//iframe的url
				}); 
			}
			
		function fzpz(obj){
			var tr = $(obj).parent().parent();
			var itemNo = tr.attr("itemNo");
			dialog({
				id:'fzpz-dialog',
				url:webPath+"/riskItemThreashode/getListPageByItemNo?itemNo="+itemNo+"&query=${query}/>",
				backdropOpacity:0,
				width:800,
				height:400,
				title:"阀值配置"
			}).showModal();
			
		};
			
			//获取可选的拦截项
			function getNotSelectedItem(obj){
					var spanCheckBox = $(obj).find("span");
					var checkbox_false = spanCheckBox.hasClass("checkbox_false");
					if(checkbox_false){
						spanCheckBox.removeClass("checkbox_false");
						spanCheckBox.addClass("checkbox_true");
						$(obj).removeClass("item_false");
						$(obj).addClass("item_true");
					}else{
						spanCheckBox.removeClass("checkbox_true");
						spanCheckBox.addClass("checkbox_false");
						$(obj).removeClass("item_true");
						$(obj).addClass("item_false");
					}
					riskPreventClass="";
					$(".risk_class_item").each(function(){
							var $this = $(this);
							var $thisSpan = $this.find("span");
							if($thisSpan.hasClass("checkbox_true")){	
								var item_key = $(this).find(".item_key").text();
								riskPreventClass+=item_key+"|";
							}
					});
					riskPreventClass = riskPreventClass.substring(0,riskPreventClass.length-1);
					getRiskItem(riskPreventClass);
			}
			
			
			
			
		</script>
    </head>
    <body class="overflow">
    	<div class="content">
    		<div class="row">
    			<div class="col-md-12">
    				<div class="select-border">
    					
	    					<div class="colmon">
	    						<label>拦截模型：</label><label style="color: #32b5cb">${riskPreventSceGen.genName}</label>
	    					</div>
	    					<div class="colmon">
	    						<label>维度组合：</label ><label style="color: #32b5cb">${dimeChnName}</label>
	    					</div>
	    					<div style="clear:both;"></div>
	    					<div class="colmon" style="line-height: 26px;height: 26px;">
	    						<label>拦截项类别：</label >
	    						<ul class="risk_class">
    								<%-- <s:iterator value="riskClassparmDicList" id="riskClass"  status="test">
	    								<li class="risk_class_item item_false" onclick="getNotSelectedItem(this);">
		    								<span class="checkbox_false"><i class="i i-gouxuan"></i></span>
											<label class="item_key"><s:property value="#riskClass.optCode"/></label>
											<lable style="padding-left: 5px;"><s:property value="#riskClass.optName"/></lable>
											<s:property value="test.length"/>
		    							</li>
									</s:iterator> --%>
									<c:forEach var="riskClass" items="${riskClassparmDicList}" varStatus="test">
	    								<li class="risk_class_item item_false" onclick="getNotSelectedItem(this);">
		    								<span class="checkbox_false"><i class="i i-gouxuan"></i></span>
											<label class="item_key">${riskClass.optCode}</label>
											<lable style="padding-left: 5px;">${riskClass.optName}</lable>
											${fn:length(test) }
		    							</li>
									</c:forEach>
	    						</ul>
	    					</div>
	    					
	    					
    					
    				</div>
    			</div>
    		</div>
    		<div class="row row_content">
    			<div class="col-md-5 col_content" style="width:40%;">
    				<div class="bgColor"> 
    					<div class="title">
    						<h4>可选拦截项</h4>
    					</div>
    					<div class="table_content child">
    					    <table cellspacing="1" border="0" width="100%" align="center"  class="ls_list" id="notSelectedItems">
    					    <thead> 
								<tr> 
								 <th align="left" name="dimesName" scope="col" >选择</th>
								 <th align="left" name="dimesName" scope="col">拦截项名称</th>
								</tr>
							</thead>
							 	<tbody >
							 		<%-- <s:iterator value="notSelecteedRiskItem" id="notSe">
								 		<tr>
										  <td align="left">
											 <input type="checkbox" value=<s:property value="#notSe.itemNo"/>>
										  </td>
								 		  <td align="left">
											 <s:property value="#notSe.itemName"/>
								  		 </td>
					                    </tr>
							 		</s:iterator> --%>
							 		<c:forEach var="notSe" items="${notSelecteedRiskItem}">
								 		<tr>
										  <td align="left">
											 <input type="checkbox" value=${notSe.itemNo}>
										  </td>
								 		  <td align="left">
											 ${notSe.itemName}
								  		 </td>
					                    </tr>
							 		</c:forEach>
							 	</tbody>
    					    </table>
    					</div>
    				</div>
    			</div>
    			<div class="col-md-1 containerArrow" style="width:5%;">
					<i class="i i-jiantou2" onclick="saveNotSelectedItem(this);"></i>
					<i class="i i-jiantou3" onclick="saveSelectedItem(this);"></i>
				</div>
    			
    			<div class="col-md-6 col_content" style="width:55%;">
    				<div class="bgColor"> 
    					<div class="title">
    						<h4>已选拦截项</h4>
    					</div>
    					<div class="table_content child">
    					<table cellspacing="1" border="0" width="100%" align="center"  class="ls_list" id="selectedItems">
    					    <thead> 
								<tr> 
								 <th align="center" name="dimesName" scope="col" >选择</th>
								 <th align="center" name="dimesName" scope="col">拦截项名称</th>
								 <th align="center" name="dimesName" scope="col">拦截提示</th>
<!-- 								 <th align="center" name="dimesName" scope="col">风险级别</th> -->
<!-- 								 <th align="center" name="dimesName" scope="col">操作</th> -->
								</tr>
							</thead>
							 	<tbody >
									<c:forEach var="sel" items="${selectedRiskPrevent}">
									<tr relNo=${sel.relNo} id=${sel.relNo} itemNo=${sel.itemNo}>
										<td align="center">
											<input type="checkbox" value="${sel.itemNo}" checked=checked>
										</td>
										<td align="center" onclick="openTip(this,'${sel.itemName}');">
											${sel.itemNameSub}
										</td>
										<td align="center" style="width: 30%" itemDesc=${sel.itemDesc}>
											<label onclick="openTip(this,'${sel.itemDesc}');" style="display: inline-block; width: 100px;">
												${sel.itemDescSub}
											</label>
										</td>
<!-- 										<td align="center" riskLevel=1> -->
<%-- 											<s:property value="#sel.riskLevelName" /> --%>
<!-- 										</td> -->
<!-- 										<td align="center"> -->
<!-- 											<a class="abatch" onclick="editRel(this)">编辑</a> -->
<!-- 											&nbsp
<!-- 											<a class="abatch" onclick="fzpz(this)">修改阀值</a> --> 
<!-- 										</td> -->
									</tr>
								</c:forEach>
							 	</tbody>
							 </table>	
    					</div>
    				</div>
    			</div>
    		</div>
    	</div>
 	</body>
</html>