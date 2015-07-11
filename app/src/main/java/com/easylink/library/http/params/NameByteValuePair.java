package com.easylink.library.http.params;

import com.easylink.library.util.TextUtil;

/**
 * 保存字节数据的键值对 对象
 * @author yhb
 *
 */
public class NameByteValuePair {

	private String name = TextUtil.TEXT_EMPTY;
	private byte[] data;

	public NameByteValuePair() {
		
	}

	public NameByteValuePair(String name, byte[] data) {
		
		setName(name);
		setData(data);
	}

	public String getName() {
		
		return name;
	}

	public void setName(String name) {
		
		this.name = TextUtil.filterNull(name);
	}

	public byte[] getData() {
		
		return data;
	}

	public void setData(byte[] data) {
		
		this.data = data;
	}
}
