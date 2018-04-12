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

import java.util.List;

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
    public JSONObject updateEstimateStartTimeById(TaskManager taskManager) {
        if (taskManager == null) {
            return ApiResponse.errorPara();
        }
        int updateTaskById = taskMapper.updateTaskById(taskManager);
        if (updateTaskById == 1) {
            return ApiResponse.success(updateTaskById);
        }
        return ApiResponse.error();
    }

    /**
     * @author: zlh
     * @param:  id 需要指派的任务id，userId 负责人id, isAssignAll 是否一并指派子任务
     * @description: 给任务添加负责人——指派
     * @date: 11:36 2018/4/12
     */
    @Override
    public JSONObject updateBeAssignUserById(Long id, String userId, boolean isAssignAll) {
        if (id == null && userId == null) {
            return ApiResponse.errorPara();
        }
        /*if () {
            //调用根据用户id 查询用户的接口,判断 返回结果！=null 则向下执行
        }*/
        TaskManager taskManager = new TaskManager();
        taskManager.setId(id);
        taskManager.getBeassignUser().setUserId(userId);
        taskMapper.updateTaskById(taskManager);

        if (isAssignAll) {
            // 根据父id查询所有的子任务id
            List<Long> ids = taskMapper.querySubTaskIdByParantId(id);
            // 如果未查询到子任务则返回
            if (ids.isEmpty()) {
                return ApiResponse.success();
            }
            // 遍历所有子任务进行指派
            for (Long subId : ids) {
                // 递归查询子任务是否还有子任务
                updateBeAssignUserById(subId, userId, isAssignAll);
            }
        }
        return ApiResponse.success();
    }

    /**
     * @author: zlh
     * @param: id 任务id
     * @description: 根据任务id查询任务详情
     * @date: 17:08 2018/4/12
     */
    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public JSONObject queryTaskById(Long id) {
        if (id == null) {
            return ApiResponse.errorPara();
        }
        return ApiResponse.success(taskMapper.queryTaskById(id));
    }

    /**
     * @author: zlh
     * @param: id 需要删除的任务的id，isDeleteAll 是否删除子任务
     * @description: 根据id删除任务
     * @date: 17:24 2018/4/12
     */
    @Override
    public JSONObject deleteTaskById(Long id, boolean isDeleteAll) {
        if (id == null) {
            return ApiResponse.errorPara();
        }
        int deleteTaskById = taskMapper.deleteTaskById(id);
        if (isDeleteAll) {
            // 根据父id查询所有的子任务id
            List<Long> ids = taskMapper.querySubTaskIdByParantId(id);
            if (ids.isEmpty()){
            // 如果未查询到子任务则返回
                return ApiResponse.success();
            }
            for (Long subId : ids) {
                deleteTaskById(subId, isDeleteAll);
            }
        }
        return ApiResponse.success(deleteTaskById);
    }
}
