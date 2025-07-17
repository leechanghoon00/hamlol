package org.example.hamlol.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "props")
@Getter
@Setter
public class Props {
    private String resetPasswordUrl;

}
