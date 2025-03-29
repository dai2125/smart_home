package lcom.metrics;

import com.home.asm.InspectedClass;

public interface MetricExtractor {

    Metrics extractMetrics(InspectedClass inspectedClass);
}