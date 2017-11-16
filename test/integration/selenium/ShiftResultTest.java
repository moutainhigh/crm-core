package integration.selenium;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Automated selenium test: registration, login, add all types of categories, add product for each one,
 * open shift, add client, add products from four categories, close client, test profit numbers on close shift page,
 * login, check statistics for the shift
 */
public class ShiftResultTest {
	@Test
	public void initAndTest() throws Exception{
		String driverLocation = System.getProperty("user.dir") + "\\test\\resources";
		System.setProperty("webdriver.chrome.driver", driverLocation + "\\chromedriver3.exe");
		WebDriver driver = new ChromeDriver();

		driver.manage().window().maximize();
//		Thread.sleep(2000);

		driver.get("http://localhost:8080");

		//init vars
		Double firstHourPrice = 300D;
		Double afterFirstHourPrice = 200D;

		Double cat1Prod1Price = 10D;
		Double cat1Prod1Cost = 9D;
		Double cat2Prod1Price = 10D;
		Double cat2Prod1Cost = 9D;
		Double cat3Prod1Price = 10D;
		Double cat3Prod1Cost = 9D;
		Double cat4Prod1Price = 10D;
		Double cat4Prod1Cost = 9D;

		Double cashBox = 1000D;
		Double bankCashBox = 1000D;

		//Registration
		driver.findElement(By.partialLinkText("Регистрация")).click();
		driver.findElement(By.name("firstName")).sendKeys("James");
		driver.findElement(By.name("lastName")).sendKeys("Bond");
		driver.findElement(By.name("email")).sendKeys("bond@gmail.com");
		driver.findElement(By.name("phone")).sendKeys("8007");
		driver.findElement(By.name("password")).sendKeys("bond");
		driver.findElement(By.name("form-password-repeat")).sendKeys("bond");
		driver.findElement(By.name("companyName")).sendKeys("BondCompany");
		driver.findElement(By.xpath("//*[@type='submit']")).click();

		//Login
		Thread.sleep(2000);
		WebElement username = driver.findElement(By.id("username"));
		username.sendKeys("bond@gmail.com");
		driver.findElement(By.id("password")).sendKeys("bond");
		driver.findElement(By.id("loginButton")).click();

		//StepByStep
		driver.findElement(By.xpath("//*[@type='number']")).sendKeys(firstHourPrice.toString());
		driver.findElement(By.id("buttonEndPrice")).click();
		Thread.sleep(1000);
		driver.findElement(By.name("nextHours")).sendKeys(afterFirstHourPrice.toString());
		driver.findElement(By.id("buttonEndPrice")).click();
		Thread.sleep(1000);
		driver.findElement(By.name("refBonus")).sendKeys("150");
		driver.findElement(By.id("buttonEndPrice")).click();
		Thread.sleep(1000);

		driver.findElement(By.name("name")).sendKeys("table1");
		driver.findElement(By.id("buttonAddBoard")).click();
		driver.findElement(By.name("name")).sendKeys("table2");
		driver.findElement(By.id("buttonAddBoard")).click();
		driver.findElement(By.name("name")).sendKeys("table3");
		driver.findElement(By.id("buttonAddBoard")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("buttonEndPrice")).click();
		Thread.sleep(1000);

		driver.findElement(By.name("settingsName")).sendKeys("gmail");
		driver.findElement(By.name("email")).sendKeys("bond@gmail.com");
		driver.findElement(By.name("password")).sendKeys("bond");
		driver.findElement(By.id("buttonEndPrice")).click();
		Thread.sleep(1000);
		driver.findElement(By.name("masterKey")).sendKeys("master_key");
		driver.findElement(By.id("buttonEndPrice")).click();
		Thread.sleep(1000);

		driver.findElement(By.id("showVKInputButton")).click();
		driver.findElement(By.id("inputAccessToken")).sendKeys("70843606992ba5fb5abbb7621705dcfd44741e9a9e0dab630bed9f9ec126530a0a5a9fea737b2af7af55d");
		driver.findElement(By.id("inputApplicationId")).sendKeys("6247124");
		driver.findElement(By.id("inputChatId")).sendKeys("12");
		driver.findElement(By.id("buttonEndPrice")).click();
		Thread.sleep(1000);

//		add four categories of different types
		driver.findElement(By.xpath("//*[@class='btn  btn-primary']")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("loginProd2")).sendKeys("Cat1");
		driver.findElement(By.id("saveCat")).click();

		driver.findElement(By.xpath("//*[@class='btn  btn-primary']")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("loginProd2")).sendKeys("Cat2withDirty");
		driver.findElement(By.id("addCatDirtyProfit")).click();
		driver.findElement(By.id("saveCat")).click();

		driver.findElement(By.xpath("//*[@class='btn  btn-primary']")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("loginProd2")).sendKeys("Cat3withPrice");
		driver.findElement(By.id("addCatFloatingPrice")).click();
		driver.findElement(By.id("saveCat")).click();

		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@class='btn  btn-primary']")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("loginProd2")).sendKeys("Cat4withDirtyAndPrice");
		driver.findElement(By.id("addCatDirtyProfit")).click();
		driver.findElement(By.id("addCatFloatingPrice")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("saveCat")).click();

//		add ingredients
		WebElement element = driver.findElement(By.cssSelector("a[href='/boss/menu/ingredients']"));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();" , element);

