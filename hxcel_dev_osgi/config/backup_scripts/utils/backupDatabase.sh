#!/bin/bash
#General options:
#  -f, --file=FILENAME      output file name
#  -F, --format=c|t|p       output file format (custom, tar, plain text)
#  -b, --blobs              include large objects in dump
#  -c, --clean              clean (drop) schema prior to create
#  -C, --create             include commands to create database in dump
#  -o, --oids               include OIDs in dump
#  -p, --port=PORT          database server port number
#  -U, --username=NAME      connect as specified database user

# file name of tar
globalhealth=`date '+%b-%d-%Y'`_globalhealth_db_dump
# dump globalhealth db to tar
/usr/local/pgsql/bin/pg_dump --file="/var/backup/${globalhealth}.tar" -Ft -b -c -o --port=5432 --username=postgres globalhealth_db
# compress tar in rar
/usr/local/bin/rar a -df -m0 "/var/backup/${globalhealth}.rar" "/var/backup/${globalhealth}.tar" > "/var/backup/${globalhealth}.log"
