package com.example.demo.scope;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeScopeInsideSingletonScopeTest {

    @Test
    @DisplayName("프로토타입 스코프 내부에 싱글톤 스코프")
    void singletonScopeInsidePrototypeScopeTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeScope.class, SingletonScope.class);

        PrototypeScope bean1 = ac.getBean(PrototypeScope.class);
        PrototypeScope bean2 = ac.getBean(PrototypeScope.class);
        PrototypeScope bean3 = ac.getBean(PrototypeScope.class);

        assertThat(bean1).isNotSameAs(bean2).isNotSameAs(bean3);
        assertThat(bean1.getSingleton()).isSameAs(bean2.getSingleton()).isSameAs(bean3.getSingleton());
    }

    @Scope(value = "prototype")
    static class PrototypeScope {
        private final SingletonScope singleton;

        @Autowired
        public PrototypeScope(PrototypeScopeInsideSingletonScopeTest.SingletonScope singleton) {
            System.out.println("singleton = " + singleton);
            this.singleton = singleton;
        }

        public SingletonScope getSingleton() {
            return singleton;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeScope.init");
        }
    }

    @Scope(value = "singleton")
    static class SingletonScope {
        @PostConstruct
        public void init() {
            System.out.println("SingletonScope.init");
        }
    }
}
