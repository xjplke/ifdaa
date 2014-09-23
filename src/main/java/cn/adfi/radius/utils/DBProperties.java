package cn.adfi.radius.utils;


import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.adfi.radius.model.DBPropertiesModel;
import cn.adfi.radius.repo.DBPropertiesRepository;


public class DBProperties extends Properties {
	
	public DBProperties(DBPropertiesRepository dbpRepo){
		super();
		this.DBPRepo = dbpRepo;
	}
	
	private DBPropertiesRepository DBPRepo;
	
	
	public synchronized void loadFromDB(){
		this.clear();
		Iterable<DBPropertiesModel> lst = DBPRepo.findAll();
		for(DBPropertiesModel dbp:lst){
			put(dbp.getKey(),dbp.getValue());
		}
	}
	
	public synchronized void storToDB(){
		for(java.util.Map.Entry<Object, Object> e:this.entrySet()){
			DBPropertiesModel dbp = DBPRepo.findOne((String)e.getKey());
			if(dbp == null){
				dbp = new DBPropertiesModel((String)e.getKey(),(String)e.getValue());
			}else{
				dbp.setValue((String)e.getValue());
			}
			DBPRepo.save(dbp);
		}
	}
}
