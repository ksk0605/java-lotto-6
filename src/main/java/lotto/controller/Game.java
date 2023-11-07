package lotto.controller;

import lotto.domain.*;
import lotto.util.LottoNumberGenerator;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.List;

public class Game {
    public void start() {
        // 구매 금액 입력
        Cash cash = getCash();

        // 구매 로또 출력
        List<Lotto> lottos = getLottos(cash);
        OutputView.displayLottos(lottos);

        WinningNumbers winningNumbers = getWinningNumbers();

        WinningRanks winningRanks = new WinningRanks(lottos, winningNumbers);

        winningRanks.calculateRanks();

        Profit profit = new Profit();
        profit.calculateAmount(winningRanks.getRanks());

        WinningYield winningYield = new WinningYield(cash, profit);

        OutputView.displayWinningDetails(winningRanks.getRanks(), winningYield);
    }

    private Cash getCash() {
        int purchaseAmount;
        while (true) {
            try {
                purchaseAmount = InputView.getPurchaseAmount();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
        return new Cash(purchaseAmount);
    }

    private List<Lotto> getLottos(Cash cash) {
        LottoSeller lottoSeller = new LottoSeller(new LottoNumberGenerator());
        Customer customer = new Customer(cash, lottoSeller);
        return customer.purchaseLottos();
    }

    private WinningNumbers getWinningNumbers() {
        WinningNumber winningNumber = getWinningNumber();
        BonusNumber bonusNumber = getBonusNumber();
        return new WinningNumbers(winningNumber, bonusNumber);
    }

    private WinningNumber getWinningNumber() {
        List<Integer> winningNumbers;
        while (true) {
            try {
                winningNumbers = InputView.getWinningNumbers();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
        return new WinningNumber(winningNumbers);
    }

    private BonusNumber getBonusNumber() {
        int number;
        while (true) {
            try {
                number = InputView.getBonusNumber();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
        return new BonusNumber(number);
    }
}
