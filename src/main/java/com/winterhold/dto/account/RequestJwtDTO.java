package com.winterhold.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestJwtDTO {
    private String username;

    private String password;

    private String subject;

    private String audience;

    private String secretKey;
}
