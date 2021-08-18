package com.example.demo.scope;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonScopeTest {

    @Test
    @DisplayName("항상 싱글톤인지 확인")
    void singletonTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(SingletonScope.class);

        SingletonScope bean1 = ac.getBean(SingletonScope.class);
        SingletonScope bean2 = ac.getBean(SingletonScope.class);
        SingletonScope bean3 = ac.getBean(SingletonScope.class);

        assertThat(bean1).isSameAs(bean2).isSameAs(bean3);
    }

    @Scope(value = "singleton")
    static class SingletonScope {
        @PostConstruct
        public void init() {
            System.out.println("최초 한 번만 호출");
        }
    }
}
