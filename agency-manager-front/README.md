# Agencies Manager UI

This repository holds the frontend component of the Flix Agencies Manager.
It is provide with a `Dockerfile` to easly build and run it.

To build the image run:
```
docker build -t agency-manager-ui
```

To run the container run:
```
docker run --name agency-manager-ui-container -d -p 8888:80 agency-manager-ui
```
In this case the application will be served on http://localhost:8888`