#!/bin/bash
mvn --quiet exec:java -Dexec.mainClass="it.ethiclab.dbf2xml.Main" -Dexec.args="$*" 2>err
