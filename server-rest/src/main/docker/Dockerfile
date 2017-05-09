#FROM openjdk:alpine
FROM mrigor/hsqldb

#Copy 
ADD schema.sql /schema.sql
ADD data.sql /data.sql
ADD sqltool.rc /root/sqltool.rc

#Expose Default Port on which db server runs
EXPOSE 9001



