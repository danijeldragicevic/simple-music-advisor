# Simple Music Advisor App
Java desktop application that access Spotify API and show requested information.
Application is able to:
- authenticate against Spotify API using OAuth protocol,
- show a list of new albums with artists and links on Spotify,
- show list of Spotify-featured playlists with their links fetched from API,
- show a list of all available categories on Spotify (just their names), and
- show playlists, grouped by the category names, with their links fetched from API.

# Technology
- Java 11
- Maven 3.6.3

# Create Spotify App
To be able to authenticate against Spotify API first we must go to the [Spotify Web site for developers](https://developer.spotify.com/) and create our application. We can give it arbitrary name and description and put our localhost address as redirect URI: "http://localhost:8080". We must use exactly this URL and port because our Music Advisor app depends on it.

# To run application:
To compile and build, navigate to the project root directory and run following command:
> mvn clean package

After we have successfully built the project, we can run application with the following command:
> mvn exec:java -Dexec.mainClass="advisor.Main"

# Examples
The symbol **>** represents the user input.

**Example 1:** Authentication step <br>
To authenticate against Spotify API first we have to type **auth** command. <br>
After we get authentication link, application will wait till we do manual click on it and approve access to our Spotify App (the one that is created in the previous step).
```
> auth
auth
use this link to request the access code:
https://accounts.spotify.com/authorize?client_id=6c4fbf0c811242f0bd6c40f85bcbba17&redirect_uri=http://localhost:8080&response_type=code
waiting for code...
code received
Making http request for access_token...
response:
{"access_token":"BQDLJmcfXvTv2ctGLJuP2FSpkNCplvlBL5JbIb-opBWq2sv8gjUAa2HrNxGhxkILEdgohj34oN1uM1I0wqxA3EyIMstsq1QY4_jAAsY2u-Im5sskm9u-qIA80J2jecP3CuhTWm3G41A6OQab-d0D6o8RmdxvzS4THbOhNy9kh-LmmcvjPxxejuAhxwZzhaXz0DyNSA","token_type":"Bearer","expires_in":3600,"refresh_token":"AQC1PfNZhsTYC-c64xj5WimkTu_CAZgWt2rgDD_KublNL2fcyBgrBpRpn-4_vjjU7mBzJQpozFMPJ9XSKN4hYmiSYi1ZYJkHfPjSoCe7mFJQQCkqb7DrloiDQ6nZDVhb_E0"}
Success!
```
![Screenshot 2023-06-15 at 15 58 09](https://github.com/danijeldragicevic/simple-music-advisor/assets/82412662/8840cf89-18e6-4620-8386-627aede68c5b)


**Example 2:** Main menu operations <br>
To show a list of new albums:
```
> new
Harry's House
[Harry Styles]
https://open.spotify.com/album/5r36AJ6VOJtp00oxSkBZ5h

emotionally unavailable
[Cat Burns]
https://open.spotify.com/album/3IdEptw0LPQv9qNLbxkdAU

So Far So Good
[The Chainsmokers]
https://open.spotify.com/album/1CxCEPIZbaE28qUDW4wN0t

Dance Fever
[Florence + The Machine]
https://open.spotify.com/album/4ohh1zQ4yybSK9FS7LLyDE

ESQUEMAS
[Becky G]
https://open.spotify.com/album/7eC4wtMG1I2Jtk4FDWbkKC

---Page 1 OF 9---
```

To show featured playlists:
```
> featured
Rock Classics
https://open.spotify.com/playlist/37i9dQZF1DWXRqgorJj26U

Work From Home
https://open.spotify.com/playlist/37i9dQZF1DWTLSN7iG21yC

Jazz Rap
https://open.spotify.com/playlist/37i9dQZF1DX8Kgdykz6OKj

Mood Booster
https://open.spotify.com/playlist/37i9dQZF1DX3rxVfibe1L0

The Black Power Mixtape 1967–1975
https://open.spotify.com/playlist/37i9dQZF1DX94QVAxB7Dum

---Page 1 OF 2---
```

To show available categories:
```
> categories
Top Lists
Pop
EQUAL
Mood
Decades
---Page 1 OF 8---
```

To show playlists grouped by the category names:
```
> playlists Mood
A Perfect Day
https://open.spotify.com/playlist/37i9dQZF1DWSXBu5naYCM9

*end credits
https://open.spotify.com/playlist/37i9dQZF1DX2DKrE9X6Abv

goosebumps
https://open.spotify.com/playlist/37i9dQZF1DXdl6IPOySdX4

Energy Booster: Dance
https://open.spotify.com/playlist/37i9dQZF1DX35X4JNyBWtb

DOPAMINE
https://open.spotify.com/playlist/37i9dQZF1DX0E9XMGembJo

---Page 1 OF 21---
```

To go on a next page of some menu:
```
> next
Energy Booster: Pop
https://open.spotify.com/playlist/37i9dQZF1DX0vHZ8elq0UK

my life is a movie
https://open.spotify.com/playlist/37i9dQZF1DX4OzrY981I1W

Energy Booster: Hip-Hop
https://open.spotify.com/playlist/37i9dQZF1DWZixSclZdoFE

slowed and reverbed
https://open.spotify.com/playlist/37i9dQZF1DX0h2LvJ7ZJ15

Levitate
https://open.spotify.com/playlist/37i9dQZF1DWVY5eNJoKHd2

---Page 2 OF 21---
```

To go on a previous page of some menu:
```
> prev
A Perfect Day
https://open.spotify.com/playlist/37i9dQZF1DWSXBu5naYCM9

*end credits
https://open.spotify.com/playlist/37i9dQZF1DX2DKrE9X6Abv

goosebumps
https://open.spotify.com/playlist/37i9dQZF1DXdl6IPOySdX4

Energy Booster: Dance
https://open.spotify.com/playlist/37i9dQZF1DX35X4JNyBWtb

DOPAMINE
https://open.spotify.com/playlist/37i9dQZF1DX0E9XMGembJo

---Page 1 OF 21---
```

To exit from the application:
```
> exit
```

# Licence
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
