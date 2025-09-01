package com.resume.services.rabbitMQ;

import com.resume.config.RabbitConfig;
import com.resume.dto.EmployeeDto;
import com.resume.dto.ProjectDto;
import com.resume.model.Project;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AmqpProjectProducerService {
    public final RabbitTemplate template;

    public void sendMessage(ProjectDto project) {

        template.convertAndSend(RabbitConfig.PROJECT_QUEUE, project);
    }
}
