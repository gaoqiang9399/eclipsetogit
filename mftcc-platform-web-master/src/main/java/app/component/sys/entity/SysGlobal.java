package app.component.sys.entity;

import java.io.Serializable;

/**
 * 系统状态日期类
 * @author leopard mailto:haoxiaofeng@dhcc.com.cn
 * @date 2010-11-24
 * @see 
 * 修改记录:
 */
public class SysGlobal implements Serializable {
	
		
		private static final long serialVersionUID = -521474763013106572L;
		
		private String sysSts;              //系统状态 1.批量中 2.正常	见dic_sys_sts
		private String lstDate;             //上次系统日期
		private String sysDate;             //系统日期
		private double docSize;             //上次文件最大限制
	
		public static long getSerialVersionUID() {
			return serialVersionUID;
		}

		public String getSysSts() {
			return sysSts;
		}

		public void setSysSts(String sysSts) {
			this.sysSts = sysSts;
		}

		public String getLstDate() {
			return lstDate;
		}

		public void setLstDate(String lstDate) {
			this.lstDate = lstDate;
		}

		public String getSysDate() {
			return sysDate;
		}

		public void setSysDate(String sysDate) {
			this.sysDate = sysDate;
		}

		public double getDocSize() {
			return docSize;
		}

		public void setDocSize(double docSize) {
			this.docSize = docSize;
		}
		
}
