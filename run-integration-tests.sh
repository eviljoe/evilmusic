#!/bin/bash
# A script that will run the integration EvilMusic tests.

gradle configIntTest bootRun &
pid=$!

sleep 20
gradle intTest

kill -term $!
