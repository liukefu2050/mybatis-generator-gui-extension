
package com.spawpaw.mybatis.generator.gui.plugin;

import com.itfsw.mybatis.generator.plugins.utils.BasePlugin;
import com.itfsw.mybatis.generator.plugins.utils.XmlElementGeneratorTools;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * ---------------------------------------------------------------------------
 * 365me 适配插件
 * ---------------------------------------------------------------------------
 */
public class Me365Plugin extends BasePlugin {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(List<String> warnings) {

        // 插件使用前提是数据库为MySQL或者SQLserver，因为返回主键使用了JDBC的getGenereatedKeys方法获取主键
        if ("com.mysql.jdbc.Driver".equalsIgnoreCase(this.getContext().getJdbcConnectionConfiguration().getDriverClass()) == false
                && "com.microsoft.jdbc.sqlserver.SQLServer".equalsIgnoreCase(this.getContext().getJdbcConnectionConfiguration().getDriverClass()) == false
                && "com.microsoft.sqlserver.jdbc.SQLServerDriver".equalsIgnoreCase(this.getContext().getJdbcConnectionConfiguration().getDriverClass()) == false
                && "com.mysql.cj.jdbc.Driver".equalsIgnoreCase(this.getContext().getJdbcConnectionConfiguration().getDriverClass()) == false) {
            warnings.add("itfsw:插件" + this.getClass().getTypeName() + "插件使用前提是数据库为MySQL或者SQLserver，因为返回主键使用了JDBC的getGenereatedKeys方法获取主键！");
            return false;
        }

        return super.validate(warnings);
    }

