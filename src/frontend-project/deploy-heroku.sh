#!/bin/sh
# author: xiaotianli

# Install Heroku CI and docker first!
# then, please login the heroku
heroku login

# then, login the heroku container
heroku container:login

# then build the docker thing
docker build -t swen90007-frontend-app .

# tag the image to heroku repo
docker tag swen90007-frontend-app:latest registry.heroku.com/swen90007-alphecca-frontend/web

# push the repo to heroku
docker push registry.heroku.com/swen90007-alphecca-frontend/web

# release the container
heroku container:release web --app swen90007-alphecca-frontend

# clean-up
docker rmi swen90007-frontend-app
docker rmi registry.heroku.com/swen90007-alphecca-frontend/web
