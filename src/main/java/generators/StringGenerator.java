package generators;

import java.util.concurrent.ThreadLocalRandom;

public class StringGenerator {
    private String alphaNumericString = "0123456789"
            + "abcdefghijklmnopqrstuvxyz";

    public String generateString(int maxLineLength) {
        StringBuilder sb = new StringBuilder(maxLineLength);
        int minLineLength = 3;
        int randomSize = ThreadLocalRandom.current().nextInt(minLineLength, maxLineLength + 1);

        for (int i = 0; i < randomSize; i++) {
            int index = (int) (alphaNumericString.length() * Math.random());
            sb.append(alphaNumericString.charAt(index));
        }
        return sb.toString();
    }
}