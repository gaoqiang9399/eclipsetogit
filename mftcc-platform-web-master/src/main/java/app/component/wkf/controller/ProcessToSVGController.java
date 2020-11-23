package app.component.wkf.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dhcc.workflow.IOStream;
import com.dhcc.workflow.WF;
import com.dhcc.workflow.svg.SVGHelper;

import app.common.BaseController;
@Controller
@RequestMapping(value = "/processToSVG")
public class ProcessToSVGController extends BaseController {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	
	@RequestMapping(value = "/viewProcessIns")
	public String viewProcessIns() throws Exception {
		String processInstanceId = request.getParameter("processInstanceId");
		request.setAttribute("processInstanceId", processInstanceId);
		return "/component/wkf/viewSvg";
	}
	@RequestMapping(value = "/viewSvg")
	public String viewSvg() throws Exception {
		try {
    	    String processInstanceId = request.getParameter(WF.PARAM_PROCINST_ID);
    	    int firstIndex = processInstanceId.indexOf(".");
    	    int secondIndex = processInstanceId.indexOf(".", (firstIndex + 1));
    	    if( secondIndex > 0 ) {
    	    	processInstanceId = processInstanceId.substring(0, secondIndex);
    	    }
    	    
            byte[] bs = SVGHelper.process2SVG(processInstanceId, request.getContextPath());
            if(bs == null){
            	response.setContentType("text/html");
            	response.setCharacterEncoding("UTF-8");
            	
            	PrintWriter out = response.getWriter();
        		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        		out.println("<HTML>");
        		out.println("  <HEAD><TITLE>ERROE PAGE</TITLE></HEAD>");
        		out.println("  <BODY>");
        		out.println("  <font color='red'>");
        		out.print("     ���ʵ��ID[");
        		out.print(processInstanceId);
        		out.println("]�����ڻ�ת�Ƶ���ʷ�?");
        		out.println("  </font>");
        		out.println("  </BODY>");
        		out.println("</HTML>");
        		out.flush();
        		out.close();
            	return null;
            	
//            	<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
//            	<html>
//            	<head></head>
//            	<title>123</title>
//            	<body>
//            	<iframe id="frame" src="http://124.16.136.158:8080/myapp/Parse_Draw.jsp " width="100%" frameborder="0" marginheight="0" marginwidth="0"></iframe>
//            	<script type="text/javascript">
//            	function resizeIframe() {
//            	    var height = document.documentElement.clientHeight;
//            	    height -= document.getElementById('frame').offsetTop;
//            	    
//            	    // not sure how to get this dynamically
//            	    height -= 20; /* whatever you set your body bottom margin/padding to be */
//            	    
//            	    document.getElementById('frame').style.height = height +"px";
//            	    
//            	};
//            	document.getElementById('frame').onload = resizeIframe;
//            	window.onresize = resizeIframe;
//            	</script>
//            	</body>
//            	</html>

            }
            response.setContentType("image/svg+xml");
            InputStream is = new ByteArrayInputStream(bs);
            OutputStream os = response.getOutputStream();
            IOStream.copy(is, os);
            os.flush();
            os.close();
         }
         catch (Exception ex) {
             ex.printStackTrace();
         }
		return null;
	}
}
