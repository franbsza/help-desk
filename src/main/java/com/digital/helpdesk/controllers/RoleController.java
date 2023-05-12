package com.digital.helpdesk.controllers;

import com.digital.helpdesk.dto.RoleDTO;
import com.digital.helpdesk.service.RoleService;
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
@RequestMapping("/roles")
public class RoleController {

    private final RoleService service;

    @GetMapping
    public List<RoleDTO> getAll(){
        return service.findAll();
    }

    @GetMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDTO> get(@RequestHeader("id") Long id){
        return ResponseEntity.ok(service.findOne(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/new",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDTO> create(@RequestBody RoleDTO user, UriComponentsBuilder uriBuilder){
        RoleDTO roleCreated = service.create(user);
        URI uri = uriBuilder.path("/roles/{id}").buildAndExpand(roleCreated.getId()).toUri();
        return ResponseEntity.created(uri).body(roleCreated);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PutMapping(value = "/edit",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDTO> edit(@RequestHeader("id") Long id, @Valid @RequestBody RoleDTO roleDTO){
        return ResponseEntity.ok().body(service.update(id, roleDTO));
    }


    @DeleteMapping("{id}")
    public ResponseEntity delete(@RequestHeader("id") Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
