package org.common.util;

import java.net.UnknownHostException;

import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;

public class MongoDbManager {
	private String host;
	
	private int port;

	private Mongo mongo;
	
	private String userPicDb;

	public MongoDbManager(String host,int port){
		try {
			mongo = new Mongo(host,port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public String getUserPicDb(){
		return userPicDb;
	}
	
	public void setIp(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setUserPicDb(String userPicDb) {
		this.userPicDb = userPicDb;
	}
	
	public void close(){
		if(mongo!=null){
			mongo.close();
		}
	}
	
	public GridFS getUserPicDB(){
		return new GridFS(mongo.getDB(userPicDb));
	}
}
