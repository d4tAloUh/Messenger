FROM node:13.12.0-alpine
ENV NODE_ENV=production
ENV PATH /node_modules/.bin:$PATH
WORKDIR /src
COPY ["package.json", "package-lock.json*", "./"]
RUN npm install --production && mv node_modules ../
COPY . ./
EXPOSE 3000
CMD ["npm", "start"]
