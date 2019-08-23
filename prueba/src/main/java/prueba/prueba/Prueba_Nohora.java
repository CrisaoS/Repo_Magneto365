package prueba.prueba;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Prueba_Nohora {
	
	By boxSearch = By.name("q");
	By generalResult = By.id("fprsl");
	By listResultsFound = By.xpath("//*[@class='bkWMgd']");
	
	public static WebDriver driver;
	public static String os = "";
	
	public Prueba_Nohora() {
		os = System.getProperty("os.name").toLowerCase();
	}

	/**
	 * This method will Init Browser, Driver & Launch URL
	 */
	public static void main(String[] args) throws InterruptedException {
		// String sDirectorioTrabajo = System.getProperty("user.dir");
		// System.out.println("El directorio de trabajo es: " + sDirectorioTrabajo);

		os = System.getProperty("os.name").toLowerCase();

		driver = null;

		if (os.contains("mac")) {
			driver = new ChromeDriver();
		} else {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver.exe");
			driver = new ChromeDriver();
		}

		driver.get("https://www.google.com.co");

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	/**
	 * This method will Enter a Word On 'Cuadro de Busqueda' Option
	 * 
	 * @return TRUE if is correct, FALSE otherwise
	 */
	public Boolean enterWordToSearch() {
		WebDriverWait wait = new WebDriverWait(driver, 1);
		
		try {
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(boxSearch)));
			
			if(driver.findElement(boxSearch).isDisplayed()) {
				driver.findElement(boxSearch).clear();
				driver.findElement(boxSearch).sendKeys("pruebaz");
				clickOnSearch();
				return true;
			}else {
				throw new IOException("ERROR SEARCH: WebElement 'Cuadro Busqueda' isn't Display!");
			}
		} catch (Exception e) {
			System.out.println("Catch_enterWordToSearch:" + e.getMessage());
			return false;
		}
	}
	
	/**
	 * This method will validate if the Correct word is presented
	 * 
	 * @return String With the Word Found
	 */
	public String verifyCorrectWord() {
		String text = "";
		
		try {
			driver.findElement(generalResult);
			if(driver.findElement(generalResult).isDisplayed()) {
				text = driver.findElement(generalResult).getText().toString().toLowerCase();
				// System.out.println("Text Found: " + text);
			}else {
				throw new IOException("ERROR WORD TO SEARCH: Word To Search isn't Display!");
			}
		} catch (Exception e) {
			System.out.println("Catch_verifyCorrectWord:" + e.getMessage());
		}
		return text;
	}
	
	/**
	 * This method will Click On 'Buscar' Option
	 */
	public void clickOnSearch() {
		driver.findElement(boxSearch).sendKeys(Keys.ENTER);
	}
	
	/**
	 * This method will Click On 'Correct Word' Option
	 */
	public void clickOnCorrectWord() {
		driver.findElement(generalResult).click();
	}
	
	/**
	 * This method will get Results from the Search
	 * 
	 * @return Int With Results Number
	 */
	public int getResultsList() {
		WebDriverWait wait = new WebDriverWait(driver, 1);
		int results = 0;

		try {
			clickOnCorrectWord();

			wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(listResultsFound)));
			List<WebElement> elemInSearch = driver.findElements(listResultsFound);
			int totalElem = elemInSearch.size();
			// System.out.println("Num:" + totalElem);

			if (totalElem < 1) {
				throw new IOException("ERROR #SEARCH: Search Number Invalid < 1!");
			} else {
				String[] textSearch = new String[totalElem];
				int i = 0;
				for (WebElement e : elemInSearch) {
					textSearch[i] = e.getText();
					// System.out.println("TextSearch: " + textSearch[i]);
					if (textSearch[i].contains("Resultados de la Web")) {
						List<WebElement> elemFound = e.findElements(By.cssSelector("div[class^='g']"));
						int resultsTemp = elemFound.size();
						results += resultsTemp;
					} else {
						i++;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Catch_getResultsList:" + e.getMessage());
		}
		// System.out.println("Total:" + results);
		return results;
	}
	
	/**
	 * This method will validate if Results Number is greater than 6
	 * 
	 * @return TRUE if is correct, FALSE otherwise
	 */
	public Boolean verifyTotalResult() {

		try {
			if(getResultsList() > 6) {
				return true;
			}else {
				throw new IOException("Error - Total Results is less than 6!");
			}
		} catch (Exception e) {
			System.out.println("Catch_verifyTotalResult:" + e.getMessage());
			return false;
		}
	}

}
