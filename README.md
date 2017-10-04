# dbf2xml

    ./run.sh FILE.DBF [FILE.DBT] > FILE.xml

# dependency

    git clone git@github.com:montoyaedu/javadbf.git
    cd javadbf
    mvn clean install -Dgpg.skip=true -Dadditionalparam=-Xdoclint:none
