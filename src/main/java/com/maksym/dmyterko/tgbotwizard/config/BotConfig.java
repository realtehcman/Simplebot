package com.maksym.dmyterko.tgbotwizard.config;
/*Class that represents the actual bot bean */

import com.maksym.dmyterko.tgbotwizard.TgBotWizardApplication;
import com.maksym.dmyterko.tgbotwizard.bot_api.Facade;
import com.maksym.dmyterko.tgbotwizard.models.WizardBot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {
    private String webHookPath;
    private String botUserName;
    private String botToken;



    @Bean
    public WizardBot TgBotWizardApplication(Facade facade) {
        DefaultBotOptions options = ApiContext
                .getInstance(DefaultBotOptions.class);

        WizardBot mySuperTelegramBot = new WizardBot(options, facade);
        mySuperTelegramBot.setBotUserName(botUserName);
        mySuperTelegramBot.setBotToken(botToken);
        mySuperTelegramBot.setWebHookPath(webHookPath);

        return mySuperTelegramBot;
    }

    @Bean
    public MessageSource messageSource() {
//ReloadableResourceBundleMessageSource class in such a way that it will search the class-path for
// properties files on application start-up and dynamically load all of them, thus, I don’t need
// to restart web app after each alteration


        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource =
                new ReloadableResourceBundleMessageSource();

        reloadableResourceBundleMessageSource.addBasenames("classpath:questions");
        reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");
        //looks for the languages at the resource folder

        return reloadableResourceBundleMessageSource;
    }
}
