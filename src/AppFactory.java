final class AppFactory {
    private AppFactory() {}

    static Container build() {
        final Container c = new Container();

        c.register(BankAccountRepository.class, new Container.Provider<BankAccountRepository>() {
            public BankAccountRepository get() { return new InMemoryBankAccountRepository(); }
        });
        c.register(CategoryRepository.class, new Container.Provider<CategoryRepository>() {
            public CategoryRepository get() { return new InMemoryCategoryRepository(); }
        });
        c.register(OperationRepository.class, new Container.Provider<OperationRepository>() {
            public OperationRepository get() { return new InMemoryOperationRepository(); }
        });

        c.register(FinanceService.class, new Container.Provider<FinanceService>() {
            public FinanceService get() {
                return new FinanceService(
                        c.get(BankAccountRepository.class),
                        c.get(CategoryRepository.class),
                        c.get(OperationRepository.class)
                );
            }
        });
        c.register(AnalyticsService.class, new Container.Provider<AnalyticsService>() {
            public AnalyticsService get() {
                return new AnalyticsService(
                        c.get(OperationRepository.class),
                        c.get(CategoryRepository.class)
                );
            }
        });
        c.register(BalanceRecalculator.class, new Container.Provider<BalanceRecalculator>() {
            public BalanceRecalculator get() {
                return new BalanceRecalculator(
                        c.get(BankAccountRepository.class),
                        c.get(OperationRepository.class)
                );
            }
        });
        c.register(Stats.class, new Container.Provider<Stats>() {
            public Stats get() { return new Stats(); }
        });

        return c;
    }
}
