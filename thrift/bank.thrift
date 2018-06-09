namespace java bank

typedef string PESEL
typedef string GUID
typedef string Currency
typedef string Date

struct Account {
    1: PESEL pesel,
    2: string firstname,
    3: string lastname,
    4: double incomeThreshold,
    5: string nativeCurrency,
}

struct AccountDetails {
    1: double balance,
    2: Currency currency,
    3: bool isPremium,
    4: GUID guid,
}

struct LoanCosts {
    1: double nativeCurrencyCost,
    2: double requestedCurrencyCost,
}

struct LoanParameters {
    1: Currency currency,
    2: double moneyAmount,
    3: Date startDate,
    4: Date closeDate,
}

exception AuthorizationException {
    1: string why
}

exception InvalidArgumentException {
    1: string why
}

service AccountManagement {
    AccountDetails createAccount(1: Account account) throws(1: InvalidArgumentException authorizationException),
}

service AccountService {
    AccountDetails getAccountDetails(1: GUID guid) throws(1: AuthorizationException authorizationException),
}

service PremiumAccountService extends AccountService {
    LoanCosts getLoanDetails(1: GUID guid, 2: LoanParameters loanParameters) throws (
        1: AuthorizationException authorizationException,
        2: InvalidArgumentException invalidArgumentException,
    ),
}
