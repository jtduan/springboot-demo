package rest.constants;

import org.junit.Assert;
import org.junit.Test;

public class MethodTest{

    @Test
    public void testGenerateEmail() throws Exception {
        for(int i=0;i<1000;i++) {
            Assert.assertTrue(RandomGenerator.email().matches("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$"));
        }
    }
}
