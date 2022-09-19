package site.bufgix.junitproject.util;

import org.springframework.stereotype.Component;

//가짜
@Component // IoC컨테이너에 등록
public class MailSenderStub implements MailSender {
    @Override
    public boolean send() {
        return true;
    }
}