package com.capitalone.dashboard.model;

public class Item {

	private String configurationItem;
	private String configurationItemSubType;
	
	public Item(String configurationItem, String configurationItemSubType) {
		this.configurationItem = configurationItem;
		this.configurationItemSubType = configurationItemSubType;
	}

	public String getConfigurationItem() {
		return configurationItem;
	}

	public void setConfigurationItem(String configurationItem) {
		this.configurationItem = configurationItem;
	}

	public String getConfigurationItemSubType() {
		return configurationItemSubType;
	}

	public void setConfigurationItemSubType(String configurationItemSubType) {
		this.configurationItemSubType = configurationItemSubType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((configurationItem == null) ? 0 : configurationItem.hashCode());
		result = prime * result + ((configurationItemSubType == null) ? 0 : configurationItemSubType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (configurationItem == null) {
			if (other.configurationItem != null)
				return false;
		} else if (!configurationItem.equals(other.configurationItem))
			return false;
		if (configurationItemSubType == null) {
			if (other.configurationItemSubType != null)
				return false;
		} else if (!configurationItemSubType.equals(other.configurationItemSubType))
			return false;
		return true;
	}

}
