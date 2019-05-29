package com.nmefc.hpcmmp.ActivitiTest;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: QuYuan
 * @Description: 分支情况下的工作流
 * @Date: Created in 23:13 2019/5/14
 * @Modified By:
 */
public class SequenceFlow_5 {
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
                .addClasspathResource("diagram/SequenceFlow.bpmn")
                .name("连线流程")
                .category("办公类别")
                .deploy();
    }

    @Test
    public void startProcess(){
        String processDefikey = "sequenceBill";
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(processDefikey);
        System.out.println("流程对象ID " + processInstance.getId());
        System.out.println("流程定义的ID  " + processInstance.getProcessDefinitionId());//默认执行最新版本
        System.out.println("流程实例的ID  " + processInstance.getProcessInstanceId());
    }

    /**
     * @description: 查询任务
     * @author: QuYuan
     * @date: 17:55 2019/5/13
     * @param: []
     * @return: void
     */
    @Test
    public void queryTask(){
        //String assignee = "王五";
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery().list();
        if(taskList != null && taskList.size()>0){
            for(Task task:taskList){
                System.out.println(task.getAssignee());
                System.out.println(task.getId());
                System.out.println(task.getName());

            }
        }
    }
    /**
     * @description: 完成任务
     * @author: QuYuan
     * @date: 18:05 2019/5/13
     * @param: []
     * @return: void
     */
    @Test
    public void completeTask(){
        String taskId = "35006";
        Map<String,Object> map = new HashMap<>();
        map.put("message","level2");
        processEngine.getTaskService().complete(taskId,map);
        System.out.println("当前任务完成");
    }
    /**
     * @description: 获取流程实例状态
     * @author: QuYuan
     * @date: 20:43 2019/5/14
     * @param: []
     * @return: void
     */
    @Test
    public void getProcessInstanceState() {
        String processInstanceId = "2501";
        ProcessInstance singleResult = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

        if (singleResult != null) {
            System.out.println("实例ID" + singleResult.getId() + "当前任务的ID" + singleResult.getActivityId());
        } else {
            System.out.println("当前实例已经结束");
        }
    }
    @Test
    public void queryHistoryProcessInst(){
        List<HistoricProcessInstance> list = processEngine.getHistoryService().createHistoricProcessInstanceQuery().list();
        if(list !=null&&list.size()>0){
            for(HistoricProcessInstance historicProcessInstance:list){
                System.out.println("历史流程实例ID"+historicProcessInstance.getId());
                System.out.println("历史流程定义ID"+historicProcessInstance.getProcessDefinitionId());
                System.out.println("历史流程实例开始时间"+historicProcessInstance.getStartTime());
                System.out.println("历史流程实例结束时间"+historicProcessInstance.getEndTime());

            }
        }
    }
    /**
     * @description: 查询历史任务
     * @author: QuYuan
     * @date: 20:42 2019/5/14
     * @param: []
     * @return: void
     */
    @Test
    public void queryHistoryTask(){
        List<HistoricTaskInstance> list = processEngine.getHistoryService().createHistoricTaskInstanceQuery().processInstanceId("2501").list();
        if(list !=null&&list.size()>0){
            for(HistoricTaskInstance historicTaskInstance:list){
                System.out.print("历史任务实例ID"+historicTaskInstance.getId());
                System.out.print("历史任务定义ID"+historicTaskInstance.getProcessDefinitionId());
                System.out.print("历史任务实例ID"+historicTaskInstance.getProcessInstanceId());
                System.out.print("历史任务实例开始时间"+historicTaskInstance.getStartTime());
                System.out.print("历史任务实例结束时间"+historicTaskInstance.getEndTime());
                System.out.print("历史任务执行人"+historicTaskInstance.getAssignee());
                System.out.println();
            }
        }
    }

    public void getAndSetVariable(){
        //有四种方式设置值
        TaskService taskService = processEngine.getTaskService();
        RuntimeService runtimeService = processEngine.getRuntimeService();

//        1.通过运行时服务设置变量

//        runtimeService.setVariable(executionId,variableName,values);
//        runtimeService.setVariableLocal(executionId,variableName,values);//只对当前执行对象有用
//        runtimeService.setVariables(executionId,varibles);

//        2.通过任务服务设置变量
//        taskService.setVariable(taskID,variableName,values);
//        taskService.setVariableLocal(taskID,variableName,values);//只对当前执行对象有用
//        taskService.setVariables(taskID,varibles);

//          3.当流程开始的时候，设置变量
//          processEngine.getRuntimeService().startProcessInstanceByKey(processKey,varibles);
//            4.当执行任务的时候，可以设置流程变量
//            processEngine.getTaskService().complete(taskId,variables);

//            有三种方式取值
//            runtimeService.getVariable(executionID,variableName);
//            runtimeService.getVariableLocal(executionID,variableName)
//            runtimeService.getVariables(variableName);
        //    taskService.getVariable(taskID,variableName);
//            taskService.getVariableLocal(taskID,variableName)
//            taskService.getVariables(variableName);

    }
    /**
     * @description: 设置任务流程变量
     * @author: QuYuan
     * @date: 20:25 2019/5/14
     * @param: []
     * @return: void
     */
    @Test
    public void setVariable(){
        //1.第一次设置流程变量
        TaskService taskService = processEngine.getTaskService();
        taskService.setVariable("20002","cost",5000);//在整个流程实例中都有效
        taskService.setVariable("20002","申请时间",new Date());//在整个流程实例中都有效
        taskService.setVariableLocal("20002","申请人","李某某");//本地设置一点该任务完成，这个变量就失效了
        System.out.println("设置成功");
        //传递自定义对象
//        PayBillBean payBillBean = new PayBillBean();
//        payBillBean.setCost(200);
//        payBillBean.setName("王某某");
//        payBillBean.setDate(new Date());
//    taskService.setVariable("20002","bean",payBillBean);//在整个流程实例中都有效
    }
    /**
     * @description: 获取任务变量
     * @author: QuYuan
     * @date: 20:40 2019/5/14
     * @param: []
     * @return: void
     */
    @Test
    public void getVariable(){
        String taskId = "20002";
        TaskService taskService = processEngine.getTaskService();
        Integer cost  = (Integer)taskService.getVariable(taskId,"cost");
        Date date = (Date)taskService.getVariable(taskId,"申请时间");
        String appayPerson = (String)taskService.getVariableLocal(taskId,"申请人");

        System.out.println(cost + " " + date + " " + appayPerson);
        //获取序列化的对象变量
//    PayBillBean bean = (PayBillBean) taskService.getVariable(taskId, "bean");
    }

}
