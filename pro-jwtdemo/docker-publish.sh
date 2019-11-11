#!/bin/bash

if [ -z "$DOCKER_USERNAME" ]; then
  echo "\$DOCKER_USERNAME required"
  exit 1
fi

VERSION=$(ls build/libs | sed 's/.*sbs-jwtdemo-//' | sed 's/.jar$//')

echo "sbs-jwtdemo version: $VERSION"

#echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker build --build-arg JAR_FILE=build/libs/sbs-jwtdemo-$VERSION.jar -t $DOCKER_USERNAME/sbs-jwtdemo -t $DOCKER_USERNAME/sbs-jwtdemo:$VERSION .
docker images
docker push $DOCKER_USERNAME/sbs-jwtdemo
