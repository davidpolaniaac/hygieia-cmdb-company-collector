---
title: Company Collector
tags: CMDB
keywords:
summary:
sidebar: hygieia_sidebar
permalink: company.html
---
Configure the Company Collector to display and monitor information (related to configuration management) on the Hygieia Dashboard, from Azure DevOps. Hygieia uses Spring Boot to package the collector as an executable JAR file with dependencies.

### Setup Instructions

## Fork and Clone the Collector 

Fork and clone the Company Collector from the [GitHub repo](). 

To configure the Company Collector, execute the following steps:

*   **Step 1: Change Directory**

	Change the current working directory to the `hygieia-cmdb-company-collector` directory of your Hygieia source code installation.

	For example, in the Windows command prompt, run the following command:

	```
	cd C:\Users\[username]\hygieia-cmdb-company-collector
	```

*   **Step 2: Run Maven Build**

	Run the maven build to package the collector into an executable JAR file:

	```bash
	mvn install
	```

	The output file `[collector name].jar` is generated in the `hygieia-cmdb-company-collector\target` folder.

*   **Step 3: Set Parameters in Application Properties File**

	Set the configurable parameters in the `application.properties` file to connect to the Dashboard MongoDB database instance, including properties required by the Company Collector.

	To configure parameters for the Company Collector, refer to the sample [application.properties](#sample-application-properties-file) file.

	For information about sourcing the application properties file, refer to the [Spring Boot Documentation](http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#boot-features-external-config-application-property-files).

*   **Step 4: Deploy the Executable File**

	To deploy the `[collector name].jar` file, change directory to `hygieia-cmdb-company-collector\target`, and then execute the following from the command prompt:

	```bash
	java -Dcollector.action=[action] -jar [collector name].jar --spring.config.name=company --spring.config.location=[path to application.properties file]
	```

### Sample Application Properties File

The sample `application.properties` file lists parameters with sample values to configure the Company Collector. Set the parameters based on your environment setup.

```properties
# Database Name
dbname=dashboarddb

# Database HostName - default is localhost
dbhost=localhost

# Database Port - default is 27017
dbport=27017

# Database Username - default is blank
dbusername=dashboarduser

# Database Password - default is blank
dbpassword=dbpassword

# Logging File location
logging.file=./logs/cmdb.log

#Collector schedule (required)
cmdb.cron=0 0/2 * * * *

# Proxy Host
cmdb.proxyHost=

# Proxy Port 8080
cmdb.proxyPort=

# Non Proxy xxx.xxx.xxx.xxx
cmdb.nonProxy=

# Azure DevOps api version
cmdb.apiVersion=5.0-preview.1

# Azure DevOps personal access token
cmdb.apiKey=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

# Azure DevOps project name
cmdb.project=xxxxxxxxxxxxxxxxxxxxxxxxxxxx

# Azure DevOps organization Name or Account Name
cmdb.organizationName=xxxxxxxxxxxxxxxxxxxxxxx

# Azure DevOps publisher extension
cmdb.publisher=xxxxxxxxxxxxxxxxxxxxxx
		
```
