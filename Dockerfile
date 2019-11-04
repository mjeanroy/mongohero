##
# The MIT License (MIT)
#
# Copyright (c) 2019 Mickael Jeanroy
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in
# all copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
# THE SOFTWARE.
##

# ----------------------------------------------------------- #
# First stage: build the application                          #
# ----------------------------------------------------------- #

FROM debian:stretch AS mongohero-built

ENV NODE_VERSION 12.13.0
ENV NVM_DIR /usr/local/nvm

RUN rm /bin/sh && ln -s /bin/bash /bin/sh
RUN apt-get update && apt-get install -y maven curl git

# Install openjdk, nvm & node
RUN apt-get install -y --force-yes openjdk-8-jdk ca-certificates-java
RUN curl --silent -o- https://raw.githubusercontent.com/creationix/nvm/v0.31.2/install.sh | bash
RUN source $NVM_DIR/nvm.sh && nvm install $NODE_VERSION && nvm alias default $NODE_VERSION && nvm use default

ENV NODE_PATH $NVM_DIR/v$NODE_VERSION/lib/node_modules
ENV PATH $NVM_DIR/versions/node/v$NODE_VERSION/bin:$PATH

RUN mvn -version
RUN node --version
RUN npm --version

COPY . /mongohero
WORKDIR /mongohero
RUN ls -la && mvn clean install -DskipTests

# ----------------------------------------------------------- #
# Second stage: build the image application                   #
# ----------------------------------------------------------- #

FROM openjdk:8-jre-alpine AS mongohero
LABEL author="Mickael Jeanroy"
COPY --from=mongohero-built /mongohero/mongohero-webapp/target/mongohero.jar /mongohero.jar
EXPOSE 8080
CMD ["java", "-jar", "/mongohero.jar"]
