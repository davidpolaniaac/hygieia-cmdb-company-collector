package com.capitalone.dashboard.collector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capitalone.dashboard.constant.CmdbType;
import com.capitalone.dashboard.constant.Environment;
import com.capitalone.dashboard.constant.MainCollection;
import com.capitalone.dashboard.misc.HygieiaException;
import com.capitalone.dashboard.model.Cmdb;
import com.capitalone.dashboard.model.Document;
import com.capitalone.dashboard.model.DocumentApp;
import com.capitalone.dashboard.model.DocumentEnv;
import com.capitalone.dashboard.model.DocumentManagement;
import com.capitalone.dashboard.service.DocumentService;

@Component
public class CmdbClientImpl implements CmdbClient {

	private static final Logger LOG = LoggerFactory.getLogger(CmdbClientImpl.class);
	private final DocumentService documentService;
	private final CmdbSettings cmdbSettings;

	@Autowired
	public CmdbClientImpl(DocumentService documentService, CmdbSettings cmdbSettings) {
		this.documentService = documentService;
		this.cmdbSettings = cmdbSettings;
	}

	private List<DocumentEnv> createEnviroment() {
		List<DocumentEnv> envs = new ArrayList<>();

		DocumentEnv develop = new DocumentEnv(Environment.DEVELOPMENT.toString(), Environment.DEVELOPMENT.toString());
		DocumentEnv release = new DocumentEnv(Environment.RELEASE.toString(), Environment.RELEASE.toString());
		DocumentEnv pdn = new DocumentEnv(Environment.PRODUCTION.toString(), Environment.PRODUCTION.toString());

		envs.add(develop);
		envs.add(release);
		envs.add(pdn);

		return envs;
	}

	@Override
	public List<Cmdb> getApps() throws HygieiaException {

		List<Cmdb> cmdbList = new ArrayList<>();
		List<DocumentManagement> elements = new ArrayList<>();
		List<Document> domains = documentService.getDocuments(cmdbSettings.getProjectId());
		if (domains.isEmpty()) {
			LOG.error("Collection empty: " + MainCollection.MANAGEMENTS.toString() + " - " + "CollectionId: "
					+ cmdbSettings.getProjectId());
		}
		domains.parallelStream().forEach(domain -> {
			DocumentManagement management = new DocumentManagement(domain.getName(), domain.getId());
			List<Document> apps = documentService.getDocuments(domain.getId());
			badCollections(apps, domain, MainCollection.MANAGEMENTS.toString());
			apps.parallelStream().forEach(app -> {
				DocumentApp docApp = new DocumentApp(app.getName(), app.getId());
				List<Document> components = documentService.getDocuments(app.getId());
				badCollections(components, app, MainCollection.APPLICATIONS.toString());
				List<DocumentEnv> environments = createEnviroment();

				environments.parallelStream().forEach(env -> {
					Set<Document> set = new HashSet<>(components);
					env.setComponents(set);
					docApp.addEnviroment(env);
				});

				management.addApp(docApp);

			});

			elements.add(management);
		});

		cmdbList.addAll(getAppList(elements));
		cmdbList.addAll(getComponentList(elements));
		cmdbList.addAll(getEnvironmentList(elements));

		return cmdbList;

	}

	private void badCollections(List<Document> documents, Document document, String type) {
		if (documents.isEmpty()) {
			LOG.error("Collection empty: " + document.getId());
			LOG.error(type + " Name: " + document.getName() + " collectionId" + document.getId() + " collectionParent:"
					+ document.getCollection());
		}
	}