		driver.findElement(By.xpath("//*[@placeholder='Введите название']")).sendKeys("Молоко");
		driver.findElement(By.xpath("//*[@placeholder='Введите размерность']")).sendKeys("л");
		driver.findElement(By.id("ingAmountInput")).clear();
		driver.findElement(By.id("ingAmountInput")).sendKeys("5");
		driver.findElement(By.id("ingCostInput")).clear();
		driver.findElement(By.id("ingCostInput")).sendKeys("250");
		driver.findElement(By.xpath("//*[@class='btn btn-success btn-lg col-md-offset-5']")).click();

		driver.findElement(By.xpath("//*[@placeholder='Введите название']")).sendKeys("Сыр");
		driver.findElement(By.xpath("//*[@placeholder='Введите размерность']")).sendKeys("кг");
		driver.findElement(By.id("ingAmountInput")).clear();
		driver.findElement(By.id("ingAmountInput")).sendKeys("3");
		driver.findElement(By.id("ingCostInput")).clear();
		driver.findElement(By.id("ingCostInput")).sendKeys("600");
		driver.findElement(By.xpath("//*[@class='btn btn-success btn-lg col-md-offset-5']")).click();

		driver.findElement(By.xpath("//*[@placeholder='Введите название']")).sendKeys("Сахар");
		driver.findElement(By.xpath("//*[@placeholder='Введите размерность']")).sendKeys("кг");
		driver.findElement(By.id("ingAmountInput")).clear();
		driver.findElement(By.id("ingAmountInput")).sendKeys("2");
		driver.findElement(By.id("ingCostInput")).clear();
		driver.findElement(By.id("ingCostInput")).sendKeys("200");
		driver.findElement(By.xpath("//*[@class='btn btn-success btn-lg col-md-offset-5']")).click();

		driver.findElement(By.xpath("//*[@placeholder='Введите название']")).sendKeys("Кокс");
		driver.findElement(By.xpath("//*[@placeholder='Введите размерность']")).sendKeys("гр");
		driver.findElement(By.id("ingAmountInput")).clear();
		driver.findElement(By.id("ingAmountInput")).sendKeys("50");
		driver.findElement(By.id("ingCostInput")).clear();
		driver.findElement(By.id("ingCostInput")).sendKeys("10000");
		driver.findElement(By.xpath("//*[@class='btn btn-success btn-lg col-md-offset-5']")).click();

