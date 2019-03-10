package tk.mybatis.simple.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;
import tk.mybatis.simple.pojo.SysRoleExtend;

public interface UserMapper {
	/**
	 * 新增用户
	 * @param sysUser
	 * @return
	 */
	int insert(SysUser sysUser);
	/**
	 * 新增用户，返回数据库自增序列主键给插入对象测试，只适用支持主键自增的数据库（mysql）
	 * @param sysUser
	 * @return
	 */
	int insert2(SysUser sysUser);
	/**
	 * 新增用户，适用selectKey方式，适用支持主键自增的数据库（mysql）和非自增而通过序列获取的数据库（oracle)
	 * @param sysUser
	 * @return
	 */
	int insert3(SysUser sysUser);
	/**
	 * 批量新增用户，利用foreach，目前不支持oracle
	 * @param sysUser
	 * @return
	 */
	int insertList(List<SysUser> sysUserList);
	/**
	 * 通过主键ID更新用户
	 * @param sysUser
	 * @return
	 */
	int updateById(SysUser sysUser);
	/**
	 * 通过主键ID更新用户,有选择的更新
	 * @param sysUser
	 * @return
	 */
	int updateBySelective(SysUser sysUser);
	/**
	 * 通过map更新列
	 * @param sysUser
	 * @return
	 */
	int updateByMap(Map<String,Object> map);
	/**
	 * 通过主键ID删除用户
	 * @param userId
	 * @return
	 */
	int deleteById(Long userId);
	/**
	 * 通过ID查询用户
	 * @param id
	 * @return
	 */
	SysUser selectById(Long id);
	/**
	 * 查询所有用户
	 * @return
	 */
	List<SysUser> selectAll();
	/**
	 * 通过用户信息查询相关的用户列表，利用动态SQL和OGNL表达式支持模糊查询和if条件查询
	 * @param sysUser
	 * @return
	 */
	List<SysUser> selectByUser(SysUser sysUser);
	/**
	 * 通过ID或者用户名查询用户信息
	 * @param sysUser
	 * @return
	 */
	SysUser selectByIdOrUserName(SysUser sysUser);
	/**
	 * 通过ID查询用户及用户所属角色信息（假设用户与角色一对一关系）
	 * @param id
	 * @return
	 */
	SysUser selectUserAndRoleById(Long id);
	/**
	 * 通过ID查询用户及用户所属角色信息（假设用户与角色一对一关系）
	 * @param id
	 * @return
	 */
	SysUser selectUserAndRoleById2(Long id);
	/**
	 * 通过分成两次查询的方法查询用户所属角色（假设用户与角色一对一关系）
	 * @param id
	 * @return
	 */
	SysUser selectUserAndRoleByIdSelect(Long id);
	/**
	 * 通过ID查询用户及用户所属角色信息（假设用户与角色一对多关系）
	 * @return
	 */
	List<SysUser> selectAllUserAndRole();
	/**
	 * 通过ID集合查询用户信息
	 * @param idList
	 * @return
	 */
	List<SysUser> selectByIdList(List<Long> idList);
	/**
	 * 根据用户ID查询用户拥有的角色信息
	 * @param userId
	 * @return
	 */
	List<SysRole> selectRolesByUserId(Long userId);
	/**
	 * 根据用户ID和角色enabled标志查询用户拥有的角色信息。多个参数时，适用@Param注解表明在XML中使用参数的key，否则会报错
	 * @param userId
	 * @param enabled
	 * @return
	 */
	List<SysRole> selectRolesByUserIdAndEnabled(@Param("userId")Long userId,@Param("enabled")Integer enabled);
	/**
	 * 也可以通过将参数放入Map中实现上边的多个查询参数的需求，由于这种方式还需要手动创建Map，并赋值，所以并不简洁，参数较少时不推荐使用
	 * 参数较多时可以使用，可以避免函数参数太多。PS:在Mapper接口层可以重载函数，在XML中不能有相同ID的函数即可
	 * @param param
	 * @return
	 */
	List<SysRole> selectRolesByUserIdAndEnabled(Map<String,Object> param);
	/**
	 * 根据用户ID查询用户拥有的角色信息及用户名，少量其他对象信息的情况，使用扩展类
	 * @param userId
	 * @return
	 */
	List<SysRoleExtend> selectRoleExtendsByUserId(Long userId);
	/**
	 * 根据用户ID查询用户拥有的角色信息，同时带出全部用户对象信息，使用扩展类组合类
	 * @param userId
	 * @return
	 */
	List<SysRole> selectRoleAndUsersByUserId(Long userId);
	/**
	 * connection嵌套方式查询用户信息，懒加载模式
	 */
	SysUser selectAllRolePrivilegesById(Long userId);
	
	/**
	 * 通过ID查询用户（存储过程方式）
	 * @param id
	 * @return
	 */
	void selectUserById(SysUser sysUser);
	
	/**
	 * 根据用户名分页查询用户，调用存储过程
	 * @param params
	 * @return
	 */
	List<SysUser> selectUserPage(Map<String,Object> params);
	/**
	 * 保存用户及角色信息，调用存储过程
	 * @param sysUser
	 * @param roleIds
	 * @return
	 */
	void insertUserAndRoles(@Param("user")SysUser sysUser,@Param("roleIds")String roleIds);
	/**
	 * 通过ID删除用户及其角色信息，调用存储过程
	 * @param id
	 * @return
	 */
	void deleteUserById(Long id);
	
	/**
	 * 通过ID查询用户信息，通过Map返回
	 * @param id
	 * @return
	 */
	Map<String,Object> selectUserMapInfoById(Long id);
}
