<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/auth/js/MfCusCredit_List.js?v=${cssJsVersion}"></script>
		<script type="text/javascript" >
			$(function(){
				MfCusCredit_List.init();
			});
		</script>
        <style type="text/css">
            .change-td-color-4{
                color:red !important;
            }
            .change-td-color-5{
                color:green !important;
            }
            .change-td-color-7{
                color:red !important;
            }
            .change-td-color-8{
                color:red !important;
            }
            .change-td-color-6{
                color:red !important;
            }

        </style>
	</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div style="display:none;">
					<input name="cusName" type="hidden"></input>
					<input name="cusNo" type="hidden"></input>
				</div>
				<div class="btn-div">
					<dhcc:pmsTag pmsId="auth-credit-list-add">
						<button type="button" class="btn btn-primary" onclick="MfCusCredit_List.input();">新增</button>
					</dhcc:pmsTag>
				</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/授信申请号"/>
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
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
	<script type="text/javascript">
		/*我的筛选加载的json*/
		filter_dic = [
		     {
                  "optName": "授信总额",
                  "parm": [],
                  "optCode":"creditSum",
                  "dicType":"num"
              }, {
                  "optName": "授信期限",
                  "parm": [],
                  "optCode":"creditTerm",
                  "dicType":"num"
              },{
                  "optName": "授信开始日期",
                  "parm": [],
                  "optCode":"beginDate",
                  "dicType":"date"
              },{
                  "optName": "授信结束日期",
                  "parm": [],
                  "optCode":"endDate",
                  "dicType":"date"
              },{
                  "optName": "客户类型",
                  "parm": ${cusTypeJsonArray},
                  "optCode":"cusType",
                  "dicType":"y_n"
              }
          ];
          
	</script>
</html>
