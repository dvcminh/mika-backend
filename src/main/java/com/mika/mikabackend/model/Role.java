package com.mika.mikabackend.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mika.mikabackend.model.Permission.*;

@RequiredArgsConstructor
public enum Role {

  STAFF(
          Set.of(
                  STAFF_READ,
                  STAFF_UPDATE,
                  STAFF_DELETE,
                  STAFF_CREATE
          )
  ),
  CUSTOMER(
          Set.of(
                  CUSTOMER_READ,
                  CUSTOMER_UPDATE,
                  CUSTOMER_DELETE,
                  CUSTOMER_CREATE
          )
  ),
  ADMIN(
          Set.of(
                  ADMIN_READ,
                  ADMIN_UPDATE,
                  ADMIN_DELETE,
                  ADMIN_CREATE
          )
  )

  ;

  @Getter
  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}
