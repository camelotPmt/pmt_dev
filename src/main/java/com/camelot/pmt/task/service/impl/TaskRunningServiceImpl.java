package com.camelot.pmt.task.service.impl;

import com.camelot.pmt.platform.user.model.UserModel;
import com.camelot.pmt.platform.utils.DataGrid;
import com.camelot.pmt.platform.utils.ExecuteResult;
import com.camelot.pmt.platform.utils.Pager;
import com.camelot.pmt.task.mapper.TaskLogMapper;
import com.camelot.pmt.task.mapper.TaskMapper;
import com.camelot.pmt.task.model.Task;
import com.camelot.pmt.task.service.TaskRunningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author muyuanpei
 * @date 2018/4/10    15:20
 */

@Service
public class TaskRunningServiceImpl implements TaskRunningService{

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskLogMapper taskLogMapper;


    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRunningServiceImpl.class);


    public ExecuteResult<DataGrid<Map<String, Object>>> queryoverdueTaskRunning(Pager page, Long id) {
        ExecuteResult<DataGrid<Map<String, Object>>> result = new ExecuteResult<DataGrid<Map<String, Object>>>();
        try {
            List<Map<String, Object>> list = taskMapper.listTaskRunning(page, id);
            // 如果没有查询到数据，不继续进行
            if (CollectionUtils.isEmpty(list)) {
                DataGrid<Map<String, Object>> dg = new DataGrid<Map<String, Object>>();
                result.setResult(dg);
                return result;
            }
            DataGrid<Map<String, Object>> dg = new DataGrid<Map<String, Object>>();
            dg.setRows(list);
            // 查询总条数
            Long total = taskMapper.queryRunningCount();
            dg.setTotal(total);
            result.setResult(dg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * <p>
     * Description:[根据id关闭任务]
     * <p>
     *
     * @return ExecuteResult<long>
     */
    @Override
    public ExecuteResult<Long> updateStatus(Long id) {
        ExecuteResult<Long> result = new ExecuteResult<Long>();
        try {
            if (!id.equals("") && !id.equals("0")) {
                Long updateStatusResult = taskMapper.updateStatus(id);
                result.setResult(updateStatusResult);
                return result;
            }
            result.addErrorMessage("关闭失败！");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }


    /**
     * <p>
     * Description:[完成任务完成功能]
     * <p>
     *
     * @return ExecuteResult<long>
     */
    @Override
    public ExecuteResult<Long> updateStatusFinish(Long id) {
        ExecuteResult<Long> result = new ExecuteResult<Long>();
        try {
            if (!id.equals("") && !id.equals("0")) {
                Long updateStatusResult = taskMapper.updateStatus(id);
                result.setResult(updateStatusResult);
                return result;
            }
            result.addErrorMessage("完成失败！");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * <p>
     * Description:[根据id获取单个任务明细]
     * <p>
     *
     * @return ExecuteResult<Task>
     */
    @Override
    public ExecuteResult<Task> queryTaskById(Long id) {
        ExecuteResult<Task> result = new ExecuteResult<Task>();
        try {
            if (!id.equals("") && !id.equals("0")) {
                Task queryResult = taskMapper.selectTaskById(id);
                result.setResult(queryResult);
                return result;
            }
            result.addErrorMessage("查询失败！");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

}
