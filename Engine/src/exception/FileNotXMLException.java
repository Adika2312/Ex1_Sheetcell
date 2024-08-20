package exception;

public class FileNotXMLException extends RuntimeException {

    public FileNotXMLException() {
        super("The provided file is not in XML format.");
    }

    public FileNotXMLException(String message) {
        super(message);
    }
}
