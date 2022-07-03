package com.example.core.singleton;

import org.junit.jupiter.api.DisplayName;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatefulServiceTest {

    @Test
    @DisplayName("[o] Thread 간 동시성 문제")
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA: A사용자 10000원 주문
        int userAprice = statefulService1.order("userA", 10000);
        // ThreadB: B사용자 20000원 주문
        int userBprice = statefulService2.order("userB", 20000);
        // ThreadA: 사용자A 주문 금액 조회
        System.out.println("userAprice = " + userAprice);
        System.out.println("userBprice = " + userBprice);
        assertThat(userAprice).isEqualTo(10000);
        assertThat(userBprice).isEqualTo(20000);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}