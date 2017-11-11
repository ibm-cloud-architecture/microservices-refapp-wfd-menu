# Microservices Reference Application - What's For Dinner

## Menu Service - MicroProfile

This repository contains the **Java MicroProfile** implementation of the **Menu Service** which is a part of microservice-based reference application called **What's For Dinner** that can be found in https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd

<p align="center">
  <a href="https://www.ibm.com/us-en/marketplace/microservice-builder/">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/MSB.jpg" width="300" height="100">
  </a>
  &nbsp;
  <a href="https://microprofile.io/">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/microprofile_small.png" width="300" height="100">
  </a>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <a href="https://www.ibm.com/cloud-computing/solutions/private-cloud/">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/ICP.jpg" width="120" height="100">
  </a>
</p>

1. [Introduction](#introduction)
2. [How it works](#how-it-works)
3. [API Endpoints](#api-endpoints)
4. [Implementation](#implementation)
    1. [Microprofile](#microprofile)
    2. [Maven build](#maven-build)
        - [Running the application locally using Maven Build](#running-the-application-locally-using-Maven-Build)
    3. [Docker file](#docker-file)
        - [Running the application locally in a docker container](#running-the-application-locally-in-a-docker-container)
    4. [Microservice Builder](#microservice-builder)
        1. [Minikube development Environment](#minikube-development-environment)
           - [Running the application on Minikube](#running-the-application-on-minikube)
        2. [IBM Cloud Private](#ibm-cloud-private)
           - [Running the application on IBM Cloud Private](#running-the-application-on-ibm-cloud-private)
5. [References](#references)

### Introduction

This project demonstrates the implementation of Menu Microservice. The menu microservice retrieves the list of appetizers, desserts and entrees from the down stream services [Appetizer](https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd-appetizer/tree/microprofile), [Dessert](https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd-dessert/tree/microprofile) and [Entree](https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd-entree/tree/microprofile) microservices.

- Based on [MicroProfile](https://microprofile.io/).
- Using Backend for Frontend design pattern.
- Integrated with the [MicroService Builder](https://developer.ibm.com/microservice-builder/).
- Deployment options for local, Docker Container-based runtimes, Minikube environment and ICP/BMX.

### How it works

Menu Microservice serves [**What's For Dinner**](https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd), Microservice-based reference application. Though it is a part of a bigger application, Entree service is itself an application in turn that gets the data from the downstream services. This service reads the data from the downstream services and returns it through a REST service. This service acts as the backend for the UI.

#### Pre-requisite

For the Menu microservice to work,the downstream services [Appetizer](https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd-appetizer/tree/microprofile), [Dessert](https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd-dessert/tree/microprofile) and [Entree](https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd-entree/tree/microprofile) must be running.

### Endpoints

```
GET     /WfdMenu/rest/health/        # Health check of the service
GET     /WfdMenu/rest/menu/          # Returns the appetizers, desserts, entrees available
```

### Implementation

#### [MicroProfile](https://microprofile.io/)

MicroProfile is an open platform that optimizes the Enterprise Java for microservices architecture. In this application, we are using [**MicroProfile 1.2**](https://github.com/eclipse/microprofile-bom). This includes

- MicroProfile 1.0 ([JAX-RS 2.0](https://jcp.org/en/jsr/detail?id=339), [CDI 1.2](https://jcp.org/en/jsr/detail?id=346), and [JSON-P 1.0](https://jcp.org/en/jsr/detail?id=353))
- MicroProfile 1.1 (MicroProfile 1.0, [MicroProfile Config 1.0.](https://github.com/eclipse/microprofile-config))
- [MicroProfile Config 1.1](https://github.com/eclipse/microprofile-config) (supercedes MicroProfile Config 1.0), [MicroProfile Fault Tolerance 1.0](https://github.com/eclipse/microprofile-fault-tolerance), [MicroProfile Health Check 1.0](https://github.com/eclipse/microprofile-health), [MicroProfile Metrics 1.0](https://github.com/eclipse/microprofile-metrics), [MicroProfile JWT Authentication 1.0](https://github.com/eclipse/microprofile-jwt-auth).

You can make use of this feature by including this dependency in Maven.

```
<dependency>
<groupId>org.eclipse.microprofile</groupId>
<artifactId>microprofile</artifactId>
<version>1.2</version>
<type>pom</type>
<scope>provided</scope>
</dependency>
```

You should also include a feature in [server.xml](https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd-menu/blob/microprofile/src/main/liberty/config/server.xml).

```
<server description="Sample Liberty server">

  <featureManager>
      <feature>microprofile-1.2</feature>
  </featureManager>

  <httpEndpoint httpPort="${default.http.port}" httpsPort="${default.https.port}"
      id="defaultHttpEndpoint" host="*" />

</server>
```

#### Maven build

Maven is a project management tool that is based on the Project Object Model (POM). Typically, people use Maven for project builds, dependencies, and documentation. Maven simplifies the project build. In this task, you use Maven to build the project.

For Liberty, there is nice tool called [Liberty Accelerator](https://liberty-app-accelerator.wasdev.developer.ibm.com/start/) that generates a simple project based upon your configuration. Using this, you can build and deploy to Liberty either using the Maven or Gradle build.

<p align="center">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/LibertyAcc_Home.png">
</p>

Just check the options of your choice and click Generate project. You can either Download it as a zip or you can create git project.

<p align="center">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/LibertyAcc_PrjGen.png">
</p>

Once you are done with this, you will have a sample microprofile based application that you can deploy on Liberty.

If you are using Liberty accelerator, look for the generated POM. It may contain unwanted dependencies. Please remove accordingly.

For our application, the following are the required dependencies which are the part of auto-generated POM. Please remove the rest of the dependencies.

```
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.apache.cxf</groupId>
    <artifactId>cxf-rt-rs-client</artifactId>
    <version>3.1.11</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.glassfish</groupId>
    <artifactId>javax.json</artifactId>
    <version>1.0.4</version>
    <scope>test</scope>
</dependency>
```

Using Liberty Accelerator is your choice. You can also create the entire project manually, but using Liberty Accelerator will make things easier.

To make use of MicroProfile 1.2, include the below dependency in your POM.

```
<dependency>
    <groupId>org.eclipse.microprofile</groupId>
    <artifactId>microprofile</artifactId>
    <version>1.2</version>
    <type>pom</type>
    <scope>provided</scope>
</dependency>
```

If you want to enable Zipkin OpenTracing feature, please include the below dependency in your POM.

```
<dependency>
    <groupId>net.wasdev.wlp.tracer</groupId>
    <artifactId>liberty-opentracing-zipkintracer</artifactId>
    <version>1.0</version>
    <type>jar</type>
    <scope>provided</scope>
</dependency>
```

If you are planning to include zipkin tracer in your application, please add the below feature to your [server.xml](https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd-menu/blob/microprofile/src/main/liberty/config/server.xml).

```
<feature>opentracingZipkin-0.30</feature>
```

##### Running the application locally using Maven Build

1. Clone this repository.

   `git clone https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd-menu.git`

2. Checkout MicroProfile branch.

   `git checkout microprofile`

3. `cd refarch-cloudnative-wfd-menu/`

4. Run this command. This command builds the project and installs it.

   `mvn install`

   If this runs successfully, you will be able to see the below messages.

```
[INFO] --- maven-failsafe-plugin:2.18.1:verify (verify-results) @ WfdMenu ---
[INFO] Failsafe report directory: /Users/Hemankita.Perabathini@ibm.com/PurpleCompute/Microprofile/WhatsForDinner/refarch-cloudnative-wfd-menu/target/test-reports/it
[INFO]
[INFO] --- maven-install-plugin:2.4:install (default-install) @ WfdMenu ---
[INFO] Installing /Users/Hemankita.Perabathini@ibm.com/PurpleCompute/Microprofile/WhatsForDinner/refarch-cloudnative-wfd-menu/target/WfdMenu-1.0-SNAPSHOT.war to /Users/Hemankita.Perabathini@ibm.com/.m2/repository/projects/WfdMenu/1.0-SNAPSHOT/WfdMenu-1.0-SNAPSHOT.war
[INFO] Installing /Users/Hemankita.Perabathini@ibm.com/PurpleCompute/Microprofile/WhatsForDinner/refarch-cloudnative-wfd-menu/pom.xml to /Users/Hemankita.Perabathini@ibm.com/.m2/repository/projects/WfdMenu/1.0-SNAPSHOT/WfdMenu-1.0-SNAPSHOT.pom
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 18.971 s
[INFO] Finished at: 2017-11-02T21:37:26-05:00
[INFO] Final Memory: 19M/303M
[INFO] ------------------------------------------------------------------------
```

5. Before starting your server, make sure [Appetizer](https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd-appetizer/tree/microprofile), [Dessert](https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd-dessert/tree/microprofile) and [Entree](https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd-entree/tree/microprofile) services are running. If you are running all the services locally on your system, you need to modify the ports in the respective POM.xml and run the server.

- Open POM.xml in your respective microservice. Modify the below. Place the desired port here. Make sure all the services are running on different ports.

```
<testServerHttpPort>PORT_NUMBER</testServerHttpPort>

```

Let's say the appetizer service is running on 9081, dessert service is running on 9082 and entree service is running on 9083. Verify if all these services are running.

<p align="center">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/Appetizer_local.png">
</p>

<p align="center">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/Dessert_local.png">
</p>

<p align="center">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/Entree_local.png">
</p>

You can find a greater detail on how to run these services locally [here](https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/local_readme.md).

6. Now start your server.

   `mvn liberty:start-server`

   You will see the below.
```
[INFO] Starting server defaultServer.
[INFO] Server defaultServer started with process ID 41852.
[INFO] Waiting up to 30 seconds for server confirmation:  CWWKF0011I to be found in /Users/Hemankita.Perabathini@ibm.com/PurpleCompute/Microprofile/WhatsForDinner/refarch-cloudnative-wfd-menu/target/liberty/wlp/usr/servers/defaultServer/logs/messages.log
[INFO] CWWKM2010I: Searching for CWWKF0011I in /Users/Hemankita.Perabathini@ibm.com/PurpleCompute/Microprofile/WhatsForDinner/refarch-cloudnative-wfd-menu/target/liberty/wlp/usr/servers/defaultServer/logs/messages.log. This search will timeout after 30 seconds.
[INFO] CWWKM2015I: Match number: 1 is [11/2/17 22:22:06:570 CDT] 00000019 com.ibm.ws.kernel.feature.internal.FeatureManager            A CWWKF0011I: The server defaultServer is ready to run a smarter planet..
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 14.488 s
[INFO] Finished at: 2017-11-02T22:22:06-05:00
[INFO] Final Memory: 9M/309M
[INFO] ------------------------------------------------------------------------
```

6. Now, go to your browser and access the REST endpoint at http://localhost:9080/WfdMenu/rest/menu.

<p align="center">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/MenuScreen.png">
</p>

   Access URL : `http://<HOST>:<PORT>/<WAR_CONTEXT>/<APPLICATION_PATH>/<ENDPOINT>`

In our sample application, you can get the details of the above URL as follows.

- Since, we are running the application locally on our system, the **HOST** will be `localhost`.
- You can get the **PORT** and **WAR_CONTEXT** from the `<properties> </properties>` section of our POM.

```
<app.name>WfdMenu</app.name>
<testServerHttpPort>9080</testServerHttpPort>
<testServerHttpsPort>9443</testServerHttpsPort>
<warContext>${app.name}</warContext>
```
So, **PORT** will be `9080` and **WAR_CONTEXT** will be `WfdMenu`.

- **APPLICATION_PATH** can be found in [MenuApplication.java](https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd-menu/blob/microprofile/src/main/java/application/rest/MenuApplication.java)

```
@ApplicationPath("/rest")
```

In our sample, the **APPLICATION_PATH** will be `rest`.

- Finally you can find the entree endpoint at [MenuResource.java](https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd-menu/blob/microprofile/src/main/java/application/rest/MenuResource.java)

```
@Path("menu")
```
So, **ENDPOINT** to access the rest api that exposes the list of appetizers, desserts, entrees is `menu`.

Also, there is one more endpoint defined at [HealthEndpoint.java](https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd-menu/blob/microprofile/src/main/java/application/rest/HealthEndpoint.java)

```
@Path("health")
```

To access the health api, replace the **ENDPOINT** with `health`. This endpoint gives the health of your application. To check this, use http://localhost:9080/WfdMenu/rest/health.

7. If you are done accessing the application, you can stop your server using the following command.

   `mvn liberty:stop-server`

Once you do this, you see the below messages.

```
[INFO] CWWKM2001I: Invoke command is [/Users/Hemankita.Perabathini@ibm.com/PurpleCompute/Microprofile/WhatsForDinner/refarch-cloudnative-wfd-menu/target/liberty/wlp/bin/server, stop, defaultServer].
[INFO] objc[45369]: Class JavaLaunchHelper is implemented in both /Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre/bin/java (0x10a4fa4c0) and /Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre/lib/libinstrument.dylib (0x10a5f44e0). One of the two will be used. Which one is undefined.
[INFO] Stopping server defaultServer.
[INFO] Server defaultServer stopped.
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.885 s
[INFO] Finished at: 2017-11-02T22:33:26-05:00
[INFO] Final Memory: 7M/245M
```

#### Docker file

We are using Docker to containerize the application. With Docker, you can pack, ship, and run applications on a portable, lightweight container that can run anywhere virtually.

```
FROM websphere-liberty:microProfile

MAINTAINER IBM Java engineering at IBM Cloud

COPY /target/liberty/wlp/usr/servers/defaultServer /config/
COPY target/liberty/wlp/usr/shared /opt/ibm/wlp/usr/shared/

RUN wget -t 10 -x -nd -P /opt/ibm/wlp/usr https://repo1.maven.org/maven2/net/wasdev/wlp/tracer/liberty-opentracing-zipkintracer/1.0/liberty-opentracing-zipkintracer-1.0-sample.zip && cd /opt/ibm/wlp/usr && unzip liberty-opentracing-zipkintracer-1.0-sample.zip && rm liberty-opentracing-zipkintracer-1.0-sample.zip

# Install required features if not present
RUN installUtility install --acceptLicense defaultServer

CMD ["/opt/ibm/wlp/bin/server", "run", "defaultServer"]
```

- The `FROM` instruction sets the base image. You're setting the base image to `websphere-liberty:microProfile`.
- The `MAINTAINER` instruction sets the Author field. Here it is `IBM Java engineering at IBM Cloud`.
- The `COPY` instruction copies directories and files from a specified source to a destination in the container file system.
  - You're copying the `/target/liberty/wlp/usr/servers/defaultServer` to the `config` directory in the container.
  - You're replacing the contents of `/opt/ibm/wlp/usr/shared/` with the contents of `target/liberty/wlp/usr/shared`.
- The `RUN` instruction runs the commands.
  - The first instruction gets the Opentracing Zipkin feature and installs it in your server.
  - The second instruction is a precondition to install all the utilities in the server.xml file. You can use the RUN command to install the utilities on the base image.
- The `CMD` instruction provides defaults for an executing container.

##### Running the application locally in a container

1. Clone this repository.

   `git clone https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd-menu.git`

2. Checkout MicroProfile branch.

   `git checkout microprofile`

3. `cd refarch-cloudnative-wfd-menu/`

4. Run this command. This command builds the project and installs it.

   `mvn install`

5. Build the docker image.

`docker build -t wfd-menu:microprofile .`

Once this is done, you will see something similar to the below messages.
```
Successfully built 0f28e1d268d8
Successfully tagged wfd-menu:microprofile
```
You can see the docker images by using this command.

`docker images`

```
REPOSITORY                     TAG                 IMAGE ID            CREATED             SIZE
wfd-menu                       microprofile        0f28e1d268d8        15 seconds ago      377MB
```
6. Before running the docker images, make sure [Appetizer](https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd-appetizer/tree/microprofile), [Dessert](https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd-dessert/tree/microprofile) and [Entree](https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd-entree/tree/microprofile) services are running. If you are running all the services locally on your system, you need to modify the ports.

- Modify the below. Place the desired port here. Make sure all the services are running on different ports.

```
docker run -p <PORT_NUMBER>:9080 --name <NAME> -t <IMAGE_NAME>
```

Let's say the appetizer service is running on 9081, dessert service is running on 9082 and entree service is running on 9083. Verify if all these services are running.

<p align="center">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/Appetizer_local.png">
</p>

<p align="center">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/Dessert_local.png">
</p>

<p align="center">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/Entree_local.png">
</p>

You can find a greater detail on how to run these services locally [here](https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/local_docker_readme.md).

7. Run the docker image.

```
docker run -p 9080:9080 --name menu -t --link appetizer:appetizer --link dessert:dessert --link entree:entree --env appetizer_url=http://appetizer:9080/WfdAppetizer/rest/appetizer --env entree_url=http://entree:9080/WfdEntree/rest/entree --env dessert_url=http://dessert:9080/WfdDessert/rest/dessert wfd-menu:microprofile
```

When it is done, you will see the following output.
```
[AUDIT   ] CWWKZ0058I: Monitoring dropins for applications.
[AUDIT   ] CWWKT0016I: Web application available (default_host): http://ad29d4d694f5:9080/jwt/
[AUDIT   ] CWWKT0016I: Web application available (default_host): http://ad29d4d694f5:9080/ibm/api/
[AUDIT   ] CWWKT0016I: Web application available (default_host): http://ad29d4d694f5:9080/health/
[AUDIT   ] CWWKT0016I: Web application available (default_host): http://ad29d4d694f5:9080/metrics/
[AUDIT   ] CWWKT0016I: Web application available (default_host): http://ad29d4d694f5:9080/WfdMenu/
[AUDIT   ] CWWKZ0001I: Application WfdMenu-1.0-SNAPSHOT started in 0.912 seconds.
[AUDIT   ] CWWKF0012I: The server installed the following features: [microProfile-1.2, mpFaultTolerance-1.0, servlet-3.1, ssl-1.0, jndi-1.0, mpHealth-1.0, appSecurity-2.0, jsonp-1.0, mpConfig-1.1, jaxrs-2.0, jaxrsClient-2.0, concurrent-1.0, jwt-1.0, mpMetrics-1.0, mpJwt-1.0, json-1.0, cdi-1.2, distributedMap-1.0].
[AUDIT   ] CWWKF0011I: The server defaultServer is ready to run a smarter planet.
```
8. Now, view the REST endpoint at http://localhost:9080/WfdMenu/rest/menu.

<p align="center">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/MenuScreen.png">
</p>

   Access URL : `http://<HOST>:<PORT>/<WAR_CONTEXT>/<APPLICATION_PATH>/<ENDPOINT>`

9. Once you make sure the application is working as expected, you can come out of the process. You can do this by pressing Ctrl+C on the command line where the server was started.

10. You can also remove the container if desired. This can be done in the following way.

`docker ps`

```
CONTAINER ID        IMAGE                   COMMAND                  CREATED              STATUS              PORTS                              NAMES
ad29d4d694f5        wfd-menu:microprofile   "/opt/ibm/wlp/bin/..."   About a minute ago   Up About a minute   0.0.0.0:9080->9080/tcp, 9443/tcp   menu
```

Grab the container id.

- Do `docker stop <CONTAINER ID>`
In this case it will be, `docker stop ad29d4d694f5`
- Do `docker rm <CONTAINER ID>`
In this case it will be, `docker rm ad29d4d694f5`

#### [Microservice Builder](https://www.ibm.com/us-en/marketplace/microservice-builder)

Microservice Builder helps us to develop and deploy microservice based applications. It helps us to maintain the application end to end from development to production supporting continuous delivery. Using the pre-integrated Devops pipeline, developers can rapidly build innovative services and deploy them easily.   

##### Minikube development Environment

You can always test your application locally using [Minikube](https://kubernetes.io/docs/getting-started-guides/minikube/) in a local kubernetes environment. It runs a single-node Kubernetes cluster inside a VM.

Before running the application, do the below.

1. Install [Docker](https://docs.docker.com/engine/installation/)
2. Install [Minikube](https://kubernetes.io/docs/getting-started-guides/minikube/#installation)
3. Install [Kubectl CLI](https://kubernetes.io/docs/tasks/tools/install-kubectl/)

**Setting up your environment**

1. Start your minikube. Run the below command.

`minikube start`

You will see output similar to this.

```
Setting up certs...
Connecting to cluster...
Setting up kubeconfig...
Starting cluster components...
Kubectl is now configured to use the cluster.
```

2. To install Tiller which is a server side component of Helm, initialize helm. Run the below command.

`helm init`

If it is successful, you will see the below output.

```
$HELM_HOME has been configured at /Users/Hemankita.Perabathini@ibm.com/.helm.

Tiller (the helm server side component) has been installed into your Kubernetes Cluster.
Happy Helming!
```

3. Check if your tiller is available. Run the below command.

`kubectl get deployment tiller-deploy --namespace kube-system`

If it available, you can see the availability as below.

```
NAME            DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
tiller-deploy   1         1         1            1           1m
```

4. Run the below command to add IBM helm repository

`helm repo add ibm-charts https://raw.githubusercontent.com/IBM/charts/master/repo/stable/`

If added, you will see the below message.

```
"ibm-charts" has been added to your repositories
```

5. To install microservice builder fabric using helm, run the below command.

`helm install --name fabric ibm-charts/ibm-microservicebuilder-fabric`

If you see something like the below message, your tiller version is not compatible.T

```
Error: Chart incompatible with Tiller v2.4.2
```

Sometimes the version of helm installed by Kubernetes package manager might not be compatible. If you are encountering a problem, please upgrade your helm tiller version to packages 2.5.0 or higher. You can do this using the below command.

`helm init --upgrade --tiller-image gcr.io/kubernetes-helm/tiller:v2.5.0`

If the command is successful, you will see the below message.

```
Tiller (the helm server side component) has been upgraded to the current version.
Happy Helming!
```

6. Verify your helm version before proceeding like below.

`helm version`

You will see the below output.

```
Client: &version.Version{SemVer:"v2.4.2", GitCommit:"82d8e9498d96535cc6787a6a9194a76161d29b4c", GitTreeState:"clean"}
Server: &version.Version{SemVer:"v2.5.0", GitCommit:"012cb0ac1a1b2f888144ef5a67b8dab6c2d45be6", GitTreeState:"clean"}
```

7. Run `helm install --name fabric ibm-charts/ibm-microservicebuilder-fabric`

```
Get the Zipkin URL by running these commands:
  export POD_NAME=$(kubectl get pods --namespace default -l "app=fabric-zipkin" -o jsonpath="{.items[0].metadata.name}")
  kubectl port-forward $POD_NAME 9411:9411
  echo "Visit http://127.0.0.1:9411 to use your application"
```

8. Check if your fabric zipkin deployment is available. You can do this by running the below command.

`kubectl get deployment fabric-zipkin`

If it is available, you can see the below message.

```
NAME            DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
fabric-zipkin   1         1         1            1           46s
```

##### Running the application on Minikube

1. Clone this repository.

   `git clone https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd-menu.git`

2. Checkout MicroProfile branch.

   `git checkout microprofile`

3. `cd refarch-cloudnative-wfd-menu/`

4. Run this command. This command builds the project and installs it.

   `mvn install`

5. For your information, this repository is enabled using [IBM Cloud Developer Tools CLI](https://console.bluemix.net/docs/cloudnative/dev_cli.html#developercli). By using `bx dev enable`, based upon your language, it generates and adds files that can be used for local Docker containers, or Kubernetes/Container Deployment etc. You can nicely make use of those templates and customize the files based upon your information.

6. Build the docker image.

Before building the docker image, set the docker environment.

- Run the beow command.

`minikube docker-env`

You will see the output similar to this.

```
export DOCKER_TLS_VERIFY="1"
export DOCKER_HOST="tcp://192.168.99.100:2376"
export DOCKER_CERT_PATH="/Users/Hemankita.Perabathini@ibm.com/.minikube/certs"
export DOCKER_API_VERSION="1.23"
# Run this command to configure your shell:
# eval $(minikube docker-env)
```

- For configuring your shell, run the below command.

`eval $(minikube docker-env)`

- Now run the docker build.

`docker build -t wfdmenu:v1.0.0 .`

If it is a success, you will see the below output.

```
Successfully built eb57538123a2
Successfully tagged wfdmenu:v1.0.0
```

7. Run the helm chart as below.

`helm install --name=wfdmenu chart/wfdmenu`

Yow will see message like below.

```
==> v1beta1/Deployment
NAME                  DESIRED  CURRENT  UP-TO-DATE  AVAILABLE  AGE
wfdmenu-deployment  1        1        1           0          0s
```

Please wait till your deployment is ready. To verify run the same command and you should see the availability.

```
==> v1beta1/Deployment
NAME                  DESIRED  CURRENT  UP-TO-DATE  AVAILABLE  AGE
wfdentree-deployment  1        1        1           1          0s
```

8. You can access the application at `http://<MinikubeIP>:<PORT>/<WAR_CONTEXT>/<APPLICATION_PATH>/<ENDPOINT>`. To get the access url.

- To get the IP, Run this command.

`minikube ip`

You will see something like below.

```
192.168.99.100
```

- To get the port, run this command.

`kubectl get service wfdmenu-service`

You will see something like below.

```
NAME              CLUSTER-IP   EXTERNAL-IP   PORT(S)                         AGE
wfdmenu-service   10.0.0.129   <nodes>       9080:31701/TCP,9443:32338/TCP   1m
```

In the above case, the access url will be `http://192.168.99.100:31701/WfdMenu/rest/menu`.

<p align="center">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/menu_minikube.png">
</p>

##### [IBM Cloud Private](https://www.ibm.com/cloud-computing/solutions/private-cloud/)

IBM Private Cloud is has all the advantages of public cloud but is dedicated to single organization. You can have your own security requirements and customize the environment as well. Basically it has tight security and gives you more control along with scalability and easy to deploy options. You can run it externally or brhind the firewall of your organization.

Basically this is an on-premise platform.
1. Includes docker container manager
2. Kubernetes based container orchestrator
3. Graphical user interface

Microservice builder has an option to deploy with IBM Cloud Private. You can set it IBM Private Cloud with Microservice Builder pipeline to deploy the microservices.

**Setting up your environment**

Microservice Builder runs on a Jenkins pipeline. Basically Jenkins runs in a docker container and it is deployed on Kubernetes using helm.

This jenkins should be integrated with the Github. The repository to which you push the code shold be integrated to Microservice Builder pipeline through Github. Then only Microservice Builder will be able to pick your code.

To find instructions on how to set your Microservice Builder pipeline up, click [here](https://www.ibm.com/support/knowledgecenter/en/SS5PWC/pipeline.html).

In addition to this, you should have [kubectl CLI](https://kubernetes.io/docs/tasks/tools/install-kubectl/) installed in your system.

##### Running the application on IBM Cloud Private

Before running the application, make sure you added the docker registry secret.

- Install the jq command line JSON processor utility.

`yum install -y jq`

or

`apt install -y jq`

- log in to the IBM Cloud Private. Login as **admin** user.

- Go to **admin > Configure Client**.

- Grab the kubectl configuration commands.

- Run those commands at your end.

- Create docker-registry secret **admin.registrykey**. This allows the pipeline to access the Docker registry.

```
kubectl create secret docker-registry admin.registrykey --docker-server=https://mycluster.icp:8500 --docker-username=admin --docker-password=admin --docker-email=null
```
- Now update the service account with the image pull secret.

```
kubectl get serviceaccounts default -o json |jq  'del(.metadata.resourceVersion)' |jq 'setpath(["imagePullSecrets"];[{"name":"admin.registrykey"}])' |kubectl replace serviceaccount default -f -
```

Once you have all this, you are ready to deploy your microservice to Microservice builder on IBM Cloud private.

- Now you have your microservice builder pipeline configured.
- Push the project to the repository that is monitored by your micro service builder pipeline.
- It will automatically pick the project, build it and deploy it to IBM cloud private.

From IBM cloud private dashboard, you can access the MSB pipeline from your services. The jenkins pipeline is as follows.

<p align="center">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/MSB_jenkins/jenkinsscreeen.png">
</p>

By clicking on the name of your organization, you can find all the MSB enabled repositories here.

<p align="center">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/MSB_jenkins/mOrgscreen.png">
</p>

By clicking on the **refarch-cloudnative-wfd-menu**, you can see the below screen.

<p align="center">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/MSB_jenkins/mjenkinsentscreen.png">
</p>

If you see a blue ball there, your service is successfully built and running fine. If it red, it means you have some problems with it.

Whenever you do some changes and push the code to this repository, MSB build queue will initiate the process.

<p align="center">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/MSB_jenkins/mMSBbuildqueue.png">
</p>

Once this is done, Jenkins slave will be called.

<p align="center">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/MSB_jenkins/mjenkinsslaveinit.png">
</p>

Then the Jenkins slave will pick up the build and initiate the process.

<p align="center">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/MSB_jenkins/mjenkinsslave%20build.png">
</p>

Then the build will begin and starts running.

<p align="center">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/MSB_jenkins/mbuild.png">
</p>

Finally your service gets deployed once the build is done.

<p align="center">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/MSB_jenkins/mcompletedbuild.png">
</p>

To access your service, go to IBM Cloud Private dashboard.
- Go to **Workload > Services > wfdmenu-service** and click on it.
- You can see the service like below.

<p align="center">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/MSB_jenkins/wfdmenuservice.png">
</p>

Click on the **http** link there. You will be redirected to the Server page. Append **/WfdMenu/rest/menu** to the url and you will be able to see the Menu service.

<p align="center">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/MSB_jenkins/menuICP.png">
</p>

### References

1. [Microservice Builder](https://www.ibm.com/support/knowledgecenter/en/SS5PWC/intro-microservice-builder.html)
2. [Developer Tools CLI](https://console.bluemix.net/docs/cloudnative/dev_cli.html#developercli)
3. [IBM Cloud Private](https://www.ibm.com/support/knowledgecenter/en/SSBS6K_2.1.0/kc_welcome_containers.html)
4. [Micrprofile](https://microprofile.io/)
