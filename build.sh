#!/bin/bash

set -euxo pipefail

docker build . -t axreng/backend

#docker run -p 4567:4567 -v "$HOME/.m2":/root/.m2 --rm axreng/backend
#docker run -e BASE_URL=http://hiring.axreng.com/ -p 4567:4567 -v "$HOME/.m2":/root/.m2 --rm axreng/backend
docker run -e BASE_URL=http://hiring.axreng.com/ -e MAX_RESULTS=100 -p 4567:4567 -v "$HOME/.m2":/root/.m2 --rm axreng/backend
#docker run -e BASE_URL=http://hiring.axreng.com/ -e MAX_RESULTS=100 -p 4567:4567 --rm axreng/backend