package com.ms.user.procucers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ms.user.dtos.EmailDto;
import com.ms.user.models.UserModel;

@Component
public class UserProducer {
	
	//Maneira Tradicional com construtor sem o @Autowired
	final RabbitTemplate rabbitTemplate;
	
	public UserProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	//Exchange do tipo default: chave routing key e o mesmo nome
	@Value(value = "${broker.queue.email.name}")
	private String routngKey;
	
	public void publishMessageEmail(UserModel userModel) {
		var emailDto = new EmailDto();
		emailDto.setUserId(userModel.getUserId());
		emailDto.setEmailTo(userModel.getEmail());
		emailDto.setSubject("Cadastro realizado com sucesso!");
		emailDto.setText(userModel.getName() +", seja bem vindo(a)! \nAgradecemos seu cadastro.");
		
		rabbitTemplate.convertAndSend("", routngKey, emailDto);
		
	}
}
