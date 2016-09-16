 /*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

 /**
 * Project  : WebQQCore
 * Package  : iqq.im.vo
 * File     : QQBuddy.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-7-31
 * License  : Apache License 2.0 
 */
package iqq.im.bean;


/**
 *
 * QQ好友，出现在好友列表的用户
 *
 * @author solosky
 */
public class QQBuddy extends QQUser{
	private static final long serialVersionUID = 1L;
	
	private String markname; // 备注
	private QQCategory category;

	/**
	 * <p>Getter for the field <code>category</code>.</p>
	 *
	 * @return a {@link iqq.im.bean.QQCategory} object.
	 */
	public QQCategory getCategory() {
		return category;
	}

	/**
	 * <p>Setter for the field <code>category</code>.</p>
	 *
	 * @param category a {@link iqq.im.bean.QQCategory} object.
	 */
	public void setCategory(QQCategory category) {
		this.category = category;
	}
	
	/**
	 * <p>Getter for the field <code>markname</code>.</p>
	 *
	 * @return a {@link String} object.
	 */
	public String getMarkname() {
		return markname;
	}

	/**
	 * <p>Setter for the field <code>markname</code>.</p>
	 *
	 * @param markname a {@link String} object.
	 */
	public void setMarkname(String markname) {
		this.markname = markname;
	}
}
