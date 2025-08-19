package by.psrer.service.impl;

import by.psrer.service.ConsumerService;
import by.psrer.service.MainService;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import static by.psrer.model.RabbitQueue.USER_TEXT_MESSAGE;

@Service
@SuppressWarnings("unused")
@Log4j
public final class ConsumerServiceImpl implements ConsumerService {
    private final MainService mainService;

    public ConsumerServiceImpl(final MainService mainService) {
        this.mainService = mainService;
    }

    @Override
    @RabbitListener(queues = USER_TEXT_MESSAGE)
    public void consumeUserTextMessage(final Update update) {
        log.debug("NODE: Text message is received");
        mainService.handleCommand(update);
    }
}