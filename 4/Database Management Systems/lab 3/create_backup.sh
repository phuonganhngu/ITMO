#!/bin/bash
rman @/u01/dbms/lab3/create_backup.rman


crontab -l 0 * * * * oracle/u01/dbms/lab3/create_backup.sh