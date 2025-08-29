package com.resume.services.rabbitMQ;

import com.resume.config.RabbitConfig;
import com.resume.dto.EmployeeDto;
import com.resume.model.FileFormat;
import com.resume.services.FileService;
import com.resume.services.MailSenderService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class AmqpConsumerService {
    MailSenderService mailSender;
    FileService fileService;

    @RabbitListener(queues = RabbitConfig.MAIL_QUEUE)
    public void receiveMessage(EmployeeDto employee) {
        log.info("Received employee {}", employee);
        mailSender.sendMailWithAttachment(
                "ailapina150@gmail.com",
                "resume of " + employee.getName(),
                "Вы можите ознакомится с содержимым письма",
                fileService.createFile(employee, FileFormat.DOCX)
        );
    }
}
