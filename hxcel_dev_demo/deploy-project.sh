#!/bin/sh
# ----------------------------------------------------------------------
# Deploys globalhealth-project:
#   1. ask whether building to staging or production
#   2. ask whether deploying from trunk / branch / tag
#   3. ask whether using a revision number or latest to build from (only applicable for branch and trunk)
#   4. switch / update
#   5. build with maven 2
#   6. shut down application server
#   7. remove old webapp directory
#   8. copiy new war to application server
#   9. start application server again
# Author: Bjorn Harvold
# ----------------------------------------------------------------------
# default variables
ENVIRONMENT="staging"
SVN_TAG=""
SVN_REVISION=""
SVN_LOCATION=""
SVN_URL=""
SVN_MERGE="n"
SVN_TAG_NAME=""
SVN_CHECKOUT_NAME=""
UPDATE_MEDIA="N";
TOMCAT_INSTALL=/opt/tomcat_builds
SVN_BIN=/usr/local/bin
SVN_BASE_URL="file:///var/svn_repo/hxcel_dev"
ENCRYPTED_PASSWORD=AzSTLHzl50ZoI

echo "Commencing Project Globalhealth deployment..."

echo -n "Please enter deployer password: "
read -e USER_PASSWORD

USER_ENCRYPTED_PASSWORD=$(perl -e 'print crypt($ARGV[0], "Az")', $USER_PASSWORD)

if [ $ENCRYPTED_PASSWORD != $USER_ENCRYPTED_PASSWORD ] ; then
    echo "You have entered an incorrect password!"
    exit
fi

#   1. ask whether building to staging or production
echo -n "What environment are you building for? 1. staging, 2. production. [ 1 | 2 ]: "
read -e DEP_ENV

case $DEP_ENV in
    "1") ENVIRONMENT="staging";;
    "2") ENVIRONMENT="production";;
    *) echo "You must type either 1 for staging or 2 for production. Exiting!"
       exit 1;;
esac

echo "Environment set to: ${ENVIRONMENT}"

#   2. ask whether deploying from trunk / branch / tag
echo -n "Where are you building from Subversion? [trunk | branch | tag]: "
read -e SVN_LOCATION

case $SVN_LOCATION in
    "trunk")
        echo "Building from trunk..."
        SVN_URL="$SVN_BASE_URL/trunk"
        ;;
    "branch")
        echo "Building from branch..."
        echo -n "Enter branch name: (e.g. 1.1.x) "
        read -e SVN_CHECKOUT_NAME
        echo "Building from branch with name $SVN_CHECKOUT_NAME"
        SVN_URL="$SVN_BASE_URL/branches/$SVN_CHECKOUT_NAME"
        ;;
    "tag")
        echo "Building from tag..."
        echo -n "Enter tag name: (e.g. 1.1.1) "
        read -e SVN_CHECKOUT_NAME
        echo "Building from tag with name $SVN_CHECKOUT_NAME"
        SVN_URL="$SVN_BASE_URL/tags/$SVN_CHECKOUT_NAME"
        ;;
    *) echo "You must type either trunk, branch or tag. Exiting!"
       exit 1;;
esac

#   3. ask whether using a revision number or latest to build from (only applicable for branch and trunk)
if [ "$SVN_LOCATION" != "tag" ] ; then
    # now determine which svn revision to build from
    echo -n "What Subversion revision do you want to build from? [e.g. 481] (default: LATEST): "
    read -e SVN_REVISION

    if [ $SVN_REVISION ] ; then
        SVN_TAG="-r $SVN_REVISION"
        echo "Building from Subversion tag: ${SVN_REVISION}"
    fi
fi

#   5. switch to correct url within subversion
# UPDATE HERE
echo "Updating to latest from Subversion into current directory..."
echo "Executing: $SVN_BIN/svn $SVN_TAG switch $SVN_URL"
$SVN_BIN/svn $SVN_TAG switch $SVN_URL

# BUILD HERE
echo "Building clean project using Maven 2..."

# Package project here
/opt/maven/bin/mvn clean package -Dmaven.test.skip=true -Denvironment=$ENVIRONMENT

echo "Building clean project using Maven 2 complete!"

# REBOOT TOMCAT HERE
echo "Shutting down Tomcat..."
/etc/init.d/tomcat_builds stop
echo "Shutting down Tomcat complete!"

# DEPLOY HERE
echo "Deploying new war to Tomcat..."

# remove the old stuff completely
echo "Removing old files"
rm -rf $TOMCAT_INSTALL/globalhealth/* $TOMCAT_INSTALL/work/*

# unzip war into globalhealth-web directory under webapps
echo "Exploding war manually"
cd $TOMCAT_INSTALL/globalhealth
jar xvf /home/bjorn/svn/hxcel/globalhealth-project/globalhealth-web/target/globalhealth.war

echo "Deploying new war to Tomcat complete!"

echo "Starting up Tomcat with log tailing enabled (Press Ctrl-C to exit tail)..."
/etc/init.d/tomcat_builds start && /usr/bin/tail -f $TOMCAT_INSTALL/logs/catalina.out
