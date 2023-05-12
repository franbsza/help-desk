package com.digital.helpdesk.controllers;

import com.digital.helpdesk.dto.UserDTO;
import com.digital.helpdesk.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("users")
public class UserController {

    private final UserService service;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping
    public List<UserDTO> getAll(){
        return service.findAll();
    }
    @GetMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> get(@RequestParam("id") Long id){
        return ResponseEntity.ok(service.findOne(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping(value = "/new",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO user, UriComponentsBuilder uriBuilder){
        UserDTO userCreated = service.create(user);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(userCreated.getId()).toUri();
        return ResponseEntity.created(uri).body(userCreated);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PutMapping(value = "/edit",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> edit(@RequestHeader(value = "id") Long id, @Valid @RequestBody UserDTO userDTO){
        return ResponseEntity.ok().body(service.update(id, userDTO));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping
    public ResponseEntity delete(@RequestHeader("id") Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
