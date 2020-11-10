package com.capitalone.dashboard.collector;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import com.capitalone.dashboard.misc.HygieiaException;
import com.capitalone.dashboard.model.Cmdb;
import com.capitalone.dashboard.model.Item;
import com.capitalone.dashboard.repository.BaseCollectorRepository;
import com.capitalone.dashboard.repository.CmdbItemRepository;
import com.capitalone.dashboard.repository.CollectorCmdbRepository;
import com.capitalone.dashboard.repository.ComponentRepository;

/**
 * CollectorTask that fetches configuration item data from Company
 */
@Component
public class CmdbCollectorTask extends CollectorTask<CmdbCollector> {
	private static final Log LOG = LogFactory.getLog(CmdbCollectorTask.class);

	private final CollectorCmdbRepository collectorCmdbRepository;
	private final CmdbItemRepository cmdbRepository;
	private final CmdbClient defaultCmdbClient;
	private final CmdbSettings cmdbSettings;

	private static final String APP_ACTION_NAME = "cmdb";

	@Autowired
	public CmdbCollectorTask(TaskScheduler taskScheduler, CmdbSettings cmdbSettings,
			CollectorCmdbRepository collectorCmdbRepository, CmdbItemRepository cmdbRepository,
			ComponentRepository componentRepository, CmdbClient defaultCmdbClient) {

		super(taskScheduler, APP_ACTION_NAME);

		this.cmdbSettings = cmdbSettings;
		this.collectorCmdbRepository = collectorCmdbRepository;
		this.cmdbRepository = cmdbRepository;
		this.defaultCmdbClient = defaultCmdbClient;
	}

	@Override
	public CmdbCollector getCollector() {
		return CmdbCollector.prototype(APP_ACTION_NAME);
	}

	@Override
	public BaseCollectorRepository<CmdbCollector> getCollectorRepository() {
		return collectorCmdbRepository;
	}

	@Override
	public String getCron() {
		return cmdbSettings.getCron();
	}

	/**
	 * Takes configurationItemNameList (list of all APP/component names) and
	 * List<Cmdb> from client and sets flag to false for old items in mongo
	 * 
	 * @param configurationItemNameList
	 * @return return count of items invalidated
	 */
	private int cleanUpOldCmdbItems(List<Item> configurationItemNameList) {
		int inValidCount = 0;
		for (Cmdb cmdb : cmdbRepository.findAllByValidConfigItem(true)) {
			Item configItem = new Item(cmdb.getConfigurationItem(), cmdb.getConfigurationItemSubType());
			if (configurationItemNameList != null && !configurationItemNameList.contains(configItem)) {
				cmdb.setValidConfigItem(false);
				cmdbRepository.save(cmdb);
				inValidCount++;
			}
		}
		return inValidCount;
	}

	private void collectApps(CmdbCollector collector) throws HygieiaException {
		List<Cmdb> cmdbList;
		List<Item> configurationItemNameList = new ArrayList<>();

		int updatedCount = 0;
		int insertCount = 0;
		int inValidCount;

		cmdbList = defaultCmdbClient.getApps();

		for (Cmdb cmdb : cmdbList) {
			String configItem = cmdb.getConfigurationItem();
			String subType = cmdb.getConfigurationItemSubType();
			String configItemType = cmdb.getConfigurationItemType();
			
			Cmdb cmdbDbItem = cmdbRepository.findByConfigurationItemAndConfigurationItemSubTypeAndConfigurationItemType(configItem, subType, configItemType);
			configurationItemNameList.add(new Item(configItem, subType));
			if (cmdbDbItem != null && !cmdb.equals(cmdbDbItem)) {
				cmdb.setId(cmdbDbItem.getId());
				cmdb.setCollectorItemId(collector.getId());
				cmdbRepository.save(cmdb);
				updatedCount++;
			} else if (cmdbDbItem == null) {
				cmdb.setCollectorItemId(collector.getId());
				cmdbRepository.save(cmdb);
				insertCount++;
			}
		}

		inValidCount = cleanUpOldCmdbItems(configurationItemNameList);

		LOG.info("Inserted Cmdb Item Count: " + insertCount);
		LOG.info("Updated Cmdb Item Count: " + updatedCount);
		LOG.info("Invalid Cmdb Item Count: " + inValidCount);

	}

	@Override
	public void collect(CmdbCollector collector) {
		long start = System.currentTimeMillis();
		log("Starting", start);
		try {
			log("Collecting Apps");
			collectApps(collector);
		} catch (HygieiaException he) {
			LOG.error(he);
		}
		log("Finished", start);
	}
}