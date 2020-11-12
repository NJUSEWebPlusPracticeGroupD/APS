#!/usr/bin/env bash
#!/bin/bash
PROCESS_NAME=aps-0.0.1-SNAPSHOT.jar
kill -9 $(ps -eaf|grep -i "java .*${PROCESS_NAME}"|grep -v 'grep'|awk '{print $2}')
echo "Closing $PROCESS_NAME"
git pull
mvn clean package -Dmaven.test.skip=true
nohup java -jar target/$PROCESS_NAME &
echo "$PROCESS_NAME Started"