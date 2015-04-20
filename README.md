#Minigame documentation

## Intro
Hi dear reviewer :-)
<br>
Here you have the result of having invested aroung 20 hours on the exercise.
<br>
To be honest, I had several doubts about the requirements. I asked about it but I got no specific answers,
so I decided just to try to do my best according to my interpretation.
<br>


## Requirements
**Important:** you will need **Java 8** to run it.
<br>


## How to make it work
Just execute:

``java -jar minigame-jar-with-dependencies.jar``


The URL format followed are those described in the documentation, so you can just call:`

[http://localhost:8081/4711/login](http://localhost:8081/4711/login)
<br>
[http://localhost:8081/2/score?sessionkey=UICSNDK](http://localhost:8081/2/score?sessionkey=UICSNDK)
<br>
[http://localhost:8081/2/highscorelist](http://localhost:8081/2/highscorelist)
<br>


## Thougths and considerations
* I read that no frameworks were allowed, but I had doubts about the use of simple libraries. In the end, I decided not to use any,
with the only exception of Guava for a couple of silly things (creating the equals() and hashCode(), and the ListMultimap).
* I used Java 8, though I have just started learning about Streams, so I know there is a looot of room for improvement in its use
(I just wanted to take advantage of this technical test to learn a little bit more about it).
* I have tried to applied the **YAGNI** principle.
* I have used an **UUID** as session key (it is reasonably unique).
* I have avoided the use of primitives as instance attributes.
* Classes like Score and SessionCookie are **immutable**.
* In the requirements, you talked about using "31 bit unsigned integer number".
I have used Integer instead, which is not exactly what you asked for,
but in Java 8 there are methods to treat them as 32-bit unsigned integer number if needed.
<br>
What I have done is to validate that only positive numbers are acceptable.
Anyway, it sounds weird to me to talk about 31-bit and not 32-bit (probably because my lack of knowledge about something).
* I didn't create any User class, since I didn't see that I needed at this moment.
I struggled a lot about "the best" way of dealing with all the concepts (User, Level, Session, Score)... and to be honest, I still have lots of doubts about the convenience of this solution.
* I created a **fault barrier** in the MinigameHttpHandler.handle(). The API uses **HTTP status code** to answer; currently, I use:
    * 200 (OK): everything was fine.
    * 404 (Not Found): if the request goes to an URL not recognized (no handler exists for it).
    * 403 (Forbidden): if the sessionkey used to post a score, has expired or it does not exist.
    * 400 (Bad Request): if the input fields received are not those expected (e.g. if we post a score which is AAA instead of a numeric value).
    * 500 (Internal Error): for any other unexpected exception.
* In general (though I'm sure I missed something), I have tried to use the CQRS principle and not no
<br>


## Pending improvements
* Concurrency: sorry, I didn't take care about it. I followed the idea of "first make it work, then improve it",
so I didn't have enough time for that, it would be the next step (including some automated tests as well).
* Take into account Time zone for the timestamp of the Scores.
* I would like to take a look to the parallel() in Java 8 Streams.
* Solve security problems like session fixation



## Feedback
Whether I continue in the process or not, I would deeply appreciate any feedback about the code, it's always a good way of learning :-)

**THANKS!!**

Incluir en PDF screenshot con el coverage.


PENDIENTE

MAÑANA
* Ordenar correctamente resultado de highscorelist   (1 hora)
* Aceptar solo enteros positivos!!! (levelId, score...).
* Documentar métodos públicos                                           (30 minutos)
* ¿Es necesario hasUserValidSessionKey?                                       (30 minutos)
* Poner en limpio esta documentación                  (30 minutos)
*


TARDE
* Puedo sustituir List por Collection???
* Concurrencia en SessionCookieRepository, ConcurrencyMap???

* Tests:
** Repository.findSessionCookieFromSessionKey
** Repository en general
** Tests de integración!!!!


Sort

http://www.leveluplunch.com/java/examples/java-util-stream-groupingBy-example/ >> groupingBy()

http://www.leveluplunch.com/java/tutorials/007-sort-arraylist-stream-of-objects-in-java8/
http://www.jayway.com/2013/11/12/immutable-list-collector-in-java-8/
https://blog.relateiq.com/java-8-examples/
* You can convert a List into a map (e.g. lista de Levels, en map de
* groupingBy() --> de una lista de Levels, pasar a un map<UserId, List<Scores>>,
* Multimaps.index(foos, Foo::getCategory()) >> agrupa por categorías, convierte una lista de Foos a una Multimap de <Category, List<Foo>>



¿Usara parallel?
http://radar.oreilly.com/2015/02/java-8-streams-api-and-parallelism.html


LEER EN FUTURO
http://www.javaworld.com/article/2461744/java-language/java-language-iterating-over-collections-in-java-8.html
