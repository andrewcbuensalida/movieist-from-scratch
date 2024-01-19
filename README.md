https://www.youtube.com/watch?v=5PdEmeopJVQ

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
In application.properties, add server.port=5000
Using maven, build a jar file
To build jar file https://www.tutorialworks.com/intellij-maven-create-jar/#:~:text=To%20create%20a%20JAR%20file%20from%20a%20Maven%20project%20in,written%20to%20the%20target%2F%20directory.
View > Tool Windows > Maven > Expand <Project Folder> > Lifecycle > package
Make sure everything is the same version, so File > Settings > Build, Execution, Deployment > Compiler > Java Compiler > Target Byte Code Version is the same with File > Project Structure > Project > SDK is the same with pom.xml <properties>
<java.version>21</java.version>
</properties>

This will generate a .jar file in the Target folder.
To run .jar,
java -jar path/to/jar