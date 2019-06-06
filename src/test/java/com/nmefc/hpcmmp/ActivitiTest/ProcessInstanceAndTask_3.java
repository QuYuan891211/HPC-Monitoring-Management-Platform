package com.nmefc.hpcmmp.ActivitiTest;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * @Author: QuYuan
 * @Description: 处理流程对象与流程实例
 * @Date: Created in 6:32 2019/5/14
 * @Modified By:
 */
public class ProcessInstanceAndTask_3 {
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    public void startProcess(){
        String processDefikey = "hotelBill";
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

    public void completeTask(){
        String taskId = "7502";
        processEngine.getTaskService().complete(taskId);
        System.out.println("当前任务完成");
    }

    public void getProcessInstanceState() {
        String processInstanceId = "20001";
        ProcessInstance singleResult = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

        if (singleResult != null) {
            System.out.println("实例ID" + singleResult.getId() + "当前任务的ID" + singleResult.getActivityId());
        } else {
            System.out.println("当前实例已经结束");
        }
    }

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


}
