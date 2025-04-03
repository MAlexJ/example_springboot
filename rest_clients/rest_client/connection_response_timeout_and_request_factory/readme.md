### Set Connection Timeout (connectTimeout), Response Timeout (readTimeout), and RequestFactory

* Connection Timeout:
  the max waiting time to establish the connection

* Response Timeout / ReadTimeout:
  number of seconds that the client (gateway) could wait for the server to send response.
  More specifically, start-of-response-time - end-of-request-time

In order to set timeouts to our outgoing requests from a `RestClient`, we have to set them through
the `ClientHttpRequestFactory` of this `RestClient`.

link: https://github.com/nlinhvu/rest-client-demo-2024?tab=readme-ov-file#3-set-connection-timeout-connecttimeout-response-timeout-readtimeout-and-requestfactory

But each type of `ClientHttpRequestFactory` has it own structure and they differ from others
so we have to know the configuration of the underlying components to configure it right.

#### Connection Timeout vs Read Timeout

In HTTP requests, there are two key timeouts:

1. Connection Timeout – the time limit for establishing a connection with the server.
    * Defines how long the client will wait for a TCP connection to be established.
    * If the server is unavailable, overloaded, or too slow to respond, the connection attempt is aborted.
    * Usually occurs due to network issues or an unresponsive server.
    * Applies during the TCP handshake (before any data is sent).

2. Read Timeout – the time limit for waiting for a response after the connection is established.
    * The client has successfully connected and sent a request, but the server is taking too long to respond.
    * If the server does not send data within the specified time, the request is aborted.
    * Applies during the reading phase (after the connection is established).

When is each timeout more important?

* Higher connection timeout – if the server takes time to establish a connection but responds quickly afterward.
* Higher read timeout – if the server processes requests slowly.
* Lower connection timeout – if you need to quickly switch to a backup server in case of failure.
* Lower read timeout – if the request is expected to be processed quickly.

If the server is unavailable, connection timeout occurs.
If the server responds too slowly, read timeout occurs.

#### Configuration

For example:

* SimpleClientHttpRequestFactory: we can set both connection timeout and response timeout on this
  SimpleClientHttpRequestFactory itself

code:

```
SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
simpleClientHttpRequestFactory.setConnectTimeout(Duration.ofSeconds(1L)); <--
simpleClientHttpRequestFactory.setReadTimeout(Duration.ofSeconds(5L)); <--

this.restClient = RestClient.builder()
        .requestFactory(simpleClientHttpRequestFactory)
        .build();
```

* JdkClientHttpRequestFactory: the connection timeout is set on HttpClient that is passed into
  JdkClientHttpRequestFactory's constructor and the response timeout is set on the JdkClientHttpRequestFactory

code:

```
HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(1L)).build(); <--
JdkClientHttpRequestFactory jdkClientHttpRequestFactory = new JdkClientHttpRequestFactory(httpClient);
jdkClientHttpRequestFactory.setReadTimeout(Duration.ofSeconds(5L)); <--

this.restClient = RestClient.builder()
        .requestFactory(jdkClientHttpRequestFactory)
        .build();
```

Spring simplified the configuration of underlying components by `ClientHttpRequestFactorySettings` and
`ClientHttpRequestFactories`:

code:

```
ClientHttpRequestFactorySettings requestFactorySettings = ClientHttpRequestFactorySettings.DEFAULTS
                .withConnectTimeout(Duration.ofSeconds(1L))
                .withReadTimeout(Duration.ofSeconds(5L));

JdkClientHttpRequestFactory requestFactory = ClientHttpRequestFactories.get(JdkClientHttpRequestFactory.class, requestFactorySettings);

this.restClient = RestClient.builder()
        .requestFactory(requestFactory)
        .build();
```
