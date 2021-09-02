import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws GeneralSecurityException, IOException {
        GoogleTableInteraction tableWriter = new GoogleTableInteraction(
                "1UZ6avRAzPkbS9F--1D9OK_HPvU_R6PSQSNrLiVT4Hwk",
                "Sheet"
        );
        System.out.println(tableWriter.readCell("A2"));
        tableWriter.writeCell("Hello", "A2");


    }
}
