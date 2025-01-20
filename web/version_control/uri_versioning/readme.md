### URI Versioning

link: https://www.freecodecamp.org/news/how-to-version-a-rest-api/

#### How to version a REST API?

REST doesn’t provide for any specific versioning guidelines,
but the more commonly used approaches fall into three categories:

1. **URI Versioning**

Using the URI is the most straightforward approach (and most commonly used as well) though it does violate
the principle that a URI should refer to a unique resource.
You are also guaranteed to break client integration when a version is updated.

http://api.example.com/v1/users
https://api.stripe.com/v1/charges
https://graph.facebook.com/v10.0/me
https://api.github.com/v3/repos/{owner}/{repo}

The version need not be numeric, nor specified using the “v[x]” syntax.

Alternatives include dates, project names, seasons, or other identifiers that are meaningful enough
to the team producing the APIs and flexible enough to change as the versions change.

2. **Versioning using Custom Request Header**

3. **Versioning using “Accept” header**
