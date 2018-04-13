package com.camelot.pmt.task.service.impl;

import com.camelot.pmt.platform.common.APIStatus;
import com.camelot.pmt.platform.common.ApiResponse;
import com.camelot.pmt.platform.utils.ExecuteResult;
import com.camelot.pmt.task.mapper.TaskMapper;
import com.camelot.pmt.task.model.Task;
import com.camelot.pmt.task.service.TaskPendingService;
import com.camelot.pmt.task.utils.RRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 
* @ClassName: TaskPendingServiceImpl
* @Description: TODO(任务-我的待办业务类)
* @author gxl
* @date 2018年4月9日 下午5:31:16
*
 */
@Service
public class TaskPendingServiceImpl implements TaskPendingService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskPendingServiceImpl.class);
	
	@Autowired
    private TaskMapper taskMapper;
	
	/**
	 * 
	* @Title: save 
	* @Description: TODO(保存任务) 
	* @param @param task
	* @param @return    设定文件 
	* @return ExecuteResult<String>    返回类型 
	* @throws
	 */
	@Override
	public ExecuteResult<String> save(Task task) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try{
			//对象检查是否为空
			if(task==null){
				result.addErrorMessage("传入的用户实体有误!");
				return result;
			}
			taskMapper.insert(task);
			result.setResult("添加任务成功！");
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
            throw new RRException(e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * 
	* @Title: update 
	* @Description: TODO(修改任务) 
	* @param @param task
	* @param @return    设定文件 
	* @return ExecuteResult<String>    返回类型 
	* @throws
	 */
	@Override
	public ExecuteResult<String> update(Task task) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		if(task==null||task.getId() == null){
			result.addErrorMessage("传入的任务实体或者任务Id有误!");
			return result;
		}
		taskMapper.updateByPrimaryKeySelective(task);
		result.setResult("修改任务成功！");
		return result;
	}
	
	/**
	 * 
	* @Title: saveOrUpdate 
	* @Description: TODO(更新或保存Task对象方法) 
	* @param @param task    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Override
	public ExecuteResult<String> saveOrUpdate(Task task){
		ExecuteResult<String> result = new ExecuteResult<String>();
		try{
			//对象检查是否为空
			if(task==null){
				result.addErrorMessage("任务对象不能为空!");
				return result;
			}
			//判断id是否为null
			if(task.getId()==null){
				taskMapper.insert(task);
				result.setResult("添加任务成功！");
			}else{
				Task taskSel= taskMapper.selectByPrimaryKey(task.getId());
				//判断此对象库中是否存在
				if(taskSel!=null){
					taskMapper.updateByPrimaryKey(task);
					result.setResult("修改任务成功！");
				}else{
					taskMapper.insert(task);
					result.setResult("添加任务成功！");
				}
			}
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
            throw new RRException(e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * 
	* @Title: delete 
	* @Description: TODO(根据taskId删除该任务，若删除该任务下的所有子任务请调用deleteTaskTreeById（）方法) 
	* @param @param taskId
	* @param @return    设定文件 
	* @return ExecuteResult<String>    返回类型 
	* @throws
	 */
	@Override
	public ExecuteResult<String> delete(Long taskId){
		ExecuteResult<String> result = new ExecuteResult<String>();
		try{
			if(taskId==null){
				result.addErrorMessage("删除任务时，taskId不能为空!");
				return result;
			}
			//查询所有的Task任务列表
			taskMapper.deleteByPrimaryKey(taskId);
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
            throw new RRException(e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * 
	* @Title: queryAllTaskList 
	* @Description: TODO(查询所有的Task任务列表) 
	* @param @return    设定文件 
	* @return ExecuteResult<List<Task>>    返回类型 
	* @throws
	 */
	@Override
	public ExecuteResult<List<Task>> queryAllTaskList(String taskType,Long beassignUserId){
		ExecuteResult<List<Task>> result = new ExecuteResult<List<Task>>();
		try{
			//查询所有的Task任务列表
			List<Task> allTaskList = taskMapper.queryAllTaskList(taskType,beassignUserId);
			result.setResult(allTaskList);
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
            throw new RRException(e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * 
	* @Title: queryTaskListNodeByParentId 
	* @Description: TODO(查询taskId下的一级子节点) 
	* @param @param taskId
	* @param @return    设定文件 
	* @return ExecuteResult<List<Task>>    返回类型 
	* @throws
	 */
	public ExecuteResult<List<Task>> queryTaskListNodeByParentId(Long taskId,String taskType,Long beassignUserId){
		ExecuteResult<List<Task>> result = new ExecuteResult<List<Task>>();
		try{
			//对象检查是否为空
			if(taskId==null){
				result.addErrorMessage("查询一级子任务时，taskId不能为空!");
				return result;
			}
			List<Task> taskList = taskMapper.queryTaskListNodeByParentId(taskId,taskType,beassignUserId);
			result.setResult(taskList);
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage());
            throw new RRException(e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * 
	* @Title: delete 
	* @Description: TODO(根据Id删除该任务及以下的所有node节点，调用递归方法，taskId不能为空) 
	* @param @param task
	* @param @return    设定文件 
	* @return ExecuteResult<String>    返回类型 
	* @throws
	 */
	@Override
	public ExecuteResult<List<Task>> deleteTaskTreeById(Long id,String taskType,Long beassignUserId){

		ExecuteResult<List<Task>> result = new ExecuteResult<List<Task>>();
		
		try{
			//判断删除的任务Id是否存在
			if(id==null){
				result.addErrorMessage("删除任务时，taskId不能为空!");
				return result;
			}
			//根据taskId删除当前对象
			//taskMapper.deleteByPrimaryKey(taskId);	
			//递归查询taskId下的所有子节点
			List<Task> childTaskNodes = taskMapper.queryTaskListNodeByParentId(id,taskType,beassignUserId); 
			//遍历子节点
			if(childTaskNodes!=null && childTaskNodes.size()>0){
				for(Task child : childTaskNodes){
					//递归
					result = deleteTaskTreeById(child.getId(),taskType,beassignUserId);
					//循环遍历list进行结点删除
					for(Task task : result.getResult()){
						//taskMapper.deleteByPrimaryKey(task.getId());
					}
				}
			}
			result.setResult(childTaskNodes);
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
            throw new RRException(e.getMessage(),e);
		}
		return result;
	
	}
	
	/**
	 * 如果taskId不为空，查询当前节点下的所有子节点，如果taskId为空，查询整张task表
	* @Title: recursiveTree 
	* @Description: TODO(通过递归获取Task任务树) 
	* @param @param taskId
	* @param @return    设定文件 
	* @return Task    返回类型 
	* @throws
	 */
	public ExecuteResult<Task> queryTaskTreeByTaskId(Long id,String taskType,Long beassignUserId){
		ExecuteResult<Task> result = new ExecuteResult<Task>();
		try{
			Task taskNode = new Task();
			//判断删除的任务Id是否存在
			if(id!=null){
				//根据taskId获取节点对象
				taskNode = taskMapper.selectByPrimaryKey(id);	
				//查询taskId下的所有子节点
				List<Task> childTaskNodes = taskMapper.queryTaskListNodeByParentId(id,taskType,beassignUserId); 
				//遍历子节点
				if(childTaskNodes!=null && childTaskNodes.size()>0){
					for(Task child : childTaskNodes){
						//递归
						//sql需要修改
						//<if test="taskType != null" > and t.task_type = #{taskType,jdbcType=BIGINT} </if>
						//<if test="beassignUserId != null" > and t.beassign_user_id = #{beassignUserId,jdbcType=BIGINT} </if>
						result = queryTaskTreeByTaskId(child.getId(),taskType,beassignUserId);
						taskNode.getChildren().add(result.getResult());
					}
				}
			}else{
				//如果taskId为空，返回整张表
				//sql需要修改
				//<if test="taskType != null" > and t.task_type = #{taskType,jdbcType=BIGINT} </if>
				//<if test="beassignUserId != null" > and t.beassign_user_id = #{beassignUserId,jdbcType=BIGINT} </if>
				List<Task> allTaskList = taskMapper.queryAllTaskList(taskType,beassignUserId);
				taskNode.setChildren(allTaskList);
			}
			result.setResult(taskNode);
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
            throw new RRException(e.getMessage(),e);
		}
		return result;
	};
	
	/**
	 * 
	* @Title: queryTopTaskNameList 
	* @Description: TODO(查询我的顶级待办任务) 
	* @param @return    设定文件 
	* @return JSONObject    返回类型 
	* @throws
	 */
	public ExecuteResult<List<Task>> queryTopTaskNameList(String taskType,Long beassignUserId){
		ExecuteResult<List<Task>> result = new ExecuteResult<List<Task>>();
		try{
			//根据当前用户Id和任务类型，查询我的顶级待办任务
			//select t.id,t.task_parent_id,t.task_name from task t where 1=1 
			//<if test="taskType != null" > and t.task_type = #{taskType,jdbcType=BIGINT} </if>
			//<if test="beassignUserId != null" > and t.beassign_user_id = #{beassignUserId,jdbcType=BIGINT} </if>
			List<Task> allTaskList = taskMapper.queryTopTaskNameList(taskType,beassignUserId);
			result.setResult(allTaskList);
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
            throw new RRException(e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * 
	* @Title: updateTaskPendingToRuning 
	* @Description: TODO(我的待办任务转为正在进行) 
	* @param @param taskId
	* @param @return    设定文件 
	* @return JSONObject    返回类型 
	* @throws
	 */
	@Override
	public ExecuteResult<String> updateTaskPendingToRuning(Long id,String taskType) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try{
			if(id==null){
				result.addErrorMessage("传入的任务Id有误!");
				return result;
			}
			//sql:update task set t.status = #{taskType,jdbcType=VARCHAR} where t.id = #{id,jdbcType=BIGINT}
			taskMapper.updateTaskPendingToRuning(id,taskType);
			result.setResult("修改任务状态成功！");
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage());
            throw new RRException(e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * 
	* @Title: updateTaskToAssign 
	* @Description: TODO(更新指派人和被指派人标识号) 
	* @param @param assignUserId
	* @param @param beassignUserId
	* @param @return    设定文件 
	* @return ExecuteResult<String>    返回类型 
	* @throws
	 */
	@Override
	public ExecuteResult<String> updateTaskToAssign(Long id,Long assignUserId,Long beassignUserId){
		ExecuteResult<String> result = new ExecuteResult<String>();
		try{
			//检查是否为空
	        if (id==null || assignUserId==null || beassignUserId==null) {
	        	result.addErrorMessage("传入的taskd或assignUserId或beassignUserId有误!");
				return result;
	        }
			//sql:update task set t.assign_user_id = #{assignUserId,jdbcType=BIGINT},t.beassign_user_id= #{beassignUserId,jdbcType=BIGINT} where t.id = #{id,jdbcType=BIGINT}
			taskMapper.updateTaskToAssign(id,assignUserId,beassignUserId);
			result.setResult("修改任务状态成功！");
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
            throw new RRException(e.getMessage(),e);
		}
		return result;
	}
}
