package com.example.demo.scope;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonScopeInsidePrototypeScopeTest {

    @Test
    @DisplayName("싱글톤 스코프 내부에 프로토타입 스코프 - 문제 발생")
    void prototypeScopeInsideSingletonScopeTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeScope.class, SingletonScope.class);

        SingletonScope bean1 = ac.getBean(SingletonScope.class);
        SingletonScope bean2 = ac.getBean(SingletonScope.class);
        SingletonScope bean3 = ac.getBean(SingletonScope.class);

        System.out.println(bean1.getPrototype());
        System.out.println(bean2.getPrototype());
        System.out.println(bean3.getPrototype());

        assertThat(bean1).isSameAs(bean2).isSameAs(bean3);
        assertThat(bean1.getPrototype()).isSameAs(bean2.getPrototype()).isSameAs(bean3.getPrototype());
    }

    @Test
    @DisplayName("ObjectProvider로 싱글톤 스코프 내부에 프로토타입 스코프 문제 해결")
    void prototypeScopeInsideSingletonScopeProviderTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(SingletonScopeProvider.class, PrototypeScope.class);

        SingletonScopeProvider bean1 = ac.getBean(SingletonScopeProvider.class);
        SingletonScopeProvider bean2 = ac.getBean(SingletonScopeProvider.class);
        SingletonScopeProvider bean3 = ac.getBean(SingletonScopeProvider.class);

        System.out.println(bean1.getPrototype());
        System.out.println(bean2.getPrototype());
        System.out.println(bean3.getPrototype());

        assertThat(bean1).isSameAs(bean2).isSameAs(bean3);
        assertThat(bean1.getPrototype()).isNotSameAs(bean2.getPrototype()).isNotSameAs(bean3.getPrototype());
    }

    @Test
    @DisplayName("Proxy로 싱글톤 스코프 내부에 프로토타입 스코프 문제 해결")
    void prototypeScopeInsideSingletonScopeProxyTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(SingletonScopeInsidePrototypeScopeProxy.class, PrototypeScopeProxy.class);

        SingletonScopeInsidePrototypeScopeProxy bean1 = ac.getBean(SingletonScopeInsidePrototypeScopeProxy.class);
        SingletonScopeInsidePrototypeScopeProxy bean2 = ac.getBean(SingletonScopeInsidePrototypeScopeProxy.class);
        SingletonScopeInsidePrototypeScopeProxy bean3 = ac.getBean(SingletonScopeInsidePrototypeScopeProxy.class);

        System.out.println("bean1 = " + bean1);
        System.out.println("bean2 = " + bean2);
        System.out.println("bean3 = " + bean3);

        PrototypeScopeProxy prototype1 = bean1.getPrototype();
        PrototypeScopeProxy prototype2 = bean1.getPrototype();

        System.out.println("prototype1 = " + prototype1);
        System.out.println("prototype2 = " + prototype2);

        assertThat(bean1).isSameAs(bean2).isSameAs(bean3);
        assertThat(prototype1.toString()).isNotSameAs(prototype2.toString());
    }


    @Scope(value = "prototype")
    static class PrototypeScope {

        @PostConstruct
        public void init() {
            System.out.println("PrototypeScope.init");
        }
    }

    @Scope(value = "singleton")
    static class SingletonScope {
        private final PrototypeScope prototype;

        @Autowired
        public SingletonScope(PrototypeScope prototype) {
            this.prototype = prototype;
        }

        public PrototypeScope getPrototype() {
            return prototype;
        }

        @PostConstruct
        public void init() {
            System.out.println("SingletonScope.init");
        }
    }

    @Scope(value = "singleton")
    static class SingletonScopeProvider {
        private final ObjectProvider<PrototypeScope> prototypeProvider;

        @Autowired
        public SingletonScopeProvider(ObjectProvider<PrototypeScope> prototypeProvider) {
            this.prototypeProvider = prototypeProvider;
        }

        public PrototypeScope getPrototype() {
            return prototypeProvider.getObject();
        }

        @PostConstruct
        public void init() {
            System.out.println("SingletonScopeProvider.init");
        }
    }

    @Scope(value = "singleton")
    static class SingletonScopeInsidePrototypeScopeProxy {
        private final PrototypeScopeProxy prototype;

        @Autowired
        public SingletonScopeInsidePrototypeScopeProxy(PrototypeScopeProxy prototype) {
            this.prototype = prototype;
        }

        public PrototypeScopeProxy getPrototype() {
            return prototype;
        }

        @PostConstruct
        public void init() {
            System.out.println("SingletonScopeInsidePrototypeScopeProxy.init");
        }
    }

    @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    static class PrototypeScopeProxy {

        @PostConstruct
        public void init() {
            System.out.println("PrototypeScopeProxy.init");
        }
    }
}
