#! /usr/bin/env bash

gradle bootJar
docker-compose build && docker-compose up