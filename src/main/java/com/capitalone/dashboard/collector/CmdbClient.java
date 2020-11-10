package com.capitalone.dashboard.collector;

import java.util.List;

import com.capitalone.dashboard.misc.HygieiaException;
import com.capitalone.dashboard.model.Cmdb;

/**
 * Client for fetching configuration item data from Company
 */
public interface CmdbClient {

    /**
     * Fetch all of the Apps

     * @return all Apps in CMDB
     */
	
	List<Cmdb> getApps() throws HygieiaException;

}
