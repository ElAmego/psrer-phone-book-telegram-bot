package by.psrer.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static by.psrer.model.RabbitQueue.ANSWER_MESSAGE;
import static by.psrer.model.RabbitQueue.CALLBACK;
import static by.psrer.model.RabbitQueue.DELETE_MESSAGE;
import static by.psrer.model.RabbitQueue.REPLACED_MESSAGE;
import static by.psrer.model.RabbitQueue.SENT_MESSAGE;
import static by.psrer.model.RabbitQueue.USER_TEXT_MESSAGE;

@Configuration
@SuppressWarnings("unused")
public class RabbitConfiguration {
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue userMessageQueue() {
        return new Queue(USER_TEXT_MESSAGE);
    }

    @Bean
    public Queue answerMessageQueue() {
        return new Queue(ANSWER_MESSAGE);
    }

    @Bean
    public Queue callBackQueue() {
        return new Queue(CALLBACK);
    }

    @Bean
    public Queue deleteMessageQueue() {
        return new Queue(DELETE_MESSAGE);
    }

    @Bean
    public Queue sentMessageQueue() {
        return new Queue(SENT_MESSAGE);
    }

    @Bean
    public Queue replacedMessageQueue() {
        return new Queue(REPLACED_MESSAGE);
    }
}