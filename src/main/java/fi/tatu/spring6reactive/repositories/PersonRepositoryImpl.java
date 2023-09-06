package fi.tatu.spring6reactive.repositories;

import fi.tatu.spring6reactive.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class PersonRepositoryImpl implements PersonRepository {

    Person mike = Person.builder()
            .id(1)
            .firstName("Michael")
            .lastName("Weston")
            .build();

    Person john = Person.builder()
            .id(2)
            .firstName("John")
            .lastName("Doe")
            .build();

    Person mario = Person.builder()
            .id(3)
            .firstName("Mario")
            .lastName("SuperBros")
            .build();

    Person luigi = Person.builder()
            .id(4)
            .firstName("Luigi")
            .lastName("SuperBros")
            .build();


    @Override
    public Mono<Person> getById(final Integer id) {
        return findAll().filter(person -> Objects.equals(person.getId(), id)).next();
    }

    @Override
    public Flux<Person> findAll() {
        return Flux.just(mike, john, mario, luigi);
    }
}