	private List<Cmdb> getAppList(List<DocumentManagement> elements) throws HygieiaException {

		List<Cmdb> appList = new ArrayList<>();

		for (DocumentManagement element : elements) {

			for (DocumentApp app : element.getApps()) {

				Cmdb cmdb = new Cmdb();

				cmdb.setConfigurationItem(app.getId());
				cmdb.setConfigurationItemSubType(element.getId());
				cmdb.setConfigurationItemType(CmdbType.APP.toString());
				cmdb.setCommonName(app.getName());
				cmdb.setAssignmentGroup(element.getName());
				cmdb.setOwnerDept(element.getName());
				cmdb.setAppServiceOwner(element.getName());
				cmdb.setBusinessOwner(element.getName());
				cmdb.setSupportOwner(element.getName());
				cmdb.setDevelopmentOwner(element.getName());
				cmdb.setItemType(CmdbType.APP.toString());
				cmdb.setValidConfigItem(true);
				cmdb.setTimestamp(System.currentTimeMillis());

				List<String> environments = app.getEnviroments().parallelStream().map(env -> env.getId()).distinct()
						.collect(Collectors.toList());
				List<String> components = app.getEnviroments().parallelStream().map(env -> env.getComponents())
						.flatMap(componentes -> componentes.stream()).map(com -> com.getId()).distinct().sorted()
						.collect(Collectors.toList());

				cmdb.setEnvironments(environments);
				cmdb.setComponents(components);

				appList.add(cmdb);

			}
		}

		return appList;

	}

	private List<Cmdb> getEnvironmentList(List<DocumentManagement> elements) throws HygieiaException {

		List<Cmdb> enviroments = new ArrayList<>();

		for (DocumentManagement element : elements) {

			List<DocumentEnv> appEnviroments = element.getApps().parallelStream().map(app -> app.getEnviroments())
					.flatMap(envs -> envs.stream()).distinct().collect(Collectors.toList());

			for (DocumentEnv appEnvironment : appEnviroments) {

				Cmdb cmdb = new Cmdb();

				cmdb.setConfigurationItem(appEnvironment.getId());
				cmdb.setConfigurationItemSubType(element.getId());
				cmdb.setConfigurationItemType(CmdbType.ENVIRONMENT.toString());
				cmdb.setCommonName(appEnvironment.getName());
				cmdb.setAssignmentGroup(element.getName());
				cmdb.setOwnerDept(element.getName());
				cmdb.setAppServiceOwner(element.getName());
				cmdb.setBusinessOwner(element.getName());
				cmdb.setSupportOwner(element.getName());
				cmdb.setDevelopmentOwner(element.getName());
				cmdb.setItemType(CmdbType.ENVIRONMENT.toString());
				cmdb.setValidConfigItem(true);
				cmdb.setTimestamp(System.currentTimeMillis());

				List<String> components = appEnvironment.getComponents().parallelStream().map(com -> com.getId())
						.distinct().sorted().collect(Collectors.toList());

				cmdb.setComponents(components);

				enviroments.add(cmdb);

			}

		}

		return enviroments;
	}

	private List<Cmdb> getComponentList(List<DocumentManagement> elements) throws HygieiaException {

		List<Cmdb> components = new ArrayList<>();

		for (DocumentManagement element : elements) {

			List<Document> compnentes = element.getApps().parallelStream().map(app -> app.getEnviroments())
					.flatMap(envs -> envs.stream()).map(env -> env.getComponents()).flatMap(comps -> comps.stream())
					.distinct().collect(Collectors.toList());

			for (Document component : compnentes) {

				Cmdb cmdb = new Cmdb();
				cmdb.setConfigurationItem(component.getId());
				cmdb.setConfigurationItemSubType(element.getId());
				cmdb.setConfigurationItemType(CmdbType.COMPONENT.toString());
				cmdb.setCommonName(component.getName());
				cmdb.setAssignmentGroup(element.getName());
				cmdb.setOwnerDept(element.getName());
				cmdb.setAppServiceOwner(element.getName());
				cmdb.setBusinessOwner(element.getName());
				cmdb.setSupportOwner(element.getName());
				cmdb.setDevelopmentOwner(element.getName());
				cmdb.setItemType(CmdbType.COMPONENT.toString());
				cmdb.setValidConfigItem(true);
				cmdb.setTimestamp(System.currentTimeMillis());

				components.add(cmdb);

			}

		}

		return components;
	}

}
