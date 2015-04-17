Time invested:  2 horas.

Time zone is ignored.


Maybe use Instant (immutable and thread-safe). isAfter, isBefore
https://docs.oracle.com/javase/tutorial/datetime/iso/instant.html

Instant is immutable and thread-safe
SessionCookie is immutable (or I hope so :-) ).


http://stackoverflow.com/questions/27067049/unit-testing-a-class-with-a-java-8-clock


31 bit unsigned integer number >>> don't you mean 32 bit unsigned integer number? (it exists in Java 8) >> Usar Integer
In Java 8 there are methods to treat them as if they were unsigned: https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
In Java, int are always signed.

2^1 - 1 = 2147483648

Java 8 optional without Consumer (ifPresent), I just wanted to avoid nulls and it is more readable.


Cookie-less session: session Id is embedded in the URL
Test naming convention: http://www.petrikainulainen.net/programming/testing/writing-clean-tests-trouble-in-paradise

                          https://www.owasp.org/index.php/Session_fixation
http://en.wikipedia.org/wiki/Session_fixation


CQRS: basic separate command and query.

PENDIENTE
* Concurrencia en SessionCookieRepository, ConcurrencyMap???
