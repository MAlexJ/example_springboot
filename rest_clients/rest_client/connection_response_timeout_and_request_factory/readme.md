### Set Connection Timeout (connectTimeout), Response Timeout (readTimeout), and RequestFactory

* Response Timeout / ReadTimeout:
  number of seconds that the client (gateway) could wait for the server to send response.
  More specifically, start-of-response-time - end-of-request-time

* Connection Timeout:
  the max waiting time to establish the connection

In order to set timeouts to our outgoing requests from a `RestClient`, we have to set them through
the `ClientHttpRequestFactory` of this `RestClient`.

link: https://github.com/nlinhvu/rest-client-demo-2024?tab=readme-ov-file#3-set-connection-timeout-connecttimeout-response-timeout-readtimeout-and-requestfactory

But each type of `ClientHttpRequestFactory` has it own structure and they differ from others
so we have to know the configuration of the underlying components to configure it right.

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
