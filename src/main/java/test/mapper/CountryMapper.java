package test.mapper;

import java.util.List;
import test.model.Country;

public interface CountryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table COUNTRY
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Short id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table COUNTRY
     *
     * @mbggenerated
     */
    int insert(Country record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table COUNTRY
     *
     * @mbggenerated
     */
    Country selectByPrimaryKey(Short id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table COUNTRY
     *
     * @mbggenerated
     */
    List<Country> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table COUNTRY
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Country record);
}