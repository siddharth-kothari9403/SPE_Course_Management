#### Stage 1: Build the react application
FROM node:18-alpine  as build

# Configure the main working directory inside the docker image. 
# This is the base directory used in any further RUN, COPY, and ENTRYPOINT 
# commands.
WORKDIR /app

# Copy the package.json as well as the package-lock.json and install 
# the dependencies. This is a separate step so the dependencies 
# will be cached unless changes to one of those two files 
# are made.
COPY package.json package-lock.json ./
RUN npm install

# Copy the main application
COPY . ./

# Build the application
RUN npm run build

FROM nginx:stable-alpine
# Nginx configuration
COPY nginx.conf /etc/nginx/nginx.conf

COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 3000
CMD ["nginx", "-g", "daemon off;"]
# ENV NODE_ENV production
# # Expose the port on which the app will be running (3000 is the default that `serve` uses)
# EXPOSE 3000
# # Start the app
# CMD [ "npx", "serve", "build" ]
