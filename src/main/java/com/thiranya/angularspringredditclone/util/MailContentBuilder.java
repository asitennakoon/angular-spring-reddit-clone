package com.thiranya.angularspringredditclone.util;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@AllArgsConstructor
public class MailContentBuilder {
    private TemplateEngine templateEngine;

    public String build(String message) {
        Context context = new Context();
        context.setVariable("message", message);

        return templateEngine.process("mailTemplate", context);
    }
}