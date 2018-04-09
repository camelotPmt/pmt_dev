package com.camelot.pmt.platform.role.mapper;

import com.camelot.pmt.platform.role.model.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface RoleMapper {

    /**
     * 查询角色列表集合
     * @return
     */
    List<Role> queryRoleArray();

    /**
     * 新增列表
     * @param role
     */
    void addRole(Role role);

    /**
     * 修改角色
     * @param role
     */
    void editRole(Role role);

    /**
     * 删除角色
     * @param Role
     */
    void deleteRole(Role role);

    /**
     * 删除角色权限
     * @param Role
     */
    void deleteRoleMenu(Role role);

    /**
     * 根据roleId查询角色
     * @param role
     * @return
     */
    List<Role> queryRoleByroleId(String role);
}
