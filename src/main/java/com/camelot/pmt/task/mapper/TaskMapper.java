package com.camelot.pmt.task.mapper;


import com.camelot.pmt.platform.user.model.UserModel;
import com.camelot.pmt.platform.utils.ExecuteResult;
import com.camelot.pmt.platform.utils.Pager;
import com.camelot.pmt.task.model.Task;

import java.util.List;
import java.util.Map;

import com.camelot.pmt.task.model.TaskManager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


public interface TaskMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);
	
	/**
     * @author: zlh
     * @param:
     * @description: 查询所有任务列表
     * @date: 16:54 2018/4/9
     */
    List<TaskManager> queryAllTask();

    /**
	 * @author: zlh
     * @param task 模糊查询的条件
     * @description: 根据条件查询任务
     * @return
     */
    List<Task> queryTaskByTask(Task task);
    /**
     * 查询延期任务个数
    * @Title: queryCount
    * @Description: TODO
    * @param @return
    * @return Long 
    * @throws
     */
	Long queryCount();
	/**
	 * 查询延期任务列表+分页+排序+时间正序+优先级倒序
	* @Title: queryOverdueTask
	* @Description: TODO
	* @param @param page
	* @param @return
	* @return List<Task> 
	* @throws
	 */
	 List<Task> queryOverdueTask(@Param(value = "page") Pager page);

	
	/**
	 * 
	* @Title: queryTaskTreeByTaskId 
	* @Description: TODO(查询taskId下的一级子节点) 
	* @param @param taskId
	* @param @return    设定文件 
	* @return List<Task>    返回类型 
	* @throws
	 */
	List<Task> queryTaskListNodeByParentId(Long taskId);
	
	/**
	 * 
	* @Title: queryAllTaskList 
	* @Description: TODO(查询整个任务表) 
	* @param @return    设定文件 
	* @return List<Task>    返回类型 
	* @throws
	 */
	List<Task> queryAllTaskList();

	/**
	 * 查询正在进行的任务
	 * myp
	 * */
	List<Map<String,Object>> listTaskRunning(@Param(value = "page") Pager page);

	/**
	 * 查询正在进行任务个数 @Title: queryCount @Description: TODO @param @return @return
	 * Long @throws
	 * myp
	 */
	Long queryRunningCount();

}