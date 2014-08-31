package cn.adfi.radius.repo;

import org.springframework.data.repository.CrudRepository;

import cn.adfi.radius.model.Permission;

public interface PermissionRepository extends CrudRepository<Permission, Long> {

}
