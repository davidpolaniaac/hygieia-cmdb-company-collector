package com.capitalone.dashboard.collector;

import com.capitalone.dashboard.model.Collector;
import com.capitalone.dashboard.model.CollectorType;

/**
 * Extension of Collector
 */

public class CmdbCollector extends Collector {

	public static CmdbCollector prototype() {
		return prototype("cmdb");
	}

	public static CmdbCollector prototype(String name) {
		CmdbCollector protoType = new CmdbCollector();
		protoType.setName(name);
		protoType.setCollectorType(CollectorType.CMDB);
		protoType.setOnline(true);
		protoType.setEnabled(true);
		return protoType;
	}
}
