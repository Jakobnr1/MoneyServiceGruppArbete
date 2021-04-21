package MoneyServiceAPP;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import MoneyService.ExchangeSite;

public class MoneyServiceGUI {

	public static void main(String[] args) {

		Runnable moneyServiceRun = new Runnable() {

			public void run() {
				ExchangeSite model = new ExchangeSite("North");
				JFrame jf = new MoneyServiceView();
				jf.setTitle("MoneyService North");
				jf.pack();
				jf.setVisible(true);
			}

		};
			SwingUtilities.invokeLater(moneyServiceRun);
	}

}
