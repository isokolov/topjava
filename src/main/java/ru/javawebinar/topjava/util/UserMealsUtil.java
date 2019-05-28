package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }


    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        //System.out.println("TODO return filtered list with correctly exceeded field");

        /* creating a HashMap for different days with an ArrayList of daily meals */
        Map<LocalDate, List<UserMeal>> map = new HashMap<>();
        for (UserMeal userMeal : mealList) {
            LocalDate date = userMeal.getDateTime().toLocalDate();
            if (!map.containsKey(date)) {
                List<UserMeal> meals = new ArrayList<UserMeal>();
                meals.add(userMeal);
                map.put(date, meals);
                continue;
            }
            if (map.containsKey(date)) {
                List<UserMeal> meals = map.get(date);
                meals.add(userMeal);
                map.put(date, meals);
            }
        }

        /* ArrayList for meals with exceeded */
        List<UserMealWithExceed> exceedList = new ArrayList<>();

        /* Creating a filtered list only for the meals between 2 dates */
        for (Map.Entry<LocalDate, List<UserMeal>> pair : map.entrySet()) {
            List<UserMeal> firstDayMeals = pair.getValue();
            //break;
            int countColoriesPerDay = 0;

            /* check if day usage exceeded that all meals are added to exceeded List */
            for (UserMeal userMeal : firstDayMeals) {
                countColoriesPerDay += userMeal.getCalories();
            }
            if (countColoriesPerDay >= caloriesPerDay) {
                for (UserMeal userMeal : firstDayMeals) {
                    if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                        exceedList.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true));
                    }
                }
            }
        }

        /* testing toString() to see the results */
        for (UserMealWithExceed userMealWithExceed : exceedList) {
            System.out.println(userMealWithExceed);
        }

        return exceedList;
    }
}
