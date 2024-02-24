package com.example.user.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class User {
    private String id;
    private String userName;

}