		((JavascriptExecutor)driver).executeScript("arguments[0].click();" , driver.findElement(By.cssSelector("a[href='/boss/menu']")));

//		//////////////Add products for all four categories
		driver.findElement(By.partialLinkText("Cat1")).click();
		driver.findElement(By.xpath("//*[@class='pull-right btn btn-primary']")).click();
		Thread.sleep(1000);
		Integer catId = 0;
		driver.findElements(By.xpath("//*[@placeholder='Название']")).get(catId).sendKeys("Cat1Prod1");
		driver.findElements(By.xpath("//*[@placeholder='Описание']")).get(catId).sendKeys("Cat1Prod1");
		driver.findElements(By.xpath("//*[@placeholder='Цена']")).get(catId).sendKeys(cat1Prod1Price.toString());
		driver.findElements(By.xpath("//*[@placeholder='Себестоимость']")).get(catId).sendKeys(cat1Prod1Cost.toString());
		driver.findElements(By.id("saveNewProductData")).get(catId).click();

		catId = 1;
		Thread.sleep(1000);
		driver.findElement(By.partialLinkText("Cat2")).click();
		driver.findElements(By.xpath("//*[@class='pull-right btn btn-primary']")).get(catId).click();
		Thread.sleep(1000);
		driver.findElements(By.xpath("//*[@placeholder='Название']")).get(catId).sendKeys("Cat2WithDirtyProd1");
		driver.findElements(By.xpath("//*[@placeholder='Описание']")).get(catId).sendKeys("Cat2WithDirtyProd1");
		driver.findElements(By.xpath("//*[@placeholder='Цена']")).get(catId).sendKeys(cat2Prod1Price.toString());
		driver.findElements(By.xpath("//*[@placeholder='Себестоимость']")).get(catId).sendKeys(cat2Prod1Cost.toString());
		driver.findElements(By.id("saveNewProductData")).get(catId).click();

		catId = 2;
		Thread.sleep(1000);
		driver.findElement(By.partialLinkText("Cat3")).click();
		driver.findElements(By.xpath("//*[@class='pull-right btn btn-primary']")).get(catId).click();
		Thread.sleep(1000);
		driver.findElements(By.xpath("//*[@placeholder='Название']")).get(catId).sendKeys("Cat3withPriceProd1");
		driver.findElements(By.xpath("//*[@placeholder='Описание']")).get(catId).sendKeys("Cat3withPriceProd1");
		driver.findElements(By.xpath("//*[@placeholder='Себестоимость']")).get(catId).sendKeys(cat3Prod1Cost.toString());
		driver.findElements(By.id("saveNewProductData")).get(catId).click();

		catId = 3;
		Thread.sleep(1000);
		driver.findElement(By.partialLinkText("Cat4")).click();
		driver.findElements(By.xpath("//*[@class='pull-right btn btn-primary']")).get(catId).click();
		Thread.sleep(1000);
		driver.findElements(By.xpath("//*[@placeholder='Название']")).get(catId).sendKeys("Cat4withDirtyAndPriceProd1");
		driver.findElements(By.xpath("//*[@placeholder='Описание']")).get(catId).sendKeys("Cat4withDirtyAndPriceProd1");
		driver.findElements(By.xpath("//*[@placeholder='Себестоимость']")).get(catId).sendKeys(cat4Prod1Cost.toString());
		driver.findElements(By.id("saveNewProductData")).get(catId).click();
		Thread.sleep(1000);

//		SHIFT START
		((JavascriptExecutor)driver).executeScript("arguments[0].click();" , driver.findElement(By.cssSelector("a[href='/manager']")));
		driver.findElement(By.partialLinkText("Начать")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@name='cashBox']")).sendKeys(cashBox.toString());
		driver.findElement(By.xpath("//*[@name='bankCashBox']")).sendKeys(bankCashBox.toString());
		driver.findElement(By.xpath("//*[@class='btn btn-danger btn-lg']")).click();
		Thread.sleep(1000);

//		BILL OPENING
		driver.findElement(By.xpath("//*[@data-target='#calculateModal']")).click();
		Thread.sleep(1000);
		driver.findElements(By.xpath("//*[@name='boardId']")).get(0).sendKeys("table1");
		driver.findElement(By.xpath("//button[@type='submit' and contains(., 'Создат')]")).click();
		Thread.sleep(1000);

//		ORDER
		((JavascriptExecutor) driver).executeScript("el = document.elementFromPoint(711, 120); el.click();");
		((JavascriptExecutor)driver).executeScript("arguments[0].click();" , driver.findElement(By.cssSelector("button[href='#menuModal1']")));
		Thread.sleep(1000);
		//add four products
		driver.findElement(By.partialLinkText("Cat1")).click();
		driver.findElements(By.xpath("//button[starts-with(@onclick, \"createLayerProductAjax(\")]")).get(2).click();
		Thread.sleep(1000);
		driver.findElement(By.partialLinkText("Cat2")).click();
		driver.findElements(By.xpath("//button[starts-with(@onclick, \"createLayerProductAjax(\")]")).get(3).click();
		Thread.sleep(1000);
		driver.findElement(By.partialLinkText("Cat3")).click();
		driver.findElements(By.xpath("//input[starts-with(@id, \"inputPriceInCategory\")]")).get(0).sendKeys("10");
		driver.findElements(By.xpath("//button[starts-with(@onclick, \"createLayerProductWithFloatingPriceAjax(\")]")).get(2).click();
		Thread.sleep(1000);
		driver.findElement(By.partialLinkText("Cat4")).click();
		driver.findElements(By.xpath("//input[starts-with(@id, \"inputPriceInCategory\")]")).get(1).sendKeys("10");
		driver.findElements(By.xpath("//button[starts-with(@onclick, \"createLayerProductWithFloatingPriceAjax(\")]")).get(3).click();

