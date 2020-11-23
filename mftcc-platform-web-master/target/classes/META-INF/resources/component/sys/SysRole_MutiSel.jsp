<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>

<style type="text/css">
	.risk_class_item{
	width: 130px;
	float: left;
    list-style-type: none;
    margin-left: 50px;
    height: 30px;
    vertical-align: middle;
    padding-right: 6px;
    color: #ffffff;
	}
	.risk_class{
		margin-top: 20px;
	}
	.risk_class li{
		cursor: pointer;
	}
	.risk_class li.item_false{

	    border: 1px solid #cacaca;
	    background: #cacaca;
	}
	.risk_class li.item_false:hover{
	    border: 1px solid #adadad;
	    background: #adadad;
	}
	.risk_class li.item_false:hover span.checkbox_false i{
	    color:#adadad;
	}
	.risk_class li.item_true{
	    border: 1px solid #32b5cb;
	    background: #32b5cb;

	}
	.risk_class li.item_true:hover{
	    border: 1px solid #009db7;
	    background: #009db7;
	}
	.risk_class li.item_true:hover span.checkbox_true i{
	   color:#009db7;
	}

	.risk_class li span.checkbox_true {
		text-align: center;
		color: #32b5cb;
		font-size: 22px;
	}


	.risk_class li span.checkbox_false {
		text-align: center;
		color: #cacaca;
		font-size: 22px;
	}

	.risk_class  .item_key{
		display:none;
	}
	.risk_class li span {
	    line-height: 0;
	    padding: 0px;
	    width: 28px;
	    height: 28px;
	    display: inline-block;
	    vertical-align: middle;
	    border: 0 none;
	    cursor: pointer;
	    outline: none;
	    background-color: #fff;
	    padding-top: 3px;
	}
	.sysRole-tr{
		height: 50px;
	}
</style>
<script type="text/javascript">
	$(function(){
		var roleNo = '${roleNo}';
		roleNo = roleNo.split("|");
		var dataMap = <%=request.getAttribute("dataMap")%>;
		var sysRoleList = dataMap.sysRoleList;
		var html = "<table><tbody>";
		var ind = 0;
		$.each(sysRoleList,function(i,sysRole){
			ind++;
			if(ind == 1){
				html = html + "<tr class='sysRole-tr'><td>";
			}else{
				html = html + "<td>";
			}
			html = html + '<li class="risk_class_item item_false" data-roleno="'+sysRole.roleNo+'" data-rolename="'+sysRole.roleName+'" title="'+sysRole.roleName+'"  onclick="getNotSelectedItem(this);">'
					+'<span class="checkbox_false"><i class="i i-gouxuan"></i></span> '
					+'<label class="item_key"></label>'
					+'<lable style="padding-left: 5px;">'+sysRole.roleName+'</lable>'
					+'</li>';
			if(ind == 3){
				html = html + "</td></tr>";
				ind =0;
			}else{
				html = html + "</td>";
			}
		});
		if(ind != 3){
			html = html + "</tr>";
		}

		html = html +"</tbody></table>";
		$("#container").append(html);

		$.each(roleNo,function(i,roleNoThis){
			if(roleNoThis){
				var $obj = $("li[data-roleno="+roleNoThis+"]");
				var spanCheckBox = $obj.find("span");
				spanCheckBox.removeClass("checkbox_false");
				spanCheckBox.addClass("checkbox_true");
				$obj.removeClass("item_false");
				$obj.addClass("item_true");
			}
		});

		$("#container").height($("body").height()-95);
	 });
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
	};
	function confirm(){
		var roleNo="";
		var roleName="";
		var sysRole = new Object();
		var length = $("li.risk_class_item.item_true").length;
		$("li.risk_class_item.item_true").each(function(i,objThis){
			if(i == length-1){
				roleNo = roleNo + $(objThis).data("roleno");
				roleName = roleName + $(objThis).data("rolename");
			}else{
				roleNo = roleNo + $(objThis).data("roleno")+"|";
				roleName = roleName + $(objThis).data("rolename")+"|";
			}
		});
		sysRole.roleNo = roleNo;
		sysRole.roleName = roleName;
		parent.dialog.get("SysRoleDialog").close(sysRole);
	};
</script>
</head>
	<body class="bg-white" style="overflow-y: hidden;">
		<div class="container risk_class" id="container">
			
		</div>
		<div class="formRowCenter" style="margin-bottom:20px">
			<dhcc:thirdButton value="确认" action="确认" onclick="confirm();"></dhcc:thirdButton>
		</div>
	</body>
</html>