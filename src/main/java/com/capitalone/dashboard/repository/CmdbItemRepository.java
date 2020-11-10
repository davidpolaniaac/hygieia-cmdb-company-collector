package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.Cmdb;

public interface CmdbItemRepository extends CmdbRepository {

	Cmdb findByConfigurationItemAndConfigurationItemSubTypeAndConfigurationItemType(String configurationItem, String configurationItemSubType, String ConfigurationItemType);
	
	Cmdb findByConfigurationItemAndConfigurationItemSubType(String configurationItem, String configurationItemSubType);

}
