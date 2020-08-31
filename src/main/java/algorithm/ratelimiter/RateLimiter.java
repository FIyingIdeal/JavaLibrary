package algorithm.ratelimiter;

/**
 * 限流公共接口
 *
 * @see <a href="http://www.tianxiaobo.com/2019/05/18/%E7%AE%80%E6%9E%90%E9%99%90%E6%B5%81%E7%AE%97%E6%B3%95/">简析限流算法</a>
 *
 * @author yanchao
 * @date 2020-08-05 15:54
 */
public interface RateLimiter {

    /**
     * 用户获取计数器、令牌等标志性标识，标识当前可以接受请求
     * @throws RejectException 无法申请到令牌的时候会抛出该异常
     */
    void acquire() throws RejectException;
}
