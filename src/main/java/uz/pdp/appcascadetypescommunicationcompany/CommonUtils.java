package uz.pdp.appcascadetypescommunicationcompany;

import uz.pdp.appcascadetypescommunicationcompany.entity.*;

import java.util.Random;

public class CommonUtils {
    public static Income createIncome(Double price, Simcard simcard, ClientMoveType clientMoveType, PaymentType paymentType) {
        Income income = new Income();
        income.setAmount(price);
        income.setSimcard(simcard);
        income.setClientMoveType(clientMoveType);
        income.setPaymentType(paymentType);
        return income;
    }

    public static Detail createDetail(String name, String description, ClientMoveType clientMoveType, Simcard simcard) {
        Detail detail = new Detail();
        detail.setName(name);
        detail.setDescription(description);
        detail.setSimcard(simcard);
        detail.setClientMoveType(clientMoveType);
        return detail;
    }


    public static Integer generateCode() {
        return new Random().nextInt((999999 - 100000) + 1) + 100000;
    }
}
