package com.sample.person.controller;

import com.sample.person.model.Person;
import com.sample.person.model.PersonList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static com.sample.person.utils.Constants.PersonAPI.*;

@RequestMapping(value = API)
public interface PersonApi {


    @GetMapping(value = PERSON_URL,
        produces = { APPLICATION_JSON })
    @Operation(security = { @SecurityRequirement(name = BEARER_KEY) })
    ResponseEntity<Object> getAllPerson();


    @PostMapping(value = PERSON_URL,
            produces = { APPLICATION_JSON },
            consumes = { APPLICATION_JSON })
    @Operation(security = { @SecurityRequirement(name = BEARER_KEY) })
    ResponseEntity<Object> createPerson(@Valid @RequestBody Person body);


    @DeleteMapping(value = PERSON_BY_ID_URL,
        produces = { APPLICATION_JSON })
    @Operation(security = { @SecurityRequirement(name = BEARER_KEY) })
    ResponseEntity<Object> deletePersonById(@NotNull @NotEmpty @PathVariable(PATCH_PARAM_ID) String id);


    @GetMapping(value = PERSON_BY_ID_URL,
        produces = { APPLICATION_JSON })
    @Operation(security = { @SecurityRequirement(name = BEARER_KEY) })
    ResponseEntity<Object> getPersonById(@NotNull @NotEmpty @PathVariable(PATCH_PARAM_ID) String id);



    @PutMapping(value = PERSON_BY_ID_URL,
        produces = { APPLICATION_JSON },
        consumes = { APPLICATION_JSON })
    @Operation(security = { @SecurityRequirement(name = BEARER_KEY) })
    ResponseEntity<Object> updatePersonById( @NotNull @NotEmpty @PathVariable(PATCH_PARAM_ID) String id, @Valid @RequestBody Person body);


}
