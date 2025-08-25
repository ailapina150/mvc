package com.resume.services.rabbitMQ;

import com.resume.config.RabbitConfig;
import com.resume.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AmqpProducerService {
    public final RabbitTemplate template;

    public void sendMessage(EmployeeDto employee) {
        template.convertAndSend(RabbitConfig.MAIL_QUEUE, employee);
    }
}
