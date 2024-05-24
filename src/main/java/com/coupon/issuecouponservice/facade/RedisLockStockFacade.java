package com.coupon.issuecouponservice.facade;

/*@Component
@RequiredArgsConstructor
public class RedisLockStockFacade {

    private final RedissonClient redissonClient;

    private final CouponService couponService;

    public void issueCoupon(CouponIssueParam couponIssueParam, User user){
        RLock lock = redissonClient.getLock(couponIssueParam.getCouponId().toString());

        try {
            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);

            if (!available) {
                System.out.println("lock 획득 실패");
                return;
            }
            couponService.issueCoupon(couponIssueParam, user);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        } finally {
            lock.unlock();
        }
    }

}*/
