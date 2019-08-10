package com.nmefc.hpcmmp.workflowEngine.Imp;

import com.nmefc.hpcmmp.common.utils.UserSessionContexts;
import com.nmefc.hpcmmp.workflowEngine.WorkflowService;
import com.nmefc.hpcmmp.workflowEngine.entity.WorkflowBean;
import org.activiti.engine.*;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
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
@Service("workflowService")
public class WorkFlowServiceImp<T> implements WorkflowService{

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FormService formService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private HistoryService historyService;
    /**
     * @description: 上传BPMN流程定义并部署
     * @author: QuYuan
     * @date: 23:10 2019/5/17
     * @param: [file, fileName, category]
     * @return: void
     */
    @Override
    public void deployProcessDefiByBpmn(ZipInputStream zipInputStream, String fileName,String category)  {
            repositoryService.createDeployment().addZipInputStream(zipInputStream)
//                    1. 添加部署的流程定义名称
                    .name(fileName)
//                    2. 添加流程定义的类别
//                    3.部署
//            影响的表：
//            act_re_procdef ：流程定义表 ：
//            该表的key属性 是bpmn 的 id决定
//            该表的name属性  是bpmn 的name 属性决定
//            act_re_deployment：部署表 ：id是由act_ge_property的 next_dbid决定
//            act_ge_property ：通用属性表
                    .category(category)
                    .deploy();
    }

