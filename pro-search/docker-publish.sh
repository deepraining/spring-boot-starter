#!/bin/bash

if [ -z "$DOCKER_USERNAME" ]; then
  echo "\$DOCKER_USERNAME required"
  exit 1
fi

VERSION=$(ls build/libs | sed 's/.*sbs-search-//' | sed 's/.jar$//')

echo "sbs-search version: $VERSION"

#echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker build --build-arg JAR_FILE=build/libs/sbs-search-$VERSION.jar -t $DOCKER_USERNAME/sbs-search -t $DOCKER_USERNAME/sbs-search:$VERSION .
docker images
docker push $DOCKER_USERNAME/sbs-search
