<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
	<title>列表表单</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
	<script type="text/javascript" >
		var aloneFlag = true;
		var dataDocParm = {
			relNo : '${pssNo}',
			docType : "pss",
			docTypeName : "附件",
			docSplitName : "",
			query : "",
		};
	</script>
</head>
<body>
   <div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div column">
					<div class="show-btn" style="float:left">
						<button type="button" class="btn btn-default" onclick="javascript:alert('建设中，敬请关注...',1);">导入附件</button>
						<button type="button" class="btn btn-primary" onclick="addATB();">生成销货单</button>
					</div>
				</div>
				<!-- 我的筛选选中后的显示块 -->
				<div class="search-div">
					<div class="col-xs-9 column mysearch-div" id="pills">
						<div class="mod-toolbar-top">
							<div class="left">
								<form id="pssATBListForm">
									<input type="radio" id="radioReturn" name="top-saleFlag" />
									<span class="txt">已生成销售单</span>
									<a class="ui-btn" onclick="my_Search();" id="psssearch">查询</a>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 文件上传  -->
		<div class="col-xs-12 column" style="width:1140px;top:-5px;padding-left: 20px">
			<div id="doc_div"></div>
			<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
		</div>
		  <!-- <div class="group">
		  <h3><span class="cboxWrap groupchk"><i class="cbox"></i>2017-09-12</span></h3>
		  <ul class="list cf">
		  <li class="picItem" id="795793591424">
		  <div class="itemInner">
		  <span class="cboxWrap"><i class="cbox"></i></span>
		  <span style="width: 40px; height: 24px;">
		  <img height="160" width="180" src="component\pss\pic\11.jpg" href="" /></span>
		  <span class="tools top"><a href="#" class="del btn" data-id="795793591424"></a></span>
		  </div>
		  <div class="tools bottom">素材图片</div>
		  </li>
		  </ul>
		  </div> -->
	</div>
</body>
</html>