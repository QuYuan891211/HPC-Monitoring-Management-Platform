package com.nmefc.hpcmmp.ActivitiTest;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;


/**
 * @Author: QuYuan
 * @Description:
 * @Date: Created in 18:21 2019/5/13
 * @Modified By:
 */
public class ProcessDefinitionManager {

    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    /**
     * @description: bpmn格式部署
     * @author: QuYuan
     * @date: 18:28 2019/5/13
     * @param: []
     * @return: void
     */
@Test
    public void deployProcessDefiByBpmn(){
        processEngine.getRepositoryService().createDeployment()
                .addClasspathResource("diagram/LeaveBill.bpmn")
                .name("请求单流程")
                .category("办公类别")
                .deploy();
    }
    /**
     * @description: 以zip方式部署
     * @author: QuYuan
     * @date: 18:28 2019/5/13
     * @param: []
     * @return: void
     */
    @Test
    public void deployProcessDefiByZip(){
        InputStream in = getClass().getClassLoader().getResourceAsStream("leaveBill.zip");
        processEngine.getRepositoryService().createDeployment()
                .addZipInputStream(new ZipInputStream(in))
                .name("请求单流程")
                .category("办公类别")
                .deploy();
    }
    /**
     * @description: 查看现有流程定义
     * @author: QuYuan
     * @date: 18:34 2019/5/13
     * @param: []
     * @return: void
     */
    @Test
    public void queryProcessDefi(){
        String processDefiKey = "leaveBill";
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefiKey)
//                .orderByProcessDefinitionVersion().desc()
                .latestVersion()
                .list();
        if(list!=null&&list.size()>0){
            for(ProcessDefinition processDefinition:list){
                System.out.println(processDefinition.getCategory());
                System.out.println(processDefinition.getId());
                System.out.println(processDefinition.getDeploymentId());
                System.out.println(processDefinition.getVersion());

            }
        }
        //.latestVersion()
        //排序

    }
    /**
     * @description: 查看图片
     * @author: QuYuan
     * @date: 23:09 2019/5/13
     * @param: []
     * @return: void
     */
    public void viewImage(){

    }
    /**
     * @description: 删除流程定义
     * @author: QuYuan
     * @date: 6:13 2019/5/14
     * @param: []
     * @return: void
     */
    @Test
    public void deleteProcessDefi(){
        String key = "1";
        processEngine.getRepositoryService().deleteDeployment(key);
    }


}
