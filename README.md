# CINEMA

Before running the application you should have Postgres and PgAdmin installed<br />

### How to run
1. Create the database with name - **_cinema_db_**
2. **git clone https://github.com/i-Gold/cinema.git** <br />
3. Go to the **_application.yml_** file in the project and change *username* and *password* to your Postgres user's credentials
4. **./gradle clean build** - for Windows from command prompt
5. cd .\src\main\java\com\geniusee\cinema
6. javac CinemaApplication.java