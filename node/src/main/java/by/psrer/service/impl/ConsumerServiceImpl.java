package by.psrer.service.impl;

import by.psrer.service.CallbackService;
import by.psrer.service.ConsumerService;
import by.psrer.service.TextMessageService;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import static by.psrer.model.RabbitQueue.CALLBACK;
import static by.psrer.model.RabbitQueue.USER_TEXT_MESSAGE;

@Service
@SuppressWarnings("unused")
@Log4j
public final class ConsumerServiceImpl implements ConsumerService {
    private final TextMessageService textMessageService;
    private final CallbackService callbackService;

    public ConsumerServiceImpl(final TextMessageService textMessageService, final CallbackService callbackService) {
        this.textMessageService = textMessageService;
        this.callbackService = callbackService;
    }

    @Override
    @RabbitListener(queues = USER_TEXT_MESSAGE)
    public void consumeUserTextMessage(final Update update) {
        log.debug("NODE: Text message is received");
        textMessageService.handleCommand(update);
    }

    @Override
    @RabbitListener(queues = CALLBACK)
    public void consumeCallback(final Update update) {
        log.debug("NODE: Callback is received");
        callbackService.handleCallback(update);
    }
}