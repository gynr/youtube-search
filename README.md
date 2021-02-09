# youtube-search
Project to implement Youtube Data API in Spring+Elasticsearch.

## Dependencies used to build docker image of the service:
1. Maven
2. Java 11
3. Elasticsearch 7.9.3
4. Youtube Data v3 API

## Youtube Data v3 API reference:
- **You need to generate API key for Youtube Data v3 API**
- YouTube data v3 API: https://developers.google.com/youtube/v3/getting-started
- Search API reference: https://developers.google.com/youtube/v3/docs/search/list

## How to Run the service:

1. Create **.env** file with following evironment variables in project root folder where docker-compose.yml is present
```
YOUTUBE_APIKEY={{Your Youtube Data v3 API key goes here.}}
```
2. Run docker compose command.
```
docker-compose up
```
## Test the apis

1. List stored latest video (by pubhlished datetime) in paginated manner
```
curl --location --request GET 'localhost:8080/api/v1/youtube/latest?page=0&size=10'
```
Parameter	|default value  |	remark
----------|---------------|-------------------------------------
page	    |	     0        | page of api result set
size	    |      10       |	size of each page of api result set

2. Search latest video (by pubhlished datetime)
```
curl --location --request GET 'localhost:8080/api/v1/youtube/search?query=boys'
```
Parameter	|default value  |	remark
----------|---------------|---------------------------------------------
page	    |	     0        | page of api result set
size	    |      10       |	size of each page of api result set
query     |     REQUIRED  | text to search in title or decription field

## To build .jar file
1. Run compile.sh script in project root.
```
sh compile.sh
```
## To build docker image file
1. Run compile.sh script in project root.
```
./mvnw spring-boot:build-image -DskipTests=true -Dspring-boot.build-image.imageName={{Your docker image name e.g. dockerID/repository:tag}}
```

