package com.cafe.crm.initMet;

import com.cafe.crm.models.estimate.Goods;
import com.cafe.crm.models.estimate.GoodsCategory;
import com.cafe.crm.service_abstract.goods.GoodsCategoryService;
import com.cafe.crm.service_abstract.goods.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.ZoneId;


@Component
public class InitGoods {
    private final GoodsService goodsService;

    private final GoodsCategoryService goodsCategoryService;

    @Autowired
    public InitGoods(GoodsCategoryService goodsCategoryService, GoodsService goodsService) {
        this.goodsCategoryService = goodsCategoryService;
        this.goodsService = goodsService;
    }

    @Transactional
    @PostConstruct
    public void init() {
        GoodsCategory category1 = new GoodsCategory("Продукты питания");
        GoodsCategory category2 = new GoodsCategory("Спиртные напитки");
        GoodsCategory category3 = new GoodsCategory("Безалкогольные напитки");
        GoodsCategory category4 = new GoodsCategory("Бытовые продукты");
        goodsCategoryService.save(category1);
        goodsCategoryService.save(category2);
        goodsCategoryService.save(category3);
        goodsCategoryService.save(category4);

        ZoneId zoneId = ZoneId.of("Europe/Moscow");

        Goods goods1 = new Goods("Свинина", 300d, 10, category1, LocalDate.now(zoneId));
        Goods goods2 = new Goods("Сыр", 100d, 5, category1, LocalDate.now(zoneId));
        Goods goods3 = new Goods("Помидоры", 230d, 3, category1, LocalDate.now(zoneId));
        Goods goods4 = new Goods("Хлеб", 30d, 10, category1, LocalDate.now(zoneId));
        Goods goods5 = new Goods("Водка", 400d, 1, category2, LocalDate.now(zoneId));
        Goods goods6 = new Goods("Пиво", 60d, 50, category2, LocalDate.now(zoneId));
        Goods goods7 = new Goods("Чай", 100d, 3, category3, LocalDate.now(zoneId));
        Goods goods8 = new Goods("Кофе", 200d, 1, category3, LocalDate.now(zoneId));
        Goods goods9 = new Goods("Мыло", 90d, 3, category4, LocalDate.now(zoneId));
        Goods goods10 = new Goods("Веревка", 300d, 11, category4, LocalDate.now(zoneId));
        goodsService.save(goods1);
        goodsService.save(goods2);
        goodsService.save(goods3);
        goodsService.save(goods4);
        goodsService.save(goods5);
        goodsService.save(goods6);
        goodsService.save(goods7);
        goodsService.save(goods8);
        goodsService.save(goods9);
        goodsService.save(goods10);
    }
}
