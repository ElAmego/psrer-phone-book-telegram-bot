package by.psrer.service.impl;

import by.psrer.service.UpdateProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@SuppressWarnings("unused")
public final class UpdateProducerImpl implements UpdateProducer {
    private final RabbitTemplate rabbitTemplate;

    public UpdateProducerImpl(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produce(final String rabbitQueue, final Update update) {
        rabbitTemplate.convertAndSend(rabbitQueue, update);
    }
}