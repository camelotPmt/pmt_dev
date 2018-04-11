package com.camelot.pmt.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.camelot.pmt.platform.common.APIStatus;
import com.camelot.pmt.platform.common.ApiResponse;
import com.camelot.pmt.platform.user.model.UserModel;
import com.camelot.pmt.platform.utils.ExecuteResult;
import com.camelot.pmt.task.model.Task;
import com.camelot.pmt.task.service.TaskPendingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
* @ClassName: TaskPendingController
* @Description: TODO(任务-我的待办)
* @author gxl
* @date 2018年4月9日 下午5:09:40
*
 */

@RestController
@RequestMapping(value="/task/taskPending")
@Api("我的工作台-我的待办：管理接口")
public class TaskPendingController {
	
	@Autowired
	private TaskPendingService taskPendingService;
	
	/**
	 * 
	* @Title: addUser 
	* @Description: TODO(添加Task对象接口方法) 
	* @param @param task
	* @param @return    设定文件 
	* @return JSONObject    返回类型 
	* @throws
	 */
	@ApiOperation(value = "添加任务单元", notes = "添加任务单元")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "任务标识号", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "taskName", value = "任务名称", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "taskParentId", value = "父级任务标识号", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "projectId", value = "项目标识号", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "demandId", value = "需求标识号", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "priority", value = "优先级", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "assignUserId", value = "指派人标识号", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "beassignUserId", value = "负责人", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "assignTime", value = "任务指派时间", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "estimateStartTime", value = "任务预期开始时间", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "estimateEndTime", value = "任务预期结束时间", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "actualStartTime", value = "任务实际开始时间", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "actualEndTime", value = "任务实际结束时间", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "taskType", value = "任务类型", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "taskSpeed", value = "任务进度", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "status", value = "任务状态", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "abnormalStatus", value = "异常状态", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "estimateHour", value = "任务预计工时", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "consumeHour", value = "任务已消耗工时", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "remainHour", value = "任务剩余工时", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "taskMileage", value = "任务里程", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "warningHour", value = "预警工时", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "warningStatus", value = "预警状态", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "comment", value = "备注", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "filepath", value = "附件路径", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "filename", value = "附件名称", required = true, paramType = "form", dataType = "String") })
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public JSONObject addTask(@ApiIgnore Task task) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			//检查用户是否登录，需要去session中获取用户登录信息
			/*if(StringUtils.isEmpty(--CreateUserId--)){
                ApiResponse.jsonData(APIStatus.UNAUTHORIZED_401);
            }*/
			//对象检查是否为空
            if (task == null) {
                return ApiResponse.jsonData(APIStatus.ERROR_400, result.getResult());
            }
			result = taskPendingService.save(task);
			//判断是否成功
			if (result.isSuccess()) {
				return ApiResponse.jsonData(APIStatus.OK_200, result.getResult());
			}
			return ApiResponse.error();
		}catch (Exception e) {
			//异常错误
			return ApiResponse.jsonData(APIStatus.ERROR_500, e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: editUser 
	* @Description: TODO(修改Task对象接口方法) 
	* @param @param task
	* @param @return    设定文件 
	* @return JSONObject    返回类型 
	* @throws
	 */
	@ApiOperation(value = "修改任务单元", notes = "修改任务单元")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "任务标识号", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "taskName", value = "任务名称", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "taskParentId", value = "父级任务标识号", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "projectId", value = "项目标识号", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "demandId", value = "需求标识号", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "priority", value = "优先级", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "assignUserId", value = "指派人标识号", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "beassignUserId", value = "负责人", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "assignTime", value = "任务指派时间", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "estimateStartTime", value = "任务预期开始时间", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "estimateEndTime", value = "任务预期结束时间", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "actualStartTime", value = "任务实际开始时间", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "actualEndTime", value = "任务实际结束时间", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "taskType", value = "任务类型", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "taskSpeed", value = "任务进度", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "status", value = "任务状态", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "abnormalStatus", value = "异常状态", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "estimateHour", value = "任务预计工时", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "consumeHour", value = "任务已消耗工时", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "remainHour", value = "任务剩余工时", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "taskMileage", value = "任务里程", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "warningHour", value = "预警工时", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "warningStatus", value = "预警状态", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "comment", value = "备注", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "filepath", value = "附件路径", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "filename", value = "附件名称", required = true, paramType = "form", dataType = "String") })
	@RequestMapping(value = "/editTask", method = RequestMethod.POST)
	public JSONObject editUser(@ApiIgnore Task task) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			//检查用户是否登录，需要去session中获取用户登录信息
			/*if(StringUtils.isEmpty(--CreateUserId--)){
                ApiResponse.jsonData(APIStatus.UNAUTHORIZED_401);
            }*/
			//对象检查是否为空
            if (task == null) {
                return ApiResponse.jsonData(APIStatus.ERROR_400, result.getResult());
            }
			result = taskPendingService.saveOrUpdate(task);
			//判断是否成功
			if (result.isSuccess()) {
				return ApiResponse.jsonData(APIStatus.OK_200, result.getResult());
			}
			return ApiResponse.error();
		}catch (Exception e) {
			//异常错误
			return ApiResponse.jsonData(APIStatus.ERROR_500, e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: addUser 
	* @Description: TODO(添加或更新Task对象接口方法) 
	* @param @param task
	* @param @return    设定文件 
	* @return JSONObject    返回类型 
	* @throws
	 */
	@ApiOperation(value = "添加或更新任务单元", notes = "添加或更新任务单元")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "任务标识号", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "taskName", value = "任务名称", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "taskParentId", value = "父级任务标识号", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "projectId", value = "项目标识号", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "demandId", value = "需求标识号", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "priority", value = "优先级", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "assignUserId", value = "指派人标识号", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "beassignUserId", value = "负责人", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "assignTime", value = "任务指派时间", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "estimateStartTime", value = "任务预期开始时间", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "estimateEndTime", value = "任务预期结束时间", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "actualStartTime", value = "任务实际开始时间", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "actualEndTime", value = "任务实际结束时间", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "taskType", value = "任务类型", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "taskSpeed", value = "任务进度", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "status", value = "任务状态", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "abnormalStatus", value = "异常状态", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "estimateHour", value = "任务预计工时", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "consumeHour", value = "任务已消耗工时", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "remainHour", value = "任务剩余工时", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "taskMileage", value = "任务里程", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "warningHour", value = "预警工时", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "warningStatus", value = "预警状态", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "comment", value = "备注", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "filepath", value = "附件路径", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "filename", value = "附件名称", required = true, paramType = "form", dataType = "String") })
	@RequestMapping(value = "/addOrUpdateTask", method = RequestMethod.POST)
	public JSONObject addOrUpdateTask(@ApiIgnore Task task) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			//检查用户是否登录，需要去session中获取用户登录信息
			/*if(StringUtils.isEmpty(--CreateUserId--)){
                ApiResponse.jsonData(APIStatus.UNAUTHORIZED_401);
            }*/
			//对象检查是否为空
            if (task == null) {
                return ApiResponse.jsonData(APIStatus.ERROR_400, result.getResult());
            }
			result = taskPendingService.save(task);
			//判断是否成功
			if (result.isSuccess()) {
				return ApiResponse.jsonData(APIStatus.OK_200, result.getResult());
			}
			return ApiResponse.error();
		}catch (Exception e) {
			//异常错误
			return ApiResponse.jsonData(APIStatus.ERROR_500, e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: deleteTask 
	* @Description: TODO(根据taskId删除该任务，若删除该任务下的所有子任务请调用deleteTaskTreeById（）方法) 
	* @param @param userId
	* @param @return    设定文件 
	* @return JSONObject    返回类型 
	* @throws
	 */
	@ApiOperation(value = "删除单个任务单元", notes = "删除单个任务单元")
    @RequestMapping(value = "/deleteTask", method = RequestMethod.POST)
	public JSONObject deleteTask(
			@ApiParam(name = "taskId", value = "任务标识号", required = true) @RequestParam(required = true) String taskId){
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			//检查用户是否登录，需要去session中获取用户登录信息
			/*if(StringUtils.isEmpty(--CreateUserId--)){
                ApiResponse.jsonData(APIStatus.UNAUTHORIZED_401);
            }*/
			//对象检查是否为空
            if (StringUtils.isEmpty(taskId)) {
                return ApiResponse.jsonData(APIStatus.ERROR_400, result.getResult());
            }
            result = taskPendingService.delete(Long.valueOf(taskId));
            //判断是否成功
            if(result.isSuccess()){
            	return ApiResponse.jsonData(APIStatus.OK_200, result.getResult());
            }
            return ApiResponse.error();
		}catch (Exception e) {
			//异常错误
			return ApiResponse.jsonData(APIStatus.ERROR_500, e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: queryAllTaskList 
	* @Description: TODO(查询整个任务列表) 
	* @param @return    设定文件 
	* @return JSONObject    返回类型 
	* @throws
	 */
	@ApiOperation(value = "查询整个任务列表", notes = "查询整个任务列表")
	@RequestMapping(value = "/queryAllTaskList", method = RequestMethod.GET)
	public JSONObject queryAllTaskList(){
		ExecuteResult<List<Task>> result = new ExecuteResult<List<Task>>();
		try {
			//检查用户是否登录，需要去session中获取用户登录信息
			/*if(StringUtils.isEmpty(--CreateUserId--)){
                ApiResponse.jsonData(APIStatus.UNAUTHORIZED_401);
            }*/
			result = taskPendingService.queryAllTaskList();
			//判断是否成功
			if(result.isSuccess()){
				return ApiResponse.jsonData(APIStatus.OK_200,result.getResult());
			}
			return ApiResponse.error();
		}catch (Exception e) {
			//异常
			return ApiResponse.jsonData(APIStatus.ERROR_500,e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: queryTaskListNodeByParentId 
	* @Description: TODO(查询taskId下的所有一级子节点) 
	* @param @param taskId
	* @param @return    设定文件 
	* @return JSONObject    返回类型 
	* @throws
	 */
	@ApiOperation(value = "查询taskId下的所有一级子节点", notes = "查询taskId下的所有一级子节点")
	@RequestMapping(value = "/queryAllTaskList", method = RequestMethod.POST)
	public JSONObject queryTaskListNodeByParentId(
			@ApiParam(name = "taskId", value = "任务标识号", required = true) @RequestParam(required = true) String taskId){
		ExecuteResult<List<Task>> result = new ExecuteResult<List<Task>>();
		try {
			//检查用户是否登录，需要去session中获取用户登录信息
			/*if(StringUtils.isEmpty(--CreateUserId--)){
                ApiResponse.jsonData(APIStatus.UNAUTHORIZED_401);
            }*/
			result = taskPendingService.queryTaskListNodeByParentId(Long.valueOf(taskId));
			//判断是否成功
			if(result.isSuccess()){
				return ApiResponse.jsonData(APIStatus.OK_200,result.getResult());
			}
			return ApiResponse.error();
		}catch (Exception e) {
			//异常
			return ApiResponse.jsonData(APIStatus.ERROR_500,e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: deleteTaskTreeById 
	* @Description: TODO(删除taskId下的所有子节点) 
	* @param @param taskId
	* @param @return    设定文件 
	* @return JSONObject    返回类型 
	* @throws
	 */
	@ApiOperation(value = "删除taskId下的所有子节点", notes = "删除taskId下的所有子节点")
	@RequestMapping(value = "/deleteTaskTreeById", method = RequestMethod.POST)
	public JSONObject deleteTaskTreeById(
			@ApiParam(name = "taskId", value = "任务标识号", required = true) @RequestParam(required = true) String taskId){
		ExecuteResult<List<Task>> result = new ExecuteResult<List<Task>>();
		try {
			//检查用户是否登录，需要去session中获取用户登录信息
			/*if(StringUtils.isEmpty(--CreateUserId--)){
                ApiResponse.jsonData(APIStatus.UNAUTHORIZED_401);
            }*/
			result = taskPendingService.deleteTaskTreeById(Long.valueOf(taskId));
			//判断是否成功
			if(result.isSuccess()){
				return ApiResponse.jsonData(APIStatus.OK_200,result.getResult());
			}
			return ApiResponse.error();
		}catch (Exception e) {
			//异常
			return ApiResponse.jsonData(APIStatus.ERROR_500,e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: queryTaskTreeByTaskId 
	* @Description: TODO(查询taskId下的所有子节点) 
	* @param @param taskId
	* @param @return    设定文件 
	* @return JSONObject    返回类型 
	* @throws
	 */
	@ApiOperation(value = "查询taskId下的所有子节点", notes = "查询taskId下的所有子节点")
	@RequestMapping(value = "/queryTaskTreeByTaskId", method = RequestMethod.POST)
	public JSONObject queryTaskTreeByTaskId(
			@ApiParam(name = "taskId", value = "任务标识号", required = true) @RequestParam(required = true) String taskId){
		ExecuteResult<Task> result = new ExecuteResult<Task>();
		try {
			//检查用户是否登录，需要去session中获取用户登录信息
			/*if(StringUtils.isEmpty(--CreateUserId--)){
                ApiResponse.jsonData(APIStatus.UNAUTHORIZED_401);
            }*/
			result = taskPendingService.queryTaskTreeByTaskId(Long.valueOf(taskId));
			//判断是否成功
			if(result.isSuccess()){
				return ApiResponse.jsonData(APIStatus.OK_200,result.getResult());
			}
			return ApiResponse.error();
		}catch (Exception e) {
			//异常
			return ApiResponse.jsonData(APIStatus.ERROR_500,e.getMessage());
		}
	}
}
