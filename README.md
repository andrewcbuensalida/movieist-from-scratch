https://www.youtube.com/watch?v=5PdEmeopJVQ
https://lucid.app/lucidchart/82ae072f-03f9-4abf-bd32-babad0358ee6/edit?viewport_loc=-168%2C113%2C1673%2C870%2C0_0&invitationId=inv_5794a1c7-b764-4235-9e32-f0384d639182

To setup mongodb:
create a project called movieist in mongodb atlas
create a cluster called Cluster0
Connect atlas(which is online) to compass(which is local)
create a collection (named movies, should match Movie.java) and database in compass and it will automatically create it
in atlas.
Import movies.json in _data folder into compass and it will automatically add the data to atlas.

For some reason it is getting an empty array when getting movies, even though I see that there are movies in atlas. This
is because collection name didn't match the first time.

Even if there is no reviews collection yet in mongodb, creating a review in postman will create the collection.

=========================================

Deploying to elastic beanstalk:
In application.properties, add server.port=5000 because that's what elastic beanstalk listens to.
Environment variables PORT and SERVER_PORT and mongodb passwords don't matter in eb. Still worked even after rebuilding and restarting in eb.
Using maven, build a jar file
To build jar file https://www.tutorialworks.com/intellij-maven-create-jar/#:~:text=To%20create%20a%20JAR%20file%20from%20a%20Maven%20project%20in,written%20to%20the%20target%2F%20directory.
View > Tool Windows > Maven > Expand <Project Folder> > Lifecycle > package
Sometimes have to do clean before package so that it won't error.
Make sure everything is the same version, so File > Settings > Build, Execution, Deployment > Compiler > Java Compiler > Target Byte Code Version is the same with File > Project Structure > Project > SDK is the same with pom.xml <properties>
<java.version>21</java.version>
</properties>

This will generate a .jar file in the Target folder.

The .env will (not) be included in the jar?

To run .jar,
java -jar path/to/jar

Getting a 502 bad gateway error with the elastic beanstalk platform 64bit Amazon Linux 2023 v4.1.2 running Corretto 17.
Trying 64bit Amazon Linux 2 v3.6.2 running Corretto 17 instead, because maven version is apache-maven-3.9.5 as seen in maven-wrapper.properties.
In ec2 of elastic beanstalk (eb), it says the version is openjdk 17.0.9.

To get pem to work, right click, edit security, owner has to be Andre.
To ssh,
ssh -i "january-8-2024-key-pair.pem" ec2-user@ec2-52-9-184-2.us-west-1.compute.amazonaws.com
Have to run cmd as admin.
Permission entries could have Administrator with read & execute access.

To check if nginx is running in linux,
sudo systemctl status nginx
To stop nginx
sudo systemctl stop nginx

In integrated terminal in intellij ubuntu 22.04 (aka wsl?), running jps says not found but can be installed with 
apt install openjdk-17-jdk-headless
Try jps to see what java processes are running.
Now when I run jps, everytime it gives a different number, like 2886 Jps, or 2866 Jps. This is regardless if I'm running or working.
java -jar movieist-0.0.1-SNAPSHOT.jar
Same thing when I do Jps in eb ec2.
Another way to check running java processes is
ps aux | grep java
This produces this output locally, and the process id always changes to plus 2 every time if you don't wait too long, but if you do, it could add more.
root        2961  0.0  0.0   4024  2028 pts/0    S+   08:26   0:00 grep --color=auto java
If it is run in ec2 eb when it's not working, output is
ec2-user 15362  0.0  0.0 119420   924 pts/0    S+   16:29   0:00 grep --color=auto java
and the pid changes by a few hundred everytime.
This is regardless if I'm running
java -jar movieist-0.0.1-SNAPSHOT.jar
If it is working, output of ps aux | grep java should be
webapp   22033  5.7 17.8 2958816 172844 ?      Ssl  18:09   0:15 java -jar application.jar
ec2-user 24808  0.0  0.1 119420   988 pts/0    S+   18:13   0:00 grep --color=auto java
Notice the application.jar, which eb renamed from your jar.

In task manager, there's no PID 22784 even though when running java -jar movieist-0.0.1-SNAPSHOT.jar, it says that is the PID.

In command prompt, running this will get you the running jar
tasklist /v /FI "IMAGENAME eq java.exe"

Reading https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/applications-sourcebundle.html and it says to upload a zip, so trying that. zipping the jar. Still didn't work.

Trying to just copy the jar from local to eb ec2 with this command in cmd as admin. No need.
scp -i "january-8-2024-key-pair.pem" C:\swe\code\movieist-from-scratch\target\movieist-0.0.1-SNAPSHOT.jar ec2-user@52.9.184.2:/home/ec2-user
Now doing
java -jar movieist-0.0.1-SNAPSHOT.jar
and it says
Exception in thread "main" java.lang.UnsupportedClassVersionError: com/movieistfromscratch/movieist/MovieistApplication has been compiled by a more recent version of the Java Runtime (class file version 65.0), this version of the Java Runtime only recognizes class file versions up to 61.0

Tried to update java version in eb ec2 but couldn't change it. The way I Tried was:
sudo yum update
https://docs.aws.amazon.com/corretto/latest/corretto-20-ug/generic-linux-install.html#rpm-linux-install-instruct
sudo rpm --import https://yum.corretto.aws/corretto.key
sudo curl -L -o /etc/yum.repos.d/corretto.repo https://yum.corretto.aws/corretto.repo
sudo yum install -y java-20-amazon-corretto-devel
To check if it installed
java -version
Still says
openjdk 17.0.9 2023-10-17 LTS

Might have to make local java version 17 (correct), or deploy some other way maybe through docker or ec2.
Change java.version in pom.xml to 17. This automatically changes the File > Settings > Build, Execution, Deployment > Compiler > Java Compiler > Target Byte Code Version. Also change File > Project Structure > Project > SDK to 17.
https://www.happycoders.eu/java/how-to-switch-multiple-java-versions-windows/ to change java version on windows 11. Have to change it in start > environment variables.
Finally, it is working. version 17 baby. not 21 because stupid elastic beanstalk isn't up-to-date as of the moment.
