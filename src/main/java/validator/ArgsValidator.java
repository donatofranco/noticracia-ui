package validator;

public class ArgsValidator implements Validator<String[]>{
    @Override
    public void validate(String[] input) {
        if (input.length != 1) {
            System.err.println("\nDebe existir un único argumento.\n");
            throw new RuntimeException();
        }
    }
}
