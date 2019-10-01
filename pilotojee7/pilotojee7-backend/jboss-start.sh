#!/bin/bash

# remove and copy new war to jboss deployments directory. Then, start jboss.
rm -rf $JOSS_HOME/standalone/deployments/piloto* & \
cp -i ./target/pilotojee7-backend.war $JOSS_HOME/standalone/deployments/ & \
$JOSS_HOME/bin/standalone.sh -c standalone-full-ha.xml