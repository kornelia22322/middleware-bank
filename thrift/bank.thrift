namespace java bank

struct Data {
    1: string pesel,
    2: string firstname,
    3: string surname,
    4: double income,
}

struct LoanCosts {
    1: double currencyCost,
}

struct LoanConfig {
    1: string currency,
    2: double moneyAmount,
    3: i32 daysCount,
}

struct AccountInfo {
    1: double balance,
    2: i32 guid,
    3: bool isPremium
}

exception AuthorizationException {
    1: string why
}

service AccountManagement {
    AccountInfo create(1: Data data)
}

service AccountService {
    AccountInfo getAccountInfo(1: i32 guid) throws(1: AuthorizationException authorizationException),
}

service PremiumAccountService extends AccountService {
    LoanCosts getLoanInfo(1: i32 guid, 2: LoanConfig loanConfig) throws (
        1: AuthorizationException authorizationException
    ),
}
