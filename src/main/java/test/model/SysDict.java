package test.model;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table sys_dict
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class SysDict {
    /**
     * 主键
     * id
     */
    private Long id;

    /**
     * 类别
     * code
     */
    private String code;

    /**
     * 字典名
     * name
     */
    private String name;

    /**
     * 字典值
     * value
     */
    private String value;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict.id
     *
     * @return the value of sys_dict.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict.id
     *
     * @param id the value for sys_dict.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict.code
     *
     * @return the value of sys_dict.code
     *
     * @mbggenerated
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict.code
     *
     * @param code the value for sys_dict.code
     *
     * @mbggenerated
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict.name
     *
     * @return the value of sys_dict.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict.name
     *
     * @param name the value for sys_dict.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict.value
     *
     * @return the value of sys_dict.value
     *
     * @mbggenerated
     */
    public String getValue() {
        return value;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict.value
     *
     * @param value the value for sys_dict.value
     *
     * @mbggenerated
     */
    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }
}