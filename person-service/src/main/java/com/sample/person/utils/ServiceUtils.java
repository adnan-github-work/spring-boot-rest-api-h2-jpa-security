package com.sample.person.utils;

import com.sample.person.model.Person;
import com.sample.person.model.PersonEntity;
import com.sample.person.model.PersonList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.sample.person.utils.Constants.UTILITY_CLASS;

public class ServiceUtils {

    public static final String VALIDATING_REQUEST_BODY = "Validating request body";

    static Logger log = LoggerFactory.getLogger(ServiceUtils.class);

    private ServiceUtils() {
        throw new IllegalStateException(UTILITY_CLASS);
    }

    public static boolean validateInput(Person person) {
        log.info(VALIDATING_REQUEST_BODY);
        return null != person && null != person.getFirstName()
                && null != person.getLastName() && null != person.getFavouriteColour()
                && person.getAge() > 0;
    }

    public static boolean validateInput(String id) {
        log.info(VALIDATING_REQUEST_BODY);
        return null != id && !id.isEmpty();
    }

    public static List<PersonEntity> convertToPersonEntityList(@Valid PersonList personList) {
        return personList.getPerson().stream().map(ServiceUtils::personDTOToPersonEntity).collect(Collectors.toList());
    }

    public static List<Person> convertToPersonDtoList(@Valid List<PersonEntity> personEntityList) {
        return personEntityList.stream().map(ServiceUtils::personEntityToPersonDTO).collect(Collectors.toList());
    }

    public static Person personEntityToPersonDTO(PersonEntity personEntity) {
        return new Person.Builder()
                .withId(personEntity.getId())
                .withFirstName(personEntity.getFirstName())
                .withLastName(personEntity.getLastName())
                .withAge(personEntity.getAge())
                .withFavouriteColor(personEntity.getFavouriteColour())
                .withCreatedAt(personEntity.getCreatedAt())
                .withModifiedAt(personEntity.getModifiedAt())
                .build();
    }

    public static PersonEntity personDTOToPersonEntity(Person person) {
        return new PersonEntity(person.getFirstName(), person.getLastName(), person.getAge(), person.getFavouriteColour());
    }

}
