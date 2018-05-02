#!/usr/bin/env bash

GAMEDIR="/home/artem/projects/TamaJumper"
$GAMEDIR/gradlew desktop:dist
java -jar $GAMEDIR/desktop/build/libs/desktop-1.3.jar