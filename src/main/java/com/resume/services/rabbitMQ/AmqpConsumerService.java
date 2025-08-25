package com.resume.services.rabbitMQ;

import com.resume.config.RabbitConfig;
import com.resume.dto.EmployeeDto;
import com.resume.model.FileFormat;
import com.resume.services.FileService;
import com.resume.services.MailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AmqpConsumerService {
    MailSenderService mailSender;
    FileService fileService;

    @RabbitListener(queues = RabbitConfig.MAIL_QUEUE)
    public void receiveMessage(EmployeeDto employee) {
        mailSender.sendMailWithAttachment(
                "ailapina150@gmail.com",
                "resume of " + employee.getName(),
                "Вы можите ознакомится с содержимым письма",
                fileService.createFile(employee, FileFormat.DOCX)
        );
    }
}
