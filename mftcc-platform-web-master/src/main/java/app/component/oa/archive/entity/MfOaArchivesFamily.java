package app.component.oa.archive.entity;
import app.base.BaseDomain;
/**
* Title: MfOaArchivesFamily.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Feb 22 17:03:05 CST 2017
* @version：1.0
**/
public class MfOaArchivesFamily extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String familyId;//家庭成员id
	private String baseId;//基础信息ID
	private String relishion;//称谓
	private String name;//姓名
	private String age;//年龄
	private String workAddr;//工作单位
	private String position;//职位
	private String homeAddr;//家庭住址
	private String tel;//联系方式
	private String birthday;//出生日期
	private String profession;//职业

	/**
	 * @return 家庭成员id
	 */
	public String getFamilyId() {
	 	return familyId;
	}
	/**
	 * @设置 家庭成员id
	 * @param familyId
	 */
	public void setFamilyId(String familyId) {
	 	this.familyId = familyId;
	}
	/**
	 * @return 称谓
	 */
	public String getRelishion() {
	 	return relishion;
	}
	/**
	 * @设置 称谓
	 * @param relishion
	 */
	public void setRelishion(String relishion) {
	 	this.relishion = relishion;
	}
	/**
	 * @return 姓名
	 */
	public String getName() {
	 	return name;
	}
	/**
	 * @设置 姓名
	 * @param name
	 */
	public void setName(String name) {
	 	this.name = name;
	}
	/**
	 * @return 年龄
	 */
	public String getAge() {
	 	return age;
	}
	/**
	 * @设置 年龄
	 * @param age
	 */
	public void setAge(String age) {
	 	this.age = age;
	}
	/**
	 * @return 工作单位
	 */
	public String getWorkAddr() {
	 	return workAddr;
	}
	/**
	 * @设置 工作单位
	 * @param workAddr
	 */
	public void setWorkAddr(String workAddr) {
	 	this.workAddr = workAddr;
	}
	/**
	 * @return 职位
	 */
	public String getPosition() {
	 	return position;
	}
	/**
	 * @设置 职位
	 * @param position
	 */
	public void setPosition(String position) {
	 	this.position = position;
	}
	/**
	 * @return 家庭住址
	 */
	public String getHomeAddr() {
	 	return homeAddr;
	}
	/**
	 * @设置 家庭住址
	 * @param homeAddr
	 */
	public void setHomeAddr(String homeAddr) {
	 	this.homeAddr = homeAddr;
	}
	/**
	 * @return 联系方式
	 */
	public String getTel() {
	 	return tel;
	}
	/**
	 * @设置 联系方式
	 * @param tel
	 */
	public void setTel(String tel) {
	 	this.tel = tel;
	}
	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return the profession
	 */
	public String getProfession() {
		return profession;
	}
	/**
	 * @param profession the profession to set
	 */
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getBaseId() {
		return baseId;
	}
	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}
}