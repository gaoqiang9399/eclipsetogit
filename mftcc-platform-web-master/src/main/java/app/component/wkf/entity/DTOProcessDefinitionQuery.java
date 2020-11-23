package app.component.wkf.entity;

import app.base.BaseDomain;

public class DTOProcessDefinitionQuery  extends BaseDomain {
	private static final long serialVersionUID = -1058028974853718911L;
	private String id;
	private String key;
	private String name;
	private String nameLike;
	private String descLike;
	private Boolean suspended;
	private Boolean activated;
	private String deploymentId;
	private boolean groupByKey;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameLike() {
		return nameLike;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public String getDescLike() {
		return descLike;
	}

	public void setDescLike(String descLike) {
		this.descLike = descLike;
	}

	public Boolean getSuspended() {
		return suspended;
	}

	public void setSuspended(Boolean suspended) {
		this.suspended = suspended;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public boolean isGroupByKey() {
		return groupByKey;
	}

	public void setGroupByKey(boolean groupByKey) {
		this.groupByKey = groupByKey;
	}

}
