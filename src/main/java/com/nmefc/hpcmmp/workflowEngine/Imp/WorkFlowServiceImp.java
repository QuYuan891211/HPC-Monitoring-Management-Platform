package com.nmefc.hpcmmp.workflowEngine.Imp;

import com.nmefc.hpcmmp.workflowEngine.WorkflowService;
import com.nmefc.hpcmmp.workflowEngine.entity.WorkflowBean;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * @Author: QuYuan
 * @Description: 工作流引擎服务实现类
 * @Date: Created in 22:00 2019/5/17
 * @Modified By:
 */
public class WorkFlowServiceImp implements WorkflowService{

    @Autowired
    private RepositoryService repositoryService;
    /**
     * @description: 上传BPMN流程定义并部署
     * @author: QuYuan
     * @date: 23:10 2019/5/17
     * @param: [file, fileName, category]
     * @return: void
     */
    @Override
    public void deployProcessDefiByBpmn(File file, String fileName,String category)  {
        try {
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
            repositoryService.createDeployment().addZipInputStream(zipInputStream)
//                    1. 添加部署的流程定义名称
                    .name(fileName)
//                    2. 添加流程定义的类别
                    .category(category)
//                    3.部署
                    .deploy();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
/**
 * @description: 查看部署信息
 * @author: QuYuan
 * @date: 12:16 2019/5/23
 * @param: []
 * @return: java.util.List<org.activiti.engine.repository.Deployment>
 */
    @Override
    public List<Deployment> findDeploymentList() {
        return repositoryService.createDeploymentQuery().orderByDeploymenTime().asc().list();
    }
/**
 * @description: 查看流程定义信息
 * @author: QuYuan
 * @date: 12:21 2019/5/23
 * @param: []
 * @return: java.util.List<org.activiti.engine.repository.ProcessDefinition>
 */
    @Override
    public List<ProcessDefinition> findProcessDefinitionList() {
        return repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionVersion().asc().list();
    }
/**
 * @description: 获取流程定义的图片信息
 * @author: QuYuan
 * @date: 12:23 2019/5/23
 * @param: [deploymentId, imageName]
 * @return: java.io.InputStream
 */
    @Override
    public InputStream findImageInputStream(String deploymentId, String imageName) {
        return repositoryService.getResourceAsStream(deploymentId, imageName)
    }
    /**
     * @description: 根据部署ID删除流程定义
     * @author: QuYuan
     * @date: 12:24 2019/5/23
     * @param: [deploymentId]
     * @return: void
     */
    @Override
    public void deleteProcessDefinitionByDeploymentId(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId,true);
    }

    @Override
    public void startProcess(WorkflowBean workflowBean) {
//1：获取请假单ID，使用请假单ID，查询请假单的对象LeaveBill
        Long id = workflowBean.getId();
        LeaveBill leaveBill = leaveBillDao.findLeaveBillById(id);
        //2：更新请假单的请假状态从0变成1（初始录入-->审核中）
        leaveBill.setState(1);
        //3：使用当前对象获取到流程定义的key（对象的名称就是流程定义的key）
        String key = leaveBill.getClass().getSimpleName();
        /**
         * 4：从Session中获取当前任务的办理人，使用流程变量设置下一个任务的办理人
         * inputUser是流程变量的名称，
         * 获取的办理人是流程变量的值
         */
        Map<String, Object> variables = new HashMap<String,Object>();
        variables.put("inputUser", SessionContext.get().getName());//表示惟一用户
        /**
         * 5：	(1)使用流程变量设置字符串（格式：LeaveBill.id的形式），通过设置，让启动的流程（流程实例）关联业务
         (2)使用正在执行对象表中的一个字段BUSINESS_KEY（Activiti提供的一个字段），让启动的流程（流程实例）关联业务
         */
        //格式：LeaveBill.id的形式（使用流程变量）
        String objId = key+"."+id;
        variables.put("objId", objId);
        //6：使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
        runtimeService.startProcessInstanceByKey(key,objId,variables);
    }

    @Override
    public List<Task> findTaskListByName(String name) {
        return null;
    }


}