		Thread.sleep(1000);
		((JavascriptExecutor) driver).executeScript("el = document.elementFromPoint(10, 150); el.click();");
		driver.findElement(By.id("checked1")).click();
		driver.findElement(By.xpath("//button[@class='btn btn-danger' and contains(., 'Рассч')]")).click();
//		CLOSE SHIFT
		Thread.sleep(1000);
		driver.findElements(By.xpath("//button[starts-with(@onclick, \"closeClient(\")]")).get(0).click();
		Thread.sleep(1000);
		((JavascriptExecutor)driver).executeScript("arguments[0].click();" , driver.findElement(By.cssSelector("a[href='/manager/shift/close']")));

		Double totalProductPrice = cat1Prod1Price + cat3Prod1Price;
		Double expectedTotalCashBox = totalProductPrice + cashBox + bankCashBox + firstHourPrice;
		Double expectedDirtyProfit = totalProductPrice + firstHourPrice;
		assertEquals(expectedTotalCashBox, Double.parseDouble(driver.findElement(By.name("totalCashBox")).getAttribute("value")),0.1);
		assertEquals(expectedDirtyProfit, Double.parseDouble(driver.findElement(By.name("allPrice")).getAttribute("value")),0.1);

		//user inputs to close shift
		Double expectedCashBox = cashBox + totalProductPrice + firstHourPrice;
		Double expectedBankCashBox = bankCashBox;
		driver.findElement(By.id("cashBox")).sendKeys(expectedCashBox.toString());
		driver.findElement(By.id("bankCashBox")).sendKeys(expectedBankCashBox.toString());
		driver.findElement(By.xpath("//*[@onclick='checkInputData()']")).click();

//		RELogin
		Thread.sleep(2000);
		driver.findElement(By.id("username")).sendKeys("bond@gmail.com");
		driver.findElement(By.id("password")).sendKeys("bond");
		driver.findElement(By.id("loginButton")).click();

//		Check statistics
		element = driver.findElement(By.cssSelector("a[href='/boss/totalStatistics']"));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();" , element);
		//dirtyProfit
		Double actualDirtyProfit = Double.parseDouble(driver.findElements(By.xpath("//*[@class='menuDirtyCost']")).get(0).getText());
		expectedDirtyProfit = totalProductPrice;
		assertEquals(expectedDirtyProfit, actualDirtyProfit, 0.1);
		//timeProfit
		Double actualTimeProfit = Double.parseDouble(driver.findElements(By.xpath("//*[@class='timeCost']")).get(0).getText());
		Double expectedTimeProfit = firstHourPrice;
		assertEquals(expectedTimeProfit, actualTimeProfit, 0.1);

		driver.quit();
	}
}
