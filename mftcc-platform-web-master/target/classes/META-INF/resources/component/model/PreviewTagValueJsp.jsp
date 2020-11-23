<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%
    Map<String,Object> tableMap = (Map)request.getAttribute("tableTagMap");
    List<List<String>> rowList = new ArrayList<List<String>>();
    String showTabFlag = (String)request.getAttribute("showTabFlag");
    Boolean tipFlag = (Boolean)request.getAttribute("tipFlag");
%>
<!DOCTYPE html>

<html>
<head>
    <title>预览标签值页面</title>
</head>
<body>
<h3 align="center"><%=request.getAttribute("templateName") %></h3>
<div class="container form-container">
    <div class="scroll-content">
        <div class="col-md-10 col-md-offset-1 column margin_top_20">
            <div class="bootstarpTag fourColumn">
                <div id="tagShowDiv" class="form-title"></div>
                <% if(tipFlag){%>
                <span style="color:#b1b1b1;font-style:italic;font-size: 20px"> 该模板暂无标签 </span>
                <% } %>
            </div>

            <% if("1".equals(showTabFlag)){%>
            <div class="list-table">
                <%for(String key : tableMap.keySet()){%>
                    <div class="title">
                        <span><i class="i i-xing blockDian"></i><%=key%></span>
                    </div>
                    <div id="tableValeDiv" class="content collapse in" aria-expanded="true">
                        <table id="tableTagTab"  class="table table-bordered"  >
                                <% rowList.addAll((List)((Map)tableMap.get(key)).get("rows"));for(int a=0;a<rowList.size();a++){%>
                            <tr>
                                <%for(int b=0;b<rowList.get(a).size();b++){%>
                                <td  title="<%if("".equals(rowList.get(a).get(b))){ out.print(""); }else{ out.print(rowList.get(a).get(b)); }%>">
                                    <%if("".equals(rowList.get(a).get(b))) {
                                        out.print("未登记");
                                        }else{
                                        out.print(rowList.get(a).get(b));
                                    }%>
                                </td>
                                <% } %>
                            </tr>
                                <% };rowList = new ArrayList<List<String>>(); %>
                        </table>
                    </div>
                <% } %>
            </div>
            <%}%>

        </div>
    </div>
</div>
<script type="text/javascript">

        var html = '${html}';
        $(function() {
            /* 下拉框 */
            myCustomScrollbarForForm({
                obj: ".scroll-content",
                advanced: {
                    updateOnContentResize: true
                }
            });
            /* */
            $("#tagShowDiv").html(html);
            /*  将title值换成value值 */
            $("input").each(function (index, element) {
                $(this).attr('title', $(this).attr('value'));
            });
            /*  */
            $("#tableTagTab td").each(function (index, element) {
                if($(this).attr('title')==""){
                    $(this).css({"color":"#b1b1b1", "font-style":"italic"});
                }
            });
        });
</script>

<style>
    body{background-color: #FDFDFD}
     .table{margin-bottom: 0px}
    #tableTagTab{width: 100%;border: 0px;text-align: center;}
    #tableValeDiv{padding-left: 0px}
</style>
</body>
</html>
