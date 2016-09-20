package rest.java8;

import org.junit.Test;
import rest.entity.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Java8Test {

    @Test
    public void TestSerializerAndDeserializer() throws IOException {
        List<String> list = new ArrayList<String>();
        list.add("123");
        List<String> list2 = new ArrayList<String>();
        list.add("234");

        List<List<String>> list3 = new ArrayList<List<String>>();
        list3.add(list);
        list3.add(list2);

        List<Integer> ints = list3.stream().flatMap((List<String> l)->l.stream().map(Integer::parseInt)).collect(Collectors.toList());

        System.out.println(ints.size());

//        Stream.generate(()->return 4.56;);
//        list.stream().flatMapToInt((s) -> IntStream.builder().add(Integer.parseInt(s)).build()).forEach((int s)->{
    }

}
