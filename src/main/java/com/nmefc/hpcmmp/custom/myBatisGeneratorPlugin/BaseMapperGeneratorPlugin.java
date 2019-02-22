package com.nmefc.hpcmmp.custom.myBatisGeneratorPlugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * @Author: QuYuan
 * @Description: 让MyBatis Generator产生的Mapper更简洁,自行建立BaseMapper并让所有mapper继承
 * @Date: Created in 13:53 2019/2/22
 * @Modified By:
 */
public class BaseMapperGeneratorPlugin extends PluginAdapter {
    public boolean validate(List<String> warnings) {
        return true;
    }

    public boolean clientGenerated(Interface interfaze,
                                   TopLevelClass topLevelClass, IntrospectedTable introspectedTable){
        FullyQualifiedJavaType fullyQualifiedJavaType = new FullyQualifiedJavaType("BaseMapper<" + introspectedTable.getBaseRecordType() + "," + introspectedTable.getExampleType()+"," + "java.lang.Integer" + ">");
        FullyQualifiedJavaType importedType = new FullyQualifiedJavaType("com.nmefc.hpcmmp.dao.BaseMapper");
//        添加继承
        interfaze.addSuperInterface(fullyQualifiedJavaType);
//        导包
        interfaze.addImportedType(importedType);

//        清空所有方法
        interfaze.getMethods().clear();
//        清空所有注解
        interfaze.getAnnotations().clear();
        return true;
    }


}
