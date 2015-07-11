package com.easylink.library.http.params;

import com.easylink.library.util.TextUtil;

/**
 * 保存上传文件的键值对 对象
 * @author yhb
 */
public class NameFileValuePair {

	private String name = TextUtil.TEXT_EMPTY;
	private String uri = TextUtil.TEXT_EMPTY;

	public NameFileValuePair(String name, String uri) {
		
		setName(name);
		setUri(uri);
	}

	public String getName() {
		
		return name;
	}

	public void setName(String name) {
		
		this.name = TextUtil.filterNull(name);;
	}

	public String getData() {
		
		return this.uri;
	}

	public void setUri(String uri) {
		
		this.uri = TextUtil.filterNull(uri);
	}
}
