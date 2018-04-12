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
     * @description: 根据任务id修改任务
     * @date: 10:18 2018/4/12
     */
    JSONObject updateTaskById(TaskManager taskManager);
}
