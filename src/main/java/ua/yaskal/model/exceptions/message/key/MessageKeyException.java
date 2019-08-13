package ua.yaskal.model.exceptions.message.key;

/**
 * This class is created to implement an exception with a key for message in bundle
 * that can be displayed in frontend
 *
 * @author Nazar Yaskal
 */
public abstract class MessageKeyException extends RuntimeException {
    public abstract String getMessageKey();

    @Override
    public String getMessage() {
        return getMessageKey();
    }
}
