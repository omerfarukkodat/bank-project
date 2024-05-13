package com.kodat.of.bankproject.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.kodat.of.bankproject.dto.EmailDetails;
import com.kodat.of.bankproject.entity.Transaction;
import com.kodat.of.bankproject.entity.User;
import com.kodat.of.bankproject.repository.TransactionRepository;
import com.kodat.of.bankproject.repository.UserRepository;
import com.kodat.of.bankproject.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * retrieve list of transactions within a data range given an account number
 * generate a pdf file of transactions
 * send the file via email
 */
@AllArgsConstructor
@Service
@Slf4j
public class BankStatement {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private EmailService emailService;

    private static final String FILE = "/Users/farukkodat/Downloads/mystatement1.pdf";
    private static final Logger LOGGER = LoggerFactory.getLogger(BankStatement.class);

    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate) throws DocumentException, MessagingException, FileNotFoundException {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
        LOGGER.info("Start date is {}", start);
        LOGGER.info("End date is {}", end);
        List<Transaction> transactionList = getFilteredTransactions(accountNumber, start, end);

        User user = userRepository.findByAccountNumber(accountNumber);
        String customerName = user.getFirstName() + " " + user.getOtherName() + " " + user.getLastName();

        createPDF(transactionList, user, startDate, endDate, customerName);
        sendStatementEmail(user, FILE);
        return transactionList;
    }


    private List<Transaction> getFilteredTransactions(String accountNumber, LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findAll()
                .stream()
                .filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
                .filter(transaction -> isAfterOrEqual(transaction.getCreateDate(), startDate))
                .filter(transaction -> isBeforeOrEqual(transaction.getCreateDate(), endDate))
                .toList();
    }

    private void createPDF(List<Transaction> transactionList, User user, String startDate, String endDate, String customerName) throws DocumentException, MessagingException, FileNotFoundException {

        Document document = new Document(PageSize.A4);
        try (OutputStream stream = new FileOutputStream(FILE)) {
            PdfWriter.getInstance(document, stream);
            document.open();
            addBankInfo(document);
            addStatementInfo(document, user, startDate, endDate, customerName);
            addTransactions(document, transactionList);
            document.close();

        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void addBankInfo(Document document) throws DocumentException {
        PdfPTable bankInfoTable = new PdfPTable(1);
        PdfPCell bankName = new PdfPCell(new Phrase("Bank Of Turkey"));
        bankName.setBorder(0);
        bankName.setBackgroundColor(BaseColor.GRAY);
        bankName.setPadding(20f);

        PdfPCell bankAddress = new PdfPCell(new Phrase("34 , Umraniye , Istanbul"));
        bankAddress.setBorder(0);
        bankInfoTable.addCell(bankName);
        bankInfoTable.addCell(bankAddress);
        document.add(bankInfoTable);
    }

    private void addStatementInfo(Document document, User user, String startDate, String endDate, String customerName) throws DocumentException {

        PdfPTable statementInfo = new PdfPTable(2);
        PdfPCell customerInfo = new PdfPCell(new Phrase("Start Date: " + startDate));
        customerInfo.setBorder(0);
        PdfPCell statement = new PdfPCell(new Phrase("State Of Account"));
        statement.setBorder(0);
        PdfPCell lastDate = new PdfPCell(new Phrase("End Date: " + endDate));
        lastDate.setBorder(0);
        PdfPCell customerNameInfo = new PdfPCell(new Phrase("Customer Name: " + customerName));
        customerNameInfo.setBorder(0);
        PdfPCell space = new PdfPCell();
        space.setBorder(0);
        PdfPCell address = new PdfPCell(new Phrase("Customer Address: " + user.getAddress()));
        address.setBorder(0);

        statementInfo.addCell(customerInfo);
        statementInfo.addCell(statement);
        statementInfo.addCell(lastDate);
        statementInfo.addCell(customerNameInfo);
        statementInfo.addCell(space);
        statementInfo.addCell(address);


        document.add(statementInfo);


    }

    private void addTransactions(Document document, List<Transaction> transactionList) throws DocumentException {
        PdfPTable transactionsTable = new PdfPTable(4);
        PdfPCell date = new PdfPCell(new Phrase("Date"));
        date.setBackgroundColor(BaseColor.LIGHT_GRAY);
        date.setBorder(0);
        PdfPCell transactionType = new PdfPCell(new Phrase("Transaction Type"));
        transactionType.setBackgroundColor(BaseColor.LIGHT_GRAY);
        transactionType.setBorder(0);
        PdfPCell amount = new PdfPCell(new Phrase("Amount"));
        amount.setBackgroundColor(BaseColor.LIGHT_GRAY);
        amount.setBorder(0);
        PdfPCell status = new PdfPCell(new Phrase("Status"));
        status.setBackgroundColor(BaseColor.LIGHT_GRAY);
        status.setBorder(0);

        transactionsTable.addCell(date);
        transactionsTable.addCell(transactionType);
        transactionsTable.addCell(amount);
        transactionsTable.addCell(status);


        transactionList.forEach(transaction -> {
            transactionsTable.addCell(new Phrase(transaction.getCreateDate().toString()));
            transactionsTable.addCell(new Phrase(transaction.getTransactionType()));
            transactionsTable.addCell(new Phrase(transaction.getAmount().toString()));
            transactionsTable.addCell(new Phrase(transaction.getStatus().toString()));
        });

        document.add(transactionsTable);

    }

    private void sendStatementEmail(User user, String FILE) throws MessagingException {
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("STATEMENT OF ACCOUNT")
                .messageBody("You can see your requested account statement attachment")
                .attachment(FILE)
                .build();
        emailService.sendEmailWithAttachment(emailDetails);
    }


    @Transactional
    public void deleteTransaction(String transactionId) {
        transactionRepository.deleteByTransactionId(transactionId);

    }

    private boolean isBeforeOrEqual(LocalDate localDate1, LocalDate localDate2) {
        return !localDate1.isAfter(localDate2);
    }

    private boolean isAfterOrEqual(LocalDate localDate1, LocalDate localDate2) {
        return !localDate1.isBefore(localDate2);
    }

}
