package app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.core.struts.taglib.FormTag;
import com.core.struts.taglib.ListTableTag;
import com.core.struts.taglib.PmsTag;
import com.core.struts.taglib.SysRoleButtonTag;
import com.core.struts.taglib.third.BigFormTag;
import com.core.struts.taglib.third.BootstarpTag;
import com.core.struts.taglib.third.MarkPoint;
import com.core.struts.taglib.third.PropertySeeTag;
import com.core.struts.taglib.third.RecordCountTag;
import com.core.struts.taglib.third.ThirdButtonTag;
import com.core.struts.taglib.third.ThirdTableTag;
@Configuration
public class TldTag{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5760091325401904523L;

	@Bean
	public BootstarpTag getBootstarpTag(){
		return new BootstarpTag();
	}
	
	@Bean
	public ListTableTag getTableTag(){
		return new ListTableTag();
	}
	@Bean
	public SysRoleButtonTag getButtonTag(){
		return new SysRoleButtonTag();
	}
	@Bean
	public FormTag getFormTag(){
		return new FormTag();
	}
	@Bean
	public ThirdTableTag getThirdTableTag(){
		return new ThirdTableTag();
	}
	@Bean
	public BigFormTag getBigFormTag(){
		return new BigFormTag();
	}
	@Bean
	public MarkPoint getMarkPoint(){
		return new MarkPoint();
	}
	@Bean
	public PropertySeeTag getPropertySeeTag(){
		return new PropertySeeTag();
	}
	@Bean
	public RecordCountTag getRecordCountTag(){
		return new RecordCountTag();
	}
	@Bean
	public ThirdButtonTag getThirdButton(){
		return new ThirdButtonTag();
	}
	@Bean
	public PmsTag getPmsTag(){
		return new PmsTag();
	}
}
