package com.camelot.pmt.task.controller;

import java.util.List;

//import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.camelot.pmt.platform.common.APIStatus;
import com.camelot.pmt.platform.common.ApiResponse;
import com.camelot.pmt.platform.role.model.Role;
import com.camelot.pmt.platform.user.model.UserModel;
import com.camelot.pmt.platform.utils.DataGrid;
import com.camelot.pmt.platform.utils.ExecuteResult;
import com.camelot.pmt.platform.utils.Pager;
import com.camelot.pmt.task.model.Task;
import com.camelot.pmt.task.service.TaskService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
	* @ClassName: TaskController
	* @Description: TODO
	* @author zhangao
	* @date 2018年4月9日
	*
 */
@RestController
@RequestMapping("/task")
@Api(value = "任务管理接口", description = "任务管理接口")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	/**
	 * 
	* @Title: queryUserAll
	* @Description: TODO查询所有任务
	* @param @return
	* @return JSONObject 
	* @throws
	 */
	@ApiOperation(value = "查询逾期所有任务", notes = "查询逾期所有任务")
	@RequestMapping(value = "/queryoverdueTask", method = RequestMethod.GET)
    @ApiImplicitParams({
	    	@ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "int"),
	    	@ApiImplicitParam(name = "rows", value = "每页数量", required = true, paramType = "query", dataType = "int")
	  })
	public JSONObject queryoverdueTask(@ApiIgnore Pager page){
		ExecuteResult<DataGrid<Task>> result = new ExecuteResult<DataGrid<Task>>();
		try {
			if(page == null) {
    			return ApiResponse.errorPara();
    		}
			result = taskService.queryoverdueTask(page);
			if (result.isSuccess()) {
				return ApiResponse.success(result.getResult());
			}
			return ApiResponse.error();
		} catch (Exception e) {
			return ApiResponse.error();
		}
	}
	
}
	