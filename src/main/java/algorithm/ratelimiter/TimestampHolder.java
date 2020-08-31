package algorithm.ratelimiter;

import lombok.Data;

/**
 * @author yanchao
 * @date 2020-08-05 16:21
 */
@Data
public class TimestampHolder {

    private long timestamp;

    public TimestampHolder() {
        this(System.currentTimeMillis());
    }

    public TimestampHolder(long timestamp) {
        this.timestamp = timestamp;
    }
}
