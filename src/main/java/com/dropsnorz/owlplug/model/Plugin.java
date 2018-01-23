package com.dropsnorz.owlplug.model;

public class Plugin {
	
	protected String name;
	protected String path;
	protected String bundleId;
	protected String version;
	
	
	public Plugin(){
		
	}
	
	public Plugin(String name, String path){
		this.name = name;
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String getBundleId() {
		return bundleId;
	}

	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	
	

	@Override
	public String toString() {
		return name;
	}
	
	
	
	

}
