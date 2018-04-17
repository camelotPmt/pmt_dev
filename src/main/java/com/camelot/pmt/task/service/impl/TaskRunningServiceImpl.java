package com.camelot.pmt.task.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.camelot.pmt.platform.utils.ExecuteResult;
import com.camelot.pmt.task.mapper.TaskLogMapper;
import com.camelot.pmt.task.mapper.TaskMapper;
import com.camelot.pmt.task.model.Task;
import com.camelot.pmt.task.model.TaskLog;
import com.camelot.pmt.task.service.TaskRunningService;
import com.camelot.pmt.task.utils.Constant;
import com.camelot.pmt.task.utils.DateUtils;
import com.camelot.pmt.task.utils.RRException;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author muyuanpei
 * @date 2018/4/10    15:20
 */

@Service
public class TaskRunningServiceImpl implements TaskRunningService{

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskLogMapper taskLogMapper;


    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRunningServiceImpl.class);

    /**
     * <p>
     * Description:[根据用户id获取正在进行的任务列表]
     * <p>
     *
     * @return ExecuteResult<PageInfo<Map<String, Object>>>
     */
    @Override
    public ExecuteResult<PageInfo<Map<String, Object>>> queryoverdueTaskRunning(int page , int rows, String id) {
        ExecuteResult<PageInfo<Map<String, Object>>> result = new ExecuteResult<PageInfo<Map<String, Object>>>();
        //利用PageHelper进行分页
        PageHelper.startPage(page, rows);
        //根据用户id查询全部的正在进行的任务
        List<Map<String, Object>> list = taskMapper.listTaskRunning(id);
        //分页之后的结果集
        PageInfo<Map<String, Object>> clist = new PageInfo<Map<String, Object>>(list);
        //返回结果集
        result.setResult(clist);
        return result;
    }

    /**
     *
     * @Title: runningtoclose
     * @Description: TODO(我的任务转为关闭)
     * @param @param taskId
     * @param @return    设定文件
     * @return JSONObject    返回类型
     * @throws
     */
    @Override
    public ExecuteResult<String> runningtoclose(Long id) {
        ExecuteResult<String> result=new ExecuteResult<>();
        try{
            //遍历此任务下是否有引用--->查询所有任务父id为id的记录
            List<Task> taskList = taskMapper.selectByPId(id);
            System.out.println(id);
            List<Long> list = new ArrayList<Long>();
            if(taskList.size()>0){
                for (Task task : taskList) {
                    List<Task> tempList = taskMapper.selectByPId(task.getId());
                    if(tempList.size()>0){
                        System.out.println(tempList.size());
                        for (Task task2 : tempList) {
                            List<Task> tempList2 = taskMapper.selectByPId(task2.getId());
                            if(tempList2.size()>0){
                                for (Task task3 : tempList2) {
                                    list.add(task3.getId());
                                }
                            }else{
                                //说明没有子任务
                                list.add(task2.getId());
                            }
                        }
                    }else{
                        //说明没有子任务
                        list.add(task.getId());
                    }
                }
                if(list.size() == taskList.size()){
                    list.add(id);
                }
            }else{
                //说明没有子任务
                list.add(id);
            }

            taskMapper.runningtoclose(list);
            result.setResult("关闭任务成功");
        }catch (Exception e){
            //logger.error("-------需求业务根据id删除------"+e.getMessage());
            throw new RuntimeException(e);

        }
        return result;
    }


    /**
     * <p>
     * Description:[根据id获取单个任务明细]
     * <p>
     *
     * @return ExecuteResult<Task>
     */
    @Override
    public ExecuteResult<Task> queryTaskById(Long id) {
        ExecuteResult<Task> result = new ExecuteResult<Task>();
        try {
            if (!id.equals("") && !id.equals("0")) {
                Task queryResult = taskMapper.selectTaskById(id);
                result.setResult(queryResult);
                return result;
            }
            result.addErrorMessage("查询失败！");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * <p>
     * Description:[添加历史记录]
     * <p>
     *
     * @return ExecuteResult<long>
     */
    @Override
    public ExecuteResult<Long> saveHistoryLog(TaskLog taskLog) {
        ExecuteResult<Long> result = new ExecuteResult<Long>();
        try {
            Long updateStatusResult = taskMapper.saveHistoryLog(taskLog);
            result.setResult(updateStatusResult);
            return result;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }




    /**
     *
     * @Title: updateTaskPendingToDelay
     * @Description: TODO(我的已完成任务转为正在进行,会将该节点及节点下的所有子节点变为正在进行(不包括关闭的任务))
     * @param @param taskId taskType
     * @param @return    设定文件
     * @return JSONObject    返回类型
     * @throws
     */
    @Override
    public ExecuteResult<String> updateTaskAlreadyToRunning(Long id,String taskType,String delayDescribe,Date estimateStartTime) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        try{
            if(id==null || StringUtils.isEmpty(delayDescribe) || estimateStartTime == null){
                result.addErrorMessage("传入的参数有误!");
                return result;
            }
            //判断状态是否为已完成，如果是已完成更新为正在进行
            if(Constant.TaskType.ALREADY.getValue().equals(taskType)){
                //格式化日期格式为yyyy-mm-dd HH:mm:ss,根据id更新待办任务状态为延期
                //sql:update task set t.status = #{taskType,jdbcType=VARCHAR},t.delay_describe = #{delayDescribe,jdbcType=VARCHAR},t.estimate_start_time = #{estimateStartTime,jdbcType=TIMESTAMP} where t.id = #{id,jdbcType=BIGINT}
                if(!Constant.TaskType.CLOSE.getValue().equals(taskType)){
                    taskMapper.updateTaskAlreadyToRunning(id,taskType,delayDescribe, DateUtils.format(estimateStartTime,DateUtils.DATE_TIME_PATTERN));
                }
                //查询taskId下的所有子节点
                //select * from task t where <if test="id != null" >t.task_parent_id = #{id}</if> <if test="taskType != null" > and t.task_type = #{taskType,jdbcType=BIGINT} </if>
                List<Task> childTaskNodes = taskMapper.queryTaskListNodeByParentId(id,null);
                //遍历子节点
                if(childTaskNodes!=null && childTaskNodes.size()>0){
                    for(Task child : childTaskNodes){
                        //递归
                        //sql需要修改
                        //<if test="taskType != null" > and t.task_type = #{taskType,jdbcType=BIGINT} </if>
                        //<if test="beassignUserId != null" > and t.beassign_user_id = #{beassignUserId,jdbcType=BIGINT} </if>
                        //非关闭需要改为重做
                        updateTaskAlreadyToRunning(child.getId(),taskType,delayDescribe,estimateStartTime);
                    }
                }
            }
            result.setResult("重做任务成功！");
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RRException(e.getMessage(),e);
        }
        return result;
    }

}
