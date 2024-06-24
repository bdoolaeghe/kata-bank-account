package my.bank.account;

record Amount(double value, Currency currency) {

    Amount {
        if (value < 0) {
            throw new IllegalArgumentException("Amount cannot be negative (was: " + value + ")");
        }
    }

    static Amount of(double value, Currency currency) {
        return new Amount(value, currency);
    }

    Amount plus(Amount added) {
        checkCurrencyIsSame(added);
        return new Amount(value + added.value, currency);
    }

    Amount minus(Amount subtracted) {
        checkCurrencyIsSame(subtracted);
        if (subtracted.gt(this)) {
            throw new IllegalArgumentException("Can't subtract " + subtracted + " because it's greater than current amount (" + this + ")");
        }
        return new Amount(value - subtracted.value, currency);
    }

    private void checkCurrencyIsSame(Amount anotherAmount) {
        if (anotherAmount.currency != currency) {
            throw new IllegalArgumentException("Amount " + anotherAmount + " has not same currency as " + this);
        }
    }

    private boolean gt(Amount another) {
        return currency == another.currency &&
               value > another.value;
    }

    @Override
    public String toString() {
        return value + " " + currency;
    }

}
