@echo off

SET PROFILE=dist-minified
REM Created exploded war first. Then minify the js files. Lastly create war with ant
mvn clean compile war:exploded -DskipTests -P %PROFILE% && grunt --gruntfile src\main\webapp\Gruntfile.js dist && mvn antrun:run -DskipTests -P %PROFILE%
