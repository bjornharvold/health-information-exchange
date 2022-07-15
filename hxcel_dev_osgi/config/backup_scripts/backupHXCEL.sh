#!/bin/bash

# this will run all our backup scripts and put the result in /var/backup
for f in `ls /root/backup_scripts/utils/*.sh`
  do
    echo "Executing script: ${f}"
    /bin/sh ${f}
  done

# let's zip it all up
FILE_DATE=`date '+%b-%d-%Y'`_GaGaahTotal
# compress rar
echo "Compressing backups"
/usr/local/bin/rar a -df -m3 "/var/backup_ftp/full_nightly_backup.rar" /var/backup/ > "/var/backup_ftp/${FILE_DATE}.log"

# and run ftp script
echo "Uploading to ftp server"
/usr/bin/perl /root/backup_scripts/utils/ftp_transfer.pl

echo "Removing local backup files"
rm -rf /var/backup_ftp/*
