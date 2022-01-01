import org.junit.jupiter.api.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class RestaurantServiceTest {

    RestaurantService service = new RestaurantService();
    Restaurant restaurant;

    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {

        createRestaurant();
        Restaurant restaurantExist=service.findRestaurantByName("Amelie's cafe");
        assertNotNull(restaurantExist);
    }

    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {

        createRestaurant();
        assertThrows(restaurantNotFoundException.class,()->{
            service.findRestaurantByName("Cafe de Milano");
        });
    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {

        createRestaurant();
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants-1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {

        createRestaurant();
        assertThrows(restaurantNotFoundException.class,()->service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){

        createRestaurant();
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales","Chennai",LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }
    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>Calculate Bill<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Test
    public void find_order_value_should_return_zero_if_no_item_is_added()
    {
        createRestaurant();
        List<String> items=new ArrayList<String>();
        double amount=service.findOrderValue(items,restaurant);
        assertEquals(0.0,amount);
    }

    @Test
    public void find_order_value_should_return_the_total_order_value_when_not_all_the_items_from_the_menu_are_added()
    {
        createRestaurant();
        List<String> items=new ArrayList<String>();
        items.add("Sweet corn soup");
        items.add("Vegetable lasagne");
        double amount=service.findOrderValue(items,restaurant);
        assertEquals(388.0,amount);
    }

    @Test
    public void find_order_value_should_return_the_total_order_value_when_all_the_items_from_the_menu_are_added()
    {
        createRestaurant();
        List<String> items=new ArrayList<String>();
        items.add("Sweet corn soup");
        items.add("Vegetable lasagne");
        items.add("Sizzling brownie");
        double amount=service.findOrderValue(items,restaurant);
        assertEquals(707.0,amount);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<Calculate Bill>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    public void createRestaurant()
    {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Sizzling brownie", 319);

    }

}