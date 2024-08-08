# Bot to post current weather to Mastodon (Jive Practice Month Project)

To run please:
- clone project
- create environment variable named `Mastodon_Access_Token` in IntelliJ:
  - make sure that `Main` is displayed in the Run/Debug Configuration menu
  - select `Edit Configurations...` in that menu
  - create the environment variable and set the access token as value
- run `Main` to post the current weather of all active cities to Mastodon :-)

## Run as command line application (e.g. cronjob)

- Create an Uberjar (a jar containing all dependencies)
  - This can be done with [Maven Shade](https://maven.apache.org/plugins/maven-shade-plugin)
  - Some people prefer to avoid Uber jars and write extensive start scripts
- Run `mvn package` to test if the Uber jar was successfully created
  - You can rename the .jar file to .zip to open it's contents (view with your favorite app)
  - If this does not work, use Linux (WSL on Windows): $> unzip <my_jar_file>.jar
  - Or use: expand-archive. [Details](https://stackoverflow.com/questions/27768303/how-to-unzip-a-file-in-powershell)
  - Verify all dependencies are available in the Uber jar
- Write a shell script to start the Jar
  - `java -cp ./target/mastodonweatherbot-1.0-SNAPSHOT.jar com.acn.jive.mastadonweatherbot.Main`
    - java -> Starts Java :)
    - -cp -> Says: now comes the classpath. Classpath is where Java looks for classes/resources
- Export environment variables.
  - Export means: create the variable
    - export db_user=root
    - export db_pass=secret
    - export Mastodon_Access_Token=<your token>
- Start the shell script:
  - ./start-bot.sh