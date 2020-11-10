package com.capitalone.dashboard.model;

public class Document{

	private String id;
	private String userName;
	private String date;
	private String collection;
	private String name;
	private int __etag;
	
	public int get__etag() {
		return __etag;
	}

	public void set__etag(int __etag) {
		this.__etag = __etag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Document [id=" + id + ", userName=" + userName + ", date=" + date + ", collection=" + collection
				+ ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Document other = (Document) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}