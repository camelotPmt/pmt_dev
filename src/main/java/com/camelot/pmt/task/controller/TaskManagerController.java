package com.camelot.pmt.task.controller;

import com.alibaba.fastjson.JSONObject;
import com.camelot.pmt.platform.common.ApiResponse;
import com.camelot.pmt.task.model.TaskManager;
import com.camelot.pmt.task.service.TaskManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zlh
 * @date 2018/4/10 16:52
 */

@RestController
@RequestMapping(value = "/task/taskManager")
@Api(value = "任务管理接口", description = "任务管理接口")
public class TaskManagerController {
    @Autowired
    private TaskManagerService taskManagerService;

    @PostMapping(value = "/queryAllTask")
    @ApiOperation(value = "查询所有任务列表接口", notes = "查询所有任务列表")
    public JSONObject queryAllTask(){
        return taskManagerService.queryAllTask();
    }

    @PostMapping(value = "/queryTaskByTask")
    @ApiOperation(value = "条件查询任务接口", notes = "根据项目、类型、截止日期、名称、状态、异常状态、负责人查询任务")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "Project", name = "project.proName", value = "项目名称", required = false),
            @ApiImplicitParam(dataType = "String", name = "taskType", value = "任务类型", required = false),
            @ApiImplicitParam(dataType = "java.util.Date", name = "estimateEndTime", value = "截止日期格式0000-00-00", required = false),
            @ApiImplicitParam(dataType = "String", name = "taskName", value = "任务名称", required = false),
            @ApiImplicitParam(dataType = "String", name = "status", value = "任务状态", required = false),
            @ApiImplicitParam(dataType = "String", name = "abnormalStatus", value = "任务异常状态", required = false),
            @ApiImplicitParam(dataType = "UserModel", name = "beassignUser.userId", value = "负责人", required = false),
    })
    public JSONObject queryTaskByTask(TaskManager taskManager){
            return taskManagerService.queryTaskByTask(taskManager);
    }

    @PostMapping(value = "/insertTask")
    @ApiOperation(value = "新增任务接口", notes = "新增任务")
    public JSONObject insertTask(TaskManager taskManager){
            return taskManagerService.insertTask(taskManager);
    }

    @PostMapping(value = "/updateTask")
    @ApiOperation(value = "修改任务接口", notes = "根据id修改任务")
    public JSONObject updateTask(TaskManager taskManager){
            return taskManagerService.updateTaskById(taskManager);
    }
}
