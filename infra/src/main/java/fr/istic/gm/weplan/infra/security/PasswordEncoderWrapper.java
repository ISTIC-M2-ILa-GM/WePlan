package fr.istic.gm.weplan.infra.security;

import fr.istic.gm.weplan.domain.adapter.PasswordEncoderAdapter;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PasswordEncoderWrapper implements PasswordEncoderAdapter {

    private PasswordEncoder passwordEncoder;

    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
