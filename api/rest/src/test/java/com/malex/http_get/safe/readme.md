### Safe (HTTP Methods)

An HTTP method is safe if it doesn't alter the state of the server. In other words, a method is safe if it leads to a
read-only operation. 
Several common HTTP methods are safe: the GET, HEAD, OPTIONS, and TRACE methods  are defined to be safe.
All safe methods are also idempotent, but not all idempotent methods are safe. 
For example, PUT and DELETE are both idempotent but unsafe.

Even if safe methods have a read-only semantic, servers can alter their state: e.g. they can log or keep statistics.
What is important here is that by calling a safe method, the client doesn't request any server change itself, and
therefore won't create an unnecessary load or burden for the server. Browsers can call safe methods without fearing to
cause any harm to the server; this allows them to perform activities like pre-fetching without risk. Web crawlers also
rely on calling safe methods.

Safe methods don't need to serve static files only; a server can generate an answer to a safe method on-the-fly, as long
as the generating script guarantees safety: it should not trigger external effects, like triggering an order in an
e-commerce website.

It is the responsibility of the application on the server to implement the safe semantic correctly, the web server
itself, being Apache, Nginx or IIS, can't enforce it by itself. In particular, an application should not allow GET
requests to alter its state.

A call to a safe method, not changing the state of the server:
`GET /pageX.html HTTP/1.1`

A call to a non-safe method, that may change the state of the server:
`POST /pageX.html HTTP/1.1`

A call to an idempotent but non-safe method:
`DELETE /idX/delete HTTP/1.1`
