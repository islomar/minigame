Time invested:  7 horas.

https://agilewarrior.wordpress.com/2015/04/18/classical-vs-mockist-testing/

Time zone is ignored.

YAGNI: nada que no se necesite ahora mismo.

Avoided primitives
Return immutable values
Map me asegura que hay un único Key (e.g. para los usuarios).

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
* Documentar métodos públicos
* Puedo sustituir List por Collection???
* Concurrencia en SessionCookieRepository, ConcurrencyMap???
* Borrar todo código que no se pida ahora mismo!!! (YAGNI)



Sort

http://www.leveluplunch.com/java/examples/java-util-stream-groupingBy-example/ >> groupingBy()

http://www.leveluplunch.com/java/tutorials/007-sort-arraylist-stream-of-objects-in-java8/
http://www.jayway.com/2013/11/12/immutable-list-collector-in-java-8/
https://blog.relateiq.com/java-8-examples/
* You can convert a List into a map (e.g. lista de Levels, en map de
* groupingBy() --> de una lista de Levels, pasar a un map<UserId, List<Scores>>,
* Multimaps.index(foos, Foo::getCategory()) >> agrupa por categorías, convierte una lista de Foos a una Multimap de <Category, List<Foo>>


Max
https://stevewall123.wordpress.com/2014/08/31/java-8-streams-max-and-streams-min-example/


¿Usara parallel?
http://radar.oreilly.com/2015/02/java-8-streams-api-and-parallelism.html


LEER EN FUTURO
http://www.javaworld.com/article/2461744/java-language/java-language-iterating-over-collections-in-java-8.html



@Test
public void whenSortingEntitiesByNameReversed_thenCorrectlySorted() {
    List<Human> humans = Lists.newArrayList(
      new Human("Sarah", 10), new Human("Jack", 12));
    Comparator<Human> comparator = (h1, h2) -> h1.getName().compareTo(h2.getName());

    humans.sort(comparator.reversed());
    Assert.assertThat(humans.get(0), equalTo(new Human("Sarah", 10)));
}


//SERVER
Asked, but no answer.
Started with Servlets and Jetyy... then I realized about the use of HttpServer, I don't know if we I have done is what you meant.
