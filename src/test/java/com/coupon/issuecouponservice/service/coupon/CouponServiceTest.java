package com.coupon.issuecouponservice.service.coupon;

import com.coupon.issuecouponservice.domain.user.Role;
import com.coupon.issuecouponservice.domain.user.User;
import com.coupon.issuecouponservice.dto.request.coupon.CouponIssueParam;
import com.coupon.issuecouponservice.repository.coupon.UserCouponRepository;
import com.coupon.issuecouponservice.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@TestPropertySource(locations = "/application-test.properties")
@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    /*@Autowired
    private RedisLockStockFacade redisLockStockFacade;*/

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private UserRepository userRepository;

    CouponIssueParam couponIssueParam;

    List<User> users = new ArrayList<>();

//    List<User> users;

    @BeforeEach
    void setUp() {
        couponIssueParam = new CouponIssueParam(1L);
        for (int i = 0; i < 1000; i++ ) {
            User user = User.builder()
                    .username("test" + i)
                    .nickName("Test" + i)
                    .role(Role.USER)
                    .email("test" + i + "@example.com")
                    .provider("test")
                    .providerId("test" + i)
                    .build();
            userRepository.save(user);
            users.add(user);
        }
//        users = userRepository.findAll();
    }

    @AfterEach
    void after() {
        userCouponRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("쿠폰 한 명 발급")
    void 쿠폰한명_발급() {
        // given
//        User user = userRepository.findById(1L).get();

        User user = users.get(0);
        // when
        couponService.issueCoupon(couponIssueParam, user);

        // then
        int count = userCouponRepository.countByCouponId(couponIssueParam.getCouponId());
        AssertionsForClassTypes.assertThat(count).isEqualTo(1L);
    }

/*    @Test
    @DisplayName("쿠폰 여러 명 발급 - 레디슨")
    void issueCoupon_redisson() throws InterruptedException {
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadNumber = i + 1;
            User user = users.get(i);
            executorService.submit(() -> {
                try {
                    redisLockStockFacade.issueCoupon(couponIssueParam, user);
                    System.out.println("Thread " + threadNumber + " - 성공");

                } catch (PessimisticLockingFailureException e) {
                    System.out.println("Thread " + threadNumber + " - 락 충돌 감지");

                } catch (Exception e) {
                    System.out.println("Thread " + threadNumber + " - " + e.getMessage());

                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        int count = userCouponRepository.countByCouponId(couponIssueParam.getCouponId());

        assertThat(count).isEqualTo(100);
    }*/
}