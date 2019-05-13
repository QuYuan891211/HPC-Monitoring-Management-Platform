package com.nmefc.hpcmmp.ActivitiTest;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;


/**
 * @Author: QuYuan
 * @Description: 模拟工作流框架
 * @Date: Created in 10:47 2019/5/13
 * @Modified By:
 */
public class TestActiviti {
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//    /**
//     * @description: 获取工具流引擎
//     * @author: QuYuan
//     * @date: 11:12 2019/5/13
//     * @param: []
//     * @return: void
//     */
  //  @Test
 //   public void createProcessEngine(){
        //1. 方式1 使用代码方式创建
//        ProcessEngineConfiguration engineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
//        //1.1. 获取引擎
//        engineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
//        engineConfiguration.setJdbcUrl("jdbc:mysql://localhost:3306/activitiDB?createDatabaseIfNotExist=true"
//                +"&useUnicode=true&characterEncoding=utf8");
//        engineConfiguration.setJdbcUsername("root");
//        engineConfiguration.setJdbcPassword("admin123");
//        //1.2. 设置建表策略
//        //		  public static final java.lang.String DB_SCHEMA_UPDATE_FALSE = "false";//不会自动创建表，没有表，则抛异常
//        //		  public static final java.lang.String DB_SCHEMA_UPDATE_CREATE_DROP = "create-drop";//先删除，再创建表
//        //		  public static final java.lang.String DB_SCHEMA_UPDATE_TRUE = "true";//假如没有表，则自动创建
//        engineConfiguration.setDatabaseSchemaUpdate("true");
//        //1.3 生成引擎
//        ProcessEngine processEngine = engineConfiguration.buildProcessEngine();
//        System.out.println("流程引擎创建成功");
        //2. 通过加载配置文件来创建工具流引擎
//        ProcessEngineConfiguration engineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
//        ProcessEngine processEngine = engineConfiguration.buildProcessEngine();
//        System.out.println("加载配置文件创建引擎");
        //3. 通过流程引擎自动加载
        //默认会加载类路径下的activiti.cfg.xml


  //  }
    /**
     * @description: 部署
     * @author: QuYuan
     * @date: 17:21 2019/5/13
     * @param:
     * @return:
     */
    @Test
    public void deploy(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment().addClasspathResource("diagram/LeaveBill.bpmn")
        .name("请求单流程")
        .category("办公类别")
        .deploy();
        System.out.println("部署完成");
    }
}
