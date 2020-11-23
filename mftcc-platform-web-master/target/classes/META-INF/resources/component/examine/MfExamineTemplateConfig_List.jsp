<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfExamineTemplateConfig/findByPageAjax",//列表数据查询的url
			    	tableId:"tableexatemp0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	callback:function(){
			    		$("table").tableRcswitcher({
			    		name:"useFlag",onText:"启用",offText:"停用"});
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			 });
			//新增检查模板配置
			function addExamineTemplateConfig(){
				top.window.openBigForm(webPath+'/mfExamineTemplateConfig/input','检查模板配置',function(){});
			}
			 //编辑检查模板配置
	 		function getById(obj,url){
				window.parent.openBigForm(url,"检查模板配置",function(){});
			 };
			//模板配置
			function DcouTemplate_set(obj,url){
					//var returnUrl = window.location;
					var saveFileName = url.split("?")[1].split("&")[1].split("=")[1];
					var templateId = url.split("?")[1].split("&")[0].split("=")[1];
					var pathFileName = "/factor/component/model/docmodel/"+saveFileName;
					var savePath = "/factor/component/examine/docuTemplate/";
					var fileName = saveFileName.substr(0,saveFileName.lastIndexOf("."));
					if(fileName=="examDocuTemp"){
						var fileNameEnd = saveFileName.substr(saveFileName.lastIndexOf("."));
						saveFileName = fileName+"_"+templateId+fileNameEnd;
					}else{
						pathFileName = "/factor/component/examine/docuTemplate/"+saveFileName;
					}
					var returnUrl = window.location.href;
					var saveUrl = "${webPath}component/examine/updateDcouTemplate.jsp?templateId="+templateId+"&filename="+saveFileName+"&returnUrl="+returnUrl;
					var poCntJson = {
						pathFileName : "" + pathFileName,
						savePath : "" + savePath,
						saveFileName : saveFileName,
						fileType : 0
					};
					poCntJson.returnUrl = returnUrl;
					poCntJson.saveUrl = saveUrl;
					poCntJson.markUrl="${webPath}MfSysTemplateAction_labelSetBase.action?fileName=examDocuTemp.doc";
					poCntJson.printBtn="0";//取消打印按钮
					var poCnt = JSON.stringify(poCntJson);
					var url="/pageoffice/pageOfficeFactor.do?method=pageOfficeEdit&poCnt="+encodeURIComponent(encodeURIComponent(poCnt));
					window.open(url, '_self', '');
				}
			
			function openFormTemplate(obj,url){
				var formTemplate = url.split("?")[1].split("&")[1].split("=")[1];
				var templateId = url.split("?")[1].split("&")[0].split("=")[1];
				if(formTemplate=="examFormTemp"){
					$.ajax({
						url:webPath+"/mfExamineTemplateConfig/copyFormTemplateAjax",
						type:'post',
		    			data:{templateId:templateId,formTemplate:formTemplate},
		    			async:false,
						success:function(data){
							formTemplate = data.formTemplate;
						},error:function(){
							alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
						}
					});
				}
				var url = '<%=webPath %>'+'/tech/dragDesginer/openForm.action?formId='+formTemplate;
				window.open(url,'width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+ 'top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
			}
		</script>
	</head>
	<body style="overflow-y: hidden;">
		<dhcc:markPoint markPointName="MfExamineTemplateConfig_List"/>
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<div class="col-xs-9 column">
							<button type="button" class="btn btn-primary pull-left" onclick="addExamineTemplateConfig();">新增</button>
							<ol class="breadcrumb pull-left">
								<li><a href="${webPath}/sysDevView/setC?setType=model">贷后设置</a></li>
								<li class="active">检查模板配置</li>
							</ol>
						</div>
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=检查模板名称/产品种类"/>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content"  style="height: auto;">
					</div>
				</div>
			</div>
		</div>
	</body>	
</html>
