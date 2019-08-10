package com.nmefc.hpcmmp.controller.dutyManagement;

import com.nmefc.hpcmmp.common.enumPackage.ResponseMsg;
import com.nmefc.hpcmmp.workflowEngine.WorkflowService;
import com.nmefc.hpcmmp.workflowEngine.entity.WorkflowBean;
import org.activiti.bpmn.exceptions.XMLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

/**
 * @Author: QuYuan
 * @Description:
 * @Date: Created in 15:49 2019/7/29
 * @Modified By:
 */
@Controller
@RequestMapping("/incident")
public class incidentController {

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
    public String newDepolyByClasspath(@RequestBody WorkflowBean workflowBean){
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
    public String newDepolyByZipInputStream(){
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
}
