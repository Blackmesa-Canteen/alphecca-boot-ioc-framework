#!/bin/sh

# firstly, please login the heroku
heroku login

# then, login the heroku container
heroku container:login

# then build the docker thing
docker build -t swen90007-backend-app .

# tag the image to heroku repo
docker tag swen90007-backend-app:latest registry.heroku.com/swen90007-alphecca-backend-app/web

# push the repo to heroku
docker push registry.heroku.com/swen90007-alphecca-backend-app/web

# release the container
heroku container:release web --app swen90007-alphecca-backend-app

# then it works
