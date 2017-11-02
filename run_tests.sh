#!/bin/bash
mvn -Djdbc.drivers=org.sqlite.JDBC org.jacoco:jacoco-maven-plugin:prepare-agent clean install -Pcoverage-per-test || exit 1
mvn sonar:sonar || exit 1
