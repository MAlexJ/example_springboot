## Live Remote Debugging

video tutorial: https://www.youtube.com/watch?v=QuBgmaILdWI
additional info: https://ealebed.github.io/tags/docker/

### Docker documentation

#### build

The docker build command builds Docker images from a Dockerfile and "context"

```
docker build .
```

A build's context is the set of files located in the specified PATH or URL.
The build process can refer to any of the files in the context.

Build with URL

```
docker build github.com/creack/docker-firefox
```  

info: https://docs.docker.com/reference/cli/docker/image/build/

##### options:

-t, --tag : Name and optionally a tag in the name:tag format

-f, --file : Name of the Dockerfile (Default is PATH/Dockerfile)

```
docker build -t spering ./ --file .\docker_live_remote_debugging\Dockerfile
```

#### create

#### start

#### run

