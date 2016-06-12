package testForProv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ATMrobbery {

    static ArrayList<Integer> amount = new ArrayList<>();
    static ArrayList<Integer> nominals = new ArrayList<>();
    static ArrayList<Integer> amountToIssue = new ArrayList<>();
    static ArrayList<Integer> nominalsToIssue = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        fillArray();

        while (amount.size() > 0) {
            System.out.println("");
            System.out.println("What do You need?");
            System.out.println("For get money, type \"A\". For check denominations in ATM, type \"B\"");
            inputChoice();
        }
    }

    private static void inputChoice() throws IOException {
        BufferedReader select = new BufferedReader(new InputStreamReader(System.in));
        switch (select.readLine()) {
            case "A":
                System.out.println("How much money do You need?");
                BufferedReader money = new BufferedReader(new InputStreamReader(System.in));
                isHaveRequiredAmount(money.readLine());
                break;
            case "B":
                getAmountNominals();
                break;
        }
    }

    private static void fillArray() {
        nominals.add(5);
        nominals.add(10);
        nominals.add(20);
        nominals.add(50);
        nominals.add(100);
        nominals.add(200);
        nominals.add(500);

        for (int i = 0; i < 7; i++) {
            amount.add(50);
        }
    }

    private static void isHaveRequiredAmount(String money) throws IOException {
        if (!checkString(money) || Integer.parseInt(money) <= 0) {
            System.out.println("Are You realy want money?");
            System.out.println("How much money do You need?");
            BufferedReader newSelect = new BufferedReader(new InputStreamReader(System.in));
            isHaveRequiredAmount(newSelect.readLine());
        } else {
            isHaveMoney(Integer.parseInt(money));
        }
    }

    private static boolean checkString(String string) {
        try {
            Integer.parseInt(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static void isHaveMoney(int money) throws IOException {

        if (!isCanGetMoney(money) && money > 0) {
            System.out.println("ATM can not give the required amount. " +
                    "Please select one of the following amounts: " +
                    (money - (money % nominals.get(0))) + " UAH, or " +
                    (money - (money % nominals.get(0)) + nominals.get(0)) + " UAH.");
            BufferedReader newSelect = new BufferedReader(new InputStreamReader(System.in));
            isHaveRequiredAmount(newSelect.readLine());
        } else {
            giveMoney(money);
        }
    }

    private static void giveMoney(int money) throws IOException {
        if (money > getSum()) {
            System.out.println("ATM doesn`t have much money");
        } else if (money <= 0) {
            isHaveRequiredAmount(Integer.toString(money));
        }
        else {
            int k = amount.size() - 1;
            while (k >= 0)
                if (nominals.get(k) > money) {
                    k--;
                } else {
                    toIssue(nominals.get(k));
                    money -= nominals.get(k);
                    amount.set(k, (amount.get(k) - 1));
                    if (checkAmount()) {
                        k--;
                    }
                }
            System.out.println("You received next denominations:");
            for (int i = 0; i < nominalsToIssue.size(); i++) {
                System.out.println(nominalsToIssue.get(i) + " UAH - " + amountToIssue.get(i) + " banknotes");

            }
            System.out.println("Total money left in the ATM = " + getSum() + " UAH");
        }
        nominalsToIssue.clear();
        amountToIssue.clear();

    }

    private static boolean isCanGetMoney(int money) {
        return !(money % nominals.get(0) != 0 && ((money - (money % nominals.get(0)) + nominals.get(0)) <= getSum()));
    }

    private static void toIssue(int banknotes) {
        boolean clone = false;

        for (Integer i : nominalsToIssue) {
            if (i == banknotes) {
                clone = true;
            }
        }

        for (int j = 0; j < nominalsToIssue.size(); j++) {
            if (nominalsToIssue.size() == 0) {
                nominalsToIssue.add(banknotes);
                amountToIssue.add(1);
            }
            if (nominalsToIssue.get(j) == banknotes) {
                amountToIssue.set(j, (amountToIssue.get(j) + 1));
            }
        }
        if (!clone) {
            nominalsToIssue.add(banknotes);
            amountToIssue.add(1);
        }
    }

    private static void getAmountNominals() {
        for (int i = 0; i < nominals.size(); i++) {
            System.out.println("Denomination: " + nominals.get(i) + ", left - " + amount.get(i));
        }
    }

    private static int getSum() {
        int sum = 0;
        for (int i = 0; i < amount.size(); i++) {
            sum += (nominals.get(i) * amount.get(i));
        }
        return sum;
    }

    private static boolean checkAmount() {
        for (int i = 0; i < amount.size(); i++) {
            if (amount.get(i) <= 0) {
                nominals.remove(i);
                amount.remove(i);
                return true;
            }
        }
        return false;
    }
}
