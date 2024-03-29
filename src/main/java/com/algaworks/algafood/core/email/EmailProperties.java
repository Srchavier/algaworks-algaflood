package com.algaworks.algafood.core.email;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Validated
@Data
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {

    @NotNull
    private String remetente;

    private Sandbox sandbox = new Sandbox();

    @NotNull
    private Implementacao impl = Implementacao.FAKE;

    public enum Implementacao {
        SMTP, FAKE, SANDBOX
    }

    @Getter
    @Setter
    public class Sandbox {
        private String destinatario;
    }

}
