# spring-boot-stocktwits-starter

## About

This is a spring-boot starter project that uses [stocktwits-java-api](https://github.com/enid0227/stocktwits-java-api) client project. This project covers OAuth-login with Stocktwits credential and getting data using [StockTwtis API].

## How to Use this Project

### Prerequisite

install [docker](https://hub.docker.com/editions/community/docker-ce-desktop-mac)

install [minikube](https://minikube.sigs.k8s.io/docs/start/) (or with homebrew `brew install minikube`)

install [ngrok](https://ngrok.com/download) (or with homebrew `brew install --cask ngrok`)

### Run Database Server on `minikube`

**Required for both local development server!!**

1. make sure minikube started already

if not, run the below command:

```sh
minikube start
```

2. make sure minikube env variables are in the terminal context

```sh
eval $(minikube docker-env)
```

3. setup kube secrets for database

```sh
source devops/scripts/create-local-mysql-secrets.sh
```

4. start MySQL services on minikube

```sh
kubectl create -f devops/k8s/local-mysql-deployment.yaml
```

5. check service is ready

```sh
# command to see all running services
kubectl get service

# you should see the following
NAME                              TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)          AGE
demo-app-mysql-service   NodePort    10.105.13.181   <none>        3306:32322/TCP   85m
```

6. export secrets to environment variables inside terminal context

```sh
source devops/scripts/export-secrets-for-local-run.sh
```

7. test mysql connection with env variables

```sh
mysql --host ${LOCAL_MINIKUBE_IP} --port 32322 --protocol=TCP \
    --user=${APP_DATASOURCE_USERNAME} \
    --password=${APP_DATASOURCE_PASSWORD}
```

### Update StockTwtis App Client Id(Key) and Secret

8. Change client-id and client-secret in the [main/application.yaml](src/main/resources/application.yaml)

### Run locally with maven wrapper

```sh
./mvnw spring-boot:run

# open browser on http://localhost:8080
# to see local server running on machine

# to stop server: Ctrl + c
```

### Run `ngrok` to Expose localhost to Generated Domain

9. expose localhost:8080 to a ngrok generated domain over http

**IMPORTANT**: Sign up [ngrok](ngrok.com) and authenticate local ngrok binary so the tunnneling will work.

**Command**

```sh
# command
ngrok http 8080
```

**Example Output**

```
ngrok by @inconshreveable                                                                                                                                         (Ctrl+C to quit)

Session Status                online
Account                       <youraccount>@gmail.com (Plan: Free)
Version                       2.3.40
Region                        United States (us)
Web Interface                 http://127.0.0.1:4040
Forwarding                    http://tempdomain1.ngrok.io -> http://localhost:8080
Forwarding                    https://tempdomain1.ngrok.io -> http://localhost:8080

Connections                   ttl     opn     rt1     rt5     p50     p90
                              0       0       0.00    0.00    0.00    0.00
```

10. visit your localhost website on the generated URL. You should be able to access your local server over the generated URL (in the example output: https://tempodmain1.ngrok.io)

`ngrok http 8080` will expose your local 8080 port to a generated ngrok domain over http. This generated domain (`tempdomain1.ngrok.io` in the example output) will be used in StockTwits app setting.

11. update StockTwits app setting in the [registered app page](https://api.stocktwits.com/developers/apps)

- Site Domain: ngrok generated domain (example: `tempdomain1.ngrok.io`)
- Website URL: ngrok generated domain url (example: `https://tempdomain1.ngrok.io`)

12. try login with StockTwits on the page index

Now, go to the generated domain URL (example: `https://tempdomain1.ngrok.io`), and click `Login With StockTwits: click here`. You'll be prompt to grant access to the app you created on the StockTwits. Once you agree, you'll be redirected back to the demo app index, and the StockTwits username should show up on the index.

13. try access `sterams` endpoint

- visit `/streams/trending/` endpoint to see latest trending messages
- visit `/streams/symbols/` endpoint to see symbol messages from AAPL, MSFT

See how the demo app uses [stocktwits-java-api](https://github.com/enid0227/stocktwits-java-api) client in the [StreamController.java](src/main/java/com/example/stocktwitsdemo/streams/StreamController.java).

### Cleanup MySQL Service

```sh
kubectl delete -f devops/k8s/local-mysql-deployment.yaml
```

## TODO

- add blog about OAuth customization changes
- add better comments throughout codebase
