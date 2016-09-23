package rest.constants;

import rest.entity.User;

import java.util.Random;

public class RandomGenerator{
    private static Random random = new Random();
    public static String text(int length){
        char[] chars = new char[]{
                '1','2','3','4','5','6','7','8','9','0',
                'q','w','e','r','t','y','u','i','o','p','a','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m',
                'Q','W','E','R','T','Y','U','I','O','P','A','S','D','F','G','H','J','K','L','Z','X','C','V','B','N','M'
        };
        StringBuilder sb = new StringBuilder(length);
        for(int i=0;i<length;i++) {
            sb.append(chars[random.nextInt(61)]);
        }
        return sb.toString();
    }

    public static int getRandom(int max){
        if(max<=0){
            return 0;
        }
        return random.nextInt(max);
    }

    public static String email(){
        return new StringBuilder().append(text(10))
                .append("@")
                .append(text(2))
                .append(".")
                .append(text(3))
                .toString();
    }

    public static User getRandomUser(){
        return new User(RandomGenerator.email(), RandomGenerator.text(5), RandomGenerator.text(4));
    }
}
