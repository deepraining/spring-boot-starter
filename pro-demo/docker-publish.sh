#!/bin/bash

if [ -z "$DOCKER_USERNAME" ]; then
  echo "\$DOCKER_USERNAME required"
  exit 1
fi

VERSION=$(ls build/libs | sed 's/.*sbs-demo-//' | sed 's/.jar$//')

echo "sbs-demo version: $VERSION"

#echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker build --build-arg JAR_FILE=build/libs/sbs-demo-$VERSION.jar -t $DOCKER_USERNAME/sbs-demo -t $DOCKER_USERNAME/sbs-demo:$VERSION .
docker images
docker push $DOCKER_USERNAME/sbs-demo
