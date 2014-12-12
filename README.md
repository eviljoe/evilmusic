EvilMusic
=========

Web based music player for lossless formats.

## Supported Formats
 * [FLAC](https://xiph.org/flac)

## External Dependencies
 * `gradle`: Gradle is used to build (and currently to run) EvilMusic.  It can be obtained from https://www.gradle.org.
 * `metaflac`: The `metaflac` command is required to read meta data from FLAC files.  It can be obtained from the `flac` package using the command, `apt-get install flac`.

## Building
In the project's root directory, execute `gradle build`.

## Running
In the project's root directory, execute `gradle bootrun`.  This uses [Spring Boot](http://projects.spring.io/spring-boot) to start the server at `localhost:8080`.

**Note:** Issue [#20](https://github.com/eviljoe/evilmusic/issues/20) has been created to address how confusing starting the server is for a non-tech-savvy user.

## Configuration
The `/src/main/resources/em.properties` file can be used to configure EvilMusic.  The following are settings that can be specified within it.

**Note:** Issue [#21](https://github.com/eviljoe/evilmusic/issues/21) has been created to address how confusing the configuration file is for a non-tech-savvy user.

#### em.music_directories
A semicolon (;) separated list of directories that should be searched for music files.

`$home` is a special keyword to represent the current user's home directory.  If a directory within the list starts with `$home`, the `$home` will be replaced with the user's home directory.  For example, if the user is "eviljoe", a directory specified as `$home/Music` will actually search the `/home/eviljoe/Music` directory for music files.

*Default Value:* *none*<br/>
*Example Usage:*
 * `em.music_directories=$home/Documents/music`
 * `em.music_directories=/first/music/directory;/second/music/directory`

#### em.metaflac_command
Specifies the location of the `metaflac` command.  This command is used to read meta data from FLAC files.

*Default Value:* `metaflac`<br/>
*Example Usage*
 * `em.metaflac_command=/usr/bin/metaflac`

#### em.database.home
Specifies the location of the database.  EvilMusic uses a file system database ([Derby](http://db.apache.org/derby/)) to store information about the user's music library.

`$home` is a special keyword to represent the current user's home directory.  If this directory starts with `$home`, the `$home` will be replaced with the user's home directory.  For example, if the user is "eviljoe", a directory specified as `$home/db` will actually use `/home/eviljoe/db` as the database home directory.

*Default Value:* `$home/.evilmusic`<br/>
*Example Usage:*
 * `em.database.home=$home/db`
 * `em.database.home=/foo/bar/database-home`

#### em.database.rollback_on_close
*This value should not be used for unless you are developing for EvilMusic.*  If this property is set to `true`, the entire database will be cleared out after the EvilMusic server is no longer running.

*Default Value:* `false`<br/>
*Example Usage:*
 * `em.database.rollback_on_close=false`