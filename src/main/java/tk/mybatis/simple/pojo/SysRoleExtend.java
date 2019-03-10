package tk.mybatis.simple.pojo;

import tk.mybatis.simple.model.SysRole;

/**
 * 角色扩展表
 * @author Administrator
 *
 */
public class SysRoleExtend extends SysRole{
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SysRoleExtend [userName=");
		builder.append(userName);
		builder.append("]");
		return builder.toString();
	}
}
