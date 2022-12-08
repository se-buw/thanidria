# Thanidria
![gradle build](https://github.com/se-buw/thanidria/actions/workflows/gradle.yml/badge.svg)

little doc describing what we did, hope it helps a bit!
# implemented functions
(App.java ist bloß noch vom gradle template und wird nicht benutzt)
-Audio Dateien .wav durch 10 angeklickte Punkte im Window wird gerendert. Diese Punkte werden in einer Liste gespeichert und invertiert, weil Ursprung Koordinatensystem oben links ist und nicht unten.
Dann werden durch die gezeichneten Linien .wav Dateien erstellt und diese kombiniert durch combineAudio. Die wavAppended3.wav Datei ist dann der ganze gezeichnete Sound.

-Zeichnen auf einer Leinwand ist mit Linien möglich
-Frequenzen und Noten sind auf der Leinwand zu sehen

# java-gradle-template
A template java project using gradle and basic dependencies

It basically follows the tutorial described here: https://docs.gradle.org/current/samples/sample_building_java_applications.html

I have added an example dependency that reads a CSV file (many of you look like they want something like that). A tutorial is here: https://www.callicoder.com/java-read-write-csv-file-apache-commons-csv/

There are different options for reading input from the console: https://www.geeksforgeeks.org/ways-to-read-input-from-console-in-java/
