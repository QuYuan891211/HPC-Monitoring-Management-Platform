package com.nmefc.hpcmmp.workflowEngine;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author: QuYuan
 * @Description: 工作流引擎接口
 * @Date: Created in 21:46 2019/5/17
 * @Modified By:
 */
public interface WorkflowService {
        void deployProcessDefiByBpmn(File file, String fileName,String category) throws FileNotFoundException;

        List<Deployment> findDeploymentList();

        List<ProcessDefinition> findProcessDefinitionList();

        InputStream findImageInputStream(String deploymentId, String imageName);

        void deleteProcessDefinitionByDeploymentId(String deploymentId);

}
