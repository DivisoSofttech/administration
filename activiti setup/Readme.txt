 ################################################feel free to edit#################################
 author [sooraj pn]

the below rar file is used to extract the sms senting class code
step 1:compile the  file then put the com folder into ur tomcat webapps\activiti-rest\WEB-INF\classes

step 2:deploy bpm  file in activiti in this url =>http://ec2-52-15-174-35.us-east-2.compute.amazonaws.com:8080/activiti-rest/service/repository/deployments

use post method and attach bpm xml file in request body as File 

step 3:after you post the file u get an response with id use the id an call the get url=>
http://ec2-52-15-174-35.us-east-2.compute.amazonaws.com:8080/activiti-rest/service/repository/process-definitions?deploymentId=40

you get an name doning this step take the id it is the process-definition id that you need to put in yout microservice configuration
 

to swagger url of activiti is=http://ec2-52-15-174-35.us-east-2.compute.amazonaws.com:8080/activiti-rest/docs/activiti.json

#note activiti is only connect with my sql version 5.7 an d 5.6

 
