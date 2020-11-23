package app.component.wkf;

public class Tools {
	
	public static void main(String []ag)
	{
		String appNo="dsf";
		System.out.print(appNo);
		toUpper("SELECT WKF_USER_NAME,U.DISPLAYNAME,W.WKF_ROLE_NO,WKF_ROLE_NAME FROM WKF_APPROVAL_USER W,TBL_ORG_USER U,WKF_APPROVAL_ROLE R WHERE W.WKF_USER_NAME=U.USERNAME AND W.WKF_ROLE_NO=R.WKF_ROLE_NO");
		String s="2332";
		System.out.println(s.getBytes().length);
		s="232��";
		System.out.println(s.getBytes().length);
		
		byte[] receHead = new byte[0];
		byte[] rObject = new byte[1];
		System.arraycopy(rObject,0,receHead,0,0);
		System.out.println();
	}

	public static String toUpper(String str)
	{
		System.out.println( str.toUpperCase());
		return str.toUpperCase();
	}
}
