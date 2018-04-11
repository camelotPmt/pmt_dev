package com.camelot.pmt.task.service;

import com.camelot.pmt.platform.utils.DataGrid;
import com.camelot.pmt.platform.utils.ExecuteResult;
import com.camelot.pmt.platform.utils.Pager;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author muyuanpei
 * @date 2018/4/10    15:19
 */
@Service
public interface TaskRunningService {

    /**
     *
     * @Title: queryoverdueTask @Description: TODO @param @param
     * page @param @return @return ExecuteResult<DataGrid<UserModel>> @throws
     */
    ExecuteResult<DataGrid<Map<String, Object>>> queryoverdueTaskRunning(Pager page);


}
