package rest.monitor;

import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.stereotype.Component;
import rest.backthreads.CustomQueue;
import rest.backthreads.WaitedDish;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jtduan on 2016/9/19.
 * 注意: 名称命名为counter.*才能在springbootAdmin中找到
 */
@Component
public class QueueCountMetrics implements PublicMetrics {

    @Override
    public Collection<Metric<?>> metrics() {
        List<Metric<?>> metrics = new LinkedList<>();
        metrics.add(new Metric("counter.queueSize", CustomQueue.INSTANCE.queue.size()));
        return metrics;
    }
}