#sql base image used
FROM mysql:8.0.33

#create script copied to the docker-entrypoint-initdb.d folder to ensure it runs on startup
COPY ./create.sql /docker-entrypoint-initdb.d/ 

EXPOSE 3306
