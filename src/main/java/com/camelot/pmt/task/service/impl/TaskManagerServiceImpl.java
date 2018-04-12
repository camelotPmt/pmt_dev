package com.camelot.pmt.task.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.camelot.pmt.platform.common.ApiResponse;
import com.camelot.pmt.task.mapper.TaskMapper;
import com.camelot.pmt.task.model.Task;
import com.camelot.pmt.task.model.TaskManager;
import com.camelot.pmt.task.service.TaskManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zlh
 * @date 2018/4/9 16:27
 */
@Service
public class TaskManagerServiceImpl implements TaskManagerService {

    @Autowired
    private TaskMapper taskMapper;

    /**
     * @author: zlh
     * @param:
     * @description: 查询所有任务
     * @date: 16:54 2018/4/9
     */
    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public JSONObject queryAllTask() {
        return ApiResponse.success(taskMapper.queryAllTask());
    }

    /**
     * @author: zlh
     * @param taskManager 模糊查询的条件
     * @description: 根据条件查询任务
     */
    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public JSONObject queryTaskByTask(TaskManager taskManager) {
        if (taskManager == null) {
            return ApiResponse.errorPara();
        }
        return ApiResponse.success(taskMapper.queryTaskByTask(taskManager));
    }

    /**
     * @author: zlh
     * @param:  taskManager 插入任务的数据
     * @description: 新增任务
     * @date: 9:10 2018/4/12
     */
    @Override
    public JSONObject insertTask(TaskManager taskManager) {
        if (taskManager == null) {
            return ApiResponse.errorPara();
        }
        //默认状态下任务状态为未开始
        taskManager.setStatus("未开始");

        int insertTask = taskMapper.insertTask(taskManager);
        if (insertTask == 1) {
            return ApiResponse.success();
        }
        return ApiResponse.error();
    }

    /**
     * @author: zlh
     * @param: taskManager 需要修改的任务数据
     * @description: 修改任务-任务延期
     * @date: 10:18 2018/4/12
     */
    @Override
    public JSONObject updateTaskById(TaskManager taskManager) {
        if (taskManager == null) {
            return ApiResponse.errorPara();
        }
        int updateTaskById = taskMapper.updateTaskById(taskManager);
        if (updateTaskById == 1) {
            return ApiResponse.success(updateTaskById);
        }
        return ApiResponse.error();
    }
}
