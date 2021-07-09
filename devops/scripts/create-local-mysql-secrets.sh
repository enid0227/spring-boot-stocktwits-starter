# IMPORTANT: this is only for local development usage. Changing the below values is preferred
export ROOT_PASSWORD="Zaq!@34"
export APP_DATASOURCE_USERNAME="appdbuser"
export APP_DATASOURCE_PASSWORD="localdbpass"

# check mysql service name in local-mysql-deployment.yml file
MYSQL_SERVICE_NAME="stocktwitlist-app-mysql-service"
MYSQL_SERVICE_PORT="3306"
MYSQL_APP_DB_NAME="web-app-db"
export APP_DATASOURCE_URL="jdbc:mysql://${MYSQL_SERVICE_NAME}:${MYSQL_SERVICE_PORT}/${MYSQL_APP_DB_NAME}?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false"

# setup root username/password secret
kubectl create secret generic mysql-root-pass \
    --from-literal=password=${ROOT_PASSWORD}

# setup mysql username/password secret
kubectl create secret generic mysql-user-pass \
    --from-literal=username=${APP_DATASOURCE_USERNAME} \
    --from-literal=password=${APP_DATASOURCE_PASSWORD}

# setup myslq url secret
kubectl create secret generic mysql-db-url \
    --from-literal=database=${MYSQL_APP_DB_NAME} \
    --from-literal=url=${APP_DATASOURCE_URL}
