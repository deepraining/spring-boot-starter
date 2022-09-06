#!/bin/bash

if [ -z "$DOCKER_USERNAME" ]; then
  echo "\$DOCKER_USERNAME required"
  exit 1
fi

VERSION=$(ls build/libs | sed 's/.*sbs-front-//' | sed 's/.jar$//')

echo "sbs-front version: $VERSION"

#echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker build --build-arg JAR_FILE=build/libs/sbs-front-$VERSION.jar -t $DOCKER_USERNAME/sbs-front -t $DOCKER_USERNAME/sbs-front:$VERSION .
docker images
docker push $DOCKER_USERNAME/sbs-front
