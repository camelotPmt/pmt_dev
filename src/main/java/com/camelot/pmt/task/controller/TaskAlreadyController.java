package com.camelot.pmt.task.controller;

import com.alibaba.fastjson.JSONObject;
import com.camelot.pmt.platform.common.ApiResponse;
import com.camelot.pmt.platform.utils.DataGrid;
import com.camelot.pmt.platform.utils.ExecuteResult;
import com.camelot.pmt.platform.utils.Pager;
import com.camelot.pmt.task.service.TaskAlreadyService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;


@RestController
@RequestMapping("/task/taskAlready")
@Api(value = "我的工作台-我的已办-接口", description = "我的工作台-我的已办-接口")
public class TaskAlreadyController {

    @Autowired
    private TaskAlreadyService taskAlreadyService;


    /**
     *
     * @Title: queryUserAll @Description: TODO查询所有已完成的任务 @param @return @return
     *         JSONObject @throws
     *         myp
     */
    @ApiOperation(value = "查询所有已完成的任务", notes = "查询所有已完成的任务")
    @RequestMapping(value = "/queryoverdueTaskAlready", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "rows", value = "每页数量", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "query", dataType = "Long") })
    public JSONObject queryoverdueTaskAlready(@ApiIgnore Pager page, @ApiIgnore  Long id) {
        ExecuteResult<DataGrid<Map<String, Object>>> result = new ExecuteResult<DataGrid<Map<String, Object>>>();
        try {
            if (page == null) {
                return ApiResponse.errorPara();
            }
            result = taskAlreadyService.queryoverdueTaskAlready(page,id);
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
     * Description:[重做功能]
     * </p>
     *
     * @param id
     *          任务id
     * @return {"status": {"message": "请求处理成功.","code": 200}, "data": {userModel}]
     */
    @ApiOperation(value = "实现重做任务功能", notes = "实现重做任务功能")
    @RequestMapping(value = "/updateRepetitiveOperation;", method = RequestMethod.POST)
    public JSONObject updateRepetitiveOperation(
            @ApiParam(name = "id", value = "任务id", required = true) @RequestParam(required = true) Long id) {
        ExecuteResult<Long> result = new ExecuteResult<Long>();
        try {
            result = taskAlreadyService.updateRepetitiveOperation(id);
            if (result.isSuccess()) {
                return ApiResponse.success(result.getResult());
            }
            return ApiResponse.error();
        } catch (Exception e) {
            return ApiResponse.error();
        }
    }


}
