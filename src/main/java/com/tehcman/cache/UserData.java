package com.tehcman.cache;

/*It should be used by the FillingProfile class*/

import com.tehcman.user_data.UserProfile;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class UserData {
    Map<Integer, BotState> usersBotState = new HashMap<>();
    Map<Integer, UserProfile> userInformation = new HashMap<>();

    public void setUsersCurrentBotState(int userId, BotState botState){
        usersBotState.put(userId, botState);
    }

    public BotState getUsersCurrentBotState(int userId){
        return usersBotState.get(userId);
    }

    public UserProfile getUserProfileData(int userId){
        return userInformation.get(userId);
    }

    public void saveUserProfileData(int userId, UserProfile userProfileData){
        userInformation.put(userId, userProfileData);
    }

}
