package com.cafe.crm.services.impl.menu;

import com.cafe.crm.dto.WrapperOfProduct;
import com.cafe.crm.exceptions.services.NegativeOrZeroInputsIngredientsServiceException;
import com.cafe.crm.exceptions.services.NotEnoughIngredientsIngredientsServiceException;
import com.cafe.crm.exceptions.services.NullInputsIngredientsServiceException;
import com.cafe.crm.models.menu.Ingredients;
import com.cafe.crm.repositories.menu.IngredientsRepository;
import com.cafe.crm.services.interfaces.menu.IngredientsService;
import com.cafe.crm.utils.CompanyIdCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created on 9/8/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IngredientsServiceCreateRecipeTest {

    @MockBean
    private IngredientsRepository ingredientsRepository;

    @MockBean
    private CompanyIdCache companyIdCache;

    @Autowired
    private IngredientsService ingredientsService;

    private Ingredients ingredients1;
    private Ingredients ingredients2;
    private WrapperOfProduct wrapperOfProduct;
    private Map<Ingredients, Integer> expectedMap;

    private void initIngredientsAmounts(Integer IngAmount1, Integer IngAmount2, Integer recipeAmount1, Integer recipeAmount2) {

        ingredients1 = new Ingredients("ingredients1", "big", IngAmount1, 2D);
        ingredients2 = new Ingredients("ingredients2", "big", IngAmount2, 4D);

        wrapperOfProduct = new WrapperOfProduct();
        List<String> names = new ArrayList<>();
        List<Integer> amount = new ArrayList<>();
        names.add("ingredients1");
        names.add("ingredients2");
        amount.add(recipeAmount1);
        amount.add(recipeAmount2);
        wrapperOfProduct.setAmount(amount);
        wrapperOfProduct.setNames(names);

        expectedMap = new HashMap<>();
        expectedMap.put(ingredients1, recipeAmount1);
        expectedMap.put(ingredients2, recipeAmount2);
    }

    @Test
    public void testCreateRecipe__correctInputs__matchesExpected() {
        initIngredientsAmounts(4, 2, 2, 2);

        when(ingredientsRepository.findByNameAndCompanyId("ingredients1",1L)).thenReturn(ingredients1);
        when(ingredientsRepository.findByNameAndCompanyId("ingredients2", 1L)).thenReturn(ingredients2);
        when(companyIdCache.getCompanyId()).thenReturn(1L);

        Map<Ingredients, Integer> map = ingredientsService.createRecipe(wrapperOfProduct);

        assertEquals(expectedMap, map);
    }
    @Test(expected = NegativeOrZeroInputsIngredientsServiceException.class)
    public void testCreateRecipe__zeroRecipeAmounts__raisesException() {
        initIngredientsAmounts(4, 2, 0, 0);

        when(ingredientsRepository.findByNameAndCompanyId("ingredients1",1L)).thenReturn(ingredients1);
        when(ingredientsRepository.findByNameAndCompanyId("ingredients2", 1L)).thenReturn(ingredients2);
        when(companyIdCache.getCompanyId()).thenReturn(1L);

        ingredientsService.createRecipe(wrapperOfProduct);

    }

    @Test(expected = NotEnoughIngredientsIngredientsServiceException.class)
    public void testCreateRecipe__notEnoughIngredientsInRepository__raisesException() {
        initIngredientsAmounts(1, 1, 2, 2);

        when(ingredientsRepository.findByNameAndCompanyId("ingredients1",1L)).thenReturn(ingredients1);
        when(ingredientsRepository.findByNameAndCompanyId("ingredients2", 1L)).thenReturn(ingredients2);
        when(companyIdCache.getCompanyId()).thenReturn(1L);

        ingredientsService.createRecipe(wrapperOfProduct);
    }

    @Test(expected = NullInputsIngredientsServiceException.class)
    public void testCreateRecipe__nullWrapperOfProduct__raisesException() {
        ingredientsService.createRecipe(null);
    }

    @Test(expected = NotEnoughIngredientsIngredientsServiceException.class)
    public void testCreateRecipe__negativeAmountIngredientInRepository__raisesException() {
        initIngredientsAmounts(-1, 2, 2, 1);

        when(ingredientsRepository.findByNameAndCompanyId("ingredients1",1L)).thenReturn(ingredients1);
        when(ingredientsRepository.findByNameAndCompanyId("ingredients2", 1L)).thenReturn(ingredients2);
        when(companyIdCache.getCompanyId()).thenReturn(1L);

        ingredientsService.createRecipe(wrapperOfProduct);
    }

    @Test(expected = NegativeOrZeroInputsIngredientsServiceException.class)
    public void testCreateRecipe__negativeAmountInRecipe__raisesException() {
        initIngredientsAmounts(4, 2, -1, 1);

        when(ingredientsRepository.findByNameAndCompanyId("ingredients1",1L)).thenReturn(ingredients1);
        when(ingredientsRepository.findByNameAndCompanyId("ingredients2", 1L)).thenReturn(ingredients2);
        when(companyIdCache.getCompanyId()).thenReturn(1L);

        ingredientsService.createRecipe(wrapperOfProduct);
    }
}
