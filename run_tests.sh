#!/bin/bash
mvn org.jacoco:jacoco-maven-plugin:prepare-agent clean install -Pcoverage-per-test || exit 1
mvn sonar:sonar || exit 1
