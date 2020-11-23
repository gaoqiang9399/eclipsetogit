<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath + "/mfSysTemplate/findByPageAjax",//列表数据查询的url
			    	tableId:"tablesystemplate0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	callback:function(){
			    		$("table").tableRcswitcher({
			    		name:"useFlag",onText:"启用",offText:"禁用"});
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			 });
			//模板配置
			function template_set(obj,url){
				var saveFileName = url.split("?")[1].split("&")[1].split("=")[1];
				var fileNameTemp = saveFileName;
				var templateNo = url.split("?")[1].split("&")[0].split("=")[1];
				var fileName = saveFileName.substr(0,saveFileName.lastIndexOf("."));
				var filePath = "";
				var saveUrl = "";
				if(fileName=="templateFile"){
					var fileNameEnd = saveFileName.substr(saveFileName.lastIndexOf("."));
					saveFileName = fileName+"_"+templateNo+fileNameEnd;
					saveUrl = webPath + "/component/model/updateTemplateName.jsp?templateNo="+templateNo+"&filename="+saveFileName;
				}else{
					saveFileName = fileNameTemp;
				}
				$.ajax({
					url: webPath + "/mfTemplateModelRel/getIfSaveTemplateInfo",
					type:"post",
					data:{templateNo:templateNo},
					dataType:"json",
					success:function(data){
						fileNameTemp = data.fileName;
						filePath = data.filePath;
						var poCntJson = {
							filePath : filePath,
							fileName : fileNameTemp,
							saveFileName : saveFileName,
							printBtn : "0",//取消打印按钮
							saveUrl : saveUrl,
							fileType : 0
						};
						poCntJson.markUrl=webPath+"/mfSysTemplate/labelSetBase?fileName="+fileNameTemp;
						mfPageOffice.editOpen(poCntJson);
					},error:function(){alert('error');},
				});
			}

			 function addModel(){
				 var url = webPath + "/mfSysTemplate/addTemplate";
				 top.openBigForm(url,'新增模板',myclose);
			} 
			 function editModelInfo(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				top.openBigForm(url,'修改模板',myclose);
			} 
			//模板标签配置
			function baseTagSet(obj, url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				top.openBigForm(url,'标签配置',null);
			}
			//删除模板信息,删除前判断是否在模型中配置，如果配置不允许删除
			function deleteTemplate(obj, url){
				var templateNo = url.split("?")[1].split("=")[1];
				$.ajax({
					//url:webPath + "/mfTemplateModelConfig/getTemplateIsSetedAjax?templateNo="+templateNo,
                    url:webPath + url,
                    type:'post',
	    			dataType:'json',
					success:function(data){
						if(data.flag=="success"){
							alert(data.msg,3,function () {
                                updateTableData();
                            });
						}else{
							ajaxTrDelete(obj,url);
						}
					}
				});
			}
			
		</script>
	</head>
	<body>
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<div class="col-xs-9 column">
							<button type="button" class="btn btn-primary pull-left" onclick="addModel();">新增</button>
							<ol class="breadcrumb pull-left">
								<li><a href="${webPath}/sysDevView/setC?setType=developer">开发者平台</a></li>
								<li class="active">pageoffice设计模板 </li>
							</ol>
						</div>
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=模板编号/模板中文名称"/>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content"  style="height: auto;">
						<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
					</div>
				</div>
			</div>
		</div>
	</body>	
</html>
