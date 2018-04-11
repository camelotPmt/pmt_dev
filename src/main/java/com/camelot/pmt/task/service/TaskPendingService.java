package com.camelot.pmt.task.service;

import java.util.List;

import com.camelot.pmt.platform.utils.ExecuteResult;
import com.camelot.pmt.task.model.Task;

/**
 * 
* @ClassName: TaskPendingService
* @Description: TODO(任务-我的待办service)
* @author gxl
* @date 2018年4月9日 下午5:30:05
*
 */
public interface TaskPendingService {
	/**
	 * 
	* @Title: save 
	* @Description: TODO(保存任务) 
	* @param @param task
	* @param @return    设定文件 
	* @return ExecuteResult<String>    返回类型 
	* @throws
	 */
	ExecuteResult<String> save(Task task);

	/**
	 * 
	* @Title: saveOrUpdate 
	* @Description: TODO(更新或保存Task对象方法) 
	* @param @param task    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	ExecuteResult<String> saveOrUpdate(Task task);
	
	/**
	 * 
	* @Title: delete 
	* @Description: TODO(根据taskId删除该任务，若删除该任务下的所有子任务请调用deleteTaskTreeById（）方法) 
	* @param @param taskId
	* @param @return    设定文件 
	* @return ExecuteResult<String>    返回类型 
	* @throws
	 */
	ExecuteResult<String> delete(Long taskId);
	
	/**
	 * 
	* @Title: update 
	* @Description: TODO(修改任务) 
	* @param @param task
	* @param @return    设定文件 
	* @return ExecuteResult<String>    返回类型 
	* @throws
	 */
	ExecuteResult<String> update(Task task);
	
	/**
	 * 
	* @Title: queryAllTaskList 
	* @Description: TODO(查询所有的Task任务列表) 
	* @param @return    设定文件 
	* @return ExecuteResult<List<Task>>    返回类型 
	* @throws
	 */
	ExecuteResult<List<Task>> queryAllTaskList();
	
	/**
	 * 
	* @Title: queryTaskListNodeByParentId 
	* @Description: TODO(查询taskId下的一级子节点) 
	* @param @param taskId
	* @param @return    设定文件 
	* @return ExecuteResult<List<Task>>    返回类型 
	* @throws
	 */
	public ExecuteResult<List<Task>> queryTaskListNodeByParentId(Long taskId);
	
	/**
	 * 
	* @Title: delete 
	* @Description: TODO(根据Id删除该任务及以下的所有node节点，调用递归方法) 
	* @param @param task
	* @param @return    设定文件 
	* @return ExecuteResult<String>    返回类型 
	* @throws
	 */
	ExecuteResult<List<Task>> deleteTaskTreeById(Long taskId);
	
	/**
	 * 
	* @Title: queryTaskTreeByTaskId 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param taskId
	* @param @return    设定文件 
	* @return ExecuteResult<Task>    返回类型 
	* @throws
	 */
	ExecuteResult<Task> queryTaskTreeByTaskId(Long taskId);
	
}
