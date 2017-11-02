#!/bin/bash
java -cp target/dbf2xml-1.0.0-SNAPSHOT.jar:$HOME/.m2/repository/com/github/albfernandez/javadbf/1.6.2-SNAPSHOT/javadbf-1.6.2-SNAPSHOT.jar it.ethiclab.dbf2xml.Main $*
