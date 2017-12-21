package NewFeatures.streamtest.GroupTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/8/3.
 */
public class GroupTest {

    public List<Record> getRecords() {
        List<Record> records = new ArrayList<>();
        records.add(new Record("h1", "101", "A"));
        records.add(new Record("h1", "101", "B"));
        records.add(new Record("h1", "102", "A"));
        records.add(new Record("h1", "102", "C"));
        records.add(new Record("h2", "103", "D"));
        return records;
    }

    @Test
    public void test() {
        List<Record> records = getRecords();
        Map<String, List<Record>> map = records.stream().collect(
                Collectors.groupingBy(record -> record.getHotel() + "$" + record.getRoom())
        );
        List<List<Record>> result = new ArrayList<>();
        for (Map.Entry<String, List<Record>> entry : map.entrySet()) {
            if (entry.getValue().stream().map(Record::getName).collect(Collectors.toList()).contains("D")) {
                result.add(entry.getValue());
            }
        }
        System.out.println(map);
        System.out.println(result);
    }
}
