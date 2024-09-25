package com.example.rasnassesment;


import com.example.rasnassesment.entity.Authority;
import com.example.rasnassesment.entity.Role;
import com.example.rasnassesment.entity.User;
import com.example.rasnassesment.repository.AuthorityRepository;
import com.example.rasnassesment.repository.RoleRepository;
import com.example.rasnassesment.repository.UserRepository;
import com.example.rasnassesment.utils.ERole;
import com.example.rasnassesment.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class InitialUserSetup {
    final
    UserRepository userRepository;
    final
    AuthorityRepository authorityRepository;

    final
    RoleRepository roleRepository;

    final
    Utils utils;

    final
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public InitialUserSetup(UserRepository userRepository, AuthorityRepository authorityRepository, RoleRepository roleRepository, Utils utils, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.roleRepository = roleRepository;
        this.utils = utils;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // Create authorities
        Authority systemConfigure = createAuthority("SYSTEM_CONFIGURE");
        Authority invoiceAdd = createAuthority("INVOICE_ADD");
        Authority invoiceView = createAuthority("INVOICE_VIEW");
        Authority invoiceEdit = createAuthority("INVOICE_EDIT");
        Authority invoiceDelete = createAuthority("INVOICE_DELETE");
        Authority userManage = createAuthority("USER_MANAGE");
        Authority roleManage = createAuthority("ROLE_MANAGE");

        // Create roles with their respective authorities
        Role roleAdmin = createRole(ERole.ROLE_ADMIN.name(), Arrays.asList(
                systemConfigure,
                invoiceAdd,
                invoiceView,
                invoiceEdit,
                invoiceDelete,
                userManage,
                roleManage
        ));

        Role roleChefAccountant = createRole(ERole.ROLE_CHEF_ACCOUNTANT.name(), Arrays.asList(
                invoiceAdd,
                invoiceView,
                invoiceEdit,
                invoiceDelete,
                userManage
        ));

        Role roleAccountant = createRole(ERole.ROLE_ACCOUNTANT.name(), Arrays.asList(
                invoiceAdd
        ));

        // Create an admin user
        if (roleAdmin != null) {
            User adminUser = User.builder()
                    .firstName("Rewan")
                    .lastName("Yehia")
                    .email("test@test.com")
                    .userId(utils.generateUserId(30))
                    .encryptedPassword(bCryptPasswordEncoder.encode("123456"))
                    .roles(Arrays.asList(roleAdmin))
                    .build();

            userRepository.save(adminUser);
        }
    }

    @Transactional
    protected Authority createAuthority(String name){
        Authority authority = authorityRepository.findByName(name);
        if(authority==null){
            authority = new Authority(name);
            authorityRepository.save(authority);

        }
        return authority;
    }

    @Transactional
    protected Role createRole(String name, Collection<Authority> authorities){
        Role role = roleRepository.findByName(name);
        if(role==null){
            role = new Role(name);
            role.setAuthorities(authorities);
            roleRepository.save(role);

        }
        return role;
    }
}
