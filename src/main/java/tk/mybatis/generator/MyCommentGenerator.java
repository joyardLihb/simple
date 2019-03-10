package tk.mybatis.generator;

import java.util.Properties;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;
/**
 * 自己实现的注释生成器
 * @author Administrator
 *
 */
public class MyCommentGenerator extends DefaultCommentGenerator {
    private boolean suppressAllComments;

    private boolean addRemarkComments;
    
    /**
     * 设置用户配置参数
     */
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        suppressAllComments = StringUtility.isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
        addRemarkComments = StringUtility.isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));
    }
    
    public void addFieldComment(Field field,IntrospectedTable introspectedTable,IntrospectedColumn introspectedColumn){
    	//如果阻止生成所有注释，则直接返回
    	if(suppressAllComments){
    		return;
    	}
    	//文档注释开始
    	field.addJavaDocLine("/**");
    	//获取数据库字段的备注信息
    	String remarks = introspectedColumn.getRemarks();
    	//根据参数和备注信息判断是否加到注释中
    	if(addRemarkComments && StringUtility.stringHasValue(remarks)){
    		String[] remarkLines = remarks.split(System.getProperty("line.separator"));
    		for(String remarkLine: remarkLines){
    			field.addJavaDocLine(" * " + remarkLine);
    		}
    	}
    	//由于java对象名和数据库字段名可能不一样，这里保留数据库字段名
    	field.addJavaDocLine(" * " + introspectedColumn.getActualColumnName());
    	field.addJavaDocLine(" */");
    }
}
