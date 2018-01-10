### DevOps for the menu microservice on ICP

#### Install & Configure Jenkins

1. Install jenkins from the ICP catalog preferably on the default namespace since it's service account is already privileged (also, set persistence to false for simplicity). If you decide to install Jenkins on a different namespace, you will need to make its service account priviledged:

```
kubectl create rolebinding <NAMESPACE>-admin --role=admin --serviceaccount=<NAMESPACE>:default
kubectl create clusterrolebinding <NAMESPACE>-admin --clusterrole=admin --serviceaccount=<NAMESPACE>:default
```

2. Once Jenkins is installed, open the Jenkins dashboard with the appropriate user and password (instructions for retrieving these can be found at the bottom of the page for the jenkins helm release just installed previously)
3. Go to Manage Jenkins and update Jenkins to the latest version (if you know the latest version beforehand, you can specify it in the configuration when installing jenkins from the catalog)
4. Go to Manage Jenkins > Manage Plugins and download and install all the updates listed (this updates plugins like pipeline, docker, etc)
5. Go to Manage Jenkins > Configure System > Cloud > Kubernetes and increate to 1000 the Container Cap attribute.

#### Create the pipeline job

1. Download the **jenkins-cli.jar** from http://<YOUR_JENKINS_SERVER_URL>/jnlpJars/jenkins-cli.jar
2. Create the pipeline job by executing

```
java -jar jenkins-cli.jar -s http://<YOUR_JENKINS_SERVER_URL>/ create-job <JOB_NAME> < pipeline_job_creation.xml
```

where *pipeline_job_creation* is stored in this repo.

3. Go to the Jenkins dashboard and make sure your new jenkins pipeline job has been created.
4. Click on your newly created job and click on Configure.
5. Modify **release_name, docker_registry, namespace and artifactory_url** appropriately.

#### Execute your pipeline

1. Click on your pipeline job
2. Click on build with parameters
3. Make sure parameters are correct.
4. Click on build.
