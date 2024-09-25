package com.example.rasnassesment.mapper;

import com.example.rasnassesment.entity.Role;
import com.example.rasnassesment.entity.User;
import com.example.rasnassesment.model.request.UserDetailsRequest;
import com.example.rasnassesment.model.response.UserResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.HashSet;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse userToUserResponse(User user);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoles")
    User userDetailsToUser(UserDetailsRequest userDetailsRequest);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoles")
    void updateUserFromDto(UserDetailsRequest userDetailsRequest, @MappingTarget User user);

    @Named("mapRoles")
    default Collection<Role> mapRoles(Collection<String> roleNames) {
        if (roleNames == null) {
            return null;
        }

        Collection<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            Role role = new Role();
            role.setName(roleName);
            roles.add(role);
        }
        return roles;
    }
}
