FROM cidarlab/cello-dnacompiler:latest

VOLUME /tmp

ARG JAR_FILE

RUN apt-get install -y --no-install-recommends gnupg procps && \
	cd /root && \
	curl -s -L https://www.mongodb.org/static/pgp/server-4.2.asc --output server-4.2.asc && \
	apt-key add server-4.2.asc && \
	echo "deb http://repo.mongodb.org/apt/debian buster/mongodb-org/4.2 main" | tee /etc/apt/sources.list.d/mongodb-org-4.2.list && \
	apt-get update && \
	apt-get install -y mongodb-org

COPY target/${JAR_FILE} /root/app.jar
COPY src/main/docker/entry.sh /root
COPY src/main/docker/cmd.sh /root

ENTRYPOINT ["/root/entry.sh"]
CMD [ "/root/cmd.sh" ]
