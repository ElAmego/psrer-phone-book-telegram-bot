package by.psrer.service.impl;

import by.psrer.dao.AppUserConfigDAO;
import by.psrer.dao.AppUserDAO;
import by.psrer.entity.AppUser;
import by.psrer.entity.AppUserConfig;
import by.psrer.service.SentMessageService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@SuppressWarnings("unused")
public final class SentMessageServiceImpl implements SentMessageService {
    private final AppUserDAO appUserDAO;
    private final AppUserConfigDAO appUserConfigDAO;

    public SentMessageServiceImpl(final AppUserDAO appUserDAO, final AppUserConfigDAO appUserConfigDAO) {
        this.appUserDAO = appUserDAO;
        this.appUserConfigDAO = appUserConfigDAO;
    }

    @Override
    public void handleSentMessage(final Message sentMessage) {
        final int messageId = sentMessage.getMessageId();
        final Long chatId = sentMessage.getChatId();
        final AppUser appUser = appUserDAO.findAppUserByTelegramUserId(chatId);
        final AppUserConfig appUserConfig = appUser.getAppUserConfigId();

        appUserConfig.setLastBotMessageId(messageId);
        appUserConfigDAO.save(appUserConfig);
    }
}