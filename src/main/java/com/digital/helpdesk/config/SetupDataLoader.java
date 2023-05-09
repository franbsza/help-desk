package com.digital.helpdesk.config;

import com.digital.helpdesk.models.Privilege;
import com.digital.helpdesk.models.Role;
import com.digital.helpdesk.models.User;
import com.digital.helpdesk.repository.PrivilegeRepository;
import com.digital.helpdesk.repository.RoleRepository;
import com.digital.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Transactional
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private boolean alreadySetup = false;

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PrivilegeRepository privilegeRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        // == create initial privileges
        final Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        final Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        final Privilege passwordPrivilege = createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");

        // == create initial roles
        final List<Privilege> adminPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege));
        final List<Privilege> userPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, passwordPrivilege));
        final Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", userPrivileges);

        // == create initial user
        createUserIfNotFound("test@test.com", "Test", "Test", "test", "test", new ArrayList<>(Arrays.asList(adminRole)));

        alreadySetup = true;
    }

    @Transactional
    public Privilege createPrivilegeIfNotFound(final String name) {
        Privilege privilege;
        if(!privilegeRepository.findByName(name).isPresent()){
            privilege = new Privilege();
            privilege.setName(name);
            return privilegeRepository.save(privilege);
        }
        privilege = privilegeRepository.findByName(name).get();
        return privilege;
    }

    @Transactional
    public Role createRoleIfNotFound(final String name, final List<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
        }
        role.setPrivileges(privileges);
        role = roleRepository.save(role);
        return role;
    }


    @Transactional
    public User createUserIfNotFound(final String email, final String firstName, final String lastName, final String password, String nickname, final List<Role> roles) {
        User user;
        if(userRepository.findByEmail(email).isPresent()){
            user = userRepository.findByEmail(email).get();
        }
        else {
            user = new User();
            user.setName(firstName);
            user.setLastName(lastName);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setActive(true);
            user.setNickname(nickname);
        }
        user.setRoles(roles);
        user = userRepository.save(user);
        return user;
    }
}
