package com.camelot.pmt.task.controller;

import com.alibaba.fastjson.JSONObject;
import com.camelot.pmt.platform.common.ApiResponse;
import com.camelot.pmt.platform.user.model.UserModel;
import com.camelot.pmt.platform.utils.DataGrid;
import com.camelot.pmt.platform.utils.ExecuteResult;
import com.camelot.pmt.platform.utils.Pager;
import com.camelot.pmt.task.service.TaskRunningService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author muyuanpei
 * @date 2018/4/10    15:18
 */

@RestController
@RequestMapping("/task/taskRunning")
@Api(value = "我的工作台-正在进行-接口", description = "我的工作台-正在进行-接口")
public class TaskRunningController {

    @Autowired
    private TaskRunningService taskRunningService;

    /**
     *
     * @Title: queryUserAll @Description: TODO查询所有任务 @param @return @return
     *         JSONObject @throws
     */
    @ApiOperation(value = "查询所有正在进行的任务", notes = "查询所有正在进行的任务")
    @RequestMapping(value = "/queryoverdueTaskRunning", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "rows", value = "每页数量", required = true, paramType = "query", dataType = "int") })
    public JSONObject queryoverdueTaskRunning(@ApiIgnore Pager page) {
        ExecuteResult<DataGrid<Map<String, Object>>> result = new ExecuteResult<DataGrid<Map<String, Object>>>();
        try {
            if (page == null) {
                return ApiResponse.errorPara();
            }
            result = taskRunningService.queryoverdueTaskRunning(page);
            if (result.isSuccess()) {
                return ApiResponse.success(result.getResult());
            }
            return ApiResponse.error();
        } catch (Exception e) {
            return ApiResponse.error();
        }
    }


    /**
     * <p>
     * Description:[关闭任务]
     * </p>
     *
     * @param id
     *          任务id
     * @return {"status": {"message": "请求处理成功.","code": 200}, "data": {userModel}]
     */
    @ApiOperation(value = "实现根据id关闭任务功能", notes = "实现根据id关闭任务功能")
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    public JSONObject updateStatus(
            @ApiParam(name = "id", value = "任务id", required = true) @RequestParam(required = true) String id) {
        ExecuteResult<Long> result = new ExecuteResult<Long>();
        try {
            result = taskRunningService.updateStatus(id);
            if (result.isSuccess()) {
                return ApiResponse.success(result.getResult());
            }
            return ApiResponse.error();
        } catch (Exception e) {
            return ApiResponse.error();
        }
    }

    /**
     * <p>
     * Description:[完成任务]
     * </p>
     *
     * @param id
     *          任务id
     * @return {"status": {"message": "请求处理成功.","code": 200}, "data": {userModel}]
     */
    @ApiOperation(value = "实现任务完成功能", notes = "实现任务完成功能")
    @RequestMapping(value = "/updateStatusFinish", method = RequestMethod.POST)
    public JSONObject updateStatusFinish(
            @ApiParam(name = "id", value = "任务id", required = true) @RequestParam(required = true) String id) {
        ExecuteResult<Long> result = new ExecuteResult<Long>();
        try {
            result = taskRunningService.updateStatusFinish(id);
            if (result.isSuccess()) {
                return ApiResponse.success(result.getResult());
            }
            return ApiResponse.error();
        } catch (Exception e) {
            return ApiResponse.error();
        }
    }

}
