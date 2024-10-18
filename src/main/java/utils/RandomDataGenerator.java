package utils;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class RandomDataGenerator {

    public static String generateUniqueName()
    {
      String currentTime = new SimpleDateFormat("mmssSSS").format(new Date());
      return "Test" + currentTime;
    }

    public static String generateUniqueInteger()
    {
        return new SimpleDateFormat("mmssSSS").format(new Date());
    }

    public static String generateUniqueEmail()
    {
        String currentTime = new SimpleDateFormat("mmssSSS").format(new Date());
        return "test" + currentTime + "@gmail.com";
    }

    public static String generateStrongPassword()
    {
        String currentTime = new SimpleDateFormat("mmssSSS").format(new Date());
        return "Test" + "@%^" + currentTime;
    }

    public static String generateName()
    {
       return new Faker().name().firstName().replace("'", "''");
    }

    public static String generateCompany()
    {
        return new Faker().company().name().replace("'", "''");
    }

    public static String generateAddress()
    {
        return new Faker().address().fullAddress().replace("'", "''");
    }

    public static String generateCity()
    {
        return new Faker().address().city().replace("'", "''");
    }

    public static String generateZipCode()
    {
        return new Faker().address().zipCode();
    }

    public static String generateDescription()
    {
        return new Faker().lorem().paragraph();
    }


    public static String generateItemFromList(List<String> list)
    {
        int randomIndex = new Random().nextInt(list.size());
        return list.get(randomIndex);
    }

    public static String generateNumericalString (int min,int max)
    {
        int randomNumber = new Random().nextInt(max-min)+min;
        return String.valueOf(randomNumber);
    }
}
