#!/bin/bash
postmaster -D /usr/local/pgsql/data/ -i > /usr/local/pgsql/pgsql_log.txt 2>&1 &
