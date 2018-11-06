#!/bin/bash

# remove and copy new war to jboss deployments directory. Then, start jboss.
rm -rf /home/rbssa/DevTJPE/AppServers/jboss-eap-7.0/standalone/deployments/piloto* & \
cp -i /home/rbssa/DevTJPE/workspaceReferencia/framework-tjpe-parent/pilotojee7/pilotojee7-backend/target/pilotojee7-backend.war /home/rbssa/DevTJPE/AppServers/jboss-eap-7.0/standalone/deployments/ & \
/home/rbssa/DevTJPE/AppServers/jboss-eap-7.0/bin/standalone.sh -c standalone-full-ha.xml