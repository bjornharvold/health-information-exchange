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
FILE_DATE=`date '+%b-%d-%Y'`_GaGaahDBDump
# dump db to tar
pg_dump --file="$FILE_DATE.tar" -Ft -b -c -o --port=5432 --username=postgres hxcel
# compress tar in rar
/usr/local/bin/rar a -df "$FILE_DATE.rar" "$FILE_DATE.tar" > db_rar.log

