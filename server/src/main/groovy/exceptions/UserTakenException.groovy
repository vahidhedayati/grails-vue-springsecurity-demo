package exceptions

class UserTakenException extends Exception {

    @Override
    public Throwable fillInStackTrace() {
        // do nothing
        return this
    }
}
