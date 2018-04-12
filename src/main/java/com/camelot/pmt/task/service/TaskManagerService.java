package com.camelot.pmt.task.service;

import com.alibaba.fastjson.JSONObject;
import com.camelot.pmt.task.model.Task;
import com.camelot.pmt.task.model.TaskManager;

/**
 * @author zlh
 * @date 2018/4/9 16:25
 */
public interface TaskManagerService {

    /**
     * @author: zlh
     * @param:
     * @description: 查询所有任务列表
     * @date: 16:54 2018/4/9
     */
    JSONObject queryAllTask();

    /**
     * @author: zlh
     * @param taskManager 模糊查询的条件
     * @description: 根据条件查询任务
     * @return
     */
    JSONObject queryTaskByTask(TaskManager taskManager);

    /**
     * @author: zlh
     * @param:  taskManager 插入任务的数据
     * @description: 新增任务
     * @date: 9:10 2018/4/12
     */
    JSONObject insertTask(TaskManager taskManager);

    /**
     * @author: zlh
     * @param: taskManager 需要修改的任务数据
     * @description: 任务延期
     * @date: 10:18 2018/4/12
     */
    JSONObject updateEstimateStartTimeById(TaskManager taskManager);

    /**
     * @author: zlh
     * @param:  taskManager 需要修改的任务数据
     * @description: 指派
     * @date: 11:36 2018/4/12
     */
    JSONObject updateBeAssignUserById(Long id, String userId, boolean isAssignAll);

    /**
     * @author: zlh
     * @param: id 任务id
     * @description: 根据任务id查询任务详情
     * @date: 17:08 2018/4/12
     */
    JSONObject queryTaskById(Long id);

    /**
     * @author: zlh
     * @param: id 需要删除的任务的id，isDeleteAll 是否删除子任务
     * @description: 根据id删除任务
     * @date: 17:24 2018/4/12
     */
    JSONObject deleteTaskById(Long id, boolean isDeleteAll);
}
