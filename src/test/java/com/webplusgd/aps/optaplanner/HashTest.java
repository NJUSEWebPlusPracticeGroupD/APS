package com.webplusgd.aps.optaplanner;

import com.webplusgd.aps.optaplanner.domain.Order;
import com.webplusgd.aps.optaplanner.domain.Shift;
import com.webplusgd.aps.optaplanner.domain.Task;
import com.webplusgd.aps.optaplanner.domain.resource.GroupResource;
import com.webplusgd.aps.optaplanner.domain.resource.Resource;
import com.webplusgd.aps.optaplanner.utils.DataUtil;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Rollingegg
 * @create_time 12/27/2020 6:48 PM
 */
public class HashTest {
    @Test
    public void testHashCode(){
        GroupResource g1=new GroupResource(100,"g1",new Shift());
        GroupResource g2=new GroupResource(101,"g1",new Shift());
        GroupResource g3=new GroupResource(11,"g1",new Shift());
        Map<Resource, Integer> map=new HashMap<>();
        map.put(g1,1);
        map.put(g2,2);
        assertEquals(1,map.size());
        assertEquals(2,map.get(g3));
    }

}
