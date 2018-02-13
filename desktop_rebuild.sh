#!/usr/bin/env bash

GAMEDIR="/home/artem/projects/Game2D"
$GAMEDIR/gradlew desktop:dist
java -jar $GAMEDIR/desktop/build/libs/desktop-1.0.jar