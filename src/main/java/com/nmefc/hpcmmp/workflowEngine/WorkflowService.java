package com.nmefc.hpcmmp.workflowEngine;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @Author: QuYuan
 * @Description: 工作流引擎接口
 * @Date: Created in 21:46 2019/5/17
 * @Modified By:
 */
public interface WorkflowService {
        void deployProcessDefiByBpmn(File file, String fileName,String category) throws FileNotFoundException;

}
