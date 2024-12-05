FROM mysql:5.7
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=elective_management
COPY create.sql /docker-entrypoint-initdb.d/
EXPOSE 3306