    /**
     * SQL Map Methods 生成
     * 具体执行顺序 http://www.mybatis.org/generator/reference/pluggingIn.html
     * @param document
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        // 1. selectAll
        XmlElement selectAllEle = new XmlElement("select");
        selectAllEle.addAttribute(new Attribute("id", "selectAll"));
        // 参数类型
        selectAllEle.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        // 添加注释(!!!必须添加注释，overwrite覆盖生成时，@see XmlFileMergerJaxp.isGeneratedNode会去判断注释中是否存在OLD_ELEMENT_TAGS中的一点，例子：@mbg.generated)
        commentGenerator.addComment(selectAllEle);

        // 使用JDBC的getGenereatedKeys方法获取主键并赋值到keyProperty设置的领域模型属性中。所以只支持MYSQL和SQLServer
        XmlElementGeneratorTools.useGeneratedKeys(selectAllEle, introspectedTable);

        selectAllEle.addElement(new TextElement("select <include refid=\"Base_Column_List\"/> from "));
        selectAllEle.addElement(new TextElement(introspectedTable.getFullyQualifiedTableNameAtRuntime()));

        document.getRootElement().addElement(selectAllEle);
        logger.debug("Me365Plugin插件:" + introspectedTable.getMyBatis3XmlMapperFileName() + "增加selectAll实现方法。");

        // 2. selectByCondition
        XmlElement selectByConditionEle = new XmlElement("select");
        selectByConditionEle.addAttribute(new Attribute("parameterType", "java.lang.String"));
        selectByConditionEle.addAttribute(new Attribute("id", "selectByCondition"));
        // 参数类型
        selectByConditionEle.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        // 添加注释(!!!必须添加注释，overwrite覆盖生成时，@see XmlFileMergerJaxp.isGeneratedNode会去判断注释中是否存在OLD_ELEMENT_TAGS中的一点，例子：@mbg.generated)
        commentGenerator.addComment(selectByConditionEle);

        // 使用JDBC的getGenereatedKeys方法获取主键并赋值到keyProperty设置的领域模型属性中。所以只支持MYSQL和SQLServer
        XmlElementGeneratorTools.useGeneratedKeys(selectByConditionEle, introspectedTable);

        selectByConditionEle.addElement(new TextElement("select <include refid=\"Base_Column_List\"/> from "));
        selectByConditionEle.addElement(new TextElement(introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        selectByConditionEle.addElement(new TextElement("where ${value}"));

        document.getRootElement().addElement(selectByConditionEle);
        logger.debug("Me365Plugin插件:" + introspectedTable.getMyBatis3XmlMapperFileName() + "增加selectByCondition实现方法。");

        // 3. selectMapsByCondition
        XmlElement selectMapsByConditionEle = new XmlElement("select");
        selectMapsByConditionEle.addAttribute(new Attribute("parameterType", "java.lang.String"));
        selectMapsByConditionEle.addAttribute(new Attribute("id", "selectMapsByCondition"));
        // 参数类型
        selectMapsByConditionEle.addAttribute(new Attribute("resultType", "java.util.HashMap"));
        // 添加注释(!!!必须添加注释，overwrite覆盖生成时，@see XmlFileMergerJaxp.isGeneratedNode会去判断注释中是否存在OLD_ELEMENT_TAGS中的一点，例子：@mbg.generated)
        commentGenerator.addComment(selectMapsByConditionEle);

        // 使用JDBC的getGenereatedKeys方法获取主键并赋值到keyProperty设置的领域模型属性中。所以只支持MYSQL和SQLServer
        XmlElementGeneratorTools.useGeneratedKeys(selectMapsByConditionEle, introspectedTable);

        selectMapsByConditionEle.addElement(new TextElement("select <include refid=\"Base_Column_List\"/>  from "));
        selectMapsByConditionEle.addElement(new TextElement(introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        selectMapsByConditionEle.addElement(new TextElement("where ${value}"));

        document.getRootElement().addElement(selectMapsByConditionEle);
        logger.debug("Me365Plugin插件:" + introspectedTable.getMyBatis3XmlMapperFileName() + "增加selectMapsByCondition实现方法。");

        // 4. countByCondition
        XmlElement countByConditionEle = new XmlElement("select");
        countByConditionEle.addAttribute(new Attribute("parameterType", "java.lang.String"));
        countByConditionEle.addAttribute(new Attribute("id", "countByCondition"));
        // 参数类型
        countByConditionEle.addAttribute(new Attribute("resultType", "int"));
        // 添加注释(!!!必须添加注释，overwrite覆盖生成时，@see XmlFileMergerJaxp.isGeneratedNode会去判断注释中是否存在OLD_ELEMENT_TAGS中的一点，例子：@mbg.generated)
        commentGenerator.addComment(countByConditionEle);

        // 使用JDBC的getGenereatedKeys方法获取主键并赋值到keyProperty设置的领域模型属性中。所以只支持MYSQL和SQLServer
        XmlElementGeneratorTools.useGeneratedKeys(countByConditionEle, introspectedTable);

        String keyColumnName = "id";
        if(introspectedTable.getPrimaryKeyColumns()!=null && introspectedTable.getPrimaryKeyColumns().size()>0){
            keyColumnName = introspectedTable.getPrimaryKeyColumns().get(0).getActualColumnName();
        }
        countByConditionEle.addElement(new TextElement("select count("+keyColumnName+")  from "));
        countByConditionEle.addElement(new TextElement(introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        countByConditionEle.addElement(new TextElement("where ${value}"));

        document.getRootElement().addElement(countByConditionEle);
        logger.debug("Me365Plugin插件:" + introspectedTable.getMyBatis3XmlMapperFileName() + "增加countByCondition实现方法。");

        // 5. deleteByCondition
        XmlElement deleteByConditionEle = new XmlElement("delete");
        deleteByConditionEle.addAttribute(new Attribute("id", "deleteByCondition"));
        deleteByConditionEle.addAttribute(new Attribute("parameterType", "java.lang.String"));
        // 添加注释(!!!必须添加注释，overwrite覆盖生成时，@see XmlFileMergerJaxp.isGeneratedNode会去判断注释中是否存在OLD_ELEMENT_TAGS中的一点，例子：@mbg.generated)
        commentGenerator.addComment(deleteByConditionEle);

        // 使用JDBC的getGenereatedKeys方法获取主键并赋值到keyProperty设置的领域模型属性中。所以只支持MYSQL和SQLServer
        XmlElementGeneratorTools.useGeneratedKeys(deleteByConditionEle, introspectedTable);

        deleteByConditionEle.addElement(new TextElement(" delete from  "));
        deleteByConditionEle.addElement(new TextElement(introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        deleteByConditionEle.addElement(new TextElement("where ${value}"));

        document.getRootElement().addElement(deleteByConditionEle);
        logger.debug("Me365Plugin插件:" + introspectedTable.getMyBatis3XmlMapperFileName() + "增加deleteByCondition实现方法。");

        // 6. discardByCondition
        XmlElement discardByConditionEle = new XmlElement("update");
        discardByConditionEle.addAttribute(new Attribute("parameterType", "java.lang.String"));
        discardByConditionEle.addAttribute(new Attribute("id", "discardByCondition"));
        // 添加注释(!!!必须添加注释，overwrite覆盖生成时，@see XmlFileMergerJaxp.isGeneratedNode会去判断注释中是否存在OLD_ELEMENT_TAGS中的一点，例子：@mbg.generated)
        commentGenerator.addComment(discardByConditionEle);

        // 使用JDBC的getGenereatedKeys方法获取主键并赋值到keyProperty设置的领域模型属性中。所以只支持MYSQL和SQLServer
        XmlElementGeneratorTools.useGeneratedKeys(discardByConditionEle, introspectedTable);

        discardByConditionEle.addElement(new TextElement("    update "));
        discardByConditionEle.addElement(new TextElement(introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        discardByConditionEle.addElement(new TextElement("set dr = 1"));
        discardByConditionEle.addElement(new TextElement("where ${value}"));

        document.getRootElement().addElement(discardByConditionEle);
        logger.debug("Me365Plugin插件:" + introspectedTable.getMyBatis3XmlMapperFileName() + "增加discardByCondition实现方法。");


        // 7. discardByPrimaryKey
        XmlElement discardByPrimaryKeyEle = new XmlElement("update");
        discardByPrimaryKeyEle.addAttribute(new Attribute("parameterType", "java.lang.String"));
        discardByPrimaryKeyEle.addAttribute(new Attribute("id", "discardByPrimaryKey"));
        // 添加注释(!!!必须添加注释，overwrite覆盖生成时，@see XmlFileMergerJaxp.isGeneratedNode会去判断注释中是否存在OLD_ELEMENT_TAGS中的一点，例子：@mbg.generated)
        commentGenerator.addComment(discardByPrimaryKeyEle);

        // 使用JDBC的getGenereatedKeys方法获取主键并赋值到keyProperty设置的领域模型属性中。所以只支持MYSQL和SQLServer
        XmlElementGeneratorTools.useGeneratedKeys(discardByPrimaryKeyEle, introspectedTable);

        discardByPrimaryKeyEle.addElement(new TextElement("update "));
        discardByPrimaryKeyEle.addElement(new TextElement(introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        discardByPrimaryKeyEle.addElement(new TextElement("set ts = #{ts,jdbcType=CHAR}, dr = #{dr,jdbcType=INTEGER}"));

        discardByPrimaryKeyEle.addElement(new TextElement("where " + keyColumnName+" = #{"+keyColumnName+",jdbcType=CHAR} " ));

        document.getRootElement().addElement(discardByPrimaryKeyEle);
        logger.debug("Me365Plugin插件:" + introspectedTable.getMyBatis3XmlMapperFileName() + "增加discardByPrimaryKey实现方法。");

        // 8. batchDiscard
        XmlElement batchDiscardEle = new XmlElement("update");
        batchDiscardEle.addAttribute(new Attribute("parameterType", "java.lang.String"));
        batchDiscardEle.addAttribute(new Attribute("id", "batchDiscard"));
        // 添加注释(!!!必须添加注释，overwrite覆盖生成时，@see XmlFileMergerJaxp.isGeneratedNode会去判断注释中是否存在OLD_ELEMENT_TAGS中的一点，例子：@mbg.generated)
        commentGenerator.addComment(batchDiscardEle);

        // 使用JDBC的getGenereatedKeys方法获取主键并赋值到keyProperty设置的领域模型属性中。所以只支持MYSQL和SQLServer
        XmlElementGeneratorTools.useGeneratedKeys(batchDiscardEle, introspectedTable);

        batchDiscardEle.addElement(new TextElement("update "));
        batchDiscardEle.addElement(new TextElement(introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        batchDiscardEle.addElement(new TextElement("set ts = #{ts,jdbcType=CHAR}, dr = #{dr,jdbcType=INTEGER}, pk_updator = #{pk_user,jdbcType=CHAR}"));

        batchDiscardEle.addElement(new TextElement("where " + keyColumnName+" in  " ));
        batchDiscardEle.addElement(new TextElement(" <foreach collection=\"pk_list\" item=\"obj\" index=\"index\" open=\"(\" separator=\",\" close=\")\" >  " ));
        batchDiscardEle.addElement(new TextElement(" #{obj} " ));
        batchDiscardEle.addElement(new TextElement(" </foreach> " ));

        document.getRootElement().addElement(batchDiscardEle);
        logger.debug("Me365Plugin插件:" + introspectedTable.getMyBatis3XmlMapperFileName() + "增加batchDiscard实现方法。");

        return true;
    }

}