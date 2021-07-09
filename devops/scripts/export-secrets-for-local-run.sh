# export secret to env variables defined in application.properties
export APP_DATASOURCE_USERNAME="$(kubectl get secret mysql-user-pass -o jsonpath="{.data.username}" | base64 --decode)"
export APP_DATASOURCE_PASSWORD="$(kubectl get secret mysql-user-pass -o jsonpath="{.data.password}" | base64 --decode)"
export LOCAL_MINIKUBE_IP="$(minikube ip)"
export APP_DB_NAME="$(kubectl get secret mysql-db-url -o jsonpath="{.data.database}" | base64 --decode)"
export DB_SVC_NODEPORT=32322
export APP_DATASOURCE_URL="jdbc:mysql://${LOCAL_MINIKUBE_IP}:${DB_SVC_NODEPORT}/${APP_DB_NAME}?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false"