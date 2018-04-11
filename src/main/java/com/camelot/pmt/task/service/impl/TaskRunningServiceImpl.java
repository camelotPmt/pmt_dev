package com.camelot.pmt.task.service.impl;

import com.camelot.pmt.platform.utils.DataGrid;
import com.camelot.pmt.platform.utils.ExecuteResult;
import com.camelot.pmt.platform.utils.Pager;
import com.camelot.pmt.task.mapper.TaskLogMapper;
import com.camelot.pmt.task.mapper.TaskMapper;
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
public class TaskRunningServiceImpl {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskLogMapper taskLogMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRunningServiceImpl.class);


    public ExecuteResult<DataGrid<Map<String, Object>>> queryoverdueTaskRunning(Pager page) {
        ExecuteResult<DataGrid<Map<String, Object>>> result = new ExecuteResult<DataGrid<Map<String, Object>>>();
        try {
            List<Map<String, Object>> list = taskMapper.listTaskRunning(page);
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

}
