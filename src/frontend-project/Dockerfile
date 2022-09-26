FROM node:14 as build

WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH

COPY package*.json ./
RUN npm install

COPY src ./src
COPY public ./public
COPY config.js ./config.js

#ENV PORT=$port
#EXPOSE $PORT
#RUN PORT=5000 npm start

RUN npm run build

FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html
ENV PORT=$port
EXPOSE $PORT

COPY nginx.conf /etc/nginx/conf.d/default.conf
# CMD ["nginx", "-g", "daemon off;"]
CMD sed -i -e 's/$PORT/'"$PORT"'/g' /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'


