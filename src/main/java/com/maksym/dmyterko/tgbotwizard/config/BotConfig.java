package com.maksym.dmyterko.tgbotwizard.config;
/*Class that represents the actual bot bean */

import com.maksym.dmyterko.tgbotwizard.TgBotWizardApplication;
import com.maksym.dmyterko.tgbotwizard.models.WizardBot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;


@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {
    private String webHookPath;
    private String botUserName;
    private String botToken;


    @Bean
    public WizardBot TgBotWizardApplication() {
        DefaultBotOptions options = ApiContext
                .getInstance(DefaultBotOptions.class);

        WizardBot mySuperTelegramBot = new WizardBot(options);
        mySuperTelegramBot.setBotUserName(botUserName);
        mySuperTelegramBot.setBotToken(botToken);
        mySuperTelegramBot.setWebHookPath(webHookPath);

        return mySuperTelegramBot;
    }
}
