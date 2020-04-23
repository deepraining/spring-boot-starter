#!/bin/bash

if [ -z "$DOCKER_USERNAME" ]; then
  echo "\$DOCKER_USERNAME required"
  exit 1
fi

VERSION=$(ls build/libs | sed 's/.*sbs-admin-//' | sed 's/.jar$//')

echo "sbs-admin version: $VERSION"

#echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker build --build-arg JAR_FILE=build/libs/sbs-admin-$VERSION.jar -t $DOCKER_USERNAME/sbs-admin -t $DOCKER_USERNAME/sbs-admin:$VERSION .
docker images
docker push $DOCKER_USERNAME/sbs-admin
