package com.project.tutor.service;

import com.project.tutor.dto.RoleDTO;
import com.project.tutor.many.dto.RoleManyDTO;
import com.project.tutor.request.RoleRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    public List<RoleManyDTO> getAllRole ();
    public RoleRequest addRole (RoleRequest request);
    public boolean deleteRole (int roleId);
    public boolean updateRole (int roleId , RoleRequest request);
    public RoleManyDTO getRoleById (int roleId);
}
