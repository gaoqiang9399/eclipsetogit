package app.base.shiro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import app.component.sys.entity.SysUser;
import app.component.sys.feign.SysUserFeign;

/**
 * MyShiroRealm
 *
 */
public class SysShiroRealm extends AuthorizingRealm {

	public SysShiroRealm() {
		// super();
		// super.setCachingEnabled(true);
		// super.setAuthorizationCachingEnabled(true);
	}

	private static final Logger logger = LoggerFactory.getLogger(SysShiroRealm.class);

	@Autowired
	private SysUserFeign sysUserFeign;

	/**
	 * 权限认证，为当前登录的Subject授予角色和权限
	 * 
	 * @see 经测试：本例中该方法的调用时机为需授权资源被访问时
	 * @see 经测试：并且每次访问需授权资源时都会执行该方法中的逻辑，这表明本例中默认并未启用AuthorizationCache
	 * @see 经测试：如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置），超过这个时间间隔再刷新页面，该方法会被执行
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		logger.info("##################执行Shiro权限认证##################");
		// 获取当前登录输入的用户名，等价于(String)
		// principalCollection.fromRealm(getName()).iterator().next();
		String loginName = (String) super.getAvailablePrincipal(principalCollection);
		// 到数据库查是否有此对象
		// User user=userService.findByName(loginName);//
		// 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
		// TODO
		SysUser sysUserSearch = new SysUser();
		sysUserSearch.setOpNo(loginName);
		SysUser user;
		try {
			user = sysUserFeign.getById(sysUserSearch);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if (user != null) {
			// 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			// 用户的角色集合
			Set<String> set = new HashSet<String>();
			set.add(user.getRoleNo());// "admin");//todo: roleID
			info.setRoles(set);
			// 用户的角色对应的所有权限，如果只使用角色定义访问权限，下面的四行可以不要
			List<String> list = new ArrayList<String>();
			list.add(user.getRoleNo());
			info.addStringPermissions(list);// menuURLs

			return info;
		}
		// 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
		return null;
	}

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		// UsernamePasswordToken对象用来存放提交的登录信息
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

		logger.info("验证当前Subject时获取到token为：" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));

		// 查出是否有此用户
		SysUser sysUserSearch = new SysUser();
		sysUserSearch.setOpNo(token.getUsername());
		SysUser user = null;
		try {
			user = sysUserFeign.getById(sysUserSearch);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AuthenticationException("查询用户失败！");
		}
		if (user != null && ("1").equals(user.getOpSts())) {
			// 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
			return new SimpleAuthenticationInfo(user.getOpNo(), user.getPasswordhash(), getName());
		}
		return null;
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
		// super.clearCachedAuthorizationInfo();
		// clearCachedAuthorizationInfo();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}
}