#!/bin/bash

PASTE_SECRET_ID=e6qh5b8uvsdkub755o62
PROPERTIES_FILE=lockbox.properties

java -jar properties-fetcher.jar $YC_OAUTH_TOKEN $PASTE_SECRET_ID $PROPERTIES_FILE
java -jar paste.jar --spring.config.import=$PROPERTIES_FILE > logs.log &
echo $! > paste-backend.pid
