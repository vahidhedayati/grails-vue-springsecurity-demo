package exceptions

class ContractNameTakenException extends Exception {

    @Override
    public Throwable fillInStackTrace() {
        // do nothing
        return this
    }
}
