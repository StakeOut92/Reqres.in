package utilites;
import com.github.javafaker.Faker;


public class GenerateFakeMessage {

        public static String getFirstName(){
            Faker faker = new Faker();
            return faker.name().firstName();
        }

        public static String getJob(){
            Faker faker = new Faker();
            return faker.job().field();
        }
}
