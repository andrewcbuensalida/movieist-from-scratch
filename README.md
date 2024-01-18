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