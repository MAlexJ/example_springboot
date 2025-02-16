### @ConditionalOnProperty annotation

example:

```
@ConditionalOnProperty(prefix = "jwt.security", name = "enable", havingValue = "true")
```