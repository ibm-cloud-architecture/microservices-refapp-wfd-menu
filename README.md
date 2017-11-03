# Microservices Reference Application - What's For Dinner

## Menu Service - MicroProfile

This repository contains the **Java MicroProfile** implementation of the **Menu Service** which is a part of microservice-based reference application called **What's For Dinner** that can be found in https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd

<p align="center">
  <a href="https://microprofile.io/">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/microprofile_small.png">
  </a>
</p>

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

1. Build the docker image.

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
2. Before running the docker images, make sure [Appetizer](https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd-appetizer/tree/microprofile), [Dessert](https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd-dessert/tree/microprofile) and [Entree](https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd-entree/tree/microprofile) services are running. If you are running all the services locally on your system, you need to modify the ports. 

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

3. Run the docker image.

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
4. Now, view the REST endpoint at http://localhost:9080/WfdMenu/rest/menu.

<p align="center">
    <img src="https://github.com/ibm-cloud-architecture/refarch-cloudnative-wfd/blob/microprofile/static/imgs/MenuScreen.png">
</p>

   Access URL : `http://<HOST>:<PORT>/<WAR_CONTEXT>/<APPLICATION_PATH>/<ENDPOINT>`

5. Once you make sure the application is working as expected, you can come out of the process. You can do this by pressing Ctrl+C on the command line where the server was started.

6. You can also remove the container if desired. This can be done in the following way.

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



