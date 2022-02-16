package com.tehcman.cache;

public enum BotState {
    ASK_NAME,
    ASK_GENDER,
    ASK_AGE,
    FILLING_PROFILE, //Init state; executes abive commands
    SHOW_HELP_MENU,
    PROFILE_FILLED; //outputs the user data to the user
    }
