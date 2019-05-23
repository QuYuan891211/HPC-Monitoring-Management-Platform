package com.nmefc.hpcmmp.workflowEngine.Imp;

import com.nmefc.hpcmmp.workflowEngine.WorkflowService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
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
}
