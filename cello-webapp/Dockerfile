FROM adoptopenjdk/openjdk8:debian

VOLUME /tmp

ARG YOSYS_VERSION
ARG JAR_FILE

RUN apt-get update
RUN apt-get install -y --no-install-recommends bison clang flex gawk gcc-multilib git graphviz libffi-dev libffi6 libreadline-dev libreadline7 pkg-config python3 tcl tcl-dev

RUN	cd /root && \
	curl -s -L https://github.com/YosysHQ/yosys/archive/yosys-$YOSYS_VERSION.tar.gz --output yosys-$YOSYS_VERSION.tar.gz && \
	tar xzf yosys-$YOSYS_VERSION.tar.gz && \
	cd yosys-yosys-$YOSYS_VERSION && \
	make && \
	make install

RUN cd /root && \
	# curl -s -L https://gitlab.cecs.anu.edu.au/u5568237/cf2cs/raw/0f758d7b06c597e30db0579695107aadff0db486/hmetis-1.5-linux/hmetis-1.5-linux.tar.gz --output hmetis-1.5-linux.tar.gz && \
	curl -s -L http://glaros.dtc.umn.edu/gkhome/fetch/sw/hmetis/hmetis-1.5-linux.tar.gz --output hmetis-1.5-linux.tar.gz && \
	tar xzf hmetis-1.5-linux.tar.gz && \
	cd hmetis-1.5-linux && \
	install hmetis /usr/bin

RUN apt-get remove -y bison clang flex gawk git libffi-dev libreadline-dev pkg-config tcl-dev
RUN apt-get install -y --no-install-recommends gnupg && \
	curl -s -L https://www.mongodb.org/static/pgp/server-4.2.asc --output server-4.2.asc && \
	apt-key add server-4.2.asc && \
	echo "deb http://repo.mongodb.org/apt/debian buster/mongodb-org/4.2 main" | tee /etc/apt/sources.list.d/mongodb-org-4.2.list && \
	apt-get update && \
	apt-get install -y mongodb-org procps && \
	update-alternatives --install /usr/bin/python python /usr/bin/python3 1
RUN apt-get install -y --no-install-recommends python3-pip python3-setuptools git && \
	pip3 install wheel matplotlib numpy sympy && \
	pip3 install dnaplotlib && \
	pip3 install git+https://github.com/tim-tx/pycello2

COPY target/${JAR_FILE} /root/app.jar
COPY docker/entry.sh /root
COPY docker/cmd.sh /root

ENTRYPOINT ["/root/entry.sh"]
CMD [ "/root/cmd.sh" ]
