package tk.mybatis.simple.model;
/**
 * 角色权限关联表
 * @author Administrator
 *
 */
public class SysRolePrivilege {
	private Long roleId;
	
	private Long privilegeId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SysRolePrivilege [roleId=");
		builder.append(roleId);
		builder.append(", privilegeId=");
		builder.append(privilegeId);
		builder.append("]");
		return builder.toString();
	}
	
}
