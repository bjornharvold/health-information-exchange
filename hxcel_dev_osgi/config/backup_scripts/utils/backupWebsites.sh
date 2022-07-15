#!/bin/bash
# file name of tar
FILE_DATE=`date '+%b-%d-%Y'`_website_dump
# compress rar
/usr/local/bin/rar a -m0 "/var/backup/${FILE_DATE}.rar" "/var/www/vhosts" > "/var/backup/${FILE_DATE}.log"

FILE_DATE=`date '+%b-%d-%Y'`_nexus_dump
# compress rar
/usr/local/bin/rar a -m0 "/var/backup/${FILE_DATE}.rar" "/opt/nexus" > "/var/backup/${FILE_DATE}.log"

FILE_DATE=`date '+%b-%d-%Y'`_atlassian_dump
# compress rar
/usr/local/bin/rar a -m0 "/var/backup/${FILE_DATE}.rar" "/opt/tomcat_atlassian" > "/var/backup/${FILE_DATE}.log"

FILE_DATE=`date '+%b-%d-%Y'`_confluence_dump
# compress rar
/usr/local/bin/rar a -m0 "/var/backup/${FILE_DATE}.rar" "/opt/confluence_files" > "/var/backup/${FILE_DATE}.log"

FILE_DATE=`date '+%b-%d-%Y'`_jira_dump
# compress rar
/usr/local/bin/rar a -m0 "/var/backup/${FILE_DATE}.rar" "/opt/jira_files" > "/var/backup/${FILE_DATE}.log"

