# Simple Music Advisor App
Java desktop application that access Spotify API and show requested information.
Application is able to:
- authenticate against Spotify API using OAuth protocol,
- show a list of new albums with artists and links on Spotify,
- show list of Spotify-featured playlists with their links fetched from API,
- show a list of all available categories on Spotify (just their names), and
- show list of Playlist grouped by the category names, fetched from the API.

# Technology
- Java 11
- Maven 3.6.3

# Create Spotify App
To be able to authenticate against Spotify API first we must go to the [Spotify Web site for developers](https://developer.spotify.com/) and create our application. We can give it arbitrary name and description and put our localhost address as redirect URI: "http://localhost:8080". Our Music Advisor app depends on it.
Once we are done, our Spotify App Configuration will look something like this: <br>
screen-shot here...

# To run application:
To compile files, navigate to the project root directory and run following command:
> mvn clean package

After we have successfully built the project, we can run application with the following command:
> mvn exec:java -Dexec.mainClass="advisor.Main"

# Examples
The symbol **>** represents the user input.

**Example 1:**