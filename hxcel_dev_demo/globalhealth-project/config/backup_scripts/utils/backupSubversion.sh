#!/bin/bash

# file name of tar
FILE_DATE=`date '+%b-%d-%Y'`_SubversionDump
# dump first
/root/backup_scripts/utils/svn-backup-dumps.py /var/svn_repo/hxcel_dev/ /var/backup > /dev/null
# compress rar
for f in `ls -d /var/backup/hxcel_dev*` 
  do
    echo "Backing up directory ${f}"
    /usr/local/bin/rar a -df -m0 "/var/backup/${FILE_DATE}_hxcel_dev.rar" ${f} > "/var/backup/${FILE_DATE}.log"
  done

/root/backup_scripts/utils/svn-backup-dumps.py /var/svn_repo/hxcel_bus/ /var/backup > /dev/null
# compress rar
for f in `ls -d /var/backup/hxcel_bus*`
  do
    echo "Backing up directory ${f}"
    /usr/local/bin/rar a -df -m0 "/var/backup/${FILE_DATE}_hxcel_bus.rar" ${f} > "/var/backup/${FILE_DATE}.log"
  done


/root/backup_scripts/utils/svn-backup-dumps.py /var/svn_repo/java_commons/ /var/backup > /dev/null
# compress rar
for f in `ls -d /var/backup/java_commons*`
  do
    echo "Backing up directory ${f}"
    /usr/local/bin/rar a -df -m0 "/var/backup/${FILE_DATE}_java_commons.rar" ${f} > "/var/backup/${FILE_DATE}.log"
  done

/root/backup_scripts/utils/svn-backup-dumps.py /var/svn_repo/communicator/ /var/backup > /dev/null
# compress rar
for f in `ls -d /var/backup/communicator*`
  do
    echo "Backing up directory ${f}"
    /usr/local/bin/rar a -df -m0 "/var/backup/${FILE_DATE}_communicator.rar" ${f} > "/var/backup/${FILE_DATE}.log"
  done

/root/backup_scripts/utils/svn-backup-dumps.py /var/svn_repo/flex_commons/ /var/backup > /dev/null
# compress rar
for f in `ls -d /var/backup/flex_commons*`
  do
    echo "Backing up directory ${f}"
    /usr/local/bin/rar a -df -m0 "/var/backup/${FILE_DATE}_flex_commons.rar" ${f} > "/var/backup/${FILE_DATE}.log"
  done

/root/backup_scripts/utils/svn-backup-dumps.py /var/svn_repo/chatter_bubble/ /var/backup > /dev/null
# compress rar
for f in `ls -d /var/backup/chatter_bubble*`
  do
    echo "Backing up directory ${f}"
    /usr/local/bin/rar a -df -m0 "/var/backup/${FILE_DATE}_chatter_bubble.rar" ${f} > "/var/backup/${FILE_DATE}.log"
  done

