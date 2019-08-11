package com.nmefc.hpcmmp.controller.common;

import com.nmefc.hpcmmp.common.enumPackage.ResponseMsg;
import com.nmefc.hpcmmp.workflowEngine.WorkflowService;
import com.nmefc.hpcmmp.workflowEngine.entity.WorkflowBean;
import org.activiti.bpmn.exceptions.XMLException;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

/**
 * @Author: QuYuan
 * @Description:
 * @Date: Created in 15:49 2019/7/29
 * @Modified By:
 */
@Controller
@RequestMapping("/workflow")
public class WorkflowController {

    @Autowired
    private WorkflowService workflowService;
    /**
     * @description: 使用资源文件的方式部署BPMN文件
     * @author: QuYuan
     * @date: 9:14 2019/8/10
     * @param: [workflowBean]
     * @return: java.lang.String
     */
    @ResponseBody
    @PostMapping(value = "/newDepolyByClasspath")
    public String newDeployByClasspath(@RequestBody WorkflowBean workflowBean){
        if(workflowBean == null){ return ResponseMsg.PARAMETERS_MISSING.getValue();}
        String path = workflowBean.getPath();
        String name = workflowBean.getFilename();
        String category = workflowBean.getCategory();
        try {
            workflowService.deployProcessByClasspath(path,name,category);
            return ResponseMsg.SUCCESS.getValue();
        } catch (Exception e) {
            e.printStackTrace();
            String error_message = "";
            if (e instanceof FileNotFoundException){
                error_message = ResponseMsg.FILE_NOT_FOUND.getValue();
            }
            if(e instanceof XMLException){
                error_message = ResponseMsg.FILE_FORMAT_ERROR.getValue();
            }
            return error_message;
        }
    }
    /**
     * @description: 使用上传zip文件方式(Zip包中为1个bpmn文件和一个流程图png文件)，加入前端后需修改次方法
     * @author: QuYuan
     * @date: 9:16 2019/8/10
     * @param: []
     * @return: java.lang.String
     */
    @ResponseBody
    @PostMapping(value = "/newDepolyByZipInputStream")
    public String newDeployByZipInputStream(){
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("processes/ExclusiveGateway.zip");
            ZipInputStream zipInputStream = new ZipInputStream(inputStream);
            workflowService.deployProcessDefiByBpmn(zipInputStream,"test1","test");
            return ResponseMsg.SUCCESS.getValue();
        } catch (Exception e) {
            e.printStackTrace();
            String error_message = "";

            if (e instanceof NullPointerException){
                error_message = ResponseMsg.FILE_NOT_FOUND.getValue();
            }
            if (e instanceof ZipException){
                error_message = ResponseMsg.FILE_FORMAT_ERROR.getValue();
            }
            return error_message;
        }
    }
    /**
     * @description: 查询所有流程定义（不分页）
     * @author: QuYuan
     * @date: 11:09 2019/8/10
     * @param: []
     * @return: java.lang.String
     */
    @ResponseBody
    @GetMapping(value = "/getProcessDefinitionList")
    public List<String> findProcessDefinitionList(){
       List<ProcessDefinition> processDefinitions = workflowService.findProcessDefinitionList();
       List<String> nameList = new ArrayList<>();
       if (processDefinitions != null || processDefinitions.size()>0){
           for(ProcessDefinition processDefinition:processDefinitions){
                nameList.add(processDefinition.getKey());
           }
       }
        return  nameList;
    }
    /**
     * @description: 根据key查找流程定义
     * @author: QuYuan
     * @date: 11:34 2019/8/10
     * @param: [key]
     * @return: java.lang.String
     */
    @ResponseBody
    @PostMapping(value = "/findProcessDefinitionByKey")
    public String findProcessDefinitionByKey(String key){

        if(key != null || key.length()>0 ){
          ProcessDefinition  processDefinition =workflowService.findProcessDefinitionByKey(key);
            return processDefinition.getName();
        }
        return ResponseMsg.PARAMETERS_MISSING.getValue();
    }

    @ResponseBody
    @PostMapping(value = "/deleteProcessDefinition")
    public String deleteProcessDefinition(String deploymentId){

        if(deploymentId != null || deploymentId.length()>0 ){
            workflowService.deleteProcessDefinitionByDeploymentId(deploymentId);
            return ResponseMsg.SUCCESS.getValue();
        }
        return ResponseMsg.PARAMETERS_MISSING.getValue();
    }
}
