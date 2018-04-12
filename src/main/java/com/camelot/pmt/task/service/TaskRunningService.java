package com.camelot.pmt.task.service;

import com.camelot.pmt.platform.user.model.UserModel;
import com.camelot.pmt.platform.utils.DataGrid;
import com.camelot.pmt.platform.utils.ExecuteResult;
import com.camelot.pmt.platform.utils.Pager;
import com.camelot.pmt.task.model.Task;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author muyuanpei
 * @date 2018/4/10    15:19
 */
public interface TaskRunningService {

    /**
     *
     * @Title: queryoverdueTaskRunning @Description: TODO @param @param
     * page @param @return @return ExecuteResult<DataGrid<Map<String, Object>>> @throws
     */
    ExecuteResult<DataGrid<Map<String, Object>>> queryoverdueTaskRunning(Pager page);

    /**
     *
     * @Title: updateStatus @Description: TODO @param @param
     * page @param @return @return ExecuteResult<DataGrid<Long>> @throws
     */
    ExecuteResult<Long> updateStatus(Long id);

    /**
     *
     * @Title: updateStatusFinish @Description: TODO @param @param
     * page @param @return @return ExecuteResult<DataGrid<Long>> @throws
     */
    ExecuteResult<Long> updateStatusFinish(Long id);


    /**
     *
     * @Title: updateStatusFinish @Description: TODO @param @param
     * page @param @return @return ExecuteResult<DataGrid<Long>> @throws
     */
    ExecuteResult<Task> queryTaskById(Long id);


}
