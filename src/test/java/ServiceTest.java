import com.netcracker.vmsis.kpp.entity.Line;
import com.netcracker.vmsis.kpp.service.LineService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LineService.class})
public class ServiceTest {


    @Autowired
    LineService service;


    @Test
    public void createLine() {
        int x1 = 1, y1 = 1, x2 = 5, y2 = 1;
        double length = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
        List<Integer> list = Arrays.asList(x1, x2, y1, y2);
        Optional<Line> expected = Optional.of(new Line(x2 - x1, y2 - y1, length));
        Optional<Line> actual = service.createLine(list);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createLine_NULL() {
        List<Integer> list = new ArrayList<>();
        Optional expected = Optional.empty();
        Optional<Line> actual = service.createLine(list);
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void createLine_BAD_PARAMS() {
        int x1 = 1, y1 = 2, x2 = 5, y2 = 1;
        List<Integer> list = Arrays.asList(x1, x2, y1, y2);
        Optional expected = Optional.empty();
        Optional<Line> actual = service.createLine(list);
        Assert.assertEquals(expected, actual);
    }
}
