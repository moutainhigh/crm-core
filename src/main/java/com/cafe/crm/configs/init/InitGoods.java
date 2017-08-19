package com.cafe.crm.configs.init;

import com.cafe.crm.models.cost.CostCategory;
import com.cafe.crm.services.interfaces.cost.CostCategoryService;
import com.cafe.crm.services.interfaces.cost.CostService;
import com.cafe.crm.utils.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;


@Component
public class InitGoods {

	private final CostService goodsService;
	private final CostCategoryService goodsCategoryService;
	private final TimeManager timeManager;

	@Value("${cost-category-name.salary-for-shift}")
	private String categoryNameSalaryForShift;

	@Autowired
	public InitGoods(CostCategoryService goodsCategoryService, CostService goodsService, TimeManager timeManager) {
		this.goodsCategoryService = goodsCategoryService;
		this.goodsService = goodsService;
		this.timeManager = timeManager;
	}

	@Transactional
	@PostConstruct
	public void init() {
//		CostCategory category1 = new CostCategory("Продукты питания");
//		CostCategory category2 = new CostCategory("Спиртные напитки");
//		CostCategory category3 = new CostCategory("Безалкогольные напитки");
//		CostCategory category4 = new CostCategory("Бытовые продукты");
		CostCategory category5 = new CostCategory(categoryNameSalaryForShift);
//		goodsCategoryService.save(category1);
//		goodsCategoryService.save(category2);
//		goodsCategoryService.save(category3);
//		goodsCategoryService.save(category4);
		goodsCategoryService.save(category5);

//		ZoneId zoneId = ZoneId.of("Europe/Moscow");
//
//		Cost goods1 = new Cost("Свинина", 300d, 10, category1, LocalDate.now(zoneId));
//		Cost goods2 = new Cost("Сыр", 100d, 5, category1, LocalDate.now(zoneId));
//		Cost goods3 = new Cost("Помидоры", 230d, 3, category1, LocalDate.now(zoneId));
//		Cost goods4 = new Cost("Хлеб", 30d, 10, category1, LocalDate.now(zoneId));
//		Cost goods5 = new Cost("Водка", 400d, 1, category2, LocalDate.now(zoneId));
//		Cost goods6 = new Cost("Пиво", 60d, 50, category2, LocalDate.now(zoneId));
//		Cost goods7 = new Cost("Чай", 100d, 3, category3, LocalDate.now(zoneId));
//		Cost goods8 = new Cost("Кофе", 200d, 1, category3, LocalDate.now(zoneId));
//		Cost goods9 = new Cost("Мыло", 90d, 3, category4, LocalDate.now(zoneId));
//		Cost goods10 = new Cost("Веревка", 300d, 11, category4, LocalDate.now(zoneId));
//		goodsService.save(goods1);
//		goodsService.save(goods2);
//		goodsService.save(goods3);
//		goodsService.save(goods4);
//		goodsService.save(goods5);
//		goodsService.save(goods6);
//		goodsService.save(goods7);
//		goodsService.save(goods8);
//		goodsService.save(goods9);
//		goodsService.save(goods10);
	}
}
