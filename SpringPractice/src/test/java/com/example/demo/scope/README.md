# 빈 스코프 
## 싱글톤 스코프
- 기본 타입. 
- 애플리케이션에서 해당 빈의 인스턴스를 오직 하나만 생성해서 사용 
- ```
  @Scope(value = "singleton")
  static class SingletonScope {
      @PostConstruct
      public void init() {
          System.out.println("최초 한 번만 호출");
      }
  }
  ```
## 프로토타입 스코프 
- 항상 새로운 인스턴스를 생성해서 반환한다.
- 스프링 컨테이너는 프로토타입 빈을 생성하고, 의존관계 주입, 초기화까지만 처리한다. 그 이후는 스프링 컨테이너가 관리해주지 않으므로, @PreDestory가 먹지 않음. 프로토타입 빈은 클라이언트에서 관리해야 한다.
- ```
    @Scope(value = "prototype")
    static class PrototypeScope {
        @PostConstruct
        public void init() {
            System.out.println("매번 호출");
        }
    }
  ```

## 웹 관련 스코프
  + request: 웹 요청이 들어오고 나갈때 까지 유지되는 스코프이다.
  + session: 웹 세션이 생성되고 종료될 때 까지 유지되는 스코프이다.
  + application: 웹의 서블릿 컨텍스와 같은 범위로 유지되는 스코프이다.
  
# 스코프 사용시 주의점

## 프로토타입 빈에서 싱글톤 스코프를 사용하는 경우, 문제 없음 
- 새로운 인스턴스 내부에서 동일한 싱글톤을 사용하므로 문제 없음 
- ```
  @Scope(value = "prototype")
  static class PrototypeScope {
      private final SingletonScope singleton;

      @Autowired
      public PrototypeScope(PrototypeScopeInsideSingletonScopeTest.SingletonScope singleton) {
          System.out.println("singleton = " + singleton);
          this.singleton = singleton;
      }
      ...
  }
  ```

## 싱글톤 스코프에서 프로토타입 빈을 참조하는 경우 문제가 발생 
- 싱글톤 빈 생성 시점에 의존관계인 프로토타입도 주입이 된다. 그렇기 때문에 주입반은 빈은 싱글톤으로 동작하게 된다.   
- ```
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
  ```
- 이를 해결하려면 사용할 때마다 새로 생성할 수 있도록 수정해야한다.
## 해결 방법 
### ObjectProvider 
- ```
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
  ```

### proxyMode = ScopedProxyMode.TARGET_CLASS
- ```
  @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
  static class PrototypeScopeProxy {

      @PostConstruct
      public void init() {
          System.out.println("PrototypeScopeProxy.init");
      }
  }
  ```