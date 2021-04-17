FROM node:13.12.0-alpine
ENV NODE_ENV=production
ENV PATH /app/node_modules/.bin:$PATH
WORKDIR /src
COPY ["package.json", "package-lock.json*", "npm-shrinkwrap.json*", "./"]
RUN npm install --production --silent && mv node_modules ../
COPY . ./
EXPOSE 3000
CMD ["npm", "start"]