    /**
     * @description: 根据路径获取BPMN并部署
     * @author: QuYuan
     * @date: 18:24 2019/8/8
     * @param: [path, fileName, category]
     * @return: boolean
     */
    @Override
    public void deployProcessByClasspath(String path, String fileName, String category) {
            repositoryService.createDeployment()
                    .name(fileName)
                    .addClasspathResource(path)
                    .category(category)
                    .deploy();
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
        return repositoryService.getResourceAsStream(deploymentId, imageName);
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
    public boolean startProcess(WorkflowBean workflowBean, Object object) {
        Long id = workflowBean.getId();
//        使用当前对象获取到流程定义的key（对象的名称就是流程定义的key）
        String key = object.getClass().getSimpleName();
//        从Session中获取当前任务的办理人，使用流程变量设置下一个任务的办理人
//         inputUser是流程变量的名称，
//         获取的办理人是流程变量的值
         Map<String,Object> varibleMap = new HashMap<>();

         if (UserSessionContexts.getUser()!=null){
             varibleMap.put("inputUser", UserSessionContexts.getUser().getName());
             String objId = key+"."+id;
             varibleMap.put("objId", objId);
             runtimeService.startProcessInstanceByKey(key,objId,varibleMap);
             return true;
         }
        return false;
    }


////1：获取请假单ID，使用请假单ID，查询请假单的对象LeaveBill
//        Long id = workflowBean.getId();
//        LeaveBill leaveBill = leaveBillDao.findLeaveBillById(id);
//        //2：更新请假单的请假状态从0变成1（初始录入-->审核中）
//        leaveBill.setState(1);
//        //3：使用当前对象获取到流程定义的key（对象的名称就是流程定义的key）
//        String key = leaveBill.getClass().getSimpleName();
//        /**
//         * 4：从Session中获取当前任务的办理人，使用流程变量设置下一个任务的办理人
//         * inputUser是流程变量的名称，
//         * 获取的办理人是流程变量的值
//         */
//        Map<String, Object> variables = new HashMap<String,Object>();
//        variables.put("inputUser", SessionContext.get().getName());//表示惟一用户
//        /**
//         * 5：	(1)使用流程变量设置字符串（格式：LeaveBill.id的形式），通过设置，让启动的流程（流程实例）关联业务
//         (2)使用正在执行对象表中的一个字段BUSINESS_KEY（Activiti提供的一个字段），让启动的流程（流程实例）关联业务
//         */
//        //格式：LeaveBill.id的形式（使用流程变量）
//        String objId = key+"."+id;
//        variables.put("objId", objId);
//        //6：使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
//        runtimeService.startProcessInstanceByKey(key,objId,variables);


    /**
     * @description: 根据执行人获取任务列表
     * @author: QuYuan
     * @date: 15:03 2019/5/23
     * @param: [name]
     * @return: java.util.List<org.activiti.engine.task.Task>
     */
    @Override
    public List<Task> findTaskListByName(String name) {
        return taskService.createTaskQuery().taskAssignee(name).orderByTaskCreateTime().asc().list();
    }
/**
 * @description: 使用
 * @author: QuYuan
 * @date: 15:09 2019/5/23
 * @param: [taskId]
 * @return: java.lang.String
 */
    @Override
    public String findTaskFormKeyByTaskId(String taskId) {
        TaskFormData formData = formService.getTaskFormData(taskId);
        //获取Form key的值
        String url = formData.getFormKey();
        return url;

    }

    /**
     * @description: 根据任务ID返回事件单
     * @author: QuYuan
     * @date: 16:02 2019/5/23
     * @param: [taskId]
     * @return: java.lang.Object
     */
    @Override
    public Object findBillByTaskId(String taskId) {
        //1：使用任务ID，查询任务对象Task
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        //2：使用任务对象Task获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        //3：使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //4：使用流程实例对象获取BUSINESS_KEY
        String buniness_key = processInstance.getBusinessKey();
        //5：获取BUSINESS_KEY对应的主键ID，使用主键ID，查询
        String id = "";
        if(buniness_key != null && buniness_key.length()>0){
            //截取字符串，取buniness_key小数点的第2个值
            id = buniness_key.split("\\.")[1];
        }

        return null;
    }
///**
// * @description: 已知任务ID，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中
// * @author: QuYuan
// * @date: 16:55 2019/5/23
// * @param: [taskId]
// * @return: java.util.List<java.lang.String>
// */
////    @Override
////    public abstract List<String> findOutComeListByTaskId(String taskId);
////
////
    /**
     * @description: 获取批注信息，传递当前任务ID，获取历史任务ID对应的
     * @author: QuYuan
     * @date: 17:22 2019/5/23
     * @param: [taskId]
     * @return: java.util.List<org.activiti.engine.task.Comment>
     */
   public List<Comment> findCommentByTaskId(String taskId){
        List<Comment> list = new ArrayList<>();
       //1：使用任务ID，查询任务对象Task
       Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
       //2：使用任务对象Task获取流程实例ID
       String processInstanceId = task.getProcessInstanceId();
       list = taskService.getProcessInstanceComments(processInstanceId);
       return list;
   }

/**
 * @description: 根据事件单获取历史批注信息
 * @author: QuYuan
 * @date: 18:09 2019/5/23
 * @param: [object]
 * @return: java.util.List<org.activiti.engine.task.Comment>
 */
   public List<Comment> findCommentByObject(Object object,Long id){
       //获取对象的名称
       String objectName = object.getClass().getSimpleName();
       //组织流程表中的字段中的值
       String objId = objectName+"."+id;
       //:使用历史的流程变量查询，返回历史的流程变量的对象，获取流程实例ID
       HistoricVariableInstance hvi = historyService.createHistoricVariableInstanceQuery()
               .variableValueEquals("objId",id)
               .singleResult();
       String processInstanceId = hvi.getProcessInstanceId();
      return taskService.getProcessInstanceComments(processInstanceId);
   }
/**
 * @description: 获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象
 * @author: QuYuan
 * @date: 18:26 2019/5/23
 * @param: [taskId]
 * @return: org.activiti.engine.repository.ProcessDefinition
 */
    @Override
    public ProcessDefinition findProcessDefinitionByTaskId(String taskId) {
        Task task = taskService.createTaskQuery()//
                .taskId(taskId)//使用任务ID查询
                .singleResult();
        //获取流程定义ID
        String processDefinitionId = task.getProcessDefinitionId();
        //查询流程定义的对象
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()//创建流程定义查询对象，对应表act_re_procdef
                .processDefinitionId(processDefinitionId)//使用流程定义ID查询
                .singleResult();
        return processDefinition;
    }



}
