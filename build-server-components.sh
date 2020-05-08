#!/bin/bash

pushd ./server
./build-components.sh
popd

pushd ./server-auth
./build.sh
popd
