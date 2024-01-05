#!/bin/bash
#
# Start script for extensions-api

PORT=8080

exec java -jar -Dserver.port="${PORT}" "suppression-api.jar"
