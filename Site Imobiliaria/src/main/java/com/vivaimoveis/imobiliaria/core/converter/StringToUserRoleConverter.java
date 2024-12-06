package com.vivaimoveis.imobiliaria.core.converter;

import com.vivaimoveis.imobiliaria.core.entity.UserRole;
import com.vivaimoveis.imobiliaria.core.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToUserRoleConverter implements Converter<String, UserRole> {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserRole convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }

        // Procura o UserRole pelo código (ex: "ROLE_USER")
        return userRoleRepository.findByCode(source);
    }
}
