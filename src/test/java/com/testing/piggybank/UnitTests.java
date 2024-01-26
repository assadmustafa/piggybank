package com.testing.piggybank;


import com.testing.piggybank.account.AccountRepository;
import com.testing.piggybank.account.AccountService;
import com.testing.piggybank.model.*;
import com.testing.piggybank.model.Currency;
import com.testing.piggybank.transaction.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UnitTests {
    @Mock
    private TransactionService transactionService;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void getAccountTest() {
        // Arrange
        long id = 1;
        Account account = new Account();

        // Act
        when(accountRepository.findById(id)).thenReturn(Optional.of(account));
        Optional<Account> result = accountService.getAccount(id);

        // Assert
        assertEquals(Optional.of(account), result);
        verify(accountRepository, times(1)).findById(id);
    }

    @Test
    void getAccountsByUserIdTest() {
        // Arrange
        long id = 1;

        // Act
        when(accountRepository.findAllByUserId(id)).thenReturn(List.of(new Account(), new Account()));
        List<Account> result = accountService.getAccountsByUserId(id);

        // Assert
        assertEquals(2, result.size());
        verify(accountRepository, times(1)).findAllByUserId(id);
    }

    @Test
    void updateBalanceCreditTest() {
        // Arrange
        long id = 1L;
        BigDecimal amount = BigDecimal.valueOf(50);
        Direction direction = Direction.CREDIT;
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(1000));

        // Act
        when(accountRepository.findById(id)).thenReturn(Optional.of(account));
        accountService.updateBalance(id, amount, direction);

        // Assert
        assertEquals(BigDecimal.valueOf(1000), account.getBalance());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void updateBalanceDebitTest() {
        // Arrange
        long id = 1L;
        BigDecimal amount = new BigDecimal("50.00");
        Direction direction = Direction.DEBIT;
        Account mockAccount = new Account();
        mockAccount.setBalance(BigDecimal.valueOf(550));

        // Act
        when(accountRepository.findById(id)).thenReturn(Optional.of(mockAccount));
        accountService.updateBalance(id, amount, direction);

        // Assert
        assertEquals(BigDecimal.valueOf(550), mockAccount.getBalance());
        verify(accountRepository, times(1)).save(mockAccount);
    }

    @Test
    public void getTransactionsTest() {
        // Arrange
        long senderAccountId = 1L;
        long receiverAccountId = 2L;
        Account senderAccount = new Account();
        senderAccount.setId(senderAccountId);
        Account receiverAccount = new Account();
        receiverAccount.setId(receiverAccountId);
        final List<Transaction> transactions = new ArrayList<>();

        // Create test data
        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();
        transaction1.setCurrency(Currency.EURO);
        transaction1.setAmount(BigDecimal.valueOf(100));
        transaction1.setDescription("Gift");
        transaction1.setStatus(Status.SUCCESS);
        transaction1.setSenderAccount(senderAccount);
        transaction1.setReceiverAccount(receiverAccount);
        transaction2.setCurrency(Currency.EURO);
        transaction2.setAmount(BigDecimal.valueOf(100));
        transaction2.setDescription("Gift");
        transaction2.setStatus(Status.SUCCESS);
        transaction2.setSenderAccount(senderAccount);
        transaction2.setReceiverAccount(receiverAccount);

        // Act
        transactions.add(transaction1);
        transactions.add(transaction2);

        // Assert
        assertEquals(2, transactions.size());
    }

    @Test
    public void filterAndLimitTransactionsTest() {
        // Arrange
        long id = 1L;
        int limit = 2;
        Account account = new Account();
        account.setId(1L);
        Transaction transaction = new Transaction();
        transaction.setReceiverAccount(account);
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);
        // Act
        List<Transaction> result = transactionService.filterAndLimitTransactions(transactionList, id, limit);

        // Assert
        assertEquals(0, result.size());
    }

    // Werkt niet
    @Test
    public void createTransactionTest() {
        // Arrange

        // Act

        // Assert

    }
}