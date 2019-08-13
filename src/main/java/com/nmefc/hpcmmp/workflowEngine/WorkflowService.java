package com.nmefc.hpcmmp.workflowEngine;

import com.nmefc.hpcmmp.entity.management.User;
import com.nmefc.hpcmmp.workflowEngine.entity.Bill;
import com.nmefc.hpcmmp.workflowEngine.entity.WorkflowBean;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * @Author: QuYuan
 * @Description: 工作流引擎接口
 * @Date: Created in 21:46 2019/5/17
 * @Modified By:
 */
public interface WorkflowService<T extends Bill> {
        void deployProcessDefiByBpmn(ZipInputStream zipInputStream, String fileName, String category) throws Exception;

        void deployProcessByClasspath(String path,String fileName,String category)throws Exception;

        List<Deployment> findDeploymentList();

        List<ProcessDefinition> findProcessDefinitionList();

        InputStream findImageInputStream(String deploymentId, String imageName);

        void deleteProcessDefinitionByDeploymentId(String deploymentId);

        boolean startProcess(WorkflowBean workflowBean,User user);

        List<Task> findTaskListByName(String name);

        String findTaskFormKeyByTaskId(String taskId);

        T findBillByTaskId(String taskId);

//        List<String> findOutComeListByTaskId(String taskId);

        List<Comment> findCommentByTaskId(String taskId);

//        List<Comment> findCommentByObject(Long id);

        ProcessDefinition findProcessDefinitionByTaskId(String taskId);

        ProcessDefinition findProcessDefinitionByKey(String key);
}
