#LapReader
=========

LapReader project for JavaLabra-course. LapReader is developed for reading and archiving lapdata generated in radio controlled model car racing. Current features include saving and displaying laptimes. The program also calculates result, average laptime and finds best lap from heat. The application is only tested with Ubuntu Linux, but it should be possible to use it with windows.

##Compile
Run 'mvn install' in the LapReader directory

##Usage
Run compiled jar, like 'java -jar LapReader.jar'

##Known issues
* App doesn't check database schema
* Windows that doesn't update automatically causes problems sometimes
* Database errors caused by action listener are not handled nicely
* Sometimes wrong error message when adding heat

More info in dokumentaatio/testausdokumentaatio.md

##Future features
* Support for different Tracks
* Support for multiple Cars
* More parsers
* Compare two or more heats
* Search
* Automatically update open windows
