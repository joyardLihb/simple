package tk.mybatis.simple.mapper;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespaceRef;

import tk.mybatis.simple.model.SysRole;
//@CacheNamespaceRef(RoleMapper.class)//配置二级缓存
public interface RoleMapper {
	/**
	 * 查询所有的角色及权限信息
	 * @return
	 */
	List<SysRole> selectAllRoleAndPrivilege();
	
	/**
	 * 通过ID查询角色
	 * @param id
	 * @return
	 */
	SysRole selectById(Long id);
	/**
	 * 通过userId查询用户所属的角色列表
	 * @param userId
	 * @return
	 */
	List<SysRole> selectRolesByUserId(Long userId);
	
	/**
	 * 根据主键更新
	 */
	int updateByPrimaryKey(SysRole sysRole);
}
