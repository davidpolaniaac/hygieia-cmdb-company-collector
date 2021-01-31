#!/bin/sh
  
# if [ "$MONGO_PORT" != "" ]; then
# 	# Sample: MONGO_PORT=tcp://172.17.0.20:27017
# 	MONGODB_HOST=`echo $MONGO_PORT|sed 's;.*://\([^:]*\):\(.*\);\1;'`
# 	MONGODB_PORT=`echo $MONGO_PORT|sed 's;.*://\([^:]*\):\(.*\);\2;'`
# else
# 	env
# 	echo "ERROR: MONGO_PORT not defined"
# 	exit 1
# fi

# echo "MONGODB_HOST: $MONGODB_HOST"
# echo "MONGODB_PORT: $MONGODB_PORT"


cat > $PROP_FILE <<EOF

# Database Name
dbname=${MONGODB_DATABASE:-dashboarddb}

# Database HostName - default is localhost
dbhost=${MONGODB_HOST:-db}

# Database Port - default is 27017
dbport=${MONGODB_PORT:-27017}

# Database Username - default is blank
dbusername=${MONGODB_USERNAME:-dashboarduser}

# Database Password - default is blank
dbpassword=${MONGODB_PASSWORD:-dbpassword}

# Logging File location
logging.file=./logs/cmdb.log

#Collector schedule (required)
cmdb.cron=${CRON:-0 0/2 * * * *}

# Proxy Host
cmdb.proxyHost=${PROXY_HOST:-}

#Proxy Port
cmdb.proxyPort=${PROXY_PORT:-}

#Non Proxy xxx.xxx.xxx.xxx
cmdb.nonProxy=${NOT_PROXY_HOST:-}

# Azure DevOps api version
cmdb.apiVersion=${APIVERSION:-5.0-preview.1}

# Azure DevOps personal access token
cmdb.apiKey=${CMDB_PERSONAL_ACCESS_TOKEN:-}

# Azure DevOps project
cmdb.project=${PROJECT:-}

# Azure DevOps publisher extension
cmdb.organizationName=${ORGANIZATIONNAME:-}

# Azure DevOps publisher extension
cmdb.publisher=${PUBLISHER:-davidpolaniaac}

EOF

echo "

===========================================
Properties file created `date`:  $PROP_FILE
Note: passwords hidden
===========================================
`cat $PROP_FILE |egrep -vi apiKey`
 "

exit 0
