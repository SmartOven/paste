#!/bin/bash

PASTE_SECRET_ID=e6qqq8bchnjmj39c7b26
PROPERTIES_FILE=lockbox.properties

java -jar properties-fetcher.jar $YC_OAUTH_TOKEN $PASTE_SECRET_ID $PROPERTIES_FILE
java -jar paste.jar --spring.config.import=$PROPERTIES_FILE
