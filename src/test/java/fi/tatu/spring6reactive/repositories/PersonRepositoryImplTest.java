package fi.tatu.spring6reactive.repositories;

import fi.tatu.spring6reactive.domain.Person;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryImplTest {

    PersonRepository personRepository = new PersonRepositoryImpl();

    // This is example of bad way to work with reactive streams
    // Person object is being fetched, but the process is blocked meanwhile
    // @Disabled
    @Test
    void testMonoByIdBlock() {
        Mono<Person> personMono = personRepository.getById(1);
        Person person = personMono.block();
        System.out.println(person.toString());
    }

    // This is preferred way to work with reactive streams
    // Subscriber proceeds asynchronously when the data is available (=published)
    @Test
    void testGetByIdSubscriber() {
        Mono<Person> personMono = personRepository.getById(1);
        personMono.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    // Map Mono object using lambda function syntax
    // Note: this is exactly the same as testMapOperationMethodReference() method but in longer format
    @Test
    void testMapOperationLambdaSyntax() {
        Mono<Person> personMono = personRepository.getById(1);

        personMono.map(person -> {
            return person.getFirstName();
        }).subscribe(firstName -> {
            System.out.println(firstName);
        });
    }


    // Map Mono object using method reference
    // Note: this is exactly the same as testMapOperationLambdaSyntax() method but using syntactic sugar :)
    @Test
     void testMapOperationMethodReference() {
        Mono<Person> personMono = personRepository.getById(1);

        personMono.map(Person::getFirstName)
            .subscribe(System.out::println);
    }

    @Test
    void testFluxBlockFirst() {
        Flux<Person> personFlux = personRepository.findAll();

        Person person = personFlux.blockFirst();

        System.out.println(person.toString());
    }

    @Test
    void testFluxSubscriber() {
        Flux<Person> personFlux = personRepository.findAll();

        personFlux.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void testFluxMap() {
        Flux<Person> personFlux = personRepository.findAll();

        personFlux.map(Person::getFirstName)
            .subscribe(firstName -> System.out.println(firstName));
    }
}