### Testing in Spring Boot applications

In addition to production code, we typically also write tests to discover and correct possible errors as early
as possible in the development process.
For new functionality, it isn’t even uncommon to write more test code than production code.
At the same time, the tests should be as fast as possible, so they can be executed frequently without causing long
delays.
In this article, we explore how this can be done in applications based on Spring Boot.

1. Unit tests without application context
2. Tests with application context
3. Spring Boot tests
4. Spring Boot test slices
5. Creating a custom test slice

link: https://www.innoq.com/en/articles/2023/10/spring-boot-testing/


TODO: ?????
@SpringBootTest(classes = {DtoToChildParentObjectMapper.class, DtoToChildParentService.class})