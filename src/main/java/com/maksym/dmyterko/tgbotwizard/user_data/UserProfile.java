package com.maksym.dmyterko.tgbotwizard.user_data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfile {
    String name;
    int age;
    char sex;
}
