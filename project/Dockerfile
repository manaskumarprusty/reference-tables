FROM openjdk:8-jdk AS base

# Install sbt
RUN \
  mkdir /working/ && \
  cd /working/ && \
  curl -L -o sbt-1.6.2.deb https://repo.scala-sbt.org/scalasbt/debian/sbt-1.6.2.deb && \
  dpkg -i sbt-1.6.2.deb && \
  rm sbt-1.6.2.deb && \
  apt-get update && \
  apt-get install -y sbt build-essential && \
  sbt exit

FROM base AS build 
WORKDIR /app
COPY . /app/

FROM base AS prod-base
WORKDIR /app
COPY . /app/
RUN cd /app/ && \
  sbt "project reference-tables" debian:packageBin
