package cn.adfi.radius.repo;

import org.springframework.data.repository.CrudRepository;

import cn.adfi.radius.model.DBPropertiesModel;


public interface DBPropertiesRepository extends CrudRepository<DBPropertiesModel, String> {

}
