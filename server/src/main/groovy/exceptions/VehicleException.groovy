package exceptions

class VehicleException extends Exception {

    @Override
    public Throwable fillInStackTrace() {
        // do nothing
        return this
    }
}
