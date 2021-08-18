package com.example.demo.scope;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeScopeTest {

    @Test
    @DisplayName("매번 새로운 빈인지 확인")
    void prototypeTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeScope.class);

        PrototypeScope bean1 = ac.getBean(PrototypeScope.class);
        PrototypeScope bean2 = ac.getBean(PrototypeScope.class);
        PrototypeScope bean3 = ac.getBean(PrototypeScope.class);

        System.out.println("bean1 = " + bean1);
        System.out.println("bean2 = " + bean2);
        System.out.println("bean3 = " + bean3);

        assertThat(bean1).isNotSameAs(bean2).isNotSameAs(bean3);
    }

    @Scope(value = "prototype")
    static class PrototypeScope {
        @PostConstruct
        public void init() {
            System.out.println("매번 호출");
        }
    }
}
